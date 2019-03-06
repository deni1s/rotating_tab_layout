package com.example.testapp.PetsList;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp.BaseFragment;
import com.example.testapp.Helper.ListHelper;
import com.example.testapp.Model.Pet;
import com.example.testapp.Model.PetResponse;
import com.example.testapp.PetDetail.PetDetailsFragment;
import com.example.testapp.R;
import com.example.testapp.Retrofit.RetrofitSingleton;
import com.example.testapp.Utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PetsFragment extends BaseFragment {

    private final static String TITLE_BUNDLE = "title";
    private final static String REQUEST_QUERY_BUNDLE = "requestQuery";
    private static final String SAVED_RECYCLER_VIEW_STATUS_ID = "list_state";
    private static final String SAVED_RECYCLER_VIEW_DATASET_ID = "items";
    private RecyclerView recyclerViewRequests;
    private PetsAdapter petsAdapter;
    private View rootView;
    private boolean isLoading;
    private Subscription subscription;
    private ArrayList<Pet> petsList = new ArrayList<>();


    public static PetsFragment newInstance(String title, String requestQuery) {
        PetsFragment fragment = new PetsFragment();
        Bundle args = new Bundle();
        args.putString(TITLE_BUNDLE, title);
        args.putString(REQUEST_QUERY_BUNDLE, requestQuery);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setTitle("Питомцы");
        if (ListHelper.isEmpty(petsList)) {
            rootView = inflater.inflate(R.layout.fragment_pets_list, container, false);
            prepareViews(rootView);
        }
        if (savedInstanceState != null) {
            restorePreviousState(savedInstanceState); // Restore data found in the Bundle
        } else if (ListHelper.isEmpty(petsList)) {
            loadPets();
        }
        return rootView;
    }

    public void restorePreviousState(Bundle savedInstanceState) {
        // getting recyclerview position
        Parcelable listState = savedInstanceState.getParcelable(SAVED_RECYCLER_VIEW_STATUS_ID);
        // getting recyclerview items
        petsList = savedInstanceState.getParcelableArrayList(SAVED_RECYCLER_VIEW_DATASET_ID);
        // Restoring adapter items
        petsAdapter.setCatsList(petsList);
        // Restoring recycler view position
        recyclerViewRequests.getLayoutManager().onRestoreInstanceState(listState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Parcelable listState = recyclerViewRequests.getLayoutManager().onSaveInstanceState();
        // putting recyclerview position
        outState.putParcelable(SAVED_RECYCLER_VIEW_STATUS_ID, listState);
        // putting recyclerview items
        outState.putParcelableArrayList(SAVED_RECYCLER_VIEW_DATASET_ID, petsList);
        super.onSaveInstanceState(outState);
    }


    private void prepareViews(View rootView) {
        petsAdapter = new PetsAdapter(getContext());
        recyclerViewRequests = rootView.findViewById(R.id.recycler_view_pets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ItemClickSupport.addTo(recyclerViewRequests).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Pet pet = petsAdapter.getItem(position);
                if (pet != null) {
                    PetDetailsFragment petDetailsFragment = PetDetailsFragment.newInstance(pet);
                    loadFragment(petDetailsFragment, true);
                }
            }
        });
        recyclerViewRequests.setLayoutManager(linearLayoutManager);
        recyclerViewRequests.setAdapter(petsAdapter);
    }

    private void loadPets() {
        showProgressBar();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = RetrofitSingleton.getModelsObservable(getArguments().getString(REQUEST_QUERY_BUNDLE)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<PetResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("das", "onError", e);
                        isLoading = false;
                        hideProgressBar();
                        if (isAdded()) {
                            hideKeyboard();
                            Snackbar.make(recyclerViewRequests, "dasdas", Snackbar.LENGTH_SHORT)
                                    .setAction("dsasda", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RetrofitSingleton.resetModelsObservable("dog");
                                            showProgressBar();
                                            loadPets();
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onNext(PetResponse petResponse) {
                        hideProgressBar();
                        isLoading = false;
                        petsList.clear();
                        petsList.addAll(petResponse.getData());
                        if (isAdded()) {
                            petsAdapter.setCatsList(petsList);
                        }
                    }
                });
    }
}

package com.example.testapp.PetsList;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp.BaseFragment;
import com.example.testapp.Model.Pet;
import com.example.testapp.Model.PetResponse;
import com.example.testapp.PetDetail.PetDetailsFragment;
import com.example.testapp.R;
import com.example.testapp.Retrofit.RetrofitSingleton;
import com.example.testapp.Utils.ItemClickSupport;

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
    private Parcelable listState;


    public static PetsFragment newInstance(String title, String requestQuery) {
        PetsFragment fragment = new PetsFragment();
        Bundle args = new Bundle();
        args.putString(TITLE_BUNDLE, title);
        args.putString(REQUEST_QUERY_BUNDLE, requestQuery);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recyclerViewRequests != null) {
            listState = recyclerViewRequests.getLayoutManager().onSaveInstanceState();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setTitle("Питомцы");
        rootView = inflater.inflate(R.layout.fragment_pets_list, container, false);
        prepareViews(rootView);
        loadPets(savedInstanceState);
        return rootView;
    }

    public void restorePreviousState(Bundle savedInstanceState) {
        if (listState == null) {
            // getting recyclerview position
            listState = savedInstanceState.getParcelable(SAVED_RECYCLER_VIEW_STATUS_ID);
            // getting recyclerview items
            //  petsAdapter.setPetsList(petsList);
            // Restoring recycler view position
        }
        recyclerViewRequests.getLayoutManager().onRestoreInstanceState(listState);
        listState = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Parcelable listState = recyclerViewRequests.getLayoutManager().onSaveInstanceState();
        // putting recyclerview position
        outState.putParcelable(SAVED_RECYCLER_VIEW_STATUS_ID, listState);
        // putting recyclerview items
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

    private void loadPets(final Bundle savedInstanceState) {
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
                        isLoading = false;
                        hideProgressBar();
                        if (isAdded()) {
                            hideKeyboard();
                            Snackbar.make(recyclerViewRequests, "dasdas", Snackbar.LENGTH_SHORT)
                                    .setAction("dsasda", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RetrofitSingleton.resetModelsObservable(getArguments().getString(REQUEST_QUERY_BUNDLE));
                                            showProgressBar();
                                            loadPets(savedInstanceState);
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onNext(PetResponse petResponse) {
                        hideProgressBar();
                        isLoading = false;
                        List<Pet> petsList = petResponse.getData();
                        if (isAdded()) {
                            petsAdapter.setPetsList(petsList);
                        }
                        if (savedInstanceState != null || listState != null) {
                            restorePreviousState(savedInstanceState);
                        }
                    }
                });
    }
}

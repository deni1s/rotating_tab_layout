package com.example.testapp.presentation.view.petslist;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp.presentation.utils.BaseFragment;
import com.example.testapp.model.Pet;
import com.example.testapp.presentation.view.petdetail.PetDetailsFragment;
import com.example.testapp.R;
import com.example.testapp.data.RetrofitSingleton;
import com.example.testapp.presentation.utils.ItemClickSupport;

import java.util.List;

public class PetsFragment extends BaseFragment implements PetsListContract.View {

    private final static String TITLE_BUNDLE = "title";
    private final static String REQUEST_QUERY_BUNDLE = "requestQuery";
    private static final String SAVED_RECYCLER_VIEW_STATUS_ID = "list_state";
    private RecyclerView recyclerViewRequests;
    private PetsAdapter petsAdapter;
    private PetsListPresenter presenter;
    private Parcelable listState;


    public static PetsFragment newInstance(String requestQuery) {
        PetsFragment fragment = new PetsFragment();
        Bundle args = new Bundle();
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
        View rootView = inflater.inflate(R.layout.fragment_pets_list, container, false);
        prepareViews(rootView);
        preparePresenter();
        tryGetSavedRecyclerViewState(savedInstanceState);
        presenter.loadPets();
        return rootView;
    }

    private void preparePresenter() {
        presenter = new PetsListPresenter();
        presenter.attachView(this);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(REQUEST_QUERY_BUNDLE)) {
            String query = bundle.getString(REQUEST_QUERY_BUNDLE);
            presenter.setQuery(query);
        }
    }

    private void tryGetSavedRecyclerViewState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(SAVED_RECYCLER_VIEW_STATUS_ID);
        }
    }

    public void restorePreviousState() {
        if (listState != null) {
            recyclerViewRequests.getLayoutManager().onRestoreInstanceState(listState);
            listState = null;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        listState = recyclerViewRequests.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVED_RECYCLER_VIEW_STATUS_ID, listState);
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

    @Override
    public void showPets(List<Pet> pets) {
        if (isAdded()) {
            petsAdapter.setPetsList(pets);
        }
        restorePreviousState();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showErrorReload(String localizedMessage) {
        if (isAdded()) {
            hideKeyboard();
            Snackbar.make(recyclerViewRequests, localizedMessage, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.reload, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RetrofitSingleton.getInstance().resetModelsObservable(getArguments().getString(REQUEST_QUERY_BUNDLE));
                            showProgressBar();
                            presenter.loadPets();
                        }
                    })
                    .show();
        }
    }
}

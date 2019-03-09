package com.example.testapp.presentation.view.petslist;

import com.example.testapp.model.Pet;
import com.example.testapp.presentation.utils.mvp.MvpPresenter;
import com.example.testapp.presentation.utils.mvp.MvpView;

import java.util.List;

public class PetsListContract {

    interface View extends MvpView {

        void showProgressBar();

        void showPets(List<Pet> pets);

        void hideProgressBar();

        void showErrorReload(String localizedMessage);
    }


    interface Presenter extends MvpPresenter<View> {

        void loadPets();
        void reloadPets();
    }
}

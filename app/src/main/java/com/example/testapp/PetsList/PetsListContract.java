package com.example.testapp.PetsList;

import com.example.testapp.Model.Pet;
import com.example.testapp.Utils.MVP.MvpPresenter;
import com.example.testapp.Utils.MVP.MvpView;

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
    }
}

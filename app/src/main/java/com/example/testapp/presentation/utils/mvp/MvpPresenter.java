package com.example.testapp.presentation.utils.mvp;

public interface MvpPresenter<V extends MvpView> {
 
    void attachView(V mvpView);

    void detachView();
}
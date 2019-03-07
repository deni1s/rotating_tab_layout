package com.example.testapp.Utils.MVP;

public interface MvpPresenter<V extends MvpView> {
 
    void attachView(V mvpView);

    void detachView();
}
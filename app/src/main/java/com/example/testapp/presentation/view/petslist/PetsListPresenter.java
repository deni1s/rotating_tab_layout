package com.example.testapp.presentation.view.petslist;

import com.example.testapp.data.PetResponse;
import com.example.testapp.data.RetrofitSingleton;
import com.example.testapp.presentation.utils.mvp.PresenterBase;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PetsListPresenter extends PresenterBase<PetsListContract.View> implements PetsListContract.Presenter {

    private Subscription subscription;
    private String query;

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public void loadPets() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        getView().showProgressBar();
        subscription = RetrofitSingleton.getInstance().getModelsObservable(query).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<PetResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgressBar();
                        getView().showErrorReload(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(PetResponse petResponse) {
                        getView().hideProgressBar();
                        getView().showPets(petResponse.getData());
                    }
                });
    }

    @Override
    public void reloadPets() {
        RetrofitSingleton.getInstance().resetModelsObservable(query);
        getView().showProgressBar();
        loadPets();
    }

    @Override
    public void detachView() {
        super.detachView();
        query = null;
        subscription.unsubscribe();
    }
}

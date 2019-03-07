package com.example.testapp.PetsList;

import com.example.testapp.Model.PetResponse;
import com.example.testapp.Retrofit.AppSingleton;
import com.example.testapp.Utils.MVP.PresenterBase;

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
        subscription = AppSingleton.getModelsObservable(query).
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
    public void detachView() {
        super.detachView();
        query = null;
        subscription.unsubscribe();
    }
}

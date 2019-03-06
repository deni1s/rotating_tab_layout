package com.example.testapp.Retrofit;

import android.util.Log;

import com.example.testapp.BuildConfig;
import com.example.testapp.Model.PetResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class RetrofitSingleton {
    private static final String TAG = RetrofitSingleton.class.getSimpleName();

    private static PetsApiInterface apiService;
    private static HashMap<String, BehaviorSubject<PetResponse>> observableModelsList;
    private static Subscription subscription;

    private RetrofitSingleton() {
    }

    public static void init() {
        Log.d(TAG, "init");

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(PetsApiInterface.class);
        observableModelsList = new HashMap<>();
    }

    public static void resetModelsObservable(String query) {
        final BehaviorSubject<PetResponse> observableModelsItem = BehaviorSubject.create();
        observableModelsList.put(query, observableModelsItem);

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = apiService.getCatsList(query).subscribe(new Subscriber<PetResponse>() {
            @Override
            public void onCompleted() {
                //do nothing
            }

            @Override
            public void onError(Throwable e) {
                observableModelsItem.onError(e);
            }

            @Override
            public void onNext(PetResponse petResponse) {
                observableModelsItem.onNext(petResponse);
            }
        });
    }


    public static Observable<PetResponse> getModelsObservable(String query) {
        Observable<PetResponse> observableModelItem = observableModelsList.get(query);
        if (observableModelItem == null) {
            resetModelsObservable(query);
        }
        return observableModelsList.get(query);
    }
}
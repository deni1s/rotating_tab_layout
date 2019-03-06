package com.example.testapp;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitSingleton.init();
    }
}

package com.example.testapp;

import com.example.testapp.data.RetrofitSingleton;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitSingleton.init();
    }
}

package com.example.testapp;

import com.example.testapp.Retrofit.AppSingleton;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppSingleton.init();
    }
}

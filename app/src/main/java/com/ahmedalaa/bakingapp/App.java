package com.ahmedalaa.bakingapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by ahmed on 21/11/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

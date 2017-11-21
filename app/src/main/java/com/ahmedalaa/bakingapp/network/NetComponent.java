package com.ahmedalaa.bakingapp.network;


import android.app.Activity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ahmed on 05/10/2017.
 */
@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    void inject(Activity activity);


    RecipeApi getPre();
}

package com.ahmedalaa.bakingapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ahmed on 05/10/2017.
 */
@Module
public class NetModule {
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @Provides
    public Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        return gsonBuilder.create();
    }

    @Provides
    public Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    @Provides
    public RecipeApi provideApi(Retrofit retrofit) {
        return retrofit.create(RecipeApi.class);

    }
}

package com.ahmedalaa.bakingapp.network;

import com.ahmedalaa.bakingapp.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by ahmed on 05/10/2017.
 */

public interface RecipeApi {
    @GET("baking.json")
    Observable<List<Recipe>> getRecipes();

}

package com.ahmedalaa.bakingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ahmed on 06/10/2017.
 */

@Parcel
public class RecipeIngredient {
    @SerializedName("quantity")
    @Expose
    public double quantity;
    @SerializedName("measure")
    @Expose
    public String measure;
    @SerializedName("ingredient")
    @Expose
    public String ingredientName;

    public RecipeIngredient() {
    }

    public RecipeIngredient(double quantity, String measure, String ingredientName) {

        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = ingredientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }
}

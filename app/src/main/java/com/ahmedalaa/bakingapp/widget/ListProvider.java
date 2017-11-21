package com.ahmedalaa.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ahmedalaa.bakingapp.R;
import com.ahmedalaa.bakingapp.model.Recipe;
import com.ahmedalaa.bakingapp.model.RecipeIngredient;
import com.ahmedalaa.bakingapp.util.IngredientsWidgetManager;

/**
 * Created by ahmed on 19/11/2017.
 */

class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    IngredientsWidgetManager ingredientsWidgetManager;
    Recipe recipe;
    Context context;

    public ListProvider(Context applicationContext) {
        ingredientsWidgetManager = new IngredientsWidgetManager(applicationContext);
        context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipe = ingredientsWidgetManager.loadRecipe();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe != null ? recipe.ingredients.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (recipe != null) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_list_item2);
            RecipeIngredient recipeIngredient = recipe.ingredients.get(i);
            remoteViews.setTextViewText(R.id.ingredient_description, recipeIngredient.ingredientName);
            remoteViews.setTextViewText(R.id.ingredient_calc, recipeIngredient.quantity + " " + recipeIngredient.measure);
            return remoteViews;
        }
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

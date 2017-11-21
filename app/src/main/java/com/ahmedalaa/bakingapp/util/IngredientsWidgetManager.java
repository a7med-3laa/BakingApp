package com.ahmedalaa.bakingapp.util;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ahmedalaa.bakingapp.R;
import com.ahmedalaa.bakingapp.model.Recipe;
import com.ahmedalaa.bakingapp.widget.IntegrientWidget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by ahmed on 21/11/2017.
 */

public class IngredientsWidgetManager {
    private final String STORAGE = " com.ahmedalaa.bakingapp.STORAGE";
    private SharedPreferences preferences;
    private Context context;

    public IngredientsWidgetManager(Context context) {
        this.context = context;
    }

    public void storeRecipe(Recipe recipe) {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        editor.putString("recipe", json);
        editor.apply();
        updateWidget();
    }

    public Recipe loadRecipe() {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("recipe", null);
        Type type = new TypeToken<Recipe>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    void updateWidget() {
        Intent i = new Intent(context, IntegrientWidget.class);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, IntegrientWidget.class);
        int[] widgetid = appWidgetManager.getAppWidgetIds(componentName);
        context.sendBroadcast(i);
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetid, R.id.appwidget_list);
    }

}


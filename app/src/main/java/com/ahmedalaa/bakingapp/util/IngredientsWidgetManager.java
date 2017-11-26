package com.ahmedalaa.bakingapp.util;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.ahmedalaa.bakingapp.R;
import com.ahmedalaa.bakingapp.model.Recipe;
import com.ahmedalaa.bakingapp.widget.IntegrientWidget;
import com.ahmedalaa.bakingapp.widget.WidgetService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by ahmed on 21/11/2017.
 */

public class IngredientsWidgetManager {
    private SharedPreferences preferences;
    private Context context;

    public IngredientsWidgetManager(Context context) {
        this.context = context;
        String STORAGE = " com.ahmedalaa.bakingapp.STORAGE";
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);

    }

    public void storeRecipe(Recipe recipe) {

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString("name2", recipe.getName());
        editor.apply();

        String json = gson.toJson(recipe);
        editor.putString("recipe", json);
        editor.apply();
        updateWidget();
    }

    public Recipe loadRecipe() {
        Gson gson = new Gson();
        String json = preferences.getString("recipe", null);
        Type type = new TypeToken<Recipe>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public String getName() {
        return preferences.getString("name2", "no Data");
    }

    private void updateWidget() {
        Intent i = new Intent(context, IntegrientWidget.class);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, IntegrientWidget.class);
        int[] widgetId = appWidgetManager.getAppWidgetIds(componentName);
        context.sendBroadcast(i);
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.appwidget_list);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        views.setRemoteAdapter(R.id.appwidget_list, new Intent(context, WidgetService.class));
        String recipe = new IngredientsWidgetManager(context).getName();

        views.setTextViewText(R.id.appwidget_text, recipe);

        appWidgetManager.updateAppWidget(widgetId, views);
    }

}


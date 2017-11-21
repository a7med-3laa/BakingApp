package com.ahmedalaa.bakingapp.ui.mainActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmedalaa.bakingapp.R;
import com.ahmedalaa.bakingapp.model.Recipe;
import com.ahmedalaa.bakingapp.network.DaggerNetComponent;
import com.ahmedalaa.bakingapp.network.RecipeApi;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipeMainActivity extends AppCompatActivity {
    private static final String RECIPE_KEY = "Recipe";

    @BindView(R.id.recipes_list)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    RecipeMainAdapter recipeMainAdapter;
    RecipeApi api;
    List<Recipe> recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_recipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        initViews();
        showProgressBar(true);
        api = DaggerNetComponent.builder().build().getPre();
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPE_KEY)) {
                recipes = Parcels.unwrap(savedInstanceState.getParcelable(RECIPE_KEY));
                recipeMainAdapter.setData(recipes);
                showProgressBar(false);
            }
        } else {

            api.getRecipes().subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(r -> {
                        recipes = r;
                        recipeMainAdapter.setData(recipes);
                        showProgressBar(false);

                    }, e -> {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        showProgressBar(false);

                    });
        }


    }

    private void showProgressBar(boolean show) {
        recyclerView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putParcelable(RECIPE_KEY, Parcels.wrap(recipes));
    }


    private void initViews() {
        recipeMainAdapter = new RecipeMainAdapter(this);

        recyclerView.setAdapter(recipeMainAdapter);
        GridLayoutManager gridLayoutManager;
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            gridLayoutManager = new GridLayoutManager(this, 3);
        } else
            gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }


}

package com.ahmedalaa.bakingapp.ui.listActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ahmedalaa.bakingapp.R;
import com.ahmedalaa.bakingapp.model.Recipe;
import com.ahmedalaa.bakingapp.ui.detailsActivity.RecipeDetailActivity;
import com.ahmedalaa.bakingapp.ui.detailsActivity.RecipeDetailFragment;
import com.ahmedalaa.bakingapp.util.IngredientsWidgetManager;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.app.NavUtils.navigateUpFromSameTask;

public class RecipeListActivity extends AppCompatActivity implements OnClickListener {
    public static final String KEY = "recipe";

    @BindView(R.id.ingredients_list)
    RecyclerView ingredientsList;

    Recipe recipe;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recipe = Parcels.unwrap(getIntent().getParcelableExtra(KEY));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(recipe.getName());

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);
        initViews();


        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpFromSameTask(this);
            return true;
        } else if (id == R.id.action_addWidget) {
            new IngredientsWidgetManager(this).storeRecipe(recipe);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        ingredientsList.setLayoutManager(new LinearLayoutManager(this));

        RecipeStepAdapter recipeStepAdapter = new RecipeStepAdapter(this, recipe.getSteps(),
                recipe.getIngredients(), this);

        ingredientsList.setAdapter(recipeStepAdapter);

    }

    @Override
    public void onItemClick(int position) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(RecipeDetailFragment.ARG_ITEM_ID, position);
            arguments.putString("name", recipe.getName());
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();
        } else {
            Context context = this;
            Bundle arguments = new Bundle();
            arguments.putInt(RecipeDetailFragment.ARG_ITEM_ID, position);
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, arguments);
            intent.putExtra("name", recipe.getName());

            context.startActivity(intent);
        }

    }

    @Override
    public boolean isTwoPane() {
        return mTwoPane;
    }
}

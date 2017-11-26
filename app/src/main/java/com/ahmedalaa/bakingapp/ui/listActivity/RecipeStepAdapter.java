package com.ahmedalaa.bakingapp.ui.listActivity;

/**
 * Created by ahmed on 11/10/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedalaa.bakingapp.R;
import com.ahmedalaa.bakingapp.model.RecipeIngredient;
import com.ahmedalaa.bakingapp.model.RecipeStep;
import com.ahmedalaa.bakingapp.model.RecipeStepWrapper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<RecipeStep> items;
    private OnClickListener clickListener;
    private List<RecipeIngredient> items2;

    public RecipeStepAdapter(Context context, List<RecipeStep> items, List<RecipeIngredient> items2, OnClickListener clickListener) {
        this.context = context;
        this.items = items;
        this.items2 = items2;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v;
        if (viewType == 1) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_item, parent, false);

            return new RecipeStepAdapterHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_list_item, parent, false);
            return new IngredientsAdapterHolder(v);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder2, int position) {
        int type = getItemViewType(position);
        if (type == 1) {
            RecipeStep item = items.get(position - (items2.size()));
            RecipeStepAdapterHolder holder = (RecipeStepAdapterHolder) holder2;
            holder.recipeName.setText(item.getShortDescription());
            if (item.getThumbnailURL().isEmpty())
                holder.recipeImg.setImageResource(R.drawable.ic_food);
            else
                Picasso.with(context).load(item.getThumbnailURL()).placeholder(R.drawable.ic_food)
                        .error(R.drawable.ic_food).into(holder.recipeImg);

        } else if (type == 0) {
            RecipeIngredient item = items2.get(position);
            IngredientsAdapterHolder holder = (IngredientsAdapterHolder) holder2;

            holder.ingredientTxt.setText(item.getIngredientName().trim());
            holder.ingredientCalc.setText(String.format("%s %s", item.getQuantity(), item.getMeasure()));

        }

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size() + items2.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position <= items2.size() - 1 ? 0 : 1;
    }

    class RecipeStepAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.recipe_img)
        ImageView recipeImg;

        RecipeStepAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {


                RecipeStepWrapper.setList(items);
                clickListener.onItemClick((getAdapterPosition() - (items2.size() - 1)));
            });
        }
    }


    public class IngredientsAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_description)
        TextView ingredientTxt;
        @BindView(R.id.ingredient_calc)
        TextView ingredientCalc;
        public IngredientsAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

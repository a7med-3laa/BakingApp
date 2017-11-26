package com.ahmedalaa.bakingapp.ui.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedalaa.bakingapp.R;
import com.ahmedalaa.bakingapp.model.Recipe;
import com.ahmedalaa.bakingapp.ui.listActivity.RecipeListActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeMainAdapter extends RecyclerView.Adapter<RecipeMainAdapter.RecipeListAdapterHolder> {
    private final Context context;
    private List<Recipe> items;

    RecipeMainAdapter(Context context) {
        items = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecipeListAdapterHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeListAdapterHolder(v);
    }

    public void setData(List<Recipe> recipes) {
        items = recipes;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecipeListAdapterHolder holder, int position) {
        Recipe item = items.get(position);
        holder.recipeName.setText(item.getName());
        if (item.getImage().isEmpty())
            holder.recipeImg.setImageResource(R.drawable.ic_food);
        else
            Picasso.with(context).load(item.getImage()).placeholder(R.drawable.ic_food)
                    .error(R.drawable.ic_food).into(holder.recipeImg);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class RecipeListAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.recipe_img)
        ImageView recipeImg;

        RecipeListAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                Intent i = new Intent(context, RecipeListActivity.class);
                i.putExtra(RecipeListActivity.KEY, Parcels.wrap(items.get(getAdapterPosition())));
                context.startActivity(i);
            });
        }
    }
}
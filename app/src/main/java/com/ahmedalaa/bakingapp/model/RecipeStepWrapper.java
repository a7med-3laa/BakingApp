package com.ahmedalaa.bakingapp.model;

import java.util.List;

/**
 * Created by ahmed on 18/10/2017.
 */

public class RecipeStepWrapper {
    public static List<RecipeStep> steps;

    public static void setList(List<RecipeStep> steps1) {
        steps = steps1;
    }

    public static RecipeStep getStep(int pos) {
        return steps.get(pos - 1);
    }

}

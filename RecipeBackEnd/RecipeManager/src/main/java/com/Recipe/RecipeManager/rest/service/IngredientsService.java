package com.Recipe.RecipeManager.rest.service;

import com.Recipe.RecipeManager.entity.Ingredients;

import java.util.Optional;

public interface IngredientsService {
    Optional<Ingredients> getIngredientsByRecipeId(int recipeId);

    Ingredients save(int recipeId, Ingredients ingredients);

    void delete(int id);

    Optional<Ingredients> getIngredientsById(int id);

    Ingredients saveIngredients(int id, Ingredients ingredients);
}

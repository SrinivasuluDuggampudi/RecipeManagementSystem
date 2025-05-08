package com.Recipe.RecipeManager.rest.service;

import com.Recipe.RecipeManager.entity.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> findAll();
    Recipe find(int id);
    Recipe save(Recipe theRecipe);
    Recipe saveById(int id,Recipe theRecipe);
    void delete(int id);
    List<Recipe> searchRecipes(String keyword,String user);

    List<Recipe> searchRecipesByKeywords(String keywords, String user);
}

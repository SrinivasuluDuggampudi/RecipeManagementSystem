package com.Recipe.RecipeManager.rest.service;

import com.Recipe.RecipeManager.dao.IngredientsRepository;
import com.Recipe.RecipeManager.dao.RecipeRepository;
import com.Recipe.RecipeManager.entity.Ingredients;
import com.Recipe.RecipeManager.entity.Instructions;
import com.Recipe.RecipeManager.entity.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService{

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private RecipeRepository recipeRepository;
    @Override
    public Optional<Ingredients> getIngredientsByRecipeId(int recipeId) {
        Optional<Ingredients> ingredients = ingredientsRepository.findByRecipeId(recipeId);

        Ingredients theIngredients;

        if(ingredients.isPresent())
        {
            theIngredients = ingredients.get();
        }
        else {
            throw new RuntimeException("No Recipe found for "+recipeId);
        }
        return Optional.of(theIngredients);
    }

    @Transactional
    @Override
    public Ingredients save(int recipeId, Ingredients ingredients) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(!(recipe.isPresent())){
            throw new RuntimeException("Recipe not found");
        }else {
            ingredients.setRecipe(recipe.get());
            return ingredientsRepository.save(ingredients);
        }
    }

    @Override
    public void delete(int id) {
        ingredientsRepository.deleteById(id);
    }

    @Override
    public Optional<Ingredients> getIngredientsById(int id) {
        Optional<Ingredients> ingredients = ingredientsRepository.findById(id);
        if(ingredients.isPresent()){
            return Optional.of(ingredients.get());
        }
        else{
            throw new RuntimeException("Ingredient not exist");
        }
    }

    @Override
    public Ingredients saveIngredients(int id, Ingredients ingredients) {
        Optional<Ingredients> theIngredients = ingredientsRepository.findById(id);
        if(theIngredients.isPresent()){
            return ingredientsRepository.save(ingredients);
        }
        else{
            throw new RuntimeException("Ingredient not exist");
        }
    }
}

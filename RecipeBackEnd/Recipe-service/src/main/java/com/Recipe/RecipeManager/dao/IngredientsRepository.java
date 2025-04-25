package com.Recipe.RecipeManager.dao;

import com.Recipe.RecipeManager.entity.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientsRepository extends JpaRepository<Ingredients,Integer> {

    Optional<Ingredients> findByRecipeId(int recipeId);
}

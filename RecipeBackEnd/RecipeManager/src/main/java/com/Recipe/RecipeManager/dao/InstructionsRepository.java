package com.Recipe.RecipeManager.dao;

import com.Recipe.RecipeManager.entity.Instructions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructionsRepository extends JpaRepository<Instructions,Integer> {

    Optional<Instructions> findByRecipeId(int recipeId);
}

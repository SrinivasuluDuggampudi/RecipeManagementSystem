package com.Recipe.RecipeManager.rest.service;

import com.Recipe.RecipeManager.entity.Instructions;
import com.Recipe.RecipeManager.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface InstructionsService {
    Optional<Instructions> getInstructionsByRecipeId(int recipeId);

    Instructions save(int recipeId, Instructions instructions);

    void delete(int id);

    Optional<Instructions> getInstructionsById(int id);

    Instructions saveInstruction(int id, Instructions instructions);
}

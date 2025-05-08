package com.Recipe.RecipeManager.rest.service;

import com.Recipe.RecipeManager.dao.InstructionsRepository;
import com.Recipe.RecipeManager.dao.RecipeRepository;
import com.Recipe.RecipeManager.entity.Ingredients;
import com.Recipe.RecipeManager.entity.Instructions;
import com.Recipe.RecipeManager.entity.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructionsServiceImpl implements InstructionsService{

    @Autowired
    private InstructionsRepository instructionsRepository;

    @Autowired
    private RecipeRepository recipeRepository;
    @Override
    public Optional<Instructions> getInstructionsByRecipeId(int recipeId) {
        return instructionsRepository.findByRecipeId(recipeId);
    }

    @Transactional
    @Override
    public Instructions save(int recipeId, Instructions instructions) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(!(recipe.isPresent())){
            throw new RuntimeException("Recipe not found");
        }else{
            instructions.setRecipe(recipe.get());
        return instructionsRepository.save(instructions);}
        }
    @Transactional
    @Override
    public void delete(int id) {
        instructionsRepository.deleteById(id);
    }

    @Override
    public Optional<Instructions> getInstructionsById(int id) {
        Optional<Instructions> instructions = instructionsRepository.findById(id);
        if(instructions.isPresent()){
            return Optional.of(instructions.get());
        }
        else{
            throw new RuntimeException("Instruction not exist");
        }
    }

    @Override
    public Instructions saveInstruction(int id, Instructions instructions) {
        Optional<Instructions> theInstructions = instructionsRepository.findById(id);
        if(theInstructions.isPresent()){
            return instructionsRepository.save(instructions);
        }
        else{
            throw new RuntimeException("Instruction not exist");
        }
    }
}



package com.Recipe.RecipeManager.rest.service;

import com.Recipe.RecipeManager.dao.IngredientsRepository;
import com.Recipe.RecipeManager.dao.InstructionsRepository;
import com.Recipe.RecipeManager.dao.RecipeRepository;
import com.Recipe.RecipeManager.entity.Ingredients;
import com.Recipe.RecipeManager.entity.Instructions;
import com.Recipe.RecipeManager.entity.Recipe;
import com.Recipe.RecipeManager.exception.NoIngredientFoundException;
import com.Recipe.RecipeManager.exception.NoInstructionFoundException;
import com.Recipe.RecipeManager.exception.NoRecipeFoundException;
import jakarta.transaction.Transactional;
import org.aspectj.apache.bcel.generic.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService{

    private RecipeRepository recipeRepository;
    @Autowired
    public RecipeServiceImpl(RecipeRepository theRecipeRepository)
    {
        recipeRepository = theRecipeRepository;
    }
    @Autowired
    private IngredientsRepository ingredientRepository;

    @Autowired
    private InstructionsRepository instructionRepository;
    public List<Recipe> findAll()
    {
        return recipeRepository.findAll();
    }
    @Override
    public Recipe find(int id)
    {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        Recipe theRecipe;

        if(recipe.isPresent())
        {
            theRecipe = recipe.get();
        }
        else {
            throw new NoRecipeFoundException("No Recipe Found");
        }
        return theRecipe;
    }
    @Transactional
    @Override
    public Recipe save(Recipe theRecipe) {
        return recipeRepository.save(theRecipe);
    }
    @Transactional
    public Recipe saveById(int id, Recipe theRecipe) {
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);

        if (existingRecipe.isPresent()) {
            Recipe existingRecipeNew = existingRecipe.get();
            existingRecipeNew.setName(theRecipe.getName());
            existingRecipeNew.setDescription(theRecipe.getDescription());
            existingRecipeNew.setCookingTime(theRecipe.getCookingTime());
            existingRecipeNew.setRating(theRecipe.getRating());
            existingRecipeNew.setServings(theRecipe.getServings());
            existingRecipeNew.setUpdatedDate(LocalDateTime.now());

            recipeRepository.save(existingRecipeNew);

            for (Ingredients ingredient : theRecipe.getIngredients()) {
                if (ingredient.getId() == null || ingredient.getId() == 0) {
                    ingredient.setRecipe(existingRecipeNew);
                    ingredientRepository.save(ingredient);
                } else {
                    Optional<Ingredients> existingIngredientOpt = ingredientRepository.findById(ingredient.getId());
                    if (existingIngredientOpt.isPresent()) {
                        Ingredients existingIngredient = existingIngredientOpt.get();
                        existingIngredient.setName(ingredient.getName());
                        existingIngredient.setQuantity(ingredient.getQuantity());
                        ingredientRepository.save(existingIngredient);
                    } else {
                        throw new NoIngredientFoundException("No Ingredient exists with this ID: " + ingredient.getId());
                    }
                }
            }

            for (Instructions instruction : theRecipe.getInstructions()) {
                if (instruction.getId() == null || instruction.getId() == 0) {
                    instruction.setRecipe(existingRecipeNew);
                    instructionRepository.save(instruction);
                } else {
                    Optional<Instructions> existingInstructionOpt = instructionRepository.findById(instruction.getId());
                    if (existingInstructionOpt.isPresent()) {
                        Instructions existingInstruction = existingInstructionOpt.get();
                        existingInstruction.setStep(instruction.getStep());
                        instructionRepository.save(existingInstruction);
                    } else {
                        throw new NoInstructionFoundException("No Instruction exists with this ID: " + instruction.getId());
                    }
                }
            }

            return existingRecipeNew;
        } else {
            throw new NoRecipeFoundException("No Recipe Found with ID: " + id);
        }
    }

    @Transactional
    @Override
    public void delete(int id)
    {
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);
        if(existingRecipe.isPresent()) {
            recipeRepository.deleteById(id);
        }else{
            throw new NoRecipeFoundException("No Recipe Found");
        }
    }

    @Override
    public List<Recipe> searchRecipes(String keyword,String user) {
        return recipeRepository.searchByNameOrDescription(keyword,user);
    }
    public List<Recipe> searchRecipesByKeywords(String keywords, String user) {
        List<String> keywordList = Arrays.stream(keywords.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        List<Recipe> allRecipes = recipeRepository.findAll();

        return allRecipes.stream()
                .filter(recipe -> recipe.getCreatedBy().equalsIgnoreCase(user))
                .filter(recipe -> keywordList.stream().anyMatch(keyword ->
                        recipe.getName().toLowerCase().contains(keyword) ||
                                recipe.getDescription().toLowerCase().contains(keyword) ||
                                recipe.getCreatedBy().toLowerCase().contains(keyword) ||
                                recipe.getIngredients().stream().anyMatch(ing ->
                                        ing.getName().toLowerCase().contains(keyword)) ||
                                recipe.getInstructions().stream().anyMatch(ins ->
                                        ins.getStep().toLowerCase().contains(keyword)))
                )
                .collect(Collectors.toList());
    }
}

package com.Recipe.RecipeManager.rest.Controller;

import com.Recipe.RecipeManager.entity.Ingredients;
import com.Recipe.RecipeManager.rest.service.IngredientsService;
import com.Recipe.RecipeManager.security.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class IngredientsController {

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private AuthClient authClient;

    @PostMapping("/recipes/ingredients/{recipeId}")
    public Ingredients addIngredients(@PathVariable int recipeId, @RequestBody Ingredients ingredients,
                                      @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        return ingredientsService.save(recipeId, ingredients);
    }

    @GetMapping("/recipes/ingredients/{recipeId}")
    public Optional<Ingredients> getIngredients(@PathVariable int recipeId,
                                                @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        return ingredientsService.getIngredientsByRecipeId(recipeId);
    }

    @DeleteMapping("/ingredients/{id}")
    public String deleteIngredientsById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        Optional<Ingredients> ingredients = ingredientsService.getIngredientsById(id);
        if (ingredients.isPresent()) {
            ingredientsService.delete(id);
            return "Ingredient Deleted Successfully";
        } else {
            throw new RuntimeException("Ingredient doesn't exist to delete");
        }
    }

    @GetMapping("/ingredients/{id}")
    public Ingredients getIngredientsById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        Optional<Ingredients> ingredients = ingredientsService.getIngredientsById(id);
        if (ingredients.isPresent()) {
            return ingredients.get();
        } else {
            throw new RuntimeException("Ingredient doesn't exist");
        }
    }

    @PutMapping("/ingredients/{id}")
    public Ingredients updateIngredientsDetails(@PathVariable int id, @RequestBody Ingredients ingredients,
                                                @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        return ingredientsService.saveIngredients(id, ingredients);
    }
}

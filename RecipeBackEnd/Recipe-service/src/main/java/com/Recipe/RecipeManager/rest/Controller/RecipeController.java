package com.Recipe.RecipeManager.rest.Controller;

import com.Recipe.RecipeManager.dao.RecipeRepository;
import com.Recipe.RecipeManager.entity.Recipe;
import com.Recipe.RecipeManager.rest.service.RecipeService;
//import com.Recipe.RecipeManager.security.AuthClient;
import com.Recipe.RecipeManager.security.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RecipeController {

    private RecipeService recipeService;
    @Autowired
    private AuthClient authClient;

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    public RecipeController(RecipeService theRecipeService)
    {
        recipeService = theRecipeService;
    }

    //This doesn't contains JWT config
    /*
    @GetMapping("/recipes")
    public List<Recipe> getAllRecipeDetails(){
        return recipeService.findAll();
    }
    @GetMapping("/recipes/{recipeId}")
    public Recipe getRecipeDetailsById(@PathVariable int recipeId){
        return recipeService.find(recipeId);
    }
    @PostMapping("/recipes")
    public Recipe saveRecipeDetails(@RequestBody Recipe recipe)
    {
        //also just in case they pass an id in JSON set id to 0
        recipe.setId(0);
        return recipeService.save(recipe);
    }
    @PutMapping("/recipes/{recipeId}")
    public Recipe updateRecipeDetails(@PathVariable int recipeId,@RequestBody Recipe recipe)
    {
        return recipeService.saveById(recipeId,recipe);
    }
    @DeleteMapping("/recipes/{recipeId}")
    public String deleteRecipeDetails(@PathVariable int recipeId)
    {
        Recipe recipe = recipeService.find(recipeId);
        if(recipe == null){
            return "No Recipe found to delete for Id"+recipeId;
        }
        else{
            recipeService.delete(recipeId);
            return "Recipe Deleted Successfully";
        }
    }
*/
    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipeDetails(@RequestHeader("Authorization") String token) {
        ResponseEntity<String> response = authClient.validateToken(token);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(recipeService.findAll());
    }

    @GetMapping("/recipes/{recipeId}")
    public ResponseEntity<Recipe> getRecipeDetailsById(@PathVariable int recipeId, @RequestHeader("Authorization") String token) {
        ResponseEntity<String> response = authClient.validateToken(token);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Recipe recipe = recipeService.find(recipeId);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> saveRecipeDetails(@RequestBody Recipe recipe,@RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);

            if (response.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            System.out.println("response"+response);
            String username = response.getBody();
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            recipe.setId(0);
            recipe.setCreatedBy(recipe.getCreatedBy());

            recipe.setCreatedDate(LocalDateTime.now());
            recipe.setUpdatedDate(LocalDateTime.now());

            Recipe savedRecipe = recipeService.save(recipe);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/recipes/{recipeId}")
    public ResponseEntity<Recipe> updateRecipeDetails(@PathVariable int recipeId, @RequestBody Recipe recipe, @RequestHeader("Authorization") String token) {
        ResponseEntity<String> response = authClient.validateToken(token);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        recipe.setUpdatedDate(LocalDateTime.now());
        return ResponseEntity.ok(recipeService.saveById(recipeId, recipe));
    }

    @DeleteMapping("/recipes/{recipeId}")
    public ResponseEntity<String> deleteRecipeDetails(@PathVariable int recipeId, @RequestHeader("Authorization") String token) {
        ResponseEntity<String> response = authClient.validateToken(token);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Recipe recipe = recipeService.find(recipeId);
        if (recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Recipe found to delete for Id " + recipeId);
        }
        recipeService.delete(recipeId);
        return ResponseEntity.ok("Recipe Deleted Successfully");
    }

    @GetMapping("/recipes/user")
    public ResponseEntity<List<Recipe>> getUserRecipes(@RequestParam String usernew,@RequestHeader("Authorization") String token) {
        ResponseEntity<String> response = authClient.validateToken(token);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = response.getBody();
        List<Recipe> recipes = recipeRepository.findByCreatedBy(usernew);
        //System.out.println("recipes"+recipes);
        return ResponseEntity.ok(recipes);
    }
    @GetMapping("/recipes/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String key,@RequestParam String user,@RequestHeader("Authorization") String token) {

        ResponseEntity<String> response = authClient.validateToken(token);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Recipe> results = recipeService.searchRecipes(key,user);
        return ResponseEntity.ok(results);
    }
}

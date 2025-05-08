package com.Recipe.RecipeManager.rest.Controller;

import com.Recipe.RecipeManager.entity.Instructions;
import com.Recipe.RecipeManager.rest.service.InstructionsService;
import com.Recipe.RecipeManager.security.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class InstructionsController {

    @Autowired
    private InstructionsService instructionsService;

    @Autowired
    private AuthClient authClient;

    @PostMapping("/recipes/instructions/{recipeId}")
    public Instructions addInstructions(@PathVariable int recipeId, @RequestBody Instructions instructions,
                                        @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        return instructionsService.save(recipeId, instructions);
    }

    @GetMapping("/recipes/instructions/{recipeId}")
    public Optional<Instructions> getInstruction(@PathVariable int recipeId,
                                                 @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        return instructionsService.getInstructionsByRecipeId(recipeId);
    }

    @DeleteMapping("/instructions/{id}")
    public String deleteInstructionById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        Optional<Instructions> instructions = instructionsService.getInstructionsById(id);
        if (instructions.isPresent()) {
            instructionsService.delete(id);
            return "Instruction Deleted Successfully";
        } else {
            throw new RuntimeException("Instruction doesn't exist to delete");
        }
    }

    @GetMapping("/instructions/{id}")
    public Instructions getInstructionById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        Optional<Instructions> instructions = instructionsService.getInstructionsById(id);
        if (instructions.isPresent()) {
            return instructions.get();
        } else {
            throw new RuntimeException("Instruction doesn't exist");
        }
    }

    @PutMapping("/instructions/{id}")
    public Instructions updateInstructionsDetails(@PathVariable int id, @RequestBody Instructions instructions,
                                                  @RequestHeader("Authorization") String token) {
        try {
            ResponseEntity<String> response = authClient.validateToken(token);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unauthorized");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized", e);
        }
        return instructionsService.saveInstruction(id, instructions);
    }
}

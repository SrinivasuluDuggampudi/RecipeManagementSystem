package com.Recipe.RecipeManager.rest.Controller;

import com.Recipe.RecipeManager.rest.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin
public class AIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateRecipe(@RequestBody Map<String, String> request) {
        // Check if the request contains a valid prompt
        String prompt = request.get("prompt");
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Prompt is required.");
        }

        try {
            // Pass the full request map (not just the prompt) to the service
            String aiResponse = openAIService.generateRecipe(request);
            return ResponseEntity.ok(aiResponse);
        } catch (Exception e) {
            // Return an error response if something goes wrong
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating recipe: " + e.getMessage());
        }
    }
}

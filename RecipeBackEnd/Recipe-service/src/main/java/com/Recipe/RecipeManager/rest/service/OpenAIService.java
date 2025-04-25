package com.Recipe.RecipeManager.rest.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
@Service
public class OpenAIService {

    private static final String API_URL = "https://models.github.ai/inference/openai/gpt-4.1";
    private static final String API_KEY = "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";

    public String generateRecipe(Map<String, String> request) {
        String inputText = request.getOrDefault("inputText", "Once upon a time in a land far away,");
        int maxTokens = Integer.parseInt(request.getOrDefault("maxTokens", "150"));
        double temperature = Double.parseDouble(request.getOrDefault("temperature", "0.7"));

        String jsonInputString = "{"
                + "\"input\": \"" + inputText + "\","
                + "\"max_tokens\": " + maxTokens + ","
                + "\"temperature\": " + temperature
                + "}";

        try {
            URL url = new URL(API_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                return response.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}

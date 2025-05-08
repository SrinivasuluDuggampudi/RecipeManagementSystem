package com.Recipe.RecipeManager;

import com.Recipe.RecipeManager.dao.RecipeRepository;
import com.Recipe.RecipeManager.entity.Recipe;
import com.Recipe.RecipeManager.rest.Controller.RecipeController;
import com.Recipe.RecipeManager.rest.service.RecipeService;
import com.Recipe.RecipeManager.security.AuthClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class RecipeManagerApplicationTests {

	@InjectMocks
	private RecipeController recipeController;

	@Mock
	private RecipeService recipeService;

	@Mock
	private AuthClient authClient;

	@Mock
	private RecipeRepository recipeRepository;

	private Recipe sampleRecipe;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		sampleRecipe = new Recipe();
		sampleRecipe.setId(1);
		sampleRecipe.setName("Biryani");
		sampleRecipe.setDescription("Nicely made");
		sampleRecipe.setCreatedBy("user1");
		sampleRecipe.setCreatedDate(LocalDateTime.now());
		sampleRecipe.setUpdatedDate(LocalDateTime.now());
	}

	@Test
	public void testGetAllRecipeDetails_Success() {
		when(authClient.validateToken("Bearer token")).thenReturn(ResponseEntity.ok("user1"));
		when(recipeService.findAll()).thenReturn(List.of(sampleRecipe));

		ResponseEntity<List<Recipe>> response = recipeController.getAllRecipeDetails("Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	@Test
	public void testGetRecipeDetailsById_Success() {
		when(authClient.validateToken("Bearer token")).thenReturn(ResponseEntity.ok("user1"));
		when(recipeService.find(1)).thenReturn(sampleRecipe);

		ResponseEntity<Recipe> response = recipeController.getRecipeDetailsById(1, "Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(sampleRecipe, response.getBody());
	}

	@Test
	public void testSaveRecipeDetails_Success() {
		when(authClient.validateToken("Bearer token")).thenReturn(ResponseEntity.ok("user1"));
		when(recipeService.save(any(Recipe.class))).thenReturn(sampleRecipe);

		ResponseEntity<Recipe> response = recipeController.saveRecipeDetails(sampleRecipe, "Bearer token");

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	public void testUpdateRecipeDetails_Success() {
		when(authClient.validateToken("Bearer token")).thenReturn(ResponseEntity.ok("user1"));
		when(recipeService.saveById(eq(1), any(Recipe.class))).thenReturn(sampleRecipe);

		ResponseEntity<Recipe> response = recipeController.updateRecipeDetails(1, sampleRecipe, "Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteRecipeDetails_Success() {
		when(authClient.validateToken("Bearer token")).thenReturn(ResponseEntity.ok("user1"));
		when(recipeService.find(1)).thenReturn(sampleRecipe);

		ResponseEntity<String> response = recipeController.deleteRecipeDetails(1, "Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Recipe Deleted Successfully", response.getBody());
	}

	@Test
	public void testGetUserRecipes_Success() {
		when(authClient.validateToken("Bearer token")).thenReturn(ResponseEntity.ok("user1"));
		when(recipeRepository.findByCreatedBy("user1")).thenReturn(List.of(sampleRecipe));

		ResponseEntity<List<Recipe>> response = recipeController.getUserRecipes("user1", "Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(response.getBody().isEmpty());
	}

	@Test
	public void testSearchRecipes_Success() {
		when(authClient.validateToken("Bearer token")).thenReturn(ResponseEntity.ok("user1"));
		when(recipeService.searchRecipes("test", "user1")).thenReturn(List.of(sampleRecipe));

		ResponseEntity<List<Recipe>> response = recipeController.searchRecipes("test", "user1", "Bearer token");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	@Test
	public void testUnauthorizedToken() {
		when(authClient.validateToken("Bearer token")).thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

		ResponseEntity<List<Recipe>> response = recipeController.getAllRecipeDetails("Bearer token");

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
}

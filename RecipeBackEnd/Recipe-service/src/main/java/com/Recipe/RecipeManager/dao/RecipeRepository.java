package com.Recipe.RecipeManager.dao;

import com.Recipe.RecipeManager.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Integer> {
List<Recipe> findByCreatedBy(String username);

@Query("SELECT r FROM Recipe r WHERE r.createdBy = :user AND (UPPER(r.name) LIKE UPPER(CONCAT('%', :key, '%')) OR UPPER(r.description) LIKE UPPER(CONCAT('%', :key, '%')))")
List<Recipe> searchByNameOrDescription(@Param("key") String key,@Param("user") String user);
}

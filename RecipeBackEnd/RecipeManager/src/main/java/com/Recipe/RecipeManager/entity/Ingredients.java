package com.Recipe.RecipeManager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "recipe")
public class Ingredients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String quantity;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    @JsonBackReference
    private Recipe recipe;

}

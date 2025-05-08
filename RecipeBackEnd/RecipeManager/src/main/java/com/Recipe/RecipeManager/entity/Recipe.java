package com.Recipe.RecipeManager.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"ingredients", "instructions"})// used for tostring infinte resoltion
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private String createdBy;

    private int servings;

    private String cookingTime;

    private int rating;
    @JsonManagedReference //for json serialization used for infinite loop of ingrediant and instructions
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredients> ingredients;
    @JsonManagedReference
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Instructions> instructions;


}

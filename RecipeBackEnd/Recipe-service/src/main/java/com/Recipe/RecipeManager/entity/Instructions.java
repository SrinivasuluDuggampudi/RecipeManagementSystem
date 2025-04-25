package com.Recipe.RecipeManager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Instructions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "recipe") // used for tostring infinte resoltion
public class Instructions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String step;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    @JsonBackReference //for json serialization used for infinite loop of ingrediant and instructions
    private Recipe recipe;

}

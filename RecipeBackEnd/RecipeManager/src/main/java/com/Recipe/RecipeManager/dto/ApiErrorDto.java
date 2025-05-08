package com.Recipe.RecipeManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDto {
    public String errorDesc;
    public int errorCode;

}

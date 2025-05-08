package com.Recipe.RecipeManager.exception;

import com.Recipe.RecipeManager.dto.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = NoRecipeFoundException.class)
    public ResponseEntity<ApiErrorDto> NoRecipeExceptionHandler(){
        ApiErrorDto er = new ApiErrorDto("No Recipe found for this id",400);
        return new ResponseEntity<ApiErrorDto>(er,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NoIngredientFoundException.class)
    public ResponseEntity<ApiErrorDto> NoIngredientFoundExceptionHandler(){
        ApiErrorDto er = new ApiErrorDto("No Ingredient found for this id",400);
        return new ResponseEntity<ApiErrorDto>(er,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NoInstructionFoundException.class)
    public ResponseEntity<ApiErrorDto> NoInstructionFoundExceptionHandler(){
        ApiErrorDto er = new ApiErrorDto("No Instruction found for this id",400);
        return new ResponseEntity<ApiErrorDto>(er,HttpStatus.BAD_REQUEST);
    }
}

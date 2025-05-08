package com.Recipe.RecipeManager.exception;

public class NoRecipeFoundException extends RuntimeException{
    public NoRecipeFoundException(String msg){
        super(msg);
    }
}

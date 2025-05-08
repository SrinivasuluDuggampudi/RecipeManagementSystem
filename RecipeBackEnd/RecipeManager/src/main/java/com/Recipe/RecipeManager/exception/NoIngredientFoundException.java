package com.Recipe.RecipeManager.exception;

public class NoIngredientFoundException extends RuntimeException{
   public NoIngredientFoundException(String msg)
   {
       super(msg);
   }
}

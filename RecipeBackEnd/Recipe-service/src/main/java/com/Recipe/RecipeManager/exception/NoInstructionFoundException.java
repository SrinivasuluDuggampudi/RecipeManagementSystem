package com.Recipe.RecipeManager.exception;

public class NoInstructionFoundException extends RuntimeException{
    public NoInstructionFoundException(String msg)
    {
        super(msg);
    }
}

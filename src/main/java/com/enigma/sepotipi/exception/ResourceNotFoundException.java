package com.enigma.sepotipi.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super(String.format("Resource for this id is not found."));
    }
}
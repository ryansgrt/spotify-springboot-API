package com.enigma.sepotipi.exception;

public class EmailNotValidException extends RuntimeException{
    public EmailNotValidException(){
        super(String.format("This email is invalid."));
    }
}

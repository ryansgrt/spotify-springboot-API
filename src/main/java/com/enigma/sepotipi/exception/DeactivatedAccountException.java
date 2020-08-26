package com.enigma.sepotipi.exception;

public class DeactivatedAccountException extends RuntimeException{
    public DeactivatedAccountException(){
        super(String.format("This account is not active"));
    }
}

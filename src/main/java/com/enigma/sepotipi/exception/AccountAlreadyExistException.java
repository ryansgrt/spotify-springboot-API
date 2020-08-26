package com.enigma.sepotipi.exception;

public class AccountAlreadyExistException extends RuntimeException{
    public AccountAlreadyExistException(){
        super(String.format("Account is already exist"));
    }
}

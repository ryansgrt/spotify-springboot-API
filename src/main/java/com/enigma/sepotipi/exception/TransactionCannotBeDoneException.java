package com.enigma.sepotipi.exception;

public class TransactionCannotBeDoneException extends RuntimeException{

    public TransactionCannotBeDoneException(){
        super(String.format("Transaction cannot be done"));
    }
}

package com.enigma.sepotipi.exception;

public class ThisItemHasAlreadyBoughtException extends RuntimeException{
    public ThisItemHasAlreadyBoughtException(){
        super(String.format("This item has already bought."));
    }
}

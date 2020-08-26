package com.enigma.sepotipi.exception;

public class BalanceNotEnoughToTakeThisActionException extends RuntimeException{
    public BalanceNotEnoughToTakeThisActionException(){
        super(String.format("Balance is not enough to take this action."));
    }
}

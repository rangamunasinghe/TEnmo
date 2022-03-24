package com.techelevator.tenmo.exceptions;

public class InsufficientFundsException extends Exception{
    String message = "\nYou have insufficient funds for this transaction!\n";

    public InsufficientFundsException () {
    }

    @Override
    public String getMessage() {
        return message;
    }
}

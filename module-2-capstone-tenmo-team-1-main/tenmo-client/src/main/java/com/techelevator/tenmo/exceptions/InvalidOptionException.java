package com.techelevator.tenmo.exceptions;

public class InvalidOptionException extends Exception{
    public InvalidOptionException () {
    }

    String message = "\nInvalid Option Selection\n";

    @Override
    public String getMessage() {
        return message;
    }
}

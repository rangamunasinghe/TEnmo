package com.techelevator.tenmo.exceptions;

public class IllegalTransferAmountException extends Exception {
    String message = "\nTransfer amount must be greater than zero\n";

    public IllegalTransferAmountException () {

    }

    @Override
    public String getMessage() {return message;}

}

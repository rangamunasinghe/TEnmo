package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Transfer Does Not Exist")
public class TransferNotFoundException extends Exception {
    private static final long SERIAL_VERSION_UID = 1L;

    public TransferNotFoundException () {
        super("Transfer Does Not Exist");
    }

}

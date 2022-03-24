package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User Does Not Exist")
public class UserNotFoundException extends Exception{
    private static final long SERIAL_VERSION_UID = 1L;

    public UserNotFoundException () {
        super("User Does Not Exist");
    }

}

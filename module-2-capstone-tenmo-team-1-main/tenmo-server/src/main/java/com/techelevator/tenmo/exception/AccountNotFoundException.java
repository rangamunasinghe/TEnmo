package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Account Does Not Exist")
public class AccountNotFoundException extends Exception {

        private static final long SERIAL_VERSION_UID = 1L;

        public AccountNotFoundException () {
            super("Account Does Not Exist");
        }

}

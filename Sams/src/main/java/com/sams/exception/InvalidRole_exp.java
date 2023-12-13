package com.sams.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class InvalidRole_exp extends RuntimeException {

    public InvalidRole_exp(String message) {
        super(message);
    }
}

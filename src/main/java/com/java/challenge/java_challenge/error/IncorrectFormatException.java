package com.java.challenge.java_challenge.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
public class IncorrectFormatException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 402114765770301738L;
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public IncorrectFormatException(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

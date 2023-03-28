package com.java.challenge.java_challenge.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Getter
public class RepositoryException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -4607986199110034184L;

    private final Throwable throwable;
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public RepositoryException(Throwable throwable, String code, String message, HttpStatus httpStatus) {
        super(throwable);
        this.throwable = throwable;
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public RepositoryException(String code, String message, HttpStatus httpStatus) {
        this.throwable = null;
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }


}

package com.java.challenge.java_challenge.controller;

import com.java.challenge.java_challenge.error.ErrorMessage;
import com.java.challenge.java_challenge.error.InvalidAccessException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class SecurityControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> passwordNullException(){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timeStamp(LocalDate.now())
                .code(HttpStatus.BAD_REQUEST.toString())
                .detailMessage("Password can't be empty")
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorMessage> tokenException(SignatureException signatureException){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timeStamp(LocalDate.now())
                .code(HttpStatus.UNAUTHORIZED.toString())
                .detailMessage(signatureException.getMessage())
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<ErrorMessage> incorrectPasswordException(InvalidAccessException invalidAccessException){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timeStamp(LocalDate.now())
                .code(invalidAccessException.getCode())
                .detailMessage(invalidAccessException.getMessage())
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}

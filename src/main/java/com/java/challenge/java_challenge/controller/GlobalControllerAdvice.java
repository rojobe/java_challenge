package com.java.challenge.java_challenge.controller;

import com.java.challenge.java_challenge.error.ErrorMessage;
import com.java.challenge.java_challenge.error.IncorrectFormatException;
import com.java.challenge.java_challenge.error.InvalidAccessException;
import com.java.challenge.java_challenge.error.RepositoryException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalControllerAdvice {



     @ExceptionHandler(RepositoryException.class)
     public ResponseEntity<ErrorMessage> repositoryException(RepositoryException repositoryException){
          ErrorMessage errorMessage = ErrorMessage.builder()
                  .timeStamp(LocalDate.now())
                  .code(repositoryException.getCode())
                  .detailMessage(repositoryException.getMessage())
                  .build();

          return new ResponseEntity<>(errorMessage, repositoryException.getHttpStatus());
     }

     @ExceptionHandler(ConstraintViolationException.class)
     public ResponseEntity<ErrorMessage> emailNullException(){
          ErrorMessage errorMessage = ErrorMessage.builder()
                  .timeStamp(LocalDate.now())
                  .code(HttpStatus.BAD_REQUEST.toString())
                  .detailMessage("Email can't be empty")
                  .build();

          return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
     }

     @ExceptionHandler(IncorrectFormatException.class)
     public ResponseEntity<ErrorMessage> incorrectFormat(IncorrectFormatException incorrectFormatException){
          ErrorMessage errorMessage = ErrorMessage.builder()
                  .timeStamp(LocalDate.now())
                  .code(incorrectFormatException.getCode())
                  .detailMessage(incorrectFormatException.getMessage())
                  .build();

          return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
     }

}

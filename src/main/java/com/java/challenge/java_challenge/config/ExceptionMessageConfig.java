package com.java.challenge.java_challenge.config;

import com.java.challenge.java_challenge.error.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("errors")
public class ExceptionMessageConfig {

    private ErrorMessage userNotFound;
    private ErrorMessage emailAlreadyRegistered;
    private ErrorMessage incorrectEmailFormat;
    private ErrorMessage incorrectPasswordFormat;
    private ErrorMessage emailCanNotBeEmpty;
    private ErrorMessage passwordCanNotBeEmpty;



}

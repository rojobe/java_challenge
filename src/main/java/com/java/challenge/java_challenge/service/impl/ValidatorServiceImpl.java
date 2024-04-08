package com.java.challenge.java_challenge.service.impl;

import com.java.challenge.java_challenge.service.ValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
@Slf4j
@Service
public class ValidatorServiceImpl implements ValidatorService {


    @Override
    public boolean regexPassword(String password) {
        if(Pattern.matches(PASS_REGEX, password)){
            return true;
        }
        return false;
    }

    @Override
    public boolean regexEmail(String email) {
        if(Pattern.matches(EMAIL_REGEX, email)){
            return true;
        }
        return false;
    }
}

package com.java.challenge.java_challenge.service.impl;

import com.java.challenge.java_challenge.service.RegularExpresionService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegularExpresionImpl implements RegularExpresionService {


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

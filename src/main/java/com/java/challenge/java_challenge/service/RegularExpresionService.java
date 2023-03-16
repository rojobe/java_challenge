package com.java.challenge.java_challenge.service;

import java.util.regex.Pattern;

public interface RegularExpresionService {

    String PASS_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"; //example: a2asfGf6
    String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    boolean regexPassword(String password);

    boolean regexEmail(String email);

}

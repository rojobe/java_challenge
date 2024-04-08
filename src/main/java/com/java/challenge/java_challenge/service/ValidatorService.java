package com.java.challenge.java_challenge.service;

public interface ValidatorService {

    String PASS_REGEX = "^(?=[^A-Z]*[A-Z])(?=\\D*\\d){2}\\w{8,12}$";

    String EMAIL_REGEX = "^[a-zA-Z]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";


    boolean regexPassword(String password);

    boolean regexEmail(String email);

}

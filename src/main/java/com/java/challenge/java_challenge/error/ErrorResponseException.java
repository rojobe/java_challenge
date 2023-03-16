package com.java.challenge.java_challenge.error;

import java.util.List;

public class ErrorResponseException extends Exception{

    private List<Error> errorList;

    public List<Error> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<Error> errorList) {
        this.errorList = errorList;
    }
}

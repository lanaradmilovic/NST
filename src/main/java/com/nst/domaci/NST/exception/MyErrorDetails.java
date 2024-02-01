package com.nst.domaci.NST.exception;

public class MyErrorDetails {
    String message;

    public MyErrorDetails(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.ysl.dagger2.model;

import javax.inject.Inject;

public class U {
    private String message;

//    @Inject
    public U(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "U{" +
                "message='" + message + '\'' +
                '}';
    }
}

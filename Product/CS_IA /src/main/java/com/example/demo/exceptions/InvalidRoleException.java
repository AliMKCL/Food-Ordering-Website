package com.example.demo.exceptions;

public class InvalidRoleException extends WebRedirectException{

    //Sends users to the login page if they try to access a page their role can't
    public InvalidRoleException(String errorMessage) {
        super(errorMessage);
        this.redirectUrl = "/login";
    }
}

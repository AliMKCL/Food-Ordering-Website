package com.example.demo.exceptions;

public class SessionException extends WebRedirectException{

    //Sends users to the login page if the session expires
    public SessionException(String message){
        super(message);
        this.redirectUrl = "/login";
    }

}

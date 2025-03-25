package com.example.demo.exceptions;

public class WebRedirectException extends Exception{
    protected String redirectUrl;

    //Parent class of all exception classes
    public WebRedirectException(String errorMessage){
        super(errorMessage);
        this.redirectUrl = "/forbidden";
    }

    public String getRedirectUrl(){
        return redirectUrl;
    }
}

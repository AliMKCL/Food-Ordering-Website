package com.example.demo.exceptions;


import javax.servlet.http.HttpServletResponse;


public class ForbiddenException extends WebRedirectException {

    //Sends users to the "access denied" page if they commit a forbidden act
    public ForbiddenException(String errorMessage){
        super(errorMessage);
        this.redirectUrl = "/forbidden";
    }
}

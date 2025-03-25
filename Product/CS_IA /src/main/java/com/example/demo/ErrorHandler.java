package com.example.demo;

import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.exceptions.SessionException;
import com.example.demo.exceptions.WebRedirectException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(WebRedirectException.class)
    public void ForbiddenRedirect(WebRedirectException re, HttpServletResponse res) throws IOException {
        res.sendRedirect(re.getRedirectUrl());
    }

}

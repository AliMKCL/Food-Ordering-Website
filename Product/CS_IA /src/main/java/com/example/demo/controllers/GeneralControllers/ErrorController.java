package com.example.demo.controllers.GeneralControllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    // Redirects users who attempt to gain forbidden access to a page to a seperate "access denied" page
    @GetMapping("/forbidden")
    public String ForbiddenPage(HttpSession session)  {
        return "error/forbidden";
    }

    //Redirects users who attempt to go to a restricted page to the login page
    @GetMapping("/loginRedirect")
    public String ForbiddenToRoleRedirect(HttpSession session){
        session.invalidate();
        return "login";
    }
}



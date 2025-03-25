package com.example.demo.service.aspects;

import com.example.demo.exceptions.InvalidRoleException;
import com.example.demo.exceptions.SessionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class SessionAspect
{
    //Checks if session is valid
    @Before("execution (* com.example.demo.controllers.ServiceControllers.*.*()) || execution(* com.example.demo.controllers.ServiceControllers.*.*(..)))")
    public void checkSessionValidity() throws SessionException, InvalidRoleException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        //Session won't be valid if the user isn't logged in or the 30 minute timer has been finished
        checkSessionUserValidity(session);
        checkSessionTime(session);
    }

    //Checks if the user's role is Admin before transferring the user to an admin page
    @Before("execution (* com.example.demo.controllers.ServiceControllers.AdminController.*()) || execution(* com.example.demo.controllers.ServiceControllers.AdminController.*(..)))")
    public void checkForAdminRole() throws InvalidRoleException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        String role = (String) session.getAttribute("userrole");

        if (role == null || role.equals("Tenant")){
            throw new InvalidRoleException("Admin Only Zone");
        }

    }
    //Checks if the user's role is Tenant before transferring the user to a customer page
    @Before("execution (* com.example.demo.controllers.ServiceControllers.TenantController.*()) || execution(* com.example.demo.controllers.ServiceControllers.TenantController.*(..)))")
    public void checkForTenantRole() throws InvalidRoleException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        String role = (String) session.getAttribute("userrole");

        if (role == null || role.equals("Admin")){
            throw new InvalidRoleException("Tenant Only Zone");
        }

    }

    //Stops session if 30 minutes are spent in it
    public void checkSessionTime(HttpSession session) throws SessionException {
        Long currentTime = System.currentTimeMillis();
        Long sessionStartTime = (Long)session.getAttribute("time");
        Long sessionCreationTime = session.getCreationTime();

        if (sessionStartTime != null) {
            if (currentTime - sessionCreationTime > 3000000) { // 30 minutes
                session.invalidate();
                throw new SessionException("Your session has ended!");
            }
        }
    }

    //Stops session if there is no user in it
    public void checkSessionUserValidity(HttpSession session) throws InvalidRoleException {
        String user = (String) session.getAttribute("user");

        if (user == null){
            session.invalidate();
            throw new InvalidRoleException("You are not logged in!");
        }
    }



}

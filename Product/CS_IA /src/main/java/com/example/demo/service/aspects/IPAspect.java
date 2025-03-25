package com.example.demo.service.aspects;

import com.example.demo.dao.IPAddress;
import com.example.demo.exceptions.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Aspect
@Component
public class IPAspect {
    //Determines a possible range for the ip addresses which can be used. The last digit can go from 0 to 255.
    private final List<String> hotelIpRanges = Arrays.asList
            ("0:0:0:0:0:0:0:1", // localhost
                    "10.223.10.0/24"); //A random ip assigned for the hotel

    //Code which makes sure before entering the website the ip address matches the hotel's.
    @Before("execution (* com.example.demo.controllers.ServiceControllers.*.*()) || execution(* com.example.demo.ServiceControllers.*.*(..)))")
    private void CheckIPAddress() throws ForbiddenException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = request.getRemoteAddr();

        boolean isMatched = false;

        for (String ip: hotelIpRanges){
            IpAddressMatcher matcher = new IpAddressMatcher(ip);

            if (matcher.matches(ipAddress)){
                isMatched = true;
            }
        }

        if (!isMatched){
            throw new ForbiddenException("IP Denied");
        }
    }
}




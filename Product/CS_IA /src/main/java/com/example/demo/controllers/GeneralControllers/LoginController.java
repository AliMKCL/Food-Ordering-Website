package com.example.demo.controllers.GeneralControllers;

import com.example.demo.entities.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String LoginScreen(HttpSession session)
    {
        return "firstpage";
    }

    //Determines the role of the user before letting them in to their dedicated pages
    @PostMapping("/login")
    public String TrySignIn(@RequestParam String pw,
                            @RequestParam String name,
                            Model model,
                            HttpSession session) throws NoSuchAlgorithmException {

        User user = userService.CheckUserWithCredentials(name,pw);

        if (user == null){
            return LoginScreen(session);
        }


        if (user.getRole().equals("Tenant")){
            session.setAttribute("user",name);
            session.setAttribute("userrole","Tenant");
            session.setAttribute("time", System.currentTimeMillis());
            return "redirect:/menu?roomId=" + name;
        }
        else if(user.getRole().equals("Admin")){
            session.setAttribute("user",name);
            session.setAttribute("userrole","Admin");
            session.setAttribute("time", System.currentTimeMillis());
            return "AdminFirstPage";
        }

        return "Menu";
    }

    //Same thing with the previous code block but can be configured to a qr code to be redirected to a specific page
    @GetMapping("qrlogin")
    public String QRLogin(@RequestParam String name, String pw, HttpSession session) throws NoSuchAlgorithmException {

        User user = userService.CheckUserWithCredentials(name,pw);
        if (user.getRole().equals("Tenant")){
            session.setAttribute("user",name);
            session.setAttribute("userrole","Tenant");
            session.setAttribute("time", System.currentTimeMillis());
            return "redirect:/menu?roomId=" + name;
        }
        else if(user.getRole().equals("Admin")){
            session.setAttribute("user",name);
            session.setAttribute("userrole","Admin");
            session.setAttribute("time", System.currentTimeMillis());
            return "AdminFirstPage";
        }
        else {
            return "login";
        }
    }

}

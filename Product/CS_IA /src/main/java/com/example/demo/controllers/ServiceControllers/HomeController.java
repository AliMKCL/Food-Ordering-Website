package com.example.demo.controllers.ServiceControllers;
import com.example.demo.dao.*;
import com.example.demo.entities.*;
import com.example.demo.entities.CartItem;
import com.example.demo.service.SortService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.CartItemHelper;
import com.example.demo.utilities.OrderDictionary;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController {





    public HomeController() throws NoSuchAlgorithmException {



    }







}

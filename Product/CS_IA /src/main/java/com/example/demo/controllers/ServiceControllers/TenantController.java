package com.example.demo.controllers.ServiceControllers;


import com.example.demo.dao.DigestibleRepository;
import com.example.demo.dao.DrinkRepository;
import com.example.demo.dao.FoodRepository;
import com.example.demo.dao.OrderRepository;
import com.example.demo.entities.Digestible;
import com.example.demo.entities.Drink;
import com.example.demo.entities.Food;
import com.example.demo.entities.Order;
import com.example.demo.utilities.CartItemHelper;
import com.example.demo.utilities.OrderDictionary;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TenantController {

    private DecimalFormat  df = new DecimalFormat("0.00");

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DigestibleRepository digestibleRepository;

    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    CartItemHelper helper;


    //Determines how many food cards will be in one row in the menu page
    @GetMapping("/menu")
    public String Menu(@RequestParam String roomId, HttpSession session, Model model)
    {
        model.addAttribute("roomId",roomId);

        //List<Digestible> digestibles =  digestibleRepository.findAll();
        List<Drink> drinksList = drinkRepository.findAll();
        List<Food> foodsList = foodRepository.findAll();

        List<List<Digestible>> drinks = new ArrayList<>();
        List<List<Digestible>> foods = new ArrayList<>();

        for (int i = 0;i < drinksList.size();i++)
        {
            if (drinks.size() == 0 || i % 2 == 0){
                drinks.add(new ArrayList<>());
            }

            drinks.get(i/2).add(drinksList.get(i));
        }

        for (int i = 0;i < foodsList.size();i++)
        {
            if (foods.size() == 0 || i % 2 == 0){
                foods.add(new ArrayList<>());
            }

            foods.get(i/2).add(foodsList.get(i));
        }

        model.addAttribute("drinkRows",drinks);
        model.addAttribute("foodRows",foods);

        if (session.getAttribute("user").equals(roomId)){
            return "Menu";
    }
        else{
            return "firstPage";
        }
    }

    //Adds items to the cart
    @PostMapping("/addDigestible")
    public String AddDigestibleToCart(@RequestParam int count,
                                      @RequestParam int foodId,
                                      @RequestParam int roomId, Model model){


        OrderDictionary dict = new OrderDictionary();
        Digestible digestible =  digestibleRepository.getReferenceById(foodId);
        dict.AddDigestibleRequest(roomId, digestible, count);

        return "redirect:/menu?roomId="+roomId;
    }

    //Cart page visible for customers
    @PostMapping("/cart")
    public String Cart(@RequestParam int roomId, Model model)
    {
        OrderDictionary dictionary = new OrderDictionary();

        Order order = dictionary.GetRoomOrder(roomId);

        if (order == null){
            model.addAttribute("message","Cart Is Empty");
        }
        else{
            model.addAttribute("Requests", helper.CreateCartItemList(order.getOrders()));
        }

        model.addAttribute("roomId",roomId);
        model.addAttribute("totalCost",df.format(order.getTotalCost()));

        return "Cart";
    }

    @PostMapping("/cart/remove")
    public String RemoveFromCart(int roomId, String digestibleName, Model model){
        OrderDictionary dictionary = new OrderDictionary();
        Order order = dictionary.GetRoomOrder(roomId);
        order.RemoveDigestibleRequest(digestibleName);

        return Cart(roomId, model);
    }


    //Submits products in the cart as an order to the database
    @PostMapping("/submitCart")
    public String SubmitCartToDB(@RequestParam int roomId, @RequestParam String note){

        OrderDictionary dictionary = new OrderDictionary();
        Order order = dictionary.GetRoomOrder(roomId);
        order.setNote(note);
        for(int i = 0; i < order.getOrders().size(); i++){
            order.getOrders().get(i).setOrder(order);
        }

        orderRepository.save(order);
        dictionary.RemoveOrder(roomId);

        return "redirect:/menu?roomId="+roomId;
    }

}

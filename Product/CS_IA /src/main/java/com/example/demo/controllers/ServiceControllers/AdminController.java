package com.example.demo.controllers.ServiceControllers;

import com.example.demo.dao.DigestibleRepository;
import com.example.demo.dao.DrinkRepository;
import com.example.demo.dao.FoodRepository;
import com.example.demo.dao.OrderRepository;
import com.example.demo.entities.*;
import com.example.demo.service.SortService;
import com.example.demo.utilities.CartItemHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartItemHelper helper;

    @Autowired
    DigestibleRepository digestibleRepository;

    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    FoodRepository foodRepository;

    //Code which enables admins to see a list of pending orders
    @GetMapping("admin/orders")
    public String OrdersGet(Model model){

        List<Order> Orders = orderRepository.GetUnfinishedOrders();
        model.addAttribute("orders", Orders);

        return "orders";
    }
//Code which enables admins to see the details of an order
    @PostMapping("/orders/details")
    public String OrderDetails(@RequestParam int orderid, Model model){

        Order order = orderRepository.findById(orderid).get();

        List<CartItem> items = helper.CreateCartItemList(order.getOrders());

        model.addAttribute("foods",items);
        model.addAttribute("order",order);

        return "orderdetails";
    }

    //Changes the situation of the order from not delivered to delivered in the database
    @PostMapping("/orders/details/submit")
    public String RedirectToOrders(@RequestParam int orderid, Model model){

        Order order = orderRepository.findById(orderid).get();
        order.setDelivered(true);
        orderRepository.save(order);
        return OrdersGet(model);
    }
    //Puts all foods in the database in a list
    @GetMapping("admin/digestible/list")
    public String ListFood(Model model){

        List<Digestible> digestibles = digestibleRepository.findAll();

        digestibles.sort(Comparator.comparing(Digestible::getId));

        model.addAttribute("foods", digestibles);

        return "foodlist";
    }
//Decides whether a given digestible is a food or a drink
    @PostMapping("admin/digestible/edit")
    public String EditFood(@RequestParam int id, Model model){

        String category = "Drink";
        List<Drink> drink = drinkRepository.findAllById(Collections.singleton(id));
        Drink d = null;
        Food food = null;
        if (drink.size() == 0){
            category = "Food";
            food = foodRepository.getReferenceById(id);
        }
        else{
            d = drink.get(0);
        }

        if (category.equals("Food")){
            model.addAttribute("food",food);
        }
        else if (category.equals("Drink")){
            model.addAttribute("food",d);
        }

        return "editfood";
    }

    //Code which enables admins to edit foods already existing in the database
    @PostMapping("admin/digestible/update")
    public String UpdateFood(int id, String name, String photoUrl, double price, Model model)
    {

        if (name == null || name.equals("")){
            return EditFood(id,model);
        }

        if (photoUrl == null || photoUrl.equals("")){
            return EditFood(id,model);
        }

        if (price < 0){
            return EditFood(id,model);
        }

        Digestible digestible = digestibleRepository.getReferenceById(id);

        digestible.setName(name);
        digestible.setPhotoURL(photoUrl);
        digestible.setPrice(price);

        digestibleRepository.save(digestible);

        return ListFood(model);
    }

    //Code which enables admins to add foods to the database
    @GetMapping("admin/digestible/add")
    public String AddDigestible(){
        return "addfood";
    }
    @PostMapping("admin/digestible/add")
    public String AddDigestiblePost(int type, String name, double price, String photoUrl, Model model){

        if (name == null || name.equals("")){
            return "addfood";
        }

        if (photoUrl == null || photoUrl.equals("")){
            return "addfood";
        }

        if (price < 1){
            return "addfood";
        }

        Digestible digestible = null;

        if (type == 1){
            digestible = new Food(name, photoUrl, price);
        } else if (type == 2) {
            digestible = new Drink(name,photoUrl,price);
        } else{
            return "addfood";
        }

        digestibleRepository.save(digestible);

        return ListFood(model);
    }

    @GetMapping("admin/menu")
    public String AdminMenu() {

        return "adminFirstPage";
    }

    //Adds the cost of all purchases made in a single day and sorts the total values in descending order from top to bottom
    @GetMapping("admin/sales")
    public String SalesInformation(Model model){
        HashMap<String, Double> dateSales = new HashMap<>();
        List<Order> orders = orderRepository.findAll();

        for (Order order: orders){
            if (dateSales.get(order.getDate()) == null){
                dateSales.put(order.getDate(),0.0);
            }
            dateSales.put(order.getDate(), dateSales.get(order.getDate()) + order.getTotalCost());
        }

        dateSales = (HashMap<String, Double>) SortService.sortByValueDescending(dateSales);

        model.addAttribute("sales",dateSales);

        return "sales";
    }
}



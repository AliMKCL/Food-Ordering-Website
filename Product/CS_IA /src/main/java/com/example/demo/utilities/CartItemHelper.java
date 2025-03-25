package com.example.demo.utilities;

import com.example.demo.dao.DigestibleRepository;
import com.example.demo.entities.Digestible;
import com.example.demo.entities.DigestibleRequest;
import com.example.demo.entities.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemHelper {

    @Autowired
    private DigestibleRepository repository;

    public CartItem CreateCartItem(DigestibleRequest request){

        Digestible digestible = repository.getById(request.getDigestible().getId());

        CartItem item = new CartItem(digestible.getName(),request.getCount());

        return item;
    }

    public List<CartItem> CreateCartItemList(List<DigestibleRequest> digestibleRequests){

        List<CartItem> items = new ArrayList<>();

        for (int i = 0; i < digestibleRequests.size(); i++){
            items.add(
                    CreateCartItem(digestibleRequests.get(i)));
        }

        return items;
    }

}

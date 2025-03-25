package com.example.demo.utilities;

import com.example.demo.entities.Digestible;
import com.example.demo.entities.DigestibleRequest;
import com.example.demo.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class OrderDictionary {


    private static HashMap<Integer, Order> RoomOrders;

    public OrderDictionary(){
        if (RoomOrders == null){
            RoomOrders = new HashMap<>();
        }
    }
    //Matches rooms with orders
    public void AddDigestibleRequest(int roomId, Digestible digestible, int count){

        Order order = RoomOrders.get(roomId);

        if (order == null){
            Order o = new Order();
            o.setRoomName(roomId+"");

            o.AddDigestibleRequest(new DigestibleRequest(digestible,count, o));
            RoomOrders.put(roomId, o);
        }
        else{
            boolean hasOrder = false;
            for(int i = 0; i < order.getOrders().size(); i++){
                if (order.getOrders().get(i).getDigestible().getId() == digestible.getId()){
                    int newcount = order.getOrders().get(i).getCount() + count;
                    order.getOrders().get(i).setCount(newcount);
                    hasOrder = true;
                }
            }
            if (!hasOrder){
                order.AddDigestibleRequest(new DigestibleRequest(digestible,count));
            }
        }
    }

    public Order GetRoomOrder(int roomId){
        return RoomOrders.get(roomId);
    }

    public void RemoveOrder(int roomId){
        RoomOrders.remove(roomId);
    }



}

package com.example.demo.dao;

import com.example.demo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(nativeQuery = true, value = "select * from Orders where is_delivered = false")
    public List<Order> GetUnfinishedOrders();

}

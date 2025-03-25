package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class Drink extends Digestible{

    public Drink(){

    }

    public Drink(String name, String photoUrl, double price ) {
        super(name,photoUrl, price);
    }

}

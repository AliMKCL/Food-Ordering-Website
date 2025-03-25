package com.example.demo.entities;

import jakarta.persistence.Entity;
import jdk.jfr.Enabled;

import java.util.List;

@Entity
public class Food extends Digestible{


    public Food(){

    }

    public Food(String name, String photoURL, double price) {
        super(name, photoURL, price);
    }


}

package com.example.demo.entities;


import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//Creates a table named Digestibles in the database
@Table(name = "Digestibles")
public class Digestible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "photo_url")
    private String photoURL;

    @Column(name = "price")
    private double price;


    public Digestible(){

    }
    public Digestible(String name, String photoURL, double price) {
        this.name = name;
        this.photoURL = photoURL;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public String toString() {
        return "Digestible{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}

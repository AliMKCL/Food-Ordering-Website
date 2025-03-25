package com.example.demo.entities;

import jakarta.persistence.*;

//Creates a table named Users in the database
@Table(name = "Users")
@Entity
public class User {

    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Increments ID one by one for each row
    @Column(name = "id")
    private int id;
    @Column(name = "roomNumber")
    private String roomNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

    public User(){

    }

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.roomNumber = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, String role) {
        roomNumber = username;
        this.password=password;
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setUsername(String username) {
        this.roomNumber = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}




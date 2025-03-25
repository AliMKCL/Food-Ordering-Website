package com.example.demo.entities;

import jakarta.persistence.*;
import org.springframework.data.repository.cdi.Eager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
//Creates a table named Orders in the database
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String roomName;
    private String note;
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
    private List<DigestibleRequest> digestibleRequests;
    private boolean isDelivered;
    private String date;
    @Column(name = "total_cost")
    private double totalCost;


    public Order(){
        digestibleRequests = new ArrayList<>();
        date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now());
        totalCost = 0;
    }

    public Order(String roomName, String note){
        this.roomName = roomName;
        this.note = note;
        digestibleRequests = new ArrayList<>();
        date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now());
        totalCost = 0;
    }

    public void AddDigestibleRequest(DigestibleRequest request){
        digestibleRequests.add(request);
        totalCost += request.getCount() * request.getDigestible().getPrice();
    }

    public void RemoveDigestibleRequest(String digestibleName){
        int index = -1;

        for (int i = 0; i<digestibleRequests.size();i++){
            if (digestibleRequests.get(i).getDigestible().getName().equals(digestibleName)){
                index = i;
                break;
            }
        }

        if (index != -1){
            totalCost -= digestibleRequests.get(index).getDigestible().getPrice() * digestibleRequests.get(index).getCount();
            digestibleRequests.remove(index);
        }
    }

    public int getId(){
        return id;
    }
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<DigestibleRequest> getOrders() {
        return digestibleRequests.stream().collect(Collectors.toList());
    }

    public void setOrders(List<DigestibleRequest> orders) {
        this.digestibleRequests = new ArrayList<>(orders);
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getDate() {
        return date;
    }


}

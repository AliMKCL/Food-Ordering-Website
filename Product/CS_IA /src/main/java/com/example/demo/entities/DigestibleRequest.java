package com.example.demo.entities;

import jakarta.persistence.*;
import org.aspectj.weaver.ast.Or;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Digest;

import java.util.List;

@Entity
//Creates a table named Digestible_Requests in the database
@Table(name = "Digestible_Requests")
public class DigestibleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "digestible_id")
    private Digestible digestible;
    private int count;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public DigestibleRequest(){

    }

    public DigestibleRequest(Digestible digestible, int count) {
        this.digestible = digestible;
        this.count = count;
    }

    public DigestibleRequest(Digestible digestible, int count, Order order) {
        this.digestible = digestible;
        this.count = count;
        this.order = order;
    }

    public Digestible getDigestible() {
        return digestible;
    }

    public void setDigestible(Digestible digestible) {
        this.digestible = digestible;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Order getOrder(){
        return order;
    }

    public void setOrder(Order order){
        this.order = order;
    }

}

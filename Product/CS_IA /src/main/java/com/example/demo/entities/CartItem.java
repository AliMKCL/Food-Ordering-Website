package com.example.demo.entities;

public class CartItem {

    private String digestibleName;
    private int count;

    public CartItem(String digestibleName, int count) {
        this.digestibleName = digestibleName;
        this.count = count;
    }

    public String getDigestibleName() {
        return digestibleName;
    }

    public void setDigestibleName(String digestibleName) {
        this.digestibleName = digestibleName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

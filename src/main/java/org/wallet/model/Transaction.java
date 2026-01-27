package org.wallet.model;

import java.sql.Timestamp;

public class Transaction {

    private int id;
    private int userId;
    private double amount;
    private String type;
    private Timestamp createdAt;

    public Transaction() {}

    //Transaction Operation 
    public Transaction(int id, int userId, double amount,
                       String type, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}

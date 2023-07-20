package com.app.tradeServer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserFunds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    double amount;
    double holdAmount;

    public double getAmount(){
        return this.amount;
    }
    public void setAmount(double amount){
        this.amount=amount;
    }
}

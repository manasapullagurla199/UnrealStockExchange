package com.app.tradeServer.model;

import jakarta.persistence.*;

@Entity
public class UserFunds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long FundsId;
    @OneToOne
    @JoinColumn(name="userId")
    private User user;
    double amount;
    double holdAmount;

    public double getAmount(){
        return this.amount;
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public void setAmount(double amount){
        this.amount=amount;
    }
}

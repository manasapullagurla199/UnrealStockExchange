package com.app.tradeServer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFunds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy="funds")
    private User user;
    double amount;
    double holdAmount;

    public double getAmount(){
        return this.amount;
    }


    public void setAmount(double amount){
        this.amount=amount;
    }
}

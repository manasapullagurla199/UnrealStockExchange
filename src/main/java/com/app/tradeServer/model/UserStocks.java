package com.app.tradeServer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserStocks{
    @Id
    Long stockId;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
//    Long userId;
    double quantity;
    double buyPrice;
    double holdQuantity;
//    public Long getUserId(){
//        return this.userId;
//    }
    public Long getUserId(){
        return user.getUserId();
    }
    public Long getStockId(){
        return this.stockId;
    }
    public double getQuantity(){
        return this.quantity;
    }

}

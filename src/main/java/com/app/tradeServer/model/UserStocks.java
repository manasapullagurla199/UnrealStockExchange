package com.app.tradeServer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserStocks{
    @Id
    Long stockId;
    Long userId;
    double quantity;
    double buyPrice;
    double holdQuantity;
    public Long getUserId(){
        return this.userId;
    }
    public Long getStockId(){
        return this.stockId;
    }
    public double getQuantity(){
        return this.quantity;
    }

}

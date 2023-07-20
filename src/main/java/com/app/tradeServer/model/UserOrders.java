package com.app.tradeServer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserOrders{
    @Id
    Long orderId;
    Long userId;
    Long stockId;
    double quantity;
    boolean status;
    String orderType;
    public String getOrderType(){
        return this.orderType;
    }
    public double getQuantity(){
        return this.quantity;
    }
    public Long getStockId(){
        return this.stockId;
    }
    public Long getOrderId(){
        return this.orderId;
    }
    public Long getUserId(){
        return this.userId;
    }
    public boolean getStatus(){
        return this.status;
    }
}

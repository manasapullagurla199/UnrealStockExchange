package com.app.tradeServer.model;

import jakarta.persistence.*;

@Entity
public class UserOrders{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
//    Long userId;
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
//    public Long getUserId(){
//        return this.userId;
//    }
    public Long getUserId(){
        return user.getUserId();
    }
    public boolean getStatus(){
        return this.status;
    }
}

package com.app.tradeServer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class UserStocks{
    @Id
    Long stockId;

    String stockSymbol;

    @ManyToOne
    private User user;
    double quantity;
    double buyPrice;
    double holdQuantity;

}

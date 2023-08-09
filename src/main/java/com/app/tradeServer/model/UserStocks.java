package com.app.tradeServer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStocks{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long stockId;

    String stockSymbol;

    @ManyToOne
    private User user;
    double quantity;
    double buyPrice;
    double holdQuantity;

}

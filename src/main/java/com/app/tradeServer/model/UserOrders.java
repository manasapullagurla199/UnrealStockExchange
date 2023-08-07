package com.app.tradeServer.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class UserOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    Long stockId;
    String stockSymbol;
    double quantity;
    boolean status;
    String orderType;
}

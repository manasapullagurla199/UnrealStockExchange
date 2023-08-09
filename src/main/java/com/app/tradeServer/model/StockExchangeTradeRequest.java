package com.app.tradeServer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockExchangeTradeRequest {
    Long orderId;
    Long stockId;
    String stockSymbol;
    double quantity;
    String orderType;
}

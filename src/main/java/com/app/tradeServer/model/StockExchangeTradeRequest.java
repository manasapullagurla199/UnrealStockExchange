package com.app.tradeServer.model;


import lombok.Data;


@Data
public class StockExchangeTradeRequest {
    Long stockId;
    double quantity;
    //buy price optional
    public StockExchangeTradeRequest(Long orderId, Long stockId, String stockSymbol, double quantity){
        this.stockId=stockId;
        this.quantity=quantity;
    }
}

package com.app.tradeServer.model;

public class StockExchangeTradeRequest {
    Long stockId;
    double quantity;
    //buy price optional
    public StockExchangeTradeRequest(Long stockId,double quantity){
        this.stockId=stockId;
        this.quantity=quantity;
    }

    public Long getStockId() {
        return stockId;
    }
}

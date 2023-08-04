package com.app.tradeServer.model;

public class StockExchangeTradeResponse {
    Long stockId;
    Long orderId;
    public StockExchangeTradeResponse(Long stockId){
        this.stockId=stockId;
    }

    public Long getStockId() {
        return stockId;
    }

    public Long getOrderId() {
        return orderId;
    }
}

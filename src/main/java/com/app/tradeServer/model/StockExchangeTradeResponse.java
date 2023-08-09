package com.app.tradeServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockExchangeTradeResponse {
    Long orderId;
    boolean status;
}

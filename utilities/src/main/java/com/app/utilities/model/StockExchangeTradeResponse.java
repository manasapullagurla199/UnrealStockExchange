package com.app.utilities.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockExchangeTradeResponse {
    Long orderId;
    boolean status;
    double price;
    double quantity;
}

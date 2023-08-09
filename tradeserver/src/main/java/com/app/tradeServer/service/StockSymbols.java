package com.app.tradeServer.service;

import org.springframework.stereotype.Service;

@Service
public class StockSymbols {
    // Method to get stock symbols based on the given stock ID
    public String getStockSymbols(Long id) {
        // Return the stock symbol based on the provided stock ID
        if (id == 0) {
            return "TSLA";
        } else if (id == 1) {
            return "AAPL";
        } else {
            return "GOOGL";
        }
    }
}

package com.app.tradeServer.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StockPriceService {
    // HashMap to store stock prices with stock symbols as keys
    HashMap<String, Double> prices = new HashMap<>();

    // Method to get the stock price based on the given stock symbol
    public Double getStockPrice(String stockSymbol) {

        return 1.0;
    }
}

package com.app.tradeServer.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StockPriceService {
    HashMap<String, Double> prices = new HashMap<>();

    public Double getStockPrice(String stockSymbol){
        return 1.0;
    }

}

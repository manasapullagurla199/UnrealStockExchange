package com.app.stockexchange.utils;

import com.app.utilities.model.StockExchangeTradeRequest;
import com.app.utilities.model.StockExchangeTradeResponse;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;

import java.util.PriorityQueue;

public class TradeMatchingProcessFunction implements Processor<String, StockExchangeTradeRequest, String, StockExchangeTradeResponse> {

    private final PriorityQueue<StockExchangeTradeRequest> buyOrders = new PriorityQueue<>((a, b) -> Double.compare(b.getTargetPrice(), a.getTargetPrice()));
    private final PriorityQueue<StockExchangeTradeRequest> sellOrders = new PriorityQueue<>((a, b) -> Double.compare(a.getTargetPrice(), b.getTargetPrice()));

    private ProcessorContext<String, StockExchangeTradeResponse> context;

    private void matchTrades() {
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            StockExchangeTradeRequest buyTrade = buyOrders.peek();
            StockExchangeTradeRequest sellTrade = sellOrders.peek();

            if (buyTrade.getTargetPrice() >= sellTrade.getTargetPrice()) {
                double matchedPrice = (buyTrade.getTargetPrice() + sellTrade.getTargetPrice()) / 2;
                double matchedQuantity = Math.min(buyTrade.getQuantity(), sellTrade.getQuantity());

                StockExchangeTradeResponse matchedBuyTrade = new StockExchangeTradeResponse(buyTrade.getOrderId(), true, matchedPrice, matchedQuantity);
                StockExchangeTradeResponse matchedSellTrade = new StockExchangeTradeResponse(sellTrade.getOrderId(), true, matchedPrice, matchedQuantity);

                this.context.forward(new Record<>(matchedBuyTrade.getOrderId().toString(), matchedBuyTrade, 0));
                this.context.forward(new Record<>(matchedSellTrade.getOrderId().toString(), matchedSellTrade, 0));

                buyTrade.setQuantity(buyTrade.getQuantity() - matchedQuantity);
                sellTrade.setQuantity(sellTrade.getQuantity() - matchedQuantity);

                if (buyTrade.getQuantity() == 0) {
                    buyOrders.poll();
                }

                if (sellTrade.getQuantity() == 0) {
                    sellOrders.poll();
                }
            } else {
                break; // No more matches possible
            }
        }
    }

    @Override
    public void init(ProcessorContext<String, StockExchangeTradeResponse> context) {
        Processor.super.init(context);
        this.context = context;
    }

    @Override
    public void process(Record<String, StockExchangeTradeRequest> record) {
        StockExchangeTradeRequest trade = record.value();
        if (trade.getOrderType().equals("buy")) {
            buyOrders.add(trade);
        } else {
            sellOrders.add(trade);
        }
        matchTrades();
    }

    @Override
    public void close() {
        Processor.super.close();
    }
}

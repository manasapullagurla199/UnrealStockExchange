package com.app.stockexchange;

import com.app.utilities.model.StockExchangeTradeRequest;
import com.app.utilities.model.StockExchangeTradeResponse;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.util.PriorityQueue;

public class TradeMatchingProcessFunction extends ProcessFunction<StockExchangeTradeRequest, StockExchangeTradeResponse> {

    private PriorityQueue<StockExchangeTradeRequest> buyOrders = new PriorityQueue<>((a, b) -> Double.compare(b.getTargetPrice(), a.getTargetPrice()));
    private PriorityQueue<StockExchangeTradeRequest> sellOrders = new PriorityQueue<>((a, b) -> Double.compare(a.getTargetPrice(), b.getTargetPrice()));

    @Override
    public void processElement(StockExchangeTradeRequest trade, Context context, Collector<StockExchangeTradeResponse> out) {
        if (trade.getOrderType().equals("buy")) {
            buyOrders.add(trade);
        } else {
            sellOrders.add(trade);
        }

        matchTrades(out);
    }

    private void matchTrades(Collector<StockExchangeTradeResponse> out) {
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            StockExchangeTradeRequest buyTrade = buyOrders.peek();
            StockExchangeTradeRequest sellTrade = sellOrders.peek();

            if (buyTrade.getTargetPrice() >= sellTrade.getTargetPrice()) {
                double matchedPrice = (buyTrade.getTargetPrice() + sellTrade.getTargetPrice()) / 2;
                double matchedQuantity = Math.min(buyTrade.getQuantity(), sellTrade.getQuantity());

                StockExchangeTradeResponse matchedBuyTrade = new StockExchangeTradeResponse(buyTrade.getOrderId(), true, matchedPrice, matchedQuantity);
                StockExchangeTradeResponse matchedSellTrade = new StockExchangeTradeResponse(sellTrade.getOrderId(), true, matchedPrice, matchedQuantity);

                out.collect(matchedBuyTrade);
                out.collect(matchedSellTrade);

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
}

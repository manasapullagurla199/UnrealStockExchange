package com.app.tradeServer.service;

import com.app.tradeServer.repository.UserFundsRepository;
import com.app.tradeServer.repository.UserOrdersRepository;
import com.app.tradeServer.model.*;
import com.app.tradeServer.repository.UserStocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOrdersService {

    @Autowired
    private UserOrdersRepository userOrdersRepository;

    @Autowired
    private UserFundsRepository userFundsRepository;

    @Autowired
    private OrderProducerKafka orderProducerKafka;

    @Autowired
    private StockPriceService stockPriceService;

    // Method to place a buy order
    public void placeBuyOrder(UserOrders userOrder) {
        // Retrieve user funds based on user ID
        UserFunds userFunds = userFundsRepository.findByUserId(userOrder.getUser().getId());
        double userAvailableAmount = userFunds.getAmount();

        // Calculate required amount for the buy order
        double userRequiredAmount = stockPriceService.getStockPrice(userOrder.getStockSymbol()) * userOrder.getQuantity();

        // Check if user has sufficient funds
        if (userRequiredAmount <= userAvailableAmount) {
            userAvailableAmount -= userRequiredAmount;
            userFunds.setAmount(userAvailableAmount);
            userFunds.setHoldAmount(userRequiredAmount);

            // Set buy price for the order
            userOrder.setBuyPrice(stockPriceService.getStockPrice(userOrder.getStockSymbol()));

            // Save user order and update user funds
            userOrdersRepository.save(userOrder);
            userFundsRepository.save(userFunds);

            // Create and send a trade request to Kafka
            StockExchangeTradeRequest stockExchangeTradeRequest = new StockExchangeTradeRequest(
                    userOrder.getOrderId(), userOrder.getStockId(), userOrder.getStockSymbol(),
                    userOrder.getQuantity(), userOrder.getOrderType());
            orderProducerKafka.send(stockExchangeTradeRequest);
        }
    }

    // Method to place a sell order
    public void placeSellOrder(UserOrders userOrder) {
        List<UserStocks> userStocks = UserStocksService.getUserStocksByUserId(userOrder.getUser().getId());

        // Loop through user stocks to find matching stock for the sell order
        for (UserStocks stock : userStocks) {
            if (stock.getStockId().equals(userOrder.getStockId())) {
                if (stock.getQuantity() >= userOrder.getQuantity()) {
                    stock.setQuantity(stock.getQuantity() - userOrder.getQuantity());
                    stock.setHoldQuantity(userOrder.getQuantity());
                    userOrdersRepository.save(userOrder);

                    // Create and send a trade request to Kafka
                    orderProducerKafka.send(new StockExchangeTradeRequest(
                            userOrder.getOrderId(), userOrder.getStockId(), userOrder.getStockSymbol(),
                            userOrder.getQuantity(), userOrder.getOrderType()));
                }
            }
        }
    }
}

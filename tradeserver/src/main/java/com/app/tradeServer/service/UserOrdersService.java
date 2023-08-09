package com.app.tradeServer.service;

import com.app.tradeServer.model.UserFunds;
import com.app.tradeServer.model.UserOrders;
import com.app.tradeServer.model.UserStocks;
import com.app.tradeServer.repository.UserFundsRepository;
import com.app.tradeServer.repository.UserOrdersRepository;
import com.app.tradeServer.repository.UserStocksRepository;
import com.app.utilities.model.StockExchangeTradeRequest;
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

    @Autowired
    private UserStocksRepository userStocksRepository;

    // Method to place a buy order
    public void placeBuyOrder(UserOrders userOrder) {
        // Retrieve user funds based on user ID
        UserFunds userFunds = userFundsRepository.findByUserId(userOrder.getUser().getId());
        double userAvailableAmount = userFunds.getAmount();

        // Calculate required amount for the buy order
        double userRequiredAmount = userOrder.getTargetPrice() * userOrder.getQuantity();

        // Check if user has sufficient funds
        if (userRequiredAmount <= userAvailableAmount) {
            userAvailableAmount -= userRequiredAmount;
            userFunds.setAmount(userAvailableAmount);
            userFunds.setHoldAmount(userRequiredAmount);

            // Save user order and update user funds
            userOrdersRepository.save(userOrder);
            userFundsRepository.save(userFunds);

            // Create and send a trade request to Kafka
            StockExchangeTradeRequest stockExchangeTradeRequest = new StockExchangeTradeRequest(
                    userOrder.getOrderId(), userOrder.getStockId(), userOrder.getStockSymbol(),
                    userOrder.getQuantity(), userOrder.getOrderType(), userOrder.getTargetPrice());
            orderProducerKafka.send(stockExchangeTradeRequest);
        }
    }

    // Method to place a sell order
    public void placeSellOrder(UserOrders userOrder) {
        List<UserStocks> userStocks = UserStocksService.getUserStocksByUserId(userOrder.getUser().getId());

        double quantityToSell = userOrder.getQuantity();
        // Loop through user stocks to find matching stock for the sell order
        for (UserStocks stock : userStocks) {
            if (stock.getStockId().equals(userOrder.getStockId())) {
                if (quantityToSell == 0) {
                    break;
                }
                if (stock.getQuantity() >= quantityToSell) {
                    stock.setQuantity(stock.getQuantity() - quantityToSell);
                    stock.setHoldQuantity(quantityToSell);
                    quantityToSell = 0;
                } else {
                    stock.setHoldQuantity(stock.getQuantity());
                    quantityToSell -= stock.getQuantity();
                    stock.setQuantity(0);
                }
                userStocksRepository.save(stock);
                // Create and send a trade request to Kafka
                orderProducerKafka.send(new StockExchangeTradeRequest(
                        userOrder.getOrderId(), userOrder.getStockId(), userOrder.getStockSymbol(),
                        userOrder.getQuantity(), userOrder.getOrderType(), userOrder.getTargetPrice()));
            }
        }
    }
}


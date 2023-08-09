package com.app.tradeServer.service;

import com.app.tradeServer.model.*;
import com.app.tradeServer.repository.UserFundsRepository;
import com.app.tradeServer.repository.UserOrdersRepository;
import com.app.tradeServer.repository.UserRepository;
import com.app.tradeServer.repository.UserStocksRepository;
import com.app.utilities.serdes.SETradeResponseSerde;
import com.app.utilities.model.StockExchangeTradeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class OrderConsumerKafka {

    // Serde (Serializer/Deserializer) for StockExchangeTradeResponse
    private final SETradeResponseSerde serde = new SETradeResponseSerde();

    // Kafka topic to listen to
    final String TOPIC = "se-trade-responses";

    @Autowired
    UserOrdersRepository userOrdersRepository;
    @Autowired
    UserFundsRepository userFundsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserStocksRepository userStocksRepository;

    // Kafka listener method to consume messages from the specified topic
    @KafkaListener(topicPattern = TOPIC)
    public void consume(byte[] data) {

        // Deserialize the incoming Kafka message into a StockExchangeTradeResponse object
        StockExchangeTradeResponse stockExchangeTradeResponse = serde.deserialize(TOPIC, data);
        log.info("Received response: {}", stockExchangeTradeResponse);

        // Retrieve the corresponding UserOrder based on the trade response
        UserOrders userOrder = userOrdersRepository.getOrderByOrderId(stockExchangeTradeResponse.getOrderId());

        // Get the associated User and UserFunds for the UserOrder
        User user = userOrder.getUser();
        UserFunds userFunds = user.getFunds();

        // Create a new UserStocks object (will be used for buy orders)
        UserStocks userStocks = new UserStocks();

        if (stockExchangeTradeResponse.isStatus()) {
            if (userOrder.getOrderType().equals("sell")) {
                // Handle sell order: Update UserStocks and UserFunds
                userStocks = userStocksRepository.getReferenceById(userOrder.getStockId());
                userStocks.setHoldQuantity(userStocks.getHoldQuantity() - userOrder.getQuantity());
                userStocks.setStockSymbol(userOrder.getStockSymbol());
                userFunds.setAmount(userFunds.getAmount() + (userStocks.getQuantity() * userOrder.getTargetPrice()));
            } else {
                // Handle buy order: Update UserStocks, UserFunds, and UserOrders
                userFunds.setHoldAmount(userFunds.getHoldAmount() - (userOrder.getQuantity() * userOrder.getTargetPrice()));
                userStocks.setQuantity(userOrder.getQuantity());
                userStocks.setBuyPrice(userOrder.getTargetPrice());
                userStocks.setUser(user);
            }

            // Update UserOrder status and save changes to the database
            userOrder.setStatus(true);
            userFundsRepository.save(userFunds);
            userStocksRepository.save(userStocks);
            userOrdersRepository.save(userOrder);
        }
    }
}

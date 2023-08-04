package com.app.tradeServer.service;

import com.app.tradeServer.model.StockExchangeTradeRequest;
import com.app.tradeServer.model.StockExchangeTradeResponse;
import com.app.tradeServer.model.UserOrders;
import com.app.tradeServer.repository.UserOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumerKafka {
    @Autowired
    UserOrdersRepository userOrdersRepository;

    public void OrderConsumerKafka(UserOrdersRepository userOrdersRepository){
        this.userOrdersRepository=userOrdersRepository;
    }

    @KafkaListener(topics = "se-trade-responses")
    public void consume(StockExchangeTradeResponse stockExchangeTradeResponse){
        UserOrders userOrder=userOrdersRepository.getOrderByOrderId(stockExchangeTradeResponse.getOrderId());

    }

}

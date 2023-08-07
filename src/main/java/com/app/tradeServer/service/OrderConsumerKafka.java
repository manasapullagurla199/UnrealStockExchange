package com.app.tradeServer.service;

import com.app.tradeServer.model.StockExchangeTradeRequest;
import com.app.tradeServer.model.StockExchangeTradeResponse;
import com.app.tradeServer.model.UserOrders;
import com.app.tradeServer.repository.UserOrdersRepository;
import com.app.tradeServer.serdes.SETradeResponseSerde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumerKafka {

    private final SETradeResponseSerde serde = new SETradeResponseSerde();

    final String TOPIC = "se-trade-responses";

    @Autowired
    UserOrdersRepository userOrdersRepository;

    @KafkaListener(topicPattern = TOPIC)
    public void consume(byte[] data){
        StockExchangeTradeResponse stockExchangeTradeResponse = serde.deserialize(TOPIC, data);
        UserOrders userOrder=userOrdersRepository.getOrderByOrderId(stockExchangeTradeResponse.getOrderId());

    }

}

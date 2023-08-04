package com.app.tradeServer.service;
import com.app.tradeServer.model.StockExchangeTradeRequest;
import com.app.tradeServer.model.StockExchangeTradeResponse;
import com.app.tradeServer.model.UserOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerKafka {

        private static final String TOPIC = "se-trade-requests";

        @Autowired
        private KafkaTemplate<Long, StockExchangeTradeRequest> kafkaTemplate;

        public void send(StockExchangeTradeRequest stockExchangeTradeRequest) {

            kafkaTemplate.send(TOPIC, stockExchangeTradeRequest.getStockId(),stockExchangeTradeRequest);
        }
}

package com.app.tradeServer.service;
import com.app.tradeServer.model.StockExchangeTradeRequest;
import com.app.tradeServer.model.StockExchangeTradeResponse;
import com.app.tradeServer.model.UserOrders;
import com.app.tradeServer.serdes.SETradeRequestSerde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerKafka {

        private final SETradeRequestSerde serde = new SETradeRequestSerde();
        private static final String TOPIC = "se-trade-requests";

        @Autowired
        private KafkaTemplate<String, byte[]> kafkaTemplate;

        public void send(StockExchangeTradeRequest stockExchangeTradeRequest) {
            byte[] request = serde.serialize(TOPIC, stockExchangeTradeRequest);
            kafkaTemplate.send(TOPIC, stockExchangeTradeRequest.getStockId().toString(),request);
        }
}

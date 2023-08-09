package com.app.tradeServer.service;

import com.app.utilities.model.StockExchangeTradeRequest;
import com.app.utilities.serdes.SETradeRequestSerde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerKafka {

    // Serde (Serializer/Deserializer) for StockExchangeTradeRequest
    private final SETradeRequestSerde serde = new SETradeRequestSerde();

    // Kafka topic to send messages to
    private static final String TOPIC = "se-trade-requests";

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    // Method to send a StockExchangeTradeRequest to the Kafka topic
    public void send(StockExchangeTradeRequest stockExchangeTradeRequest) {
        // Serialize the trade request into a byte array using the serde
        byte[] request = serde.serialize(TOPIC, stockExchangeTradeRequest);

        // Send the serialized request to the Kafka topic, using the stockId as the message key
        kafkaTemplate.send(TOPIC, stockExchangeTradeRequest.getStockId().toString(), request);
    }
}

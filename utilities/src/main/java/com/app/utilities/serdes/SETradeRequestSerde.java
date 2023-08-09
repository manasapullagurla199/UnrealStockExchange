package com.app.utilities.serdes;

import com.app.utilities.model.StockExchangeTradeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class SETradeRequestSerde implements Serde<StockExchangeTradeRequest>, Serializer<StockExchangeTradeRequest>, Deserializer<StockExchangeTradeRequest> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public StockExchangeTradeRequest deserialize(String topic, byte[] data) {
        try {
            return this.objectMapper.readValue(data, StockExchangeTradeRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] serialize(String topic, StockExchangeTradeRequest data) {
        try {
            return this.objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }


    @Override
    public void close() {
        Serializer.super.close();
    }

    @Override
    public Serializer<StockExchangeTradeRequest> serializer() {
        return this;
    }

    @Override
    public Deserializer<StockExchangeTradeRequest> deserializer() {
        return this;
    }
}

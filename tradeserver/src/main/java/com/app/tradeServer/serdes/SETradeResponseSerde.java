package com.app.tradeServer.serdes;

import com.app.utilities.model.StockExchangeTradeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class SETradeResponseSerde implements Serializer<StockExchangeTradeResponse>, Deserializer<StockExchangeTradeResponse> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public StockExchangeTradeResponse deserialize(String topic, byte[] data) {
        try {
            return this.objectMapper.readValue(data, StockExchangeTradeResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] serialize(String topic, StockExchangeTradeResponse data) {
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
}

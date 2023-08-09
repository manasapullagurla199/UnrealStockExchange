package com.app.stockexchange.serde;

import com.app.utilities.model.StockExchangeTradeResponse;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SETradeResponseSerde implements DeserializationSchema<StockExchangeTradeResponse>, SerializationSchema<StockExchangeTradeResponse> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public StockExchangeTradeResponse deserialize(byte[] message) throws IOException {
        try {
            return this.objectMapper.readValue(message, StockExchangeTradeResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isEndOfStream(StockExchangeTradeResponse nextElement) {
        return false;
    }

    @Override
    public byte[] serialize(StockExchangeTradeResponse element) {
        try {
            return this.objectMapper.writeValueAsBytes(element);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TypeInformation<StockExchangeTradeResponse> getProducedType() {
        return null;
    }
}
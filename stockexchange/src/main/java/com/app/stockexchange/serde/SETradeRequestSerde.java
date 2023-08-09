package com.app.stockexchange.serde;

import com.app.utilities.model.StockExchangeTradeRequest;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SETradeRequestSerde implements DeserializationSchema<StockExchangeTradeRequest>, SerializationSchema<StockExchangeTradeRequest> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public StockExchangeTradeRequest deserialize(byte[] message) throws IOException {
        try {
            return this.objectMapper.readValue(message, StockExchangeTradeRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isEndOfStream(StockExchangeTradeRequest nextElement) {
        return false;
    }

    @Override
    public byte[] serialize(StockExchangeTradeRequest element) {
        try {
            return this.objectMapper.writeValueAsBytes(element);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TypeInformation<StockExchangeTradeRequest> getProducedType() {
        return null;
    }
}

package com.app.stockexchange;

import com.app.stockexchange.utils.TradeMatchingProcessFunction;
import com.app.utilities.model.StockExchangeTradeRequest;
import com.app.utilities.model.StockExchangeTradeResponse;
import com.app.utilities.serdes.SETradeRequestSerde;
import com.app.utilities.serdes.SETradeResponseSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class TradeMatchingApplication{
    public static void main(String[] args) {
        SpringApplication.run(TradeMatchingApplication.class, args);
    }

}

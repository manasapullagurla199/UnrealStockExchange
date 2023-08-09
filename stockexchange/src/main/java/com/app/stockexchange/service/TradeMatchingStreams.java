package com.app.stockexchange.service;

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
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class TradeMatchingStreams implements InitializingBean {
    private final Serdes.StringSerde stringSerde = new Serdes.StringSerde();
    private final SETradeRequestSerde requestSerde = new SETradeRequestSerde();
    private final SETradeResponseSerde responseSerde = new SETradeResponseSerde();
    @Value("${bootstrap.servers}")
    private String kafkaBrokers;
    @Value("${input.topic.name}")
    private String inputTopic;
    @Value("${output.topic.name}")
    private String outputTopic;
    @Value("${application.id}")
    private String kafkaConsumerGroup;

    static void runKafkaStreams(final KafkaStreams streams) {
        final CountDownLatch latch = new CountDownLatch(1);
        streams.setStateListener((newState, oldState) -> {
            if (oldState == KafkaStreams.State.RUNNING && newState != KafkaStreams.State.RUNNING) {
                latch.countDown();
            }
        });

        streams.start();

        try {
            latch.await();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Streams Closed");
    }

    public Topology getTopology() throws Exception {

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, StockExchangeTradeRequest> stockTradeRequests =
                builder.stream(this.inputTopic, Consumed.with(this.stringSerde, this.requestSerde));

        KStream<String, StockExchangeTradeResponse> stockTradeResponses =
                stockTradeRequests.process(TradeMatchingProcessFunction::new);

        stockTradeResponses.to(this.outputTopic, Produced.with(this.stringSerde, this.responseSerde));

        return builder.build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties properties = new Properties();

        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, this.kafkaConsumerGroup);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBrokers);
        KafkaStreams kafkaStreams = new KafkaStreams(this.getTopology(), properties);

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

        log.info("Kafka Streams App Started");
        runKafkaStreams(kafkaStreams);
    }
}

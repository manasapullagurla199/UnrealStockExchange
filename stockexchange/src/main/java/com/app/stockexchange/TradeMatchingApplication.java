package com.app.stockexchange;

import com.app.stockexchange.serde.SETradeRequestSerde;
import com.app.stockexchange.serde.SETradeResponseSerde;
import com.app.utilities.model.StockExchangeTradeRequest;
import com.app.utilities.model.StockExchangeTradeResponse;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Objects;

public class TradeMatchingApplication {

    private final KafkaSource<StockExchangeTradeRequest> ordersSource;
    private final KafkaSink<StockExchangeTradeResponse> sink;
    private final String kafkaBrokers;
    private final String inputTopic;
    private final String outputTopic;
    private final String kafkaConsumerGroup;

    /**
     * Creates a job using the sources and sink provided.
     */
    public TradeMatchingApplication() {
        this.kafkaBrokers = Objects.nonNull(System.getenv("KAFKA_BROKERS")) ? System.getenv("KAFKA_BROKERS") : "localhost";
        this.inputTopic = Objects.nonNull(System.getenv("KAFKA_INPUT_TOPIC")) ? System.getenv("KAFKA_INPUT_TOPIC") : "se-trade-requests";
        this.outputTopic = Objects.nonNull(System.getenv("KAFKA_OUTPUT_TOPIC")) ? System.getenv("KAFKA_OUTPUT_TOPIC") : "se-trade-responses";
        this.kafkaConsumerGroup = Objects.nonNull(System.getenv("KAFKA_CONSUMER_GROUP")) ? System.getenv("KAFKA_CONSUMER_GROUP") : "stockexchangeconsumergroup";
        this.ordersSource = this.stockTradeRequestsSource();
        this.sink = this.stockTradeResponseSink();
    }

    /**
     * Main method.
     *
     * @throws Exception which occurs during job execution.
     */
    public static void main(String[] args) throws Exception {
        TradeMatchingApplication job = new TradeMatchingApplication();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        job.execute(env);
    }

    /**
     * Creates and executes the pipeline using the StreamExecutionEnvironment provided.
     *
     * @param env The {StreamExecutionEnvironment}.
     * @return {JobExecutionResult}
     * @throws Exception which occurs during job execution.
     */
    public JobExecutionResult execute(StreamExecutionEnvironment env) throws Exception {

        // A stream of trade request orders, keyed by stock symbol.
        DataStream<StockExchangeTradeRequest> stockTradeRequests =
                env.fromSource(ordersSource, WatermarkStrategy.noWatermarks(), "Kafka source");

        DataStream<StockExchangeTradeRequest> keyedStream =
                stockTradeRequests.keyBy(StockExchangeTradeRequest::getStockSymbol);

        SingleOutputStreamOperator<StockExchangeTradeResponse> stockTradeResponses =
                keyedStream.process(new TradeMatchingProcessFunction());

        stockTradeResponses.sinkTo(sink);

        // Execute the trade matching pipeline
        return env.execute("Trade Matching");
    }

    private KafkaSource<StockExchangeTradeRequest> stockTradeRequestsSource() {
        return KafkaSource.<StockExchangeTradeRequest>builder()
                .setBootstrapServers(this.kafkaBrokers)
                .setTopics(this.inputTopic)
                .setGroupId(this.kafkaConsumerGroup)
                .setStartingOffsets(OffsetsInitializer.committedOffsets())
                .setValueOnlyDeserializer(new SETradeRequestSerde())
                .build();
    }

    private KafkaSink<StockExchangeTradeResponse> stockTradeResponseSink() {
        return KafkaSink.<StockExchangeTradeResponse>builder()
                .setBootstrapServers(this.kafkaBrokers)
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(this.outputTopic)
                        .setValueSerializationSchema(new SETradeResponseSerde())
                        .build()
                )
                .setDeliveryGuarantee(DeliveryGuarantee.EXACTLY_ONCE)
                .build();
    }
}

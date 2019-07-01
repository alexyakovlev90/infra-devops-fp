package ru.otus.fp.trxreceiver;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Properties;

@Configuration
public class KafkaConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    private static final String clientId = "sample-client";

    @Value("${KAFKA_HOST:localhost:9092}")
    public String kafkaHost;

    @Value("${TOPIC_NAME:sample-topic}")
    private String topicName;

    @Value("${GROUP_ID:sample-group}")
    private String groupId;

    @PostConstruct
    public void init() {
        logger.info("Initialized KAFKA_HOST: {}", kafkaHost);
    }

    @Bean
    public KafkaConsumer<String, String> consumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaHost);
        props.put("group.id", groupId);
        props.put("client.id", clientId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        //props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        //props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        logger.info("Subscribed to topic={}, group={}, clientId={}", topicName, groupId, clientId);
        return consumer;
    }
}

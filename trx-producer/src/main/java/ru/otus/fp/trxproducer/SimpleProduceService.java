package ru.otus.fp.trxproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.UUID;

@Service
public class SimpleProduceService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleProduceService.class);

    @Value("${TOPIC_NAME:sample-topic}")
    private String topicName;

    @Resource
    private KafkaProducer<String, String> producer;

    @PostConstruct
    public void init() {
        logger.info("Initialized TOPIC_NAME: {}", topicName);
    }

    public void sendTrx() {
        String uid = UUID.randomUUID().toString();
        produceTransaction("trx", uid);
    }

    public void sendTrxProcess() {
        String uid = UUID.randomUUID().toString();
        produceTransaction("trx_process", "process_" + uid);
    }

    private void produceTransaction(String key, String val) {
        logger.info("Produce transaction {}:{}", key, val);
        producer.send(new ProducerRecord<>(topicName, key, val));
    }
}

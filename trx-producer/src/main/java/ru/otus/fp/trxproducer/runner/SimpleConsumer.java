package ru.otus.fp.trxproducer.runner;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import static ru.otus.fp.trxproducer.runner.SimpleProducer.ADDRESS;
import static ru.otus.fp.trxproducer.runner.SimpleProducer.TOPIC_NAME;

public class SimpleConsumer {

    public static void main(String[] args) throws Exception {
        //Kafka consumer configuration settings
        final String topicName;
        final String groupId;
        final String clientId;
        if (args.length != 3) {
            System.out.println("Enter topic name, groupId, clientId");
            topicName = TOPIC_NAME;
            groupId = "sample-group";
            clientId = "sample-client";
        } else {
            topicName = args[0];
            groupId = args[1];
            clientId = args[2];
        }

        Properties props = new Properties();

        props.put("bootstrap.servers", ADDRESS);
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

        //Kafka Consumer subscribes list of topics here.
        consumer.subscribe(Arrays.asList(topicName));

        //print the topic name
        System.out.println("Subscribed to topic=" + topicName + ", group=" + groupId + ", clientId=" + clientId);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        // looping until ctrl-c
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)

                // print the offset,key and value for the consumer records.
                System.out.printf("offset = %d, key = %s, value = %s,  time = %s \n",
                        record.offset(), record.key(), record.value(), sdf.format(new Date()));
        }

    }
}

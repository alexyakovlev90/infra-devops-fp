package ru.otus.fp.trxproducer.runner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.StreamsConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class SimpleProducer {

    public static final String TOPIC_NAME = "sample-topic";

    public static final String ADDRESS = "34.77.218.161:9092";

    public static void main(String[] args) throws Exception {

        // Check arguments length value
        String topicName;

        if (args.length == 0) {
            System.out.println("Enter topic name");
            topicName = TOPIC_NAME;
        } else {
            topicName = args[0];
        }

        //Assign topicName to string variable
        System.out.println("Producer topic=" + topicName);

        // create instance for properties to access producer configs
        Properties props = new Properties();
        //Assign localhost id
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ADDRESS);
        //Set acknowledgements for producer requests.
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);
        //Specify buffer size in config
        props.put("batch.size", 16384);
        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter key:value, q - Exit");
        while (true) {
            String input = br.readLine();
            String[] split = input.split(":");

            if ("q".equals(input)) {
                producer.close();
                System.out.println("Exit!");
                System.exit(0);
            } else {
                switch (split.length) {
                    case 1:
                        // strategy by round
                        producer.send(new ProducerRecord<>(topicName, split[0]));
                        break;
                    case 2:
                        // strategy by hash
                        producer.send(new ProducerRecord<>(topicName, split[0], split[1]));
                        break;
                    case 3:
                        // strategy by partition
                        producer.send(new ProducerRecord<>(topicName, Integer.valueOf(split[2]), split[0], split[1]));
                        break;
                    default:
                        System.out.println("Enter key:value, q - Exit");
                }
            }
        }
    }

}

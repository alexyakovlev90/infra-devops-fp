package ru.otus.fp.trxreceiver;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(StartupApplicationListener.class);

    @Resource
    private KafkaConsumer<String, String> consumer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        new Thread(() -> {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5L));
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Receive. offset = {}, key = {}, value = {},  time = {}",
                            record.offset(), record.key(), record.value(), sdf.format(new Date()));
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

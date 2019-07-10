package ru.otus.fp.trxreceiver;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.fp.trxreceiver.api.TrxProcessorApiClient;
import ru.otus.fp.trxreceiver.model.TrxData;
import ru.otus.fp.trxreceiver.model.TrxDataRepository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(StartupApplicationListener.class);

    @Resource
    private KafkaConsumer<String, String> consumer;

    @Resource
    private TrxProcessorApiClient trxProcessorApiClient;

    @Resource
    private TrxDataRepository trxDataRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        new Thread(() -> {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5L));
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Receive. offset = {}, key = {}, value = {},  time = {}",
                            record.offset(), record.key(), record.value(), sdf.format(new Date()));

                    TrxData trxData = new TrxData().setTrxInfo(record.value())
                            .setDateReceived(new Date());
                    trxData = trxDataRepository.save(trxData);
                    ResponseEntity<Long> resp = trxProcessorApiClient.processTrx(trxData);

                    logger.info("Processed: {}", resp);
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

package ru.otus.fp.trxproducer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class TrxProducerApplication implements CommandLineRunner {

    @Resource
    private SimpleProduceService simpleProduceService;

    public static void main(String[] args) {
        SpringApplication.run(TrxProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread trxThread = new Thread(() -> {
            while (true) {
                try {
                    simpleProduceService.sendTrx();
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread trxProcessThread = new Thread(() -> {
            while (true) {
                try {
                    simpleProduceService.sendTrxProcess();
                    Thread.sleep(10_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        trxThread.start();
        trxProcessThread.start();

        trxProcessThread.join();
    }
}

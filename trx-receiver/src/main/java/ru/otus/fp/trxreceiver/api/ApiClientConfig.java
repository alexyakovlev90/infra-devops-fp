package ru.otus.fp.trxreceiver.api;

import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApiClientConfig {

    private static final int READ_TIMEOUT = 2 * 60 * 1000; // 2 min

    @Bean
    public TrxProcessorApiClient stashApiClient(@Value("${trx-processor.endpoint.url}") String address) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .contract(new JAXRSContract())
//                .requestInterceptor(new BasicAuthRequestInterceptor(username, password))
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .options(new Request.Options(10 * 1000, READ_TIMEOUT))
                .target(TrxProcessorApiClient.class, address);
    }
}

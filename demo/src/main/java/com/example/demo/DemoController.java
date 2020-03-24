package com.example.demo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

@RestController
@RequestMapping(value = "/api")
@Api(tags = "Demo API")
@Slf4j
public class DemoController {

    @PostMapping("/random")
    public Long random() {
        return new Random().nextLong();
    }

    @GetMapping("/localhost")
    public String localhost() throws UnknownHostException {
        log.info("REST request to localhost");
        return InetAddress.getLocalHost().getHostAddress();
    }
}

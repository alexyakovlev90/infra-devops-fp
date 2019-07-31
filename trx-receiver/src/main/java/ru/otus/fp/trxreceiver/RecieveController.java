package ru.otus.fp.trxreceiver;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.fp.trxreceiver.model.TrxDataRepository;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api")
@Api(tags = "Trx processor")
@Slf4j
public class RecieveController {

    @Resource
    private TrxDataRepository repository;

    @GetMapping("/count")
    public Long count() {
        log.info("REST request to count Transactions");
        return repository.count();
    }
}

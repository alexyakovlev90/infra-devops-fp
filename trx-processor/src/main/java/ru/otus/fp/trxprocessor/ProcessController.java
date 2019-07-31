package ru.otus.fp.trxprocessor;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.fp.trxprocessor.model.TrxDataProcessed;
import ru.otus.fp.trxprocessor.model.TrxDataProcessedRepository;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api")
@Api(tags = "Trx processor")
@Slf4j
public class ProcessController {

    @Resource
    private TrxDataProcessedService service;

    @Resource
    private TrxDataProcessedRepository repository;

    @PostMapping("/process")
    public Long createBuildingObject(@RequestBody TrxDataDto trxData) {
        log.info("REST request to process Transaction: {}", trxData);
        TrxDataProcessed dataProcessed = service.processTrx(trxData);
        return dataProcessed.getId();
    }

    @GetMapping("/count")
    public Long count() {
        log.info("REST request to count Transactions");
        return repository.count();
    }
}

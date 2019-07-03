package ru.otus.fp.trxprocessor;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.fp.trxprocessor.model.TrxDataProcessed;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/process")
@Api(tags = "Trx processor")
@Slf4j
public class ProcessController {

    @Resource
    private TrxDataProcessedService service;

    @PostMapping
    public ResponseEntity<Long> createBuildingObject(@RequestBody TrxDataDto trxData) {
        log.info("REST request to process Transaction: {}", trxData);
        TrxDataProcessed dataProcessed = service.processTrx(trxData);
        return ResponseEntity.ok()
                .body(dataProcessed.getId());
    }
}

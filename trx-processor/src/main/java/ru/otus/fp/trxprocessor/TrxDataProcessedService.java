package ru.otus.fp.trxprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.fp.trxprocessor.model.TrxDataProcessed;
import ru.otus.fp.trxprocessor.model.TrxDataProcessedRepository;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Slf4j
public class TrxDataProcessedService {

    @Resource
    private TrxDataProcessedRepository repository;

    @Transactional
    public TrxDataProcessed processTrx(TrxDataDto dto) {
        TrxDataProcessed dataProcessed = new TrxDataProcessed()
                .setTrxId(dto.getId())
                .setTrxInfo(dto.getTrxInfo())
                .setTrxDateReceived(dto.getDateReceived())
                .setDateProcessed(new Date());
        TrxDataProcessed trxDataProcessed = repository.save(dataProcessed);
        log.info("Process Transaction: {}", trxDataProcessed);
        return trxDataProcessed;
    }

}

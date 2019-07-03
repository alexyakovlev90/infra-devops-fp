package ru.otus.fp.trxprocessor.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrxDataProcessedRepository extends CrudRepository<TrxDataProcessed, Long> {
}

package ru.otus.fp.trxreceiver.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrxDataRepository extends CrudRepository<TrxData, Long> {
}

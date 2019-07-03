package ru.otus.fp.trxprocessor;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class TrxDataDto {

    private Long id;
    private Date dateReceived;
    private String trxInfo;
}

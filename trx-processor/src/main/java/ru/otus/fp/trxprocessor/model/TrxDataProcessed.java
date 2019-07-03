package ru.otus.fp.trxprocessor.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRX_DATA_PROCESSED")
@Data
@Accessors(chain = true)
public class TrxDataProcessed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TRX_ID")
    private Long trxId;

    @Column(name = "TRX_DATE_RECEIVED")
    private Date trxDateReceived;

    @Column(name = "DATE_PROCESSED")
    private Date dateProcessed;

    @Column(name = "TRX_INFO")
    private String trxInfo;
}

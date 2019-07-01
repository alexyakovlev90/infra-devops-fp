package ru.otus.fp.trxreceiver.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRX_DATA")
@Data
public class TrxData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DATE_RECEIVED")
    private Date dateReceived;

    @Column(name = "TRX_INFO")
    private String trxInfo;
}

package ru.otus.fp.trxreceiver.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRX_DATA")
@Data
@Accessors(chain = true)
public class TrxData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DATE_RECEIVED")
    private Date dateReceived;

    @Column(name = "TRX_INFO")
    private String trxInfo;
}

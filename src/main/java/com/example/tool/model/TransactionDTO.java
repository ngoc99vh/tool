package com.example.tool.model;

import javax.persistence.*;

@Entity
@Table(name = "test_transaction")
public class TransactionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account;
    private String txnhandT;
    private String methohT;
    private String blockT;
    private String datetime;
    private String fromT;
    private String toT;
    private String direction;
    private String valueT;
    private String txnfeeT;

    public TransactionDTO() {
    }

    public TransactionDTO(String account, String txnhand, String methoh, String block, String datetime, String from, String to, String direction, String value, String txnfee) {
        this.account = account;
        this.txnhandT = txnhand;
        this.methohT = methoh;
        this.blockT = block;
        this.datetime = datetime;
        this.fromT = from;
        this.toT = to;
        this.direction = direction;
        this.valueT = value;
        this.txnfeeT = txnfee;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxnhand() {
        return txnhandT;
    }

    public void setTxnhand(String txnhand) {
        this.txnhandT = txnhand;
    }

    public String getMethoh() {
        return methohT;
    }

    public void setMethoh(String methoh) {
        this.methohT = methoh;
    }

    public String getBlock() {
        return blockT;
    }

    public void setBlock(String block) {
        this.blockT = block;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFrom() {
        return fromT;
    }

    public void setFrom(String from) {
        this.fromT = from;
    }

    public String getTo() {
        return toT;
    }

    public void setTo(String to) {
        this.toT = to;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getValue() {
        return valueT;
    }

    public void setValue(String value) {
        this.valueT = value;
    }

    public String getTxnfee() {
        return txnfeeT;
    }

    public void setTxnfee(String txnfee) {
        this.txnfeeT = txnfee;
    }
}

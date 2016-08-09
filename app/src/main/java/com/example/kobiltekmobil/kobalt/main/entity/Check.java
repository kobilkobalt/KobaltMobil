package com.example.kobiltekmobil.kobalt.main.entity;

import java.util.Date;

/**
 * Created by kobiltekMobil on 4.08.2016.
 */
public class Check {

    private int id;
    private int kobilId;
    private Date operationDate;
    private long seriNo;
    private Date termDate;
    private String correspondentBranch;
    private Prevalent debtor;
    private String drawingLocation;
    private double balance;
    private String currency;
    private Bank bank;
    private String description;
    private String status;
    private String type;
    private boolean ciro;
    private Date creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKobilId() {
        return kobilId;
    }

    public void setKobilId(int kobilId) {
        this.kobilId = kobilId;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public long getSeriNo() {
        return seriNo;
    }

    public void setSeriNo(long seriNo) {
        this.seriNo = seriNo;
    }

    public Date getTermDate() {
        return termDate;
    }

    public void setTermDate(Date termDate) {
        this.termDate = termDate;
    }

    public String getCorrespondentBranch() {
        return correspondentBranch;
    }

    public void setCorrespondentBranch(String correspondentBranch) {
        this.correspondentBranch = correspondentBranch;
    }

    public Prevalent getDebtor() {
        return debtor;
    }

    public void setDebtor(Prevalent debtor) {
        this.debtor = debtor;
    }

    public String getDrawingLocation() {
        return drawingLocation;
    }

    public void setDrawingLocation(String drawingLocation) {
        this.drawingLocation = drawingLocation;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCiro() {
        return ciro;
    }

    public void setCiro(boolean ciro) {
        this.ciro = ciro;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

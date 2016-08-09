package com.example.kobiltekmobil.kobalt.main.entity;

import java.util.Date;

/**
 * Created by kobiltekMobil on 4.08.2016.
 */
public class Bank {

    private int id;
    private int kobilId;
    private Date operationDate;
    private String name;
    private String accountOwner;
    private String accountNo;
    private String branchCode;
    private String iban;
    private String currency;
    private double balance;
    private String forWhich;
    private boolean isPos;
    private Date creationDate;
    private long bankCode;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getForWhich() {
        return forWhich;
    }

    public void setForWhich(String forWhich) {
        this.forWhich = forWhich;
    }

    public boolean isPos() {
        return isPos;
    }

    public void setPos(boolean pos) {
        isPos = pos;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getBankCode() {
        return bankCode;
    }

    public void setBankCode(long bankCode) {
        this.bankCode = bankCode;
    }
}

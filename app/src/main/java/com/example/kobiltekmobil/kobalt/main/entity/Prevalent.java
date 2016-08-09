package com.example.kobiltekmobil.kobalt.main.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kobiltekMobil on 15.07.2016.
 */
public class Prevalent implements Serializable{

    private int id;
    private int kobilId;
    private long code;
    private String name;
    private String authorityName;
    private boolean doYouSale;
    private boolean individual;
    private String taxOffice;
    private String taxNo;
    private Double riskLimit;
    private String description;
    private boolean smsSendable;
    private double point;
    private Date operationDate;
    private Date creationDate;

    private String address;
    private String postalCode;
    private String email;
    private String phone1;
    private String phone2;
    private String mobilePhone;

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

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public boolean isDoYouSale() {
        return doYouSale;
    }

    public void setDoYouSale(boolean doYouSale) {
        this.doYouSale = doYouSale;
    }

    public boolean isIndividual() {
        return individual;
    }

    public void setIndividual(boolean individual) {
        this.individual = individual;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public Double getRiskLimit() {
        return riskLimit;
    }

    public void setRiskLimit(Double riskLimit) {
        this.riskLimit = riskLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSmsSendable() {
        return smsSendable;
    }

    public void setSmsSendable(boolean smsSendable) {
        this.smsSendable = smsSendable;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}

package com.example.kobiltekmobil.kobalt.main.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kobiltekMobil on 28.07.2016.
 */
public class KobaltInfo implements Serializable {

    private int id;
    private int kobilId;
    private Date operationDate;
    private String productName;
    private String version;
    private String licenseCode;
    private String owner;
    private String address;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return owner;
    }
}

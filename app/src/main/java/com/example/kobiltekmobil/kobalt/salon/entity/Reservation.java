package com.example.kobiltekmobil.kobalt.salon.entity;

import com.example.kobiltekmobil.kobalt.main.entity.Prevalent;
import com.example.kobiltekmobil.kobalt.main.entity.CategoryItem;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kobiltekMobil on 12.07.2016.
 */
public class Reservation implements Serializable{

    private int id;
    private int kobilId;


    private boolean isCertain;
    private boolean status;
    private Date reservationDate;
    private Date startDate;
    private Date finishDate;
    private Prevalent prevalent;
    private CategoryItem type;
    private long contractNo;
    private Date creationDate;
    private String brideName;
    private String groomName;
    private String detail;
    private String personCount;
    private String region;
    private double netAmount;

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public int getKobilId() {
        return kobilId;
    }

    public void setKobilId(int kobilId) {
        this.kobilId = kobilId;
    }


    public CategoryItem getType() {
        return type;
    }

    public void setType(CategoryItem type) {
        this.type = type;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isCertain() {
        return isCertain;
    }

    public void setCertain(boolean certain) {
        isCertain = certain;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Prevalent getPrevalent() {
        return prevalent;
    }

    public void setPrevalent(Prevalent prevalent) {
        this.prevalent = prevalent;
    }

    public long getContractNo() {
        return contractNo;
    }

    public void setContractNo(long contractNo) {
        this.contractNo = contractNo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getBrideName() {
        return brideName;
    }

    public void setBrideName(String brideName) {
        this.brideName = brideName;
    }

    public String getGroomName() {
        return groomName;
    }

    public void setGroomName(String groomName) {
        this.groomName = groomName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}

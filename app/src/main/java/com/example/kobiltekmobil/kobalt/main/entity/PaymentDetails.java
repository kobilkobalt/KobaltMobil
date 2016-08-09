package com.example.kobiltekmobil.kobalt.main.entity;

import java.util.Map;

/**
 * Created by kobiltekMobil on 4.08.2016.
 */
public class PaymentDetails {

    private int id;
    private int kobilId;
    private Map<String, Double> paymentCash;
    private Map<Bank, Double> paymentBank;
    private PaymentCheck checks;
    private String description;

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

    public Map<String, Double> getPaymentCash() {
        return paymentCash;
    }

    public void setPaymentCash(Map<String, Double> paymentCash) {
        this.paymentCash = paymentCash;
    }

    public Map<Bank, Double> getPaymentBank() {
        return paymentBank;
    }

    public void setPaymentBank(Map<Bank, Double> paymentBank) {
        this.paymentBank = paymentBank;
    }

    public PaymentCheck getChecks() {
        return checks;
    }

    public void setChecks(PaymentCheck checks) {
        this.checks = checks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

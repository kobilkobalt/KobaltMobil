package com.example.kobiltekmobil.kobalt.main.entity;

import java.util.List;

/**
 * Created by kobiltekMobil on 4.08.2016.
 */
public class PaymentCheck {

    private int id;
    private int kobilId;
    private List<Check> checks;

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

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }
}

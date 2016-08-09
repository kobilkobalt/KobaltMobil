package com.example.kobiltekmobil.kobalt.main.entity;

import java.io.Serializable;

/**
 * Created by kobiltekMobil on 15.07.2016.
 */
public class CategoryItem implements Serializable{

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

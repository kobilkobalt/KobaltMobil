package com.example.kobiltekmobil.kobalt.main.helper;

import com.example.kobiltekmobil.kobalt.main.entity.KobaltInfo;

import java.util.List;

/**
 * Created by kobiltekMobil on 29.06.2016.
 */
public class ActiveSettings {
    private static ActiveSettings ourInstance;
    private String dbSchema;
    private String dbUrl;
    private String dbPort;
    private String dbUsername;
    private String dbPassword;
    private String username;
    private String password;
    private String modules;

    public List<KobaltInfo> getKobalts() {
        return kobalts;
    }

    public void setKobalts(List<KobaltInfo> kobalts) {
        this.kobalts = kobalts;
    }

    private List<KobaltInfo> kobalts;

    public static ActiveSettings getInstance() {

        if(ourInstance==null)
            ourInstance=new ActiveSettings();
        return ourInstance;
    }

    public String getDbSchema() {
        return dbSchema;
    }

    public void setDbSchema(String dbSchema) {
        this.dbSchema = dbSchema;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }
}

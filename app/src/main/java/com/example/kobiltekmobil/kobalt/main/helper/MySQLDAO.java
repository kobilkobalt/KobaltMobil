package com.example.kobiltekmobil.kobalt.main.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.kobiltekmobil.kobalt.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by kobiltekMobil on 11.07.2016.
 */
public class MySQLDAO {

    private static MySQLDAO instance;

    private String schema;
    private String url;
    private String port;
    private String username;
    private String password;
    private Connection con;
    private Connection conSaled;
    private Context context;

    public MySQLDAO(Context context) {
        this.context = context;
    }


    public static MySQLDAO getInstance(Context context) {
        if (instance == null) {
            instance = new MySQLDAO(context);
            instance.setUrl(ActiveSettings.getInstance().getDbUrl());
            instance.setSchema(ActiveSettings.getInstance().getDbSchema());
            instance.setUsername(ActiveSettings.getInstance().getDbUsername());
            instance.setPassword(ActiveSettings.getInstance().getDbPassword());
            instance.setPort(ActiveSettings.getInstance().getDbPort());
        }
        return instance;
    }

    private Connection connectDatabase() {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (con == null) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                con = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/" + schema, username, password);
            }

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (SQLException e) {
            DBHelperLoggedUser db = new DBHelperLoggedUser(context);
            db.deleteUsers();
            e.printStackTrace();

        }
        return con;
    }

    private Connection connectDatabaseToKobiltek() {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (conSaled == null) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                conSaled = DriverManager.getConnection("jdbc:mysql://" + "217.116.198.62" + ":" + "3306" + "/" + "kobiltek_control", "control", "kobil2013");
            }

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (SQLException e) {
            DBHelperLoggedUser db = new DBHelperLoggedUser(context);
            db.deleteUsers();
            e.printStackTrace();

        }
        return conSaled;
    }

    public ResultSet getObjectFromSaledKobalt(String sql) {

        connectDatabaseToKobiltek();
        ResultSet resultSet = null;
        try {

            Statement statement = conSaled.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context,
                    "İnternet Bağlantınızı Kontrol Ediniz !", Toast.LENGTH_LONG)
                    .show();
            con = null;
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(context,
                    "İnternet Bağlantınızı Kontrol Ediniz !", Toast.LENGTH_LONG)
                    .show();
            con = null;
            return null;
        }
        return resultSet;
    }



    public ResultSet getObject(String sql) {

        connectDatabase();
        ResultSet resultSet = null;
        try {

            Statement statement = con.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException e) {

            Toast.makeText(context,
                    "İnternet Bağlantınızı Kontrol Ediniz !", Toast.LENGTH_LONG)
                    .show();
            con = null;
            return null;
        } catch (NullPointerException e) {
            Toast.makeText(context,
                    "İnternet Bağlantınızı Kontrol Ediniz !", Toast.LENGTH_LONG)
                    .show();
            con = null;
            return null;
        }
        return resultSet;
    }


    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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


}

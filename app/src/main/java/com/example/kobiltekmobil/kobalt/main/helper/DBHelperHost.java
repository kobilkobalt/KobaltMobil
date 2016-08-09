package com.example.kobiltekmobil.kobalt.main.helper;

/**
 * Created by kobiltekMobil on 23.06.2016.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelperHost extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "kobalt.db";
    public static final String CONTACTS_TABLE_NAME = "host";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String HOST_COLUMN_SCHEMA = "schema";
    public static final String HOST_COLUMN_URL = "url";
    public static final String HOST_COLUMN_PORT = "port";
    public static final String HOST_COLUMN_USERNAME = "username";
    public static final String HOST_COLUMN_PASSWORD = "password";
    private HashMap hp;

    public DBHelperHost(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table host " +
                        "(id integer primary key, schema text,url text,port text, username text,password text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS host");
        onCreate(db);
    }

    public boolean insertContact(String schema, String url, String port, String username, String password) {
        if (schema.length() <= 0 || url.length() <= 0 || username.length() <= 0 || password.length() <= 0) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("schema", schema);
        contentValues.put("url", url);
        contentValues.put("port", port);
        contentValues.put("username", username);
        contentValues.put("password", password);
        db.insert("host", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from host where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateHost(Integer id, String schema, String url, String port, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("schema", schema);
        contentValues.put("url", url);
        contentValues.put("port", port);
        contentValues.put("username", username);
        contentValues.put("password", password);
        db.update("host", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteHost(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("host",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllHosts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from host", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(HOST_COLUMN_URL)));
            res.moveToNext();
        }
        return array_list;
    }
}

package com.example.kobiltekmobil.kobalt.main.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.ActiveSettings;
import com.example.kobiltekmobil.kobalt.main.helper.DBHelperLoggedUser;
import com.example.kobiltekmobil.kobalt.main.helper.MySQLDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class Splash extends AppCompatActivity {

    private DBHelperLoggedUser db;
    private String name;
    private String password;
    private static final int REQUEST_CAMERA = 0;
    private boolean hasMobileId = false;
    private int thisKobilId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBHelperLoggedUser(getApplicationContext());

        if (db.numberOfRows() > 0) {

            // User is already logged in. Take him to main activity
            HashMap<String, String> user = db.getUserDetails();
            name = user.get("username");
            password = user.get("password");
            String dbSchema = user.get("schema");
            String dbUrl = user.get("url");
            String dbPort = user.get("port");
            String dbUsername = user.get("db_username");
            String dbPassword = user.get("db_password");

            ActiveSettings.getInstance().setDbSchema(dbSchema);
            ActiveSettings.getInstance().setDbUrl(dbUrl);
            ActiveSettings.getInstance().setDbPort(dbPort);
            ActiveSettings.getInstance().setDbUsername(dbUsername);
            ActiveSettings.getInstance().setDbPassword(dbPassword);


            login();

        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void login() {

        ResultSet resultSet = MySQLDAO.getInstance(this).getObject("select ROLE_ID,kobilId from user where username='" + name + "' and password='" + password + "'");
        if (resultSet != null) {

            try {
                if (!resultSet.isBeforeFirst()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.loginFault, Toast.LENGTH_LONG)
                            .show();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                while (resultSet.next()) {

                    int id = resultSet.getInt("ROLE_ID");
                    thisKobilId = resultSet.getInt("kobilId");
                    controlHasPermission();
                    if (!hasMobileId) {
                        for (int i=0; i < 2; i++)
                        {
                            Toast.makeText(getApplicationContext(),
                                    R.string.no_mobile_permission, Toast.LENGTH_LONG)
                                    .show();
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    String sql = "select * from role where ROLE_ID=" + id + "";
                    ResultSet resultSet1 = MySQLDAO.getInstance(this).getObject(sql);
                    while (resultSet1.next()) {

                        String modules = resultSet1.getString("modules");
                        if (modules.length() <= 0) {
                            Toast.makeText(getApplicationContext(),
                                    R.string.no_module_permission, Toast.LENGTH_LONG)
                                    .show();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            db = new DBHelperLoggedUser(this);
                            db.addUser(name, password, ActiveSettings.getInstance().getDbSchema(),
                                    ActiveSettings.getInstance().getDbUrl(), ActiveSettings.getInstance().getDbPort(),
                                    ActiveSettings.getInstance().getDbUsername(), ActiveSettings.getInstance().getDbPassword());
                            ActiveSettings.getInstance().setModules(modules);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(this,
                                    ModulesActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }


            } catch (SQLException e) {


                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        R.string.loginFault, Toast.LENGTH_LONG)
                        .show();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                Intent intent = new Intent(this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {

            Toast.makeText(this,
                    R.string.loginFault, Toast.LENGTH_LONG)
                    .show();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void controlHasPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Telefon IMEI Erişimi");
                alertBuilder.setMessage("Uygulamayı kullanmanız için aygıtınızın imei numarasına erişim izni gerekiyor .");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(Splash.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},
                                REQUEST_CAMERA);
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();

            } else {

                // Camera permission has not been granted yet. Request it directly.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_CAMERA);
            }
        } else {
            String deviceId = null;

            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = mngr.getDeviceId();



            String mobileId = null;
            ResultSet resultSet = MySQLDAO.getInstance(this).getObjectFromSaledKobalt("select mobileId from saledkobalt where thisKobilId=" + thisKobilId);
            System.out.println("select mobileId from saledkobalt where thisKobilId=" + thisKobilId);
            try {

                if (!resultSet.isBeforeFirst()) {
                    hasMobileId = false;

                }
                while (resultSet.next()) {
                    System.out.println(456+resultSet.getString("mobileId"));
                    mobileId = resultSet.getString("mobileId");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }


            if (mobileId == null) {
                hasMobileId = false;
                return;
            }



            String[] split = mobileId.split(",");

            if (split != null)
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(deviceId))
                        hasMobileId = true;
                }
            else

                    if (split[0].equals(deviceId))
                        hasMobileId = true;

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CAMERA) {

            String deviceId = null;

            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = mngr.getDeviceId();


            String mobileId = null;
            ResultSet resultSet = MySQLDAO.getInstance(this).getObjectFromSaledKobalt("select mobileId from saledkobalt where thisKobilId=" + thisKobilId);
            try {
                if (!resultSet.isBeforeFirst()) {
                    hasMobileId = false;
                }
                while (resultSet.next()) {
                    mobileId = resultSet.getString("mobileId");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }


            if (mobileId == null) {
                hasMobileId = false;
                return;
            }
            String[] split = mobileId.split(",");

            if (split != null)
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(deviceId))
                        hasMobileId = true;
                }

            else

            if (split[0].equals(deviceId))
                hasMobileId = true;

        }
    }
}



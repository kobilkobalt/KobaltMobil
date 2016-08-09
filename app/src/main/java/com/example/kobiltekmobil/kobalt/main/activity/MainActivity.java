package com.example.kobiltekmobil.kobalt.main.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.ActiveSettings;
import com.example.kobiltekmobil.kobalt.main.helper.DBHelperHost;
import com.example.kobiltekmobil.kobalt.main.helper.DBHelperLoggedUser;
import com.example.kobiltekmobil.kobalt.main.helper.MySQLDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    DBHelperHost mydb;
    private Spinner obj;
    EditText editUsername;
    EditText editPassword;

    private LinearLayout layout;
    private DBHelperLoggedUser db;
    private String name;
    private String password;
    private static final int REQUEST_CAMERA = 0;
    private boolean hasMobileId = false;
    private int thisKobilId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KOBALT MOBİL");


        db = new DBHelperLoggedUser(getApplicationContext());


        // Check if user is already logged in or not


        layout = (LinearLayout) findViewById(R.id.main_Layout);


        mydb = new DBHelperHost(this);
        ArrayList array_list = mydb.getAllHosts();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        obj = (Spinner) findViewById(R.id.spinnerHost);
        obj.setAdapter(arrayAdapter);

        obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int Value = arg2 + 1;
                if (Value > 0) {
                    //means this is the view part not the add contact part.
                    Cursor rs = mydb.getData(Value);

                    rs.moveToFirst();
                    String sch = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_SCHEMA));
                    String url1 = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_URL));
                    String port1 = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_PORT));
                    String usr = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_USERNAME));
                    String pass = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_PASSWORD));

                    ActiveSettings.getInstance().setDbSchema(sch);
                    ActiveSettings.getInstance().setDbUrl(url1);
                    ActiveSettings.getInstance().setDbPort(port1);
                    ActiveSettings.getInstance().setDbUsername(usr);
                    ActiveSettings.getInstance().setDbPassword(pass);


                    if (!rs.isClosed()) {
                        rs.close();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        ImageButton buttonHost = (ImageButton) findViewById(R.id.buttonHost);

        buttonHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HostActivity.class);
                startActivity(intent);
            }
        });

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editUsername = (EditText) findViewById(R.id.editUsernameLogin);
                editPassword = (EditText) findViewById(R.id.editPasswordLogin);
                name = editUsername.getText().toString();
                password = editPassword.getText().toString();

                if (!name.isEmpty() && !password.isEmpty()) {
                    login();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            R.string.inputParamaters, Toast.LENGTH_LONG)
                            .show();
                }


            }
        });


    }

    private void login() {


        ResultSet resultSet = MySQLDAO.getInstance(this).getObject("select ROLE_ID,kobilId from user where username='" + name + "' and password='" + password + "'");
        if (resultSet != null) {

            try {
                if (!resultSet.isBeforeFirst()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.loginFault, Toast.LENGTH_LONG)
                            .show();
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
                        } else {
                            db = new DBHelperLoggedUser(this);
                            db.addUser(name, password, ActiveSettings.getInstance().getDbSchema(),
                                    ActiveSettings.getInstance().getDbUrl(), ActiveSettings.getInstance().getDbPort(),
                                    ActiveSettings.getInstance().getDbUsername(), ActiveSettings.getInstance().getDbPassword());
                            ActiveSettings.getInstance().setModules(modules);
                            Intent intent = new Intent(this,
                                    ModulesActivity.class);
                            startActivity(intent);
                        }

                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        R.string.loginFault, Toast.LENGTH_LONG)
                        .show();
            }
        } else {

            Toast.makeText(this,
                    R.string.loginFault, Toast.LENGTH_LONG)
                    .show();
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
                        ActivityCompat.requestPermissions(MainActivity.this,
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

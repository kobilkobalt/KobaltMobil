package com.example.kobiltekmobil.kobalt.main.activity;

import android.os.Bundle;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.DBHelperHost;

public class DisplayHost extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelperHost mydb ;

    Toolbar toolbar;

    TextView schema ;
    TextView url;
    TextView port;
    TextView username;
    TextView password;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_host);
        initToolBar();
        schema = (TextView) findViewById(R.id.editSchema);
        url = (TextView) findViewById(R.id.editUrl);
        port = (TextView) findViewById(R.id.editPort);
        username = (TextView) findViewById(R.id.editUsernameLogin);
        password = (TextView) findViewById(R.id.editPasswordLogin);

        mydb = new DBHelperHost(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);

                id_To_Update = Value;
                rs.moveToFirst();

                String sch = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_SCHEMA));
                String url1 = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_URL));
                String port1 = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_PORT));
                String usr = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_USERNAME));
                String pass = rs.getString(rs.getColumnIndex(DBHelperHost.HOST_COLUMN_PASSWORD));

                if (!rs.isClosed())
                {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                schema.setText((CharSequence)sch);
                schema.setFocusable(false);
                schema.setClickable(false);

                url.setText((CharSequence)url1);
                url.setFocusable(false);
                url.setClickable(false);

                port.setText((CharSequence)port1);
                port.setFocusable(false);
                port.setClickable(false);

                username.setText((CharSequence)usr);
                username.setFocusable(false);
                username.setClickable(false);

                password.setText((CharSequence)pass);
                password.setFocusable(false);
                password.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            }

            else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                schema.setEnabled(true);
                schema.setFocusableInTouchMode(true);
                schema.setClickable(true);

                url.setEnabled(true);
                url.setFocusableInTouchMode(true);
                url.setClickable(true);

                port.setEnabled(true);
                port.setFocusableInTouchMode(true);
                port.setClickable(true);

                username.setEnabled(true);
                username.setFocusableInTouchMode(true);
                username.setClickable(true);

                password.setEnabled(true);
                password.setFocusableInTouchMode(true);
                password.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteHost)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteHost(id_To_Update);
                                Toast.makeText(getApplicationContext(), R.string.deletedSuccessfully, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),HostActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateHost(id_To_Update,schema.getText().toString(), url.getText().toString(), port.getText().toString(), username.getText().toString(), password.getText().toString())){
                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),HostActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.not_updated, Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertContact(schema.getText().toString(), url.getText().toString(), port.getText().toString(), username.getText().toString(), password.getText().toString())){
                    Toast.makeText(getApplicationContext(), R.string.done, Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), R.string.not_done, Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),HostActivity.class);
                startActivity(intent);
            }
        }
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_display_host);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(DisplayHost.this,HostActivity.class);
                        startActivity(intent);
                    }
                }

        );
    }
}

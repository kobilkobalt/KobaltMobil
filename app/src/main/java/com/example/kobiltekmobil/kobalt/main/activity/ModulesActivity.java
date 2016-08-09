package com.example.kobiltekmobil.kobalt.main.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.bordro.activity.BordroActivity;
import com.example.kobiltekmobil.kobalt.main.helper.ActiveSettings;
import com.example.kobiltekmobil.kobalt.main.helper.DBHelperLoggedUser;
import com.example.kobiltekmobil.kobalt.salon.activity.SalonActivity;
import com.example.kobiltekmobil.kobalt.salon.activity.SalonCalendarFragment;

public class ModulesActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KOBALT MOBİL");


        String[] split = ActiveSettings.getInstance().getModules().split(",");


        LinearLayout ll = (LinearLayout) findViewById(R.id.modulesLayout);

        for (int i = 0; i < split.length; i++) {

            Button moduleButton = new Button(ModulesActivity.this);
            moduleButton.setTextSize(20.0f);
            moduleButton.setTextColor(Color.BLACK);
            moduleButton.setBackgroundColor(Color.TRANSPARENT);
            moduleButton.setGravity(Gravity.LEFT);

            if (split[i].equals("1011")) {
                moduleButton.setText("SALON");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.salon, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (split[i].equals("1000")) {
                moduleButton.setText("BORDRO");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bordro, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, BordroActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (split[i].equals("1007")) {
                moduleButton.setText("KASA/LAR");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.safe, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (split[i].equals("1012")) {
                moduleButton.setText("SMS");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sendsms, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (split[i].equals("996")) {
                moduleButton.setText("BANKA");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bank, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (split[i].equals("997")) {
                moduleButton.setText("MÜŞTERİ");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.customer, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (split[i].equals("998")) {
                moduleButton.setText("FİRMA");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.company, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (split[i].equals("999")) {
                moduleButton.setText("PERSONEL");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.employee, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1001")) {
                moduleButton.setText("KAMPANYA");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.campaign, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1002")) {
                moduleButton.setText("ARAÇ TAKİP");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cartrack, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1003")) {
                moduleButton.setText("OTO SERVİS");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vehicleservice, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1004")) {
                moduleButton.setText("STOK");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stock, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1005")) {
                moduleButton.setText("STOK ARAMA");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stocksearch, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1008")) {
                moduleButton.setText("SATILAN ÜRÜNLER");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.saledproducts, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1009")) {
                moduleButton.setText("SATIŞ BİLGİLERİ");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.saleinformations, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1010")) {
                moduleButton.setText("SATIŞ");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sale, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1013")) {
                moduleButton.setText("ALIŞ");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.purchase, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1014")) {
                moduleButton.setText("ALIŞ BİLGİLERİ");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.purchasedproducts, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (split[i].equals("1015")) {
                moduleButton.setText("ALINAN ÜRÜNLER");
                moduleButton.setCompoundDrawablePadding(30);
                moduleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.purchaseinformations, 0, 0, 0);
                ll.addView(moduleButton);
                moduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ModulesActivity.this, SalonActivity.class);
                        startActivity(intent);
                    }
                });
            }


        }

        Button exitButton = new Button(ModulesActivity.this);
        exitButton.setTextSize(20.0f);
        exitButton.setTextColor(Color.BLACK);
        exitButton.setBackgroundColor(Color.TRANSPARENT);
        exitButton.setCompoundDrawablePadding(30);
        exitButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.exit,0, 0, 0);
        exitButton.setText("ÇIKIŞ");
        exitButton.setGravity(Gravity.LEFT);
        ll.addView(exitButton);


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelperLoggedUser db = new DBHelperLoggedUser(ModulesActivity.this);
                db.deleteUsers();

                // Launching the login activity
                Intent intent = new Intent(ModulesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

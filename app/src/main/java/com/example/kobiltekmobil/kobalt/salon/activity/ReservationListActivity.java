package com.example.kobiltekmobil.kobalt.salon.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.GeneralMethods;
import com.example.kobiltekmobil.kobalt.main.entity.KobaltInfo;
import com.example.kobiltekmobil.kobalt.main.helper.MySQLDAO;
import com.example.kobiltekmobil.kobalt.main.entity.Prevalent;
import com.example.kobiltekmobil.kobalt.salon.helper.AdapterReservationList;
import com.example.kobiltekmobil.kobalt.salon.entity.Reservation;
import com.example.kobiltekmobil.kobalt.main.entity.CategoryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationListActivity extends AppCompatActivity {

    private int totalAmount;
    private TextView textTotalAmount;
    private int totalResult;
    private TextView textTotalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);
        final Intent intent = getIntent();
        Date date = (Date) intent.getExtras().get("day");
        KobaltInfo salon = (KobaltInfo) intent.getSerializableExtra("salon");
        List<Reservation> idList = (List<Reservation>) intent.getSerializableExtra("idList");
        List<Reservation> searchResults = null;
        if (idList == null) {
            searchResults = getReservations(date, salon);
        } else {
            searchResults = idList;
        }
        final ListView lv1 = (ListView) findViewById(R.id.listViewReservationList);
        lv1.setAdapter(new AdapterReservationList(this, searchResults));

        textTotalAmount = (TextView) findViewById(R.id.textTotalAmount);
        textTotalResult = (TextView) findViewById(R.id.textTotalResult);

        for (Reservation reservation : searchResults) {
            totalAmount += reservation.getNetAmount();
        }
        totalResult=searchResults.size();

        textTotalAmount.setText(GeneralMethods.getInstance().setValue(totalAmount));
        textTotalResult.setText(String.valueOf(totalResult));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Arama Sonucu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Reservation fullObject = (Reservation) o;
                Intent intent = new Intent(ReservationListActivity.this, ReservationActivity.class);
                intent.putExtra("id", fullObject.getId());
                startActivity(intent);


            }
        });
    }

    private List<Reservation> getReservations(Date day, KobaltInfo salon) {

        List<Reservation> list = new ArrayList<>();
        String changeTimeTo = GeneralMethods.getInstance().changeTimeTo(day);
        String changeTime = GeneralMethods.getInstance().changeTime(day);
        String sql = "SELECT t1.netAmount,t1.id,t1.type,t1.reservationDate,t1.startdate,t1.finishdate,t1.isCertain,t2.name,t2.id FROM reservation t1,prevalent t2 where t1.kobilId=" + salon.getKobilId() + " and (t1.reservationDate between '" + changeTime + "' and '" + changeTimeTo + "') and status=1 and t1.prevalent=t2.id";
        ResultSet resultSet = MySQLDAO.getInstance(this).getObject(sql);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setReservationDate(resultSet.getDate("reservationDate"));
                    reservation.setStartDate(resultSet.getTimestamp("t1.startDate"));
                    reservation.setFinishDate(resultSet.getTimestamp("t1.finishDate"));
                    reservation.setId(resultSet.getInt("t1.id"));
                    reservation.setKobilId(salon.getKobilId());
                    Prevalent prevalent = new Prevalent();
                    prevalent.setName(resultSet.getString("name"));
                    prevalent.setId(resultSet.getInt("t2.id"));
                    reservation.setPrevalent(prevalent);
                    reservation.setNetAmount(resultSet.getDouble("t1.netAmount"));

                    String sqlType = "select * from categoryitem where id=" + resultSet.getInt("t1.type");
                    ResultSet resultSet1 = MySQLDAO.getInstance(this).getObject(sqlType);
                    CategoryItem categoryItem = new CategoryItem();
                    while (resultSet1.next()) {
                        categoryItem.setId(resultSet1.getInt("id"));
                        categoryItem.setName(resultSet1.getString("name"));
                    }
                    reservation.setType(categoryItem);
                    list.add(reservation);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return list;

    }


}

package com.example.kobiltekmobil.kobalt.salon.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.ActiveSettings;
import com.example.kobiltekmobil.kobalt.main.entity.CategoryItem;
import com.example.kobiltekmobil.kobalt.main.helper.GeneralMethods;
import com.example.kobiltekmobil.kobalt.main.entity.KobaltInfo;
import com.example.kobiltekmobil.kobalt.main.helper.MySQLDAO;
import com.example.kobiltekmobil.kobalt.main.entity.Prevalent;
import com.example.kobiltekmobil.kobalt.salon.entity.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by kobiltekMobil on 3.08.2016.
 */


public class ReservationDetailFragment extends Fragment {

    private TextView contractNo;
    private TextView contractDate;
    private TextView reservationDate;
    private TextView startDate;
    private TextView finishDate;
    private TextView brideName;
    private TextView groomName;
    private TextView prevalent;
    private TextView salon;
    private TextView type;
    private TextView detail;
    private TextView personCount;
    private TextView region;
    private TextView isCertain;
    private TextView status;

    public ReservationDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void fillView(int resId) {

        Reservation reservation = new Reservation();
        String sql = "select t1.id,t1.kobilId,t1.contractNo, t1.creationDate, t1.type, t1.brideName, t1.groomName, t1.detail, " +
                "t1.reservationDate, t1.startDate, t1.finishDate, t1.isCertain, t1.status, t1.personCount, t1.region, t2.name,t2.id from reservation t1,prevalent t2 where t1.id=" + resId + " and t1.prevalent=t2.id";
        ResultSet resultSet = MySQLDAO.getInstance(getActivity()).getObject(sql);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    int type = resultSet.getInt("t1.type");

                    ResultSet resultSetType = MySQLDAO.getInstance(getActivity()).getObject("select * from categoryitem where id=" + type);

                    CategoryItem type1 = new CategoryItem();


                    while (resultSetType.next()) {
                        type1.setName(resultSetType.getString("name"));
                    }

                    Prevalent prevalent = new Prevalent();
                    prevalent.setName(resultSet.getString("t2.name"));
                    reservation.setPrevalent(prevalent);
                    reservation.setId(resultSet.getInt("t1.id"));
                    reservation.setReservationDate(resultSet.getDate("t1.reservationDate"));
                    reservation.setFinishDate(resultSet.getTimestamp("t1.finishDate"));
                    reservation.setStartDate(resultSet.getTimestamp("t1.startDate"));
                    reservation.setBrideName(resultSet.getString("t1.brideName"));
                    reservation.setGroomName(resultSet.getString("t1.groomName"));
                    reservation.setCertain(resultSet.getBoolean("t1.isCertain"));
                    reservation.setStatus(resultSet.getBoolean("t1.status"));
                    reservation.setContractNo(resultSet.getLong("t1.contractNo"));
                    reservation.setKobilId(resultSet.getInt("t1.kobilId"));
                    reservation.setType(type1);
                    reservation.setRegion(resultSet.getString("t1.region"));
                    reservation.setPersonCount(resultSet.getString("t1.personCount"));
                    reservation.setDetail(resultSet.getString("t1.detail"));
                    reservation.setCreationDate(resultSet.getDate("t1.creationDate"));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        contractNo.setText(String.valueOf(reservation.getContractNo()));
        contractDate.setText(GeneralMethods.getInstance().getFormattedDateWithoutTime("TR", reservation.getCreationDate()));
        reservationDate.setText(GeneralMethods.getInstance().getFormattedDateWithoutTime("TR", reservation.getReservationDate()));
        DateFormat formato = new SimpleDateFormat("HH:mm");
        String hora = formato.format(reservation.getStartDate());
        startDate.setText(hora);
        DateFormat formato1 = new SimpleDateFormat("HH:mm");
        String hora1 = formato.format(reservation.getFinishDate());
        finishDate.setText(hora1);
        brideName.setText(reservation.getGroomName());
        groomName.setText(reservation.getBrideName());
        prevalent.setText(reservation.getPrevalent().getName());
        String salonText = null;
        for (KobaltInfo kobaltInfo : ActiveSettings.getInstance().getKobalts()) {
            if(reservation.getKobilId()==kobaltInfo.getKobilId())
                salonText=kobaltInfo.getOwner();
        }
        salon.setText(salonText);
        type.setText(reservation.getType().toString());
        detail.setText(reservation.getDetail());
        personCount.setText(reservation.getPersonCount());
        region.setText(reservation.getRegion());
        if (!reservation.isCertain())
            isCertain.setText("Satış");
        else
            isCertain.setText("Ön Rezervasyon");
        if (!reservation.isStatus())
            status.setText("Aktif");
        else
            status.setText("Pasif");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_reservation_detail, container, false);
        contractNo = (TextView) rootView.findViewById(R.id.textContractNo);
        contractDate = (TextView) rootView.findViewById(R.id.textContractDate);
        reservationDate = (TextView) rootView.findViewById(R.id.textReservationDate);
        startDate = (TextView) rootView.findViewById(R.id.textStartDate);
        finishDate = (TextView) rootView.findViewById(R.id.textFinishDate);
        brideName = (TextView)rootView.findViewById(R.id.textBride);
        groomName = (TextView)rootView.findViewById(R.id.textGroom);
        prevalent = (TextView) rootView.findViewById(R.id.textPrevalent);
        salon = (TextView) rootView.findViewById(R.id.textSalon);
        type = (TextView) rootView.findViewById(R.id.textType);
        detail = (TextView) rootView.findViewById(R.id.textDetail);
        personCount = (TextView) rootView.findViewById(R.id.textPersonCount);
        region = (TextView) rootView.findViewById(R.id.textRegion);
        isCertain = (TextView) rootView.findViewById(R.id.textCertain);
        status = (TextView) rootView.findViewById(R.id.textStatus);

        detail.setMovementMethod(new ScrollingMovementMethod());


        int resId = getActivity().getIntent().getExtras().getInt("id");

        fillView(resId);


        return rootView;
    }

}
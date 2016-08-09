package com.example.kobiltekmobil.kobalt.salon.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.MySQLDAO;
import com.example.kobiltekmobil.kobalt.salon.helper.AdapterServiceList;
import com.example.kobiltekmobil.kobalt.salon.entity.ReservationService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kobiltekMobil on 3.08.2016.
 */


public class ReservationServiceFragment extends Fragment {

    private View rootView;


    public ReservationServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void fillView(int resId) {

        List<ReservationService> services=new ArrayList<>();
        ResultSet resultSet = MySQLDAO.getInstance(getActivity()).getObject("select * from reservation_saledservice where reservation_id=" + resId);

        try {
            while (resultSet.next()) {

                ResultSet resultSet1 = MySQLDAO.getInstance(getActivity()).getObject("select * from saledservice where id=" + resultSet.getInt("services_id"));

                while (resultSet1.next()) {

                    ReservationService service = new ReservationService();
                    service.setName(resultSet1.getString("name"));
                    service.setAmount(resultSet1.getDouble("amount"));
                    service.setDescription(resultSet1.getString("description"));
                    service.setDiscount(resultSet1.getDouble("discount"));
                    service.setSalePrice(resultSet1.getDouble("price"));
                    service.setNetAmount(resultSet1.getDouble("price") * resultSet1.getDouble("amount") - resultSet1.getDouble("discount"));
                    services.add(service);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView lv1 = (ListView) rootView.findViewById(R.id.listViewServiceList);
        lv1.setAdapter(new AdapterServiceList(getActivity(), services));




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootView = inflater.inflate(R.layout.fragment_reservation_service, container, false);

        int resId = getActivity().getIntent().getExtras().getInt("id");

        fillView(resId);


        return rootView;
    }

}
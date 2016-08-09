package com.example.kobiltekmobil.kobalt.main.helper;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kobiltekmobil.kobalt.main.entity.CategoryItem;
import com.example.kobiltekmobil.kobalt.main.entity.KobaltInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kobiltekMobil on 11.07.2016.
 */
public class GeneralMethods {

    public static GeneralMethods instance;

    public static GeneralMethods getInstance(){
        if(instance==null)
            instance=new GeneralMethods();
        return instance;
    }




    public void fillCategorySpinner(Context context, Spinner spinner, List<CategoryItem> array){



        ArrayAdapter<CategoryItem> adapter = new ArrayAdapter<CategoryItem>(
                context, android.R.layout.simple_spinner_item, array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }

    public String changeTimeForMonth(Date date) {
        Calendar now=Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.DAY_OF_MONTH,1);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
    }

    public String changeTimeToForMonth(Date date) {
        Calendar now=Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, 1);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.add(Calendar.DATE, -1);
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 24);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
    }

    public String changeTime(Date dateFrom2) {
        Calendar now = Calendar.getInstance();
        now.setTime(dateFrom2);
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
    }

    public String changeTimeTo(Date dateFrom2) {
        Calendar now = Calendar.getInstance();
        now.setTime(dateFrom2);
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 24);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
    }

    public  String getFormattedDateWithoutTime(String locale, Date date) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            date = formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formatter.format(date);
    }

    public void fillAllKobalts(Context context){
        if(ActiveSettings.getInstance().getKobalts()==null){
            List<KobaltInfo> kobaltInfos=new ArrayList<>();



            ResultSet resultSet = MySQLDAO.getInstance(context).getObject("select * from KobaltInfo");
            try {
                while (resultSet.next()){
                    KobaltInfo kobaltInfo=new KobaltInfo();
                    kobaltInfo.setId(resultSet.getInt("id"));
                    kobaltInfo.setCreationDate(resultSet.getDate("creationDate"));
                    kobaltInfo.setAddress(resultSet.getString("address"));
                    kobaltInfo.setKobilId(resultSet.getInt("kobilId"));
                    kobaltInfo.setLicenseCode(resultSet.getString("licenseCode"));
                    kobaltInfo.setOperationDate(resultSet.getDate("operationDate"));
                    kobaltInfo.setOwner(resultSet.getString("owner"));
                    kobaltInfo.setVersion(resultSet.getString("version"));
                    kobaltInfos.add(kobaltInfo);

                }
                ActiveSettings.getInstance().setKobalts(kobaltInfos);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public String setValue(double value) {
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        String value1=formatter.format(value)+" TL";
        return value1;
    }

}

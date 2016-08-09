package com.example.kobiltekmobil.kobalt.salon.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.ActiveSettings;
import com.example.kobiltekmobil.kobalt.main.entity.CategoryItem;
import com.example.kobiltekmobil.kobalt.main.helper.GeneralMethods;
import com.example.kobiltekmobil.kobalt.main.entity.KobaltInfo;
import com.example.kobiltekmobil.kobalt.main.helper.MySQLDAO;
import com.example.kobiltekmobil.kobalt.main.entity.Prevalent;
import com.example.kobiltekmobil.kobalt.salon.entity.Reservation;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SalonSearchFragment extends Fragment {


    private View rootView;
    private EditText textResStartDate;

    private DatePickerDialog resStartDatePicker;
    private DatePickerDialog resFinishDatePicker;
    private DatePickerDialog contractStartDatePicker;
    private DatePickerDialog contractFinishDatePicker;

    private SimpleDateFormat dateFormatter;
    private EditText textResFinishDate;
    private EditText textContractStartDate;
    private EditText textContractFinishDate;

    private EditText textContractNo;
    private EditText textPrevalent;
    private Spinner salon;
    private Spinner type;
    private Spinner isCertain;
    private Spinner status;
    private EditText textBride;
    private EditText textGroom;

    private Button clear;
    private Button search;

    public SalonSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_salon_search, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

        clear();

        initListeners();


        return rootView;
    }

    private void initListeners() {

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

    }

    private void fillStatusSpinner() {

        List<CategoryItem> categoryItems = new ArrayList<>();

        CategoryItem choose = new CategoryItem();
        choose.setName("Seçiniz");

        categoryItems.add(choose);

        CategoryItem active = new CategoryItem();
        active.setName("Aktif");

        categoryItems.add(active);

        CategoryItem passive = new CategoryItem();
        passive.setName("Pasif");

        categoryItems.add(passive);

        GeneralMethods.getInstance().fillCategorySpinner(getActivity(), status, categoryItems);

    }

    private void clear() {

        textContractFinishDate.setText("");
        textContractStartDate.setText("");
        textResFinishDate.setText("");
        textResStartDate.setText("");

        fillSalonSpinner();
        fillTypeSpinner();
        fillStatusSpinner();
        fillIsCertainSpinner();

        textBride.setText("");
        textGroom.setText("");
        textPrevalent.setText("");
        textContractNo.setText("");


    }

    private void search() {

        String sql = "select * from reservation t1,prevalent t2 where t1.prevalent=t2.id ";
        String conStart = textContractStartDate.getText().toString();
        String conFinish = textContractFinishDate.getText().toString();
        String resStart = textResStartDate.getText().toString();
        String resFinish = textResFinishDate.getText().toString();
        String conNo = textContractNo.getText().toString();
        String prevalent = textPrevalent.getText().toString();
        String bride = textBride.getText().toString();
        String groom = textGroom.getText().toString();


        if (!conNo.equals("")) sql += " and t1.contractNo=" + Integer.valueOf(conNo);
        if (!prevalent.equals("")) sql += " and t2.name like '%" + prevalent + "%'";
        if (!bride.equals("")) sql += " and t1.brideName like '%" + bride + "%'";
        if (!groom.equals("")) sql += " and t1.groomName like '%" + groom + "%'";
        if (!((KobaltInfo) salon.getSelectedItem()).getOwner().equals("Seçiniz"))
            sql += " and t1.kobilId=" + ((KobaltInfo) salon.getSelectedItem()).getKobilId();
        if (!((CategoryItem) type.getSelectedItem()).getName().equals("Seçiniz"))
            sql += " and t1.type=" + ((CategoryItem) type.getSelectedItem()).getId();
        if (!((CategoryItem) status.getSelectedItem()).getName().equals("Seçiniz")) {
            if (((CategoryItem) status.getSelectedItem()).getName().equals("Aktif"))
                sql += " and t1.status=1";
            else
                sql += " and t1.status=0";
        }
        if (!((CategoryItem) isCertain.getSelectedItem()).getName().equals("Seçiniz")) {
            if (((CategoryItem) isCertain.getSelectedItem()).getName().equals("Satış"))
                sql += " and t1.isCertain=1";
            else
                sql += " and t1.isCertain=0";
        }


        if (!conStart.equals("") && conFinish.equals("")) {
            String formConStart = GeneralMethods.getInstance().changeTime(formatDate(conStart));
            sql += " and t1.creationDate > ('" + formConStart + "')";
        }
        if (conStart.equals("") && !conFinish.equals("")) {
            String formConFinish = GeneralMethods.getInstance().changeTimeTo(formatDate(conFinish));
            sql += " and t1.creationDate < ('" + formConFinish + "')";
        }
        if (!conStart.equals("") && !conFinish.equals("")) {
            String formConStart = GeneralMethods.getInstance().changeTime(formatDate(conStart));
            String formConFinish = GeneralMethods.getInstance().changeTimeTo(formatDate(conFinish));
            sql += " and (t1.creationDate between '(" + formConStart + "' and '" + formConFinish + "')";
        }

        if (!resStart.equals("") && resFinish.equals("")) {
            String formConStart = GeneralMethods.getInstance().changeTime(formatDate(resStart));
            sql += " and t1.reservationDate > ('" + formConStart + "')";
        }
        if (resStart.equals("") && !resFinish.equals("")) {
            String formConFinish = GeneralMethods.getInstance().changeTimeTo(formatDate(resFinish));
            sql += " and t1.reservationDate < ('" + formConFinish + "')";
        }
        if (!resStart.equals("") && !resFinish.equals("")) {
            String formConStart = GeneralMethods.getInstance().changeTime(formatDate(resStart));
            String formConFinish = GeneralMethods.getInstance().changeTimeTo(formatDate(resFinish));
            sql += " and (t1.reservationDate between ('" + formConStart + "' and '" + formConFinish + "')";
        }

        System.out.println(sql);
        ResultSet resultSet = MySQLDAO.getInstance(getActivity()).getObject(sql);

        try {
            if (!resultSet.isBeforeFirst())
                Toast.makeText(getActivity(),
                        "Sonuç Bulunamadı !", Toast.LENGTH_SHORT)
                        .show();

            else {
                List<Reservation> list = new ArrayList<>();
                while (resultSet.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setReservationDate(resultSet.getDate("reservationDate"));
                    reservation.setStartDate(resultSet.getTimestamp("t1.startDate"));
                    reservation.setFinishDate(resultSet.getTimestamp("t1.finishDate"));
                    reservation.setId(resultSet.getInt("t1.id"));
                    reservation.setKobilId(resultSet.getInt("t1.kobilId"));
                    Prevalent prevalent1 = new Prevalent();
                    prevalent1.setName(resultSet.getString("name"));
                    prevalent1.setId(resultSet.getInt("t2.id"));
                    reservation.setPrevalent(prevalent1);
                    reservation.setNetAmount(resultSet.getDouble("t1.netAmount"));

                    String sqlType = "select * from categoryitem where id=" + resultSet.getInt("t1.type");
                    ResultSet resultSet1 = MySQLDAO.getInstance(getActivity()).getObject(sqlType);
                    CategoryItem categoryItem = new CategoryItem();
                    while (resultSet1.next()) {
                        categoryItem.setId(resultSet1.getInt("id"));
                        categoryItem.setName(resultSet1.getString("name"));
                    }
                    reservation.setType(categoryItem);
                    list.add(reservation);
                }

                Intent intent = new Intent(getActivity(), ReservationListActivity.class);
                intent.putExtra("idList", (Serializable) list);
                startActivity(intent);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private Date formatDate(String inputDate) {

        DateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy"); //Input date format

        Date date = null;
        try {
            date = originalFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private void fillIsCertainSpinner() {

        List<CategoryItem> categoryItems = new ArrayList<>();

        CategoryItem choose = new CategoryItem();
        choose.setName("Seçiniz");

        categoryItems.add(choose);

        CategoryItem active = new CategoryItem();
        active.setName("Satış");

        categoryItems.add(active);

        CategoryItem passive = new CategoryItem();
        passive.setName("Ön Rezervasyon");

        categoryItems.add(passive);

        GeneralMethods.getInstance().fillCategorySpinner(getActivity(), isCertain, categoryItems);
    }

    private void findViewsById() {
        textResStartDate = (EditText) rootView.findViewById(R.id.textResStartDate);
        textResStartDate.setInputType(InputType.TYPE_NULL);
        textResFinishDate = (EditText) rootView.findViewById(R.id.textResFinishDate);
        textResFinishDate.setInputType(InputType.TYPE_NULL);
        textContractStartDate = (EditText) rootView.findViewById(R.id.textContractStartDate);
        textContractStartDate.setInputType(InputType.TYPE_NULL);
        textContractFinishDate = (EditText) rootView.findViewById(R.id.textContractFinishDate);
        textContractFinishDate.setInputType(InputType.TYPE_NULL);
        salon = (Spinner) rootView.findViewById(R.id.spinnerSalon);
        type = (Spinner) rootView.findViewById(R.id.spinnerType);
        isCertain = (Spinner) rootView.findViewById(R.id.spinnerIsCertain);
        status = (Spinner) rootView.findViewById(R.id.spinnerStatus);
        clear = (Button) rootView.findViewById(R.id.buttonClear);
        textBride = (EditText) rootView.findViewById(R.id.textBride);
        textGroom = (EditText) rootView.findViewById(R.id.textGroom);
        textPrevalent = (EditText) rootView.findViewById(R.id.textPrevalent);
        textContractNo = (EditText) rootView.findViewById(R.id.textContractNo);
        search = (Button) rootView.findViewById(R.id.buttonSearch);

    }

    private void fillSalonSpinner() {
        KobaltInfo categoryItem = new KobaltInfo();
        categoryItem.setOwner("Seçiniz");

        List<KobaltInfo> salons = ActiveSettings.getInstance().getKobalts();


        for (KobaltInfo kobaltInfo : salons) {
            if (kobaltInfo.getOwner().equals("Seçiniz"))
                salons.remove(kobaltInfo);

        }

        salons.add(categoryItem);

        ArrayAdapter<KobaltInfo> adapter = new ArrayAdapter<KobaltInfo>(
                getActivity(), android.R.layout.simple_spinner_item, salons);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salon.setAdapter(adapter);
        salon.setSelection(salons.size() - 1);


    }

    private void fillTypeSpinner() {
        List<CategoryItem> categoryItems = new ArrayList<>();
        ResultSet resultSet = MySQLDAO.getInstance(getActivity()).getObject("select * from category where forWhichClazz=" + "\"" + "reservationType" + "\"");
        CategoryItem categoryItemss = new CategoryItem();
        categoryItemss.setName("Seçiniz");

        categoryItems.add(categoryItemss);

        if (resultSet != null)
            try {
                while (resultSet.next()) {
                    ResultSet resultSet1 = MySQLDAO.getInstance(getActivity()).getObject("select * from category_categoryitem where category_id=" + resultSet.getInt("id"));
                    while (resultSet1.next()) {
                        ResultSet resultSet2 = MySQLDAO.getInstance(getActivity()).getObject("select * from categoryitem where id=" + resultSet1.getInt("items_id"));
                        while (resultSet2.next()) {
                            CategoryItem categoryItem = new CategoryItem();
                            categoryItem.setId(resultSet2.getInt("id"));
                            categoryItem.setName(resultSet2.getString("name"));
                            categoryItems.add(categoryItem);

                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        GeneralMethods.getInstance().fillCategorySpinner(getActivity(), type, categoryItems);

    }

    private void setDateTimeField() {
        textResStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resStartDatePicker.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();


        resStartDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textResStartDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        textResFinishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resFinishDatePicker.show();
            }
        });

        resFinishDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textResFinishDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        textContractStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contractStartDatePicker.show();
            }
        });

        contractStartDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textContractStartDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        textContractFinishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contractFinishDatePicker.show();
            }
        });

        contractFinishDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textContractFinishDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }


}

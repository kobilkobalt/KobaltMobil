package com.example.kobiltekmobil.kobalt.salon.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.ActiveSettings;
import com.example.kobiltekmobil.kobalt.main.helper.GeneralMethods;
import com.example.kobiltekmobil.kobalt.main.entity.KobaltInfo;
import com.example.kobiltekmobil.kobalt.main.helper.MySQLDAO;
import com.example.kobiltekmobil.kobalt.salon.entity.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SalonCalendarFragment extends Fragment {


    Spinner spinnerMonth;
    Spinner spinnerSalon;
    ImageButton buttonYearBack;
    ImageButton buttonYearNext;
    EditText editYear;
    int currentYear;
    int currentMonth;
    private ProgressDialog pDialog;
    private ArrayList<String> salonList;
    HashMap<String, Integer> spinnerMap;
    private ResultSet resultSetReservations;
    private List<Reservation> reservations;

    private GestureDetector mGesture;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private View rootView;

    public SalonCalendarFragment() {
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
        rootView= inflater.inflate(R.layout.fragment_salon_calendar, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        /*final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                spinnerMonth.setSelection(spinnerMonth.getSelectedItemPosition() - 1);
                                currentMonth = spinnerMap.get(spinnerMonth.getSelectedItem().toString());
                                fillSalonButtons();
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                spinnerMonth.setSelection(spinnerMonth.getSelectedItemPosition() + 1);
                                currentMonth = spinnerMap.get(spinnerMonth.getSelectedItem().toString());
                                fillSalonButtons();
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);

                    }
                });

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });*/

        editYear = (EditText) rootView.findViewById(R.id.editYear);
        spinnerMonth = (Spinner) rootView.findViewById(R.id.spinnerMonth);
        spinnerSalon = (Spinner) rootView.findViewById(R.id.spinnerSalon);
        buttonYearBack = (ImageButton) rootView.findViewById(R.id.buttonYearBack);
        buttonYearNext = (ImageButton) rootView.findViewById(R.id.buttonYearNext);

        Calendar c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH);

        editYear.setText(String.valueOf(currentYear));

        fillMonthSpinner(currentMonth);
        GeneralMethods.getInstance().fillAllKobalts(getActivity());
        fillSalonSpinner();

        fillSalonButtons();
        initListeners();




        return rootView;
    }




    private void fillSalonButtons() {

        int daysCount = 1;
        int startDay;
        int starting = 0;

        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, Integer.valueOf(editYear.getText().toString()));
        cal.set(Calendar.MONTH, currentMonth); // 11 = december
        cal.set(Calendar.DAY_OF_MONTH, 1);
        daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        startDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);

        SimpleDateFormat formatter = new SimpleDateFormat("EEE", Locale.ENGLISH);
        String text = formatter.format(cal.getTime());
        if (text.equals("Mon"))
            starting = 1;
        else if (text.equals("Tue"))
            starting = 2;
        else if (text.equals("Wed"))
            starting = 3;
        else if (text.equals("Thu"))
            starting = 4;
        else if (text.equals("Fri"))
            starting = 5;
        else if (text.equals("Sat"))
            starting = 6;
        else if (text.equals("Sun"))
            starting = 7;

        for (int i = 1; i < 43; i++) {
            Resources res = getResources();
            int id = res.getIdentifier("button" + i, "id", getActivity().getPackageName());
            Button button = (Button) rootView.findViewById(id);
            button.setText("");

            button.setHeight(220);
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            button.setGravity(Gravity.TOP);

        }

        resultSetReservations = getReservationsOfGivenMonth();
        reservations = new ArrayList<>();
        if (resultSetReservations != null) {
            try {
                while (resultSetReservations.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(resultSetReservations.getInt("id"));
                    reservation.setCertain(resultSetReservations.getBoolean("isCertain"));
                    reservation.setStatus(resultSetReservations.getBoolean("status"));
                    reservation.setFinishDate(resultSetReservations.getTimestamp("finishDate"));
                    reservation.setStartDate(resultSetReservations.getTimestamp("startDate"));
                    reservation.setReservationDate(resultSetReservations.getDate("reservationDate"));
                    KobaltInfo salon = (KobaltInfo) spinnerSalon.getSelectedItem();
                    reservation.setKobilId(salon.getKobilId());
                    reservations.add(reservation);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i <= daysCount; i++) {

            Resources res = getResources();
            int id = res.getIdentifier("button" + starting, "id", getActivity().getPackageName());
            Button button = (Button) rootView.findViewById(id);
            button.setText(String.valueOf(i));
            button.setGravity(Gravity.TOP);
            button.setHeight(220);
            int day = i;
            final Calendar cal1 = new GregorianCalendar();
            cal1.set(Calendar.YEAR, Integer.valueOf(editYear.getText().toString()));
            cal1.set(Calendar.MONTH, currentMonth); // 11 = december
            cal1.set(Calendar.DAY_OF_MONTH, day);

            starting = starting + 1;


            int[] hourComparison = getWhichHour(i);

            if (hourComparison[0] == 1 && hourComparison[1] == 0) {
                button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.reserve1);
                System.out.println(hourComparison[0] + " " + hourComparison[1] + " sabah" + " " + i);
            }
            if (hourComparison[0] == 0 && hourComparison[1] == 1) {
                button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.reserve2);
                System.out.println(hourComparison[0] + " " + hourComparison[1] + " akşam" + " " + i);
            }
            if (hourComparison[0] == 1 && hourComparison[1] == 1) {
                button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.reserve);
                System.out.println(hourComparison[0] + " " + hourComparison[1] + " ikisi" + " " + i);
            }

            if (hourComparison[0] == 0 && hourComparison[1] == 0) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),
                                "Bu Tarihte Rezervasyon Bulunmamaktadır !", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            } else {

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ReservationListActivity.class);
                        intent.putExtra("day", cal1.getTime());
                        KobaltInfo salon = (KobaltInfo) spinnerSalon.getSelectedItem();
                        intent.putExtra("salon", salon);
                        startActivity(intent);
                    }
                });
            }


        }

    }


    private int getReservationsThisDate(int day) {
        int count = 0;
        if (resultSetReservations != null) {

            try {
                while (resultSetReservations.next()) {
                    boolean isStatus = resultSetReservations.getBoolean("isCertain");
                    if (isStatus) {
                        Date date1 = resultSetReservations.getDate("reservationDate");
                        Calendar cal = new GregorianCalendar();
                        cal.set(Calendar.YEAR, Integer.valueOf(editYear.getText().toString()));
                        cal.set(Calendar.MONTH, currentMonth); // 11 = december
                        cal.set(Calendar.DAY_OF_MONTH, day);
                        if (compareDates(date1, cal.getTime())) {
                            count++;
                        }

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    private int[] getWhichHour(int day) {
        int[] sabah = new int[2];

        for (Reservation reservation : reservations) {
            Date date1 = reservation.getReservationDate();
            Calendar cal = new GregorianCalendar();
            cal.set(Calendar.YEAR, Integer.valueOf(editYear.getText().toString()));
            cal.set(Calendar.MONTH, currentMonth); // 11 = december
            cal.set(Calendar.DAY_OF_MONTH, day);
            if (compareDates(date1, cal.getTime())) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(reservation.getStartDate());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour < 18) {
                    sabah[0] = 1;
                }
                if (hour >= 18)
                    sabah[1] = 1;
            }
        }

        return sabah;
    }

    private boolean compareDates(Date reservationDate, Date date) {
        boolean check = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(reservationDate);
        int yearS = c1.get(Calendar.YEAR);
        int monthS = c1.get(Calendar.MONTH);
        int dayS = c1.get(Calendar.DAY_OF_MONTH);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);
        int yearE = c2.get(Calendar.YEAR);
        int monthE = c2.get(Calendar.MONTH);
        int dayE = c2.get(Calendar.DAY_OF_MONTH);

        if (yearS == yearE && monthS == monthE && dayS == dayE)
            check = true;


        return check;
    }

    private ResultSet getReservationsOfGivenMonth() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, Integer.valueOf(editYear.getText().toString()));
        cal.set(Calendar.MONTH, currentMonth);
        String changeTimeTo = GeneralMethods.getInstance().changeTimeToForMonth(cal.getTime());
        String changeTime = GeneralMethods.getInstance().changeTimeForMonth(cal.getTime());
        KobaltInfo salon = (KobaltInfo) spinnerSalon.getSelectedItem();
        String sql = "select reservationDate,finishDate,startDate,id,kobilId,isCertain,status from reservation" +
                " where kobilId=" + salon.getKobilId() + " and (reservationDate between '" + changeTime + "' and '" + changeTimeTo + "') and status=true";
        ResultSet resultSet = MySQLDAO.getInstance(getActivity()).getObject(sql);
        return resultSet;
    }

    private void initListeners() {

        buttonYearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = Integer.valueOf(editYear.getText().toString());
                editYear.setText(String.valueOf(year - 1));
                fillSalonButtons();
            }
        });

        buttonYearNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = Integer.valueOf(editYear.getText().toString());
                editYear.setText(String.valueOf(year + 1));
                fillSalonButtons();
            }
        });

        editYear.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    fillSalonButtons();
                }
                return false;
            }
        });

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentMonth = spinnerMap.get(spinnerMonth.getSelectedItem().toString());
                fillSalonButtons();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        spinnerSalon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                fillSalonButtons();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    private void fillSalonSpinner() {


        List<KobaltInfo> salons = ActiveSettings.getInstance().getKobalts();

        ArrayAdapter<KobaltInfo> adapter = new ArrayAdapter<KobaltInfo>(
                getActivity(), android.R.layout.simple_spinner_item, salons);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSalon.setAdapter(adapter);


    }

    private void fillMonthSpinner(int month) {

        String[] monthNames = {"Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"};
        spinnerMap = new HashMap<String, Integer>();
        for (int i = 0; i < monthNames.length; i++) {
            spinnerMap.put(monthNames[i], i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, monthNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMonth.setAdapter(adapter);
        spinnerMonth.setSelection(month);

    }

    private String getNameOfMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }






}

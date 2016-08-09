package com.example.kobiltekmobil.kobalt.bordro.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.entity.CategoryItem;
import com.example.kobiltekmobil.kobalt.main.helper.GeneralMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BordroActivity extends AppCompatActivity {

    private Spinner type;
    private Spinner status;
    private SimpleDateFormat dateFormatter;
    private EditText creationStart;
    private EditText creationFinish;
    private EditText termStart;
    private EditText termFinish;
    private DatePickerDialog creationDatePicker;
    private DatePickerDialog termDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bordro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BORDRO TAKİBİ");

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();
        fillTypeSpinner();
        fillStatusSpinner();
    }

    private void setDateTimeField() {
        creationStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creationDatePicker.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();


        creationDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                creationStart.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        termStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                termDatePicker.show();
            }
        });

        termDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                termStart.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));




    }

    private void fillStatusSpinner() {

        List<CategoryItem> items=new ArrayList<>();

        CategoryItem itemOne = new CategoryItem();
        itemOne.setName("Seçiniz");
        items.add(itemOne);

        CategoryItem itemTwo = new CategoryItem();
        itemTwo.setName("Portföyde");
        items.add(itemTwo);

        CategoryItem itemThree = new CategoryItem();
        itemThree.setName("Tahsil Edildi");
        items.add(itemThree);

        CategoryItem itemFour = new CategoryItem();
        itemFour.setName("Teminata Verildi");
        items.add(itemFour);

        CategoryItem itemFive = new CategoryItem();
        itemFive.setName("Tahsile Verildi");
        items.add(itemFive);

        CategoryItem itemSix = new CategoryItem();
        itemSix.setName("Ciro Edildi");
        items.add(itemSix);

        CategoryItem itemSeven = new CategoryItem();
        itemSeven.setName("Parçalandı");
        items.add(itemSeven);

        CategoryItem itemEight = new CategoryItem();
        itemEight.setName("Karşılıksız");
        items.add(itemEight);



        GeneralMethods.getInstance().fillCategorySpinner(this,status,items);

    }

    private void findViewsById() {

        type=(Spinner)findViewById(R.id.spinnerType);
        status=(Spinner)findViewById(R.id.spinnerStatus);
        creationStart =(EditText) findViewById(R.id.textCreationDate);
        termStart =(EditText) findViewById(R.id.textTermDate);
        creationStart.setInputType(InputType.TYPE_NULL);
        termStart.setInputType(InputType.TYPE_NULL);
        termStart.setInputType(InputType.TYPE_NULL);
    }

    private void fillTypeSpinner() {

        List<CategoryItem> items=new ArrayList<>();

        CategoryItem itemOne = new CategoryItem();
        itemOne.setName("Seçiniz");
        items.add(itemOne);

        CategoryItem itemTwo = new CategoryItem();
        itemTwo.setName("Müşteri Senedi");
        items.add(itemTwo);

        CategoryItem itemThree = new CategoryItem();
        itemThree.setName("Müşteri Çeki");
        items.add(itemThree);

        CategoryItem itemFour = new CategoryItem();
        itemFour.setName("Kendi Senedim");
        items.add(itemFour);

        CategoryItem itemFive = new CategoryItem();
        itemFive.setName("Kendi Çekim");
        items.add(itemFive);

        GeneralMethods.getInstance().fillCategorySpinner(this,type,items);

    }
}

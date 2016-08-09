package com.example.kobiltekmobil.kobalt.salon.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.GeneralMethods;
import com.example.kobiltekmobil.kobalt.main.helper.MySQLDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kobiltekMobil on 3.08.2016.
 */


public class ReservationAccountancyFragment extends Fragment {

    private View rootView;
    private TextView textTotalAmount;
    private TextView textDiscount;
    private TextView textNetAmount;
    private TextView textCash;
    private TextView textBank;
    private TextView textBordro;


    public ReservationAccountancyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void fillView(int resId) {

        int paymentCheckId=0;
        int paymentDetailsId = 0;
        double totalCash=0;
        double totalCheck=0;
        double totalAmount=0;
        double discount=0;
        double netAmount=0;
        String bankText="";
        String sql="select discount,paymentdetails,netAmount,totalAmount from reservation where id="+resId;
        ResultSet resultSet=MySQLDAO.getInstance(getActivity()).getObject(sql);
        try {
            while (resultSet.next()){
                paymentDetailsId=resultSet.getInt("paymentdetails");
                totalAmount=resultSet.getDouble("totalAmount");
                netAmount=resultSet.getDouble("netAmount");
                discount=resultSet.getDouble("discount");
            }
            String sql1="select * from paymentdetails where id="+paymentDetailsId;
            ResultSet resultSet1 = MySQLDAO.getInstance(getActivity()).getObject(sql1);
            while (resultSet1.next()){
                paymentCheckId=resultSet1.getInt("paymentcheck");
            }
            String sql2="select * from paymentdetails_paymentcash where Paymentdetails_id="+paymentDetailsId;
            ResultSet resultSet2=MySQLDAO.getInstance(getActivity()).getObject(sql2);
            while (resultSet2.next()){
                totalCash+=resultSet2.getDouble("paymentCash");
            }
            String sql3="select * from paymentdetails_paymentBank where Paymentdetails_id="+paymentDetailsId;
            ResultSet resultSet3=MySQLDAO.getInstance(getActivity()).getObject(sql3);
            while (resultSet3.next()){
                String sql4="select * from bank where id="+resultSet3.getInt("paymentBank_KEY");
                ResultSet resultSet4=MySQLDAO.getInstance(getActivity()).getObject(sql4);
                while (resultSet4.next()){
                    String bankName=resultSet4.getString("name");
                    bankText+=" "+bankName;
                }
                double bankValue=resultSet3.getDouble("paymentBank");
                bankText+=" "+GeneralMethods.getInstance().setValue(bankValue);
            }
            String sql4="select * from paymentcheck_check where paymentcheck_id="+paymentCheckId;
            ResultSet resultSet4=MySQLDAO.getInstance(getActivity()).getObject(sql4);
            while (resultSet4.next()){
                String sql5="select balance from "+MySQLDAO.getInstance(getActivity()).getSchema()+".check where id="+resultSet4.getInt("checks_id");
                ResultSet resultSet5=MySQLDAO.getInstance(getActivity()).getObject(sql5);
                System.out.println(paymentCheckId);
                System.out.println("select balance from "+MySQLDAO.getInstance(getActivity()).getSchema()+".check where id="+resultSet4.getInt("checks_id"));
                while (resultSet5.next()){
                    totalCheck+=resultSet5.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        textBank.setText(bankText);
        textBordro.setText(GeneralMethods.getInstance().setValue(totalCheck));
        textCash.setText(  GeneralMethods.getInstance().setValue(totalCash));
        textTotalAmount.setText(GeneralMethods.getInstance().setValue(totalAmount));
        textNetAmount.setText(GeneralMethods.getInstance().setValue(netAmount));
        textDiscount.setText( GeneralMethods.getInstance().setValue(discount));



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootView = inflater.inflate(R.layout.fragment_reservation_accountancy, container, false);

        int resId = getActivity().getIntent().getExtras().getInt("id");

        findViewsById();
        fillView(resId);


        return rootView;
    }

    private void findViewsById() {

        textBank=(TextView)rootView.findViewById(R.id.textBank);
        textBordro=(TextView)rootView.findViewById(R.id.textBordro);
        textTotalAmount=(TextView)rootView.findViewById(R.id.textTotalAmount);
        textNetAmount=(TextView)rootView.findViewById(R.id.textNetAmount);
        textDiscount=(TextView)rootView.findViewById(R.id.textDiscount);
        textCash=(TextView)rootView.findViewById(R.id.textCash);

    }

}
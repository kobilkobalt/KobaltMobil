package com.example.kobiltekmobil.kobalt.salon.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.GeneralMethods;
import com.example.kobiltekmobil.kobalt.salon.entity.Reservation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by kobiltekMobil on 13.07.2016.
 */
public class AdapterReservationList extends BaseAdapter {
    private static List<Reservation> searchArrayList;

    private LayoutInflater mInflater;

    public AdapterReservationList(Context context, List<Reservation> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.reservation_row_view, null);
            holder = new ViewHolder();
            holder.prevalent = (TextView) convertView.findViewById(R.id.prevalent);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.reservationDate = (TextView) convertView.findViewById(R.id.reservationDate);
            holder.salon = (TextView) convertView.findViewById(R.id.salon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.prevalent.setText(searchArrayList.get(position).getPrevalent().getName());
        holder.type.setText(String.valueOf(searchArrayList.get(position).getType()));

        String date ="";
        date= GeneralMethods.getInstance().getFormattedDateWithoutTime("TR",searchArrayList.get(position).getReservationDate());
        DateFormat formato = new SimpleDateFormat("HH:mm");
        String hora = formato.format(searchArrayList.get(position).getStartDate());
        DateFormat formato1 = new SimpleDateFormat("HH:mm");
        String hora1 = formato1.format(searchArrayList.get(position).getFinishDate());
        date+=" "+hora;
        date+=" - "+hora1;

        holder.reservationDate.setText(date);

        holder.salon.setText(GeneralMethods.getInstance().setValue(searchArrayList.get(position).getNetAmount()));

        return convertView;
    }

    static class ViewHolder {
        TextView prevalent;
        TextView type;
        TextView reservationDate;
        TextView salon;
    }
}

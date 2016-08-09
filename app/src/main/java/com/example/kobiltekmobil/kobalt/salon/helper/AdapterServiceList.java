package com.example.kobiltekmobil.kobalt.salon.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kobiltekmobil.kobalt.R;
import com.example.kobiltekmobil.kobalt.main.helper.GeneralMethods;
import com.example.kobiltekmobil.kobalt.salon.entity.ReservationService;

import java.util.List;

/**
 * Created by kobiltekMobil on 13.07.2016.
 */
public class AdapterServiceList extends BaseAdapter {
    private static List<ReservationService> searchArrayList;

    private LayoutInflater mInflater;

    public AdapterServiceList(Context context, List<ReservationService> results) {
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
            convertView = mInflater.inflate(R.layout.reservation_row_service, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textName);
            holder.salePrice = (TextView) convertView.findViewById(R.id.textSalePrice);
            holder.amount = (TextView) convertView.findViewById(R.id.textAmount);
            holder.discount = (TextView) convertView.findViewById(R.id.textDiscount);
            holder.netAmount = (TextView) convertView.findViewById(R.id.textNetAmount);
            holder.description = (TextView) convertView.findViewById(R.id.textDescription);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(searchArrayList.get(position).getName());
        holder.salePrice.setText(GeneralMethods.getInstance().setValue(searchArrayList.get(position).getSalePrice()));
        holder.amount.setText(GeneralMethods.getInstance().setValue(searchArrayList.get(position).getAmount()));
        holder.discount.setText(GeneralMethods.getInstance().setValue(searchArrayList.get(position).getDiscount()));
        holder.netAmount.setText(GeneralMethods.getInstance().setValue(searchArrayList.get(position).getNetAmount()));
        holder.description.setText(String.valueOf(searchArrayList.get(position).getDescription()));

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView salePrice;
        TextView amount;
        TextView discount;
        TextView netAmount;
        TextView description;
    }
}

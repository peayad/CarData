package com.peayad.cardata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class CarListAdapter extends ArrayAdapter{

    Context context;
    ArrayList<CarItem> carList = new ArrayList<>();

    public CarListAdapter(Context context, ArrayList<CarItem> carList) {
        super(context, R.layout.single_row, carList);
        this.context = context;
        this.carList = carList;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_row, parent, false);

        TextView brandTV = (TextView) view.findViewById(R.id.brand_tv);
        TextView usedTV = (TextView) view.findViewById(R.id.is_used_tv);
        TextView yearTV = (TextView) view.findViewById(R.id.year_tv);

        brandTV.setText(carList.get(position).getBrand());
        usedTV.setText(String.valueOf(carList.get(position).getIsUsed()));
        yearTV.setText(String.valueOf(carList.get(position).getConstructionYear()));

        ToggleButton favBtn = (ToggleButton) view.findViewById(R.id.fav_btn);
        favBtn.setChecked(carList.get(position).getIsFav());
        favBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                carList.get(position).setFav(isChecked);
            }
        });

        return view;
    }
}

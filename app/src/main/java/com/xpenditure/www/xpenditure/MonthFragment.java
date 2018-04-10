package com.xpenditure.www.xpenditure;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {

    HorizontalBarChart horizontalBarChart;
    TextView mdatepicker;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    public MonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);
        //code idhar

        horizontalBarChart = (HorizontalBarChart) rootView.findViewById(R.id.barChart);
        mdatepicker = (TextView) rootView.findViewById(R.id.datePicker);
        Integer catCount = CountManager.returnCategoryCount();
        Log.v("E_VALUE", "Categories for bar : " + catCount);

        setData(catCount, 100);

        mdatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MonthFragment.this.getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String NameOfMonth = "";
                switch (month) {
                    
                    case  1:
                        NameOfMonth = "January";
                        break;
                    case  2:
                        NameOfMonth = "Febauary";
                        break;
                    case  3:
                        NameOfMonth = "March";
                        break;
                    case  4:
                        NameOfMonth = "April";
                        break;
                    case  5:
                        NameOfMonth = "May";
                        break;
                    case  6:
                        NameOfMonth = "June";
                        break;
                    case  7:
                        NameOfMonth = "July";
                        break;
                    case  8:
                        NameOfMonth = "August";
                        break;
                    case  9:
                        NameOfMonth = "September";
                        break;
                    case  10:
                        NameOfMonth = "October";
                        break;
                    case  11:
                        NameOfMonth = "November";
                        break;
                    case  12:
                        NameOfMonth = "December";
                        break;
                }

                String date = NameOfMonth + " " + year;
                mdatepicker.setText(date);
            }
        };

        return rootView;
    }

    public void setData(int count, int range) {
        ArrayList<BarEntry> yValue = new ArrayList<>();
        float Barwidth = 9f;
        float SpaceBar = 10f;

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            yValue.add(new BarEntry(i * SpaceBar, val));
        }
        BarDataSet set1;

        set1 = new BarDataSet(yValue, "Data Set1");
        BarData data = new BarData(set1);

        data.setBarWidth(Barwidth);
        horizontalBarChart.getLegend().setEnabled(false);
        horizontalBarChart.setClickable(false);
        horizontalBarChart.setDoubleTapToZoomEnabled(false);
        horizontalBarChart.setPinchZoom(true);
        horizontalBarChart.valuesToHighlight();
        horizontalBarChart.enableScroll();
        horizontalBarChart.getContentDescription();
        horizontalBarChart.animateY(3000, Easing.EasingOption.EaseInCubic);
        horizontalBarChart.setDrawGridBackground(false);

        horizontalBarChart.setData(data);

    }

}

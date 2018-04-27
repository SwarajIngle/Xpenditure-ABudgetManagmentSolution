package com.xpenditure.www.xpenditure;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerDataDisplay extends Fragment {

    Firebase mrefTitle;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
    SimpleDateFormat sdf_month = new SimpleDateFormat("MM");
    SimpleDateFormat sdf_day = new SimpleDateFormat("dd");
    String Date;
    String time;
    String Day;
    String Month;
    String Year;

    String uid;
    RecyclerView displayData;
    private DatabaseReference dataref;

    public RecyclerDataDisplay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_recycler_data_display, container, false);

        Bundle bundle = getArguments();

        String title = String.valueOf(bundle.getString("title"));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();
        mrefTitle = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Category/" + title );

        Calendar c = Calendar.getInstance();
        Date = simpleDateFormat.format(c.getTime());
        time = sdf.format((c.getTime()));
        Day = sdf_day.format((c.getTime()));
        Month = sdf_month.format((c.getTime()));
        Year = sdf_year.format((c.getTime()));

//           mdatepicker = (TextView) rootView.findViewById(R.id.datePicker);
//        Integer catCount = CountManager.returnCategoryCount();
//        Log.v("E_VALUE", "Categories for bar : " + catCount);
            RecyclerView displayData = (RecyclerView) rootView.findViewById(R.id.recyclerViewMonth);
            displayData.setHasFixedSize(true);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(RecyclerDataDisplay.this.getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            displayData.setLayoutManager(layoutManager);

       dataref = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/Category0/2018/04" + "/");
//        setData(catCount, 100);
//            mdatepicker.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Calendar cal = Calendar.getInstance();
//                    int year = cal.get(Calendar.YEAR);
//                    int month = cal.get(Calendar.MONTH);
//                    int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                    DatePickerDialog dialog = new DatePickerDialog(MonthFragment.this.getActivity(),
//                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                            onDateSetListener,
//                            year, month, day);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    dialog.show();
//
//                }
//            });
//
//
//            onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//                @Override
//
//                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                    month = month + 1;
//                    String NameOfMonth = "";
//                    switch (month) {
//
//                        case 1:
//                            NameOfMonth = "January";
//                            break;
//                        case 2:
//                            NameOfMonth = "Febauary";
//                            break;
//                        case 3:
//                            NameOfMonth = "March";
//                            break;
//                        case 4:
//                            NameOfMonth = "April";
//                            break;
//                        case 5:
//                            NameOfMonth = "May";
//                            break;
//                        case 6:
//                            NameOfMonth = "June";
//                            break;
//                        case 7:
//                            NameOfMonth = "July";
//                            break;
//                        case 8:
//                            NameOfMonth = "August";
//                            break;
//                        case 9:
//                            NameOfMonth = "September";
//                            break;
//                        case 10:
//                            NameOfMonth = "October";
//                            break;
//                        case 11:
//                            NameOfMonth = "November";
//                            break;
//                        case 12:
//                            NameOfMonth = "December";
//                            break;
//                    }
//
//                    String date = NameOfMonth + " " + year;
//                    mdatepicker.setText(date);
//
//
//
//
//
////                int categoryCount = countCategory();
////
////                for (int i = 0; i < categoryCount; i++) {
////                    Log.d("category", String.valueOf(i));
//////                    mrefcategorytitle = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Category/Category" + i + "/" + year + "/" + month);
////                    mrefcategorytitle = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Category/Category" + i);
////
////                    mrefcategorytitle.addChildEventListener(new ChildEventListener() {
////
////                        @Override
////                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                            String kuchto = dataSnapshot.getValue().toString();
////                            Log.d("E_VALUE", "kuchto" + kuchto);
////                            Log.d("EXPENSEYear", dataSnapshot.getKey());
////                            for (DataSnapshot year : dataSnapshot.getChildren()) {
////                                Log.d("EXPENSEMonth", year.getKey());
////                                for (DataSnapshot month : year.getChildren()) {
////                                    for (DataSnapshot expense : month.getChildren()) {
////                                        Map<String, String> expenses = month.getValue(Map.class);
////                                        Log.d("EXPENSENote", expenses.get("note"));
////                                        Log.d("EXPENSEAmount", expenses.get("entered"));
////                                        Log.d("EXPENSEDay", expenses.get("Day"));
////
////                                    }
////                                }
////                            }
////                        }
////
////                        @Override
////                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////
////                        }
////
////                        @Override
////                        public void onChildRemoved(DataSnapshot dataSnapshot) {
////
////                        }
////
////                        @Override
////                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
////
////                        }
////
////                        @Override
////                        public void onCancelled(FirebaseError firebaseError) {
////
////                        }
////
////                    });
//////                }
//////
//////            }
////
////                }
//                    ;
//                }
//            };

            return rootView;
        }


        @Override
        public void onStart() {
            super.onStart();
            FirebaseRecyclerAdapter<DisplayData,DisplayDataViewHolder> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<DisplayData, DisplayDataViewHolder>(
                            DisplayData.class,
                            R.layout.displaydatacards,
                            DisplayDataViewHolder.class,
                            dataref
                    ) {
                        @Override
                        protected void populateViewHolder(DisplayDataViewHolder viewHolder, DisplayData model, int position) {
                            viewHolder.setDay(model.getDay());
                            viewHolder.setNote(model.getNote());
                            viewHolder.setEntered(model.getEntered());
                        }
                    };
            try {
                displayData.setAdapter(firebaseRecyclerAdapter);
            }catch(Exception e){
            }
        }
        public static class DisplayDataViewHolder extends RecyclerView.ViewHolder{
            View Myview;
            public DisplayDataViewHolder(View itemView) {
                super(itemView);
                Myview= itemView;
            }
            public void setDay(String Day){
                TextView day = (TextView) Myview.findViewById(R.id.DateDisplay);
                day.setText(Day);
            }
            public void setNote(String note){
                TextView day = (TextView) Myview.findViewById(R.id.expenseTitleDisplay);
                day.setText(note);
            }
            public void setEntered(String entered){
                TextView day = (TextView) Myview.findViewById(R.id.expenseAmountDisplay);
                day.setText(entered);
            }
        };


    private int countCategory() {
        int countCategories = CountManager.returnCategoryCount();

        Log.v("E_VALUE", "countCategories" + countCategories);
        return countCategories;
    }

//    public void setData(int count, int range) {
//        ArrayList<BarEntry> yValue = new ArrayList<>();
//        float Barwidth = 9f;
//        float SpaceBar = 10f;
//
//        for (int i = 0; i < count; i++) {
//            float val = (float) (Math.random() * range);
//            yValue.add(new BarEntry(i * SpaceBar, val));
//        }
//        BarDataSet set1;
//
//        set1 = new BarDataSet(yValue, "Data Set1");
//        BarData data = new BarData(set1);
//
//        data.setBarWidth(Barwidth);
//        horizontalBarChart.getLegend().setEnabled(false);
//        horizontalBarChart.setClickable(false);
//        horizontalBarChart.setDoubleTapToZoomEnabled(false);
//        horizontalBarChart.setPinchZoom(true);
//        horizontalBarChart.valuesToHighlight();
//        horizontalBarChart.enableScroll();
//        horizontalBarChart.getContentDescription();
//        horizontalBarChart.animateY(3000, Easing.EasingOption.EaseInCubic);
//        horizontalBarChart.setDrawGridBackground(false);
//
//        horizontalBarChart.setData(data);
//
//    }


}



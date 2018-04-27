package com.xpenditure.www.xpenditure;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.android.gms.internal.zzs.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private PieChart pieChart;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button addMoney;
    private Button removeMoney;
    Firebase mref;
    Firebase mrefGoals;
    Firebase firebase;
    Firebase mrefcategory;
    Integer total_money;
    Integer Goals=0;
    TextView money;
    Toolbar toolbar;
    DatabaseReference databaseReference;
    FragmentTransaction fragmentTransaction;
    ActionBar actionBar = null;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        addMoney = (Button) rootView.findViewById(R.id.addMoney);
        removeMoney = (Button) rootView.findViewById(R.id.removeMoney);

//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        addMoney = (Button) rootView.findViewById(R.id.addMoney);
        removeMoney = (Button) rootView.findViewById(R.id.removeMoney);

        pieChart = (PieChart) rootView.findViewById(R.id.pieChart);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    pieChart.setUsePercentValues(true);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setExtraOffsets(5, 10, 5, 5);
                    pieChart.getLegend().setEnabled(false);
                    pieChart.setDragDecelerationFrictionCoef(0.95f);
                    pieChart.setDrawHoleEnabled(true);
                    pieChart.setHoleRadius(25f);
                    pieChart.setHoleColor(Color.WHITE);
                    pieChart.setTransparentCircleRadius(50f);

                    user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    firebase = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<Integer, Integer> map = dataSnapshot.getValue(Map.class);
                            int countCat = map.get("Category Count");


                            Log.v("E_VALUE", "Category Count " + countCat);

                            CountManager.getCountCategories(countCat);

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    Map<String, String> map = new Map<String, String>() {
                        @Override
                        public int size() {
                            return 0;
                        }

                        @Override
                        public boolean isEmpty() {
                            return false;
                        }

                        @Override
                        public boolean containsKey(Object o) {
                            return false;
                        }

                        @Override
                        public boolean containsValue(Object o) {
                            return false;
                        }

                        @Override
                        public String get(Object o) {
                            return null;
                        }

                        @Override
                        public String put(String s, String s2) {
                            return null;
                        }

                        @Override
                        public String remove(Object o) {
                            return null;
                        }

                        @Override
                        public void putAll(Map<? extends String, ? extends String> map) {

                        }

                        @Override
                        public void clear() {

                        }

                        @NonNull
                        @Override
                        public Set<String> keySet() {
                            return null;
                        }

                        @NonNull
                        @Override
                        public Collection<String> values() {
                            return null;
                        }

                        @NonNull
                        @Override
                        public Set<Entry<String, String>> entrySet() {
                            return null;
                        }
                    };

                    int categoryCount = countCategory();
                    final ArrayList<PieEntry> yValues = new ArrayList<>();
                    for (int i = 0; i < categoryCount; i++) {
                        mrefcategory = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Category/Category" + i);
                        mrefcategory.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.

                                Map<String, String> map = dataSnapshot.getValue(Map.class);
                                String Title = map.get("Title");
                                if (Title == null) {

                                } else {
                                    yValues.add(new PieEntry(15f, Title));
                                    Log.d(TAG, "Title is: " + Title);
                                    pieChart.animateY(5000, Easing.EasingOption.EaseInOutBack);
//                        pieChart.animateX(5000, Easing.EasingOption.EaseInBounce);

                                    PieDataSet dataSet = new PieDataSet(yValues, "Titles");
                                    dataSet.setSliceSpace(3f);
                                    dataSet.setSelectionShift(15f);
                                    dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                                    PieData data = new PieData((dataSet));
                                    data.setValueTextSize(10f);
                                    data.setValueTextColor(Color.WHITE);

                                    pieChart.setData(data);
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });


                    }

//        yValues.add(new PieEntry(15f, "Category2"));
//        Log.v("E_VALUE", "Title: " + yValues);
//        yValues.add(new PieEntry(15f, "Category3"));
//        Log.v("E_VALUE", "Title: " + yValues);
//        yValues.add(new PieEntry(15f, "Category4"));
//        Log.v("E_VALUE", "Title: " + yValues);
//        yValues.add(new PieEntry(15f, "Category5"));
//        Log.v("E_VALUE", "Title: " + yValues);
//        yValues.add(new PieEntry(15f, "Category6"));
//        Log.v("E_VALUE", "Title: " + yValues);


                    mref = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Total");


                    mref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            total_money = dataSnapshot.getValue(Integer.class);

                            money = (TextView) rootView.findViewById(R.id.money);
                            money.setText("Rs. " + total_money.toString());



                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });




                    addMoney.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddMoneyFragment addMoneyFragment = new AddMoneyFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayout, addMoneyFragment);

                            fragmentTransaction.commit();

                        }
                    });

                    removeMoney.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RemoveMoneyFragment removeMoneyFragment = new RemoveMoneyFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayout, removeMoneyFragment);

                            fragmentTransaction.commit();

                        }
                    });

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    LoginFragment loginFragment = new LoginFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, loginFragment);
//                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Login");

                }
                // ...

            }
        };





            return rootView;
    }

    private int countCategory() {
        int countCategories = CountManager.returnCategoryCount();

        Log.v("E_VALUE", "countCategories" + countCategories);
        return countCategories;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}
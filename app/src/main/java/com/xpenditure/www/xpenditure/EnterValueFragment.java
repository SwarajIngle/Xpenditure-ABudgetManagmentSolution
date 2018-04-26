package com.xpenditure.www.xpenditure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterValueFragment extends Fragment {
    TextView Cat_name;
    EditText Value_edit;
    EditText Notes;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
    SimpleDateFormat sdf_month = new SimpleDateFormat("MM");
    SimpleDateFormat sdf_day = new SimpleDateFormat("dd");
    String note;
    int Value;
    int Expenses;
    String Entered;
    Firebase mrefTitle;
    Firebase mrefTotal;
    Firebase mrefExpenses;
    Firebase mrefcategorytitle;
    Firebase exp_Reference_Day;
    Firebase exp_Reference_Month;
    Firebase exp_Reference_Year;
    Button Spent;
    Integer total;
    String Date;
    String time;
    String Day;
    String totalExpense = "Total Expense";
    String Month;
    String Year;
    String uid;
    Integer MonthExpenseCount = 0;
    ;
    Integer yExpense;
    String title, selected;
    int Day_expenses;
    int Month_expenses_count;
    int Year_expenses;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enter_value, container, false);

        Cat_name = (TextView) rootView.findViewById(R.id.selected_category);
        Notes = (EditText) rootView.findViewById(R.id.Notes_edit);
        Value_edit = (EditText) rootView.findViewById(R.id.Value_edit);
        Spent = (Button) rootView.findViewById(R.id.button_spent);

        Date = simpleDateFormat.format(c.getTime());
        time = sdf.format((c.getTime()));
        Day = sdf_day.format((c.getTime()));
        Month = sdf_month.format((c.getTime()));
        Year = sdf_year.format((c.getTime()));



        Bundle bundle = getArguments();

        title = String.valueOf(bundle.getString("title"));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        uid = user.getUid();
        rejkrdg();
        mrefTotal = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Total");
        mrefTotal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mrefTitle = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Category/" + title + "/Title");
        Spent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Entered = Value_edit.getText().toString().trim();
                Value = Integer.parseInt(Entered);
                note = Notes.getText().toString().trim();
                total = total - Value;
                if (TextUtils.isEmpty(note)) {
                    //eamil is empty
                    Toast.makeText(EnterValueFragment.this.getActivity(), "Please enter Note", Toast.LENGTH_SHORT).show();
                    return;
                }

                mrefTotal.setValue(total);
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title);
                mrefcategorytitle = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Category/" + title);


                mrefcategorytitle.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot yeardata : dataSnapshot.getChildren()) {

//                            if (yeardata.child(Year).exists()) {
//                                final Firebase mrefYear = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Category/" + title + "/" + Year);
//                                //exists year data yaha pe ayga
////                                        for (DataSnapshot monthData : dataSnapshot.getChildren()) {
////                                            if (monthData.child(Month).exists()) {
////                                                final DatabaseReference month_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month);
////                                                DatabaseReference entry_expnode = month_Reference.child("Expense3");
////                                                entry_expnode.child("Amount:").setValue(Entered);
////                                                entry_expnode.child("Notes:").setValue(note);
////                                                entry_expnode.child("Day").setValue(Day);
////                                            } else {
////                                                final DatabaseReference year_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year);
////                                                DatabaseReference entry_month = year_Reference.child(Month);
////                                                entry_month.child(Month);
////                                                entry_month.child("MonthExpenseCount").setValue(1);
////                                                entry_month.child("MonthExpenseTotal").setValue(0);
////
////                                                final DatabaseReference month_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month);
////                                                DatabaseReference entry_expnode = month_Reference.child("Expense2");
////                                                entry_expnode.child("Amount:").setValue(Entered);
////                                                entry_expnode.child("Notes:").setValue(note);
////
////
////                                            }
////                                        }
//                                    } else {

                                DatabaseReference entry = databaseReference.child(Year);
                                entry.child(Year);
                                databaseReference.child("YearExpenseTotal").setValue(0);
                                entry.child(Year);
                                final DatabaseReference year_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year);
                                DatabaseReference entry_month = year_Reference.child(Month);
                                entry_month.child(Month);
                                entry_month.child("MonthExpenseCount").setValue(1);
                                entry_month.child("MonthExpenseTotal").setValue(0);

                                final DatabaseReference month_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month);
                                DatabaseReference entry_expnode = month_Reference.child("Expense1");
                            DatabaseReference mRef = month_Reference.push();

                            mRef.child("entered").setValue(Entered);
                            mRef.child("note").setValue(note);



//                            }
                        }
//                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                Toast.makeText(EnterValueFragment.this.getActivity(), "Successfully added the Record", Toast.LENGTH_SHORT).show();
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, homeFragment);
                fragmentTransaction.commit();

            }


        });


        return rootView;
    }

    public void rejkrdg(){
        mrefcategorytitle = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Category/" + title);
        mrefcategorytitle.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("EXPENSE1", dataSnapshot.getKey());
                for(DataSnapshot year : dataSnapshot.getChildren()) {
                    Log.d("EXPENSE1", year.getKey());
                    for(DataSnapshot month: year.getChildren()){
                        for(DataSnapshot expense: month.getChildren()){
                            Map<String, String> expenses = month.getValue(Map.class);
                            Log.d("EXPENSE1", expenses.get("note"));
                            Log.d("EXPENSE1", expenses.get("entered"));
                        }
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
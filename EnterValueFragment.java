package com.xpenditure.www.xpenditure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EnterValueFragment extends Fragment {
    TextView Cat_name;
    EditText Value_edit;
    EditText Notes;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
    SimpleDateFormat sdf_month = new SimpleDateFormat("MM");
    SimpleDateFormat sdf_day = new SimpleDateFormat("dd");
    String note;
    int Value;
    int Expenses;
    String Entered;
    Firebase mref;
    Firebase mref1;
    Firebase mref2;
    Firebase mref3;
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
    String title,selected;
    int Day_expenses;
    int Month_expenses;
    int Year_expenses;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enter_value,container,false);
        Cat_name= (TextView)rootView.findViewById(R.id.selected_category);
        Notes = (EditText)rootView.findViewById(R.id.Notes_edit);
        Value_edit = (EditText) rootView.findViewById(R.id.Value_edit);
        Spent = (Button)rootView.findViewById(R.id.button_spent);
        Date = simpleDateFormat.format(c.getTime());
        time = sdf.format((c.getTime()));
        Day = sdf_day.format((c.getTime()));
        Month = sdf_month.format((c.getTime()));
        Year = sdf_year.format((c.getTime()));
        Bundle bundle = getArguments();
        title= String.valueOf(bundle.getString("title"));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mref1 = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Total");
        mref = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Category/"+title+"/Title");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                selected= dataSnapshot.getValue(String.class);
                Cat_name.setText(selected);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        mref2 = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Category/"+title+"/Expenses");
        mref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Expenses = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        Spent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entered = Value_edit.getText().toString().trim();
                Value = Integer.parseInt(Entered);
                note = Notes.getText().toString().trim();
                total=total-Value;
                mref1.setValue(total);
                Expenses++;
                mref2.setValue(Expenses);
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users/"+uid+"/Category/"+title);
                mref3 = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Category/"+title);
                mref3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()) {
                            if (data.child(Year).exists()) {
                                final DatabaseReference year_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year);
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    if (dataSnapshot1.child(Month).exists()) {
                                        final DatabaseReference month_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month);
                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                            if (dataSnapshot2.child(Day).exists()) {
                                                final DatabaseReference day_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month + "/" + Day);
                                                for (DataSnapshot dataSnapshot3 : dataSnapshot.getChildren()) {
                                                    if (dataSnapshot3.child(totalExpense).exists()) {
                                                        exp_Reference_Day = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/" + uid + "/Category/" + title + "/" + Year + "/" + Month + "/" + Day + "/" + totalExpense);
                                                        exp_Reference_Day.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Day_expenses = dataSnapshot.getValue(Integer.class);
                                                                Day_expenses += Value;
                                                            }

                                                            @Override
                                                            public void onCancelled(FirebaseError firebaseError) {

                                                            }
                                                        });
                                                       exp_Reference_Day.setValue(Day_expenses);
                                                    } else {
                                                    DatabaseReference dref2 = day_Reference.child(totalExpense);
                                                    dref2.child(totalExpense).setValue(Entered);
                                                    day_Reference.child("Total Expenses").setValue(Entered);
                                                    }
                                                    DatabaseReference entry = day_Reference.child(time);
                                                    entry.child(time);
                                                    final DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month + "/" + Day + "/" + time);
                                                    dref.child("Amount:").setValue(Entered);
                                                    dref.child("Notes:").setValue(note);
                                              }
                                            }
                                            else {
                                                DatabaseReference entry = month_Reference.child(Day);
                                                entry.child(Day);
                                                final DatabaseReference day_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month + "/" + Day);
                                                DatabaseReference entry_time = day_Reference.child(time);
                                                entry_time.child(time);
                                                final DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month + "/" + Day + "/" + time);
                                                dref.child("Amount:").setValue(Entered);
                                                dref.child("Notes:").setValue(note);
                                            }
                                        }
                                    }
                                    else {
                                        DatabaseReference entry = year_Reference.child(Month);
                                        entry.child(Month);
                                        final DatabaseReference month_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month);
                                        DatabaseReference entry_day = month_Reference.child(Day);
                                        entry_day.child(Day);
                                        final DatabaseReference day_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month + "/" + Day);
                                        DatabaseReference entry_time = day_Reference.child(time);
                                        entry_time.child(time);
                                        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month + "/" + Day + "/" + time);
                                        dref.child("Amount:").setValue(Entered);
                                        dref.child("Notes:").setValue(note);
                                    }
                                }
                            } else {
                                DatabaseReference entry = databaseReference.child(Year);
                                entry.child(Year);
                                final DatabaseReference year_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year);
                                DatabaseReference entry_month = year_Reference.child(Month);
                                entry_month.child(Month);
                                final DatabaseReference month_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month);
                                DatabaseReference entry_day = month_Reference.child(Day);
                                entry_day.child(Day);
                                final DatabaseReference day_Reference = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month + "/" + Day);
                                DatabaseReference entry_time = day_Reference.child(time);
                                entry_time.child(time);
                                final DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/Category/" + title + "/" + Year + "/" + Month + "/" + Day + "/" + time);
                                dref.child("Amount:").setValue(Entered);
                                dref.child("Notes:").setValue(note);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                Toast.makeText(EnterValueFragment.this.getActivity(),"Successfully added the Record", Toast.LENGTH_SHORT).show();
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, homeFragment);
                fragmentTransaction.commit();

            }

        });

        return rootView;
    }
}


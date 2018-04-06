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
    String note;
    int Value;
    int Expenses;
    Integer goals;
    String Entered;
    Firebase mref;
    Firebase mref1;
    Firebase mref2;
    Firebase mref3;
    Firebase mrefgoals;
    Button Spent;
    Integer total;
    String Date;
    String time;
    String uid;
    String title,selected;
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
        Bundle bundle = getArguments();
        title= String.valueOf(bundle.getString("title"));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mref1 = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Total");
        mrefgoals = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Goals");
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
                mrefgoals.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        goals = dataSnapshot.getValue(Integer.class);
                        if (total < goals){
                            Toast.makeText(EnterValueFragment.this.getActivity(),"Goals Amount Exceded!!", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                mref1.setValue(total);
                Expenses++;
                mref2.setValue(Expenses);

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users/"+uid+"/Category/"+title);
                mref3 = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Category/"+title);
                mref3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            if(data.child(Date).exists()){
                                final DatabaseReference dateReference = FirebaseDatabase.getInstance().getReference().child("users/"+uid+"/Category/"+title+"/"+Date);
                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                    if(dataSnapshot1.child(time).exists()){
                                        final  DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("users/"+uid+"/Category/"+title+"/"+Date+"/"+time);
                                        dref.child("Amount:").setValue(Entered);
                                        dref.child("Notes:").setValue(note);
                                        break;
                                    }
                                }
                                break;
                            }
                            else {
                                DatabaseReference entry = databaseReference.child(Date);
                                entry.child(time);
                                final  DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("users/"+uid+"/Category/"+title+"/"+Date+"/"+time);
                                dref.child("Amount:").setValue(Entered);
                                dref.child("Notes:").setValue(note);


                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                Toast.makeText(EnterValueFragment.this.getActivity(),"Transaction Complete", Toast.LENGTH_SHORT).show();
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


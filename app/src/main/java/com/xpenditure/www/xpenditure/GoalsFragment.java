package com.xpenditure.www.xpenditure;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.xpenditure.www.xpenditure.R.id.updateGoalsButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoalsFragment extends Fragment {

    EditText goalsEditText;
    Button goalsUpdatebutton;
    Firebase mref;
    Integer goals;



    public GoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_goals, container, false);

        goalsEditText = (EditText) rootView.findViewById(R.id.goals);
        goalsUpdatebutton = (Button) rootView.findViewById(updateGoalsButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mref  = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Goals");
        final DatabaseReference myRef = database.getReference("Goals");

        goalsUpdatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goalsAmt = goalsEditText.getText().toString();
                Integer goals = Integer.parseInt(goalsAmt);
                if (TextUtils.isEmpty(goalsAmt)) {
                    //eamil is empty
                    Toast.makeText(GoalsFragment.this.getActivity(), " Enter Goal Amount! ", Toast.LENGTH_SHORT).show();
                    return;
                }

                mref.setValue(goals);
                Toast.makeText(GoalsFragment.this.getActivity(), " Goals Updated! ", Toast.LENGTH_SHORT).show();
                transaction();
            }
        });
        return rootView;    }

    private void transaction() {


        DisplayGoalsFragment diaplaygoals = new DisplayGoalsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, diaplaygoals);
        fragmentTransaction.commit();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Goals");


    }

}

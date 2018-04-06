package com.xpenditure.www.xpenditure;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayGoalsFragment extends Fragment {
    TextView displaygoals;
    Button updateGoals;
    Firebase mref;
    Integer goalsdisplay;


    public DisplayGoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_display_goals, container, false);
        // Inflate the layout for this fragment

        displaygoals = (TextView) rootView.findViewById(R.id.display_goals);
        updateGoals = (Button) rootView.findViewById(R.id.updateGoalsButton);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mref = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Goals");

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                goalsdisplay = dataSnapshot.getValue(Integer.class);

                displaygoals.setText("Rs. " + goalsdisplay.toString());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        updateGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                mref = new Firebase("https://xpenditure-7d2a5.firebaseio.com/users/"+uid+"/Goals");

                GoalsFragment goalsFragment = new GoalsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, goalsFragment);
                fragmentTransaction.commit();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Set Goals");
            }
        });

        return rootView;
    }

}

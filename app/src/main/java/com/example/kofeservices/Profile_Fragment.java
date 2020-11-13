package com.example.kofeservices;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment {

    TextView tvname,tvemail,tvphone,tvName;
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    ImageView imglogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.profile_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        tvname=view.findViewById(R.id.idtvname);
        tvName=view.findViewById(R.id.iddname);
        tvemail=view.findViewById(R.id.idtvemail);
        tvphone=view.findViewById(R.id.idtvphone);
        imglogout=view.findViewById(R.id.idimglogout);
        return view;
    }

    Userclass userclass =new Userclass();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new Login_fragment());
                fragmentTransaction.commit();

            }
        });


        reference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait...", "Proccessing...", true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                  for (DataSnapshot Snapshot: dataSnapshot.getChildren())
                {
                    userclass=Snapshot.getValue(Userclass.class);
                    tvName.setText(userclass.getName());
                    tvname.setText(userclass.getName());
                    tvemail.setText(userclass.getEmail());
                    tvphone.setText(userclass.getPhonenumb());


                }
                  progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}

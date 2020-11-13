package com.example.kofeservices;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class BookingFragment extends Fragment {

    String usersname,usersemail;
    EditText edtdescription,edtmessage,edtweburl;
    Button btngetbooking;

    TextView edtdate;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    CheckBox checkBox_Marketing_Strategies,checkBox_General_Consultation;
    CheckBox checkBox_SEO,checkBox_websecurity;

    String services="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.booking_fragment, container, false);

        edtdate=view.findViewById(R.id.idedtdate);
        edtweburl=view.findViewById(R.id.idedtweburl);
        edtdescription=view.findViewById(R.id.idedtdesc);
        edtmessage=view.findViewById(R.id.idedtmessage);
        checkBox_Marketing_Strategies=view.findViewById(R.id.checkBoxMarketingStrategies);
        checkBox_General_Consultation=view.findViewById(R.id.checkBoxGeneralConsultation);
        checkBox_SEO=view.findViewById(R.id.checkBoxSearchengineoptimisation);
        checkBox_websecurity=view.findViewById(R.id.checkBoxSecurityWebDesignanddevelopment);
        btngetbooking=view.findViewById(R.id.idbuttonbooking);
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseReference reference2;
        reference2 = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait...", "Proccessing...", true);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot Snapshot: dataSnapshot.getChildren())
                {
                    Userclass userclass =Snapshot.getValue(Userclass.class);
                    usersname=userclass.getName();
                    usersemail=userclass.getEmail();

                }
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });


        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBookingDate();
            }
        });
        btngetbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtweburl.getText()) ||TextUtils.isEmpty(edtdate.getText()) || TextUtils.isEmpty(edtdescription.getText()) || TextUtils.isEmpty(edtmessage.getText()))
                {
                    Toast.makeText(getContext(), "Please enter info", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(checkBox_Marketing_Strategies.isChecked()){

                        services=services+"Marketing Strategies ";
                    }
                    if(checkBox_General_Consultation.isChecked()){
                        services=services+" General Consultation ";
                    }
                    if(checkBox_SEO.isChecked()){
                        services=services+" Search Engine Optimisation";
                    }
                    if(checkBox_websecurity.isChecked()){
                        services=services+"  Security Web Design and Development ";
                    }
                    final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait...", "Proccessing...", true);
                    reference = FirebaseDatabase.getInstance().getReference("Bookings");
                    String key=reference.push().getKey();
                    BookingClass bookingClass = new BookingClass(key,mAuth.getUid(),edtdate.getText().toString(),edtdescription.getText().toString(),edtweburl.getText().toString(),services,edtmessage.getText().toString(),usersname,usersemail);
                    reference.child(key).child(mAuth.getUid()).setValue(bookingClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            edtweburl.setText("");
                            edtdescription.setText("");
                            edtmessage.setText("");
                            services="";
                            Toast.makeText(getContext(), "Booking Successfull", Toast.LENGTH_SHORT).show();

                        }


                    });


                }

            }
        });
    }



    public void setBookingDate() {
        Calendar calendar=Calendar.getInstance();
        final Calendar calendar1=Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                   edtdate.setText(year+"-"+month+"-"+dayOfMonth);
                   edtdate.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }



}

package com.example.kofeservices;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Register_Fragment extends Fragment {

    TextView tvlogin;
    ImageView imback;
    EditText edtname,edtemail,edtphone,edtpass;
    Button btnregister;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.register_fragment, container, false);
        edtname=view.findViewById(R.id.idedtname);
        edtemail=view.findViewById(R.id.idedtemail);
        edtphone=view.findViewById(R.id.idedtphone);
        edtpass=view.findViewById(R.id.idedtpass);
        btnregister=view.findViewById(R.id.idbtnregister);
        tvlogin=view.findViewById(R.id.idtvlogin);
        imback=view.findViewById(R.id.idimgbacl);
        mAuth = FirebaseAuth.getInstance();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        imback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new Login_fragment());
                fragmentTransaction.commit();
            }
        });
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new Login_fragment());
                fragmentTransaction.commit();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtname.getText()) || TextUtils.isEmpty(edtphone.getText()) || TextUtils.isEmpty(edtemail.getText())  || TextUtils.isEmpty(edtpass.getText())){
                    Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                }else
                {
                    if ( edtpass.length() < 9)
                    {
                        Toast.makeText(getContext(), "Your pass is min then the 9 digits", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait...", "Proccessing...", true);
                        mAuth.createUserWithEmailAndPassword(edtemail.getText().toString(),edtpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Userclass userClass = new Userclass(mAuth.getUid(),edtname.getText().toString(),edtphone.getText().toString(),edtemail.getText().toString(),edtpass.getText().toString());
                                    reference = FirebaseDatabase.getInstance().getReference("Users");
                                    String key=reference.push().getKey();
                                    reference.child(mAuth.getUid()).child(key).setValue(userClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "Register User Successfull", Toast.LENGTH_SHORT).show();
                                            FragmentManager fragmentManager;
                                            FragmentTransaction fragmentTransaction;
                                            fragmentManager = getFragmentManager();
                                            fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(android.R.id.content, new Login_fragment());
                                            fragmentTransaction.commit();
                                        }
                                    });

                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "User  Not Registered Successfull", Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
                    }


                }
            }
        });
    }
}

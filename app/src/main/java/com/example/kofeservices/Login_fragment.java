package com.example.kofeservices;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Login_fragment extends Fragment {
    TextView tvregister;
    EditText edtemail,edtpass;
    Button btnlogin;
    String adminemail="admin332211";
    String adminpass="kofeservices332211";
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.login_fragment, container, false);
       edtemail=view.findViewById(R.id.idedtemail);
       edtpass=view.findViewById(R.id.idedtpass);
       btnlogin=view.findViewById(R.id.idbuttonlogin);
        tvregister=view.findViewById(R.id.idtvregister);
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtemail.getText().toString()) || TextUtils.isEmpty(edtpass.getText().toString()))
                {
                    Toast.makeText(getContext(), "Enter Email and Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (edtemail.getText().toString().equals(adminemail) && edtpass.getText().toString().equals(adminpass))
                    {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(android.R.id.content, new AdminDashboard_Fragment());
                        fragmentTransaction.commit();

                    }else {

                        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait...", "Proccessing...", true);
                        mAuth.signInWithEmailAndPassword(edtemail.getText().toString(), edtpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
//
                                    Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                    FragmentManager fragmentManager;
                                    FragmentTransaction fragmentTransaction;
                                    fragmentManager = getFragmentManager();
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(android.R.id.content, new UserDashboard_Fragment());
                                    fragmentTransaction.commit();


                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Not Successfully Login", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }


                }

            }
        });

        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new Register_Fragment());
                fragmentTransaction.commit();
            }
        });
    }

}

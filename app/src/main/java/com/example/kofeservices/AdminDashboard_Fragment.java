package com.example.kofeservices;

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

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AdminDashboard_Fragment extends Fragment {

    private FirebaseAuth mAuth;
    DatabaseReference reference;
    ArrayList<BookingClass> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    SearchView searchView;
    BookingsAdapter adapter;
    ImageView imglogout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.admin_dashboard_fragment, container, false);
        recyclerView=view.findViewById(R.id.idrvbookings);
        searchView= view.findViewById(R.id.idsearchview);
        imglogout=view.findViewById(R.id.idimglogout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        DatabaseReference reference2;
        reference2 = FirebaseDatabase.getInstance().getReference("Bookings");
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait...", "Proccessing...", true);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot Snapshot: dataSnapshot.getChildren())
                {
                    for (DataSnapshot Snapshot2: Snapshot.getChildren()) {
                        arrayList.add(Snapshot2.getValue(BookingClass.class));
                    }
                }
                progressDialog.dismiss();
                 adapter=new BookingsAdapter(arrayList,getContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, new Login_fragment());
                fragmentTransaction.commit();

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText=newText.toLowerCase();
                ArrayList<BookingClass> filteredlist=new ArrayList<>();

                for (BookingClass item :arrayList) {
                    String username=item.getUsersname().toLowerCase();
                    String useremail=item.getUseremail().toLowerCase();
                    String weburl=item.getBookingweburl().toLowerCase();
                    if (username.contains(newText) || useremail.contains(newText) || weburl.contains(newText) ) {
                        filteredlist.add(item);
                    }
                }
                adapter=new BookingsAdapter(filteredlist,getContext());
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

    }

    public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder>{


        Dialog myDialog;
        Context context;
        ArrayList<BookingClass> aList;

        public BookingsAdapter(ArrayList<BookingClass> aList, Context context)
        {
            this.aList=aList;
            this.context=context;
        }
        @NonNull
        @Override
        public BookingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_rv_bookings,viewGroup,false);
            return new BookingsAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final  BookingsAdapter.MyViewHolder myViewHolder, final int i) {




            myViewHolder.tvdate.setText(aList.get(i).getBookingdate());
            myViewHolder.tvweburl.setText(aList.get(i).getBookingweburl());
            myViewHolder.tvservices.setText(aList.get(i).getBookingservices());
            myViewHolder.tvdesc.setText(aList.get(i).getBookingdescription());
            myViewHolder.tvmessage.setText(aList.get(i).getBookingmessage());
            myViewHolder.tvusersname.setText(aList.get(i).getUsersname());
            myViewHolder.useremail.setText(aList.get(i).getUseremail());




        }
        @Override
        public int getItemCount() {
            return aList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView tvusersname,tvdate,tvweburl,tvservices,tvdesc,tvmessage,useremail;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);


                tvusersname=itemView.findViewById(R.id.idusersname);
                tvdate=itemView.findViewById(R.id.idtrvdate);
                tvweburl=itemView.findViewById(R.id.idtrvweburl);
                tvservices=itemView.findViewById(R.id.idrvservices);
                tvdesc=itemView.findViewById(R.id.idrvdesc);
                tvmessage=itemView.findViewById(R.id.idrvmessage);
                useremail=itemView.findViewById(R.id.idrvuseremail);


            }
        }
    }

}

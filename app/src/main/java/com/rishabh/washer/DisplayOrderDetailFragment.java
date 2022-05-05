package com.rishabh.washer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.washer.databinding.FragmentDisplayOrderDetailBinding;

public class DisplayOrderDetailFragment extends Fragment {

    private FirebaseAuth mAuth ;
    private FragmentDisplayOrderDetailBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentDisplayOrderDetailBinding.inflate(getLayoutInflater(), container, false);
       View view = binding.getRoot();

       mAuth = FirebaseAuth.getInstance();
        String orderId = getArguments().getString("orderId");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orderDetails");
        databaseReference.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String orderDateFD = snapshot.child("orderDate").getValue(String.class);
                    String orderTimeFD = snapshot.child("orderTime").getValue(String.class);
                    String pickupDateFD = snapshot.child("pickupDate").getValue(String.class);
                    String pickupTimeFD = snapshot.child("pickupTime").getValue(String.class);
                    String deliveryDateFD = snapshot.child("deliveryDate").getValue(String.class);
                    String deliveryTimeFD = snapshot.child("deliveryTime").getValue(String.class);
                    String paymentTypeFD = snapshot.child("paymentType").getValue(String.class);
                    String serviceTypeFD = snapshot.child("serviceType").getValue(String.class);
                    String totalItemFD = snapshot.child("totalItem").getValue(String.class);
                    String promoCodeFD = snapshot.child("promoCode").getValue(String.class);


                    Toast.makeText(getContext(), orderDateFD, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







       return view;
    }
}
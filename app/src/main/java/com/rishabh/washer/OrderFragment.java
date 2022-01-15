package com.rishabh.washer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.washer.adapter.AddCartAdapter;
import com.rishabh.washer.adapter.DisplayOrderAdapter;
import com.rishabh.washer.databinding.FragmentOrderBinding;
import com.rishabh.washer.model.AddCartModel;
import com.rishabh.washer.model.DisplayOrderModel;

import java.sql.Array;
import java.util.ArrayList;


public class OrderFragment extends Fragment {

    private FragmentOrderBinding binding;
    private FirebaseAuth mAuth;

    private ArrayList<DisplayOrderModel> displayOrderModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(getLayoutInflater() , container , false);
        View view =  binding.getRoot();

        mAuth =FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String uid = user.getUid();
        displayOrderModelArrayList = new ArrayList<>();

        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL ,false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orderDetails");
        final Query itemFilter = reference.orderByChild("uid").equalTo(uid);
        itemFilter.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                displayOrderModelArrayList.clear();

                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        DisplayOrderModel orderModel = dataSnapshot.getValue(DisplayOrderModel.class);



                        displayOrderModelArrayList.add(orderModel);
                    }

                    DisplayOrderAdapter displayOrderAdapter = new DisplayOrderAdapter(getActivity() , displayOrderModelArrayList);
                    binding.shimmerLayout.setVisibility(View.GONE);
                    binding.notFoundLayout.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.recyclerView.setAdapter(displayOrderAdapter);
                }else {

                    binding.shimmerLayout.setVisibility(View.GONE);
                    binding.notFoundLayout.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}
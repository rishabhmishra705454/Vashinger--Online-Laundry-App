package com.rishabh.washer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.washer.adapter.AddCartAdapter;
import com.rishabh.washer.adapter.AddDetailAdapter;
import com.rishabh.washer.databinding.FragmentWashAndIronBinding;
import com.rishabh.washer.model.AddCartModel;
import com.rishabh.washer.model.AddDetailModel;

import java.util.ArrayList;


public class WashAndIronFragment extends Fragment {

    private FirebaseAuth mAuth;

    private ArrayList<AddDetailModel> addDetailModelArrayList;

    private FragmentWashAndIronBinding binding;

    String uid;

    private ArrayList<AddCartModel> addCartModelArrayList;


    int totalPrice;
    int totalItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentWashAndIronBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        uid = user.getUid();


        //Add detail
        addDetailModelArrayList = new ArrayList<>();
        binding.addDetailRecyclerView.setHasFixedSize(true);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3);

        binding.addDetailRecyclerView.setLayoutManager(linearLayoutManager);

        //add cart
        addCartModelArrayList = new ArrayList<>();
        binding.cartRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        binding.cartRecyclerView.setLayoutManager(layoutManager);

        loadCartRecyclerViewData();
        loadrecyclerViewData();
        checkCartIsEmptyOrNot();


        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle  bundle = new Bundle();
                bundle.putString("pricing" , Integer.toString(totalPrice));
                bundle.putString("totalItem" , Integer.toString(totalItems));
                bundle.putString("serviceType" , getArguments().getString("serviceType"));

                Navigation.findNavController(view).navigate(R.id.action_washAndIronFragment_to_washingPreferenceFragment, bundle);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return view;
    }

    private void loadCartRecyclerViewData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("cart").child(uid);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addCartModelArrayList.clear();
                 totalPrice = 0;
                totalItems = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddCartModel cartModel = dataSnapshot.getValue(AddCartModel.class);


                    int cost = Integer.parseInt(cartModel.getTotalPrice());
                    //int cost = Integer.parseInt(cartModel.getTotalPrice());
                    int items = Integer.parseInt(cartModel.getQuantity());

                    totalPrice = totalPrice + cost;
                   // totlaItems = totlaItems + items;
                    totalItems = totalItems + items;

                    addCartModelArrayList.add(cartModel);
                }

                binding.totalPriceText.setText( "Price " + "- " +Integer.toString(totalPrice));

                AddCartAdapter addCartAdapter = new AddCartAdapter(getActivity() , addCartModelArrayList);
                binding.cartRecyclerView.setAdapter(addCartAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void checkCartIsEmptyOrNot() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("cart").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.textView2.setVisibility(View.GONE);
                    binding.textView3.setVisibility(View.GONE);
                    binding.cartRecyclerView.setVisibility(View.VISIBLE);
                    binding.doneBtn.setEnabled(true);
                } else {

                    binding.textView2.setVisibility(View.VISIBLE);
                    binding.textView3.setVisibility(View.VISIBLE);
                    binding.cartRecyclerView.setVisibility(View.GONE);
                    binding.doneBtn.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void loadrecyclerViewData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dryCleaningItem");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addDetailModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    AddDetailModel addDetailModel = dataSnapshot.getValue(AddDetailModel.class);
                    addDetailModelArrayList.add(addDetailModel);
                    AddDetailAdapter addDetailAdapter = new AddDetailAdapter(getActivity(), addDetailModelArrayList);
                    binding.addDetailRecyclerView.setAdapter(addDetailAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
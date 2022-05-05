package com.rishabh.washer;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.washer.adapter.PromoCodeAdapter;
import com.rishabh.washer.databinding.FragmentPromoBinding;
import com.rishabh.washer.model.PromoCodeModel;

import java.util.ArrayList;

public class PromoFragment extends Fragment {

    private FragmentPromoBinding binding;

    ArrayList<PromoCodeModel> promoCodeModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentPromoBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();

        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        promoCodeModelArrayList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("PromoCode");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                promoCodeModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    PromoCodeModel promoCodeModel = dataSnapshot.getValue(PromoCodeModel.class);


                    promoCodeModelArrayList.add(promoCodeModel);
                    PromoCodeAdapter promoCodeAdapter = new PromoCodeAdapter(view.getContext(), promoCodeModelArrayList);
                    binding.recyclerView.setAdapter(promoCodeAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.promoCodeEditText.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "Please enter promo code", Toast.LENGTH_SHORT).show();

                }


                String code = binding.promoCodeEditText.getText().toString().trim();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("PromoCode");
                myRef.child(code).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            String title = snapshot.child("title").getValue(String.class);
                            String description = snapshot.child("description").getValue(String.class);
                            String minOrderPrice = snapshot.child("minOrderPrice").getValue(String.class);
                            String discountPercentage = snapshot.child("discountPercentage").getValue(String.class);
                            String maxDiscountPrice = snapshot.child("maxDiscountPrice").getValue(String.class);
                            String promoCode = snapshot.child("promoCode").getValue(String.class);


                            SharedPreferences setLocation = getActivity().getSharedPreferences("PROMOAPPLIED", MODE_PRIVATE);
                            SharedPreferences.Editor editor = setLocation.edit();

                            editor.putString("title", title);
                            editor.putString("description", description);
                            editor.putString("minOrderPrice", minOrderPrice);
                            editor.putString("discountPercentage", discountPercentage);
                            editor.putString("maxDiscountPrice", maxDiscountPrice);
                            editor.putString("promoCode", promoCode);
                            editor.apply();
                            editor.commit();
                            Toast.makeText(view.getContext(), promoCode, Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).popBackStack();

                        } else {
                            Toast.makeText(view.getContext(), "Incorrect Promo Code", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
}
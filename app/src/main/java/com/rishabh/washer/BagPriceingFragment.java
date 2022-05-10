package com.rishabh.washer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.washer.databinding.FragmentBagPriceingBinding;

public class BagPriceingFragment extends Fragment {

    private FragmentBagPriceingBinding binding;
    View view;

    String packagingType, pricing;

    private String bundleBagPrice , multiBagPrice ;
    private boolean isSubscriptionPlan;

    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBagPriceingBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");


        //default packaging
        binding.bundlePackaging.setSelected(true);

        //fetching data from database

        gettingDataFromDatabase();




        /*

        binding.bundlePackaging.setSelected(true);

        if (getArguments().getString("serviceType").equals("Wash And Iron")) {
            if (binding.bundlePackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 479 / Bag");
            }
            if (binding.multiPackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 579 / Bag");
            }
        }
        if (getArguments().getString("serviceType").equals("Wash And Fold")){
            if (binding.bundlePackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 369 / Bag");
            }
            if (binding.multiPackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 419 / Bag");
            }
        }

         */

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        binding.bundlePackagingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.bundlePackaging.setChecked(true);
                binding.bundlePackagingCard.setBackgroundResource(R.drawable.card_background_layout);
                binding.multiPackagingCard.setBackground(ContextCompat.getDrawable(view.getContext(), R.color.white));

                binding.washandIronPricing.setText("Rs " + bundleBagPrice + " / Kg");



            }
        });

        binding.multiPackagingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.multiPackaging.setChecked(true);
                binding.multiPackagingCard.setBackgroundResource(R.drawable.card_background_layout);
                binding.bundlePackagingCard.setBackground(ContextCompat.getDrawable(view.getContext(), R.color.white));

                binding.washandIronPricing.setText("Rs " + multiBagPrice + " / Kg");


            }
        });

        /*
        binding.bundlePackaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getString("serviceType").equals("Wash And Iron")){
                    binding.washandIronPricing.setText("₦ 479 / Bag");
                }
                if (getArguments().getString("serviceType").equals("Wash And Fold")){
                    binding.washandIronPricing.setText("₦ 369 / Bag");
                }

            }
        });

        binding.multiPackaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getString("serviceType").equals("Wash And Iron")){
                    binding.washandIronPricing.setText("₦ 579 / Bag");
                }
                if (getArguments().getString("serviceType").equals("Wash And Fold")){
                    binding.washandIronPricing.setText("₦ 419 / Bag");
                }
            }
        });

         */

        binding.doneBtn.setEnabled(true);

        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.bundlePackaging.isChecked()) {
                        packagingType = "Bundle Packaging";
                        pricing = bundleBagPrice;

                    /*
                    if (getArguments().getString("serviceType").equals("Wash And Fold")){
                        packagingType = "Bundle Packaging";
                        pricing = "369";
                    }

                     */

                }
                if (binding.multiPackaging.isChecked()) {

                        packagingType = "Multi Packaging";
                        pricing = multiBagPrice;

                    /*
                    if (getArguments().getString("serviceType").equals("Wash And Fold")){
                        packagingType = "Multi Packaging";
                        pricing = "419";
                    }

                     */

                }


                Bundle bundle = new Bundle();
                //from home fragment
                bundle.putString("serviceType", getArguments().getString("serviceType"));
                bundle.putString("packagingType", packagingType);
                bundle.putString("pricing", pricing);

                Navigation.findNavController(view).navigate(R.id.action_bagPriceingFragment_to_washingPreferenceFragment, bundle);
            }
        });
        return view;
    }

    private void gettingDataFromDatabase() {
        progressDialog.show();

        if (binding.bundlePackaging.isChecked()){
            binding.bundlePackagingCard.setBackgroundResource(R.drawable.card_background_layout);
            binding.multiPackagingCard.setBackground(ContextCompat.getDrawable(view.getContext(), R.color.white));


        }
        if (binding.multiPackaging.isChecked()){
            binding.multiPackagingCard.setBackgroundResource(R.drawable.card_background_layout);
            binding.bundlePackagingCard.setBackground(ContextCompat.getDrawable(view.getContext(), R.color.white));


        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("settings");

        myRef.child("washAndIronSetting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                bundleBagPrice = snapshot.child("bundleBagPrice").getValue(String.class);
                multiBagPrice = snapshot.child("multiBagPrice").getValue(String.class);
                isSubscriptionPlan = snapshot.child("isSubscriptionPlan").getValue(Boolean.class);

                if (binding.bundlePackaging.isChecked()){
                    binding.washandIronPricing.setText("Rs " + bundleBagPrice + " / Kg");
                }
                if (binding.multiPackaging.isChecked()){

                    binding.washandIronPricing.setText("Rs " + multiBagPrice + " / Kg");
                }
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


        gettingDataFromDatabase();

    }
}
package com.rishabh.washer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.washer.databinding.FragmentWashingPreferenceBinding;
import com.squareup.picasso.Picasso;


public class WashingPreferenceFragment extends Fragment {


    private FragmentWashingPreferenceBinding binding;

    String colorPreference, washingTemperature, additionalNote;
    boolean dryHeater, scentedDetergent, useSoftner;
    ProgressDialog progressDialog;

    String pricing;
    int additionalPrice = 0;

    String dryHeaterPrice;
    String scentedDetergentPrice;
    String useSoftnerPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWashingPreferenceBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        progressDialog.show();

        pricing = getArguments().getString("pricing");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("settings");

        myRef.child("washingPreferenceSetting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.show();

                boolean isColorPreference = snapshot.child("isColorPreference").getValue(Boolean.class);
                boolean isWashingTemp = snapshot.child("isWashingTemp").getValue(Boolean.class);
                boolean isDryHeater = snapshot.child("isDryHeater").getValue(Boolean.class);
                boolean isScentedDetergent = snapshot.child("isScentedDetergent").getValue(Boolean.class);
                boolean isUseSoftner = snapshot.child("isUseSoftner").getValue(Boolean.class);

                boolean isAdditionalNote = snapshot.child("isAdditionalNote").getValue(Boolean.class);
                dryHeaterPrice = snapshot.child("dryHeaterPrice").getValue(String.class);
                scentedDetergentPrice = snapshot.child("scentedDetergentPrice").getValue(String.class);
                useSoftnerPrice = snapshot.child("useSoftnerPrice").getValue(String.class);


                if (isColorPreference) {
                    binding.colorPreferenceLayout.setVisibility(View.VISIBLE);
                } else {

                    binding.colorPreferenceLayout.setVisibility(View.GONE);
                }

                if (isWashingTemp) {
                    binding.washingTemperatureLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.washingTemperatureLayout.setVisibility(View.GONE);
                }

                if (isDryHeater) {
                    binding.dryHeaterLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.dryHeaterLayout.setVisibility(View.GONE);
                }

                if (isScentedDetergent) {
                    binding.scentedDetergentLayout.setVisibility(View.VISIBLE);
                } else {

                    binding.scentedDetergentLayout.setVisibility(View.GONE);

                }

                if (isUseSoftner) {
                    binding.useSoftnerLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.useSoftnerLayout.setVisibility(View.GONE);
                }

                if (isAdditionalNote) {
                    binding.additionalNoteLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.additionalNoteLayout.setVisibility(View.GONE);
                }

                binding.dryHeaterPrice.setText(dryHeaterPrice + "Rs");
                binding.scentedDetergentPrice.setText(scentedDetergentPrice + "Rs");
                binding.useSoftnerPrice.setText(useSoftnerPrice + "Rs");


                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressDialog.dismiss();
            }
        });


        binding.colorClothCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.colorClothCheckbox.setChecked(true);
                binding.whiteClothCheckbox.setChecked(false);
            }
        });
        binding.whiteClothCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.colorClothCheckbox.setChecked(false);
                binding.whiteClothCheckbox.setChecked(true);
            }
        });

        //*********
        binding.colorPreferenceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.preference_info_layout, null);
                builder.setView(dialogView);

                ImageView imageView = dialogView.findViewById(R.id.imageView6);
                ImageView close = dialogView.findViewById(R.id.close);
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/washer-7bf32.appspot.com/o/images%2Fwhite_color_cloth.jpg?alt=media&token=01fda095-3636-4f86-8aa6-fefc2bf2fc9d").into(imageView);
                AlertDialog dialog
                        = builder.create();

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        binding.washTempInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.temp_preference_info_layout, null);
                builder.setView(dialogView);

                ImageView close = dialogView.findViewById(R.id.close);
                AlertDialog dialog
                        = builder.create();

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        binding.otherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.others_info_layout, null);
                builder.setView(dialogView);

                ImageView imageView = dialogView.findViewById(R.id.imageView7);
                ImageView close = dialogView.findViewById(R.id.close);
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/washer-7bf32.appspot.com/o/images%2Fdry_heater.jpg?alt=media&token=208e873c-ec2a-46f8-9c50-9673459baaf7").into(imageView);
                AlertDialog dialog
                        = builder.create();

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        binding.additionalNotesInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.aditional_info_layout, null);
                builder.setView(dialogView);

                ImageView close = dialogView.findViewById(R.id.close);
                AlertDialog dialog
                        = builder.create();

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //************


        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking color preferences
                if (binding.colorClothCheckbox.isChecked()) {
                    colorPreference = "Color Clothes";
                }
                if (binding.whiteClothCheckbox.isChecked()) {
                    colorPreference = "White Clothes";
                }

                //getting washing temperature


                washingTemperature = "30";
                // binding.editText.getText().toString();

                //getting other services
                if (binding.dryHeater.isChecked()) {
                    dryHeater = true;
                    int totalPricing = Integer.valueOf(pricing) + Integer.valueOf(dryHeaterPrice);
                    pricing = Integer.toString(totalPricing);

                    additionalPrice = additionalPrice + Integer.valueOf(dryHeaterPrice);

                } else {
                    dryHeater = false;
                }
                if (binding.scentedDetergent.isChecked()) {
                    scentedDetergent = true;
                    int totalPricing = Integer.valueOf(pricing) + Integer.valueOf(scentedDetergentPrice);
                    pricing = Integer.toString(totalPricing);

                    additionalPrice = additionalPrice + Integer.valueOf(scentedDetergentPrice);

                } else {
                    scentedDetergent = false;
                }
                if (binding.useSoftner.isChecked()) {
                    useSoftner = true;
                    int totalPricing = Integer.valueOf(getArguments().getString("pricing")) + Integer.valueOf(useSoftnerPrice);
                    pricing = Integer.toString(totalPricing);

                    additionalPrice = additionalPrice + Integer.valueOf(useSoftnerPrice);

                } else {
                    useSoftner = false;
                }

                additionalNote = binding.additionalNotes.getText().toString();

                //passing data
                Bundle washingPreferenceBundle = new Bundle();

                //handling wash and iron
                if (getArguments().getString("serviceType").equals("Wash And Iron")) {

                    washingPreferenceBundle.putString("serviceType", getArguments().getString("serviceType"));
                    washingPreferenceBundle.putString("packagingType", getArguments().getString("packagingType"));
                    washingPreferenceBundle.putString("pricing", getArguments().getString("pricing"));
                    washingPreferenceBundle.putString("additionalPrice", String.valueOf(additionalPrice));
                    washingPreferenceBundle.putString("totalPricing", pricing);

                }

                washingPreferenceBundle.putString("serviceType", getArguments().getString("serviceType"));
                if (getArguments().getString("serviceType").equals("Wash And Fold")) {
                    washingPreferenceBundle.putString("packagingType", getArguments().getString("packagingType"));
                    washingPreferenceBundle.putString("serviceType", getArguments().getString("serviceType"));
                    washingPreferenceBundle.putString("pricing", pricing);
                }

                washingPreferenceBundle.putString("colorPreference", colorPreference);
                washingPreferenceBundle.putString("washingTemperature", washingTemperature);
                washingPreferenceBundle.putBoolean("dryHeater", dryHeater);
                washingPreferenceBundle.putBoolean("scentedDetergent", scentedDetergent);
                washingPreferenceBundle.putBoolean("useSoftner", useSoftner);
                washingPreferenceBundle.putString("additionalNote", additionalNote);


                if (getArguments().getString("serviceType").equals("Dry Cleaning") || getArguments().getString("serviceType").equals("Iron")) {
                    washingPreferenceBundle.putString("pricing", pricing);
                    washingPreferenceBundle.putString("totalItem", getArguments().getString("totalItem"));

                }
                // washingPreferenceBundle.putString("serviceType", getArguments().getString("serviceType"));

                Navigation.findNavController(view).navigate(R.id.action_washingPreferenceFragment_to_otherDetailFragment, washingPreferenceBundle);
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
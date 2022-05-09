package com.rishabh.washer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.rishabh.washer.databinding.FragmentWashingPreferenceBinding;
import com.squareup.picasso.Picasso;


public class WashingPreferenceFragment extends Fragment {


    private FragmentWashingPreferenceBinding binding;

    String colorPreference, washingTemperature, additionalNote;
    boolean dryHeater, scentedDetergent, useSoftner;

    String pricing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWashingPreferenceBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();

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
                washingTemperature ="30";
                        // binding.editText.getText().toString();

                //getting other services
                if (binding.dryHeater.isChecked()) {
                    dryHeater = true;
                } else {
                    dryHeater = false;
                }
                if (binding.scentedDetergent.isChecked()) {
                    scentedDetergent = true;
                } else {
                    scentedDetergent = false;
                }
                if (binding.useSoftner.isChecked()) {
                    useSoftner = true;
                } else {
                    useSoftner = false;
                }

                additionalNote = binding.additionalNotes.getText().toString();

                //checking dry heater
                if (dryHeater == true) {

                    int totalPricing = Integer.valueOf(getArguments().getString("pricing")) + 10;
                    pricing = Integer.toString(totalPricing);
                } else {
                    pricing = getArguments().getString("pricing");
                }


                Bundle washingPreferenceBundle = new Bundle();

                washingPreferenceBundle.putString("serviceType", getArguments().getString("serviceType"));
               if (getArguments().getString("serviceType").equals("Wash And Iron") || getArguments().getString("serviceType").equals("Wash And Fold")){
                   washingPreferenceBundle.putString("packagingType", getArguments().getString("packagingType"));

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
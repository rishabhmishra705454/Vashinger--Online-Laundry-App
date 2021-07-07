package com.rishabh.washer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.rishabh.washer.databinding.FragmentWashingPreferenceBinding;


public class WashingPreferenceFragment extends Fragment {


    private FragmentWashingPreferenceBinding binding;

    String colorPreference, washingTemperature, additionalNote;
    boolean dryHeater, scentedDetergent, useSoftner;


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


        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.colorClothCheckbox.isChecked()) {
                    colorPreference = "Color Clothes";
                }
                if (binding.whiteClothCheckbox.isChecked()) {
                    colorPreference = "White Clothes";
                }

                washingTemperature = binding.editText.getText().toString();

                if (binding.dryHeater.isChecked()) {
                    dryHeater = true;
                }else {
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

                Bundle washingPreferenceBundle = new Bundle();
                washingPreferenceBundle.putString("colorPreference", colorPreference);
                washingPreferenceBundle.putString("washingTemperature", washingTemperature);
                washingPreferenceBundle.putBoolean("dryHeater", dryHeater);
                washingPreferenceBundle.putBoolean("scentedDetergent", scentedDetergent);
                washingPreferenceBundle.putBoolean("useSoftner", useSoftner);
                washingPreferenceBundle.putString("additionalNote", additionalNote);


                washingPreferenceBundle.putString("totalPrice", getArguments().getString("totalPrice"));
                washingPreferenceBundle.putString("totalItem", getArguments().getString("totalItem"));
                washingPreferenceBundle.putString("serviceType", getArguments().getString("serviceType"));

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
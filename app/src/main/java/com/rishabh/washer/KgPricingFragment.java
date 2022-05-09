package com.rishabh.washer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.rishabh.washer.databinding.FragmentKgPricingBinding;

public class KgPricingFragment extends Fragment {

    private FragmentKgPricingBinding binding;
    View view;

    String packagingType, pricing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentKgPricingBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();


        binding.bundlePackaging.setSelected(true);

        if (getArguments().getString("serviceType").equals("Wash And Iron")) {
            if (binding.bundlePackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 89 / Kg");
            }
            if (binding.multiPackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 109 / Kg");
            }
        }
        if (getArguments().getString("serviceType").equals("Wash And Fold")){
            if (binding.bundlePackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 59 / Kg");
            }
            if (binding.multiPackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 79 / Kg");
            }
        }

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

                if (getArguments().getString("serviceType").equals("Wash And Iron")){
                    binding.washandIronPricing.setText("₦ 89 / Kg");
                }
                if (getArguments().getString("serviceType").equals("Wash And Fold")){
                    binding.washandIronPricing.setText("₦ 59 / Kg");
                }

            }
        });

        binding.multiPackagingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.multiPackaging.setChecked(true);
                binding.multiPackagingCard.setBackgroundResource(R.drawable.card_background_layout);
                binding.bundlePackagingCard.setBackground(ContextCompat.getDrawable(view.getContext(), R.color.white));


                if (getArguments().getString("serviceType").equals("Wash And Iron")){
                    binding.washandIronPricing.setText("₦ 109 / Kg");
                }
                if (getArguments().getString("serviceType").equals("Wash And Fold")){
                    binding.washandIronPricing.setText("₦ 79 / Kg");
                }
            }
        });

        binding.bundlePackaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getString("serviceType").equals("Wash And Iron")){
                    binding.washandIronPricing.setText("₦ 89 / Kg");
                }
                if (getArguments().getString("serviceType").equals("Wash And Fold")){
                    binding.washandIronPricing.setText("₦ 59 / Kg");
                }

            }
        });

        binding.multiPackaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getString("serviceType").equals("Wash And Iron")){
                    binding.washandIronPricing.setText("₦ 109 / Kg");
                }
                if (getArguments().getString("serviceType").equals("Wash And Fold")){
                    binding.washandIronPricing.setText("₦ 79 / Kg");
                }
            }
        });
        binding.doneBtn.setEnabled(true);

        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.bundlePackaging.isChecked()) {

                    if (getArguments().getString("serviceType").equals("Wash And Iron")){
                        packagingType = "Bundle Packaging";
                        pricing = "89";
                    }
                    if (getArguments().getString("serviceType").equals("Wash And Fold")){
                        packagingType = "Bundle Packaging";
                        pricing = "59";
                    }

                }
                if (binding.multiPackaging.isChecked()) {

                    if (getArguments().getString("serviceType").equals("Wash And Iron")){
                        packagingType = "Multi Packaging";
                        pricing = "109";
                    }
                    if (getArguments().getString("serviceType").equals("Wash And Fold")){
                        packagingType = "Multi Packaging";
                        pricing = "79";
                    }

                }


                Bundle bundle = new Bundle();
                //from home fragment
                bundle.putString("serviceType", getArguments().getString("serviceType"));

                bundle.putString("packagingType", packagingType);
                bundle.putString("pricing", pricing);

                Navigation.findNavController(view).navigate(R.id.action_kgPricingFragment_to_washingPreferenceFragment, bundle);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getArguments().getString("serviceType") == "Wash And Iron") {
            if (binding.bundlePackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 89 / Kg");
            }
            if (binding.multiPackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 109 / Kg");
            }
        }
        if (getArguments().getString("serviceType")=="Wash And Fold"){
            if (binding.bundlePackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 59 / Kg");
            }
            if (binding.multiPackaging.isChecked()) {
                binding.washandIronPricing.setText("₦ 79 / Kg");
            }
        }


    }
}
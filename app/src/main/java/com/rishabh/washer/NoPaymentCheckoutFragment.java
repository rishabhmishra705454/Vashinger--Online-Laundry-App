package com.rishabh.washer;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rishabh.washer.databinding.FragmentNoPaymentCheckoutBinding;
import com.rishabh.washer.model.OrderModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class NoPaymentCheckoutFragment extends Fragment {

    private FragmentNoPaymentCheckoutBinding binding;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    String promoCode = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoPaymentCheckoutBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        binding.serviceType.setText(getArguments().getString("serviceType"));


        binding.promoCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                String pricing = getArguments().getString("pricing");
                bundle.putString("pricing", pricing);
                Navigation.findNavController(view).navigate(R.id.action_noPaymentCheckoutFragment_to_promoFragment, bundle);
            }
        });
        //checking promo code

        SharedPreferences setLocation = getActivity().getSharedPreferences("PROMOAPPLIED", MODE_PRIVATE);
        SharedPreferences.Editor editor = setLocation.edit();
        if (setLocation.contains("promoCode")) {

            binding.promoCodeSelecedBtn.setVisibility(View.VISIBLE);
            binding.promoCodeBtn.setVisibility(View.GONE);

            binding.title1.setText(setLocation.getString("title", ""));
            binding.description1.setText(setLocation.getString("description", ""));
            binding.minOrderPrice1.setText("- min value ₹ " + setLocation.getString("minOrderPrice", ""));
            binding.maximumDiscount1.setText("- maximum discount ₹ " + setLocation.getString("maxDiscountPrice", ""));
            binding.promoCode1.setText(setLocation.getString("promoCode", ""));


        } else {
            binding.promoCodeSelecedBtn.setVisibility(View.GONE);
            binding.promoCodeBtn.setVisibility(View.VISIBLE);
        }

        binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                binding.promoCodeSelecedBtn.setVisibility(View.GONE);
                binding.promoCodeBtn.setVisibility(View.VISIBLE);
            }
        });


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        if (getArguments().getString("serviceType") == "Wash And Iron") {
            if (getArguments().getString("packagingType").equals("Bundle Packaging")) {
                binding.icBundlePackaging.setImageResource(R.drawable.ic_check);
                binding.icMultiPackaging.setImageResource(R.drawable.ic_close);

            }
            if ((getArguments().getString("packagingType").equals("Multi Packaging"))) {

                binding.icBundlePackaging.setImageResource(R.drawable.ic_close);
                binding.icMultiPackaging.setImageResource(R.drawable.ic_check);

            }
        }

        if (getArguments().getString("serviceType") == "Wash And Fold") {
            if (getArguments().getString("packagingType").equals("Bundle Packaging")) {
                binding.icBundlePackaging.setImageResource(R.drawable.ic_check);
                binding.icMultiPackaging.setImageResource(R.drawable.ic_close);

            }
            if ((getArguments().getString("packagingType").equals("Multi Packaging"))) {

                binding.icBundlePackaging.setImageResource(R.drawable.ic_close);
                binding.icMultiPackaging.setImageResource(R.drawable.ic_check);

            }

        }


        if (getArguments().getString("colorPreference").equals("Color Clothes")) {

            binding.icColorCloth.setImageResource(R.drawable.ic_check);
            binding.icWhiteCloth.setImageResource(R.drawable.ic_close);
        }
        if (getArguments().getString("colorPreference").equals("White Clothes")) {

            binding.icColorCloth.setImageResource(R.drawable.ic_close);
            binding.icWhiteCloth.setImageResource(R.drawable.ic_check);
        }
        binding.txtTemperature.setText(getArguments().getString("washingTemperature") + " Celsius");

        if (getArguments().getBoolean("dryHeater") == true) {
            binding.icDryHeater.setImageResource(R.drawable.ic_check);
        }
        if (getArguments().getBoolean("dryHeater") == false) {
            binding.icDryHeater.setImageResource(R.drawable.ic_close);
        }

        if (getArguments().getBoolean("scentedDetergent") == true) {
            binding.icScentedDetergent.setImageResource(R.drawable.ic_check);
        }
        if (getArguments().getBoolean("scentedDetergent") == false) {
            binding.icScentedDetergent.setImageResource(R.drawable.ic_close);
        }


        if (getArguments().getBoolean("useSoftner") == true) {
            binding.icUseSoftner.setImageResource(R.drawable.ic_check);
        }
        if (getArguments().getBoolean("useSoftner") == false) {
            binding.icUseSoftner.setImageResource(R.drawable.ic_close);
        }

        binding.txtPrice.setText("Rs " + getArguments().getString("pricing") + " / Kg");


        binding.txtAdditionalPrice.setText( "Rs "+ getArguments().getString("additionalPrice") + " / Kg");

        binding.txtTotalPrice.setText( "Rs "+ getArguments().getString("totalPricing") + " / Kg");
        binding.txtTotalPrice1.setText( "Rs " + getArguments().getString("totalPricing") + "/ Kg");


        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

                String uid = user.getUid();
                DatabaseReference ref = mdatabase.getReference("orderDetails");
                String id = ref.push().getKey();

                SharedPreferences setLocation = getActivity().getSharedPreferences("PROMOAPPLIED", MODE_PRIVATE);

                if (setLocation.contains("promoCode")) {
                    promoCode = setLocation.getString("promoCode", "");


                }

                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("id",id);
                hashMap.put("uid",uid);
                hashMap.put("status","Confirmed");
                hashMap.put("serviceType", getArguments().getString("serviceType"));
                hashMap.put("packagingType", getArguments().getString("packagingType"));
                hashMap.put("additionalPrice",getArguments().getString("additionalPrice"));

                hashMap.put("pricing", getArguments().getString("pricing"));
                hashMap.put("totalPricing", getArguments().getString("totalPricing"));

                hashMap.put("totalItem" , getArguments().getString("totalItem"));
                hashMap.put("pricing", getArguments().getString("pricing"));
                hashMap.put("colorPreference", getArguments().getString("colorPreference"));
                hashMap.put("washingTemperature", getArguments().getString("washingTemperature"));
                hashMap.put("dryHeater", getArguments().getBoolean("dryHeater"));
                hashMap.put("scentedDetergent", getArguments().getBoolean("scentedDetergent"));
                hashMap.put("useSoftner", getArguments().getBoolean("useSoftner"));
                hashMap.put("additionalNote", getArguments().getString("additionalNote"));


                //passing location
                hashMap.put("address", getArguments().getString("address"));
                hashMap.put("locality", getArguments().getString("locality"));
                hashMap.put("pincode", getArguments().getString("pincode"));
                hashMap.put("latitude", getArguments().getString("latitude"));
                hashMap.put("longitude", getArguments().getString("longitude"));
                hashMap.put("phoneNo", getArguments().getString("phoneNo"));
                hashMap.put("houseNo", getArguments().getString("houseNo"));
                hashMap.put("landmark", getArguments().getString("landmark"));
                hashMap.put("fullName", getArguments().getString("fullName"));

                hashMap.put("pickupDate", getArguments().getString("pickupDate"));

                hashMap.put("deliveryDate", getArguments().getString("deliveryDate"));

                hashMap.put("pickupTime", getArguments().getString("pickupTime"));

                hashMap.put("deliveryTime", getArguments().getString("deliveryTime"));


             //   OrderModel orderModel = new OrderModel(id, uid, currentDate, currentTime, "", "Confirmed", getArguments().getString("colorPreference"), getArguments().getString("washingTemperature"), getArguments().getString("additionalNote"), getArguments().getString("pickupDate"), getArguments().getString("pickupTime"), getArguments().getString("deliveryDate"), getArguments().getString("deliveryTime"), getArguments().getString("totalPrice"), getArguments().getString("totalItem"), getArguments().getString("serviceType"), getArguments().getString("pricing"), getArguments().getString("packagingType"), getArguments().getString("address"), getArguments().getString("pincode"), getArguments().getString("locality"), getArguments().getString("latitude"), getArguments().getString("longitude"), getArguments().getString("phoneNo"), getArguments().getString("houseNo"), getArguments().getString("landmark"), getArguments().getString("fullName"), promoCode, getArguments().getBoolean("dryHeater"), getArguments().getBoolean("scentedDetergent"), getArguments().getBoolean("useSoftner"), "", "");
                ref.child(id).updateChildren(hashMap);
                Toast.makeText(getContext(), "Ordered", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_noPaymentCheckoutFragment_to_sucessDialogFragment);

            }
        });

        return view;
    }
}
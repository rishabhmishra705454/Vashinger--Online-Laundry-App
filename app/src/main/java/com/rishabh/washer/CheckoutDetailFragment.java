package com.rishabh.washer;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.washer.databinding.FragmentCheckoutDetailBinding;
import com.rishabh.washer.model.OrderModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class CheckoutDetailFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentCheckoutDetailBinding binding;
    FirebaseUser user;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCheckoutDetailBinding.inflate(getLayoutInflater(), container, false);
       view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileDetail", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName", "");
        String phoneNo = sharedPreferences.getString("phoneNo", "");


        binding.fullNameText.setText(fullName);
        binding.serviceType.setText(getArguments().getString("serviceType"));
        binding.pickupDateAndTime.setText(getArguments().getString("pickupDate") + " | " + getArguments().getString("pickupTime"));
        binding.pickupAddress.setText(getArguments().getString("locality") + ", " + getArguments().getString("pincode"));
        binding.contactNo.setText(phoneNo);
        binding.subTotal.setText("\u20B9 " + getArguments().getString("totalPrice"));
        binding.deliveryCharge.setText("\u20B9 0");
        binding.total.setText("\u20B9 " + getArguments().getString("totalPrice"));
        binding.totalCloth.setText(getArguments().getString("totalItem"));

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).popBackStack();
            }
        });


        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.onlinePayment.isChecked()) {

                    orderOnline();

                } else if (binding.cashOnDelivery.isChecked()) {

                    sendOrderToServer();

                } else {
                    Toast.makeText(getContext(), "Select Payment Method", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void sendOrderToServer() {

        FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

        String uid = user.getUid();

        DatabaseReference refFrom = mdatabase.getReference("cart").child(uid);
        DatabaseReference refTo = mdatabase.getReference("orderItems").child(uid);
        String id = refTo.push().getKey();
        refFrom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                refTo.child(id).setValue(snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference ref = mdatabase.getReference("orderDetails").child(uid);
        OrderModel orderModel = new OrderModel(id ,currentDate ,currentTime ,"COD", "Confirmed", getArguments().getString("colorPreference"),getArguments().getString("washingTemperature"),getArguments().getString("additionalNote"), getArguments().getString("address") ,getArguments().getString("locality"), getArguments().getString("pincode") , getArguments().getString("latitude"),getArguments().getString("longitude") , getArguments().getString("pickupDate"),getArguments().getString("pickupTime"),getArguments().getString("deliveryDate") , getArguments().getString("deliveryTime"),getArguments().getString("totalPrice"),getArguments().getString("totalItem") , getArguments().getString("serviceType"),getArguments().getBoolean("dryHeater") ,getArguments().getBoolean("scentedDetergent"),getArguments().getBoolean("useSoftner"));

        ref.child(id).setValue(orderModel);
        Toast.makeText(getContext(), "Ordered", Toast.LENGTH_SHORT).show();

        DatabaseReference refr = mdatabase.getReference("cart").child(uid);
        refr.removeValue();

        successDialog();



    }




    private void orderOnline() {

    }

    private void successDialog() {
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.order_sucess_layout, viewGroup, false);

        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        MaterialButton materialButton = view1.findViewById(R.id.doneBtn);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view1).navigate(R.id.action_checkoutDetailFragment_to_nav_home);

            }
        });
        alertDialog.show();

         */

        Navigation.findNavController(view).navigate(R.id.action_checkoutDetailFragment_to_sucessDialogFragment);





    }

}
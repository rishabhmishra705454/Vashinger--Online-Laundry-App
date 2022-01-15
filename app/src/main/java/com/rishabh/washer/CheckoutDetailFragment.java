package com.rishabh.washer;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.rishabh.washer.Interfaces.PaymentUpdateInterface;
import com.rishabh.washer.databinding.FragmentCheckoutDetailBinding;
import com.rishabh.washer.model.OrderModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CheckoutDetailFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();


    private FirebaseAuth mAuth;
    private FragmentCheckoutDetailBinding binding;
    FirebaseUser user;

    String fullName;
    String phoneNo, email;

    String totalPrice1;
    View view1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCheckoutDetailBinding.inflate(getLayoutInflater(), container, false);
        view1 = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        totalPrice1 = getArguments().getString("totalPrice");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileDetail", MODE_PRIVATE);
        fullName = sharedPreferences.getString("fullName", "");
        phoneNo = sharedPreferences.getString("phoneNo", "");
        email = sharedPreferences.getString("email", "");


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

                Navigation.findNavController(view1).popBackStack();
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


        ((MainActivity) getActivity()).sendData2(new PaymentUpdateInterface() {
            @Override
            public void paymentSuccess(String paymentId) {

                Toast.makeText(getContext(), "payment done", Toast.LENGTH_SHORT).show();
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
                DatabaseReference ref = mdatabase.getReference("orderDetails").child(id);
                OrderModel orderModel = new OrderModel(id, uid, currentDate, currentTime, "ONLINE", "Confirmed", getArguments().getString("colorPreference"), getArguments().getString("washingTemperature"), getArguments().getString("additionalNote"), getArguments().getString("pickupDate"), getArguments().getString("pickupTime"), getArguments().getString("deliveryDate"), getArguments().getString("deliveryTime"), getArguments().getString("totalPrice"), getArguments().getString("totalItem"), getArguments().getString("serviceType"), getArguments().getString("address"), getArguments().getString("pincode"), getArguments().getString("locality"), getArguments().getString("latitude"), getArguments().getString("longitude"), getArguments().getString("phoneNo"), getArguments().getString("houseNo"), getArguments().getString("landmark"), getArguments().getString("fullName"), getArguments().getBoolean("dryHeater"), getArguments().getBoolean("scentedDetergent"), getArguments().getBoolean("useSoftner"), "", "");

                ref.setValue(orderModel);
                Toast.makeText(getContext(), "Ordered", Toast.LENGTH_SHORT).show();

                DatabaseReference refr = mdatabase.getReference("cart").child(uid);
                refr.removeValue();

                AlertDialog.Builder builder = new AlertDialog.Builder(view1.getContext());

                View dialogView = LayoutInflater.from(view1.getContext()).inflate(R.layout.fragment_sucess_dialog, container, false);
                builder.setView(dialogView);
                builder.setCancelable(false);
                MaterialButton button = dialogView.findViewById(R.id.doneBtn);

                AlertDialog alertDialog = builder.create();
                TextView textView = dialogView.findViewById(R.id.sucess);
                textView.setText("Payment Successful");
                button.setText("Next");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                        Navigation.findNavController(view1).navigate(R.id.action_checkoutDetailFragment_to_sucessDialogFragment);

                    }
                });

                alertDialog.show();

                }
        });

        return view1;
    }

    private void sendingOnlineOrderToServer(String paymentId) {
        Toast.makeText(getContext(), "payment done", Toast.LENGTH_SHORT).show();
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
        OrderModel orderModel = new OrderModel(id, uid, currentDate, currentTime, "ONLINE", "Confirmed", getArguments().getString("colorPreference"), getArguments().getString("washingTemperature"), getArguments().getString("additionalNote"), getArguments().getString("pickupDate"), getArguments().getString("pickupTime"), getArguments().getString("deliveryDate"), getArguments().getString("deliveryTime"), getArguments().getString("totalPrice"), getArguments().getString("totalItem"), getArguments().getString("serviceType"), getArguments().getString("address"), getArguments().getString("pincode"), getArguments().getString("locality"), getArguments().getString("latitude"), getArguments().getString("longitude"), getArguments().getString("phoneNo"), getArguments().getString("houseNo"), getArguments().getString("landmark"), getArguments().getString("fullName"), getArguments().getBoolean("dryHeater"), getArguments().getBoolean("scentedDetergent"), getArguments().getBoolean("useSoftner"), "", "");

        ref.child(id).setValue(orderModel);
        Toast.makeText(getContext(), "Ordered", Toast.LENGTH_SHORT).show();

        DatabaseReference refr = mdatabase.getReference("cart").child(uid);
        refr.removeValue();

        Navigation.findNavController(view1).navigate(R.id.action_checkoutDetailFragment_to_sucessDialogFragment);

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
        DatabaseReference ref = mdatabase.getReference("orderDetails").child(id);
        OrderModel orderModel = new OrderModel(id, uid, currentDate, currentTime, "ONLINE", "Confirmed", getArguments().getString("colorPreference"), getArguments().getString("washingTemperature"), getArguments().getString("additionalNote"), getArguments().getString("pickupDate"), getArguments().getString("pickupTime"), getArguments().getString("deliveryDate"), getArguments().getString("deliveryTime"), getArguments().getString("totalPrice"), getArguments().getString("totalItem"), getArguments().getString("serviceType"), getArguments().getString("address"), getArguments().getString("pincode"), getArguments().getString("locality"), getArguments().getString("latitude"), getArguments().getString("longitude"), getArguments().getString("phoneNo"), getArguments().getString("houseNo"), getArguments().getString("landmark"), getArguments().getString("fullName"), getArguments().getBoolean("dryHeater"), getArguments().getBoolean("scentedDetergent"), getArguments().getBoolean("useSoftner"), "", "");

        ref.setValue(orderModel);
        Toast.makeText(getContext(), "Ordered", Toast.LENGTH_SHORT).show();

        DatabaseReference refr = mdatabase.getReference("cart").child(uid);
        refr.removeValue();

        successDialog();

    }


    private void orderOnline() {

        final Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_SBON3RwDoUOLYO");


        checkout.setImage(R.mipmap.vashinger);
        try {

            JSONObject options = new JSONObject();

            options.put("name", fullName);
            options.put("description", "");
            options.put("currency", "INR");
            options.put("amount", Integer.parseInt(totalPrice1) * 100);
            options.put("prefill.email", email);
            options.put("prefill.contact", phoneNo);


            checkout.open(getActivity(), options);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void successDialog() {

        Navigation.findNavController(view1).navigate(R.id.action_checkoutDetailFragment_to_sucessDialogFragment);


    }


}
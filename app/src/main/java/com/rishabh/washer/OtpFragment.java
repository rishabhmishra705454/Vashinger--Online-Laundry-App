package com.rishabh.washer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.washer.databinding.FragmentOtpBinding;

import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class OtpFragment extends Fragment {



    private FragmentOtpBinding binding;

    private FirebaseAuth mAuth;
    private String verificationId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentOtpBinding.inflate(getLayoutInflater() , container , false);
        View view = binding.getRoot();

        //hooks
       // textPhoneNumber = view.findViewById(R.id.textPhoneNumber);
      //  verifyOtpBtn = view.findViewById(R.id.doneBtn);
       // otpText = view.findViewById(R.id.pin_view);
       // countDownText = view.findViewById(R.id.countDownText);
      //  progressBar= view.findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        String phoneNumber = getArguments().getString("phoneNumber");
        binding.textPhoneNumber.setText("OTP is send on " + phoneNumber);

        String phone = "+91" + phoneNumber;
        sendVerificationCode(phone);

        binding.verifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(binding.otpText.getText().toString())){
                    Toast.makeText(getContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                }else if (binding.otpText.length() < 6){
                    Toast.makeText(getContext(), "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                }
                else {

                    verifyCode(binding.otpText.getText().toString());
                    binding.countDownText.setVisibility(View.GONE);
                    
                }

            }
        });

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.countDownText.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                binding.progressBar.setVisibility(View.GONE);
                binding.countDownText.setText("Resend OTP");
            }
        }.start();



        return view;
    }

    private void sendVerificationCode(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            final String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                // if the code is not null then
                // we are setting that code to 
                // our OTP edittext field.
                binding.otpText.setText(code);
                binding.verifyOtpBtn.setEnabled(false);
                binding.countDownText.setVisibility(View.GONE);

                // after setting this code 
                // to OTP edittext field we 
                // are calling our verifycode method.
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            binding.countDownText.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.GONE);

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {

        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                           // Navigation.findNavController(view).navigate(R.id.action_otpFragment2_to_userDetailFragment2);
                            isUser();


                        } else {
                            Toast.makeText(getContext(), "otp not verified", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void isUser() {

        FirebaseUser user =mAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = databaseReference.orderByChild("uid").equalTo(user.getUid());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String fullNameFromDB = snapshot.child(user.getUid()).child("fullName").getValue(String.class);
                    String mobileNoFromDB = snapshot.child(user.getUid()).child("phoneNumber").getValue(String.class);
                    String emailFromDB = snapshot.child(user.getUid()).child("email").getValue(String.class);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileDetail", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("fullName", fullNameFromDB);
                    myEdit.putString("phoneNo", mobileNoFromDB);
                    myEdit.putString("email", emailFromDB);
                    myEdit.apply();

                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_otpFragment2_to_nav_home);

                }else {
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_otpFragment2_to_userDetailFragment2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

    
package com.rishabh.washer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.rishabh.washer.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;


    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(getLayoutInflater() , container ,false);
        View view = binding.getRoot();


        mAuth = FirebaseAuth.getInstance();




        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validatePhoneNumber()) {
                    return;
                }


                String phoneNumber = binding.phoneNumberText.getEditText().getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("phoneNumber" , phoneNumber);
                Navigation.findNavController(view).navigate(R.id.action_loginFragment2_to_otpFragment2 , bundle);

            }
        });
        return view;
    }

    private boolean validatePhoneNumber() {
        String val = binding.phoneNumberText.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            binding.phoneNumberText.setError("Please enter phone number");
            return false;
        }if (val.length() < 10) {
            binding.phoneNumberText.setError("Please enter valid number");
            return false;
        } else {
            binding.phoneNumberText.setError(null);
            binding.phoneNumberText.setErrorEnabled(false);
            return true;
        }

    }
}
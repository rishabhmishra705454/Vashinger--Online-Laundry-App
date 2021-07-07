package com.rishabh.washer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rishabh.washer.databinding.FragmentEditProfileBinding;
import com.rishabh.washer.model.UserDetailModel;

import static android.content.Context.MODE_PRIVATE;


public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater() , container , false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String uid = user.getUid();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileDetail", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName", "");
        String email = sharedPreferences.getString("email", "");
        String phone = sharedPreferences.getString("phoneNo", "");

        binding.phoneNo.getEditText().setText(phone);
        binding.fullName.getEditText().setText(fullName);
        binding.email.getEditText().setText(email);

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFullName() || !validateEmail()){
                    return;
                }

                String mfullName = binding.fullName.getEditText().getText().toString();
                String mEmail = binding.email.getEditText().getText().toString();

                UserDetailModel userDetailModel = new UserDetailModel(phone , mEmail , mfullName ,uid);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child(uid).setValue(userDetailModel);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("fullName", mfullName);
                myEdit.putString("email" , mEmail);
                myEdit.apply();
                Toast.makeText(getContext(), "Profile Updated Sucessfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
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

    private boolean validateEmail() {
        String val = binding.email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            binding.email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            binding.email.setError("Invalid Email!");
            return false;
        } else {
            binding.email.setError(null);
            binding.email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFullName() {
        String val = binding.fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            binding.fullName.setError("Please enter full name");
            return false;
        } else {
            binding.fullName.setError(null);
            return true;
        }
    }
}
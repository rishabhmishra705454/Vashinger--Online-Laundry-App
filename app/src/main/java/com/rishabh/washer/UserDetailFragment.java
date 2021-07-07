package com.rishabh.washer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rishabh.washer.model.UserDetailModel;

import static android.content.Context.MODE_PRIVATE;

public class UserDetailFragment extends Fragment {

    private FirebaseAuth mAuth;

    TextInputLayout inputFullname , inputEmail  ;
    MaterialButton doneBtn;
    ProgressBar progressBar;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_detail, container, false);

        mAuth =FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        inputFullname = view.findViewById(R.id.inputFullName);
        inputEmail = view.findViewById(R.id.inputEmail);
        doneBtn = view.findViewById(R.id.doneBtn);
        progressBar =view.findViewById(R.id.progressBar);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateFullName() || !validateEmail()){
                    return;

                }

                String fullName = inputFullname.getEditText().getText().toString();
                String email = inputEmail.getEditText().getText().toString();
                String uid = user.getUid();
                String phoneNumber = user.getPhoneNumber();
                //shared preference
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileDetail", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("fullName", fullName);
                myEdit.putString("phoneNo", phoneNumber);
                myEdit.putString("email", email);
                myEdit.apply();

                UserDetailModel userDetailModel = new UserDetailModel(phoneNumber , email ,fullName,uid);
                databaseReference.child(uid).setValue(userDetailModel);
                Navigation.findNavController(view).navigate(R.id.action_userDetailFragment2_to_nav_home );


            }
        });
        return  view;
    }

    private boolean validateEmail() {
        String val = inputEmail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            inputEmail.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            inputEmail.setError("Invalid Email!");
            return false;
        } else {
            inputEmail.setError(null);
            inputEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFullName() {

        String val = inputFullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            inputFullname.setError("Please enter full name");
            return false;
        } else {
            inputFullname.setError(null);
            return true;
        }
    }
}
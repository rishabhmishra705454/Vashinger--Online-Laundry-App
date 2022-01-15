package com.rishabh.washer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.rishabh.washer.databinding.FragmentProfileBinding;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;

    private FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater() , container , false);
        View view =  binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        //calling Profile detail shared preference
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileDetail", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName", "");
        String email = sharedPreferences.getString("email", "");

        binding.fullNameText.setText(fullName);

        binding.emailText.setText(email);
        //click on logout btn
        binding.logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileDetail", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.apply();

                SharedPreferences setLocation = getActivity().getSharedPreferences("LOCATION", MODE_PRIVATE);
                SharedPreferences.Editor editor = setLocation.edit();
                editor.clear();
                editor.apply();

                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(view).navigate(R.id.nav_home);

            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_profile_to_editProfileFragment);
            }
        });
        binding.savedAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_profile_to_savedAddressFragment);
            }
        });
        return  view ;
    }
}
package com.rishabh.washer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.rishabh.washer.databinding.FragmentHomeBinding;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;

    private FragmentHomeBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //checking user is logged in or not
        if (mAuth.getCurrentUser() == null) {

            Navigation.findNavController(view).navigate(R.id.loginFragment2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileDetail", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName", "");


        //setting user full name on home screen
        binding.fullNameText.setText(fullName);
        //click on wash and iron btn
        binding.washAndIronBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle washAndIronBundle = new Bundle();
                washAndIronBundle.putString("serviceType", "Wash And Iron");
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_washAndIronFragment, washAndIronBundle);


            }
        });

        binding.iron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("serviceType" , "Iron");
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_washAndIronFragment, bundle);

            }
        });


        binding.dryCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("serviceType" , "Dry Cleaning");
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_washAndIronFragment, bundle);

            }
        });


        binding.draning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("serviceType" , "Draning");
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_washAndIronFragment, bundle);

            }
        });
        return view;

    }
}
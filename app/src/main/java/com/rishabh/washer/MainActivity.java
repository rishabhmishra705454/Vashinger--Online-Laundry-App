package com.rishabh.washer;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
         bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() ==R.id.washAndIronFragment){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if (destination.getId() == R.id.washingPreferenceFragment){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if (destination.getId() == R.id.otherDetailFragment){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if (destination.getId() == R.id.checkoutDetailFragment){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if (destination.getId() == R.id.loginFragment2){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if (destination.getId()== R.id.otpFragment2){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if (destination.getId() == R.id.userDetailFragment2){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if (destination.getId() == R.id.mapsFragment){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if (destination.getId() == R.id.sucessDialogFragment){
                    bottomNavigationView.setVisibility(View.GONE);
                }else if (destination.getId() == R.id.editProfileFragment){
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });


    }



}
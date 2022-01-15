package com.rishabh.washer;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rishabh.washer.adapter.SavedAddressAdapter;
import com.rishabh.washer.databinding.FragmentSavedAddressBinding;
import com.rishabh.washer.model.SavedAddressModel;

import java.util.ArrayList;


public class SavedAddressFragment extends Fragment {

    private FragmentSavedAddressBinding binding;
    View view;
    SavedAddressAdapter savedAddressAdapter;
    private FirebaseAuth mAuth;
    ArrayList<SavedAddressModel> savedAddressModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSavedAddressBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String uid = user.getUid();


        savedAddressModelArrayList = new ArrayList<>();
        binding.addressRecycler.setHasFixedSize(true);
        binding.addressRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        savedAddressAdapter = new SavedAddressAdapter(savedAddressModelArrayList, view.getContext(), view);

        loadRecyclerView();

        binding.addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocation();
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return view;
    }


    private void checkLocation() {
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        if (isLocationEnabled()) {
                            Navigation.findNavController(view).navigate(R.id.action_savedAddressFragment_to_mapsFragment);

                        } else {
                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext())
                                    .setTitle("GPS Permission")
                                    .setMessage("GPS is required for this app to work .  Please enable GPS")
                                    .setPositiveButton("yes", ((dialog, which) -> {
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(intent);
                                    }))
                                    .setCancelable(true)
                                    .show();
                        }


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        if (permissionDeniedResponse.isPermanentlyDenied()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Permission Denied")
                                    .setMessage("Permission to access device location is permanently denied .you need to go setting to allow the permission")
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                                            getActivity().startActivity(intent);
                                        }
                                    })
                                    .show();
                        } else {
                            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }

    private void loadRecyclerView() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Address").child(uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savedAddressModelArrayList.clear();

                if (snapshot.exists()) {

                    binding.addressRecycler.setVisibility(View.VISIBLE);
                    binding.noAddressLayout.setVisibility(View.GONE);

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SavedAddressModel savedAddressModel = dataSnapshot.getValue(SavedAddressModel.class);

                        savedAddressModelArrayList.add(savedAddressModel);

                        savedAddressAdapter.notifyDataSetChanged();

                        binding.addressRecycler.setAdapter(savedAddressAdapter);
                    }
                } else {
                    savedAddressModelArrayList.clear();
                    binding.addressRecycler.setVisibility(View.GONE);
                    binding.noAddressLayout.setVisibility(View.VISIBLE);
                    SharedPreferences setLocation = view.getContext().getSharedPreferences("LOCATION", MODE_PRIVATE);
                    SharedPreferences.Editor editor = setLocation.edit();
                    editor.clear();
                    editor.apply();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean isLocationEnabled() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }
}
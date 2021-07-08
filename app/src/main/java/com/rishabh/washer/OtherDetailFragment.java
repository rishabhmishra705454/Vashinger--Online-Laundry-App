package com.rishabh.washer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rishabh.washer.databinding.FragmentOtherDetailBinding;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class OtherDetailFragment extends Fragment {


    int hour, minutes;

    private FragmentOtherDetailBinding binding;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOtherDetailBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();


        // Setting pickup Address
        SharedPreferences setLocation = getActivity().getSharedPreferences("LOCATION", MODE_PRIVATE);
        if (setLocation.contains("address")) {
            binding.pickupYes.setVisibility(View.VISIBLE);
            binding.pickupNot.setVisibility(View.GONE);
            binding.doneBtn.setEnabled(true);
            String address = setLocation.getString("address", "");
            String locality = setLocation.getString("locality", "");
            String pincode = setLocation.getString("pincode", "");
            String latitude = setLocation.getString("latitude", "");
            String longitude = setLocation.getString("longitude", "");


            binding.deliveryNo.setVisibility(View.GONE);
            binding.deliveryYes.setVisibility(View.VISIBLE);
            binding.textView6.setText(address);
            binding.textView11.setText(address);
            binding.deliveryLocality.setText(locality);
            binding.textView27.setText(locality);

        } else {
            binding.pickupYes.setVisibility(View.GONE);
            binding.pickupNot.setVisibility(View.VISIBLE);
            binding.doneBtn.setEnabled(false);
        }

        binding.addresDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences setLocation = getActivity().getSharedPreferences("LOCATION", MODE_PRIVATE);
                SharedPreferences.Editor editor = setLocation.edit();
                editor.clear();
                editor.apply();
                binding.pickupYes.setVisibility(View.GONE);
                binding.pickupNot.setVisibility(View.VISIBLE);

                binding.deliveryNo.setVisibility(View.VISIBLE);
                binding.deliveryYes.setVisibility(View.GONE);
                binding.doneBtn.setEnabled(false);
            }
        });
        binding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_otherDetailFragment_to_mapsFragment);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).popBackStack();
            }
        });
        //setting delivery address



        //pickup date

        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);


        binding.pickupDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());

                datePickerDialog =new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String pickupDate = dayOfMonth + "/" + (month +1) + "/" + year;
                        binding.pickupDateButton.setText(pickupDate);
                    }
                }, year , month , day);

                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

/*
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker1 = materialDateBuilder.build();

        binding.pickupDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker1.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");


            }
        });

        materialDatePicker1.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        binding.pickupDateButton.setText(materialDatePicker1.getHeaderText());

                    }
                });

 */


        //pickup time

        binding.pickupTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                //initialize
                                hour = hourOfDay;
                                minutes = minute;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, hour, minutes);
                                binding.pickupTimeButton.setText(DateFormat.format("hh:mm aa", calendar));

                            }
                        }, 12, 0, false);

                timePickerDialog.updateTime(hour, minutes);
                timePickerDialog.show();
            }
        });

        //delivery date

        binding.deliveryDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());

                datePickerDialog =new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String pickupDate = dayOfMonth + "/" + (month +1) + "/" + year;
                        binding.deliveryDateButton.setText(pickupDate);
                    }
                }, year , month , day);

                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

/*
        MaterialDatePicker.Builder materialDateBuilder1 = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder1.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker2 = materialDateBuilder1.build();

        binding.deliveryDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker2.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");

            }
        });

        materialDatePicker2.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        binding.deliveryDateButton.setText(materialDatePicker2.getHeaderText());

                    }
                });

 */


        //delivery time


        binding.deliveryTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                //initialize
                                hour = hourOfDay;
                                minutes = minute;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, hour, minutes);
                                binding.deliveryTimeButton.setText(DateFormat.format("hh:mm aa", calendar));

                            }
                        }, 12, 0, false);

                timePickerDialog1.updateTime(hour, minutes);
                timePickerDialog1.show();
            }
        });

        binding.selectPickAddressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                if (isLocationEnabled()) {
                                    Navigation.findNavController(view).navigate(R.id.action_otherDetailFragment_to_mapsFragment);

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


        });

        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pickupDate = "Select Pickup Date";
                String pickupTime = "Select Pickup Time";
                String deliveryDate = "Select Delivery Date";
                String deliveryTime = "Select Delivery Time";
                if (binding.pickupDateButton.getText().toString().equals(pickupDate)) {
                    Toast.makeText(getContext(), "Select Pickup date", Toast.LENGTH_SHORT).show();
                } else if (binding.pickupTimeButton.getText().toString().equals(pickupTime)) {
                    Toast.makeText(getContext(), "Select Pickup Time", Toast.LENGTH_SHORT).show();
                } else if (binding.deliveryDateButton.getText().toString().equals(deliveryDate)) {
                    Toast.makeText(getContext(), "Select Delivery Date", Toast.LENGTH_SHORT).show();
                } else if (binding.deliveryTimeButton.getText().toString().equals(deliveryTime)) {
                    Toast.makeText(getContext(), "Select Delivery Time", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences setLocation = getActivity().getSharedPreferences("LOCATION", MODE_PRIVATE);
                    String address = setLocation.getString("address", "");
                    String locality = setLocation.getString("locality", "");
                    String pincode = setLocation.getString("pincode", "");
                    String latitude = setLocation.getString("latitude", "");
                    String longitude = setLocation.getString("longitude", "");

                    Bundle detail = new Bundle();
                    detail.putString("colorPreference", getArguments().getString("colorPreference"));
                    detail.putString("washingTemperature", getArguments().getString("washingTemperature"));
                    detail.putBoolean("dryHeater", getArguments().getBoolean("dryHeater"));
                    detail.putBoolean("scentedDetergent", getArguments().getBoolean("scentedDetergent"));
                    detail.putBoolean("useSoftner", getArguments().getBoolean("useSoftner"));
                    detail.putString("additionalNote", getArguments().getString("additionalNote"));

                    detail.putString("address", address);
                    detail.putString("locality", locality);
                    detail.putString("pincode", pincode);
                    detail.putString("latitude", latitude);
                    detail.putString("longitude", longitude);

                    detail.putString("pickupDate", binding.pickupDateButton.getText().toString());
                    detail.putString("pickupTime", binding.pickupTimeButton.getText().toString());
                    detail.putString("deliveryDate", binding.deliveryDateButton.getText().toString());
                    detail.putString("deliveryTime", binding.deliveryTimeButton.getText().toString());


                    detail.putString("totalPrice" , getArguments().getString("totalPrice"));
                    detail.putString("totalItem" , getArguments().getString("totalItem"));
                    detail.putString("serviceType" ,getArguments().getString("serviceType")) ;

                    Navigation.findNavController(view).navigate(R.id.action_otherDetailFragment_to_checkoutDetailFragment , detail);
                }


            }
        });


        return view;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }


}
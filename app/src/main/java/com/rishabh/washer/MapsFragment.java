package com.rishabh.washer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rishabh.washer.databinding.FragmentMapsBinding;
import com.rishabh.washer.model.SavedAddressModel;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {

    GoogleMap mMap;
    View layout;
    private FragmentMapsBinding binding;
    FusedLocationProviderClient mLocationClient;

    View view;
    RelativeLayout bottomSheetRL;

    TextInputLayout mphoneNo;
    TextInputLayout mhouseNo;
    TextInputLayout mlandmark;
    TextInputLayout mname;
    MaterialButton button;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            // mMap.setMyLocationEnabled(true);

            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    LatLng target = mMap.getCameraPosition().target;
                    Double mlatitude = target.latitude;
                    Double mlongitude = target.longitude;

                    try {
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addressList = geocoder.getFromLocation(mlatitude, mlongitude, 1);


                        if (addressList != null && addressList.size() > 0) {
                            String address = addressList.get(0).getAddressLine(0);
                            String pinCode = addressList.get(0).getPostalCode();
                            String locality = addressList.get(0).getLocality();

                            binding.addressText.setText(address);
                            binding.localtyText.setText(locality);

                            binding.doneBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);

                                    // passing a layout file for our bottom sheet dialog.
                                    layout = LayoutInflater.from(view.getContext()).inflate(R.layout.bottom_sheet_location, bottomSheetRL);

                                    // passing our layout file to our bottom sheet dialog.
                                    bottomSheetTeachersDialog.setContentView(layout);

                                    // below line is to set our bottom sheet dialog as cancelable.
                                    bottomSheetTeachersDialog.setCancelable(false);

                                    // below line is to set our bottom sheet cancelable.
                                    bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);

                                    // below line is to display our bottom sheet dialog.
                                    bottomSheetTeachersDialog.show();

                                     button = layout.findViewById(R.id.doneBtn);
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mphoneNo = layout.findViewById(R.id.textPhoneNo2);
                                            mhouseNo = layout.findViewById(R.id.inputHouseNo2);
                                            mlandmark = layout.findViewById(R.id.inputLandMark2);
                                            mname = layout.findViewById(R.id.textname2);


                                            if (!fullNameValidation() || !phoneValidation() || !flatValidate()) {
                                                return;
                                            }
                                            String phoneNo = mphoneNo.getEditText().getText().toString().trim();
                                            String houseNo = mhouseNo.getEditText().getText().toString();
                                            String landmark = mlandmark.getEditText().getText().toString();
                                            String fullName = mname.getEditText().getText().toString();

/*
                                            SharedPreferences setLocation = getActivity().getSharedPreferences("LOCATION", MODE_PRIVATE);
                                            SharedPreferences.Editor editor;
                                            editor = setLocation.edit();
                                            editor.putString("address" , address);
                                            editor.putString("pincode",pinCode);
                                            editor.putString("locality" ,locality);
                                            editor.putString("latitude" , Double.toString(mlatitude));
                                            editor.putString("longitude" , Double.toString(mlongitude));
                                            editor.putString("phoneNo" , phoneNo);
                                            editor.putString("houseNo" ,houseNo);
                                            editor.putString("landmark" , landmark);
                                            editor.apply();

 */

                                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                            String uid = mAuth.getCurrentUser().getUid();
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                                            DatabaseReference myRef = database.getReference("Address").child(uid);

                                            String key = myRef.push().getKey();
                                            SavedAddressModel savedAddressModel = new SavedAddressModel(uid, key, address, pinCode, locality, Double.toString(mlatitude), Double.toString(mlongitude), phoneNo, houseNo, landmark, fullName);
                                            myRef.child(key).setValue(savedAddressModel);


                                            bottomSheetTeachersDialog.cancel();
                                            Navigation.findNavController(view).popBackStack();


                                        }
                                    });

                                }
                            });

                        }


                        //  textLatLong.setText(Double.toString(latitude) + " " + Double.toString(longitude));


                    } catch (Exception e) {
                        e.fillInStackTrace();
                    }
                }
            });

        }
    };



    private boolean flatValidate() {

        String val = mhouseNo.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            mhouseNo.setError("Please enter flat no/building name");
            return false;
        } else {
            mhouseNo.setError(null);
            return true;
        }
    }

    private boolean phoneValidation() {
        String val = mphoneNo.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            mphoneNo.setError("Please enter phone number");
            return false;
        }if (val.length() < 10) {
            mphoneNo.setError("Please enter valid number");
            return false;
        } else {
            mphoneNo.setError(null);
            mphoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private boolean fullNameValidation() {

        String val = mname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            mname.setError("Please enter full name");
            return false;
        } else {
            mname.setError(null);
            return true;
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMapsBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();


        Places.initialize(view.getContext(), "AIzaSyD-5uq5mMzjJZQ-Bqu5ZKCdj08E1YxsuRk");

        PlacesClient placesClient = Places.createClient(view.getContext());

        mLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        getCurrentLoc();
        binding.currentLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLoc();
            }
        });

        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                Toast.makeText(view.getContext(), place.getName() + " , " + place.getId(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });


        return view;
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLoc() {


        if (isLocationEnabled()) {

            mLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        gotoLocation(location.getLatitude(), location.getLongitude());
                    }
                }
            });
        } else {

            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("GPS Permission")
                    .setMessage("GPS is required for this app to work .  Please enable GPS")
                    .setPositiveButton("yes", ((dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }))
                    .setCancelable(false)
                    .show();
        }


    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            gotoLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());

        }
    };

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Current Location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_dot)))
        ;

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.moveCamera(cameraUpdate);
        mMap.animateCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


    }
}
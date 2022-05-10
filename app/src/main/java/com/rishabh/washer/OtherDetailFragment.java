package com.rishabh.washer;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.rishabh.washer.databinding.FragmentOtherDetailBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OtherDetailFragment extends Fragment {


    int hour, minutes;
    int numm;
    private FragmentOtherDetailBinding binding;
    View view;

    String deliveryDate , pickupDate;
    String deliveryTime , pickupTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOtherDetailBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();


        binding.pickupNot.setVisibility(View.VISIBLE);
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
            String fullname = setLocation.getString("fullName", "");
            String phoneNo = setLocation.getString("phoneNo", "");


            binding.mPhoneNo.setText(phoneNo);
            binding.mfullName.setText(fullname);
            binding.deliveryNo.setVisibility(View.GONE);
            binding.deliveryYes.setVisibility(View.VISIBLE);
            binding.textView6.setText(address);
            binding.textView11.setText(address);
            binding.deliveryLocality.setText(locality);
            binding.textView27.setText(locality);

        } else {
            binding.pickupYes.setVisibility(View.GONE);
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

                Navigation.findNavController(view).navigate(R.id.action_otherDetailFragment_to_savedAddressFragment);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).popBackStack();
            }
        });
        //setting delivery address

        //Pick up schedule setup
        int i ;
        Calendar cal = Calendar.getInstance();
        for (i = 0; i <= 6; i++) {
            if (i == 0) {
                cal.add(Calendar.DATE, 0);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal.getTime());
                String fdate = mDate.format(cal.getTime());
                String fday = mDay.format(cal.getTime());

                binding.firstMonth.setText(fmonth);
                binding.firstDate.setText(fdate);
                binding.firstDay.setText(fday);

                String month =  binding.firstMonth.getText().toString();
                String date =  binding.firstDate.getText().toString();
                String day =  binding.firstDay.getText().toString();

                pickupDate = day + ", "+ month+ " " + date;
            }
            if (i == 1) {
                cal.add(Calendar.DATE, 1);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal.getTime());
                String fdate = mDate.format(cal.getTime());
                String fday = mDay.format(cal.getTime());

                binding.secondMonth.setText(fmonth);
                binding.secondDate.setText(fdate);
                binding.secondDay.setText(fday);
            }
            if (i == 2) {
                cal.add(Calendar.DATE, 1);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal.getTime());
                String fdate = mDate.format(cal.getTime());
                String fday = mDay.format(cal.getTime());

                binding.thirdMonth.setText(fmonth);
                binding.thirdDate.setText(fdate);
                binding.thirdDay.setText(fday);
            }
            if (i == 3) {
                cal.add(Calendar.DATE, 1);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal.getTime());
                String fdate = mDate.format(cal.getTime());
                String fday = mDay.format(cal.getTime());

                binding.forthMonth.setText(fmonth);
                binding.forthDate.setText(fdate);
                binding.forthDay.setText(fday);
            }
            if (i == 4) {
                cal.add(Calendar.DATE, 1);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal.getTime());
                String fdate = mDate.format(cal.getTime());
                String fday = mDay.format(cal.getTime());

                binding.fifthMonth.setText(fmonth);
                binding.fifthDate.setText(fdate);
                binding.fifthDay.setText(fday);
            }
        }

        binding.piklay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numm = 3                                                                       ;
                binding.piklay1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.card_background_layout));
                binding.piklay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));

                Calendar cal2 = Calendar.getInstance();
                for (int j = 0; j <= 6; j++) {
                    if (j == 0) {
                        cal2.add(Calendar.DATE, numm);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.firstMonth2.setText(fmonth);
                        binding.firstDate2.setText(fdate);
                        binding.firstDay2.setText(fday);
                    }
                    if (j == 1) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.secondMonth2.setText(fmonth);
                        binding.secondDate2.setText(fdate);
                        binding.secondDay2.setText(fday);
                    }
                    if (j == 2) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.thirdMonth2.setText(fmonth);
                        binding.thirdDate2.setText(fdate);
                        binding.thirdDay2.setText(fday);
                    }
                    if (j == 3) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.forthMonth2.setText(fmonth);
                        binding.forthDate2.setText(fdate);
                        binding.forthDay2.setText(fday);
                    }
                    if (j == 4) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.fifthMonth2.setText(fmonth);
                        binding.fifthDate2.setText(fdate);
                        binding.fifthDay2.setText(fday);
                    }
                }

                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));

               String month =  binding.firstMonth.getText().toString();
               String date =  binding.firstDate.getText().toString();
               String day =  binding.firstDay.getText().toString();

                pickupDate = day + ", "+ month+ " " + date;
                pickupTime = null;
                deliveryTime = null;
                deliveryDate = null;

                checkingDateAndTiming();
            }
        });
        binding.piklay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numm=4;
                binding.piklay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.card_background_layout));
                binding.piklay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                Calendar cal2 = Calendar.getInstance();
                for (int j = 0; j <= 6; j++) {
                    if (j == 0) {
                        cal2.add(Calendar.DATE, numm);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.firstMonth2.setText(fmonth);
                        binding.firstDate2.setText(fdate);
                        binding.firstDay2.setText(fday);
                    }
                    if (j == 1) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.secondMonth2.setText(fmonth);
                        binding.secondDate2.setText(fdate);
                        binding.secondDay2.setText(fday);
                    }
                    if (j == 2) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.thirdMonth2.setText(fmonth);
                        binding.thirdDate2.setText(fdate);
                        binding.thirdDay2.setText(fday);
                    }
                    if (j == 3) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.forthMonth2.setText(fmonth);
                        binding.forthDate2.setText(fdate);
                        binding.forthDay2.setText(fday);
                    }
                    if (j == 4) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.fifthMonth2.setText(fmonth);
                        binding.fifthDate2.setText(fdate);
                        binding.fifthDay2.setText(fday);
                    }
                }
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));


                String month =  binding.secondMonth.getText().toString();
                String date =  binding.secondDate.getText().toString();
                String day =  binding.secondDay.getText().toString();

                pickupDate = day + ", "+ month+ " " + date;
                pickupTime = null;
                deliveryTime = null;
                deliveryDate = null;

                checkingDateAndTiming();
            }
        });
        binding.piklay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numm=5;
                binding.piklay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay2.setBackground(ContextCompat.getDrawable(getContext(),R.color.white));
                binding.piklay3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.card_background_layout));
                binding.piklay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                Calendar cal2 = Calendar.getInstance();
                for (int j = 0; j <= 6; j++) {
                    if (j == 0) {
                        cal2.add(Calendar.DATE, numm);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.firstMonth2.setText(fmonth);
                        binding.firstDate2.setText(fdate);
                        binding.firstDay2.setText(fday);
                    }
                    if (j == 1) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.secondMonth2.setText(fmonth);
                        binding.secondDate2.setText(fdate);
                        binding.secondDay2.setText(fday);
                    }
                    if (j == 2) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.thirdMonth2.setText(fmonth);
                        binding.thirdDate2.setText(fdate);
                        binding.thirdDay2.setText(fday);
                    }
                    if (j == 3) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.forthMonth2.setText(fmonth);
                        binding.forthDate2.setText(fdate);
                        binding.forthDay2.setText(fday);
                    }
                    if (j == 4) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.fifthMonth2.setText(fmonth);
                        binding.fifthDate2.setText(fdate);
                        binding.fifthDay2.setText(fday);
                    }
                }
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));


                String month =  binding.thirdMonth.getText().toString();
                String date =  binding.thirdDate.getText().toString();
                String day =  binding.thirdDay.getText().toString();

                pickupDate = day + ", "+ month+ " " + date;
                pickupTime = null;
                deliveryTime = null;
                deliveryDate = null;

                checkingDateAndTiming();
            }
        });
        binding.piklay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numm = 6;
                binding.piklay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.card_background_layout));
                binding.piklay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                Calendar cal2 = Calendar.getInstance();
                for (int j = 0; j <= 6; j++) {
                    if (j == 0) {
                        cal2.add(Calendar.DATE, numm);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.firstMonth2.setText(fmonth);
                        binding.firstDate2.setText(fdate);
                        binding.firstDay2.setText(fday);
                    }
                    if (j == 1) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.secondMonth2.setText(fmonth);
                        binding.secondDate2.setText(fdate);
                        binding.secondDay2.setText(fday);
                    }
                    if (j == 2) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.thirdMonth2.setText(fmonth);
                        binding.thirdDate2.setText(fdate);
                        binding.thirdDay2.setText(fday);
                    }
                    if (j == 3) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.forthMonth2.setText(fmonth);
                        binding.forthDate2.setText(fdate);
                        binding.forthDay2.setText(fday);
                    }
                    if (j == 4) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.fifthMonth2.setText(fmonth);
                        binding.fifthDate2.setText(fdate);
                        binding.fifthDay2.setText(fday);
                    }
                }
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));


                String month =  binding.forthMonth.getText().toString();
                String date =  binding.forthDate.getText().toString();
                String day =  binding.forthDay.getText().toString();

                pickupDate = day + ", "+ month+ " " + date;
                pickupTime = null;
                deliveryTime = null;
                deliveryDate = null;
                checkingDateAndTiming();
            }
        });
        binding.piklay5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numm = 7;
                binding.piklay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay2.setBackground(ContextCompat.getDrawable(getContext(),R.color.white));
                binding.piklay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.piklay5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.card_background_layout));
                Calendar cal2 = Calendar.getInstance();
                for (int j = 0; j <= 6; j++) {
                    if (j == 0) {
                        cal2.add(Calendar.DATE, numm);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.firstMonth2.setText(fmonth);
                        binding.firstDate2.setText(fdate);
                        binding.firstDay2.setText(fday);
                    }
                    if (j == 1) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.secondMonth2.setText(fmonth);
                        binding.secondDate2.setText(fdate);
                        binding.secondDay2.setText(fday);
                    }
                    if (j == 2) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.thirdMonth2.setText(fmonth);
                        binding.thirdDate2.setText(fdate);
                        binding.thirdDay2.setText(fday);
                    }
                    if (j == 3) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.forthMonth2.setText(fmonth);
                        binding.forthDate2.setText(fdate);
                        binding.forthDay2.setText(fday);
                    }
                    if (j == 4) {
                        cal2.add(Calendar.DATE, 1);
                        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                        SimpleDateFormat mDate = new SimpleDateFormat("d");
                        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                        String fmonth = mMonth.format(cal2.getTime());
                        String fdate = mDate.format(cal2.getTime());
                        String fday = mDay.format(cal2.getTime());

                        binding.fifthMonth2.setText(fmonth);
                        binding.fifthDate2.setText(fdate);
                        binding.fifthDay2.setText(fday);
                    }
                }
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));


                String month =  binding.fifthMonth.getText().toString();
                String date =  binding.fifthDate.getText().toString();
                String day =  binding.fifthDay.getText().toString();

                pickupDate = day + ", "+ month+ " " + date;
                pickupTime = null;
                deliveryTime = null;
                deliveryDate = null;

                checkingDateAndTiming();
            }
        });

        //delivery sedule setup

        Calendar cal2 = Calendar.getInstance();
        for (int j = 0; j <= 6; j++) {
            if (j == 0) {
                cal2.add(Calendar.DATE, 3);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal2.getTime());
                String fdate = mDate.format(cal2.getTime());
                String fday = mDay.format(cal2.getTime());

                binding.firstMonth2.setText(fmonth);
                binding.firstDate2.setText(fdate);
                binding.firstDay2.setText(fday);



            }
            if (j == 1) {
                cal2.add(Calendar.DATE, 1);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal2.getTime());
                String fdate = mDate.format(cal2.getTime());
                String fday = mDay.format(cal2.getTime());

                binding.secondMonth2.setText(fmonth);
                binding.secondDate2.setText(fdate);
                binding.secondDay2.setText(fday);
            }
            if (j == 2) {
                cal2.add(Calendar.DATE, 1);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal2.getTime());
                String fdate = mDate.format(cal2.getTime());
                String fday = mDay.format(cal2.getTime());

                binding.thirdMonth2.setText(fmonth);
                binding.thirdDate2.setText(fdate);
                binding.thirdDay2.setText(fday);
            }
            if (j == 3) {
                cal2.add(Calendar.DATE, 1);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal2.getTime());
                String fdate = mDate.format(cal2.getTime());
                String fday = mDay.format(cal2.getTime());

                binding.forthMonth2.setText(fmonth);
                binding.forthDate2.setText(fdate);
                binding.forthDay2.setText(fday);
            }
            if (j == 4) {
                cal2.add(Calendar.DATE, 1);
                SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
                SimpleDateFormat mDate = new SimpleDateFormat("d");
                SimpleDateFormat mDay = new SimpleDateFormat("EEE");
                String fmonth = mMonth.format(cal2.getTime());
                String fdate = mDate.format(cal2.getTime());
                String fday = mDay.format(cal2.getTime());

                binding.fifthMonth2.setText(fmonth);
                binding.fifthDate2.setText(fdate);
                binding.fifthDay2.setText(fday);
            }
        }


        binding.dellay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.card_background_layout));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));

                String month =  binding.firstMonth2.getText().toString();
                String date =  binding.firstDate2.getText().toString();
                String day =  binding.firstDay2.getText().toString();

                deliveryDate = day + ", "+ month+ " " + date;

                binding.firstDeliveryTiming.setEnabled(true);
                binding.secondDeliveryTiming.setEnabled(true);
                binding.thirdDeliveryTiming.setEnabled(true);
                binding.fourthDeliveryTiming.setEnabled(true);
                binding.deliveryGroup.clearCheck();

                if (binding.firstPickupTiming.isChecked()){
                    binding.firstDeliveryTiming.setEnabled(true);
                    binding.secondDeliveryTiming.setEnabled(true);
                    binding.thirdDeliveryTiming.setEnabled(true);
                    binding.fourthDeliveryTiming.setEnabled(true);
                }if (binding.secondPickupTiming.isChecked()){
                    binding.firstDeliveryTiming.setEnabled(false);
                    binding.secondDeliveryTiming.setEnabled(true);
                    binding.thirdDeliveryTiming.setEnabled(true);
                    binding.fourthDeliveryTiming.setEnabled(true);
                }if (binding.thirdPickupTiming.isChecked()){
                    binding.firstDeliveryTiming.setEnabled(false);
                    binding.secondDeliveryTiming.setEnabled(false);
                    binding.thirdDeliveryTiming.setEnabled(true);
                    binding.fourthDeliveryTiming.setEnabled(true);
                }if (binding.fourthPickupTiming.isChecked()){
                    binding.firstDeliveryTiming.setEnabled(false);
                    binding.secondDeliveryTiming.setEnabled(false);
                    binding.thirdDeliveryTiming.setEnabled(false);
                    binding.fourthDeliveryTiming.setEnabled(true);
                }

            }
        });
        binding.dellay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.card_background_layout));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                String month =  binding.secondMonth2.getText().toString();
                String date =  binding.secondDate2.getText().toString();
                String day =  binding.secondDay2.getText().toString();

                deliveryDate = day + ", "+ month+ " " + date;
                deliveryTime=null;

                binding.firstDeliveryTiming.setEnabled(true);
                binding.secondDeliveryTiming.setEnabled(true);
                binding.thirdDeliveryTiming.setEnabled(true);
                binding.fourthDeliveryTiming.setEnabled(true);
                binding.deliveryGroup.clearCheck();
            }
        });
        binding.dellay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(),R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.card_background_layout));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));

                String month =  binding.thirdMonth2.getText().toString();
                String date =  binding.thirdDate2.getText().toString();
                String day =  binding.thirdDay2.getText().toString();

                deliveryDate = day + ", "+ month+ " " + date;
                deliveryTime=null;


                binding.firstDeliveryTiming.setEnabled(true);
                binding.secondDeliveryTiming.setEnabled(true);
                binding.thirdDeliveryTiming.setEnabled(true);
                binding.fourthDeliveryTiming.setEnabled(true);
                binding.deliveryGroup.clearCheck();
            }
        });
        binding.dellay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.card_background_layout));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));

                String month =  binding.forthMonth2.getText().toString();
                String date =  binding.forthDate2.getText().toString();
                String day =  binding.forthDay2.getText().toString();

                deliveryDate = day + ", "+ month+ " " + date;
                deliveryTime=null;

                binding.firstDeliveryTiming.setEnabled(true);
                binding.secondDeliveryTiming.setEnabled(true);
                binding.thirdDeliveryTiming.setEnabled(true);
                binding.fourthDeliveryTiming.setEnabled(true);
                binding.deliveryGroup.clearCheck();
            }
        });
        binding.dellay5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(),R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.card_background_layout));

                String month =  binding.fifthMonth2.getText().toString();
                String date =  binding.fifthDate2.getText().toString();
                String day =  binding.fifthDay2.getText().toString();

                deliveryDate = day + ", "+ month+ " " + date;
                deliveryTime = null;

                binding.firstDeliveryTiming.setEnabled(true);
                binding.secondDeliveryTiming.setEnabled(true);
                binding.thirdDeliveryTiming.setEnabled(true);
                binding.fourthDeliveryTiming.setEnabled(true);
                binding.deliveryGroup.clearCheck();
            }
        });



        checkingDateAndTiming();


        binding.selectPickAddressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_otherDetailFragment_to_savedAddressFragment);


            }


        });







        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.firstPickupTiming.isChecked()){
                    pickupTime = "6 AM - 9AM";
                }if (binding.secondPickupTiming.isChecked()){
                    pickupTime = "9 AM - 12 AM";
                }if (binding.thirdPickupTiming.isChecked()){
                    pickupTime = "2 PM - 5 PM";
                }if (binding.fourthPickupTiming.isChecked()){
                    pickupTime = "5 PM - 9 PM";
                }

                if (binding.firstDeliveryTiming.isChecked()){
                    deliveryTime = "6 AM - 9AM";
                }if (binding.secondDeliveryTiming.isChecked()){
                    deliveryTime = "9 AM - 12 AM";
                }if (binding.thirdDeliveryTiming.isChecked()){
                    deliveryTime = "2 PM - 5 PM";
                }if (binding.fourthDeliveryTiming.isChecked()){
                    deliveryTime = "5 PM - 9 PM";
                }
                if (pickupDate ==null) {
                    Toast.makeText(getContext(), "Select Pickup date", Toast.LENGTH_SHORT).show();
                } else if (pickupTime==null) {
                    Toast.makeText(getContext(), "Select Pickup Time", Toast.LENGTH_SHORT).show();
                } else if (deliveryDate == null) {
                    Toast.makeText(getContext(), "Select Delivery Date", Toast.LENGTH_SHORT).show();
                } else if (deliveryTime==null) {
                    Toast.makeText(getContext(), "Select Delivery Time", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences setLocation = getActivity().getSharedPreferences("LOCATION", MODE_PRIVATE);
                    String address = setLocation.getString("address", "");
                    String locality = setLocation.getString("locality", "");
                    String pincode = setLocation.getString("pincode", "");
                    String latitude = setLocation.getString("latitude", "");
                    String longitude = setLocation.getString("longitude", "");
                    String phoneNo = setLocation.getString("phoneNo", "");
                    String houseNo = setLocation.getString("houseNo", "");


                    String landmark = setLocation.getString("landmark", "");
                    String fullName = setLocation.getString("fullName", "");

                    Bundle detail = new Bundle();
                    detail.putString("serviceType", getArguments().getString("serviceType"));
                    detail.putString("packagingType", getArguments().getString("packagingType"));
                    detail.putString("additionalPrice",getArguments().getString("additionalPrice"));

                    detail.putString("pricing", getArguments().getString("pricing"));
                    detail.putString("totalPricing", getArguments().getString("totalPricing"));

                    detail.putString("totalItem" , getArguments().getString("totalItem"));
                    detail.putString("colorPreference", getArguments().getString("colorPreference"));
                    detail.putString("washingTemperature", getArguments().getString("washingTemperature"));
                    detail.putBoolean("dryHeater", getArguments().getBoolean("dryHeater"));
                    detail.putBoolean("scentedDetergent", getArguments().getBoolean("scentedDetergent"));
                    detail.putBoolean("useSoftner", getArguments().getBoolean("useSoftner"));
                    detail.putString("additionalNote", getArguments().getString("additionalNote"));

                    //passing location
                    detail.putString("address", address);
                    detail.putString("locality", locality);
                    detail.putString("pincode", pincode);
                    detail.putString("latitude", latitude);
                    detail.putString("longitude", longitude);
                    detail.putString("phoneNo", phoneNo);
                    detail.putString("houseNo", houseNo);
                    detail.putString("landmark", landmark);
                    detail.putString("fullName", fullName);

                    //passing Date and time
                    detail.putString("pickupDate", pickupDate);

                    detail.putString("deliveryDate", deliveryDate);

                    detail.putString("pickupTime", pickupTime);

                    detail.putString("deliveryTime", deliveryTime);


                    if (getArguments().getString("serviceType").equals("Wash And Iron") || getArguments().getString("serviceType").equals("Wash And Fold")) {
                        Navigation.findNavController(view).navigate(R.id.action_otherDetailFragment_to_noPaymentCheckoutFragment, detail);
                    } else {

                        Navigation.findNavController(view).navigate(R.id.action_otherDetailFragment_to_checkoutDetailFragment, detail);
                    }

                }
            }
        });


        return view;
    }

    private void checkingDateAndTiming() {

        binding.firstPickupTiming.setEnabled(true);
        binding.secondPickupTiming.setEnabled(true);
        binding.thirdPickupTiming.setEnabled(true);
        binding.fourthPickupTiming.setEnabled(true);
        binding.firstDeliveryTiming.setEnabled(true);
        binding.secondDeliveryTiming.setEnabled(true);
        binding.thirdDeliveryTiming.setEnabled(true);
        binding.fourthDeliveryTiming.setEnabled(true);

        binding.firstPickupTiming.setSelected(false);
        binding.secondPickupTiming.setSelected(false);
        binding.thirdPickupTiming.setSelected(false);
        binding.fourthPickupTiming.setSelected(false);
        binding.firstDeliveryTiming.setSelected(false);
        binding.secondDeliveryTiming.setSelected(false);
        binding.thirdDeliveryTiming.setSelected(false);
        binding.fourthDeliveryTiming.setSelected(false);
        binding.pickupGroup.clearCheck();
        binding.deliveryGroup.clearCheck();



        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, 0);
        SimpleDateFormat mMonth = new SimpleDateFormat("MMM");
        SimpleDateFormat mDate = new SimpleDateFormat("d");
        SimpleDateFormat mDay = new SimpleDateFormat("EEE");
        String fmonth = mMonth.format(calendar1.getTime());
        String fdate = mDate.format(calendar1.getTime());
        String fday = mDay.format(calendar1.getTime());

        String todayDate = fday + ", "+ fmonth+ " " + fdate;

        if (todayDate.equals(pickupDate) && currentHour>=5){
            binding.firstPickupTiming.setEnabled(false);
        }
        if (todayDate.equals(pickupDate) && currentHour>=8){
            binding.firstPickupTiming.setEnabled(false);
            binding.secondPickupTiming.setEnabled(false);
        }
        if (todayDate.equals(pickupDate) && currentHour>=13){
            binding.firstPickupTiming.setEnabled(false);
            binding.secondPickupTiming.setEnabled(false);
            binding.thirdPickupTiming.setEnabled(false);
        }
        if (todayDate.equals(pickupDate) && currentHour>=16){
            binding.firstPickupTiming.setEnabled(false);
            binding.secondPickupTiming.setEnabled(false);
            binding.thirdPickupTiming.setEnabled(false);
            binding.fourthPickupTiming.setEnabled(false);
        }

        binding.firstPickupTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.firstDeliveryTiming.setEnabled(true);
                binding.secondDeliveryTiming.setEnabled(true);
                binding.thirdDeliveryTiming.setEnabled(true);
                binding.fourthDeliveryTiming.setEnabled(true);
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));

                binding.deliveryGroup.clearCheck();
                deliveryDate = null;
                deliveryTime = null;
            }
        });
        binding.secondPickupTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.firstDeliveryTiming.setEnabled(false);
                binding.secondDeliveryTiming.setEnabled(true);
                binding.thirdDeliveryTiming.setEnabled(true);
                binding.fourthDeliveryTiming.setEnabled(true);
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));

                binding.deliveryGroup.clearCheck();
                deliveryDate = null;
                deliveryTime = null;
            }
        });
        binding.thirdPickupTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.firstDeliveryTiming.setEnabled(false);
                binding.secondDeliveryTiming.setEnabled(false);
                binding.thirdDeliveryTiming.setEnabled(true);
                binding.fourthDeliveryTiming.setEnabled(true);
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));

                binding.deliveryGroup.clearCheck();
                deliveryDate = null;
                deliveryTime = null;

            }
        });
        binding.fourthPickupTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.firstDeliveryTiming.setEnabled(false);
                binding.secondDeliveryTiming.setEnabled(false);
                binding.thirdDeliveryTiming.setEnabled(false);
                binding.fourthDeliveryTiming.setEnabled(true);
                binding.dellay1.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay2.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay3.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay4.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));
                binding.dellay5.setBackground(ContextCompat.getDrawable(getContext(), R.color.white));

                binding.deliveryGroup.clearCheck();
                deliveryDate = null;
                deliveryTime = null;
            }
        });
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }


}
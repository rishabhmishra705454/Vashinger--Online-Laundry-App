package com.rishabh.washer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;


public class SucessDialogFragment extends Fragment {

    OnBackPressedCallback callback;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sucess_dialog, container, false);

        MaterialButton button = view.findViewById(R.id.doneBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.setEnabled(false);
                Navigation.findNavController(view).navigate(R.id.action_sucessDialogFragment_to_nav_home);

            }
        });


         callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                callback.setEnabled(false);
                Navigation.findNavController(view).navigate(R.id.action_sucessDialogFragment_to_nav_home);


            }
        };
        getActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);



        return view ;
    }

}
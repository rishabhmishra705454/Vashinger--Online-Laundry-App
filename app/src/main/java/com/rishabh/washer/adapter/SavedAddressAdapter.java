package com.rishabh.washer.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rishabh.washer.R;
import com.rishabh.washer.model.SavedAddressModel;

import java.util.ArrayList;

public class SavedAddressAdapter extends RecyclerView.Adapter<SavedAddressAdapter.ViewHolder> {

    ArrayList<SavedAddressModel> savedAddressModalArrayList;
    Context context;
    SavedAddressAdapter addressAdapter;
    View view;

    public SavedAddressAdapter(ArrayList<SavedAddressModel> savedAddressModalArrayList, Context context, View view) {
        this.savedAddressModalArrayList = savedAddressModalArrayList;
        this.context = context;
        this.view = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SavedAddressAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.saved_address_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SavedAddressModel addressModal = savedAddressModalArrayList.get(position);
        holder.fullName.setText(addressModal.getFullName());
        holder.address.setText(addressModal.getAddress());
        holder.phoneNo.setText(addressModal.getPhoneNo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences setLocation = context.getSharedPreferences("LOCATION", MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = setLocation.edit();
                editor.putString("address", addressModal.getAddress());
                editor.putString("pincode", addressModal.getPincode());
                editor.putString("locality", addressModal.getLocality());
                editor.putString("latitude", addressModal.getLatitude());
                editor.putString("longitude", addressModal.getLongitude());
                editor.putString("phoneNo", addressModal.getPhoneNo());
                editor.putString("houseNo", addressModal.getHouseNo());
                editor.putString("landmark", addressModal.getLandmark());
                editor.putString("fullName", addressModal.getFullName());
                editor.putString("id", addressModal.getId());
                editor.apply();

                Navigation.findNavController(view).popBackStack();

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Address").child(uid);
                reference.child(addressModal.getId()).removeValue();

            }
        });


    }


    @Override
    public int getItemCount() {
        return savedAddressModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fullName, address, phoneNo, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.mFullName);
            address = itemView.findViewById(R.id.mAddress);
            phoneNo = itemView.findViewById(R.id.mPhoneNo);
            delete = itemView.findViewById(R.id.deleteBtn);
        }
    }
}


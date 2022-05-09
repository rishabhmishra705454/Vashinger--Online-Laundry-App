package com.rishabh.washer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rishabh.washer.R;
import com.rishabh.washer.model.AddCartModel;
import com.rishabh.washer.model.AddDetailModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddDetailIronAdapter extends RecyclerView.Adapter<AddDetailIronAdapter.ViewHolder> {

    private FirebaseAuth mAuth;
    private Context context;
    private ArrayList<AddDetailModel> addDetailModelArrayList;

    public AddDetailIronAdapter(Context context, ArrayList<AddDetailModel> addDetailModelArrayList) {
        this.context = context;
        this.addDetailModelArrayList = addDetailModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_detail_recycler_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String uid = user.getUid();

        AddDetailModel addDetailModel = addDetailModelArrayList.get(position);
        holder.title.setText(addDetailModel.getTitle());
        holder.price.setText(addDetailModel.getPrice());
        Picasso.get().load(addDetailModel.getImage()).into(holder.image);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("cart").child("iron").child(uid);
                AddCartModel addCartModel = new AddCartModel(addDetailModel.getImage() , addDetailModel.getTitle() , addDetailModel.getPrice() , "1", Integer.toString(Integer.valueOf(addDetailModel.getPrice())* 1) ,"added");

                myRef.child(addDetailModel.getTitle()).setValue(addCartModel);
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return addDetailModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView price;
        TextView title;

        ImageButton add ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textView);
            price = itemView.findViewById(R.id.price);
            add = itemView.findViewById(R.id.button);
        }
    }
}

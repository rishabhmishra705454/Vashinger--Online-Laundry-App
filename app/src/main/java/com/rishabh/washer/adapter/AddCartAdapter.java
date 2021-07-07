package com.rishabh.washer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.rishabh.washer.R;
import com.rishabh.washer.model.AddCartModel;
import com.rishabh.washer.model.AddDetailModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddCartAdapter extends RecyclerView.Adapter<AddCartAdapter.ViewHolder> {

    private FirebaseAuth mAuth;
    private Context context;
    private ArrayList<AddCartModel> addCartModelArrayList;



    int totalQuantity;

    public AddCartAdapter( Context context, ArrayList<AddCartModel> addCartModelArrayList) {
        this.context = context;
        this.addCartModelArrayList = addCartModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.landry_cart_recycler_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String uid = user.getUid();

        AddCartModel addCartModel = addCartModelArrayList.get(position);
        holder.title.setText(addCartModel.getTitle());
        holder.quantity.setText(addCartModel.getQuantity());
        Picasso.get().load(addCartModel.getImage()).into(holder.image);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.quantity.getText().toString());


                if (quantity < 10) {

                     totalQuantity = quantity + 1;

                    AddCartModel addCartModel = addCartModelArrayList.get(position);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("cart").child(uid).child(addCartModel.getTitle());

                    AddCartModel addCartModel1 = new AddCartModel(addCartModel.getImage(), addCartModel.getTitle(),addCartModel.getPrice() , Integer.toString(totalQuantity) ,  Integer.toString(Integer.valueOf(addCartModel.getPrice())* totalQuantity), " added") ;

                    myRef.setValue(addCartModel1);

                }
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = Integer.parseInt(holder.quantity.getText().toString());


                if (quantity > 1) {

                    totalQuantity = quantity - 1;

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("cart").child(uid).child(addCartModel.getTitle());

                    AddCartModel addCartModel1 = new AddCartModel(addCartModel.getImage(), addCartModel.getTitle(),addCartModel.getPrice() , Integer.toString(totalQuantity) , Integer.toString(Integer.valueOf(addCartModel.getPrice())* totalQuantity) ," added") ;

                    myRef.setValue(addCartModel1);

                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCartModel addCartModel = addCartModelArrayList.get(position);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("cart").child(uid).child(addCartModel.getTitle());
                myRef.removeValue();

                addCartModelArrayList.remove(addCartModel.getTitle());
            }
        });



    }

    @Override
    public int getItemCount() {
        return addCartModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, quantity;
        ImageButton add, minus , remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView3);
            title = itemView.findViewById(R.id.textView5);
            quantity = itemView.findViewById(R.id.textView4);
            add = itemView.findViewById(R.id.addBtn);
            minus = itemView.findViewById(R.id.imageButton);

            remove = itemView.findViewById(R.id.imageButton2);
        }
    }
}

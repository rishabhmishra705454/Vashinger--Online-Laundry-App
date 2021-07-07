package com.rishabh.washer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rishabh.washer.R;
import com.rishabh.washer.model.DisplayOrderModel;

import java.util.ArrayList;

public class DisplayOrderAdapter extends RecyclerView.Adapter<DisplayOrderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DisplayOrderModel> displayOrderModelArrayList;

    public DisplayOrderAdapter(Context context, ArrayList<DisplayOrderModel> displayOrderModelArrayList) {
        this.context = context;
        this.displayOrderModelArrayList = displayOrderModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_order_layout, parent, false);

        return new DisplayOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DisplayOrderModel displayOrderModel = displayOrderModelArrayList.get(position);
        holder.serviceType.setText(displayOrderModel.getServiceType());
        holder.dateTime.setText(displayOrderModel.getOrderDate() + " | " + displayOrderModel.getOrderTime());
        holder.status.setText(displayOrderModel.getStatus());
        holder.pickupTime.setText(displayOrderModel.getPickupDate() + " | " +displayOrderModel.getPickupTime());
        holder.pickupAddress.setText(displayOrderModel.getAddress());
        holder.deliveryTime.setText(displayOrderModel.getDeliveryDate() + " | " + displayOrderModel.getDeliveryTime());
        holder.deliveryAddress.setText(displayOrderModel.getAddress());
        holder.items.setText(displayOrderModel.getTotalItem());
        holder.price.setText("\u20B9 " +displayOrderModel.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return displayOrderModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView serviceType , dateTime , status  , pickupTime , pickupAddress , deliveryTime , deliveryAddress , items , price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceType = itemView.findViewById(R.id.textView16);
            dateTime = itemView.findViewById(R.id.dateTime);
            status = itemView.findViewById(R.id.status);
            pickupTime = itemView.findViewById(R.id.pickupTime);
            pickupAddress = itemView.findViewById(R.id.pickupAddress);
            deliveryTime = itemView.findViewById(R.id.deliveryTime);
            deliveryAddress = itemView.findViewById(R.id.deliveryAddress);
            items = itemView.findViewById(R.id.cloths);
            price = itemView.findViewById(R.id.price);
        }
    }
}

package com.rishabh.washer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.rishabh.washer.R;
import com.rishabh.washer.model.DisplayOrderModel;

import java.util.ArrayList;

public class DisplayOrderAdapter extends RecyclerView.Adapter<DisplayOrderAdapter.ViewHolder> {

    private Context context;
    View view;
    private ArrayList<DisplayOrderModel> displayOrderModelArrayList;

    public DisplayOrderAdapter(Context context, ArrayList<DisplayOrderModel> displayOrderModelArrayList) {
        this.context = context;
        this.displayOrderModelArrayList = displayOrderModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_order_layout, parent, false);

        return new DisplayOrderAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DisplayOrderModel displayOrderModel = displayOrderModelArrayList.get(position);
      //  holder.serviceType.setText(displayOrderModel.getServiceType());
       // holder.dateTime.setText(displayOrderModel.getOrderDate() + " | " + displayOrderModel.getOrderTime());
       // holder.status.setText(displayOrderModel.getStatus());
      //  holder.pickupTime.setText(displayOrderModel.getPickupDate() + " | " +displayOrderModel.getPickupTime());
      //  holder.pickupAddress.setText(displayOrderModel.getAddress());
       // holder.deliveryTime.setText(displayOrderModel.getDeliveryDate() + " | " + displayOrderModel.getDeliveryTime());
      //  holder.deliveryAddress.setText(displayOrderModel.getAddress());
       // holder.items.setText(displayOrderModel.getTotalItem());
       // holder.price.setText("₦ " +displayOrderModel.getTotalPrice());

        holder.orderPlaced.setText("placed on " + displayOrderModel.getOrderDate() + ", " + displayOrderModel.getOrderTime());
        holder.serviceType.setText(displayOrderModel.getServiceType() + " (" + displayOrderModel.getTotalItem() + " items )");
        holder.pricing.setText("₦ " + displayOrderModel.getTotalPrice());
        holder.orderId.setText("order id : " + displayOrderModel.getId());
        holder.status.setText(displayOrderModel.getStatus());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId" , displayOrderModel.getId());
                Navigation.findNavController(view).navigate(R.id.action_nav_order_to_displayOrderDetailFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayOrderModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //TextView serviceType , dateTime , status  , pickupTime , pickupAddress , deliveryTime , deliveryAddress , items , price;

        TextView orderPlaced , serviceType , pricing , orderId , status ;
        TextView button ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           // serviceType = itemView.findViewById(R.id.textView16);
            //dateTime = itemView.findViewById(R.id.dateTime);
           // status = itemView.findViewById(R.id.status);
           // pickupTime = itemView.findViewById(R.id.pickupTime);
           // pickupAddress = itemView.findViewById(R.id.pickupAddress);
           // deliveryTime = itemView.findViewById(R.id.deliveryTime);
           // deliveryAddress = itemView.findViewById(R.id.deliveryAddress);
           // items = itemView.findViewById(R.id.cloths);
          //  price = itemView.findViewById(R.id.price);
            orderPlaced = itemView.findViewById(R.id.orderDateTime);
            serviceType = itemView.findViewById(R.id.serviceType);
            pricing = itemView.findViewById(R.id.pricing);
            orderId = itemView.findViewById(R.id.orderId);
            status = itemView.findViewById(R.id.orderStatus);
            button = itemView.findViewById(R.id.viewDetail);
        }
    }
}

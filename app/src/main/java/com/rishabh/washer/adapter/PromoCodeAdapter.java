package com.rishabh.washer.adapter;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rishabh.washer.R;
import com.rishabh.washer.model.PromoCodeModel;

import java.util.ArrayList;

public class PromoCodeAdapter extends RecyclerView.Adapter<PromoCodeAdapter.ViewHolder> {

    Context context;
    ArrayList<PromoCodeModel> promoCodeModelArrayList;

    private ClipboardManager myClipboard;
    private ClipData myClip;
    public PromoCodeAdapter(Context context, ArrayList<PromoCodeModel> promoCodeModelArrayList) {
        this.context = context;
        this.promoCodeModelArrayList = promoCodeModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.display_promo_code_layout , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PromoCodeModel promoCodeModel = promoCodeModelArrayList.get(position);
        holder.title1.setText(promoCodeModel.getTitle());
        holder.description.setText(promoCodeModel.getDescription());
        holder.minOrderPrice.setText( "- min value ₹ " +  promoCodeModel.getMinOrderPrice());
        holder.maximumDiscount.setText( "- maximum discount ₹ " +  promoCodeModel.getMaxDiscountPrice());
        holder.promoCode.setText(promoCodeModel.getPromoCode());

        holder.promoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);

                String text = holder.promoCode.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return promoCodeModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title1, description ,minOrderPrice,maximumDiscount,promoCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title1 = itemView.findViewById(R.id.title1);
            description = itemView.findViewById(R.id.description1);
            minOrderPrice = itemView.findViewById(R.id.minOrderPrice1);
            maximumDiscount = itemView.findViewById(R.id.maximumDiscount1);
            promoCode = itemView.findViewById(R.id.promoCode1);
        }
    }
}

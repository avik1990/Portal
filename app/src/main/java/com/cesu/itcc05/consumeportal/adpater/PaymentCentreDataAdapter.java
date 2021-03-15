package com.cesu.itcc05.consumeportal.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cesu.itcc05.consumeportal.OfferSchemeWebAcivity;
import com.cesu.itcc05.consumeportal.R;
import com.cesu.itcc05.consumeportal.modal.OfferSchemModal;
import com.cesu.itcc05.consumeportal.modal.PaymentCenterModal;

import java.util.List;

public class PaymentCentreDataAdapter extends RecyclerView.Adapter<PaymentCentreDataAdapter.ViewHolder> {


    private Context mContext;
    private List<PaymentCenterModal>bannerModals;


    public PaymentCentreDataAdapter(Context context, List<PaymentCenterModal> list) {
        this.bannerModals = list;
        mContext=context;
    }

    @NonNull
    @Override
    public PaymentCentreDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item_payment, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentCentreDataAdapter.ViewHolder holder, final int position) {
        holder.tv_add_val.setText(bannerModals.get(position).getOfficeAddress());
        holder.tv_id_value.setText(bannerModals.get(position).getOfficeId());



    }

    @Override
    public int getItemCount() {
        return bannerModals.size();
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_add_val,tv_id_value;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_add_val=itemView.findViewById(R.id.tv_add_val);
            tv_id_value=itemView.findViewById(R.id.tv_id_value);
        }
    }
}

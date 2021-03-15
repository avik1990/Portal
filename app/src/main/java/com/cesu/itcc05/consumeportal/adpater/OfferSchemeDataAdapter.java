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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.cesu.itcc05.consumeportal.OfferSchemeWebAcivity;
import com.cesu.itcc05.consumeportal.R;
import com.cesu.itcc05.consumeportal.modal.BannerModal;
import com.cesu.itcc05.consumeportal.modal.OfferSchemModal;
import java.util.List;

public class OfferSchemeDataAdapter extends RecyclerView.Adapter<OfferSchemeDataAdapter.ViewHolder> {


    private Context mContext;
    private List<OfferSchemModal>bannerModals;


    public OfferSchemeDataAdapter(Context context, List<OfferSchemModal> list) {
        this.bannerModals = list;
        mContext=context;
    }

    @NonNull
    @Override
    public OfferSchemeDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OfferSchemeDataAdapter.ViewHolder holder, final int position) {
        //holder.tv_inst.setText(listData.get(position));
        try {
            Glide.with(mContext)
                    .load(bannerModals.get(position).getImagePath())
                    .placeholder(R.drawable.logo)
                    .into(holder.iv_image);
        }catch (Exception e){

        }
        holder.cl_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, OfferSchemeWebAcivity.class);
                intent.putExtra("url",bannerModals.get(position).getImageClickUrl());
                mContext.startActivity(intent);
            }
        });

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
        private ImageView iv_image;
        private CardView cl_image;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_image=itemView.findViewById(R.id.iv_image);
            cl_image=itemView.findViewById(R.id.cl_image);
        }
    }
}

package com.cesu.itcc05.consumeportal.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cesu.itcc05.consumeportal.R;
import com.cesu.itcc05.consumeportal.ViewPDFActivity;
import com.cesu.itcc05.consumeportal.modal.DocumentModal;
import com.cesu.itcc05.consumeportal.modal.PaymentCenterModal;

import java.util.List;

public class DocumentListDataAdapter extends RecyclerView.Adapter<DocumentListDataAdapter.ViewHolder> {


    private Context mContext;
    private List<DocumentModal>bannerModals;


    public DocumentListDataAdapter(Context context, List<DocumentModal> list) {
        this.bannerModals = list;
        mContext=context;
    }

    @NonNull
    @Override
    public DocumentListDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item_document, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentListDataAdapter.ViewHolder holder, final int position) {
        holder.tv_add_val.setText(bannerModals.get(position).getDOC_DESCR());
        holder.tv_id_value.setText(bannerModals.get(position).getDOC_NAME());

        holder.cl_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ViewPDFActivity.class);
                intent.putExtra("url",bannerModals.get(position).getDOC_PATH());
                intent.putExtra("name",bannerModals.get(position).getDOC_NAME());

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
        private TextView tv_add_val,tv_id_value;
        private CardView cl_image;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_add_val=itemView.findViewById(R.id.tv_add_val);
            tv_id_value=itemView.findViewById(R.id.tv_id_value);
            cl_image=itemView.findViewById(R.id.cl_image);
        }
    }
}

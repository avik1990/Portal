package com.cesu.itcc05.consumeportal.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cesu.itcc05.consumeportal.R;
import com.cesu.itcc05.consumeportal.modal.AddUserModal;
import com.cesu.itcc05.consumeportal.modal.ModalComplainDetails;

import java.util.List;

public class AddUserAdapter extends RecyclerView.Adapter<AddUserAdapter.ViewHolder> {


    private Context mContext;
    private List<AddUserModal>bannerModals;


    public AddUserAdapter(Context context, List<AddUserModal> list) {
        this.bannerModals = list;
        mContext=context;
    }

    @NonNull
    @Override
    public AddUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.add_user_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddUserAdapter.ViewHolder holder, final int position) {

        try {
            holder.tv_consumer_id.setText(bannerModals.get(position).getConsumerId());
            holder.tv_name.setText(bannerModals.get(position).getName());
            holder.tv_address.setText(bannerModals.get(position).getAddress());

        }
        catch (Exception ex){
            ex.printStackTrace();
        }




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
        private TextView tv_consumer_id,tv_name,tv_address;



        public ViewHolder(View itemView) {
            super(itemView);
            tv_consumer_id=itemView.findViewById(R.id.tv_consumer_id);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_address=itemView.findViewById(R.id.tv_address);


        }
    }
}

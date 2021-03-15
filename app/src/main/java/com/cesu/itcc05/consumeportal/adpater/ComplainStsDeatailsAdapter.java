package com.cesu.itcc05.consumeportal.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cesu.itcc05.consumeportal.R;
import com.cesu.itcc05.consumeportal.modal.ModalComplainDetails;
import com.cesu.itcc05.consumeportal.modal.PaymentCenterModal;

import java.util.List;

public class ComplainStsDeatailsAdapter extends RecyclerView.Adapter<ComplainStsDeatailsAdapter.ViewHolder> {


    private Context mContext;
    private List<ModalComplainDetails>bannerModals;


    public ComplainStsDeatailsAdapter(Context context, List<ModalComplainDetails> list) {
        this.bannerModals = list;
        mContext=context;
    }

    @NonNull
    @Override
    public ComplainStsDeatailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.complain_stst_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComplainStsDeatailsAdapter.ViewHolder holder, final int position) {

        try {
            holder.tv_complain_date_value.setText(bannerModals.get(position).getComplaint_date_time());
            holder.tv_complain_id_value.setText(bannerModals.get(position).getComplaintID());
            holder.tv_complain_type_value.setText(bannerModals.get(position).getComplaintType());
            holder.tv_complain_sub_type_value.setText(bannerModals.get(position).getComplaintSubType());
            holder.tv_complaint_details_value.setText(bannerModals.get(position).getComplaintDetails());
            holder.tv_action_value.setText(bannerModals.get(position).getActionTaken());
            holder.tv_revised_date_value.setText(bannerModals.get(position).getResolvedDateTime());
            holder.tv_comp_sts_value.setText(bannerModals.get(position).getComplaintStatus());
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
        private TextView tv_complain_date_value,tv_complain_id_value,tv_complain_type_value,
                tv_complain_sub_type_value,tv_complaint_details_value,tv_action_value
                ,tv_revised_date_value,tv_comp_sts_value;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_complain_date_value=itemView.findViewById(R.id.tv_complain_date_value);
            tv_complain_id_value=itemView.findViewById(R.id.tv_complain_id_value);

            tv_complain_type_value=itemView.findViewById(R.id.tv_complain_type_value);
            tv_complain_sub_type_value=itemView.findViewById(R.id.tv_complain_sub_type_value);
            tv_complaint_details_value=itemView.findViewById(R.id.tv_complaint_details_value);
            tv_action_value=itemView.findViewById(R.id.tv_action_value);
            tv_revised_date_value=itemView.findViewById(R.id.tv_revised_date_value);
            tv_comp_sts_value=itemView.findViewById(R.id.tv_comp_sts_value);

        }
    }
}

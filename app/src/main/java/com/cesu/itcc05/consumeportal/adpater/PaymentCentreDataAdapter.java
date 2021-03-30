package com.cesu.itcc05.consumeportal.adpater;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cesu.itcc05.consumeportal.R;
import com.cesu.itcc05.consumeportal.modal.PaymentCenterModal;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class PaymentCentreDataAdapter extends RecyclerView.Adapter<PaymentCentreDataAdapter.ViewHolder> implements Filterable {


    private Context mContext;
    private List<PaymentCenterModal> contactList;
    private List<PaymentCenterModal> contactListFiltered;
    PassCoordinates passCoordinates;
    String searchText = "";


    public interface PassCoordinates {
        void getCoordinates(String latitude, String longitude);
    }


    public PaymentCentreDataAdapter(Context context, List<PaymentCenterModal> list, PassCoordinates passCoordinates) {
        this.contactList = list;
        this.mContext = context;
        this.contactListFiltered = list;
        this.passCoordinates = passCoordinates;
    }

    @NonNull
    @Override
    public PaymentCentreDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item_payment, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentCentreDataAdapter.ViewHolder holder, final int position) {
      //  holder.tv_id_value.setText(contactListFiltered.get(position).getOfficeId());
        /*try {
            if (searchText != null || !searchText.isEmpty()) {
                //Log.e("searchTextDemo", searchText);
                holder.tv_id_value.setText(highlightText(searchText, contactListFiltered.get(position).getOfficeId()));
            } else {
                holder.tv_id_value.setText(contactListFiltered.get(position).getOfficeId());
            }
        } catch (Exception e) {

        }*/

        try {
            if (searchText != null || !searchText.isEmpty()) {
               // Log.e("searchTextDemo", searchText);
                holder.tv_add_val.setText(highlightText(searchText, contactListFiltered.get(position).getOfficeAddress()));
            } else {
                holder.tv_add_val.setText(contactListFiltered.get(position).getOfficeAddress());
            }
        } catch (Exception e) {

        }

        holder.ivLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                passCoordinates.getCoordinates(contactListFiltered.get(position).getOfficeLat(), contactListFiltered.get(position).getOfficeLong());
            }
        });
    }

    public static CharSequence highlightText(String search, String originalText) {
        if (search != null && !search.equalsIgnoreCase("")) {
            String normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
            int start = normalizedText.indexOf(search);
            if (start < 0) {
                return originalText;
            } else {
                Spannable highlighted = new SpannableString(originalText);
                while (start >= 0) {
                    int spanStart = Math.min(start, originalText.length());
                    int spanEnd = Math.min(start + search.length(), originalText.length());
                    highlighted.setSpan(new ForegroundColorSpan(Color.BLUE), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    start = normalizedText.indexOf(search, spanEnd);
                }
                return highlighted;
            }
        }
        return originalText;
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_add_val, tv_id_value;
        ImageView ivLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_add_val = itemView.findViewById(R.id.tv_add_val);
            tv_id_value = itemView.findViewById(R.id.tv_id_value);
            ivLocation = itemView.findViewById(R.id.ivLocation);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                searchText = charSequence.toString();
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<PaymentCenterModal> filteredList = new ArrayList<>();
                    for (PaymentCenterModal row : contactList) {
                        if (row.getOfficeAddress().toLowerCase().contains(charString.toLowerCase()) || row.getOfficeId().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    contactListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<PaymentCenterModal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}

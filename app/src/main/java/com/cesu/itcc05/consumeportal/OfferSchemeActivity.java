package com.cesu.itcc05.consumeportal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cesu.itcc05.consumeportal.adpater.OfferSchemeDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class OfferSchemeActivity extends AppCompatActivity {
    private RecyclerView rv_offer;
    private Context mContext;
    private OfferSchemeDataAdapter offerSchemeDataAdapter;
    private ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_scheme);
        rv_offer=findViewById(R.id.rv_offer);
        iv_back=findViewById(R.id.iv_backs);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mContext=this;
        initAdapter();

      //  List<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
        System.out.println("asdf=="+GlobalVariable.offerSchemModals.size());


    }

    private void initAdapter() {
        rv_offer.setLayoutManager(new LinearLayoutManager(mContext));

        offerSchemeDataAdapter = new OfferSchemeDataAdapter(mContext, GlobalVariable.offerSchemModals);
        rv_offer.setAdapter(offerSchemeDataAdapter);
        offerSchemeDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

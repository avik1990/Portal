package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cesu.itcc05.consumeportal.adpater.OfferSchemeDataAdapter;
import com.cesu.itcc05.consumeportal.adpater.PaymentCentreDataAdapter;
import com.cesu.itcc05.consumeportal.modal.BannerModal;
import com.cesu.itcc05.consumeportal.modal.OfferSchemModal;
import com.cesu.itcc05.consumeportal.modal.PaymentCenterModal;
import com.cesu.itcc05.consumeportal.modal.SliderAdapterExample;
import com.cesu.itcc05.consumeportal.modal.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PaymentCentreActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView recycler_payment;
    private ProgressDialog progressDialog;
    List<PaymentCenterModal>paymentCenterModals=new ArrayList<>();
    PaymentCentreDataAdapter paymentCentreDataAdapter;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_centre);
        mContext=this;
        recycler_payment=findViewById(R.id.recycler_payment);
        iv_back=findViewById(R.id.iv_backs);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initAdapter();


        if (CommonMethods.isNetworkAvailable(mContext)){
            String url="http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Offices_list?checksum=01091981";
            new fetchBannerData().execute(url);
        }
        else {
            Toast.makeText(mContext,"No internet connection, please connect to internet",Toast.LENGTH_SHORT).show();
        }


    }

    private  class fetchBannerData extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL=params[0];
            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent=null;
            Log.d("DemoApp", " strURL   " + strURL);

            try {
                URL url =null;
                url = new URL(strURL);
                URLConnection uc = url.openConnection();
                uc.setDoInput(true);
                BufferedReader in = null;
                in=new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String inputLine="";
                String inputLine1="";
                StringBuilder a = new StringBuilder();
                Log.d("DemoApp", " a size   " + a.length());
                while ((inputLine = in.readLine()) != null) {
                    a.append(inputLine);
                    inputLine1=inputLine;
                    //  Log.d("DemoApp", " input line " + a.toString());
                }
                in.close();

                Log.d("DemoApp", " fullString   " + a.toString());

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                Element element=doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("Table");

                paymentCenterModals.clear();
                for (int temp = 0; temp < nList.getLength(); temp++)
                {
                    Node node = nList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        PaymentCenterModal paymentCenterModal=new PaymentCenterModal();

                        paymentCenterModal.setOfficeId(getValue("OFFICE_ID", element2));
                        paymentCenterModal.setOfficeAddress(getValue("OFFICE_ADDRESS", element2));
                        paymentCenterModal.setOfficeLat(getValue("OFFICE_LAT", element2));
                        paymentCenterModal.setOfficeLat(getValue("OFFICE_LONG", element2));
                        paymentCenterModals.add(paymentCenterModal);
                    }
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

            return bodycontent;
        }
        @Override

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(PaymentCentreActivity.this, "Fetching Data", "Please Wait:: connecting to server");

            ConnectivityManager cm = (ConnectivityManager)PaymentCentreActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PaymentCentreActivity.this);
                alertDialogBuilder.setTitle("Enable Data");
                alertDialogBuilder.setMessage("Enable Data & Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }
        @Override
        protected void onPostExecute(String str) {
            Log.d("DemoApp", " str   " + str);
            progressDialog.dismiss();

            paymentCentreDataAdapter.notifyDataSetChanged();

            //fetChImage(empId);
        }

    }
    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
    private void initAdapter() {
        recycler_payment.setLayoutManager(new LinearLayoutManager(mContext));

        paymentCentreDataAdapter = new PaymentCentreDataAdapter(mContext, paymentCenterModals);
        recycler_payment.setAdapter(paymentCentreDataAdapter);
        //paymentCentreDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.cesu.itcc05.consumeportal.adpater.DocumentListDataAdapter;
import com.cesu.itcc05.consumeportal.adpater.PaymentCentreDataAdapter;
import com.cesu.itcc05.consumeportal.modal.DocumentModal;
import com.cesu.itcc05.consumeportal.modal.PaymentCenterModal;

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

public class DocumentActivity extends AppCompatActivity {
    private RecyclerView recycler_document;
    private Context mContext;
    private List<DocumentModal>documentModals=new ArrayList<>();
    private DocumentListDataAdapter documentListDataAdapter;
    private ProgressDialog progressDialog;
    private ImageView iv_backs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        recycler_document=findViewById(R.id.recycler_document);
        iv_backs=findViewById(R.id.iv_backs);
        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mContext=this;

        initAdapter();

        if (CommonMethods.isNetworkAvailable(mContext)){
            String url="http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Get_Consumer_Related_Document?checksum=01091981";
            new fetchBannerData().execute(url);
        }
        else {
            Toast.makeText(mContext,"No internet connection, please connect to internet",Toast.LENGTH_SHORT).show();
        }


    }

    private void initAdapter() {
        recycler_document.setLayoutManager(new LinearLayoutManager(mContext));

        documentListDataAdapter = new DocumentListDataAdapter(mContext, documentModals);
        recycler_document.setAdapter(documentListDataAdapter);
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


                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                Element element=doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("Table");


                for (int temp = 0; temp < nList.getLength(); temp++)
                {
                    Node node = nList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        DocumentModal documentModal=new DocumentModal();

                        documentModal.setID(getValue("ID", element2));
                        documentModal.setDOC_NAME(getValue("DOC_NAME", element2));
                        documentModal.setDOC_DESCR(getValue("DOC_DESCR", element2));
                        documentModal.setDOC_PATH(getValue("DOC_PATH", element2));
                        documentModals.add(documentModal);
                    }
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

            return bodycontent;
        }
        @Override

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(DocumentActivity.this, "Fetching Data", "Please Wait:: connecting to server");

            ConnectivityManager cm = (ConnectivityManager)DocumentActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DocumentActivity.this);
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

            documentListDataAdapter.notifyDataSetChanged();

            //fetChImage(empId);
        }

    }
    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

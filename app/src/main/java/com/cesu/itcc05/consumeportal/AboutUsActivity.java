package com.cesu.itcc05.consumeportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class AboutUsActivity extends AppCompatActivity {

    private Context mContext;
    private TextView tv_about_us;
    String text;
    private ProgressDialog progressDialog;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mContext=this;
        tv_about_us=findViewById(R.id.tv_about_us);
        iv_back=findViewById(R.id.iv_backs);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

            // CALL API

            if (CommonMethods.isNetworkAvailable(mContext)){
                String url="http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/TPCODL_About_Us?checksum=01091981";
                new fetchAboutUs().execute(url);
            }
            else {
                Toast.makeText(mContext,"No internet connection, please connect to internet",Toast.LENGTH_SHORT).show();
            }



    }

    private  class fetchAboutUs extends AsyncTask<String, Integer, String> {


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
              /*  URLConnection uc = url.openConnection();
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
*/
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

                        text= getValue("DESCR", element2);
                        System.out.println("asdfgvh=="+text);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });

                    }
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

            return bodycontent;
        }
        @Override

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AboutUsActivity.this, "Fetching Data", "Please Wait:: connecting to server");

            ConnectivityManager cm = (ConnectivityManager)AboutUsActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AboutUsActivity.this);
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

            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv_about_us.setText(HtmlCompat.fromHtml(text, 0));

                    /*tv_about_us.setMovementMethod(BetterLinkMovementMethod.newInstance().setOnLinkClickListener((textView, url) -> {

                        if (Patterns.WEB_URL.matcher(url).matches()) {
                            //An web url is detected
                            tv_about_us.setMovementMethod(LinkMovementMethod.getInstance());

                            return true;
                        }
                        else if(Patterns.PHONE.matcher(url).matches()){
                            //A phone number is detected
                            System.out.println("sdfg=="+url);
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+url));
                            startActivity(callIntent);

                         //   tv_about_us.setMovementMethod(LinkMovementMethod.getInstance());

                            return true;
                        }
                        else if(Patterns.EMAIL_ADDRESS.matcher(url).matches()){
                            //An email address is detected
                            return true;
                        }


                        return false;
                    }));*/


                } else {
                    tv_about_us.setText(HtmlCompat.fromHtml(text, 0));
                    /*tv_about_us.setMovementMethod(BetterLinkMovementMethod.newInstance().setOnLinkClickListener((textView, url) -> {

                        if (Patterns.WEB_URL.matcher(url).matches()) {
                            //An web url is detected
                            tv_about_us.setMovementMethod(LinkMovementMethod.getInstance());

                            return true;
                        }
                        else if(Patterns.PHONE.matcher(url).matches()){
                            //A phone number is detected

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+url));
                            startActivity(callIntent);

                            return true;
                        }
                        else if(Patterns.EMAIL_ADDRESS.matcher(url).matches()){
                            //An email address is detected
                            return true;
                        }


                        return false;
                    }));*/
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

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
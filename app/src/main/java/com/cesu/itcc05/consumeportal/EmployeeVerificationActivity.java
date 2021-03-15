package com.cesu.itcc05.consumeportal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class EmployeeVerificationActivity extends AppCompatActivity {
    private EditText et_emp_id;
    private Context mContext;
    private Button btn_verify;
    private TextView tv_emp_id;
    private TextView tv_emp_name;
    private TextView tv_agency;
    private String empId="";
    private String empName="";
    private String empAgency="";
    private ImageView iv_emp_image;
    private LinearLayout ll_details;
    private ImageView iv_verify;
    private ProgressDialog progressDialog;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_verification);
        mContext=this;

        et_emp_id=findViewById(R.id.et_emp_id);
        btn_verify=findViewById(R.id.btn_verify);
        tv_emp_id=findViewById(R.id.tv_emp_id);
        tv_emp_name=findViewById(R.id.tv_emp_name);
        tv_agency=findViewById(R.id.tv_agency);
        iv_emp_image=findViewById(R.id.iv_emp_image);
        ll_details=findViewById(R.id.ll_details);
        iv_verify=findViewById(R.id.iv_verify);
        iv_back=findViewById(R.id.iv_backs);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonMethods.hideKeyboard(EmployeeVerificationActivity.this);

                if (!(et_emp_id.getText().toString().trim().length()==0)){
                    String url="http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Employee_Varification?EmployeeID="+et_emp_id.getText().toString()+"&MobileNo=";
                    new fetchEmployeeData().execute(url);
                }
                else {
                    Toast.makeText(mContext,"Please enter Employee ID",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private  class fetchEmployeeData extends AsyncTask<String, Integer, String> {


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
                doc.getDocumentElement().normalize();

                String[] res = doc.getFirstChild().getTextContent().split("[#]", 0);

                empId=res[0];
                empName=res[1];
                empAgency=res[2];

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if((empName.equalsIgnoreCase("Employee Not Found"))){
                            ll_details.setVisibility(View.GONE);
                            iv_verify.setVisibility(View.VISIBLE);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                            alertDialogBuilder.setTitle("Alert");
                            alertDialogBuilder.setMessage("Invalid Employee ID")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // show it
                            alertDialog.show();


                        }
                        else if (empName.equalsIgnoreCase("Exception occured")){
                            ll_details.setVisibility(View.GONE);
                            iv_verify.setVisibility(View.VISIBLE);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                            alertDialogBuilder.setTitle("Alert");
                            alertDialogBuilder.setMessage("Server is busy, please try again")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // show it
                            alertDialog.show();

                        }
                        else {
                            ll_details.setVisibility(View.VISIBLE);
                            iv_verify.setVisibility(View.GONE);
                            tv_emp_id.setText(empId);
                            tv_emp_name.setText(empName);
                            tv_agency.setText(empAgency);
                        }
                    }
                });



            } catch (Exception e) {
                e.printStackTrace();
            }

            return bodycontent;
        }
        @Override

        protected void onPreExecute() {


            ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                progressDialog = ProgressDialog.show(EmployeeVerificationActivity.this, "Fetching Data", "Please Wait:: connecting to server");

            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle("Enable Data");
                alertDialogBuilder.setMessage("Enable Data & Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
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

            fetChImage(empId);
        }

    }

    private void fetChImage(String empid) {
        String url="http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Employee_Image?EmployeeID="+empid;
        new fetchEmpImage().execute(url);
    }

    private  class fetchEmpImage extends AsyncTask<String, Integer, String> {


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
                doc.getDocumentElement().normalize();

                String[] res = doc.getFirstChild().getTextContent().split("[#]", 0);

                byte[] decodedString = Base64.decode(res[0], Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                if (res[0].toString().length()==0){
                    iv_emp_image.setImageResource(R.drawable.no_image_user);

                }
                else {
                    iv_emp_image.setImageBitmap(decodedByte);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return bodycontent;
        }
        @Override

        protected void onPreExecute() {
            ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
               // progressDialog = ProgressDialog.show(EmployeeVerificationActivity.this, "Fetching Data", "Please Wait:: connecting to server");
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle("Enable Data");
                alertDialogBuilder.setMessage("Enable Data & Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
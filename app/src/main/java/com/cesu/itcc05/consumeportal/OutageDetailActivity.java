package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cesu.itcc05.consumeportal.adpater.OutageDeatilsAdapter;
import com.cesu.itcc05.consumeportal.adpater.PaymentCentreDataAdapter;
import com.cesu.itcc05.consumeportal.modal.OutageModal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static java.lang.System.exit;

public class OutageDetailActivity extends AppCompatActivity {
    private static Button strhint;
    private static TextView strconid,strconname,stroutageinfo;
    private ArrayList<OutageModal> outageModals=new ArrayList<>();
    private  String Typeinfo="";
    private String consIDval="";
    private String conname="";
    private String emailval="";
    private String mobval="";
    private String strAddrval="";
    private ImageView iv_backs;
    private RecyclerView rv_outage;
    OutageDeatilsAdapter outageDeatilsAdapter;
    private String catDat="";
    private String StrUrl1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_outage_detail);
        strconid = (TextView) findViewById(R.id.conid);
        strconname = (TextView) findViewById(R.id.conname);
        stroutageinfo = (TextView) findViewById(R.id.outageinfo);
        Button btncomreg = (Button) findViewById(R.id.comreg);
        rv_outage=findViewById(R.id.rv_outage);
        iv_backs=findViewById(R.id.iv_backs);
        StrUrl1="http://portal.tpcentralodisha.com:8070"+"/ePortalAPP/ePortal_App.jsp?";

        getCategory();
        initAdapter();

        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle parmtrDet = getIntent().getExtras();
       // pipeDelBillInfo =getIntent().getParcelableExtra("pipeDelBillInfo");
        ArrayList<OutageModal> myList = getIntent().getParcelableExtra("pipeDelBillInfo");

         Typeinfo= parmtrDet.getString("Typeinfo");
         consIDval= parmtrDet.getString("consIDval");
         conname= parmtrDet.getString("conname");
         emailval= parmtrDet.getString("emailval");
         mobval= parmtrDet.getString("mobval");
         strAddrval= parmtrDet.getString("strAddrval");


        outageDeatilsAdapter.notifyDataSetChanged();


       /* String[] OutageInfo = pipeDelBillInfo.split("[;]");
        String outMsg="";
        for(int i=0;i<OutageInfo.length;i++) {
            try {
                outMsg=outMsg+OutageInfo[i]+"\n"+"*******************"+"\n";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        //pipeDelBillInfo = "1|1|102shhdhdh|santiranjan|line from 1.30 to 5.50 PM";
//        Log.d("DemoApp", " BillInfo[2]   " + OutageInfo[2]);//authoriztion check
        Log.d("DemoApp", " Typeinfo   " + Typeinfo);//authoriztion check
        strconid.setText(conname+"\n"+"("+consIDval+")");
        strconname.setText("");
      //  stroutageinfo.setText(outMsg);

        btncomreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commint = new Intent(getApplicationContext(), ComplainRegisterActivity.class);
                Bundle commdtls = new Bundle();
                commdtls.putString("consIDval", consIDval);
                commdtls.putString("emailval", emailval);
                commdtls.putString("mobval", mobval);
                commdtls.putString("regtype", Typeinfo);
                commdtls.putString("strAddrval", strAddrval);
                commdtls.putString("conname", conname);
                commdtls.putString("comsubcat",catDat);

                commint.putExtras(commdtls);
                startActivity(commint);
                finish();

                Log.d("DemoApp", " consIDval   " + consIDval);//authoriztion check
                Log.d("DemoApp", " emailval   " + emailval);//authoriztion check
                Log.d("DemoApp", " mobval   " + mobval);//authoriztion check
                Log.d("DemoApp", " Typeinfo   " + Typeinfo);//authoriztion check
                Log.d("DemoApp", " strAddrval   " + strAddrval);//authoriztion check
                Log.d("DemoApp", " conname   " + conname);//authoriztion check


            }
        });
    }
    private void initAdapter() {
        if (GlobalVariable.outageModals.size() > 0) {
            rv_outage.setLayoutManager(new LinearLayoutManager(OutageDetailActivity.this));
            outageDeatilsAdapter = new OutageDeatilsAdapter(OutageDetailActivity.this, GlobalVariable.outageModals);
            rv_outage.setAdapter(outageDeatilsAdapter);
        }
        //paymentCentreDataAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tolbarlogout, menu);
        return true;
    }
    // Activity's overrided method used to perform click events on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
// Display menu item's title by using a Toast.
        if (id == R.id.action_settings1) { //logout
            Toast.makeText(getApplicationContext(), "User Menu1", Toast.LENGTH_SHORT).show();
            finish();
            exit(0);
            return true;
        } else if (id == R.id.action_user) { //back
            if(Typeinfo.equals("qlinks")) {

                finish();
            }else{
                Intent i = new Intent(this,OutageInfoActivity.class);
                this.startActivity(i);
               // finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCategory() {

        if (CommonMethods.isNetworkAvailable(OutageDetailActivity.this)){
            String AuthURL1 = StrUrl1 + "CompanyID=" + 10 + "&RequestType=8";
            new callCategory().execute(AuthURL1);
        }
        else {
            Toast.makeText(OutageDetailActivity.this,"No internet connection,please connect to internet",Toast.LENGTH_SHORT).show();
        }

    }

    private  class callCategory extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL = params[0];

            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent = null;
            Log.d("DemoApp", " strURL   " + strURL);
            try {

                URL url = new URL(strURL);
                URLConnection uc = url.openConnection();
                uc.setDoInput(true);
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String inputLine;
                StringBuilder a = new StringBuilder();
                while ((inputLine = in.readLine()) != null)
                    a.append(inputLine);
                in.close();
                Log.d("DemoApp", " fullString   " + a.toString());
                String html = a.toString();
                int start = html.indexOf("<body>") + "<body>".length();
                int end = html.indexOf("</body>", start);
                bodycontent = html.substring(start, end);
                Log.d("DemoApp", " start   " + start);
                Log.d("DemoApp", " end   " + end);
                Log.d("DemoApp", " body   " + bodycontent);
            } catch (Exception e) {
                e.printStackTrace();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OutageDetailActivity.this);
                alertDialogBuilder.setTitle("Not Connectd to Server");
                alertDialogBuilder.setMessage("Please Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                OutageDetailActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }

            return bodycontent;
        }

        @Override

        protected void onPreExecute() {
            //  btnsubmit.setEnabled(false);
            // btnsubmit.setClickable(false);
            ConnectivityManager cm = (ConnectivityManager) OutageDetailActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                // progressDialog = ProgressDialog.show(ComplaintLoginActivity.this, "  ", " ");

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OutageDetailActivity.this);
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
                                OutageDetailActivity.this.finish();
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
            // progressDialog.dismiss();

            String pipeDelBillInfo = str;
            String[] BillInfo = pipeDelBillInfo.split("[|]");

            Log.d("DemoApp", " BillInfo[0]   " + pipeDelBillInfo);//authoriztion check
            Log.d("DemoApp", " BillInfo[1]  " + BillInfo[1]);//authoriztion check

            catDat=BillInfo[1];

            /*Intent compintent = new Intent(getApplicationContext(), ComplaintLoginActivity.class);
            Bundle compType = new Bundle();
            compType.putString("compinfo", "reg");
            compType.putString("comsubcat",BillInfo[1]);
            compintent.putExtras(compType);
            startActivity(compintent);
            finish();*/


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

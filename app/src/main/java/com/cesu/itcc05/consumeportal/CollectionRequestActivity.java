package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class CollectionRequestActivity extends AppCompatActivity {
    private   DatabaseAccess databaseAccess=null;
    final Context context = this;
    private static EditText strconsIDval;
    private int chkresponse=1;
    private String AuthURL=null;
    private String consIDval="";
    private static TextView strcoltext;
    RelativeLayout linearLayout1;
    Button showPopupBtn, payBtn,backBtn,btnnext;
    PopupWindow popupWindow;
    private String pipieinfo="x";
    private static TextView strName;
    private static EditText strPayAmt;
    private static TextView strPayamount;
    private static TextView strcbill;
    private static TextView strdudt;
    private static TextView strpmt,strarrear,strbdate;
    private static TextView strpmtdt,strconid,strConName,strminpay;
    private static   RadioGroup ll;
    private static RadioButton rdbtn;
    private static String selconid="";
    private String versionID="";
    private String CompanyID="";
    private String StrUrl="";
    private String mobval="";
    private String ConsID="";
    private String imeinum="";
    private String Typeinfo="";
    private Spinner spinner2;
    private String conname="";
    private static TextView strbtot;
    private static TextView strrbt;
    private int minpay=0;
    private String ColTyp="";
    private String consIDvalB="";
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_request);
        androidx.appcompat.widget.Toolbar toolbarback = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pay Now");//collection request
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_BillInfo.jsp?";

        //  strcoltext=(TextView)findViewById(R.id.coltext);
        strConName=(TextView)findViewById(R.id.conNm);

        showPopupBtn=(Button)findViewById(R.id.next);
        //  backBtn=(Button)findViewById(R.id.back);
        linearLayout1 = (RelativeLayout) findViewById(R.id.layout);//for pop up
        Bundle paytypDet = getIntent().getExtras();
        Typeinfo= paytypDet.getString("Typeinfo");
        ColTyp= paytypDet.getString("ColTyp");
        if(ColTyp.equals("1")) {
            consIDvalB = paytypDet.getString("consIDvalB");
        }
        addItemsOnSpinner2();

        //////////////////////

        /////////////////////
        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selconid=  String.valueOf(spinner2.getSelectedItem()).trim();
                //  rdbtn.setButtonDrawable(android.R.color.transparent);
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    changeTextStatus(true);
                } else {
                    changeTextStatus(false);
                }
                Log.d("DemoApp", " pipieinfo   " + pipieinfo);

            }
        });


    }

    public void changeTextStatus(boolean isConnected) {

        // Change status according to boolean value
        if (isConnected) {
            funcUrlCheck(chkresponse);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("You are not connected to internet");
            alertDialogBuilder.setMessage("Enable data and Click Retry for Login !!")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CollectionRequestActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            // internetStatus.setText("Internet Disconnected.");
            //  internetStatus.setTextColor(Color.parseColor("#ff0000"));
        }
    }
    @Override
    protected void onPause() {

        super.onPause();
        ChkConnectivity.activityPaused();// On Pause notify the Application
    }

    @Override
    protected void onResume() {

        super.onResume();
        ChkConnectivity.activityResumed();// On Resume notify the Application
    }
    private String funcUrlCheck(int resCode){
        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
        //  ConsID =sessiondata.getString("ConsID", null); // getting String
        mobval =sessiondata.getString("mobval", null); // getting String
        imeinum =sessiondata.getString("imeinum", null); // getting String
        CompanyID =sessiondata.getString("CompanyID", null); // getting String

        if(resCode==1){
            AuthURL = StrUrl+"un=TEST&pw=TEST&CompanyID="+CompanyID+"&ConsumerID="+selconid+"&imei="+imeinum+"&mosarkar=0&mobile_no="+mobval;
            new  UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            AuthURL = StrUrl+"un=TEST&pw=TEST&CompanyID="+CompanyID+"&ConsumerID="+selconid+"&imei="+imeinum+"&mosarkar=0&mobile_no="+mobval;
            new UserAuthOnline().execute(AuthURL);
        }else{
            AuthURL="ServerOut";
            //  int code=AlertErrorCall.ErrorMsgType(6);
            //   if(code==1){MainActivity.this.finish();}
        }
        Log.d("DemoApp", "in Loop AuthURL" + AuthURL);


        return AuthURL;
    }
    private class UserAuthOnline extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL=params[0];

            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent=null;
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
                int start = html.indexOf("<body>")+"<body>".length();
                int end = html.indexOf("</body>", start);
                bodycontent = html.substring(start, end);
                Log.d("DemoApp", " start   " + start);
                Log.d("DemoApp", " end   " + end);
                Log.d("DemoApp", " body   " + bodycontent);
            } catch (Exception e) {
                e.printStackTrace();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Not Connectd to Server");
                alertDialogBuilder.setMessage("Please Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CollectionRequestActivity.this.finish();
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
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                //  progressDialog = ProgressDialog.show(CollectionRequestActivity.this, " ", " ");
                spinner.setVisibility(View.VISIBLE);
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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
                                CollectionRequestActivity.this.finish();
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
            spinner.setVisibility(View.GONE);
            String pipeDelBillInfo =str;
            //  pipeDelBillInfo="1|10|santirnja|102S34343|1000|12-09-2019|580|03-08-2019|2555"; //curbill

            String[] BillInfo = pipeDelBillInfo.split("[|]");
            Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check
            Log.d("DemoApp", " BillInfo[2]   " + pipeDelBillInfo);//authoriztion check
            if(BillInfo[0].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("User Not Found");
                alertDialogBuilder.setMessage("User Not Found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CollectionRequestActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }else if(BillInfo[1].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("IP Address Not Found");
                alertDialogBuilder.setMessage("IP Address Not Found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CollectionRequestActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }else if(BillInfo[2].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Consumer Not Found");
                alertDialogBuilder.setMessage("Consumer Not Found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CollectionRequestActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }/*else if(BillInfo[3].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Current Bill Not Found");
                alertDialogBuilder.setMessage("Current Bill Not Found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CollectionRequestActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }*/else{
                LayoutInflater layoutInflater = (LayoutInflater) CollectionRequestActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup_collection_fetch,null);
                payBtn = (Button) customView.findViewById(R.id.pay);
                backBtn= (Button) customView.findViewById(R.id.back);
                // pipeDelBillInfo="1|10|santirnja|102S34343|1000|12-09-2019|580|03-08-2019"; //curbill
                Log.d("DemoApp", " BillInfo[2]   " + BillInfo[2]);//authoriztion check
                strName=(TextView)customView.findViewById(R.id.Name);
                strminpay=(TextView)customView.findViewById(R.id.minpay);
                strPayamount=(TextView)customView.findViewById(R.id.Payamount);//
                strPayAmt=(EditText)customView.findViewById(R.id.PayAmt);//pay due
                strcbill=(TextView)customView.findViewById(R.id.cbill);
                strarrear=(TextView)customView.findViewById(R.id.arrear);//
                strbdate=(TextView)customView.findViewById(R.id.bdate);//
                strdudt=(TextView)customView.findViewById(R.id.dudt);
                strrbt=(TextView)customView.findViewById(R.id.rbt1);
                strpmt=(TextView)customView.findViewById(R.id.pmt);
                strpmtdt=(TextView)customView.findViewById(R.id.pmtdt);



                // strcbill=(TextView)customView.findViewById(R.id.cbill);
                //    strdudt=(TextView)customView.findViewById(R.id.dudt);


                String stroffcode="";
                stroffcode=BillInfo[21];
                Log.d("DemoApp", "[4]   ") ;
                if(BillInfo[22].equals("1")){
                    stroffcode=stroffcode+"S";
                }else{
                    stroffcode=stroffcode+"I";
                }
                Log.d("DemoApp", "BillInfo[22]  " + BillInfo[22]) ;
                strName.setText(BillInfo[8] + " (" + stroffcode + BillInfo[7] + ")");
                //strconid.setText(BillInfo[7]);

                int bltotal=0;
                int payamt=0;
                int arrear=0;
                bltotal=(int)(Double.parseDouble(BillInfo[25]));
                payamt=(int)(Double.parseDouble(BillInfo[12]));
                arrear=(int)(Double.parseDouble(BillInfo[28]));
                bltotal=Math.round(bltotal);
                payamt=Math.round(payamt);
                arrear=Math.round(arrear);
                Log.d("DemoApp", "bltotal " + bltotal) ;
                Log.d("DemoApp", "payamt  " + payamt) ;
                Log.d("DemoApp", "arrear " + arrear) ;
                if(payamt>=bltotal){
                    if(arrear<=0){
                        minpay=payamt;
                    }else{
                        // minpay=payamt-Math.round((arrear/1)); change by santi 17/04/20
                        minpay=payamt;
                    }
                }else{
                    minpay=payamt;
                }
                minpay=payamt;
                strminpay.setText(Integer.toString(minpay));
                strPayamount.setText(BillInfo[12]);//
                strPayAmt.setText(BillInfo[12]);

                //Modify on 040520 by Santi
                // strPayAmt.setTextIsSelectable(true); //disable on 040520 by Santi
                // strPayAmt.setFocusable(true);//disable on 040520 by Santi
                // strPayAmt.setFocusableInTouchMode(true);//disable on 040520 by Santi
                // strPayAmt.setClickable(true);//disable on 040520 by Santi
                // strPayAmt.setLongClickable(true);//disable on 040520 by Santi
                //End////


                strcbill.setText(BillInfo[27]);//current bill
                strarrear.setText(BillInfo[28]);
                strbdate.setText(BillInfo[16]);
                strdudt.setText(BillInfo[17]);
                strrbt.setText(BillInfo[26]);
                //strpmt.setText(BillInfo[23]);
                strpmt.setText(BillInfo[29]);//Added on 040520 by santi
                try {
                    if (BillInfo[24] == null || BillInfo[24].equals("") || BillInfo[24].equals(" ") || BillInfo[24].equals("-")) {
                        strpmtdt.setText("-");
                    } else {
                        strpmtdt.setText(BillInfo[24].substring(0, 11));//.substring(0,10)
                    }
                }catch (Exception e){strpmtdt.setText("-");}
                //instantiate popup window
                popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                strPayAmt.setCursorVisible(false); //added on 040520 by santi
                //added on 040520 by santi
                if(strPayAmt.getText().toString().equals("0"))
                {
                    payBtn.setEnabled(false);
                    payBtn.setClickable(false);
                }else{
                    payBtn.setEnabled(true);
                    payBtn.setClickable(true);
                }
                //end//
                //    popupWindow.update();//disable on 040520 by Santi
                // strPayAmt.setTextIsSelectable(true);//disable on 040520 by Santi
                //  strPayAmt.setFocusable(true);//disable on 040520 by Santi
                //  strPayAmt.setFocusableInTouchMode(true);//disable on 040520 by Santi
                //  strPayAmt.setClickable(true);//disable on 040520 by Santi
                // strPayAmt.setLongClickable(true);//disable on 040520 by Santi
                strPayAmt.setLongClickable(false);//disable on 040520 by Santi

               /* //////////Added on 22.12.2019
                strPayAmt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(final View v, final boolean hasFocus) {
                        if (hasFocus && strPayAmt.isEnabled() && strPayAmt.isFocusable()) {
                            strPayAmt.post(new Runnable() {
                                @Override
                                public void run() {
                                    final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(strPayAmt, InputMethodManager.SHOW_IMPLICIT);
                                }
                            });
                        }
                    }
                });
                strPayAmt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(final View v, final boolean hasFocus) {
                        if (hasFocus && strPayAmt.isEnabled() && strPayAmt.isFocusable()) {
                            strPayAmt.post(new Runnable() {
                                @Override
                                public void run() {
                                    final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(strPayAmt, InputMethodManager.SHOW_IMPLICIT);
                                }
                            });
                        }
                    }
                });
                strPayAmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        strPayAmt.post(new Runnable() {
                            @Override
                            public void run() {
                                final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(strPayAmt, InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                    }
                });
                */
                ///////////////
                //close the popup window on button click
                payBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String chkpay = strPayAmt.getText().toString();
                        Log.d("DemoApp", "chkpay  " + Integer.valueOf(chkpay)) ;
                        Log.d("DemoApp", "minpay  " + Integer.valueOf(minpay)) ;
                        int xx=Integer.valueOf(chkpay)-Integer.valueOf(minpay);
                        Log.d("DemoApp", "minpay  " + xx) ;
                        if(xx>=0) {
                                /*payBtn.setEnabled(false);
                                payBtn.setClickable(false);
                                Intent PayDashboardint = new Intent(getApplicationContext(), PaymentDashboardActivity.class);
                                Bundle paydtls = new Bundle();
                                paydtls.putString("consIDval", selconid);
                                paydtls.putString("Typeinfo", Typeinfo);
                                PayDashboardint.putExtras(paydtls);
                                startActivity(PayDashboardint);
                                finish();*/
                            //By Narendra: Commented above code and adding below to open payment screen
                            CommonMethods.loadPaymentPage(CollectionRequestActivity.this,
                                    selconid,
                                    mobval);

                        }else{
                            strPayAmt.setError("Min amount and Enter value mismatch");
                        }
                    }
                });
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //from bill
                        Log.d("DemoApp", "@@@@@@@@@@@  " ) ;
                        ColTyp="0";
                        ////
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.dismiss();
                        return;
                    }
                });
            }
        }
    }
    public void  addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD "+
                "FROM CONSUMERDTLS WHERE (VALIDATE_FLG=1 OR VALIDATE_FLG=9)  ";
        Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_02);
        Cursor rs1 = DatabaseAccess.database.rawQuery(strSelectSQL_02, null);
        while (rs1.moveToNext()) {
            list.add(rs1.getString(0));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        /////////////
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                // String value = String.valueOf(item.toString());
                String value = String.valueOf(position);
                // consIDval=String.valueOf(position);
                //////////////Added from Current bill

                ////////////////
                if(ColTyp.equals("1")){
                    consIDval=consIDvalB;
                }else {
                    consIDval = String.valueOf(spinner2.getSelectedItem());
                }
                databaseAccess = DatabaseAccess.getInstance(context);
                databaseAccess.open();
                String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD "+
                        "FROM CONSUMERDTLS WHERE CONSUMER_ID='" + consIDval + "'  ";
                Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_02);
                Cursor rs1 = DatabaseAccess.database.rawQuery(strSelectSQL_02, null);
                while (rs1.moveToNext()) {
                    //   spinpos1=rs1.getString(2);
                    conname=rs1.getString(1);
                }
                strConName.setText(conname);
                Log.d("DemoApp", "consIDval" + consIDval);


                int index = 0;
                for (int i = 0; i < spinner2.getCount(); i++) {
                    if (spinner2.getItemAtPosition(i).equals(consIDval)) {
                        index = i;
                    }
                    Log.d("DemoApp", "spinner2.getItemAtPosition(i)" + spinner2.getItemAtPosition(i));
                }
                Log.d("DemoApp", "index" + index);
                Log.d("DemoApp", "spinner2.getCount()" + spinner2.getCount());
                Log.d("DemoApp", "index" + index);
                spinner2.setSelection(index);




                if(ColTyp.equals("1")){
                    selconid=  String.valueOf(spinner2.getSelectedItem()).trim();

                    //  rdbtn.setButtonDrawable(android.R.color.transparent);
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        changeTextStatus(true);
                    } else {
                        changeTextStatus(false);
                    }
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //////////////
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
            if(Typeinfo.equals("reg")) {
                Intent i = new Intent(this,MainActivity.class);
                this.startActivity(i);
                finish();
            }else{
                Intent i = new Intent(this,MainActivity.class);
                this.startActivity(i);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

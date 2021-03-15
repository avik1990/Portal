package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.System.exit;

public class InstantPaymentActivity extends AppCompatActivity {

    private static ImageView strconsID;
    private static ImageView strhint;
    private static ImageView stremail;
    private static ImageView strMobno;
    private static ImageView strstar1;
    private static TextView strmobmsg,strminpay,strarrear,strbdate;
    private static EditText strconsIDval;
    private static EditText stremailval;
    private static EditText strmobval;
    private static TextView strPayamount;
    private String consIDval="";
    private String emailval="";
    private String mobval="";
    private String imeinum = "";
    public  static final int RequestPermissionCode  = 1 ;
    final Context context = this;
    private int chkresponse=1;
    private String AuthURL=null;
    Button  UpdateSwBtn;
    Button closePopupBtn;
    private ImageView closePopupBtns;
    PopupWindow popupWindow;
    ConstraintLayout linearLayout1;
    private   DatabaseAccess databaseAccess=null;
    private CheckBox TermCond;
    Button showPopupBtn, payBtn,backBtn,btnnext;
    private String pipieinfo="";
    private static TextView strName;
    private static TextView strcbill;
    private static TextView strdudt;
    private static TextView strpmt;
    private static TextView strpmtdt,strconid;
    private String StrUrl="";
    private static TableRow strtblrw;
    private int minpay=0;
    private String ColTyp="0";
    private static TextView strrbt;
    private static EditText strPayAmt;
    private static String selconid="";
    private String Typeinfo="";
    private ProgressBar spinner;
    private Button btnsubmit;
    private TextView tncLabel;
    private ImageView iv_back;
    private String caNumber="";
    private String mobileNo="";
    private String emailId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_payment);
        // setSupportActionBar(toolbar);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        tncLabel = (TextView) findViewById(R.id.tncLabel);
        iv_back=findViewById(R.id.iv_backs);

        Intent inten=getIntent();
        caNumber=inten.getStringExtra("ca");
        mobileNo=inten.getStringExtra("mob");


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       // tncLabel.setText(Html.fromHtml(String.format(getString(R.string.tnc_label))));
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_BillInfo.jsp?";

        strconsID=(ImageView)findViewById(R.id.consID);
        strhint=(ImageView)findViewById(R.id.hint);
        stremail=(ImageView)findViewById(R.id.email);
        strMobno=(ImageView)findViewById(R.id.Mobno);
        strstar1=(ImageView)findViewById(R.id.star1);
        strmobmsg=(TextView)findViewById(R.id.mobmsg);
        strconsIDval=(EditText) findViewById(R.id.consIDval);
        stremailval=(EditText) findViewById(R.id.emailval);
        strmobval=(EditText) findViewById(R.id.mobval);
        ColTyp="0";
        //strPayamount=(EditText) findViewById(R.id.Payamount);
        // Button btnexit = (Button) findViewById(R.id.exit);
        btnsubmit = (Button) findViewById(R.id.submit);
        Button btntrmscnd = (Button) findViewById(R.id.bttrmscnd);
        ImageView btnhint = (ImageView) findViewById(R.id.hint);
        strtblrw= (TableRow) findViewById(R.id.tblrw);
        ///////checking user registration...////////////////
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO,EMAIL "+
                "FROM CONSUMERDTLS WHERE VALIDATE_FLG=1";
        Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
        String conid = "";
        String mobno="";
        String email="";
        while (cursor.moveToNext()) {
            conid=cursor.getString(0);
            mobno=cursor.getString(1);
            email=cursor.getString(2);
        }
        if(email.equals("0")){
            email="";
        }
        cursor.close();
        databaseAccess.close();




        strconsIDval.setText(caNumber);
        stremailval.setText(emailId);
        strmobval.setText(mobileNo);
        if(conid.length()>5){
            strtblrw.setVisibility(View.INVISIBLE);
            btnhint.setVisibility(View.INVISIBLE);
        }else{
            strtblrw.setVisibility(View.VISIBLE);
            btnhint.setVisibility(View.VISIBLE);
        }

        Log.d("DemoApp", "in Loop conid" + conid);
        //terms and condition agree
        TermCond = (CheckBox)findViewById(R.id.tmcnd);
        TermCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    // System.out.println("Checked");
                    Log.d("DemoApp", "chk b coxlick ");
                    enableDisableSubmitButton(true);
                } else {
                    enableDisableSubmitButton(false);
                    Log.d("DemoApp", "chk box unclick " );
                }
            }
        });
        /////////////////////////////////////

        tncLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) InstantPaymentActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup_terms_cond,null);
                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
                TextView tvTnC = (TextView) customView.findViewById(R.id.tvTnC);
                tvTnC.setMovementMethod(new ScrollingMovementMethod());
                //instantiate popup window
                popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                View container = (View) popupWindow.getContentView().getParent();
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
// add flag
                p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.5f;
                wm.updateViewLayout(container, p);
                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        linearLayout1 = (ConstraintLayout) findViewById(R.id.layout);//for pop up
        btntrmscnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) InstantPaymentActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup_terms_cond,null);
                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
                //instantiate popup window
                popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                View container = (View) popupWindow.getContentView().getParent();
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
// add flag
                p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.5f;
                wm.updateViewLayout(container, p);
                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        btnhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) InstantPaymentActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_conumer_id,null);
                closePopupBtns = (ImageView) customView.findViewById(R.id.closePopupBtn);
                //instantiate popup window
                popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                //close the popup window on button click
                closePopupBtns.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consIDval = strconsIDval.getText().toString();
                emailval = stremailval.getText().toString();
                mobval = strmobval.getText().toString();
                Log.d("DemoApp", "consIDval" + consIDval);
                if(TextUtils.isEmpty(consIDval) ) {
                    strconsIDval.setError("Please enter consumer number");
                    strconsIDval.requestFocus();
                    Log.d("DemoApp", "Query SQL " + consIDval);
                }else if(consIDval.length()>0 && consIDval.length()<12){
                    strconsIDval.setError("Please Enter Valid Consumer number of 12 character");
                    strconsIDval.requestFocus();
                }
                else if (mobval.length()==0){
                    strmobval.setError("Please Enter Mobile No");
                    strconsIDval.requestFocus();

                }

                else if(mobval.length()>=0 && mobval.length()<10) {
                    strmobval.setError("Please Enter Valid Mobile No");
                    strconsIDval.requestFocus();
                }
                else if(!TermCond.isChecked()){
                    // System.out.println("Checked");
                    Toast.makeText(InstantPaymentActivity.this,getResources().getString(R.string.accept_term_con),Toast.LENGTH_SHORT).show();
                }

                else{
                    //Added on 040520 by Santi
                    if (mobval.length() == 10) {
                        if (CommonMethods.isValidMobile(mobval)) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                changeTextStatus(true);
                            } else {
                                changeTextStatus(false);
                            }
                        } else {
                            strmobval.setError("Mobile digit start from 6/7/8/9");
                        }
                    }
                    ///end
                }
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
                            InstantPaymentActivity.this.finish();
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

        if(resCode==1){
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            //imeinum = tm.getDeviceId();
            imeinum = CommonMethods.getUniqueNumber(mobval);//Hardcoded by Narendra
            AuthURL = StrUrl+"un=TEST&pw=TEST&CompanyID=10&ConsumerID="+consIDval+"&imei="+imeinum+"&mosarkar=0&mobile_no="+mobval;
            new  UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            AuthURL = StrUrl+"un=TEST&pw=TEST&CompanyID=10&ConsumerID="+consIDval+"&imei="+imeinum+"&mosarkar=0&mobile_no="+mobval;
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
       // ProgressDialog progressDialog;
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
                                InstantPaymentActivity.this.finish();
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
            enableDisableSubmitButton(false);
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
           // progressDialog = ProgressDialog.show(InstantPaymentActivity.this, " ", " ");
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                //  progressDialog = ProgressDialog.show(InstantPaymentActivity.this, " ", " ");
                spinner.setVisibility(View.VISIBLE);
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Enable Data");
                        alertDialogBuilder.setMessage("Enable Data & Retry")
                                .setCancelable(false)
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        enableDisableSubmitButton(true);
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        InstantPaymentActivity.this.finish();
                                    }
                                });
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                    }
                });


            }
        }
        @Override
        protected void onPostExecute(String str) {
            Log.d("DemoApp", " str   " + str);
            //  progressDialog.dismiss();
            spinner.setVisibility(View.GONE);
            String pipeDelBillInfo =str;
            // pipeDelBillInfo="1|10|santirnja|102S34343|1000|12-09-2019|580|03-08-2019|2555"; //curbill


            String[] BillInfo = pipeDelBillInfo.split("[|]");
            // Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check
            // Log.d("DemoApp", " BillInfo[2]   " +pipeDelBillInfo);//authoriztion check
            if(BillInfo[0].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("User Not Found");
                alertDialogBuilder.setMessage("User Not Found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                enableDisableSubmitButton(true);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                InstantPaymentActivity.this.finish();
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
                                enableDisableSubmitButton(true);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                InstantPaymentActivity.this.finish();
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
                                enableDisableSubmitButton(true);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                InstantPaymentActivity.this.finish();
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
                                enableDisableSubmitButton(true);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                InstantPaymentActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }*/else{//curbill

                LayoutInflater layoutInflater = (LayoutInflater) InstantPaymentActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                if(BillInfo[24]==null || BillInfo[24].equals("") || BillInfo[24].equals(" ") || BillInfo[24].equals("-")){
                    strpmtdt.setText("-");
                }else{
                    if(!BillInfo[24].equalsIgnoreCase("null")) {
                        strpmtdt.setText(BillInfo[24].substring(0, 11));//.substring(0,10)
                    }else{
                        strpmtdt.setText("NA");
                    }
                }
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
                    payBtn.setBackgroundColor(Color.parseColor("#999999"));
                    payBtn.setClickable(false);
                }else{
                    payBtn.setEnabled(true);
                    payBtn.setClickable(true);
                }
                //end//
                //popupWindow.update();//disable on 040520 by Santi
                // strPayAmt.setTextIsSelectable(true);//disable on 040520 by Santi
                // strPayAmt.setFocusable(true);//disable on 040520 by Santi
                //  strPayAmt.setFocusableInTouchMode(true);//disable on 040520 by Santi
                // strPayAmt.setClickable(true);//disable on 040520 by Santi
                // strPayAmt.setLongClickable(true);//disable on 040520 by Santi
                // strPayAmt.setCursorVisible(false); //added on 040520 by santi
                //disable on 040520 by Santi
                strPayAmt.setLongClickable(false);//disable on 040520 by Santi
                /*//////////Added on 22.12.2019
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
                */
                //close the popup window on button click
                payBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String chkpay = strPayAmt.getText().toString();
                        Log.d("DemoApp", "chkpay  " + Integer.valueOf(chkpay)) ;
                        Log.d("DemoApp", "minpay  " + Integer.valueOf(minpay)) ;
                        int xx=Integer.valueOf(chkpay)-Integer.valueOf(minpay);
                        Log.d("DemoApp", "minpay  " + xx) ;
                        if(xx==0) {/*
                                payBtn.setClickable(false);
                                payBtn.setEnabled(false);
                                Intent PayDashboardint = new Intent(getApplicationContext(), PaymentDashboardActivity.class);
                                Bundle paydtls = new Bundle();
                                paydtls.putString("consIDval", selconid);
                                paydtls.putString("Typeinfo", Typeinfo);
                                PayDashboardint.putExtras(paydtls);
                                startActivity(PayDashboardint);
                                finish();*/
                                /*Intent intent = new Intent(getApplicationContext(), PaymentResponsiveActivity.class);
                                String testUrl = "http://portal1.tpcentralodisha.com:8080/ConsumerPortal/GetData?intOptionType=2&ConsumerID=103S03000170&strMobileNo=9438233014";
                                intent.putExtra("paymentUrl",testUrl);
                                startActivity(intent);*/
                            CommonMethods.loadPaymentPage(InstantPaymentActivity.this,
                                    strconsIDval.getText().toString().trim(),
                                    strmobval.getText().toString().trim());
                        }else{
                            strPayAmt.setError("Min amount and Enter value mismatch");
                        }
                    }
                });
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ColTyp="0";
                        popupWindow.dismiss();
                    }
                });
            }
        }
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
            Intent i = new Intent(this,MainActivity.class);
            this.startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void enableDisableSubmitButton(Boolean enable){
       // btnsubmit.setEnabled(enable);
       // btnsubmit.setClickable(enable);
    }
}

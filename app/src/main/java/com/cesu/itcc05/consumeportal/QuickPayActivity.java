package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.System.exit;

public class QuickPayActivity extends AppCompatActivity {
    private static ImageView strconsID;
    private static Button strhint;
    private static ImageView stremail;
    private static ImageView strMobno;
    private static TextView strmobmsg;
    private static EditText strconsIDval;
    private static EditText stremailval;
    private static EditText strmobval;
    private String consIDval="";
    private String emailval="";
    private String mobval="";
    private String imeinum = "";
    public  static final int RequestPermissionCode  = 1 ;
    final Context context = this;
    private int chkresponse=1;
    private String AuthURL=null;
    private   DatabaseAccess databaseAccess=null;
    private CheckBox TermCond;
    RelativeLayout linearLayout1;
    Button showPopupBtn, closePopupBtn,backBtn,btnnext;
    PopupWindow popupWindow;
    private static TextView strName;
    private static TextView strcbill;
    private static TextView strdudt;
    private static TextView strpmt;
    private static TextView strpmtdt,strconid;
    private ProgressBar spinner;
    private String StrUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_pay);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070";
        strconsID=(ImageView)findViewById(R.id.consID);
        strhint=(Button)findViewById(R.id.hint);
        strMobno=(ImageView)findViewById(R.id.Mobno);
        strmobmsg=(TextView)findViewById(R.id.mobmsg);
        strconsIDval=(EditText) findViewById(R.id.consIDval);
        strmobval=(EditText) findViewById(R.id.mobval);
        Button btnexit = (Button) findViewById(R.id.exit);
        final Button btnsubmit = (Button) findViewById(R.id.submit);
        Button btntrmscnd = (Button) findViewById(R.id.bttrmscnd);
        Button btnhint = (Button) findViewById(R.id.hint);
        //terms and condition agree
        TermCond = (CheckBox)findViewById(R.id.tmcnd);
        TermCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    // System.out.println("Checked");
                    btnsubmit.setEnabled(true);
                    Log.d("DemoApp", "chk box click ");
                    btnsubmit.setClickable(true);
                } else {
                    btnsubmit.setEnabled(false);
                    btnsubmit.setClickable(false);
                    Log.d("DemoApp", "chk box unclick " );
                }
            }
        });
        /////////////////////////////////////
        linearLayout1 = (RelativeLayout) findViewById(R.id.layout);//for pop up
        btntrmscnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) QuickPayActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup_terms_cond,null);
                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
                //instantiate popup window
                popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
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
                LayoutInflater layoutInflater = (LayoutInflater) QuickPayActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_conumer_id,null);
               ImageView closePopupBtnd = (ImageView) customView.findViewById(R.id.closePopupBtn);
                //instantiate popup window
                popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                //close the popup window on button click
                closePopupBtnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                exit(0);
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consIDval = strconsIDval.getText().toString();
                mobval = strmobval.getText().toString();
                Log.d("DemoApp", "consIDval" + consIDval);
                if(TextUtils.isEmpty(consIDval) ) {
                    strconsIDval.setError("Please enter consumer/CA number");
                    strconsIDval.requestFocus();
                    Log.d("DemoApp", "Query SQL " + consIDval);
                }else if(consIDval.length()>0 && consIDval.length()<12){
                    strconsIDval.setError("Enter Valid Consumer ID of 12 digit");
                    strconsIDval.requestFocus();
                }
                else if (mobval.length()==0){
                    strmobval.setError("Please enter mobile number");
                    strmobval.requestFocus();
                }
                else if(mobval.length()>=0 && mobval.length()<10) {
                    strmobval.setError("Enter Valid Mobile No");
                    strmobval.requestFocus();
                } else{
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
                            QuickPayActivity.this.finish();
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
            AuthURL=StrUrl+"/IncomingSMS/CESU_mCollection1.jsp?strCompanyID=8&un=3&pw=a&imei=358461097642898";
            new  UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            AuthURL=StrUrl+"/IncomingSMS/CESU_mCollection1.jsp?strCompanyID=8&un=3&pw=a&imei=358461097642898";
            new UserAuthOnline().execute(AuthURL);
        }else{
            AuthURL="ServerOut";
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
                                QuickPayActivity.this.finish();
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
              //  progressDialog = ProgressDialog.show(QuickPayActivity.this, " ", " ");
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
                                QuickPayActivity.this.finish();
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
            //progressDialog.dismiss();
            spinner.setVisibility(View.GONE);
            String pipeDelBillInfo =str;
            pipeDelBillInfo="1|1|102S34343|1000|12-09-2019|580|03-08-2019|Name"; //curbill

            String[] BillInfo = pipeDelBillInfo.split("[|]");
            Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check
            Log.d("DemoApp", " BillInfo[2]   " +pipeDelBillInfo);//authoriztion check
            if(BillInfo[0].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("No authorization!! Contact IT HQ");
                alertDialogBuilder.setMessage("Access Not given or withdrawn")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                QuickPayActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }else if(BillInfo[0].equals("1")) {
                if(BillInfo[1].equals("1")){//curbill
                    LayoutInflater layoutInflater = (LayoutInflater) QuickPayActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.activity_popup_collection_fetch,null);
                    closePopupBtn = (Button) customView.findViewById(R.id.submit);
                    backBtn= (Button) customView.findViewById(R.id.back);
                    //pipeDelBillInfo="1|1|102S34343|1000|12-09-2019|580|03-08-2019"; //curbill
                    Log.d("DemoApp", " BillInfo[2]   " + BillInfo[2]);//authoriztion check
                    strName=(TextView)customView.findViewById(R.id.Name);
                    strcbill=(TextView)customView.findViewById(R.id.cbill);
                    strdudt=(TextView)customView.findViewById(R.id.dudt);
                    strpmt=(TextView)customView.findViewById(R.id.pmt);
                    strpmtdt=(TextView)customView.findViewById(R.id.pmtdt);
                    strconid=(TextView)customView.findViewById(R.id.conid);
                    strName.setText(BillInfo[7]);
                    strconid.setText(BillInfo[2]);
                    strcbill.setText(BillInfo[3]);
                    strdudt.setText(BillInfo[4]);
                    strpmt.setText(BillInfo[5]);
                    strpmtdt.setText(BillInfo[6]);
                    //instantiate popup window
                    popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                    //display the popup window
                    popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                    //close the popup window on button click
                    closePopupBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    backBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
            }
        }
    }
}

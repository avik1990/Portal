package com.cesu.itcc05.consumeportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
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

import static com.cesu.itcc05.consumeportal.CommonMethods.getAppLink;
import static com.cesu.itcc05.consumeportal.CommonMethods.getAppVersion;
import static java.lang.System.exit;

public class RegisterActivity extends AppCompatActivity {
    private static ImageView strconsID;
    private static Button btnsubmit;
    private ImageView strhint;
    private static ImageView stremail;
    private static ImageView strMobno;
    private static ImageView strstar1;
    private static TextView strmobmsg,strSwVersion;
    private  EditText strconsIDval;
    private static EditText stremailval;
    private static EditText strmobval;
    private String consIDval="";
    private String emailval="";
    private String mobval="";
    private String imeinum = "";
    public  static final int RequestPermissionCode  = 1 ;
    public  static final int WRITE_REQUEST_CODE  = 1 ;
    private String strpwd="";
    private String strrepwd="";
    final Context context = this;
    private int chkresponse=1;
    private String AuthURL=null;
    Button showPopupBtn, closePopupBtn,UpdateSwBtn;
    PopupWindow popupWindow;
    ConstraintLayout linearLayout1;
    private   DatabaseAccess databaseAccess=null;
    private CheckBox TermCond;
    private String versionID="";
    private String contype="";
    private String StrUrl="";
    private String CompanyID="";
    private String swversion = "";
    private  Button btntrmscnd;
    private TextView tncLabel;
    private ConstraintLayout strconsid1;
    private static EditText pwd,repwd;
    private ProgressBar spinner;
    private String MobileNo = "";
    private String ConsID = "";
    private String ConsName = "";
    private String ConsAdd = "";
    private String ActKey = "";
    private String email = "";
    private String matchflg="0";
    private String StrvalidIpget="0";
    private String StrvalidIpgeturl="StrvalidIpgeturl";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        strconsID=(ImageView)findViewById(R.id.consID);
        strhint=(ImageView) findViewById(R.id.hint);
        stremail=(ImageView)findViewById(R.id.email);
        strMobno=(ImageView)findViewById(R.id.Mobno);
        strstar1=(ImageView)findViewById(R.id.star1);

        //strpwdimg=(ImageView)findViewById(R.id.pwdimg);

        strmobmsg=(TextView)findViewById(R.id.mobmsg);
        strconsIDval=(EditText) findViewById(R.id.consIDval);
        stremailval=(EditText) findViewById(R.id.emailval);
        strmobval=(EditText) findViewById(R.id.mobval);
      //  Button btnNewuser = (Button) findViewById(R.id.Newuser);
      //  Button btnRegUser = (Button) findViewById(R.id.RegUser);
      //  Button btnexit = (Button) findViewById(R.id.exit);
        btnsubmit = (Button) findViewById(R.id.submit);

        btntrmscnd = (Button) findViewById(R.id.bttrmscnd);
        tncLabel = (TextView) findViewById(R.id.tncLabel);
        ImageView btnhint = (ImageView) findViewById(R.id.hint);
      //  strrpedtbl=(TableRow)findViewById(R.id.rpedtbl);

        strconsid1=(ConstraintLayout) findViewById(R.id.consid1);


        StrvalidIpget = "1";
        StrvalidIpgeturl = "http://portal.tpcentralodisha.com:8070";



  /*      if(StrvalidIpget.equals("0")){
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                changeTextStatus(true);
            } else {
                changeTextStatus(false);
            }
        }else  if(StrvalidIpget.equals("2")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Server Busy");
            alertDialogBuilder.setMessage("Try after  Some Time !!")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent ChkServerLiveCon = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(ChkServerLiveCon);
                            finish();
                        }
                    })
                    .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            RegisterActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            // internetStatus.setText("Internet Disconnected.");
            //  internetStatus.setTextColor(Color.parseColor("#ff0000"));

        }else {*/
            Log.d("DemoApp", "in Loop StrvalidIpgeturl : " + StrvalidIpgeturl);
            SharedPreferences urlsessionlink = getApplicationContext().getSharedPreferences("seslinkval", 0);
            SharedPreferences.Editor urllinkaddress = urlsessionlink.edit();
            urllinkaddress.putString("strurladdr", StrvalidIpgeturl); //Storing div_code
            urllinkaddress.commit(); // commit changes
            StrUrl = StrvalidIpgeturl+"/ePortalAPP/ePortal_App.jsp?";// by santi 040520
            Log.d("DemoApp", "in Loop StrvalidIpgeturl : +StrUrl :" + StrUrl);// by santi 040520
            ///End////////////



            strconsid1.setVisibility(View.VISIBLE);
            contype="0"; //for new 1 for register consumer
            //////////bottom navigation added///////////
            // TextView bottomnavview = ((TextView) findViewById(R.id.bottomnavview));
            //setupBottomBar(bottomnavview);
            ////////////////checking software version/////////////////
            databaseAccess = DatabaseAccess.getInstance(context);
            databaseAccess.open();
            String strSelectSQL_01 = "SELECT VERSION_NO,VERSION_NAME,COMPANYID "+
                    "FROM SWVERSION WHERE RECORD_STS=1";
            Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
            Log.d("DemoApp", "Query SQL " + strSelectSQL_01);

            versionID="";
            while (cursor.moveToNext()) {
                swversion="Version:"+cursor.getString(0)+"-"+cursor.getString(1);
                Log.d("DemoApp", "in Loop conid" + swversion);
                versionID=cursor.getString(0);
                CompanyID=cursor.getString(2);
            }
            versionID = getAppVersion();

            cursor.close();
            databaseAccess.close();
            //////////////////////////////
            ////////checking user registration...////////////////
            databaseAccess = DatabaseAccess.getInstance(context);
            databaseAccess.open();
            strSelectSQL_01=null;
            cursor=null;
            strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO "+
                    "FROM CONSUMERDTLS WHERE VALIDATE_FLG=1";
            cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
            Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
            String conid = "x";
            String mobno="0";
            while (cursor.moveToNext()) {
                conid=cursor.getString(0);
                mobno=cursor.getString(1);
                Log.d("DemoApp", "in Loop conid" + conid);
            }
            cursor.close();
            databaseAccess.close();
            if(!conid.equals("x")){
                Intent Pwdmgmt = new Intent(getApplicationContext(), UserDashboardActivity.class);
                Bundle Pwdmgmtdtls = new Bundle();
                Pwdmgmtdtls.putString("ConsID", conid);
                //  Pwdmgmtdtls.putString("MobileNo", mobno);
                //  Pwdmgmtdtls.putString("versionID", versionID);
                Pwdmgmt.putExtras(Pwdmgmtdtls);
                startActivity(Pwdmgmt);
                finish();
            }
            /////////////////////////////////////////////////

            //terms and condition agree
            TermCond = (CheckBox)findViewById(R.id.tmcnd);

            TermCond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if (((CompoundButton) view).isChecked()) {
                        // System.out.println("Checked");
                        btnsubmit.setEnabled(true);
                        Log.d("DemoApp", "chk b coxlick ");
                        btnsubmit.setClickable(true);
                    } else {
                        btnsubmit.setEnabled(false);
                        btnsubmit.setClickable(false);
                        Log.d("DemoApp", "chk box unclick ");
                    }*/
                }
            });
            /////////////////////////////////////

            linearLayout1 = (ConstraintLayout) findViewById(R.id.layout);//for pop up
            //   if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
            //  android.Manifest.permission.READ_PHONE_STATE))
            //   {

            //  } else {

            //   ActivityCompat.requestPermissions(MainActivity.this, new String[]{
            //        Manifest.permission.READ_PHONE_STATE}, RequestPermissionCode);
            //  TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            //  imeinum = tm.getDeviceId();
            // }
            int PERMISSION_ALL = 1;
            String[] PERMISSIONS = {

                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                    //  android.Manifest.permission.CAMERA
            };

            if(!hasPermissions(this, PERMISSIONS)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
            //imeinum = tm.getDeviceId();
            imeinum = CommonMethods.getUniqueNumber(strmobval.getText().toString().trim());

          /*  btnNewuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contype="0";
                    strconsID.setVisibility(View.VISIBLE);
                    strconsIDval.setVisibility(View.VISIBLE);
                    strhint.setVisibility(View.VISIBLE);
                    stremail.setVisibility(View.VISIBLE);
                    stremailval.setVisibility(View.VISIBLE);
                    strstar1.setVisibility(View.VISIBLE);

                    strconsid1.setVisibility(View.VISIBLE);
                    strmobmsg.setText("All the SMS will sent to this number ");

                }
            });*/
            tncLabel.setText(Html.fromHtml(String.format(getString(R.string.tnc_label))));
            tncLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater layoutInflater = (LayoutInflater) RegisterActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            btntrmscnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater layoutInflater = (LayoutInflater) RegisterActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            btnhint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater layoutInflater = (LayoutInflater) RegisterActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.activity_conumer_id,null);
                   ImageView closePopupBtns = (ImageView) customView.findViewById(R.id.closePopupBtn);
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
            /*btnexit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    exit(0);
                }
            });*/

            /*btnRegUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // strconsIDval.setText("0");
                    contype="1";
                    //   String mobval="";
                    //  strmobval=(EditText) findViewById(R.id.mobval);
                    //   mobval = strmobval.getText().toString();
                    //  Log.d("DemoApp", "consIDval" + consIDval);
                    // if(TextUtils.isEmpty(mobval) ) {
                    //  strmobval.setError("Blank Mobile No Not taken");
                    // Log.d("DemoApp", "Query SQL " + consIDval);
                    //}else if(mobval.length()>0 && mobval.length()<10){
                    //  strmobval.setError("Enter Valid Mobile of digit 10");
                    // }
                    strconsID.setVisibility(View.GONE);
                    strconsIDval.setVisibility(View.GONE);
                    strhint.setVisibility(View.INVISIBLE);
                    btntrmscnd.setVisibility(View.INVISIBLE);
                    //TermCond.setVisibility(View.INVISIBLE);
                    stremail.setVisibility(View.GONE);
                    stremailval.setVisibility(View.VISIBLE);
                    strstar1.setVisibility(View.GONE);

                    strconsid1.setVisibility(View.GONE);
                    strmobmsg.setText("Enter Registered Mobile No.");


                }
            });*/
            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    strconsIDval.setAllCaps(true);
                    consIDval = strconsIDval.getText().toString().trim().toUpperCase();

                    emailval = stremailval.getText().toString().trim();
                    mobval = strmobval.getText().toString().trim();

                    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    //  if(contype.equals("new")) {//new user
                    if(contype.equals("1")){//registered user
                        pwd=(EditText) findViewById(R.id.pwd);
                        repwd=(EditText) findViewById(R.id.rpwd);
                        ////Added on 040520 by Santi
                        pwd.setText("#3Pw#");
                        repwd.setText("#3Pw#");
                        ////
                        consIDval="999999999999";
                        emailval="";
                        strpwd = pwd.getText().toString();
                        strrepwd = repwd.getText().toString();
                        if (mobval.length() >= 0 && mobval.length() < 10) {
                            strmobval.setError("Enter Valid Mobile No");
                            strmobval.requestFocus();
                            spinner.setVisibility(View.INVISIBLE);
                        }else if (TextUtils.isEmpty(strpwd)) {
                            pwd.setError("Please enter password");
                            pwd.requestFocus();
                            spinner.setVisibility(View.INVISIBLE);
                        } else if (TextUtils.isEmpty(strrepwd)) {
                            repwd.setError("Pleasse Re-enter Password");
                            repwd.requestFocus();
                            spinner.setVisibility(View.INVISIBLE);
                        } else if (!strpwd.equals(strrepwd)) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setTitle("Password Mismatch");
                            alertDialogBuilder.setMessage("Enter Password Correctly")
                                    .setCancelable(false)
                                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            RegisterActivity.this.finish();
                                        }
                                    });
                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // show it
                            alertDialog.show();
                            spinner.setVisibility(View.INVISIBLE);
                        }else {
                            //Added on 040520 by santi
                            String strdbtype=consIDval.substring(3,4);
                            if(strdbtype.equals("1") || strdbtype.equals("l") || strdbtype.equals("i")){
                                consIDval=consIDval.substring(0,3)+"I"+consIDval.substring(4);
                            }else{
                                consIDval=consIDval.substring(0,3)+"S"+consIDval.substring(4);
                            }
                            Log.d("DemoApp", "strdbtype" + strdbtype);
                            Log.d("DemoApp", "consIDval" + consIDval);
                            //
                            if (mobval.length() == 10) {
                                if (CommonMethods.isValidMobile(mobval)) {
                                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                                    if (networkInfo != null && networkInfo.isConnected()) {
                                        //changeTextStatus(true);
                                    } else {
                                       // changeTextStatus(false);
                                    }
                                } else {
                                    strmobval.setError("Mobile digit start from 6/7/8/9");
                                }
                            }
                            ///end
                        }
                    }else if(contype.equals("0")) { //new user
                        if (TextUtils.isEmpty(consIDval)) {
                            strconsIDval.setError("Please enter consumer/CA number");
                            strconsIDval.requestFocus();
                         //   Toast.makeText(RegisterActivity.this,"Please enter consumer number",Toast.LENGTH_SHORT).show();

                            Log.d("DemoApp", "Query SQL " + consIDval);
                        } else if (consIDval.length() > 0 && consIDval.length() < 12) {
                            strconsIDval.setError("Enter Valid Consumer ID of 12 character");
                            strconsIDval.requestFocus();
                          //  Toast.makeText(RegisterActivity.this,"Enter Valid Consumer ID of 12 digit",Toast.LENGTH_SHORT).show();

                        }
                        else if (mobval.length()==0){
                            strmobval.setError("Please enter mobile No.");
                            strmobval.requestFocus();
                        }
                        else if (mobval.length() >= 0 && mobval.length() < 10) {
                            strmobval.setError("Enter Valid Mobile No");
                            strmobval.requestFocus();
                          //  Toast.makeText(RegisterActivity.this,"Enter Valid Mobile No",Toast.LENGTH_SHORT).show();

                        } else if (!emailval.matches(emailPattern) && emailval.length() > 0) {
                           // Toast.makeText(RegisterActivity.this,"Enter Valid e-Mail ID",Toast.LENGTH_SHORT).show();

                            stremailval.setError("Enter Valid e-Mail ID");
                            stremailval.requestFocus();
                        }

                        else if (!(TermCond.isChecked())){
                            Toast.makeText(RegisterActivity.this,"Please accept term & condition",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //Added on 040520 by santi
                            String strdbtype=consIDval.substring(3,4);
                            if(strdbtype.equals("1") || strdbtype.equals("l") || strdbtype.equals("i")){
                                consIDval=consIDval.substring(0,3)+"I"+consIDval.substring(4);
                            }else{
                                consIDval=consIDval.substring(0,3)+"S"+consIDval.substring(4);
                            }
                            Log.d("DemoApp", "consIDval" + consIDval);
                            //
                            //Added on 040520 by Santi
                            if (mobval.length() == 10) {
                                if (CommonMethods.isValidMobile(mobval)) {
                                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                                    if (networkInfo != null && networkInfo.isConnected()) {
                                        funcUrlCheck(1);
                                    } else {
                                        //changeTextStatus(false);
                                    }
                                } else {
                                    strmobval.setError("Mobile digit start from 6/7/8/9");
                                }
                            }
                            ///end

                        }
                    }
                    //  }else{
                    //  if (mobval.length() >= 0 && mobval.length() < 10) {
                    //   strmobval.setError("Enter Valid Mobile No");
                    //} else {
                    //  ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    //  NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    //  if (networkInfo != null && networkInfo.isConnected()) {
                    //      changeTextStatus(true);
                    //  } else {
                    //      changeTextStatus(false);
                    //  }
                    //  }
                    //  }
                }
            });

        }//else end of server connection        040520 by santi



 //   }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
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

        //Toast.makeText(context, ""+versionID, Toast.LENGTH_SHORT).show();

        if(resCode==1){

            // send imei number to get mobile activation key if new one then activation key 1111 if different imei no then 1111
            //  if(contype.equals("new")) { CompanyID contype consIDval  emailval mobval
            AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=1&RegFlag="+contype+"&ConsumerID="+consIDval+"&Mobile_No="+mobval+"&e_Mail="+emailval+"&SoftVer="+versionID+"&IMEI="+imeinum;

            new UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            AuthURL=StrUrl+"CompanyID="+CompanyID+"&RequestType=1&RegFlag="+contype+"&ConsumerID="+consIDval+"&Mobile_No="+mobval+"&e_Mail="+emailval+"&SoftVer="+versionID+"&IMEI="+imeinum;
            new UserAuthOnline().execute(AuthURL);
        }else{
            AuthURL="ServerOut";
        }
        Log.d("DemoApp", "in Loop AuthURL" + AuthURL);
        return AuthURL;
    }
    private  class UserAuthOnline extends AsyncTask<String, Integer, String> {
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
                alertDialogBuilder.setTitle("Not Connected to Server");
                alertDialogBuilder.setMessage("Please Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RegisterActivity.this.finish();
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
            //btnsubmit.setVisibility(View.GONE);
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                //   progressDialog = ProgressDialog.show(MainActivity.this, "", "");
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
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RegisterActivity.this.finish();
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
            //pipeDelBillInfo="0|0|1|1|1111111111|102S03127108|0|0|00000000|dghghgsdghgsd@jhjhh.com";
            // 1|1|BETA TESTING|0|0|0|0|0|0|0|0
            String[] BillInfo = pipeDelBillInfo.split("[|]");
            MobileNo = "";
            ConsID = "";
            ConsName = "";
            ConsAdd = "";
            ActKey = "";
            email = "";
            try {
                MobileNo = BillInfo[5];
                ConsID = BillInfo[6];
                ConsName = BillInfo[9];
                ConsAdd = BillInfo[10];
                ActKey = BillInfo[8];
                email = BillInfo[7];
            }catch(Exception e){}
            //new user
            //Toast.makeText(context, ""+BillInfo[1], Toast.LENGTH_SHORT).show();

            if(BillInfo[0].equals("1")){
                //  Log.d("DemoApp", "[1]   ") ;
               /* if(BillInfo[1].equals("1")){ //software update =1 if there//Mean mandatory update
                    LayoutInflater layoutInflater = (LayoutInflater) RegisterActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.activity_popup,null);
                    closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
                    UpdateSwBtn = (Button) customView.findViewById(R.id.UpdateSwBtn);
                    //instantiate popup window
                    popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                    //display the popup window
                    popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                    //close the popup window on button click
                    UpdateSwBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getAppLink(getApplicationContext()))));
                            popupWindow.dismiss();
                        }
                    });
                    closePopupBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    Log.d("DemoApp", "[2]   ") ;
                }else if(BillInfo[1].equals("2")){ //software update =2 if there
                    LayoutInflater layoutInflater = (LayoutInflater) RegisterActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.activity_popup,null);
                    UpdateSwBtn = (Button) customView.findViewById(R.id.UpdateSwBtn);
                    closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
                    //instantiate popup window
                    closePopupBtn.setText("Proceed");
                    popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                    //display the popup window
                    popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                    UpdateSwBtn.setOnClickListener(new View.OnClickListener() { //not mandatory software update
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getAppLink(getApplicationContext()))));
                            popupWindow.dismiss();
                        }
                    });
                    //close the popup window on button click
                    closePopupBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    Log.d("DemoApp", "[3]   ") ;
                }*/

             if(BillInfo[1].equals("0")){
                    if(BillInfo[3].equals("0")  ) { //no privilage=0
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("You are not permitted");
                        alertDialogBuilder.setMessage("Send your request for activation to android@tpcentralodisha.com")
                                .setCancelable(false)
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        RegisterActivity.this.finish();
                                    }
                                });
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                        Log.d("DemoApp", "[4]   ") ;
                    }else if(BillInfo[3].equals("1")){ // having privilage
                        //Log.d("DemoApp", "[5]   ") ;
                        if(BillInfo[4].equals("0")){ // mobile no not exist
                            Intent Pwdmgmt = new Intent(getApplicationContext(), PasswordActivity.class);
                            Bundle Pwdmgmtdtls = new Bundle();
                            Pwdmgmtdtls.putString("ActKey", ActKey);
                            Pwdmgmtdtls.putString("MobileNo", MobileNo);
                            Pwdmgmtdtls.putString("ConsID", ConsID);
                            Pwdmgmtdtls.putString("ConsName", ConsName);
                            Pwdmgmtdtls.putString("ConsAdd", ConsAdd);
                            Pwdmgmtdtls.putString("email", email);
                            Pwdmgmtdtls.putString("imeinum", imeinum);
                            Pwdmgmtdtls.putString("versionID", versionID);
                            Pwdmgmtdtls.putString("CompanyID", CompanyID);
                            Pwdmgmtdtls.putString("swversion", swversion);
                            Pwdmgmtdtls.putString("Strtyp","1");
                            Pwdmgmtdtls.putString("strpwd1", "xx");
                            Pwdmgmtdtls.putString("strrepwd1", "yx");

                            Pwdmgmt.putExtras(Pwdmgmtdtls);
                            startActivity(Pwdmgmt);
                            finish();
                            Log.d("DemoApp", "[6]   ") ;
                        }else if(BillInfo[4].equals("1")){// mobile no  exist
                            Log.d("DemoApp", "[7]   ") ;
                            databaseAccess = DatabaseAccess.getInstance(context);
                            databaseAccess.open();
                            String strSelectSQL_01 = "select IMEI_NUMBER from CONSUMERDTLS WHERE VALIDATE_FLG=1";
                            Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_01);
                            Cursor rs = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
                            String imeifetch="";
                            while (rs.moveToNext()) {
                                imeifetch = rs.getString(0);
                            }
                            matchflg="0";
                            Log.d("DemoApp", "[imeinum]   "+imeinum) ;
                            Log.d("DemoApp", "[imeifetch]   "+imeifetch) ;
                            if(imeifetch.equals(imeinum)){
                                matchflg="1";
                            }
                            databaseAccess.close();

                            //Added as per CEO version
                            String Strtyp="1";//if o then go to dashborad via password activity
                            //
                            if(matchflg.equals("0")){
                                //added by santi on 17/04/2020
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setTitle("Your Mob. Number registered with ID:"+ConsID);
                                alertDialogBuilder.setMessage("Name:"+ConsName+"\n"+"Proceed for Login with Password")//changed on 21.04.20 by Santi
                                        .setCancelable(false)
                                        .setPositiveButton("RESET", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                String flgtype="0"; // Mobile No registered but installing app  new
                                                Intent Pwdmgmt = new Intent(getApplicationContext(), PasswordActivity.class);
                                                Bundle Pwdmgmtdtls = new Bundle();
                                                Pwdmgmtdtls.putString("ActKey", ActKey);
                                                Pwdmgmtdtls.putString("MobileNo", MobileNo);
                                                Pwdmgmtdtls.putString("ConsID", ConsID);
                                                Pwdmgmtdtls.putString("ConsName", ConsName);
                                                Pwdmgmtdtls.putString("imeinum", imeinum);
                                                Pwdmgmtdtls.putString("matchflg", matchflg);
                                                Pwdmgmtdtls.putString("ConsAdd", ConsAdd);
                                                Pwdmgmtdtls.putString("email", email);
                                                Pwdmgmtdtls.putString("versionID", versionID);
                                                Pwdmgmtdtls.putString("CompanyID", CompanyID);
                                                Pwdmgmtdtls.putString("swversion", swversion);
                                                Pwdmgmtdtls.putString("Strtyp","1");
                                                Pwdmgmtdtls.putString("strpwd1", "xx");
                                                Pwdmgmtdtls.putString("strrepwd1", "yx");
                                                Pwdmgmt.putExtras(Pwdmgmtdtls);
                                                startActivity(Pwdmgmt);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                RegisterActivity.this.finish();
                                            }
                                        });
                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // show it
                                alertDialog.show();
                                //end

                            }else{
                                Intent Pwdmgmt = new Intent(getApplicationContext(), PasswordNewActivity.class);
                                Bundle Pwdmgmtdtls = new Bundle();
                                Pwdmgmtdtls.putString("MobileNo", MobileNo);
                                Pwdmgmtdtls.putString("ConsID", ConsID);
                                Pwdmgmt.putExtras(Pwdmgmtdtls);
                                startActivity(Pwdmgmt);
                                finish();
                            }
                        }

                    }
                }
            }else if(BillInfo[0].equals("9")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(getString(R.string.error_title));
                alertDialogBuilder.setMessage(getString(R.string.ca_not_found))
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RegisterActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }else if(BillInfo[0].equals("8")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Mobile Number Not Registered");
                alertDialogBuilder.setMessage("Enter Correct Mobile Number")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RegisterActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Server Busy");
                alertDialogBuilder.setMessage("Please Try Again")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RegisterActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }
        }
    }
}

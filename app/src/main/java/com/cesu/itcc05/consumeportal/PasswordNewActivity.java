package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static com.cesu.itcc05.consumeportal.CommonMethods.getAppLink;
import static com.cesu.itcc05.consumeportal.CommonMethods.getAppVersion;
import static java.lang.System.exit;

public class PasswordNewActivity extends AppCompatActivity {
    private   DatabaseAccess databaseAccess=null;
    final Context context = this;
    private String MobileNo = "";
    private String ConsID = "";
    private static EditText loginpwd;
    private String strloginpwd="";
    PopupWindow popupWindow;
    ConstraintLayout linearLayout1;
    private CheckBox pwdcheckbox;
    private int chkresponse=1;
    private String AuthURL=null;
    private String StrUrl="";
    private String CompanyID="";
    private String versionID="";
    Button showPopupBtn, closePopupBtn,UpdateSwBtn;
    private  String mobval="";
    private  String consIDval="";
    private String imeinum="";
    private ProgressBar spinner;
    private Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_new);

       // android.support.v7.widget.Toolbar toolbarback = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);


        // setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Password Ma");
    //////////bottom navigation added///////////
        //TextView bottomnavview = ((TextView) findViewById(R.id.bottomnavview));
       // setupBottomBar(bottomnavview);
        //////////////////////////////
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/ePortalAPP/ePortal_App.jsp?";
        Bundle Pwdmgmtdtls = getIntent().getExtras();
        MobileNo = Pwdmgmtdtls.getString("MobileNo");
        ConsID = Pwdmgmtdtls.getString("ConsID");

        loginpwd=(EditText) findViewById(R.id.Existpwd);

        loginpwd.setTransformationMethod(new PasswordTransformationMethod()); // Hide password initially
        btnlogin = (Button) findViewById(R.id.login);
        Button btnexit = (Button) findViewById(R.id.exit);
        TextView fgtPwd = (TextView) findViewById(R.id.fgtPwd);
        fgtPwd.setPaintFlags(fgtPwd.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
       // ImageButton btnqlinks = (ImageButton) findViewById(R.id.qlinks);
       // ImageButton btonlinepay = (ImageButton) findViewById(R.id.onlinepay);
        linearLayout1 = (ConstraintLayout) findViewById(R.id.layout);//for pop up
        pwdcheckbox = (CheckBox) findViewById(R.id.checkBox);
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT VERSION_NO,VERSION_NAME,COMPANYID "+
                "FROM SWVERSION WHERE RECORD_STS=1";
        Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
        String swversion = "";
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


        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                exit(0);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinner.setVisibility(View.VISIBLE);
                    strloginpwd = loginpwd.getText().toString();
                    databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    String strSelectSQL_01 = "SELECT CONSUMER_ID,PASSWORD,MOBILENO,IMEI_NUMBER "+
                            "FROM CONSUMERDTLS  WHERE CONSUMER_ID='" + ConsID + "' AND VALIDATE_FLG=1" ;
                    Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
                    Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
                    String pasword = "0";
                    while (cursor.moveToNext()) {
                        consIDval= cursor.getString(0);
                        pasword = cursor.getString(1);
                        mobval= cursor.getString(2);
                        imeinum= cursor.getString(3);
                        Log.d("DemoApp", "in Loop pasword" + pasword);
                    }
                    cursor.close();
                    databaseAccess.close();

                   if(pasword.equals(strloginpwd)){
                       ///////////////
                       ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                       NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                       if (networkInfo != null && networkInfo.isConnected()) {
                           changeTextStatus(true);
                       } else {
                           changeTextStatus(false);
                       }

                       //////////////////


                   }else{
                       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                       alertDialogBuilder.setTitle("Password Mismatch");
                       alertDialogBuilder.setMessage("Enter Password Correctly")
                               .setCancelable(false)
                               .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                       dialog.cancel();
                                   }
                               })
                               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                       onBackPressed();
                                   }
                               });
                       // create alert dialog
                       AlertDialog alertDialog = alertDialogBuilder.create();
                       // show it
                       alertDialog.show();
                       spinner.setVisibility(View.INVISIBLE);
                   }
            }
        });
        fgtPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forpass = new Intent(getApplicationContext(), popupPasswordActivity.class);
                startActivity(forpass);
                finish();
                    }
        });
        pwdcheckbox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if (isChecked) {
                    loginpwd.setTransformationMethod(null); // Show password when box checked

                } else {
                    loginpwd.setTransformationMethod(new PasswordTransformationMethod()); // Hide password when box not checked

                }
            }
        } );

    }
    //////////////////////////////
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
                            onBackPressed();
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

        //Toast.makeText(context, ""+versionID, Toast.LENGTH_SHORT).show();
        if(resCode==1){
            AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=2&ConsumerID="+consIDval+"&Mobile_No="+mobval+"&SoftVer="+versionID;
            new UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            //Request type 2 means login activity
            AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=2&ConsumerID="+consIDval+"&Mobile_No="+mobval+"&SoftVer="+versionID;
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
                                onBackPressed();
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
            btnlogin.setEnabled(false);
            btnlogin.setClickable(false);
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
               // progressDialog = ProgressDialog.show(PasswordNewActivity.this, " ", " ");

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
                               onBackPressed();
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
          //  progressDialog.dismiss();

            spinner.setVisibility(View.GONE);
            String pipeDelBillInfo =str;
            //pipeDelBillInfo="0|0|1|1|1111111111|102S03127108|0|0|00000000|dghghgsdghgsd@jhjhh.com";
            // 1|1|BETA TESTING|0|0|0|0|0|0|0|0
            String[] BillInfo = pipeDelBillInfo.split("[|]");
            String MobileNo=BillInfo[5];
            String ConsID=BillInfo[6];
            Log.d("DemoApp", "in    MobileNo1:2" + MobileNo);
            if(BillInfo[0].equals("1")){
                //  Log.d("DemoApp", "[1]   ") ;
            /*    if(BillInfo[1].equals("1")){ //software update =1 if there
                    LayoutInflater layoutInflater = (LayoutInflater) PasswordNewActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    LayoutInflater layoutInflater = (LayoutInflater) PasswordNewActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                }else*/ if(BillInfo[1].equals("0")){
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
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       onBackPressed();
                                    }
                                });
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                        Log.d("DemoApp", "[4]   ") ;
                    }else if(BillInfo[3].equals("1")){ // having privilage
                        //Log.d("DemoApp", "[5]   ") ;
                        if(BillInfo[4].equals("1")){ // mobile no exist
                            if(ConsID.equals(consIDval)){
                                SharedPreferences sessionssodata = getApplicationContext().getSharedPreferences("sessionval", 0);
                                SharedPreferences.Editor ssodata = sessionssodata.edit();
                                ssodata.putString("ConsID", ConsID.toUpperCase()); //Storing div_code
                                ssodata.putString("mobval", mobval); //Storing div_code
                                ssodata.putString("imeinum", imeinum); //Storing div_code
                                ssodata.putString("CompanyID", CompanyID); //Storing div_code
                                Log.d("DemoApp", "in    imeinum:" + imeinum);
                                ssodata.commit(); // commit changes
                                /*//Added on 040520 by santi
                                int dbmobflg=0;
                                int ftchmobflg=0;
                                Log.d("DemoApp", "in    MobileNo1:" + MobileNo);
                                Log.d("DemoApp", "in    mobval1:" + mobval);
                                try {
                                    if (mobval.isEmpty() || mobval.equalsIgnoreCase(null) || mobval == null) {
                                        dbmobflg = 0;
                                    } else {
                                        dbmobflg = 1;
                                    }
                                }catch (Exception e)   {
                                    e.printStackTrace();
                                }
                                try {
                                    if(MobileNo.isEmpty() || MobileNo.equalsIgnoreCase(null) || MobileNo==null){
                                        ftchmobflg=0;
                                    }else{
                                        ftchmobflg=1;
                                    }
                                }catch (Exception e)   {
                                    e.printStackTrace();
                                }
                                if(ftchmobflg==1 && dbmobflg==1 ){
                                    Log.d("DemoApp", "in    MobileNo:" + MobileNo);
                                    Log.d("DemoApp", "in    mobval:" + mobval);
                                    if(!mobval.equals(MobileNo)){
                                        Log.d("DemoApp", "in    Mismatch:" );
                                        databaseAccess = DatabaseAccess.getInstance(context);
                                        databaseAccess.open();
                                        String strupdSelectSQL_01 = "";
                                        strupdSelectSQL_01 = "UPDATE CONSUMERDTLS SET MOBILENO='" + MobileNo + "' WHERE CONSUMER_ID='" + ConsID + "' AND MOBILENO='" + mobval + "' AND VALIDATE_FLG=1";
                                        Log.d("DemoApp", "strSelectSQL_01" + strupdSelectSQL_01);
                                        DatabaseAccess.database.execSQL(strupdSelectSQL_01);
                                        databaseAccess.close();
                                    }
                                }
                                ///end////*/
                                Intent ConDashboard = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(ConDashboard);
                                finish();
                            }else{
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setTitle("Consumer No Mismatch");
                                alertDialogBuilder.setMessage(getString(R.string.contact_us))
                                        .setCancelable(false)
                                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                onBackPressed();
                                            }
                                        });
                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // show it
                                alertDialog.show();
                            }

                            Log.d("DemoApp", "[6]   ") ;
                        }else if(BillInfo[4].equals("1")){// mobile no not exist
                            Log.d("DemoApp", "[7]   ") ;
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setTitle(getString(R.string.error_title));
                            alertDialogBuilder.setMessage(getString(R.string.ca_not_found))
                                    .setCancelable(false)
                                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                           onBackPressed();
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
            }else if(BillInfo[0].equals("9")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(getString(R.string.error_title));
                alertDialogBuilder.setMessage(getString(R.string.invalid_mobile))
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onBackPressed();
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
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onBackPressed();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    /*
    /////////////Bottom Navigation added/////////////////////////
    private void setupBottomBar(@NonNull final TextView textView) {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_bar);

        BottomBarItem item = new BottomBarItem(R.drawable.bottom_bar_contactus, R.string.bottomcontctus);
        BottomBarItem item1 = new BottomBarItem(R.drawable.bottom_bar_facebook, R.string.bottomfb);
        BottomBarItem item2 = new BottomBarItem(R.drawable.bottom_bar_twitter, R.string.bottomtwiter);
        BottomBarItem item3 = new BottomBarItem(R.drawable.bottom_bar_qrscan, R.string.bottomqrscan);
        //BottomBarItem item4 = new BottomBarItem(R.drawable.bottom_bar_default_icon, R.string.qrscan);

        bottomNavigationBar
                .addTab(item)
                .addTab(item1)
                .addTab(item2)
                .addTab(item3);

        bottomNavigationBar.setOnSelectListener(new BottomNavigationBar.OnSelectListener() {

            @Override
            public void onSelect(int position) {

                shownavContent(position, textView);
                //  Log.d("DemoApp", "position " + position);

            }

        });

        //only for translucent system navbar
        if (shouldAddNavigationBarPadding()) {
            //if your bottom bar has fixed height
            //you'll need to increase its height as well
            bottomNavigationBar.setPadding(0, 0, 0, getSystemNavigationBarHeight());
        }
    }

    private boolean shouldAddNavigationBarPadding() {
        return atLeastKitkat() && isInPortrait() && hasSystemNavigationBar();
    }

    private boolean isInPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private boolean atLeastKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    void shownavContent(int position, @NonNull TextView textView) {
        Log.d("DemoApp", "position " + position);
        // textView.setText("Tab " + position + " selected");
        if(position==1){
            String url = "https://www.facebook.com/cesu.odisha.58";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        if(position==2){
            String url = "https://twitter.com/CesuOdisha";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        if(position==3){
            Log.d("DemoApp", "[scan start]   ") ;
            Intent qrscanint = new Intent(getApplicationContext(), QrCodeScannerActivity.class);
            startActivity(qrscanint);
            finish();
        }
    }

    private int getSystemNavigationBarHeight() {
        Resources res = getResources();

        int id = res.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = 0;

        if (id > 0) {
            height = res.getDimensionPixelSize(id);
        }

        return height;
    }

    private boolean hasSystemNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        } else {
            boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !hasMenuKey && !hasBackKey;
        }
    }
    //////////////bottom navigation end///////////////
*/
}

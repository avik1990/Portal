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
import androidx.constraintlayout.solver.GoalRow;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static java.lang.System.exit;

public class ComplaintLoginActivity extends AppCompatActivity {
    private static ImageView strconsID;
    private static ImageView strhint;
    private static ImageView stremail;
    private static ImageView strMobno;
    private static ImageView strstar1;
    private static TextView strmobmsg,strConName;
    private static EditText strconsIDval;
    private static EditText stremailval;
    private static EditText strmobval,strAddr;
    private String consIDval="";
    private String emailval="";
    private String mobval="";
    private String imeinum = "";
    public  static final int RequestPermissionCode  = 1 ;
    final Context context = this;
    private int chkresponse=1;
    private String AuthURL=null;
    private String AuthURL1=null;
    Button showPopupBtn,UpdateSwBtn;
    PopupWindow popupWindow;
    ConstraintLayout linearLayout1;
    private   DatabaseAccess databaseAccess=null;
    private CheckBox TermCond;
 //rivate String compinfo="";
    private String strAddrval="";
    private String conname="";
    private Spinner  spinner2;
    private String spinpos1="";
    private String StrUrl="";
    private String CompanyID="";
    private String consacc="";
    private String divcode="";
    private String dbtype="";
    private static RadioGroup complnttype;
    private ConstraintLayout strtbl1,strtblrw4,strtblrw3,strtblrw2,strtblrw1,strconsid1;
    private TextView strtblrw5;
    private TableRow strtblrw6;
    private String conid = "";
    private  Button btnsubmit;
   private static String typStr = "";
    private String compchkval="";
    private ProgressBar spinner;
 //private String strcomsubcat="";
    private ImageView iv_back;
    ImageView btnhint;
    Button btntrmscnd;
    private String StrUrl1="";
    private String catDat="";
    private RadioButton regcoml,comsts;
    private Button closePopupBtn;
    private ImageView closePopupBtns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_login);
        // setSupportActionBar(toolbar);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_DynamicReport.jsp?";
        StrUrl1="http://portal.tpcentralodisha.com:8070"+"/ePortalAPP/ePortal_App.jsp?";
        strconsID=(ImageView)findViewById(R.id.consID);
        strhint=(ImageView) findViewById(R.id.hint);
        strhint.setVisibility(GONE);
        stremail=(ImageView)findViewById(R.id.email);
        strMobno=(ImageView)findViewById(R.id.Mobno);
        strstar1=(ImageView)findViewById(R.id.star1);
        strmobmsg=(TextView)findViewById(R.id.mobmsg);
        strConName=(TextView)findViewById(R.id.conNm);
        strconsIDval=(EditText) findViewById(R.id.consIDval);
        stremailval=(EditText) findViewById(R.id.emailval);
        strmobval=(EditText) findViewById(R.id.mobval);
        strAddr=(EditText) findViewById(R.id.Addr);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        strtbl1=(ConstraintLayout) findViewById(R.id.tbl1);
        strconsid1=(ConstraintLayout)findViewById(R.id.consid1);
        strtblrw2=(ConstraintLayout)findViewById(R.id.tblrw2);
        strtblrw3=(ConstraintLayout)findViewById(R.id.tblrw3);
        strtblrw4=(ConstraintLayout)findViewById(R.id.tblrw4);
        strtblrw5=(TextView) findViewById(R.id.tblrw5);
        strtblrw6=(TableRow) findViewById(R.id.tblrw6);
        TermCond = (CheckBox)findViewById(R.id.tmcnd);
        iv_back=findViewById(R.id.iv_backs);
        regcoml=findViewById(R.id.regcoml);
        comsts=findViewById(R.id.comsts);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        strconsid1.setVisibility(GONE);
        strtbl1.setVisibility(GONE);
        strtblrw2.setVisibility(GONE);
        strtblrw3.setVisibility(GONE);
        strtblrw4.setVisibility(GONE);
        strtblrw5.setVisibility(GONE);
        strtblrw6.setVisibility(GONE);
        strhint.setVisibility(GONE);


        // Button btnback = (Button) findViewById(R.id.back);
        btnsubmit = (Button) findViewById(R.id.submit);

        btnsubmit.setVisibility(GONE);
         btntrmscnd = (Button) findViewById(R.id.bttrmscnd);
        complnttype=(RadioGroup)findViewById(R.id.radioGroup);
        int selectedId = complnttype.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton comschkval = (RadioButton) findViewById(selectedId);
      //  compchkval=comschkval.getText().toString();
      //  Log.d("DemoApp", "comschkval" + compchkval);
        complnttype.clearCheck();
        /*
        strconsid1.setVisibility(View.VISIBLE);
        strtbl1.setVisibility(View.VISIBLE);
        strtblrw2.setVisibility(View.VISIBLE);
        strtblrw3.setVisibility(View.VISIBLE);
        strtblrw4.setVisibility(View.VISIBLE);
        strtblrw5.setVisibility(View.VISIBLE);
        strtblrw6.setVisibility(View.VISIBLE);
        */
         btnhint = (ImageView) findViewById(R.id.hint);
        btnhint.setVisibility(GONE);

        /////////////////////////////////////////////////

        ////////////

////////checking user registration...////////////////
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO,EMAIL,CONSUMER_ADD,CONSUMER_NAME "+
                "FROM CONSUMERDTLS WHERE VALIDATE_FLG=1";
        Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
        conid = "";
        String mobno="";
        String email="";
        String Addrs="";
        conname="";
        while (cursor.moveToNext()) {
            conid=cursor.getString(0);
            mobno=cursor.getString(1);
            email=cursor.getString(2);
            Addrs=cursor.getString(3);
            ///   conname=cursor.getString(4);
        }
        if(email.equals("0") || email.isEmpty() || email==null){ //added on 040520 by santi
            email="";
        }
        cursor.close();
        databaseAccess.close();
        strconsIDval.setText(conid);
        stremailval.setText(email);
        strmobval.setText(mobno);

        if (conid!=null || (!(conid.equalsIgnoreCase("")))){
            btnsubmit.setClickable(true);
            TermCond.setVisibility(GONE);
            btntrmscnd.setVisibility(GONE);
            strconsIDval.setVisibility(GONE);
            btnhint.setVisibility(GONE);
            spinner2.setVisibility(View.VISIBLE);
            strConName.setVisibility(View.VISIBLE);
            strtblrw6.setVisibility(GONE);
            // strConName.setEnabled(false);
            addItemsOnSpinner2();
        }
        else {
            if(conid.length()>5){
                TermCond.setVisibility(View.INVISIBLE);
                btntrmscnd.setVisibility(View.INVISIBLE);
            }else{
                TermCond.setVisibility(View.VISIBLE);
                btntrmscnd.setVisibility(View.VISIBLE);
            }
            strconsIDval.setVisibility(View.VISIBLE);
            TermCond.setVisibility(View.VISIBLE);
            btntrmscnd.setVisibility(View.VISIBLE);
            strconsIDval.setVisibility(View.VISIBLE);
            btnhint.setVisibility(View.VISIBLE); // COMMENTED
            spinner2.setVisibility(GONE);
            strtblrw6.setVisibility(View.VISIBLE);
            strConName.setVisibility(GONE);
        }

        getCategory();
        //  strAddr.setText(Addrs);
        Log.d("DemoApp", "in Loop conid" + conid);
        //terms and condition agree

        TermCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      /*          if(((CompoundButton) view).isChecked()){
                    // System.out.println("Checked");
                    btnsubmit.setEnabled(true);
                    Log.d("DemoApp", "chk b coxlick ");
                    btnsubmit.setClickable(true);
                } else {
                  //  btnsubmit.setEnabled(false);
                    //  btnsubmit.setClickable(false);
                    Log.d("DemoApp", "chk box unclick " );
                }*/
            }
        });
       /* if(compinfo.equals("reg")){
            btnsubmit.setClickable(true);
            TermCond.setVisibility(GONE);
            btntrmscnd.setVisibility(GONE);
            strconsIDval.setVisibility(GONE);
            btnhint.setVisibility(GONE);
            spinner2.setVisibility(View.VISIBLE);
            strConName.setVisibility(View.VISIBLE);
            // strConName.setEnabled(false);
            addItemsOnSpinner2();
        }else{
            if(conid.length()>5){
                TermCond.setVisibility(View.INVISIBLE);
                btntrmscnd.setVisibility(View.INVISIBLE);
            }else{
                TermCond.setVisibility(View.VISIBLE);
                btntrmscnd.setVisibility(View.VISIBLE);
            }
            strconsIDval.setVisibility(View.VISIBLE);
            TermCond.setVisibility(View.VISIBLE);
            btntrmscnd.setVisibility(View.VISIBLE);
            strconsIDval.setVisibility(View.VISIBLE);
            //btnhint.setVisibility(View.VISIBLE); // COMMENTED
            spinner2.setVisibility(GONE);
            strConName.setVisibility(GONE);
        }*/
        /////////////////////////////////////

        linearLayout1 = (ConstraintLayout) findViewById(R.id.layout);//for pop up
        btntrmscnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = (LayoutInflater) ComplaintLoginActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                LayoutInflater layoutInflater = (LayoutInflater) ComplaintLoginActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
/*
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(compinfo.equals("reg")){
                    Intent UserDashboard = new Intent(getApplicationContext(), ComplaintDashboardActivity.class);
                    startActivity(UserDashboard);
                    finish();
                }else{
                    Intent UserDashboard = new Intent(getApplicationContext(), QuicklinksDashboard.class);
                    startActivity(UserDashboard);
                    finish();
                }

            }
        });
        */
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strconsIDval.setAllCaps(true);
                if(typStr.equals("Reg")){
                    if (!(conid.equalsIgnoreCase(""))){
                        consIDval=  String.valueOf(spinner2.getSelectedItem()).trim();
                    }
                    else {
                        consIDval = strconsIDval.getText().toString().trim().toUpperCase();

                    }
                }else{
                    if (!(conid.equalsIgnoreCase(""))){
                        consIDval=  String.valueOf(spinner2.getSelectedItem()).trim();
                    }
                    else {
                        consIDval = strconsIDval.getText().toString().trim().toUpperCase();

                    }

                   // consIDval = strconsIDval.getText().toString().trim().toUpperCase();
                }
                emailval = stremailval.getText().toString();
                if (emailval.equalsIgnoreCase("null")){
                    emailval="";
                }

                mobval = strmobval.getText().toString();
                strAddrval= strAddr.getText().toString();
                String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                Log.d("DemoApp", "consIDval11 " + consIDval);
                if (typStr.equals("vie")) {//view Status
                    strmobval.setText("9999999999");
                    mobval="9999999999";
                    strAddr.setText("na");
                    emailval="abc@gmail.com";

                }


                if(TextUtils.isEmpty(consIDval) ) {
                    strconsIDval.setError("Please enter consumer number");
                    strconsIDval.requestFocus();

                    Log.d("DemoApp", "Query SQL " + consIDval);
                }else if(consIDval.length()>0 && consIDval.length()<12){
                    strconsIDval.setError("Consumer ID must be of 12 character");
                    strconsIDval.requestFocus();
                }
                else if (mobval.length()==0){
                    strmobval.setError("Please enter mobile number");
                    strmobval.requestFocus();
                }

                else if(mobval.length()>=0 && mobval.length()<10) {
                    strmobval.setError("Enter Valid Mobile No");
                    strmobval.requestFocus();

                } else if(strAddrval.length()==0 ) {
                    strAddr.setError("Please enter address");
                    strAddr.requestFocus();

                }/*else if(!emailval.matches(emailPattern) && emailval.length()>0) {
                    stremailval.setError("Enter Valid e-Mail No");

                }*/
                else if (!(TermCond.isChecked())){
                    Toast.makeText(ComplaintLoginActivity.this,getResources().getString(R.string.accept_term_con),Toast.LENGTH_SHORT).show();
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
                            strmobval.setError("Mobile number must be start from 6/7/8/9");
                        }
                    }
                    ///end
                }
            }
        });
        complnttype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                Log.d("DemoApp", " checkedId   " + checkedId);//authoriztion check
                typStr = "";
                typStr= (String) rb.getText();
                Log.d("DemoApp", " typStr   " + typStr);//authoriztion check
                typStr=typStr.substring(0,3);
                int selectedButtonId = group.getCheckedRadioButtonId();
                if (selectedButtonId == R.id.regcoml)  //or the saved Id when you created the view
                {
                    typStr="Reg";
                }
                else if (selectedButtonId == R.id.comsts) //or the saved Id when you created the view
                {
                    typStr="vie";
                }


                Log.d("DemoApp", " typStr   " + typStr);//authoriztion check
                if (null != rb && checkedId > -1) {
                    //   Toast.makeText(PaymentDashboardActivity.this, String.valueOf(typStr), Toast.LENGTH_SHORT).show();
                    if (typStr.equals("Reg")) {//register
                        strconsid1.setVisibility(View.VISIBLE);
                        strtbl1.setVisibility(View.VISIBLE);
                        strtblrw2.setVisibility(View.VISIBLE);
                        strtblrw3.setVisibility(View.VISIBLE);
                        strtblrw4.setVisibility(GONE); // need to change after login


                      //  strhint.setVisibility(View.VISIBLE);
                        strtblrw5.setVisibility(View.VISIBLE);
                      //  strtblrw6.setVisibility(View.VISIBLE);
                      //  btnhint.setVisibility(View.VISIBLE);
                        btnsubmit.setVisibility(View.VISIBLE);
                        if (conid!=null && (!(conid.equalsIgnoreCase("")))){
                            btnsubmit.setClickable(true);
                            TermCond.setVisibility(GONE);
                            btntrmscnd.setVisibility(GONE);
                            strconsIDval.setVisibility(GONE);
                            btnhint.setVisibility(GONE);
                            spinner2.setVisibility(View.VISIBLE);
                            strConName.setVisibility(View.VISIBLE);
                            strtblrw6.setVisibility(GONE);
                            // strConName.setEnabled(false);
                            addItemsOnSpinner2();
                        }
                        else {
                            if(conid.length()>5){
                                TermCond.setVisibility(View.INVISIBLE);
                                btntrmscnd.setVisibility(View.INVISIBLE);
                            }else{
                                TermCond.setVisibility(View.VISIBLE);
                                btntrmscnd.setVisibility(View.VISIBLE);
                            }
                            strconsIDval.setVisibility(View.VISIBLE);
                            TermCond.setVisibility(View.VISIBLE);
                            btntrmscnd.setVisibility(View.VISIBLE);
                            strconsIDval.setVisibility(View.VISIBLE);
                            btnhint.setVisibility(View.VISIBLE); // COMMENTED
                            spinner2.setVisibility(GONE);
                            strConName.setVisibility(GONE);
                            strtblrw6.setVisibility(View.VISIBLE);
                        }



                    } else {
                        strconsid1.setVisibility(View.VISIBLE);
                        strtbl1.setVisibility(GONE);
                        strtblrw2.setVisibility(GONE);
                        strtblrw3.setVisibility(GONE);
                        strtblrw4.setVisibility(GONE); // need to change afer login
                       // strhint.setVisibility(View.VISIBLE);
                        strtblrw5.setVisibility(GONE);

                      //  btnhint.setVisibility(View.VISIBLE);
                        btnsubmit.setVisibility(View.VISIBLE);

                        if (conid!=null && (!(conid.equalsIgnoreCase("")))){
                            btnsubmit.setClickable(true);
                            TermCond.setVisibility(GONE);
                            btntrmscnd.setVisibility(GONE);
                            strconsIDval.setVisibility(GONE);
                            btnhint.setVisibility(GONE);
                            spinner2.setVisibility(View.VISIBLE);
                            strConName.setVisibility(View.VISIBLE);
                            strtblrw6.setVisibility(GONE);
                            // strConName.setEnabled(false);
                            addItemsOnSpinner2();
                        }
                        else {
                            if(conid.length()>5){
                                TermCond.setVisibility(View.INVISIBLE);
                                btntrmscnd.setVisibility(View.INVISIBLE);
                            }else{
                                TermCond.setVisibility(View.VISIBLE);
                                btntrmscnd.setVisibility(View.VISIBLE);
                            }
                            strconsIDval.setVisibility(View.VISIBLE);
                            TermCond.setVisibility(View.VISIBLE);
                            btntrmscnd.setVisibility(View.VISIBLE);
                            strconsIDval.setVisibility(View.VISIBLE);
                            btnhint.setVisibility(View.VISIBLE); // COMMENTED
                            spinner2.setVisibility(GONE);
                            strConName.setVisibility(GONE);
                            strtblrw6.setVisibility(View.VISIBLE);
                        }

                    }
                } else {
                    Toast.makeText(ComplaintLoginActivity.this, "Select One ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getCategory() {

        if (CommonMethods.isNetworkAvailable(ComplaintLoginActivity.this)){
            AuthURL1 = StrUrl1+"CompanyID="+10+"&RequestType=8";
            new callCategory().execute(AuthURL1);
        }
        else {
            Toast.makeText(ComplaintLoginActivity.this,"No internet connection,please connect to internet",Toast.LENGTH_SHORT).show();
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
        if (list.size() > 0) {
            if (!CommonMethods.getSelectedPosition(context).isEmpty()) {
                spinner2.setSelection(Integer.parseInt(CommonMethods.getSelectedPosition(context)));
            }
        }

        /////////////
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CommonMethods.saveSelectedPosition(context, "" + position);
                Object item = parent.getItemAtPosition(position);
                // String value = String.valueOf(item.toString());
                String value = String.valueOf(position);
                // consIDval=String.valueOf(position);
                consIDval=String.valueOf(spinner2.getSelectedItem());
                databaseAccess = DatabaseAccess.getInstance(context);
                databaseAccess.open();
                String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD "+
                        "FROM CONSUMERDTLS WHERE CONSUMER_ID='" + consIDval + "'  ";
                Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_02);
                Cursor rs1 = DatabaseAccess.database.rawQuery(strSelectSQL_02, null);
                while (rs1.moveToNext()) {
                    spinpos1=rs1.getString(2);
                    conname=rs1.getString(1);
                }
                strAddr.setText(spinpos1);
                strConName.setText(conname);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //////////////
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
                    .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ComplaintLoginActivity.this.finish();
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
        CompanyID =sessiondata.getString("CompanyID", null); // getting String

        if(CompanyID==null){
            CompanyID="10";
        }

        divcode=consIDval.substring(0,3);
        dbtype=consIDval.substring(3,4);
        consacc=consIDval.substring(4);
        if(dbtype.equals("S")){
            dbtype="1";
        }else{
            dbtype="2";
        }
        if(typStr.equals("Reg")) {//register
            if (resCode == 1) {
                AuthURL = StrUrl + "un=TEST&pw=TEST&CompanyID=" + CompanyID + "&ReportID=1075&strDivCode=" + divcode + "&strCons_Acc=" + consacc;
                new UserAuthOnline().execute(AuthURL);
            } else if (resCode == 2) {
                AuthURL = StrUrl + "un=TEST&pw=TEST&CompanyID=" + CompanyID + "&ReportID=1075&strDivCode=" + divcode + "&strCons_Acc=" + consacc;
                new UserAuthOnline().execute(AuthURL);
            } else {
                AuthURL = "ServerOut";
                //  int code=AlertErrorCall.ErrorMsgType(6);
                //   if(code==1){MainActivity.this.finish();}
            }
        }else  if(typStr.equals("vie")) {//View
            if(resCode==1){
                AuthURL =StrUrl+"un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1076&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
                AuthURL =StrUrl+"un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1076&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else{
                AuthURL="ServerOut";
                //  int code=AlertErrorCall.ErrorMsgType(6);
                //   if(code==1){MainActivity.this.finish();}
            }
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
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ComplaintLoginActivity.this.finish();
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
            btnsubmit.setEnabled(true);
            btnsubmit.setClickable(true);
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                // progressDialog = ProgressDialog.show(ComplaintLoginActivity.this, "  ", " ");
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
                                ComplaintLoginActivity.this.finish();
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
            spinner.setVisibility(GONE);
            String pipeDelBillInfo = str;

            // pipeDelBillInfo = "1|1|1|BED/TEMPLE/OLD TOWN 1:GARAGE CHOK,SAMANTRAPUR,NATHPUR,NUAGAON, BIJAY VIHAR, GANGOTRI NAGAR. : 11kv Feeder Maintenance - Time 03-DEC-2019 07:00 to 10:00;BEDsd/TEMPLE/OLD TOWN 1:GARAGE CHOK,SAMANTRAPUR,NATHPUR,NUAGAON, BIJAY VIHAR, GANGOTRI NAGAR. : 11kv Feeder Maintenance - Time 03-DEC-2019 07:00 to 10:00 ";


            String[] BillInfo = pipeDelBillInfo.split("[|]");
            if (typStr.equals("Reg")) {//register
                Log.d("DemoApp", " BillInfo[0]   " + pipeDelBillInfo);//authoriztion check
                Log.d("DemoApp", " BillInfo[1]  " + BillInfo[1]);//authoriztion check
                if(BillInfo[2].equals("0")) {

                    Intent commint = new Intent(getApplicationContext(), ComplainRegisterActivity.class);
                    Bundle commdtls = new Bundle();
                    commdtls.putString("consIDval", consIDval);
                    commdtls.putString("emailval", emailval);
                    commdtls.putString("mobval", mobval);
                    commdtls.putString("regtype", typStr);
                    commdtls.putString("strAddrval", strAddrval);
                    commdtls.putString("conname", conname);
                   // commdtls.putString("comsubcatreg", strcomsubcat);
                    commdtls.putString("comsubcat",catDat);

                    commint.putExtras(commdtls);
                    startActivity(commint);
                    finish();
                }else if(BillInfo[2].equals("1")){
                    Log.d("DemoApp", " BillInfo[3]   " + BillInfo[3]);
                    Intent curblintent = new Intent(getApplicationContext(), OutageDetailActivity.class);
                    Bundle parmtrDet = new Bundle();
                    parmtrDet.putString("pipeDelBillInfo", BillInfo[3]);
                    parmtrDet.putString("Typeinfo", typStr);
                    parmtrDet.putString("consIDval", consIDval);
                    parmtrDet.putString("conname", conname);
                    parmtrDet.putString("emailval", emailval);
                    parmtrDet.putString("mobval", mobval);
                    parmtrDet.putString("strAddrval", strAddrval);
                    curblintent.putExtras(parmtrDet);
                    startActivity(curblintent);
                    finish();
                }
            }else  if(typStr.equals("vie")) {//view
                if(BillInfo[0].equals("0")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("User Not found");
                    alertDialogBuilder.setMessage("User Not found")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ComplaintLoginActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                } if(BillInfo[1].equals("0")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Report ID not found");
                    alertDialogBuilder.setMessage("Report ID not found")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ComplaintLoginActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }else {
                    if(BillInfo[2].equals("1")){//complaint sucess
                        Intent comstsintent = new Intent(getApplicationContext(), ComplaintStsDetailActivity.class);
                        Bundle comstsDet = new Bundle();
                        comstsDet.putString("compSts", BillInfo[3]);
                        comstsDet.putString("strtype", typStr);
                        comstsDet.putString("StrConID", consIDval);
                        comstsDet.putString("conname", conname);
                        comstsintent.putExtras(comstsDet);
                        startActivity(comstsintent);

                    }else {//complaint sucess
                        //added on 040520 by santi
                        strConName.setVisibility(View.VISIBLE);
                        strConName.setText("Complaint Details not found");
                        Toast.makeText(ComplaintLoginActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                        strConName.setTextSize(20);
                       /*
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Complaint No Not found");
                        alertDialogBuilder.setMessage("Please give correct Complaint number")
                                .setCancelable(false)
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ComplaintLoginActivity.this.finish();
                                    }
                                });
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
*/
                    }

                }
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
            //    Toast.makeText(getApplicationContext(), "User Menu1", Toast.LENGTH_SHORT).show();
            finish();
            exit(0);
            return true;
        } else if (id == R.id.action_user) { //back
            if(typStr.equals("reg")){
                Intent i = new Intent(this,ComplaintDashboardActivity.class);
                this.startActivity(i);
                finish();
            }else{

            }

            return true;
        }
        return super.onOptionsItemSelected(item);
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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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
                                ComplaintLoginActivity.this.finish();
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
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                // progressDialog = ProgressDialog.show(ComplaintLoginActivity.this, "  ", " ");
                spinner.setVisibility(View.VISIBLE);
            } else {
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
                                ComplaintLoginActivity.this.finish();
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
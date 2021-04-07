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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cesu.itcc05.consumeportal.adpater.OutageDeatilsAdapter;
import com.cesu.itcc05.consumeportal.modal.OutageModal;
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

import static java.lang.System.exit;

public class OutageInfoActivity extends AppCompatActivity {
    private static ImageView strconsID;
    private static ImageView strhint;
    private static TextView strmobmsg,strConName;
    private static EditText strconsIDval;
    private String consIDval="";
    private String emailval="";
    private String mobval="";
    private String imeinum = "";
    public  static final int RequestPermissionCode  = 1 ;
    final Context context = this;
    private int chkresponse=1;
    private String AuthURL=null;
    Button showPopupBtn, closePopupBtn,UpdateSwBtn;
    PopupWindow popupWindow;
    ConstraintLayout linearLayout1;
    private   DatabaseAccess databaseAccess=null;
    private CheckBox TermCond;
    private String Typeinfo="";
    private Spinner  spinner2;
    private String conname="";
    private String StrUrl="";
    private String CompanyID="";
    private String consacc="";
    private String divcode="";
    private String dbtype="";
    private TextView strOutagedet;
    private static CheckBox strtblterms;
   // private ProgressBar spinner;
    private LinearLayout ll_spinner;
    private ImageView iv_back;
    private ProgressDialog progressDialog;
    private ImageView btntrmscnd;
    private ArrayList<OutageModal>outageModals=new ArrayList<>();
    List<String> list = new ArrayList<String>();
    private RecyclerView rv_outage;
    OutageDeatilsAdapter outageDeatilsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outage_info);
        // setSupportActionBar(toolbar);
        //spinner=(ProgressBar)findViewById(R.id.progressBar);
        //spinner.setVisibility(View.GONE);
        ll_spinner=findViewById(R.id.ll_spinner);
        iv_back=findViewById(R.id.iv_backs);
        rv_outage=findViewById(R.id.rv_outage);
        initAdapter();


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_DynamicReport.jsp?";
        strhint = (ImageView) findViewById(R.id.hint);
        strconsIDval = (EditText) findViewById(R.id.consIDval);
        strOutagedet=(TextView) findViewById(R.id.Outagedet);
        //Button btnback = (Button) findViewById(R.id.back);
        final Button btnsubmit = (Button) findViewById(R.id.submit);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        strConName=(TextView)findViewById(R.id.conNm);
        strtblterms=(CheckBox) findViewById(R.id.tmcnd);
        btntrmscnd=findViewById(R.id.btntrmscnd);
        Bundle reqDet = getIntent().getExtras();
        Typeinfo = reqDet.getString("Typeinfo");
        /////////////////////////////////////////////////
////////checking user registration...////////////////
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO,EMAIL " +
                "FROM CONSUMERDTLS WHERE VALIDATE_FLG=1";
        Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
        String conid = "";
        String mobno = "";
        String email = "";
        while (cursor.moveToNext()) {
            conid = cursor.getString(0);
            mobno = cursor.getString(1);
            email = cursor.getString(2);
        }
        cursor.close();
        databaseAccess.close();
        strconsIDval.setText(conid);
        Log.d("DemoApp", "in Loop conid" + conid);
        //terms and condition agree
        TermCond = (CheckBox) findViewById(R.id.tmcnd);

        if (conid!=null && (!(conid.equalsIgnoreCase("")))){
            ll_spinner.setVisibility(View.VISIBLE);
            strconsIDval.setVisibility(View.GONE);
            //strhint.setVisibility(View.GONE);
            strConName.setVisibility(View.VISIBLE);
            addItemsOnSpinner2();
            btntrmscnd.setVisibility(View.GONE);

            strtblterms.setVisibility(View.GONE);
            strhint.setVisibility(View.GONE);
        }
        else {
            ll_spinner.setVisibility(View.GONE);
            strconsIDval.setVisibility(View.VISIBLE);
            strhint.setVisibility(View.VISIBLE);
            strConName.setVisibility(View.GONE);
            strtblterms.setVisibility(View.VISIBLE);
            btntrmscnd.setVisibility(View.VISIBLE);
        }


        TermCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (((CompoundButton) view).isChecked()) {
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
/*        if(!Typeinfo.equals("qlinks")){
            TermCond.setVisibility(View.GONE);
            btntrmscnd.setVisibility(View.GONE);
            strconsIDval.setVisibility(View.GONE);
            btnhint.setVisibility(View.GONE);
          //  ll_spinner.setVisibility(View.VISIBLE);
            strConName.setVisibility(View.VISIBLE);
            strtblterms.setVisibility(View.VISIBLE);
          //  strConName.setEnabled(false);
            //addItemsOnSpinner2();

        }else{
            TermCond.setVisibility(View.VISIBLE);
            btntrmscnd.setVisibility(View.VISIBLE);
          //  strconsIDval.setVisibility(View.VISIBLE);
            btnhint.setVisibility(View.VISIBLE);
           // ll_spinner.setVisibility(View.GONE);
            strConName.setVisibility(View.GONE);
            strtblterms.setVisibility(View.VISIBLE);
           // strtblterms.setVisibility(View.INVISIBLE);
        }*/
        /////////////////////////////////////

        linearLayout1 = (ConstraintLayout) findViewById(R.id.layout);//for pop up
        btntrmscnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) OutageInfoActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        strhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) OutageInfoActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_conumer_id, null);
              ImageView  closePopupBtns = (ImageView) customView.findViewById(R.id.closePopupBtn);
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
                if(Typeinfo.equals("qlinks")) {
                    Intent UserDashboard = new Intent(getApplicationContext(), QuicklinksDashboard.class);
                    startActivity(UserDashboard);
                    finish();
                }else{
                    Intent UserDashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(UserDashboard);
                    finish();
                }

            }
        });*/



        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CommonMethods.hideKeyboard(OutageInfoActivity.this);
                strconsIDval.setAllCaps(true);
             /*   if(Typeinfo.equals("qlinks")){
                    consIDval=  String.valueOf(spinner2.getSelectedItem()).trim();
                }else{
                    consIDval = strconsIDval.getText().toString().trim().toUpperCase();
                }*/
                if (list.size()>1){
                    consIDval=  String.valueOf(spinner2.getSelectedItem()).trim();
                }
                else {
                    consIDval = strconsIDval.getText().toString().trim().toUpperCase();
                }


                Log.d("DemoApp", "consIDval" + consIDval);
                if (TextUtils.isEmpty(consIDval)) {
                    strconsIDval.setError("Please enter consumer/CA number");
                    strconsIDval.requestFocus();

                    Log.d("DemoApp", "Query SQL " + consIDval);
                } else if (consIDval.length() > 0 && consIDval.length() < 12) {
                    strconsIDval.setError("Enter Valid Consumer ID of 12 digit");
                    strconsIDval.requestFocus();
                }
                else if (!(strtblterms.isChecked())){
                    Toast.makeText(OutageInfoActivity.this,"Please accept terms & conditions",Toast.LENGTH_SHORT).show();
                }
                else {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        changeTextStatus(true);
                    } else {
                        changeTextStatus(false);
                    }
                }
            }
        });
    }
    public void  addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        list.clear();
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
                   // spinpos1=rs1.getString(2);
                    conname=rs1.getString(1);
                }
              //  strAddr.setText(spinpos1);
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
                    .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            OutageInfoActivity.this.finish();
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
    private void funcUrlCheck(int resCode){
        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
        CompanyID =sessiondata.getString("CompanyID", null); // getting String

        if (CompanyID==null){
            CompanyID="10";
        }
        divcode=consIDval.substring(0,3);
        dbtype=consIDval.substring(3,4);
        consacc=consIDval;
        if(dbtype.equals("S")){
            dbtype="1";
        }else{
            dbtype="2";
        }
            if(resCode==1){
                //AuthURL =StrUrl+"un=TEST&pw=TEST&CompanyID="+10+"&ReportID=1075&strDivCode="+divcode+"&strCons_Acc="+consacc;

                AuthURL = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Get_Current_Outage?checksum=01091981&ConsumerAccount="+consacc;

                new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
              //  AuthURL =StrUrl+"un=TEST&pw=TEST&CompanyID="+10+"&ReportID=1075&strDivCode="+divcode+"&strCons_Acc="+consacc;
                AuthURL = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Get_Current_Outage?checksum=01091981&ConsumerAccount="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else{
                AuthURL="ServerOut";
                //  int code=AlertErrorCall.ErrorMsgType(6);
                //   if(code==1){MainActivity.this.finish();}
            }
            Log.d("DemoApp", "in Loop AuthURL" + AuthURL);

    }
    private void initAdapter() {
        if (GlobalVariable.outageModals.size() > 0) {

        }
        //paymentCentreDataAdapter.notifyDataSetChanged();
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
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                Element element = doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("Table");
                outageModals.clear();
                GlobalVariable.outageModals.clear();
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node node = nList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        OutageModal paymentCenterModal = new OutageModal();

                        try {
                            paymentCenterModal.setDIVISION_NAME(getValue("DIVISION_NAME", element2));

                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                        try {
                            paymentCenterModal.setFEEDER_NAME(getValue("FEEDER_NAME", element2));
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }

                        try {
                            paymentCenterModal.setSTART_DATE(getValue("START_DATE", element2));
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }

                        try {
                            paymentCenterModal.setSTART_TIME(getValue("START_TIME", element2));
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                        try {
                            paymentCenterModal.setOutage_subtype(getValue("outage_subtype", element2));
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }

                        try {
                            paymentCenterModal.setPROBABLE_RESTORE_TIME(getValue("PROBABLE_RESTORE_TIME", element2));
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }


                        try {
                            paymentCenterModal.setLIKELY_AREA_AFFECTED(getValue("LIKELY_AREA_AFFECTED", element2));

                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }

                        outageModals.add(paymentCenterModal);
                        GlobalVariable.outageModals.addAll(outageModals);
                    }
                }
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

                                OutageInfoActivity.this.finish();
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

            try {
                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                    //spinner.setVisibility(View.VISIBLE);
                    progressDialog = ProgressDialog.show(OutageInfoActivity.this, "Please wait...", "Fetching data");

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
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
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
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
        @Override
        protected void onPostExecute(String str) {
            Log.d("DemoApp", " str   " + str);

            try {
                progressDialog.dismiss();
                // spinner.setVisibility(View.GONE);
                String pipeDelBillInfo = str;
                String responseMessage="";



                if (GlobalVariable.outageModals.size()>0){
                    Intent curblintent = new Intent(getApplicationContext(), OutageDetailActivity.class);
        /*            Bundle parmtrDet = new Bundle();
                   // parmtrDet.putString("pipeDelBillInfo", BillInfo[3]);

                    parmtrDet.putString("Typeinfo", Typeinfo);
                    parmtrDet.putString("consIDval", consIDval);
                    parmtrDet.putString("conname", conname);
                    curblintent.putExtras(parmtrDet);


                    startActivity(curblintent);*/
                    rv_outage.setVisibility(View.VISIBLE);
                    strOutagedet.setVisibility(View.GONE);

                    rv_outage.setLayoutManager(new LinearLayoutManager(OutageInfoActivity.this));
                    outageDeatilsAdapter = new OutageDeatilsAdapter(OutageInfoActivity.this, GlobalVariable.outageModals);
                    rv_outage.setAdapter(outageDeatilsAdapter);


                    outageDeatilsAdapter.notifyDataSetChanged();



                }
                else {
                    strOutagedet.setVisibility(View.VISIBLE);
                    strOutagedet.setText(R.string.no_outage_found);
                    rv_outage.setVisibility(View.GONE);

                }

                //  pipeDelBillInfo = "1|1|1|BED/TEMPLE/OLD TOWN 1:GARAGE CHOK,SAMANTRAPUR,NATHPUR,NUAGAON, BIJAY VIHAR, GANGOTRI NAGAR. : 11kv Feeder Maintenance - Time 03-DEC-2019 07:00 to 10:00;BEDsd/TEMPLE/OLD TOWN 1:GARAGE CHOK,SAMANTRAPUR,NATHPUR,NUAGAON, BIJAY VIHAR, GANGOTRI NAGAR. : 11kv Feeder Maintenance - Time 03-DEC-2019 07:00 to 10:00 ";


               /* String[] BillInfo = pipeDelBillInfo.split("[|]");
                Log.d("DemoApp", " BillInfo[0]   " + pipeDelBillInfo);//authoriztion check
                Log.d("DemoApp", " BillInfo[1]  " + BillInfo[1]);//authoriztion check
                if(BillInfo[0].equals("0")){
                    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("User Not Found");
                    alertDialogBuilder.setMessage("User Not Found")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent UserDashboard = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(UserDashboard);
                                    finish();

                                }
                            });
                    // create alert dialog
                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }else if(BillInfo[1].equals("0")) {
                    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Report No Not Found");
                    alertDialogBuilder.setMessage("Report No Not Found")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent UserDashboard = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(UserDashboard);
                                    finish();

                                }
                            });
                    // create alert dialog
                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }else if(BillInfo[2].equals("0")) {

                    responseMessage=BillInfo[3];

                *//* disable on 040520 by Santi
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Record Not Found");
                alertDialogBuilder.setMessage("No Outage Details Found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(Typeinfo.equals("qlinks")) {
                                    Intent UserDashboard = new Intent(getApplicationContext(), QuicklinksDashboard.class);
                                    startActivity(UserDashboard);
                                    finish();
                                }else{
                                    Intent UserDashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                                    startActivity(UserDashboard);
                                    finish();
                                }
                            }
                        });
                // create alert dialog
                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
*//*

                }else if(BillInfo[2].equals("1")){
                    Log.d("DemoApp", " BillInfo[3]   " + BillInfo[3]);
                    Intent curblintent = new Intent(getApplicationContext(), OutageDetailActivity.class);
                    Bundle parmtrDet = new Bundle();
                    parmtrDet.putString("pipeDelBillInfo", BillInfo[3]);
                    parmtrDet.putString("Typeinfo", Typeinfo);
                    parmtrDet.putString("consIDval", consIDval);
                    parmtrDet.putString("conname", conname);
                    curblintent.putExtras(parmtrDet);
                    startActivity(curblintent);
                    finish();
                }*/
            }
            catch (Exception ex){
                ex.printStackTrace();
            }


        }
    }
    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
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
              /*  Intent i = new Intent(this,QuicklinksDashboard.class);
                this.startActivity(i);
                finish();*/
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


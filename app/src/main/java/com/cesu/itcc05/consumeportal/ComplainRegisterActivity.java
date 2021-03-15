package com.cesu.itcc05.consumeportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.System.exit;

public class ComplainRegisterActivity extends Activity {
    final Context context = this;
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    private int chkresponse=1;
    private String AuthURL=null;
    private String consIDval="";
    private String emailval="";
    private String mobval="";
    private static EditText strcompval;
    private  static final int RequestPermissionCode  = 1 ;
    private String imeinum = "";
    private DatabaseAccess databaseAccess=null;
    private String strregtype="";
    private String strAddrval="";
    private String spinpos="";
    private String spinpos1="";
    private String StrUrl="";
    private String CompanyID="";
    private String conname="";
    private String compdet ="";
    private String regtime="";
    private String spin1posID="";
    private String spin2posID="";
    private String consacc="";
    private String divcode="";
    private String dbtype="";
    private ProgressBar spinner;
    private String strcomsubcat="";
    private   String[] ContentWrap=null;
    private   String[] compcode=null;
    private   String[] compdescmet=null;
    private   String[] compdescbil=null;
    private   String[] compdescoth=null;
    private   String[] compcodemet=null;
    private   String[] compcodebil=null;
    private   String[] compcodeoth=null;
    HashMap<String,String>category=new HashMap<>();
    HashMap<String,String >subCategory=new HashMap<>();
    private List<String>catList=new ArrayList<>();
    private List<String>catListValue=new ArrayList<>();
    private List<String>subCatList=new ArrayList<>();
    private HashMap<String,String>subCatId=new HashMap<>();
    private ImageView iv_back;
    private List<ComplainModal>complainModals=new ArrayList<>();
    private List<ComplainModalSubCategory>complainModalSubCategories=new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_register);
        // setSupportActionBar(toolbar);
        // getSupportActionBar().setTitle("Complaint Login ");
        // setSupportActionBar(toolbar);
        // getSupportActionBar().setTitle("Complaint Dashboard Activity");
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        iv_back=findViewById(R.id.iv_backs);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_ComplaintRegister.jsp?";
        Bundle commdtls = getIntent().getExtras();
        consIDval = commdtls.getString("consIDval").trim();
        emailval = commdtls.getString("emailval").trim();
        mobval = commdtls.getString("mobval").trim();
        strAddrval = commdtls.getString("strAddrval").trim();
        strregtype = commdtls.getString("regtype").trim();
        conname= commdtls.getString("conname");
        //Added on 040520 by Santi
        strcomsubcat= commdtls.getString("comsubcat");


       /* strcomsubcat="1|101,Meter Defect,0;102,Meter Testing,0;103,Meter Burnt,0;104,Meter Reading,0;105,Other Meter Related,0;106,Billing ,0;107,Billing Information Change,0;108,\n" +
                "Reconnection/Disconnection Request,0;109,NSC,0;110,Enforcement,0;111,General Enquiry,0;112,Supply Related,0;113,Safety Aspects,0;\n" +
                "10101,Meter Stuck,101;10102,Meter No Display,101;10103,Meter Glass Broken,101;10104,Meter Change,101;10105,Meter Seal Open,101;10106,Other Meter Defects,101;10201,Meter - Fast,102;10202,Meter - Slow ,102;10301,Terminial Burnt,103;10302,Meter Burnt,103;10401,No Reading,104;10402,Wrong Reading,104;10501,Meter Shift,105;10502,Service Wire Related,105;10503,Phase Conversion,105;10504,Other Complaints,105;10601,Bill Revision,106;10602,Bill Adjustment details,106;10603,Bill not Served,106;10604,Payment not reflected in bill,106;10605,Duplicate Bill Request,106;10606,Meter -Change,106;10607,Meter Seal Open,106;10608,Service Wire Related,106;10609,Phase Conversion,106;10610,Shifting of Meter,106;10611,Meter Reading Not Taken,106;10612,Door Locked Cases,106;10613,Meter Reading-Correction Request,106;10614,Arrear Dispute,106;10615,Contracted Load Not Printing,106;10616,Wrong/ Non-Posting in Bill,106;10617,Other Billing Related,106;10701,Name of Change/Ownership Change,107;10702,Correction of Name,107;10703,Correction of Address,107;10704,Tariff Change,107;10801,Consumer's Disconnection Request ,108;10802,Disconnection Request other than Consumer,108;10803,Re-connection of power Supply,108;10901,New Connection,109;10902,Enhancement of CD,109;10903,Reduction of CD,109;11001,Reporting of Power Theft,110;11101,General Enquiry(Commercial),111;11201,Power Interruption,112;11202,Low Voltage ,112;11203,New Infrastructure(Additional Line/ Substation/transformer Upgradation etc.),112;11204,Transformer Brunt,112;11205,Area No Supply,112;11206,Power Supply Fluctuating,112;11301,No Fencing,113;11302,Pole Shifting,113;11303,Pole Damage,113;11304,Pole Tilting,113;11305,Conductor Sagging,113;11306,Sub-Station Shifting,113;11307,Tree Trimming,113;11308,Other Safety Aspects,113;";
*/
        Log.d("DemoApp", " strcomsubcat reg val  " + strcomsubcat);
        String[] comsubcatcode = strcomsubcat.split(";");

        int arrayparam=80;
        ContentWrap = new String[arrayparam];
        compcode= new String[70];
        compdescmet= new String[70];
        compdescbil= new String[70];
        compdescoth= new String[70];
        compcodemet=new String[70];
        compcodebil=new String[70];
        compcodeoth=new String[70];
        int j = 0;
        String comData = "";
        String[] DetData =null;
        int compcodecnt=0;
        int cnt1=0;
        int cnt2=0;
        int cnt3=0;
        Log.d("DemoApp", " comsubcatcode.length  " + comsubcatcode.length);
        catListValue.add("Select Complaint");
        for (int i = 0; i < comsubcatcode.length; i++) {
            try {
                comData = comsubcatcode[i];
                //Log.d("DemoApp", "comData   " + comData);
                //comprmlen=comsubcatcode.length;
                DetData = comData.split("[,]");
                Log.d("DemoApp", " comData   " + comData);
                Log.d("DemoApp", " comData   " + DetData[0]);

                if (DetData[2].equalsIgnoreCase("0")){

                    ComplainModal complainModal=new ComplainModal();
                    complainModal.setId(DetData[0]);
                    complainModal.setComapinReaon(DetData[1]);
                    complainModal.setCategoryId(DetData[2]);
                    complainModals.add(complainModal);

                   /* if (i==0){
                        if (DetData[0].contains("|")){
                            category.put(DetData[0].split("\\|")[1],DetData[1]);
                        }
                        else {
                            category.put(DetData[0],DetData[1]);
                        }
                        catList.add(DetData[0].split("\\|")[1]);
                        catListValue.add(DetData[1]);
                    }
                    else {
                        category.put(DetData[0],DetData[1]);
                        catList.add(DetData[0]);
                        catListValue.add(DetData[1]);
                    }*/
                }
                else {

                    ComplainModalSubCategory complainModalSubCategory=new ComplainModalSubCategory();
                    complainModalSubCategory.setId(DetData[0]);
                    complainModalSubCategory.setComapinReaon(DetData[1]);
                    complainModalSubCategory.setCategoryId(DetData[2]);

                    complainModalSubCategories.add(complainModalSubCategory);
                    /*subCatId.put(DetData[0],DetData[1]);
                    subCategory.put(DetData[1],DetData[2]);
                    subCatList.add(DetData[2]);*/
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            j++;
        }



        ////End///
        conname = conname.replaceAll(" ", "^");
        strAddrval = strAddrval.replaceAll(" ", "^");
        conname=conname.replaceAll("\n", "");//added on 16.12.2017
        conname=conname.replaceAll("\t", "");//added on 16.12.2017
        strAddrval=strAddrval.replaceAll("\n", "");//added on 16.12.2017
        strAddrval=strAddrval.replaceAll("\t", "");//added on 16.12.2017
        divcode=consIDval.substring(0,3);
        dbtype=consIDval.substring(3,4);
        consacc=consIDval.substring(4);
        if(dbtype.equals("S")){
            dbtype="1";
        }else{
            dbtype="2";
        }
        Button btnback = (Button) findViewById(R.id.back);
        strcompval=(EditText) findViewById(R.id.comdet);
        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
        CompanyID =sessiondata.getString("CompanyID", null); // getting String
        imeinum =sessiondata.getString("imeinum", null); // getting String
        imeinum ="102030405060708";
        if(imeinum==null || imeinum.equals("")){
            imeinum = "999999999999";
        }//
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        regtime=currentDate+"^"+ currentTime;
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
                /*        if(strregtype.equals("reg")){
                    Intent UserDashboard = new Intent(getApplicationContext(), ComplaintDashboardActivity.class);
                    startActivity(UserDashboard);
                    finish();
                }else{
                    Intent UserDashboard = new Intent(getApplicationContext(), QuicklinksDashboard.class);
                    startActivity(UserDashboard);
                    finish();
                }*/

            }
        });

    }

    // add items into spinner dynamically
    public void  addItemsOnSpinner2(String SelComplain,String categoryId) {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Select Subcategory");


        for (int i=0;i<complainModalSubCategories.size();i++){
            if (complainModalSubCategories.get(i).getCategoryId().equalsIgnoreCase(categoryId)){
                list.add(complainModalSubCategories.get(i).getComapinReaon());
            }
        }

      /*  for (Map.Entry<String, String> entry : subCategory.entrySet()) {
            if (entry.getValue().equals(categoryId)) {
                System.out.println(entry.getKey());
                list.add(entry.getKey());
            }
        }*/

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
                spinpos1= String.valueOf(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //////////////
    }
    public void addListenerOnSpinnerItemSelection() {
        catList.clear();
        spinner1 = (Spinner) findViewById(R.id.spinner1);

        catList.add("Select Category");
        for (int i=0;i<complainModals.size();i++){
            catList.add(complainModals.get(i).getComapinReaon());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ComplainRegisterActivity.this,
                android.R.layout.simple_spinner_item, catList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        /////////////
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                // String value = String.valueOf(item.toString());
                String value = String.valueOf(position);
              //  spinpos1= String.valueOf(position);
                spinpos= String.valueOf(position);
                 String categoryId="";

                System.out.println("sdfg=="+position+"=="+spinpos);

                if (position>=1){

                    for (int j=0;j<complainModals.size();j++){
                        if (item.toString().equalsIgnoreCase(complainModals.get(j).getComapinReaon())){
                            categoryId=complainModals.get(j).getId();
                        }
                    }


                }

                addItemsOnSpinner2(value,categoryId);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    // get the selected dropdown list value
    public void addListenerOnButton() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String spinval1 = String.valueOf(spinner1.getSelectedItem());
                String spinval2 = String.valueOf(spinner2.getSelectedItem());

                for (int i=0;i<complainModalSubCategories.size();i++){
                    if (spinval2.equalsIgnoreCase(complainModalSubCategories.get(i).getComapinReaon())){
                        spin2posID=complainModalSubCategories.get(i).getId();
                    }
                }

               /* for (Map.Entry<String, String> entry : subCatId.entrySet()) {
                    if (entry.getValue().equals(spinval2)) {
                        System.out.println(entry.getKey());
                        spin2posID=String.valueOf(entry.getKey());
                    }
                }
*/
                compdet = strcompval.getText().toString().trim();
                compdet = compdet.replaceAll(" ", "^");
                compdet = compdet.replaceAll("\n", "");//added on 16.12.2017
                compdet = compdet.replaceAll("\t", "");//added on 16.12.2017
                if (spinval1.equals("Select Complaint")) {//change on 040520 by santi
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("No Complaint Category selected");
                    alertDialogBuilder.setMessage("Please Select One")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ComplainRegisterActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                } else if (spinval2.equals("Select One")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("No Complaint Sub Category selected");
                    alertDialogBuilder.setMessage("Please Select One")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ComplainRegisterActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                } else if (compdet.length() == 0) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Please write Complaint Details");
                    alertDialogBuilder.setMessage("Please Write something")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ComplainRegisterActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                } else {

                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        changeTextStatus(true);
                    } else {
                        changeTextStatus(false);
                    }
                }
                //  Toast.makeText(ComplainRegisterActivity.this,
                // "OnClickListener : " +
                //         "\nSpinner 1 : " + String.valueOf(spinner1.getSelectedItem()) +
                //         "\nSpinner 2 : " + String.valueOf(spinner2.getSelectedItem()),
                //  Toast.LENGTH_SHORT).show();

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
                            ComplainRegisterActivity.this.finish();
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

        System.out.println("spin2posID"+spin2posID);

        AuthURL = StrUrl+"un=TEST&pw=TEST&CompanyID=10&imei="+imeinum+"&mosarkar=0&Complaint_Flg=1&MO_SARKAR_ID=0&CONSUMER_NO="+consacc+"&NAME="+conname+"&ADDRESS="+strAddrval+"&LINE=&CITY=&STATE=&COUNTRY=&PIN=0&PHONE=&MOBILE="+mobval+"&EMAIL="+emailval+"&DETAILS="+compdet+"&COMPLAINT_DATE="+regtime+"&OFF_CODE="+divcode+"&SUBDIV=&SECTION=&COMP_CODE="+spin2posID+"&DB_TYPE="+dbtype+"&MOD_BY=ePortal_APP&MODULE_ID=ePortal_APP";

        System.out.println("qwerty=="+AuthURL);
         new UserAuthOnline().execute(AuthURL);
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
                                ComplainRegisterActivity.this.finish();
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
            btnSubmit.setClickable(false);
            btnSubmit.setEnabled(false);
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                //  progressDialog = ProgressDialog.show(ComplainRegisterActivity.this, "Fetchig Data", "Please Wait:: connecting to server");
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
                                ComplainRegisterActivity.this.finish();
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
            spinner.setVisibility(View.VISIBLE);
            String pipeDelBillInfo =str;

            // pipeDelBillInfo="3|1|1|2568954|sddsadad"; //Complain register
            //  1|1|1|22222|737367|Complaint(s) Registerd
            String[] BillInfo = pipeDelBillInfo.split("[|]");
            Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check


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
                                ComplainRegisterActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }else if(BillInfo[1].equals("0")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Complaint not registered");//changed by santi 17/04/20  by santi
                alertDialogBuilder.setMessage("Complaint not registered")//changed by santi 17/04/20  by santi
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ComplainRegisterActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }else if(BillInfo[2].equals("0")) {
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
                                ComplainRegisterActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }else{//complaint success

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Your Complaint is Successfully Registered");
                alertDialogBuilder.setMessage("Your Complaint No is:"+BillInfo[4])
                        .setCancelable(false)
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(strregtype.equals("reg")){
                                  onBackPressed();
                                    finish();
                                }else{
                                    onBackPressed();
                                    finish();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ComplainRegisterActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
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
            if(strregtype.equals("reg")){
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
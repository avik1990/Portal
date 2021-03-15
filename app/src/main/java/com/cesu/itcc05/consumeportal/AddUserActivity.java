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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cesu.itcc05.consumeportal.adpater.AddUserAdapter;
import com.cesu.itcc05.consumeportal.adpater.ComplainStsDeatailsAdapter;
import com.cesu.itcc05.consumeportal.modal.AddUserModal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class AddUserActivity extends AppCompatActivity {
    private   DatabaseAccess databaseAccess=null;
    final Context context = this;
    private  EditText strconsIDval;
    private int chkresponse=1;
    private String AuthURL=null;
    private String consIDval="";
    private String StrUrl="";
    private String CompanyID="";
    private String mobval="";
    private String ConsID="";
    private ProgressBar spinner;
    private TextView tv_add,tv_name,consumer_no;
    private RecyclerView rv_user;
    private List<AddUserModal>addUserModals=new ArrayList<>();
    private AddUserAdapter addUserAdapter;
    private ImageView iv_backs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        iv_backs=findViewById(R.id.iv_backs);
        // setSupportActionBar(toolbar);
      //  CustomLineDrawableView testView=new CustomLineDrawableView(context);
     //   setContentView(testView);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/ePortalAPP/ePortal_App.jsp?";
        //CompanyID="10";
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        rv_user=findViewById(R.id.rv_user);
        initRecycler();

        consumer_no=findViewById(R.id.consumer_no);
        tv_name=findViewById(R.id.tv_name);
        tv_add=findViewById(R.id.tv_add);
        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        spinner.setVisibility(View.GONE);
        Button btnadduser = (Button) findViewById(R.id.adduser);
        strconsIDval=(EditText) findViewById(R.id.consIDval);
       // Button btnback = (Button) findViewById(R.id.back);

        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
        ConsID =sessiondata.getString("ConsID", null); // getting String
        mobval =sessiondata.getString("mobval", null); // getting String
        CompanyID =sessiondata.getString("CompanyID", null); // getting String
       /*
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT COUNT(1) "+
                "FROM CONSUMERDTLS WHERE (VALIDATE_FLG=1 OR VALIDATE_FLG=9) ";
        Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
      //  int totcnt = 0;
        while (cursor.moveToNext()) {
            totcnt=cursor.getInt(0);
        }
        Log.d("DemoApp", "in Loop totcnt" + totcnt);
        cursor.close();
        databaseAccess.close();
        if(totcnt>=4){
            btnadduser.setEnabled(false);
            btnadduser.setClickable(false);
            strconsIDval.setFocusable(false);
            strconsIDval.setEnabled(false);
        }else{
            btnadduser.setEnabled(true);
            btnadduser.setClickable(true);
            strconsIDval.setFocusable(true);
            strconsIDval.setEnabled(true);
        }

        if(totcnt>4){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Adding of Maximum User reached ");
            alertDialogBuilder.setMessage("Maximum user Allowed is 4")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent UserDashboard = new Intent(getApplicationContext(), UserDashboardActivity.class);
                            startActivity(UserDashboard);
                            finish();
                        }
                    })
                    .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AddUserActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }else{
        */
            databaseAccess = DatabaseAccess.getInstance(context);
            databaseAccess.open();
            String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD "+
                    "FROM CONSUMERDTLS WHERE (VALIDATE_FLG=1 OR VALIDATE_FLG=9)  ";
            Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_02);

            Cursor rs1 = DatabaseAccess.database.rawQuery(strSelectSQL_02, null);
            LinearLayout layout = (LinearLayout) findViewById(R.id.rootContainer);
            RadioGroup ll = new RadioGroup(this);

            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layout.addView(ll, p);
            String consid="";
            String consname="";
            String addr="";
            ///path for dotted line////
                     /////////////
            while (rs1.moveToNext()) {
                consid = rs1.getString(0);
                consname = rs1.getString(1);
                addr= rs1.getString(2);
                RadioButton rdbtn = new RadioButton(this);

                tv_add.setText(addr);
                tv_name.setText(consname);
                consumer_no.setText(consid);

                AddUserModal addUserModal=new AddUserModal();
                addUserModal.setConsumerId(consid);
                addUserModal.setAddress(addr);
                addUserModal.setName(consname);
                addUserModals.add(addUserModal);



             /*   rdbtn.setText(getResources().getString(R.string.con_id) + consid + "\n"+ getResources().getString(R.string.name) + consname+ "\n"+getResources().getString(R.string.address) + addr+":"+"\n"+"***************");
                rdbtn.setTextSize(15);
                rdbtn.setClickable(false);
                rdbtn.setFocusable(false);
                rdbtn.setButtonDrawable(android.R.color.transparent);
               // rdbtn.setVisibility(View.GONE);
                //  rdbtn.setOnClickListener(mThisButtonListener);
                ll.addView(rdbtn, p);*/
                //////////////line addded////

                /////////
           // }
            databaseAccess.close();
        }
        addUserAdapter.notifyDataSetChanged();
        btnadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                CommonMethods.hideKeyboard(AddUserActivity.this);
                strconsIDval.setAllCaps(true);

                if(!(strconsIDval.getText().toString().length()==0)){
                    consIDval = strconsIDval.getText().toString().trim().toUpperCase();
                    //Added on 040520 by santi
                    try {
                        String strdbtype=consIDval.substring(3,4);
                        if(strdbtype.equals("1") || strdbtype.equals("l") || strdbtype.equals("i")){
                            consIDval=consIDval.substring(0,3)+"I"+consIDval.substring(4);
                        }else{
                            consIDval=consIDval.substring(0,3)+"S"+consIDval.substring(4);
                        }
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }

                    Log.d("DemoApp", "consIDval" + consIDval);
                    //
                    //  Log.d("DemoApp", "in Loop consIDval" + consIDval);
                    //  Log.d("DemoApp", "in Loop ConsID" + ConsID);
                    databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    String strSelectSQL_01 = "SELECT CONSUMER_ID "+
                            "FROM CONSUMERDTLS WHERE VALIDATE_FLG=9 AND CONSUMER_ID='" + consIDval + "' ";
                    Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
                    Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
                    int recFnd=0;
                    while (cursor.moveToNext()) {
                        recFnd=1;
                    }
                    cursor.close();
                    databaseAccess.close();
                    if(recFnd==1){
                        strconsIDval.setError("Consumer Already Added");
                    }else if(consIDval.equals(ConsID)){
                        strconsIDval.setError("Enter different consumer No.");
                    }else {
                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            changeTextStatus(true);
                        } else {
                            changeTextStatus(false);
                        }
                    }
                }
                else {
                    strconsIDval.setError("Please enter consumer/CA number");
                    strconsIDval.requestFocus();
                }



               // Intent UserDashboard = new Intent(getApplicationContext(), AddUserActivity.class);
               // startActivity(UserDashboard);
            }
        });

    }

    private void initRecycler() {
        rv_user.setLayoutManager(new LinearLayoutManager(AddUserActivity.this));

        addUserAdapter = new AddUserAdapter(AddUserActivity.this, addUserModals);
        rv_user.setAdapter(addUserAdapter);

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
                    .setNegativeButton(" Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AddUserActivity.this.finish();
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
                AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=3&ConsumerID="+consIDval+"&Mobile_No="+mobval;
                new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
                AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=3&ConsumerID="+consIDval+"&Mobile_No="+mobval;
                new UserAuthOnline().execute(AuthURL);
            }else{
                AuthURL="ServerOut";
                //  int code=AlertErrorCall.ErrorMsgType(6);
                //   if(code==1){MainActivity.this.finish();}
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
                                AddUserActivity.this.finish();
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
                //progressDialog = ProgressDialog.show(AddUserActivity.this, " ", " ");
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
                                AddUserActivity.this.finish();
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
            String[] BillInfo = pipeDelBillInfo.split("[|]");
            Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check

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
                                AddUserActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }else {
                if(BillInfo[1].equals("9")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Consumer ID not found");
                    alertDialogBuilder.setMessage("Enter Correct Number")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    AddUserActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }else{
                    databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    String strSelectSQL_01 = "INSERT INTO CONSUMERDTLS  " +
                            " (CONSUMER_ID,CONSUMER_NAME,VALIDATE_FLG,CONSUMER_ADD)" +
                            " VALUES('" + consIDval + "','" + BillInfo[2] + "',9,'" + BillInfo[3] + "')" ;

                    Log.d("DemoApp", "strSelectSQL_01" + strSelectSQL_01);
                    DatabaseAccess.database.execSQL(strSelectSQL_01);
                    databaseAccess.close();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Added Successfully");
                    alertDialogBuilder.setMessage("Consumer Added Successfully")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent usrdashboard = new Intent(getApplicationContext(), AddUserActivity.class);
                                    startActivity(usrdashboard);
                                    finish();
                                }
                            })
                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
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
            Intent i = new Intent(this,UserDashboardActivity.class);
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
}

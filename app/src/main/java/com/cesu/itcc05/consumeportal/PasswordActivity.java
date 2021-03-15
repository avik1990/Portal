package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.System.exit;

public class PasswordActivity extends AppCompatActivity {
    private   DatabaseAccess databaseAccess=null;
    final Context context = this;
    private static EditText actkey,pwd,repwd;
    private String strAkey="";
    private String strpwd="";
    private String strrepwd="";
    private String ActKey = "";
    private String MobileNo = "";
    private String ConsID = "";
    private String ConsName = "";
    private String ConsAdd = "";
    private String imeinum = "";
    private String matchflg = "";
    private String email="";
    private String versionID="";
    private String CompanyID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setSupportActionBar(toolbar);
        setContentView(R.layout.activity_password);
        actkey=(EditText) findViewById(R.id.AKey);
        pwd=(EditText) findViewById(R.id.pwd);
        repwd=(EditText) findViewById(R.id.rpwd);
        Button btnsavepwd = (Button) findViewById(R.id.savepwd);
        Button btnexit = (Button) findViewById(R.id.exit);

        Bundle Pwdmgmtdtls = getIntent().getExtras();
        ActKey = Pwdmgmtdtls.getString("ActKey");
        MobileNo = Pwdmgmtdtls.getString("MobileNo");
        ConsID = Pwdmgmtdtls.getString("ConsID");
        ConsName = Pwdmgmtdtls.getString("ConsName");
        email = Pwdmgmtdtls.getString("email");
        ConsAdd = Pwdmgmtdtls.getString("ConsAdd");
        imeinum = Pwdmgmtdtls.getString("imeinum");
        versionID = Pwdmgmtdtls.getString("versionID");
        CompanyID = Pwdmgmtdtls.getString("CompanyID");
        matchflg = Pwdmgmtdtls.getString("matchflg");
        String swversion = Pwdmgmtdtls.getString("swversion");
        String Strtyp = Pwdmgmtdtls.getString("Strtyp");
        String strpwd1 = Pwdmgmtdtls.getString("strpwd1");
        String strrepwd1 = Pwdmgmtdtls.getString("strrepwd1");
        actkey.setText(ActKey);

        SharedPreferences sessionssodata = getApplicationContext().getSharedPreferences("sessionval", 0);
        SharedPreferences.Editor ssodata = sessionssodata.edit();
        ssodata.putString("ConsID", ConsID.toUpperCase()); //Storing div_code
        ssodata.putString("mobval", MobileNo); //Storing div_code
        ssodata.putString("imeinum", imeinum); //Storing div_code
        ssodata.putString("CompanyID", CompanyID); //Storing div_code
        Log.d("DemoApp", "in    imeinum:" + imeinum);
        ssodata.commit(); // commit changes


        if(Strtyp.equals("0")){
            databaseAccess = DatabaseAccess.getInstance(context);
            databaseAccess.open();
            String strSelectSQL_01 = "INSERT INTO CONSUMERDTLS  " +
                    " (CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD,PASSWORD,MOBILENO,IMEI_NUMBER,VALIDATE_FLG,EMAIL,SWVERSION,COMPANY_ID)" +
                    " VALUES('" + ConsID + "','" + ConsName + "','" + ConsAdd + "','" + strpwd1 + "'," +
                    " '" + MobileNo + "','" + imeinum + "',1,'" + email + "','" + versionID + "','" + CompanyID + "') ";
            Log.d("DemoApp", "strSelectSQL_01" + strSelectSQL_01);
            DatabaseAccess.database.execSQL(strSelectSQL_01);
            databaseAccess.close();
            // SharedPreferences sessionssodata = getApplicationContext().getSharedPreferences("sessionval1", 0);
            // SharedPreferences.Editor ssodata = sessionssodata.edit();
            //  ssodata.putString("ConsID", ConsID); //Storing div_code
            //  Log.d("DemoApp", "in    ConsID:" + ConsID);
            // ssodata.commit(); // commit changes
            Intent Colmain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Colmain);
            finish();

        }
     /*
        Intent compintent = new Intent(getApplicationContext(), ComplaintLoginActivity.class);
        Bundle compType = new Bundle();
        compType.putString("compinfo", "new");
        compintent.putExtras(compType);
        startActivity(compintent);
        finish();
*/

        btnsavepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stractkey = actkey.getText().toString();

                strpwd = pwd.getText().toString();
                strrepwd = repwd.getText().toString();
                if (TextUtils.isEmpty(strpwd)) {
                    pwd.setError("Please enter password");
                    pwd.requestFocus();
                } else if (TextUtils.isEmpty(strrepwd)) {
                    repwd.setError("Please Re-enter password ");
                    pwd.requestFocus();
                } else if (stractkey.equals(ActKey)) {
                    if (strpwd.equals(strrepwd)) {
                        databaseAccess = DatabaseAccess.getInstance(context);
                        databaseAccess.open();
                        String strSelectSQL_01 = "INSERT INTO CONSUMERDTLS  " +
                                " (CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD,PASSWORD,MOBILENO,IMEI_NUMBER,VALIDATE_FLG,EMAIL,SWVERSION,COMPANY_ID)" +
                                " VALUES('" + ConsID + "','" + ConsName + "','" + ConsAdd + "','" + strpwd + "'," +
                                " '" + MobileNo + "','" + imeinum + "',1,'" + email + "','" + versionID + "','" + CompanyID + "') ";
                        Log.d("DemoApp", "strSelectSQL_01" + strSelectSQL_01);
                        DatabaseAccess.database.execSQL(strSelectSQL_01);
                        databaseAccess.close();
                        // SharedPreferences sessionssodata = getApplicationContext().getSharedPreferences("sessionval1", 0);
                        // SharedPreferences.Editor ssodata = sessionssodata.edit();
                        //  ssodata.putString("ConsID", ConsID); //Storing div_code
                        //  Log.d("DemoApp", "in    ConsID:" + ConsID);
                        // ssodata.commit(); // commit changes
                        Intent Colmain = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(Colmain);
                        finish();
                    } else {
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
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Activation Key Mismatch");
                    alertDialogBuilder.setMessage("Enter Activation Key Correctly")
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
        });


        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
               // exit(0);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

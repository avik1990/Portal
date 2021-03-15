package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.System.exit;

public class popupPasswordActivity extends AppCompatActivity {
    private static ImageView smob1,spwd1,srepwd1;
    private static EditText smob,spwd,srepwd;
    private Button btnreset,btnback,btnrecover;
    private static TextView cf1;
    private   DatabaseAccess databaseAccess=null;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_password);
        getSupportActionBar().setTitle("Forgot Password ");
        cf1=(TextView) findViewById(R.id.cf);
        smob1=(ImageView) findViewById(R.id.mob1);
        spwd1=(ImageView) findViewById(R.id.pwd1);
        srepwd1=(ImageView) findViewById(R.id.rpwd1);
        smob=(EditText) findViewById(R.id.mob);
        spwd=(EditText) findViewById(R.id.pwd);
        srepwd=(EditText) findViewById(R.id.rpwd);

        btnreset = (Button) findViewById(R.id.savepwd);//reset
       // btnback= (Button) findViewById(R.id.back);
        btnrecover = (Button) findViewById(R.id.recover);
        spwd1.setVisibility(View.GONE);
        srepwd1.setVisibility(View.GONE);
        spwd.setVisibility(View.GONE);
        srepwd.setVisibility(View.GONE);
        cf1.setVisibility(View.GONE);
        btnrecover.setVisibility(View.GONE);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////////checking user registration...////////////////
                String MobileNo = smob.getText().toString();
                if (MobileNo.length() >= 0 && MobileNo.length() < 10) {
                    smob.setError("Enter Valid Mobile No");
                } else {
                    databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    String strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO " +
                            "FROM CONSUMERDTLS WHERE VALIDATE_FLG=1 AND MOBILENO='" + MobileNo + "' AND VALIDATE_FLG=1";
                    Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
                    Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
                    String mobno = "x";
                    while (cursor.moveToNext()) {
                        mobno = cursor.getString(1);
                        Log.d("DemoApp", "in Loop conid" + mobno);
                    }
                    cursor.close();
                    databaseAccess.close();
                    if (mobno.equals(MobileNo)) {
                        smob.setClickable(false);
                        smob.setFocusable(false);
                        smob.setText(mobno);
                        spwd1.setVisibility(View.VISIBLE);
                        srepwd1.setVisibility(View.VISIBLE);
                        spwd.setVisibility(View.VISIBLE);
                        srepwd.setVisibility(View.VISIBLE);
                        cf1.setVisibility(View.VISIBLE);
                        btnreset.setVisibility(View.GONE);
                        btnrecover.setVisibility(View.VISIBLE);
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Mobile No Mismatch");
                        alertDialogBuilder.setMessage("Enter Mobile No Correctly")
                                .setCancelable(false)
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        popupPasswordActivity.this.finish();
                                    }
                                });
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                    }
                }


            }
        });
        btnrecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strpwd = spwd.getText().toString();
                String strrepwd = srepwd.getText().toString();
                String MobileNo = smob.getText().toString();
                if (strpwd.equals(strrepwd)) {
                    databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    String strSelectSQL_01 = "UPDATE CONSUMERDTLS SET PASSWORD='" + strpwd + "' WHERE MOBILENO='" + MobileNo + "' AND VALIDATE_FLG=1";

                    Log.d("DemoApp", "strSelectSQL_01" + strSelectSQL_01);
                    DatabaseAccess.database.execSQL(strSelectSQL_01);
                    databaseAccess.close();
                    Intent forgetpwdintent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(forgetpwdintent);
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
                            .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    popupPasswordActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
            }
        });
    //    btnback.setOnClickListener(new View.OnClickListener() {
         //   @Override
       //     public void onClick(View view) {
            //    Intent UserDashboard = new Intent(getApplicationContext(), MainActivity.class);
            //    startActivity(UserDashboard);
            //    finish();
         //   }
      //  });
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
}

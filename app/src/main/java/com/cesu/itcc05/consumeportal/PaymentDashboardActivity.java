package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static java.lang.System.exit;

public class PaymentDashboardActivity extends AppCompatActivity {
    private static RadioGroup pmtgetways;
    private RadioButton getwaytyp;
    private String Typeinfo="";
    private  String consIDval="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_dashboard);
        androidx.appcompat.widget.Toolbar toolbarback = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Getways");
        pmtgetways=(RadioGroup)findViewById(R.id.radioGroup);
        pmtgetways.clearCheck();

        Bundle paydtls = getIntent().getExtras();
        consIDval = paydtls.getString("consIDval");
        Typeinfo= paydtls.getString("Typeinfo");
        Log.d("DemoApp", " Typeinfo   " + Typeinfo);
        pmtgetways.clearCheck();
        pmtgetways.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                Log.d("DemoApp", " checkedId   " + checkedId);//authoriztion check
                String typStr= "";
                int chkid=0;
                chkid=checkedId;
                typStr= (String) rb.getText();
                Log.d("DemoApp", " typStr   " + typStr);//authoriztion check
                typStr=typStr.substring(0,1);
                Log.d("DemoApp", " typStr   " + typStr);//authoriztion check
                if (null != rb && checkedId > -1) {
                 //   Toast.makeText(PaymentDashboardActivity.this, String.valueOf(typStr), Toast.LENGTH_SHORT).show();
                    if(typStr.equals("1")){
                        String strurl = "https://paytm.com/electricity-bill-payment/odisha/cesu-odisha?";
                        strurl=strurl+"recharge_number="+consIDval+"&prevalidate=true";
                        Log.d("DemoApp", " strurl   " + strurl);
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(strurl));
                        startActivity(i);
                        finish();
                      //  Toast.makeText(PaymentDashboardActivity.this, String.valueOf(checkedId), Toast.LENGTH_SHORT).show();
                    }else {
                        String strurl = "https://bit.ly/2mQVHJ9";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(strurl));
                        startActivity(i);
                        finish();
                      //  Toast.makeText(PaymentDashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }else{
                  //  Toast.makeText(PaymentDashboardActivity.this, "Select One ", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
            try {
                if (Typeinfo.equals("reg")) {
                    Intent i = new Intent(this, MainActivity.class);
                    this.startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(this, MainActivity.class);
                    this.startActivity(i);
                    finish();
                }
            }catch (Exception e)   {
                Intent i = new Intent(this, MainActivity.class);
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

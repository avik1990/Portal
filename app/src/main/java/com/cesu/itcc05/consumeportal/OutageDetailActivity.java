package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.System.exit;

public class OutageDetailActivity extends AppCompatActivity {
    private static Button strhint;
    private static TextView strconid,strconname,stroutageinfo;
    private  String pipeDelBillInfo="";
    private  String Typeinfo="";
    private String consIDval="";
    private String conname="";
    private String emailval="";
    private String mobval="";
    private String strAddrval="";
    private ImageView iv_backs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_outage_detail);
        strconid = (TextView) findViewById(R.id.conid);
        strconname = (TextView) findViewById(R.id.conname);
        stroutageinfo = (TextView) findViewById(R.id.outageinfo);
        Button btncomreg = (Button) findViewById(R.id.comreg);
        iv_backs=findViewById(R.id.iv_backs);

        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle parmtrDet = getIntent().getExtras();
        pipeDelBillInfo = parmtrDet.getString("pipeDelBillInfo");

         Typeinfo= parmtrDet.getString("Typeinfo");
         consIDval= parmtrDet.getString("consIDval");
         conname= parmtrDet.getString("conname");
         emailval= parmtrDet.getString("emailval");
         mobval= parmtrDet.getString("mobval");
         strAddrval= parmtrDet.getString("strAddrval");



        String[] OutageInfo = pipeDelBillInfo.split("[;]");
        String outMsg="";
        for(int i=0;i<OutageInfo.length;i++) {
            try {
                outMsg=outMsg+OutageInfo[i]+"\n"+"*******************"+"\n";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //pipeDelBillInfo = "1|1|102shhdhdh|santiranjan|line from 1.30 to 5.50 PM";
//        Log.d("DemoApp", " BillInfo[2]   " + OutageInfo[2]);//authoriztion check
        Log.d("DemoApp", " Typeinfo   " + Typeinfo);//authoriztion check
        strconid.setText(conname+"\n"+"("+consIDval+")");
        strconname.setText("");
        stroutageinfo.setText(outMsg);

        btncomreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commint = new Intent(getApplicationContext(), ComplainRegisterActivity.class);
                Bundle commdtls = new Bundle();
                commdtls.putString("consIDval", consIDval);
                commdtls.putString("emailval", emailval);
                commdtls.putString("mobval", mobval);
                commdtls.putString("regtype", Typeinfo);
                commdtls.putString("strAddrval", strAddrval);
                commdtls.putString("conname", conname);
                commint.putExtras(commdtls);
                startActivity(commint);
                finish();

                Log.d("DemoApp", " consIDval   " + consIDval);//authoriztion check
                Log.d("DemoApp", " emailval   " + emailval);//authoriztion check
                Log.d("DemoApp", " mobval   " + mobval);//authoriztion check
                Log.d("DemoApp", " Typeinfo   " + Typeinfo);//authoriztion check
                Log.d("DemoApp", " strAddrval   " + strAddrval);//authoriztion check
                Log.d("DemoApp", " conname   " + conname);//authoriztion check


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
            if(Typeinfo.equals("qlinks")) {
                Intent i = new Intent(this,QuicklinksDashboard.class);
                this.startActivity(i);
                finish();
            }else{
                Intent i = new Intent(this,OutageInfoActivity.class);
                this.startActivity(i);
               // finish();
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

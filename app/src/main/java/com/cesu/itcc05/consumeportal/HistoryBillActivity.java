package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.System.exit;

public class HistoryBillActivity extends AppCompatActivity {
    private static TextView strmon2;
    private static TextView strbill2;
    private static TextView strpmt2;
    private static TextView strdate2;
    private static TextView strmon3;
    private static TextView strbill3;
    private static TextView strpmt3;
    private static TextView strdate3;
    private static TextView strmon4;
    private static TextView strbill4;
    private static TextView strpmt4;
    private static TextView strdate4;
    private static TextView strmon5;
    private static TextView strbill5;
    private static TextView strpmt5;
    private static TextView strdate5;
    private static TextView strmon6;
    private static TextView strbill6;
    private static TextView strpmt6;
    private static TextView strdate6;
    private static TextView strmon1;
    private static TextView strbill1;
    private static TextView strpmt1;
    private static TextView strdate1;
    private static TextView strconName;
    private String pipeDelBillInfo="";
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bill History ");
        strconName =(TextView)findViewById(R.id.conName);
        strmon2 =(TextView)findViewById(R.id.mon2);
        strbill2=(TextView)findViewById(R.id.bill2);
        strpmt2=(TextView)findViewById(R.id.pmt2);
        strdate2=(TextView)findViewById(R.id.date2);
        strmon3 =(TextView)findViewById(R.id.mon3);
        strbill3=(TextView)findViewById(R.id.bill3);
        strpmt3=(TextView)findViewById(R.id.pmt3);
        strdate3=(TextView)findViewById(R.id.date3);
        strmon4=(TextView)findViewById(R.id.mon4);
        strbill4=(TextView)findViewById(R.id.bill4);
        strpmt4=(TextView)findViewById(R.id.pmt4);
        strdate4=(TextView)findViewById(R.id.date4);
        strmon5=(TextView)findViewById(R.id.mon5);
        strbill5=(TextView)findViewById(R.id.bill5);
        strpmt5=(TextView)findViewById(R.id.pmt5);
        strdate5=(TextView)findViewById(R.id.date5);
        strmon6=(TextView)findViewById(R.id.mon6);
        strbill6=(TextView)findViewById(R.id.bill6);
        strpmt6=(TextView)findViewById(R.id.pmt6);
        strdate6=(TextView)findViewById(R.id.date6);

        strmon1=(TextView)findViewById(R.id.mon1);
        strbill1=(TextView)findViewById(R.id.bill1);
        strpmt1=(TextView)findViewById(R.id.pmt1);
        strdate1=(TextView)findViewById(R.id.date1);
        Button btnback = (Button) findViewById(R.id.back);
        Bundle parmtrDet = getIntent().getExtras();
        pipeDelBillInfo = parmtrDet.getString("pipeDelBillInfo");
        String nameTemp = parmtrDet.getString("nameTemp");

      //  pipeDelBillInfo ="1|1|1|10-2019,296,1374.79,31-10-2019,-,0,-,0,29.6;09-2019,366,1766.03,30-09-2019,02282958,1730,26-09-2019,3,71.19;08-2019,354,1476.46,31-08-2019,00109662,1442,26-08-2019,3,64.22;07-2019,334,1644.43,31-07-2019,00086832,1612,25-07-2019,10,65.62;06-2019,176,828.81,30-06-2019,00971143,811,27-06-2019,1,32.79;";
                        // "1|1|1|OCT-2019,296,1374.79,31-10-2019,-,0,-,0,29.6,1409.95;SEP-2019,366,1766.03,30-09-2019,02282958,1730,26-SEP-2019,3,71.19,1795.79;AUG-2019,354,1476.46,31-08-2019,00109662,1442,26-AUG-2019,3,64.22,1729.65;JUL-2019,334,1644.43,31-07-2019,00086832,1612,25-JUL-2019,10,65.62,1659.41;JUN-2019,176,828.81,30-06-2019,00971143,811,27-JUN-2019,1,32.79,777.28;MAY-2019,427,2161.53,31-05-2019,00703618,2110,31-MAY-2019,3,42.7,2183.26;
        String[] BillInfo = pipeDelBillInfo.split("[|]");
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
                            HistoryBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else if(BillInfo[1].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Report ID Not Found");
            alertDialogBuilder.setMessage("Report ID Not Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HistoryBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else if(BillInfo[2].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Record Not Found");
            alertDialogBuilder.setMessage("Record Not Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HistoryBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else{
            SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
            String ConsID =sessiondata.getString("ConsID", null); // getting String
            //strconName.setText("Consumer ID: " + ConsID);
            strconName.setText(nameTemp);
            int arrayparam=70;
            String strData=BillInfo[3];
            String[] strData1=strData.split("[;]");
            String[] ContentWrap = new String[arrayparam];
            // 1|1|1|10-2019,155,.41,10-2019,00074-44,664,25-10-2019,0;09-2019,186,.35,09-2019,00074-79,799,24-09-2019,0;08-2019,130,-.24,08-2019,00552272,555,19-08-2019,0;07-2019,351,0,07-2019,00326691,1678,22-07-2019,0;06-2019,259,-.01,06-2019,00056002,1178,20-06-2019,0;05-2019,132,-2.12,05-2019,00194076,800,22-05-2019,0;04-2019,231,234.38,04-2019,00184406,800,27-04-2019,0;03-2019,177,-17.29,03-2019,0074-152,760,23-03-2019,0;02-2019,138,.17,02-2019,00139625,590,23-02-2019,0;
            String monData="";
            String[] MonthData = null;
            Log.d("DemoApp", " strData1.length   " + strData1.length);
            int j=0;
            //k=, data
            //i=month data
            // j= param data
            int moncnt=strData1.length;
            for(int i=0;i<strData1.length;i++){
                try{
                    monData=strData1[i];
                    // 10-2019,155,.41,10-2019,00074-44,664,25-10-2019,0
                    MonthData = null;
                    MonthData=monData.split("[,]");
                    Log.d("DemoApp", " MonthData.length   " + MonthData.length);
                    for(int k=0;k<MonthData.length;k++){
                        ContentWrap[j]=MonthData[k];
                        j++;
                    }

                    //  Log.d("DemoApp", " strData1.length   " + strData1.length);
                }catch (Exception e){e.printStackTrace();}
            }
            Log.d("DemoApp", " ContentWrap.length   " + ContentWrap.length);
            String[] finContentWrap = new String[arrayparam];
            for(int i=0;i<arrayparam;i++){
                finContentWrap[i]=ContentWrap[i];
                if(ContentWrap[i]==null){
                    finContentWrap[i]="-";
                }
            }

            strmon1.setText(finContentWrap[0]);
            strmon2.setText(finContentWrap[10]);
            strmon3.setText(finContentWrap[20]);
            strmon4.setText(finContentWrap[30]);
            strmon5.setText(finContentWrap[40]);
            strmon6.setText(finContentWrap[50]);

            strbill1.setText(finContentWrap[2]);
            strbill2.setText(finContentWrap[12]);
            strbill3.setText(finContentWrap[22]);
            strbill4.setText(finContentWrap[32]);
            strbill5.setText(finContentWrap[42]);
            strbill6.setText(finContentWrap[52]);

            strpmt1.setText(finContentWrap[5]);
            strpmt2.setText(finContentWrap[15]);
            strpmt3.setText(finContentWrap[25]);
            strpmt4.setText(finContentWrap[35]);
            strpmt5.setText(finContentWrap[45]);
            strpmt6.setText(finContentWrap[55]);

            strdate1.setText(finContentWrap[6]);
            strdate2.setText(finContentWrap[16]);
            strdate3.setText(finContentWrap[26]);
            strdate4.setText(finContentWrap[36]);
            strdate5.setText(finContentWrap[46]);
            strdate6.setText(finContentWrap[56]);

        }



/*
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserDashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(UserDashboard);
                finish();
            }
        });
*/

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

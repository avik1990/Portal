package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cesu.itcc05.consumeportal.adpater.ComplainStsDeatailsAdapter;
import com.cesu.itcc05.consumeportal.adpater.PaymentCentreDataAdapter;
import com.cesu.itcc05.consumeportal.modal.ModalComplainDetails;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class ComplaintStsDetailActivity extends AppCompatActivity {
    private static TextView StrConid,strCName;
    private  String strcomptyp="";
    private  String strStrConID="";
    private RecyclerView rv_complain_details;
    private List<ModalComplainDetails>modalComplainDetail=new ArrayList<>();
    ComplainStsDeatailsAdapter complainStsDeatailsAdapter;
    ImageView iv_backs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_sts_detail);

      //  strcompsts=(TextView)findViewById(R.id.compsts);
        StrConid=(TextView)findViewById(R.id.Conid);
        strCName=(TextView)findViewById(R.id.CName);
        rv_complain_details=findViewById(R.id.rv_complain_details);
        iv_backs=findViewById(R.id.iv_backs);
        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initAdapter();

        Bundle comstsDet = getIntent().getExtras();
        String strcompSts = comstsDet.getString("compSts");
        strcomptyp = comstsDet.getString("strtype");
        strStrConID = comstsDet.getString("StrConID");
        String ComstsName = comstsDet.getString("conname");
        String[] ComStsInfo = strcompSts.split("[;]");
        String[] ComStsInfodet=null;
        String StsMsg="";
        for(int i=0;i<ComStsInfo.length;i++) {
            try {
                ComStsInfodet=null;
                ComStsInfodet=ComStsInfo[i].split("[,]");

                ModalComplainDetails modalComplainDetails=new ModalComplainDetails();
                modalComplainDetails.setComplaint_date_time(ComStsInfodet[0]);
                modalComplainDetails.setComplaintID(ComStsInfodet[1]);
                modalComplainDetails.setComplaintType(ComStsInfodet[2]);
                modalComplainDetails.setComplaintSubType(ComStsInfodet[3]);
                modalComplainDetails.setComplaintDetails(ComStsInfodet[4]);
                modalComplainDetails.setActionTaken(ComStsInfodet[5]);
                modalComplainDetails.setResolvedDateTime(ComStsInfodet[6]);
                modalComplainDetails.setComplaintStatus(ComStsInfodet[7]);
                modalComplainDetail.add(modalComplainDetails);

                complainStsDeatailsAdapter.notifyDataSetChanged();

              //  StsMsg=StsMsg+"Complaint Date/Time: "+ComStsInfodet[0]+"\n"+"Complaint ID: "+ComStsInfodet[1]+"\n"+"Complaint Type: "+ComStsInfodet[2]+"\n"+"Complaint Sub-Type: "+ComStsInfodet[3]+"\n"+"Complaint Details: "+ComStsInfodet[4]+"\n"+"Action Taken: "+ComStsInfodet[5]+"\n"+"Resolved Date/Time: "+ComStsInfodet[6]+"\n"+"Complaint Status: "+ComStsInfodet[7]+"\n"+"*******************"+"\n";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //pipeDelBillInfo = "1|1|102shhdhdh|santiranjan|line from 1.30 to 5.50 PM";
//        Log.d("DemoApp", " BillInfo[2]   " + OutageInfo[2]);//authoriztion check
        Log.d("DemoApp", " Typeinfo   " + StsMsg);//authoriztion check
       // StrConid.setText("Consumer ID: " + strStrConID);
        StrConid.setText(ComstsName+"\n"+"("+strStrConID+")");
        if(ComstsName.length()>5) {
           // StrConid.setVisibility(View.VISIBLE);
       //     StrConid.setText(ComstsName+"\n"+"("+strStrConID+")");
        }else{
           // StrConid.setVisibility(View.GONE);
        }
        //strcompsts.setText(StsMsg);

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
            if(strcomptyp.equals("new")) {

                finish();
            }else{
                Intent i = new Intent(this,ComplaintDashboardActivity.class);
                this.startActivity(i);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initAdapter() {
        rv_complain_details.setLayoutManager(new LinearLayoutManager(ComplaintStsDetailActivity.this));

        complainStsDeatailsAdapter = new ComplainStsDeatailsAdapter(ComplaintStsDetailActivity.this, modalComplainDetail);
        rv_complain_details.setAdapter(complainStsDeatailsAdapter);
        //paymentCentreDataAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

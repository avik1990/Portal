package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import static java.lang.System.exit;


public class QuicklinksDashboard extends AppCompatActivity {
    private LinearLayout ll_safety;
    private LinearLayout ll_emp_ver;
    private LinearLayout ll_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quicklinks_dashboard);
        androidx.appcompat.widget.Toolbar toolbarback = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quick Links Dashboard");

        ImageButton btncomplain = (ImageButton) findViewById(R.id.complain);
       // ImageButton btncomplainsts= (ImageButton) findViewById(R.id.complainsts);
        ImageButton btnoutage = (ImageButton) findViewById(R.id.outage);
        ImageButton btntheft= (ImageButton) findViewById(R.id.theft);
        ll_safety=findViewById(R.id.ll_safety);
        ll_emp_ver=findViewById(R.id.ll_emp_ver);
        ll_feedback=findViewById(R.id.ll_feedback);
       // Button btnback = (Button) findViewById(R.id.back);
       // ImageButton btnquickinfo= (ImageButton) findViewById(R.id.quickinfo);


        ll_safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compintent = new Intent(getApplicationContext(), SafetyActivity.class);
                Bundle compType = new Bundle();
                startActivity(compintent);
               // finish();
            }
        });
        ll_emp_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compintent = new Intent(getApplicationContext(), EmployeeVerificationActivity.class);
                Bundle compType = new Bundle();
                startActivity(compintent);
              // finish();
            }
        });
        ll_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compintent = new Intent(getApplicationContext(), FeedBackActivity.class);
                startActivity(compintent);
            }
        });

        btncomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compintent = new Intent(getApplicationContext(), ComplaintLoginActivity.class);
                Bundle compType = new Bundle();
                compType.putString("compinfo", "new");
                compintent.putExtras(compType);
                startActivity(compintent);
                finish();
            }
        });
        /*
        btncomplainsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compintent = new Intent(getApplicationContext(), ComplaintStatusActivity.class);
                Bundle compType = new Bundle();
                compType.putString("compinfo", "new");
                compintent.putExtras(compType);
                startActivity(compintent);
                finish();
            }
        });
*/

        btntheft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compintent = new Intent(getApplicationContext(), TheftRegisterActivity.class);
                Bundle compType = new Bundle();
                compType.putString("compinfo", "new");
                compintent.putExtras(compType);
                startActivity(compintent);
                finish();
            }
        });
        btnoutage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent curblintent = new Intent(getApplicationContext(), OutageInfoActivity.class);
                Bundle reqDet = new Bundle();
                reqDet.putString("Typeinfo", "qlinks");
                curblintent.putExtras(reqDet);
                startActivity(curblintent);
            //    finish();

            }
        });

    //    toolbarback.setNavigationOnClickListener(new View.OnClickListener() {
         //   @Override
          //  public void onClick(View v) {
             //   startActivity(new Intent(getApplicationContext(), MainActivity.class));
             //   finish();
           // }
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

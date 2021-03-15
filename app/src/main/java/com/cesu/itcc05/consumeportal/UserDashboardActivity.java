package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static java.lang.System.exit;

public class UserDashboardActivity extends AppCompatActivity {

    private ImageView iv_back;
    private LinearLayout viewBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        iv_back=findViewById(R.id.iv_backs);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // setSupportActionBar(toolbar);

        CardView btnadduser = (CardView) findViewById(R.id.addcon);
        CardView btndeluser= (CardView) findViewById(R.id.deluser);

       // ImageButton btnmoduser = (ImageButton) findViewById(R.id.moduser);
       // Button btnback = (Button) findViewById(R.id.back);

        CardView btndeluser1= (CardView) findViewById(R.id.deluser1);
        CardView btnmodprof= (CardView) findViewById(R.id.modprof);
        btnadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent UserDashboard = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivity(UserDashboard);
               // finish();

            }
        });
        btndeluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserDashboard = new Intent(getApplicationContext(), DeleteConsumerActivity.class);
                startActivity(UserDashboard);
               // finish();
            }
        });

        /*
        btnmoduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserDashboard = new Intent(getApplicationContext(), ModifyUserActivity.class);
                startActivity(UserDashboard);
                finish();
            }
        });
        */
        btndeluser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserDashboard = new Intent(getApplicationContext(), DeleteUserActivity.class);
                startActivity(UserDashboard);
             //   finish();
            }
        });
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
        btnmodprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserDashboard = new Intent(getApplicationContext(), ModifyProfileActivity.class);
                startActivity(UserDashboard);
               // finish();
            }
        });
        CardView btncbillh = (CardView) findViewById(R.id.cbillh);
        CardView btnpmthist = (CardView) findViewById(R.id.pmthist);
        CardView btngraph1= (CardView) findViewById(R.id.graph1);
      //  ImageButton btnbilhist = (ImageButton) findViewById(R.id.bilhist);
        btncbillh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hisintent = new Intent(getApplicationContext(), ConsumerMenuActivity.class);
                Bundle hisDet = new Bundle();
                hisDet.putString("reqtype", "conhis");
                hisintent.putExtras(hisDet);
                startActivity(hisintent);
            //    finish();
            }
        });
        btnpmthist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hisintent = new Intent(getApplicationContext(), ConsumerMenuActivity.class);
                Bundle hisDet = new Bundle();
                hisDet.putString("reqtype", "payhis");
                hisintent.putExtras(hisDet);
                startActivity(hisintent);
           //     finish();
            }
        });
        btngraph1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hisintent = new Intent(getApplicationContext(), ConsumerMenuActivity.class);
                Bundle hisDet = new Bundle();
                hisDet.putString("reqtype", "dethis");
                hisintent.putExtras(hisDet);
                startActivity(hisintent);
           //     finish();
            }
        });



        /*
        btnbilhist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hisintent = new Intent(getApplicationContext(), ConsumerMenuActivity.class);
                Bundle hisDet = new Bundle();
                hisDet.putString("reqtype", "bilhis");
                hisintent.putExtras(hisDet);
                startActivity(hisintent);
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
        //    Toast.makeText(getApplicationContext(), "User Menu1", Toast.LENGTH_SHORT).show();
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

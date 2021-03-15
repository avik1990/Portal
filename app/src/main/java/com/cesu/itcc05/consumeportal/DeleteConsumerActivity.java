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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static java.lang.System.exit;

public class DeleteConsumerActivity extends AppCompatActivity {
    private   DatabaseAccess databaseAccess=null;
    final Context context = this;
    private String userID="";
    private Button deleteBtn;
    private static   RadioGroup ll;
    private static RadioButton rdbtn;
    private static String selconid="";
    private ImageView iv_backs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_consumer);

        iv_backs=findViewById(R.id.iv_backs);

        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

      //  Button btndelconsumer = (Button) findViewById(R.id.delconsumer);
      //  Button btnback = (Button) findViewById(R.id.back);
        deleteBtn=(Button)findViewById(R.id.delete);
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT CONSUMER_ID,CONSUMER_NAME "+
                "FROM CONSUMERDTLS WHERE VALIDATE_FLG=9 ";
        Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_01);
        Cursor rs = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        int totcnt = 0;
        while (rs.moveToNext()) {
            totcnt++;
        }
        if(totcnt==0){
          //  btndelconsumer.setEnabled(false);
         //   btndelconsumer.setClickable(false);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(R.string.no_consumer_to_delete);
            alertDialogBuilder.setMessage(R.string.sorry)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           onBackPressed();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            onBackPressed();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }else {
            String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME "+
                    "FROM CONSUMERDTLS WHERE VALIDATE_FLG=9 ";
            Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_02);
            Cursor rs1 = DatabaseAccess.database.rawQuery(strSelectSQL_02, null);
            LinearLayout layout = (LinearLayout) findViewById(R.id.rootContainer);
            ll = new RadioGroup(this);

            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layout.addView(ll, p);
            String consid = "";
            String consname = "";
            while (rs1.moveToNext()) {
                consid = rs1.getString(0);
                consname = rs1.getString(1);
                rdbtn = new RadioButton(this);
                rdbtn.setText("ID:" + consid +"\n" +"NAME:" + consname);
                rdbtn.setTextSize(15);

             //   rdbtn.setOnClickListener(mThisButtonListener);
                ll.addView(rdbtn, p);
            }
        }
        databaseAccess.close();
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll.getCheckedRadioButtonId() == -1) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(R.string.no_con_selected);
                    alertDialogBuilder.setMessage(R.string.select_one)
                            .setCancelable(false)
                            .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.back), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                  onBackPressed();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                    // internetStatus.setText("Internet Disconnected.");
                    //  internetStatus.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    // get selected radio button from radioGroup
                    int selectedId = ll.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    rdbtn = (RadioButton) findViewById(selectedId);
                    String coniddd = rdbtn.getText().toString();
                    String[] rbinfo = coniddd.split("[NAME:]");
                    selconid = rbinfo[1];
                    Log.d("DemoApp", " coniddd   " + selconid);
                    databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();

                    String strSelectSQL_01 = "UPDATE CONSUMERDTLS SET VALIDATE_FLG='8' WHERE CONSUMER_ID='" + selconid.toString().trim() + "' AND VALIDATE_FLG='9'";
                    Log.d("DemoApp", "strSelectSQL_01" + strSelectSQL_01);
                    DatabaseAccess.database.execSQL(strSelectSQL_01);
                    databaseAccess.close();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle(R.string.delete_success);
                    alertDialogBuilder.setMessage(R.string.delete_success)
                            .setCancelable(false)
                            .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   onBackPressed();
                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.back), new DialogInterface.OnClickListener() {
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

    }

    private View.OnClickListener mThisButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            String s = ((RadioButton) v).getText().toString();
            String[] TransInfo = s .split("[:]");

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Do You Want to Generate Receipt");
            alertDialogBuilder.setMessage("Tap Generate if yes" + "\n" + " Tap Cancel to re-select ")
                    .setCancelable(false)
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Generate", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  ReceiptGen.this.finish();


                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    };
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

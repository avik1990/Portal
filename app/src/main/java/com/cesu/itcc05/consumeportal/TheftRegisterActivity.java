package com.cesu.itcc05.consumeportal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static java.lang.System.exit;

public class TheftRegisterActivity extends AppCompatActivity {
    private String compinfo="";
    private static EditText strmobno;
    private static EditText strtheftdet;
    private int chkresponse=1;
    private String AuthURL=null;
    private String pipeDelBillInfo="";
    final Context context = this;
   // private String StrUrl="";
    private String strmobnoval = "";
    private String strtheftdetval ="";
    private String strDate="";
    private ProgressBar spinner;
    private ImageView iv_back;
    private TextView tv_send_number;
    private ImageView iv_camera,iv_gallaery;
    private ConstraintLayout ll_iv_selected;
    private TextView image_name;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    private LocationManager locationManager;
    private String provider;
    private MyLocationListener mylistener;
    private Criteria criteria;
    private String latitude="";
    private String longitude="";
    private String base64Image="";
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CALL_PHONE
    };
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private Uri picUri1;
    private  boolean camera=false;

    public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    public final String METHOD_NAME = "Theft_Registration";
    public final String SOAP_ACTION = "http://tempuri.org/Theft_Registration";
    public final String SOAP_ADDRESS = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx?WSDL";
    private String responseSubmit="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theft_register);
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        iv_back=findViewById(R.id.iv_backs);
        tv_send_number=findViewById(R.id.tv_send_number);
        iv_camera=findViewById(R.id.iv_camera);
        iv_gallaery=findViewById(R.id.iv_gallaery);
        ll_iv_selected=findViewById(R.id.ll_iv_selected);
        image_name=findViewById(R.id.image_name);
        ll_iv_selected.setVisibility(View.GONE);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera=true;
                checkPermissions();
            }
        });
        iv_gallaery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera=false;
                checkPermissions();
            }
        });

        tv_send_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + "9437158670")));

               /* String url = "https://api.whatsapp.com/send?phone="+"9437158670";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*/
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);
        provider = locationManager.getBestProvider(criteria, false);

        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
      //  StrUrl="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_SMS.jsp?";
        //StrUrl="http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Theft_Registration?checksum=string&REPORTER_MOBILENO=string&THEFT_DESCRIPTION=string&LAT=string&LONG=string&THEFT_IMAGE=string";



        try{
        Bundle compType = getIntent().getExtras();
        compinfo = compType.getString("compinfo");
        }catch(Exception e){compinfo="0";}
        strmobno=(EditText)findViewById(R.id.mobno);
        strtheftdet=(EditText)findViewById(R.id.theftdet);
        Button btnsubmit=(Button)findViewById(R.id.submit);
        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);

        String mobval =sessiondata.getString("mobval", null); // getting String
        strmobno.setText(mobval);
       // Button btnback=(Button)findViewById(R.id.back);
        /*
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(compinfo.equals("reg")){
                    Intent UserDashboard = new Intent(getApplicationContext(), ComplaintDashboardActivity.class);
                    startActivity(UserDashboard);
                    finish();
                }else{
                    Intent QDashboard = new Intent(getApplicationContext(), QuicklinksDashboard.class);
                    startActivity(QDashboard);
                    finish();
                }

            }
        });*/
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy^HH:mm:ss");
                    // SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyyHH:mm:ss");
                    strDate = sdf.format(c.getTime());
                }catch (Exception e){e.printStackTrace();}
                String Message = "CESU THF DTL ";
                strmobnoval = strmobno.getText().toString();
                strtheftdetval = strtheftdet.getText().toString();
                strtheftdetval = Message +"^"+strtheftdetval ;
                strtheftdetval = strtheftdetval.replaceAll(" ", "^");
                strtheftdetval=strtheftdetval.replaceAll("\n", "");//added on 16.12.2017
                strtheftdetval=strtheftdetval.replaceAll("\t", "");//added on 16.12.2017
                Log.d("DemoApp", "consIDval" + strmobnoval);

                if (strmobnoval.length()==0){
                    strmobno.setError(getString(R.string.enter_phoner_number));
                    strmobno.requestFocus();
                }

               else if(strmobnoval.length()>0 && strmobnoval.length()<10 ) {
                    strmobno.setError(getString(R.string.invalid_mobile));
                    strmobno.requestFocus();
                }
                else if(strtheftdet.getText().toString().length()==0) {
                    strtheftdet.setError("Please enter  theft Description");
                    strtheftdet.requestFocus();
                } else{
                    //Added on 040520 by Santi
                    if (strmobnoval.length() == 10) {
                        if (CommonMethods.isValidMobile(strmobnoval)) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {

                                       if (!(CommonMethods.checkGPS(TheftRegisterActivity.this))) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(TheftRegisterActivity.this);
                                    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                                    dialog.cancel();
                                                    changeTextStatus(true);
                                                }
                                            });
                                    final AlertDialog alert = builder.create();
                                    alert.show();
                                     }
                                       else {
                                           changeTextStatus(true);
                                       }



                            } else {
                                changeTextStatus(false);
                            }
                        } else {
                            strmobno.setError("Mobile digit start from 6/7/8/9");
                            strmobno.requestFocus();
                        }
                    }
                    ///end
                }
            }
        });
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
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            TheftRegisterActivity.this.finish();
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
           // String strURLFullPath = "http://portal1.tpcentralodisha.com:8080/IncomingSMS/CESU_SMS.jsp?un=CESU_APPS&pw=CESU_THF&sender="+smsaddress.trim()+"&message=" + msgbody + "&stime=" + strDate;
          //  AuthURL =StrUrl+"un=CESU_APPS&pw=CESU_THF&sender="+strmobnoval+"&message="+strtheftdetval+"&stime="+strDate;
            AuthURL="http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Theft_Registration?checksum=01091981&REPORTER_MOBILENO="+strmobnoval+"&THEFT_DESCRIPTION="+strtheftdetval+"&LAT="+latitude+"&LONG="+longitude+"&THEFT_IMAGE="+base64Image;


            new UserAuthOnline().execute();
        }else if(resCode==2){
           // AuthURL =StrUrl+"un=CESU_APPS&pw=CESU_THF&sender="+strmobnoval+"&message="+strtheftdetval+"&stime="+strDate;
            AuthURL="http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Theft_Registration?checksum=01091981&REPORTER_MOBILENO="+strmobnoval+"&THEFT_DESCRIPTION="+strtheftdetval+"&LAT="+latitude+"&LONG="+longitude+"&THEFT_IMAGE="+base64Image;

            new UserAuthOnline().execute();
        }else{
            AuthURL="ServerOut";
            //  int code=AlertErrorCall.ErrorMsgType(6);
            //   if(code==1){MainActivity.this.finish();}
        }

        return AuthURL;
    }
    private  class UserAuthOnline extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                    METHOD_NAME);

            request.addProperty("REPORTER_MOBILENO", strmobnoval.trim());
            request.addProperty("THEFT_DESCRIPTION", strtheftdetval.trim());
            request.addProperty("LAT", latitude);
            request.addProperty("LONG", longitude);
            request.addProperty("THEFT_IMAGE", base64Image);

            request.addProperty("checksum","01091981");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SOAP_ADDRESS);
           /* try {
                androidHttpTransport.call(SOAP_ACTION,envelope);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            catch (XmlPullParserException ex){
                ex.printStackTrace();
            }*/

            SoapObject response = null;
            try {

                androidHttpTransport.call(SOAP_ACTION, envelope);

                if (envelope.bodyIn instanceof SoapFault)
                {
                    final SoapFault sf = (SoapFault) envelope.bodyIn;
                    responseSubmit = sf.toString();

                }
                else {
                    response = (SoapObject) envelope.bodyIn;
                    responseSubmit = response.getProperty("Theft_RegistrationResult").toString();
                }

                System.out.println("sdfgh=="+responseSubmit);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return responseSubmit;
        }
        @Override

        protected void onPreExecute() {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
               // progressDialog = ProgressDialog.show(TheftRegisterActivity.this, " ", " ");
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
                                TheftRegisterActivity.this.finish();
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
            pipeDelBillInfo = str;

            //  pipeDelBillInfo="1|1|Resolved|sdfsdfsdffsdfsd"; //Complain register

            // String[] BillInfo = pipeDelBillInfo.split("[|]");
            // Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check
          //if(BillInfo[1].equals("1")){//Theft sucess

            if (responseSubmit.equalsIgnoreCase("1")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Theft incident submitted successfully");
                alertDialogBuilder.setMessage("Thank You")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onBackPressed();
                        /*    if (compinfo.equals("reg")) {
                                Intent curbill = new Intent(getApplicationContext(), DashboardActivity.class);
                                startActivity(curbill);
                                finish();
                            } else {
                                Intent curbill = new Intent(getApplicationContext(), QuicklinksDashboard.class);
                                startActivity(curbill);
                                finish();
                            }*/
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }

        //  }
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
            Toast.makeText(getApplicationContext(), "User Menu1", Toast.LENGTH_SHORT).show();
            finish();
            exit(0);
            return true;
        } else if (id == R.id.action_user) { //back
            if(compinfo.equals("reg")) {
                Intent i = new Intent(this,MainActivity.class);
                this.startActivity(i);
                finish();
            }else{

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        int REQUEST_CODE_FILE_UPLOAD = 5902;
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        if (permissionStatus.getBoolean(REQUIRED_SDK_PERMISSIONS[0], true)) {
                            //Previously Permission Request was cancelled with 'Dont Ask Again',
                            // Redirect to Settings after showing Information about why you need the permission
                            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(TheftRegisterActivity.this);
                            builder.setTitle("Need Storage Permission");
                            builder.setMessage("This app needs storage permission.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    sentToSettings = true;
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                    Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                        return;
                    }
                }
                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
                editor.commit();

                if (camera){
                    pickPicture(REQUEST_CODE_FILE_UPLOAD);
                }
                else {
                    pickPictureGallery(REQUEST_CODE_FILE_UPLOAD);
                }

                break;
        }
    }

    private void pickPictureGallery(int request_code_file_upload) {
        ImagePicker.Companion.with(this)
                // .crop()
                .galleryOnly()
                //Crop image(Optional), Check Customization for more option
                .compress(1024)
                //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    private void pickPicture(int requestCode) {
        ImagePicker.Companion.with(this)
                // .crop()
                .cameraOnly()
                //Crop image(Optional), Check Customization for more option
                .compress(1024)
                //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filePath = "";
        if (resultCode == Activity.RESULT_OK) {
            picUri1 = data != null ? data.getData() : null;
            filePath = picUri1.getPath();



            File file = new File(filePath);
            String filename = file.getName();

            base64Image = CommonMethods.readFileAsBase64String(filePath);

            //  base64Image=CommonMethods.resizeBase64Image(base64Image);
            ll_iv_selected.setVisibility(View.VISIBLE);
            image_name.setText(filename);

        }
        else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show();
            ll_iv_selected.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
            ll_iv_selected.setVisibility(View.GONE);
        }
    }
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // Initialize the location fields
            latitude=String.valueOf(location.getLatitude());
            longitude= String.valueOf(location.getLongitude());

            System.out.println("asdfgh=="+latitude);


          /*  Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(SafetyActivity.this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                System.out.println("addresses=="+address);
                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

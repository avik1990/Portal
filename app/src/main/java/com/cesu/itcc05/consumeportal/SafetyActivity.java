package com.cesu.itcc05.consumeportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesu.itcc05.consumeportal.modal.ModalSection;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SafetyActivity extends AppCompatActivity {

    private Context mContext;
    private Button btn_submit;
    private ImageView iv_gallaery;
    private ImageView iv_camera;
    private EditText et_remarks;
    private EditText et_mobile;
    private EditText et_email;
    private AutoCompleteTextView  et_pin_code;
    private EditText et_landmark;
    private EditText et_address;
    private EditText et_description;
    private LocationManager locationManager;
    private String provider;
    private MyLocationListener mylistener;
    private Criteria criteria;
    private String latitude="";
    private String longitude="";
    private ProgressDialog progressDialog;
    private String base64Image="";
    private EditText et_customer_id;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    public final String METHOD_NAME = "Add_SafetyIncident";
    public final String SOAP_ACTION = "http://tempuri.org/Add_SafetyIncident";
    public final String SOAP_ADDRESS = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx?WSDL";

    private DatabaseAccess databaseAccess;

    List<ModalSection> list = new ArrayList<ModalSection>();
    ArrayList<String> sections = new ArrayList<String>();

    // SoapPrimitive result;
    String responseSubmit="";
    String responseSection="";
    private ImageView iv_back;
    private TextView lblResult;

    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CALL_PHONE
    };
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private Uri picUri1;
    private  boolean camera=false;
    String phoneNumber="";
    String consumerId="";
    String sectionId="";
    ConstraintLayout ll_iv_selected;
    TextView image_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);
        mContext=this;
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        btn_submit=findViewById(R.id.btn_submit);
        iv_gallaery=findViewById(R.id.iv_gallaery);
        iv_camera=findViewById(R.id.iv_camera);
        et_remarks=findViewById(R.id.et_remarks);
        et_mobile=findViewById(R.id.et_mobile);
        et_email=findViewById(R.id.et_email);
        et_pin_code=findViewById(R.id.et_pin_code);
        et_landmark=findViewById(R.id.et_landmark);
        et_address=findViewById(R.id.et_address);
        et_description=findViewById(R.id.et_description);
        et_customer_id=findViewById(R.id.et_customer_id);
        iv_back=findViewById(R.id.iv_backs);
        ll_iv_selected=findViewById(R.id.ll_iv_selected);
        ll_iv_selected.setVisibility(View.GONE);
        image_name=findViewById(R.id.image_name);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);
        provider = locationManager.getBestProvider(criteria, false);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        String strUpdateSQL_01 = "";
        Cursor rs = null;
        strUpdateSQL_01 = "SELECT CONSUMER_ID,MOBILENO,EMAIL FROM CONSUMERDTLS WHERE VALIDATE_FLG=1 ";
        rs = DatabaseAccess.database.rawQuery(strUpdateSQL_01, null);
        while (rs.moveToNext()) {
            consumerId = rs.getString(0);
            phoneNumber = rs.getString(1);
        }
        rs.close();

        if (consumerId!=null){
            if (consumerId.length()==0){
                et_customer_id.setHint("Enter consumer ID");
            }
            else {
                et_customer_id.setText(consumerId);

            }
        }

        if (phoneNumber!=null){
            if (phoneNumber.length()==0){
                et_mobile.setHint("Enter Mobile No.");
            }
            else {
                et_mobile.setText(phoneNumber);
            }

        }






        fetchSectionData();

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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonMethods.hideKeyboard(SafetyActivity.this);

                if (et_description.getText().toString().trim().length() == 0){
                    Toast.makeText(mContext, "Please enter description", Toast.LENGTH_SHORT).show();

                }

            else   if (et_description.getText().toString().trim().length() < 30) {
                    Toast.makeText(mContext, "Description should be minimum of 30 character", Toast.LENGTH_SHORT).show();
                } else if (et_address.getText().toString().trim().length() == 0) {
                  Toast.makeText(mContext, "Please enter address", Toast.LENGTH_SHORT).show();
              }else if (base64Image.length() == 0) {
                    Toast.makeText(mContext, "Please capture image", Toast.LENGTH_SHORT).show();

                }

            else if (et_mobile.getText().toString().trim().length()==0){
                    Toast.makeText(mContext, "Please enter mobile number", Toast.LENGTH_SHORT).show();

                }

                else if (et_mobile.getText().toString().trim().length()>0){
                    if (!((CommonMethods.isValidMobile(et_mobile.getText().toString().trim().toString())))){
                        Toast.makeText(mContext, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    }
                    else if (et_email.getText().toString().trim().length()>0){
                        if(!(CommonMethods.isValidEmail(et_email.getText().toString().trim()))){
                            Toast.makeText(mContext, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            callSafety();

                        }
                    }
                    else {
                        callSafety();
                    }


                }

                else if (!(CommonMethods.checkGPS(mContext))) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();
                }

                else {
                    // call submit


                    callSafety();



                }

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }

        Location location = locationManager.getLastKnownLocation(provider);
        mylistener = new MyLocationListener();

        if (location != null) {
            mylistener.onLocationChanged(location);
        } else {
            // leads to the settings because there is no last known location

            if (!(CommonMethods.checkGPS(mContext))){
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                finish();
                                // dialog.cancel();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }

           /* Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);*/
        }
        // location updates: at least 1 meter and 200millsecs change
        locationManager.requestLocationUpdates(provider, 200, 1, mylistener);

    }

    private void fetchSectionData() {
        String url="http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/List_of_Sections?checksum=01091981";

        new fetchSectionData().execute(url);
    }

    private void callSafety() {
        new TestAsynk().execute();

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
                            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SafetyActivity.this);
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
    private class TestAsynk extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                    METHOD_NAME);
            request.addProperty("CustomerID", et_customer_id.getText().toString().trim());

            try {
                if ((phoneNumber!=null)){
                    request.addProperty("MobileNo", phoneNumber);

                }
                else {
                    request.addProperty("MobileNo", "");
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }


            request.addProperty("eMail", et_email.getText().toString().trim());
            request.addProperty("imei", et_mobile.getText().toString().trim());
            request.addProperty("SafetyDesc", et_description.getText().toString().trim());
            request.addProperty("SafetyRemark", et_remarks.getText().toString().trim());
            request.addProperty("lat", latitude);
            request.addProperty("lon", longitude);
            request.addProperty("image", base64Image);
            request.addProperty("address", et_address.getText().toString().trim());
            request.addProperty("landmark", et_landmark.getText().toString());
            request.addProperty("Areacode", sectionId);
            request.addProperty("Sitemobileno", et_mobile.getText().toString().trim());
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
                    responseSubmit = response.getProperty("Add_SafetyIncidentResult").toString();

                }



                //Log.e("Object response", response.toString());

                System.out.println("sdfgh=="+responseSubmit);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return responseSubmit;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                progressDialog = ProgressDialog.show(SafetyActivity.this, "Submitting Data", "Please Wait:: connecting to server");

            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle("Enable Data");
                alertDialogBuilder.setMessage("Enable Data & Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setTitle("Successful!");
            alertDialogBuilder.setMessage("Your incident submitted successfully keep this Reference No.-"+result+" "+"for future purpose")

                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            onBackPressed();
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();


        }

    }

    private  class fetchSectionData extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL=params[0];
            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent=null;
            Log.d("DemoApp", " strURL   " + strURL);

            try {
                URL url =null;
                url = new URL(strURL);
                URLConnection uc = url.openConnection();
                uc.setDoInput(true);
                BufferedReader in = null;
                in=new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String inputLine="";
                String inputLine1="";
                StringBuilder a = new StringBuilder();
                Log.d("DemoApp", " a size   " + a.length());
                while ((inputLine = in.readLine()) != null) {
                    a.append(inputLine);
                    inputLine1=inputLine;
                    //  Log.d("DemoApp", " input line " + a.toString());
                }
                in.close();

                Log.d("DemoApp", " fullString   " + a.toString());

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                Element element=doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("Table");


                for (int temp = 0; temp < nList.getLength(); temp++)
                {
                    Node node = nList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        getValue("AREA_CODE", element2);
                        getValue("AREA_DESC", element2);
                        System.out.println("----------------------------------------"+getValue("AREA_CODE", element2));
                        ModalSection modalSection=new ModalSection();
                        modalSection.setSectionId(getValue("AREA_CODE", element2));
                        modalSection.setSectionName(getValue("AREA_DESC", element2));
                        list.add(modalSection);
                        sections.add(getValue("AREA_DESC", element2));
                    }
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

            return bodycontent;
        }
        @Override

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(SafetyActivity.this, "Fetching Data", "Please Wait:: connecting to server");

            ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle("Enable Data");
                alertDialogBuilder.setMessage("Enable Data & Retry")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onBackPressed();
                            }
                        })
                       ;
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }
        @Override
        protected void onPostExecute(String str) {
            Log.d("DemoApp", " str   " + str);
            progressDialog.dismiss();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (SafetyActivity.this,android.R.layout.simple_list_item_1,sections);
            et_pin_code.setThreshold(1);//will start working from first character
            et_pin_code.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
            et_pin_code.setTextColor(Color.BLACK);


            et_pin_code.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    for (int i=0;i<list.size();i++){

                        if (et_pin_code.getText().toString().trim().equalsIgnoreCase(list.get(i).getSectionName())){
                            sectionId=list.get(i).getSectionId();
                            System.out.println("asfdghj=="+sectionId);
                        }
                    }
                }
            });
            //fetChImage(empId);
        }

    }
    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
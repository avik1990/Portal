package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class FeedBackActivity extends AppCompatActivity {
    private Context mContext;;
    private Button btn_submit;
    private EditText et_feedback;
    private EditText et_email;
    private EditText et_phone;
    public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    public final String METHOD_NAME = "Add_App_Feedback";
    public final String SOAP_ACTION = "http://tempuri.org/Add_App_Feedback";
    public final String SOAP_ADDRESS = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx?WSDL";
    private String responseSubmit;
    private ProgressDialog progressDialog;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);
        mContext=this;
        btn_submit=findViewById(R.id.btn_submit);
        et_feedback=findViewById(R.id.et_feedback);
        et_email=findViewById(R.id.et_email);
        et_phone=findViewById(R.id.et_phone);
        iv_back=findViewById(R.id.iv_backs);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_feedback.getText().toString().trim().length()==0){
                    Toast.makeText(mContext,"Please enter feedback",Toast.LENGTH_SHORT).show();
                }

                else if (et_feedback.getText().toString().length()==2000){
                    Toast.makeText(mContext,"Maximum character allowed is 2000",Toast.LENGTH_SHORT).show();
                }
                else if (et_phone.getText().toString().trim().length()>0){
                    if (!((CommonMethods.isValidMobile(et_phone.getText().toString().trim().toString())))){
                        Toast.makeText(mContext, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    }
                    else if (et_email.getText().toString().trim().length()>0){
                        if(!(CommonMethods.isValidEmail(et_email.getText().toString().trim()))){
                            Toast.makeText(mContext, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            callSubmitFeedback();

                        }
                    }
                    else {
                        callSubmitFeedback();
                    }


                }
                else {
                    callSubmitFeedback();
                }
            }
        });
    }

    private void callSubmitFeedback() {
      new TestAsynk().execute();
    }

    private class TestAsynk extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                    METHOD_NAME);
            request.addProperty("FEEDBACK", et_feedback.getText().toString().trim());
            request.addProperty("MOBILE_NO", et_phone.getText().toString().trim());
            request.addProperty("EMAILID", et_email.getText().toString().trim());
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
            }
*/
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
                    responseSubmit = response.getProperty("Add_App_FeedbackResult").toString();

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
                progressDialog = ProgressDialog.show(FeedBackActivity.this, "Submitting Feedback", "Please Wait:: connecting to server");

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
            alertDialogBuilder.setMessage("Your have successfully submitted feedback")

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

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
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.cesu.itcc05.consumeportal.modal.BannerModal;
import com.cesu.itcc05.consumeportal.modal.ModalSection;
import com.cesu.itcc05.consumeportal.modal.ModalStatics;
import com.cesu.itcc05.consumeportal.modal.OfferSchemModal;
import com.cesu.itcc05.consumeportal.modal.SliderAdapterExample;
import com.cesu.itcc05.consumeportal.modal.SliderItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import chatbots.ChatActivity;
import pl.droidsonroids.gif.GifImageView;

import static com.cesu.itcc05.consumeportal.CommonMethods.getAppLink;
import static com.cesu.itcc05.consumeportal.CommonMethods.getAppVersion;
import static java.lang.System.exit;
import static java.lang.System.in;

public class MainActivity extends Activity implements
        AdapterView.OnItemSelectedListener, View.OnTouchListener {
    private static ImageView strconsID;
    private static Button strhint, btnsubmit;
    private static ImageView stremail;
    private static ImageView strMobno;
    private static ImageView strstar1;
    private static TextView strmobmsg, strSwVersion;
    private static EditText strconsIDval;
    private static EditText stremailval;
    private static EditText strmobval;
    private String consIDval = "";
    private String emailval = "";
    private String mobval = "";
    private String imeinum = "";
    public static final int RequestPermissionCode = 1;
    public static final int WRITE_REQUEST_CODE = 1;
    private String strpwd = "";
    private String strrepwd = "";
    final Context context = this;
    private int chkresponse = 1;
    private String AuthURL = null;
    Button showPopupBtn, closePopupBtn;
    TextView UpdateSwBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    FrameLayout layout;
    private DatabaseAccess databaseAccess = null;
    private CheckBox TermCond;
    private String versionID = "";
    private String contype = "";
    private String StrUrl = "";
    private String CompanyID = "";
    private String swversion = "";
    private Button btntrmscnd;
    private TextView tncLabel;
    private static TableRow strrpedtbl, strtblpwd, strtblemail, strconsid1;
    private static EditText pwd, repwd;
    // private ProgressBar spinner;
    private String MobileNo = "";
    private String ConsID = "";
    private String ConsName = "";
    private String ConsAdd = "";
    private String ActKey = "";
    private String email = "";
    private String matchflg = "0";
    private String StrvalidIpget = "0";
    private String StrvalidIpgeturl = "StrvalidIpgeturl";
    private List<SliderItem> images = new ArrayList<>();
    private CardView cardview_outage;
    //   private CardView card_payment;
    private CardView card_my_account;
    private CardView card_register_complain;
    private CardView cardview_register_theft;
    private CardView cardview_safety;
    private CardView cardview_employee_verify;
    private CardView cardview_feedback;
    private ArrayList<String> imgaeList = new ArrayList<>();
    private String strSelectSQL_01 = "";
    private Cursor cursor;
    private Spinner spinner_ca;
    private ConstraintLayout cl_payy, cl_ca_number, pay_amount, btn_pay, cl_pay_amount;
    private ArrayList<String> conIds = new ArrayList<>();
    private List<BannerModal> listSchemes = new ArrayList<>();
    String conid = "x";
    String mobno = "0";
    private TextView tv_bill_month_val, pay_amounts, tv_due;
    String spinnerId = "";
    List<BannerModal> bannerModals = new ArrayList<BannerModal>();
    private ProgressDialog progressDialog;
    List<OfferSchemModal> offerSchemModals = new ArrayList<OfferSchemModal>();
    private CardView cardview_offer, payment_cen, cardview_about_us;
    private boolean isSync = false;
    float dX;
    float dY;
    int lastAction;
    FrameLayout frame_layout;
    LottieAnimationView animation_view;

    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE
    };
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private SharedPreferences permissionStatus;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private ImageView iv_image;
    private TextView language;
    private TextView tv_name;
    private String conName = "";
    private CardView cardview_document;
    boolean doubleBackToExitPressedOnce = false;
    private String tilesName, clickData, clickTime;
    private List<ModalStatics> modStaics = new ArrayList<>();
    private String currentDate = "";
    private String currentTimeHour = "";
    GifImageView gif_image;
    private boolean called = false;
    private int count = 0;
    private FrameLayout frame_number;
    private TextView tv_number;
    private String mobileNumberPay = "";
    private String CANumberPay = "";
    private String emailPay = "";
    private TextView tv_my_Account;
    private String version = "";
    private ImageView iv_ca_drop;
    private CardView cardview_apply;
    private boolean fromDropDown = false;
    private boolean dashBoardClick = false;
    View dragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        dragView = findViewById(R.id.fab);
        frame_layout = findViewById(R.id.frame_layout);
        dragView.setOnTouchListener(this);
        animation_view = findViewById(R.id.animation_view);

        animation_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertHit("Chat");
                dashBoardClick = true;
                Intent i = new Intent(context, ChatActivity.class);
                i.putExtra("CA", conIds);
                i.putExtra("CONSUMER_NO", conid);
                i.putExtra("MOBILE_NO", mobno);
                i.putExtra("CONSUMER_NAME", conName);
                i.putExtra("SCHEMES_ARRAY", new Gson().toJson(listSchemes));
                startActivity(i);
            }
        });

        iv_ca_drop = findViewById(R.id.iv_ca_drop);
        cardview_outage = findViewById(R.id.cardview_outage);
        //  card_payment=findViewById(R.id.card_payment);
        card_my_account = findViewById(R.id.card_my_account);
        card_register_complain = findViewById(R.id.card_register_complain);
        cardview_register_theft = findViewById(R.id.cardview_register_theft);
        cardview_safety = findViewById(R.id.cardview_safety);
        cardview_employee_verify = findViewById(R.id.cardview_employee_verify);
        cardview_feedback = findViewById(R.id.cardview_feedback);
        btn_pay = findViewById(R.id.btn_pay);
        cl_pay_amount = findViewById(R.id.cl_pay_amount);
        spinner_ca = findViewById(R.id.spinner_ca);
        cardview_offer = findViewById(R.id.cardview_offer);
        payment_cen = findViewById(R.id.payment_cen);
        tv_bill_month_val = findViewById(R.id.tv_bill_month_val);
        pay_amounts = findViewById(R.id.pay_amounts);
        tv_due = findViewById(R.id.tv_due);
        cardview_about_us = findViewById(R.id.cardview_about_us);
        cl_payy = findViewById(R.id.cl_payy);
        cl_ca_number = findViewById(R.id.cl_ca_number);
        pay_amount = findViewById(R.id.pay_amount);
        iv_image = findViewById(R.id.iv_image);
        language = findViewById(R.id.language);
        tv_name = findViewById(R.id.tv_name);
        frame_number = findViewById(R.id.frame_number);
        tv_number = findViewById(R.id.tv_number);
        tv_my_Account = findViewById(R.id.tv_my_Account);
        layout = findViewById(R.id.layout);
        cardview_apply = findViewById(R.id.cardview_apply);
        fromDropDown = false;
        dashBoardClick = false;

        //Toast.makeText(context, "Select Position" + CommonMethods.getSelectedPosition(context), Toast.LENGTH_SHORT).show();


        cardview_document = findViewById(R.id.cardview_document);
        gif_image = findViewById(R.id.gif_image);
        gif_image.setVisibility(View.GONE);
        frame_number.setVisibility(View.GONE);
        databaseAccess = DatabaseAccess.getInstance(context);

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String languae = sharedPreferences.getString("My_Lang", "");

        assert languae != null;
        if (languae.equalsIgnoreCase("hi")) {
            language.setText("हिन्दी");
        } else if (languae.equalsIgnoreCase("or")) {
            language.setText("ଓଡିଆ");
        } else {
            language.setText("ENG");
        }

        if (CommonMethods.isNetworkAvailable(MainActivity.this)) {
            fetchBanner();

            //  fetchBanner();
        } else {

            setBanner();
            //Toast.makeText(MainActivity.this, "No internet connection,please connect to internet & try again", Toast.LENGTH_SHORT).show();
        }


        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] listItem = {"English", "हिन्दी", "ଓଡିଆ"};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("Choose Language");
                mBuilder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        if (i == 0) {
                            setLocale("en");
                            language.setText("ENG");
                            restartActivity();
                        } else if (i == 1) {
                            setLocale("hi");
                            language.setText("हिन्दी");
                            restartActivity();
                        } else if (i == 2) {
                            setLocale("or");
                            language.setText("ଓଡିଆ");
                            restartActivity();
                        }
                        dialog.dismiss();
                    }


                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }


        });


        // LinearLayout fab = findViewById(R.id.fab);
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        checkPermissions();

        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval = sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl = "http://portal.tpcentralodisha.com:8070" + "/IncomingSMS/CESU_BillInfo.jsp?";

        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);

        CompanyID = sessiondata.getString("CompanyID", null); // getting String

        if (CompanyID == null) {
            CompanyID = "10";
        }


    /*    fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashBoardClick=true;

                insertHit("Chat");
                *//*if (conIds.size() > 0) {
                    Intent i = new Intent(context, ChatActivity.class);
                    i.putExtra("CA", conIds);
                    i.putExtra("CONSUMER_NO", conid);
                    i.putExtra("MOBILE_NO", mobno);
                    i.putExtra("CONSUMER_NAME", conName);
                    i.putExtra("SCHEMES_ARRAY", new Gson().toJson(listSchemes));
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this,"Please login first to initiate chat",Toast.LENGTH_SHORT).show();
                }*//*
                Intent i = new Intent(context, ChatActivity.class);
                i.putExtra("CA", conIds);
                i.putExtra("CONSUMER_NO", conid);
                i.putExtra("MOBILE_NO", mobno);
                i.putExtra("CONSUMER_NAME", conName);
                i.putExtra("SCHEMES_ARRAY", new Gson().toJson(listSchemes));
                startActivity(i);
            }
        });*/

        try{
            databaseAccess.open();
            strSelectSQL_01 = null;
            cursor = null;
            strSelectSQL_01 = "SELECT TILES_NAME,CLICK_DATE,CLICK_TIME " +
                    "FROM STATISTICS_DATA";
            cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
            Log.d("DemoApp", "Query SQL " + strSelectSQL_01);

            while (cursor.moveToNext()) {
                tilesName = cursor.getString(0);
                clickData = cursor.getString(1);
                clickTime = cursor.getString(2);

                ModalStatics modalStatics = new ModalStatics();
                modalStatics.setTileName(tilesName);
                modalStatics.setClickDate(clickData);
                modalStatics.setClickTime(clickTime);
                modStaics.add(modalStatics);

                Log.d("DemoApp", "in Loop conid" + tilesName + clickData + clickTime);
            }
            cursor.close();
            databaseAccess.close();
        }catch(Exception e){}




        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, conIds);
        aa.setDropDownViewResource(R.layout.spinner_item);
        //Setting the ArrayAdapter data on the Spinner
        if (conIds.size() > 0) {
            spinner_ca.setAdapter(aa);
            spinner_ca.setOnItemSelectedListener(this);
            if (!CommonMethods.getSelectedPosition(context).isEmpty()) {
                spinner_ca.setSelection(Integer.parseInt(CommonMethods.getSelectedPosition(context)));
            }
        }

        // if (conIds.size() > 0) {
        try {
            iv_ca_drop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(spinner_ca.getSelectedItem() == null) { // user selected nothing...
                    spinner_ca.performClick();
                    // }
                }
            });
        } catch (Exception e) {

        }
        //  }


        if (!conid.equals("x")) {
            cl_payy.setVisibility(View.GONE);
            cl_ca_number.setVisibility(View.VISIBLE);
            pay_amount.setVisibility(View.VISIBLE);
            //fetchInformation();
        } else {
            cl_payy.setVisibility(View.VISIBLE);
            cl_ca_number.setVisibility(View.GONE);
            pay_amount.setVisibility(View.GONE);
        }


        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent QDashboard = new Intent(getApplicationContext(), InstantPaymentActivity.class);
                QDashboard.putExtra("ca", "");
                QDashboard.putExtra("mob", "");
                QDashboard.putExtra("email", "");

                startActivity(QDashboard);

                insertHit("Pay");
            }
        });

        cardview_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent QDashboard = new Intent(getApplicationContext(), LandingActivityConnection.class);
                startActivity(QDashboard);
                insertHit("New_Connection");
            }
        });

        cl_pay_amount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dashBoardClick = true;

                if (mobileNumberPay == null) {
                    mobileNumberPay = "";
                }

                CommonMethods.loadPaymentPage(MainActivity.this, CANumberPay, mobileNumberPay);
                try {
                    insertHit("Pay");
                } catch (Exception e) {

                }
             /*   Intent QDashboard = new Intent(getApplicationContext(), InstantPaymentActivity.class);
                QDashboard.putExtra("ca",CANumberPay);
                QDashboard.putExtra("mob",mobileNumberPay);
                QDashboard.putExtra("email","");
                insertHit("Pay");
                startActivity(QDashboard);*/

            }
        });
        cardview_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;

                offerSchemModals.clear();
                offerSchemModals.addAll(GlobalVariable.offerSchemModals);
                Intent QDashboard = new Intent(getApplicationContext(), OfferSchemeActivity.class);
                //  QDashboard.putExtra("mylist", (Serializable) offerSchemModals);

                startActivity(QDashboard);
                insertHit("Offer");

            }
        });

        payment_cen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent QDashboard = new Intent(getApplicationContext(), PaymentCentreActivity.class);
                startActivity(QDashboard);
                insertHit("Offices");
            }
        });

        cardview_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent QDashboard = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(QDashboard);
                insertHit("AboutUs");
            }
        });

        cardview_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent QDashboard = new Intent(getApplicationContext(), DocumentActivity.class);
                startActivity(QDashboard);
                insertHit("Document");
            }
        });


      /*  card_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QDashboard = new Intent(getApplicationContext(), InstantPaymentActivity.class);
                startActivity(QDashboard);
            }
        });*/

        card_register_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent intent = new Intent(getApplicationContext(), ComplaintLoginActivity.class);
                startActivity(intent);
                insertHit("ComplainRegister");
            }
        });

        cardview_outage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent curblintent = new Intent(getApplicationContext(), OutageInfoActivity.class);
                Bundle reqDet = new Bundle();
                reqDet.putString("Typeinfo", "qlinks");
                curblintent.putExtras(reqDet);
                startActivity(curblintent);
                insertHit("Outage");
                //  finish();
            }
        });

        cardview_register_theft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent intent = new Intent(getApplicationContext(), TheftRegisterActivity.class);
                startActivity(intent);
                insertHit("Theft");
            }
        });

        cardview_safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent intent = new Intent(getApplicationContext(), SafetyActivity.class);
                startActivity(intent);
                insertHit("Safety");
            }
        });
        cardview_employee_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent intent = new Intent(getApplicationContext(), EmployeeVerificationActivity.class);
                startActivity(intent);
                insertHit("VerifyEmp");
            }
        });

        cardview_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent intent = new Intent(getApplicationContext(), FeedBackActivity.class);
                startActivity(intent);
                insertHit("Feedback");
            }
        });
        card_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashBoardClick = true;
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                insertHit("MyAcoount");

            }
        });

        try{
            insertHit("MAIN-V2.1");
        }catch (Exception e){}


    }

    private void fetchInformation() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            changeTextStatus(true);
        } else {
            changeTextStatus(false);
        }
    }

    public void changeTextStatus(boolean isConnected) {

        // Change status according to boolean value
        if (isConnected) {
            imeinum = CommonMethods.getUniqueNumber(mobval);//Hardcoded by Narendra
            AuthURL = StrUrl + "un=TEST&pw=TEST&CompanyID=" + CompanyID + "&ConsumerID=" + spinnerId + "&imei=" + imeinum + "&mosarkar=0&mobile_no=" + mobno;
            new UserAuthOnline().execute(AuthURL);
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
                            dialog.cancel();
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
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Toast.makeText(getApplicationContext(),country[position] , Toast.LENGTH_LONG).show();
        CommonMethods.saveSelectedPosition(context, "" + position);

        spinnerId = conIds.get(position);

        //position

        CANumberPay = conIds.get(position);
        if (!conid.equals("x")) {

            if (CommonMethods.isNetworkAvailable(MainActivity.this)) {
                fromDropDown = true;
                fetchInformation();

            } else {
                Toast.makeText(MainActivity.this, "No intenet connection,please connect to internet", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = dragView.getX() - event.getRawX();
                dY = dragView.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                //break;
                return true;
            case MotionEvent.ACTION_MOVE:
                dragView.setY(event.getRawY() + dY);
                dragView.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN) {
                    try {
                        insertHit("Chat");
                    }catch (Exception e){}
                    dashBoardClick = true;
                    Intent i = new Intent(context, ChatActivity.class);
                    i.putExtra("CA", conIds);
                    i.putExtra("CONSUMER_NO", conid);
                    i.putExtra("MOBILE_NO", mobno);
                    i.putExtra("CONSUMER_NAME", conName);
                    i.putExtra("SCHEMES_ARRAY", new Gson().toJson(listSchemes));
                    startActivity(i);
                }
                return true;

            default:
                return false;
        }
        layout.invalidate();
        return true;
    }

    private class UserAuthOnline extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            String bodycontent = null;
            try {
                String strURL = params[0];

                URLConnection conn = null;
                InputStream inputStreamer = null;

                Log.d("DemoApp", " strURL   " + strURL);
                try {

                    URL url = new URL(strURL);
                    URLConnection uc = url.openConnection();
                    uc.setDoInput(true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String inputLine;
                    StringBuilder a = new StringBuilder();
                    while ((inputLine = in.readLine()) != null)
                        a.append(inputLine);
                    in.close();
                    Log.d("DemoApp", " fullString   " + a.toString());
                    String html = a.toString();
                    int start = html.indexOf("<body>") + "<body>".length();
                    int end = html.indexOf("</body>", start);
                    bodycontent = html.substring(start, end);
                    Log.d("DemoApp", " start   " + start);
                    Log.d("DemoApp", " end   " + end);
                    Log.d("DemoApp", " body   " + bodycontent);
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Not Connectd to Server");
                    alertDialogBuilder.setMessage("Please Retry")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //activity = (MainActivity)params[0];


            return bodycontent;
        }

        @Override

        protected void onPreExecute() {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

                if ((fromDropDown) && (!dashBoardClick)) {
                    progressDialog = ProgressDialog.show(MainActivity.this, "Fetching Data", "Please Wait:: connecting to server");
                }

                //  progressDialog = ProgressDialog.show(InstantPaymentActivity.this, " ", " ");
                //spinner.setVisibility(View.VISIBLE);
            } else {
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
        protected void onPostExecute(String str) {
            try {

                if ((fromDropDown) && (!dashBoardClick)) {
                    progressDialog.dismiss();
                }
                fromDropDown = false;
                dashBoardClick = false;


                Log.d("DemoApp", " str   " + str);
                //  progressDialog.dismiss();
                //spinner.setVisibility(View.GONE);
                String pipeDelBillInfo = str;
                // pipeDelBillInfo="1|10|santirnja|102S34343|1000|12-09-2019|580|03-08-2019|2555"; //curbill


                String[] BillInfo = pipeDelBillInfo.split("[|]");
                // Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check
                // Log.d("DemoApp", " BillInfo[2]   " +pipeDelBillInfo);//authoriztion check
                if (BillInfo[0].equals("0")) {
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
                                    dialog.cancel();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                    Log.d("DemoApp", "[4]   ");
                } else if (BillInfo[1].equals("0")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("IP Address Not Found");
                    alertDialogBuilder.setMessage("IP Address Not Found")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                    Log.d("DemoApp", "[4]   ");
                } else if (BillInfo[2].equals("0")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Consumer Not Found");
                    alertDialogBuilder.setMessage("Consumer Not Found")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                    Log.d("DemoApp", "[4]   ");
                }/*else if(BillInfo[3].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Current Bill Not Found");
                alertDialogBuilder.setMessage("Current Bill Not Found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                enableDisableSubmitButton(true);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                InstantPaymentActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }*/ else {//curbill

                    String stroffcode = "";
                    stroffcode = BillInfo[21];
                    Log.d("DemoApp", "[4]   ");
                    if (BillInfo[22].equals("1")) {
                        stroffcode = stroffcode + "S";
                    } else {
                        stroffcode = stroffcode + "I";
                    }
                    Log.d("DemoApp", "BillInfo[22]  " + BillInfo[22]);
                    //   strName.setText(BillInfo[8] + " (" + stroffcode + BillInfo[7] + ")");
                    //strconid.setText(BillInfo[7]);

                    int bltotal = 0;
                    int payamt = 0;
                    int arrear = 0;
                    bltotal = (int) (Double.parseDouble(BillInfo[25]));
                    payamt = (int) (Double.parseDouble(BillInfo[12]));
                    arrear = (int) (Double.parseDouble(BillInfo[28]));
                    bltotal = Math.round(bltotal);
                    payamt = Math.round(payamt);
                    arrear = Math.round(arrear);
                    int minpay;
                    if (payamt >= bltotal) {
                        if (arrear <= 0) {
                            minpay = payamt;
                        } else {
                            // minpay=payamt-Math.round((arrear/1)); change by santi 17/04/20
                            minpay = payamt;
                        }
                    } else {
                        minpay = payamt;
                    }
                    minpay = payamt;
               /* strminpay.setText(Integer.toString(minpay));
                strPayamount.setText(BillInfo[12]);//*/


                    if (BillInfo[12] != null) {
                        pay_amounts.setText(BillInfo[12]);
                    } else {
                        pay_amounts.setText("");

                    }
                    // strPayAmt.setText(BillInfo[12]);
                    //Modify on 040520 by Santi
                    // strPayAmt.setTextIsSelectable(true); //disable on 040520 by Santi
                    // strPayAmt.setFocusable(true);//disable on 040520 by Santi
                    // strPayAmt.setFocusableInTouchMode(true);//disable on 040520 by Santi
                    // strPayAmt.setClickable(true);//disable on 040520 by Santi
                    // strPayAmt.setLongClickable(true);//disable on 040520 by Santi
                    //End////
               /* strcbill.setText(BillInfo[27]);//current bill
                strarrear.setText(BillInfo[28]);
                strbdate.setText(BillInfo[16]);
                strdudt.setText(BillInfo[17]);
                strrbt.setText(BillInfo[26]);*/

                    if (BillInfo[17] != null) {
                        tv_due.setText(BillInfo[17]);
                    } else {
                        tv_due.setText("");
                    }
                    if (BillInfo[16] != null) {
                        tv_bill_month_val.setText(BillInfo[16]);
                    } else {
                        tv_bill_month_val.setText("");

                    }
                    if (BillInfo[8] != null) {

                        tv_name.setText(BillInfo[8]);
                    } else {
                        tv_name.setText("");

                    }


                    //strpmt.setText(BillInfo[23]);
              /*  strpmt.setText(BillInfo[29]);//Added on 040520 by santi
                if(BillInfo[24]==null || BillInfo[24].equals("") || BillInfo[24].equals(" ") || BillInfo[24].equals("-")){
                    strpmtdt.setText("-");
                }else{
                    if(!BillInfo[24].equalsIgnoreCase("null")) {
                        strpmtdt.setText(BillInfo[24].substring(0, 11));//.substring(0,10)
                    }else{
                        strpmtdt.setText("NA");
                    }
                }
                //instantiate popup window
                popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                strPayAmt.setCursorVisible(false); //added on 040520 by santi
                //added on 040520 by santi
                if(strPayAmt.getText().toString().equals("0"))
                {
                    payBtn.setEnabled(false);
                    payBtn.setBackgroundColor(Color.parseColor("#999999"));
                    payBtn.setClickable(false);
                }else{
                    payBtn.setEnabled(true);
                    payBtn.setClickable(true);
                }*/

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void fetchBanner() {
        String url = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/GetBannerDetail?checksum=01091981";

        if (!called) {
            new fetchBannerData().execute(url);
        }
        called = true;


        System.out.println("called===");
    }

    private void checkSync() {
        String url = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/TPCODL_ConsumerApp_Verson?checksum=01091981";

        new checkSyncData().execute(url);
    }

    private class fetchBannerData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL = params[0];
            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent = null;
            Log.d("DemoApp", " strURL banner  " + strURL);

            try {
                URL url = null;
                url = new URL(strURL);

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                Element element = doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("Table");


                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node node = nList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        BannerModal bannerModal = new BannerModal();

                        bannerModal.setId(getValue("ID", element2));
                        bannerModal.setImageName(getValue("IMAGE_NAME", element2));
                        bannerModal.setImagePath(getValue("IMAGE_PATH_URL", element2));
                        bannerModal.setBannerType(getValue("BANNER_TYPE", element2));

                        if (getValue("BANNER_TYPE", element2).equalsIgnoreCase("SCHEME")) {

                            try {
                                bannerModal.setImageClickUrl(getValue("IMAGE_CLICK_URL", element2));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        bannerModal.setStartDate(getValue("START_DATE", element2));
                        bannerModal.setEnddate(getValue("END_DATE", element2));
                        bannerModals.add(bannerModal);
                    }
                }

              /*  if(nList.getLength()>=0){
                    CommonMethods.saveSharedPreferencesLogList(MainActivity.this, bannerModals);
                }*/


            } catch (Exception e) {
                e.printStackTrace();
            }

            return bodycontent;
        }

        @Override

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, "Fetching Data", "Please Wait:: connecting to server");

            ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Enable Data");
                alertDialogBuilder.setMessage("Enable Data & Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
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
        protected void onPostExecute(String str) {

            try {
                Log.d("DemoApp", " str   " + str);
                progressDialog.dismiss();

                setBanner();
                currentDate = getCurrentDateAndTime();

                System.out.println("zsdfgh==" + currentDate);
                currentTimeHour = getCurrentHour();
                Date currentDates = getDateFromString(currentDate);
                Date dbDate = null;
                for (int i = 0; i < modStaics.size(); i++) {

                    dbDate = getDateFromString(modStaics.get(i).getClickDate());

                    if (currentDates.after(dbDate)) {

                        String url = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Add_App_STATISTICS?checksum=" + "01091981" + "&TILE_NAME=" + modStaics.get(i).getTileName() + "&LOG_DATE=" + modStaics.get(i).getClickDate() + "&LOG_HOUR=" + modStaics.get(i).getClickTime();


                        new sendStatisticData().execute(url, modStaics.get(i).getTileName(), modStaics.get(i).getClickDate(), modStaics.get(i).getClickTime());
                        // upload will call
                    } else if (currentDates.equals(dbDate)) {

                        if (Integer.parseInt(currentTimeHour) > Integer.parseInt(modStaics.get(i).getClickTime())) {

                            String url = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/Add_App_STATISTICS?checksum=" + "01091981" + "&TILE_NAME=" + modStaics.get(i).getTileName() + "&LOG_DATE=" + modStaics.get(i).getClickDate() + "&LOG_HOUR=" + modStaics.get(i).getClickTime();

                            new sendStatisticData().execute(url, modStaics.get(i).getTileName(), modStaics.get(i).getClickDate(), modStaics.get(i).getClickTime());
                        }
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }

    }


    private class checkSyncData extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL = params[0];
            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent = null;
            Log.d("DemoApp", " strURL   " + strURL);

            try {
                URL url = null;
                url = new URL(strURL);


                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

                version = doc.getFirstChild().getTextContent();
                System.out.println("sdfgt==" + version);

                //  isSync = Boolean.parseBoolean(doc.getFirstChild().getTextContent());

                //  String[] res = doc.getFirstChild().getTextContent().split("[#]", 0);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return bodycontent;
        }

        @Override

        protected void onPreExecute() {

            ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                //  progressDialog = ProgressDialog.show(MainActivity.this, "Fetching Data", "Please Wait:: connecting to server");

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Enable Data");
                alertDialogBuilder.setMessage("Enable Data & Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
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
        protected void onPostExecute(String str) {

            try {
                if ((version != null) || (version.equalsIgnoreCase(""))) {

                    if (!(CommonMethods.getRemoteVersionNumber(MainActivity.this).equalsIgnoreCase(version))) {
                        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View customView = layoutInflater.inflate(R.layout.activity_popup, null);
                        closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
                        UpdateSwBtn = (TextView) customView.findViewById(R.id.UpdateSwBtn);
                        //instantiate popup window
                        popupWindow = new PopupWindow(customView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                        //display the popup window
                        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                        //close the popup window on button click
                        UpdateSwBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getAppLink(getApplicationContext()))));
                                popupWindow.dismiss();
                            }
                        });
                        closePopupBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Log.d("DemoApp", " str   " + str);
            //   progressDialog.dismiss();


            //fetChImage(empId);
        }

    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public void setBanner() {
        images.clear();
        GlobalVariable.offerSchemModals.clear();

        if (bannerModals.size() == 0) {
            bannerModals.addAll(CommonMethods.loadSharedPreferencesLogList(MainActivity.this));
        } else if ((bannerModals.size() == 1) && (bannerModals.get(0).getBannerType().equalsIgnoreCase("SCHEME"))) {
            bannerModals.clear();
            bannerModals.addAll(CommonMethods.loadSharedPreferencesLogList(MainActivity.this));

        }

        for (int i = 0; i < bannerModals.size(); i++) {

            if (bannerModals.get(i).getBannerType().equalsIgnoreCase("HEADER")) {
                SliderItem sliderItem = new SliderItem();
                sliderItem.setImageUrl(bannerModals.get(i).getImagePath());
                sliderItem.setDescription(bannerModals.get(i).getImageName());
                images.add(sliderItem);

                CommonMethods.saveSharedPreferencesLogList(MainActivity.this, bannerModals);
            } else {
                count = count + 1;
                tv_number.setText("" + count);
                OfferSchemModal offerSchemModal = new OfferSchemModal();
                offerSchemModal.setId(bannerModals.get(i).getId());
                offerSchemModal.setImageName(bannerModals.get(i).getImageName());
                offerSchemModal.setImagePath(bannerModals.get(i).getImagePath());
                offerSchemModal.setImageClickUrl(bannerModals.get(i).getImageClickUrl());

                gif_image.setVisibility(View.VISIBLE);
                frame_number.setVisibility(View.VISIBLE);
                GlobalVariable.offerSchemModals.add(offerSchemModal);
                listSchemes.add(new BannerModal(bannerModals.get(i).getImagePath(), bannerModals.get(i).getImageClickUrl(), ""));
            }
        }

        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapterExample adapter = new SliderAdapterExample(MainActivity.this, images);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
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
                            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
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


                break;
        }
    }

    private void setLocale(String en) {
        Locale locale = new Locale(en);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", en);
        editor.apply();


    }

    public void loadLocale() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String languae = sharedPreferences.getString("My_Lang", "");

        System.out.println("asdfg==" + languae);

        setLocale(languae);
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadLocale();
        checkSync();  // version control

        getLoginUser();

    }

    private void getLoginUser() {
        String ownerAccount = "";
        databaseAccess.open();
        strSelectSQL_01 = null;
        cursor = null;
        strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO,CONSUMER_NAME " +
                "FROM CONSUMERDTLS WHERE VALIDATE_FLG='1'";
        cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
        conIds.clear();
        while (cursor.moveToNext()) {
            ownerAccount = cursor.getString(0);
            Log.d("DemoApp", "owner" + ownerAccount);
        }
        cursor.close();
        databaseAccess.close();

        if ((ownerAccount != null) || (!(ownerAccount.equalsIgnoreCase("")))) {
            getData();
        }

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public static String getCurrentDateAndTime() {
        Format f = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = f.format(new Date());
        System.out.println("Current Date = " + strDate);
        return strDate;
    }

    public static String getCurrentHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String str = sdf.format(new Date());
        return str;
    }

    public void insertHit(String tilesName) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String str = sdf.format(new Date());

        try {

            databaseAccess.open();
            String strSelectSQL_01 = "INSERT INTO STATISTICS_DATA  " +
                    " (TILES_NAME,CLICK_DATE,CLICK_TIME)" +

                    " VALUES('" + tilesName + "','" + getCurrentDateAndTime() + "','" + str + "') ";

            //  " VALUES('" + "PAY" + "','" + str + "',9,'" + BillInfo[3] + "')" ;

            Log.d("DemoApp", "strSelectSQL_01" + strSelectSQL_01);
            DatabaseAccess.database.execSQL(strSelectSQL_01);
            databaseAccess.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public Date getDateFromString(String dates) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        try {
            date = format.parse(dates);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private class sendStatisticData extends AsyncTask<String, Integer, String> {

        String insertDate = "";
        String insertTiles = "";
        String insertTime = "";

        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL = params[0];
            insertDate = params[2];
            insertTiles = params[1];
            insertTime = params[3];


            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent = null;
            Log.d("DemoApp", " strURL   " + strURL);

            try {
                URL url = null;
                url = new URL(strURL);


                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

                String res = doc.getFirstChild().getTextContent();

                if (res.equalsIgnoreCase("1")) {
                    databaseAccess.open();
                    strSelectSQL_01 = null;

                    DatabaseAccess.database.execSQL("DELETE FROM " + "STATISTICS_DATA" + " WHERE " + "TILES_NAME" + "= '" + insertTiles + "'" + " AND" + " CLICK_DATE" + "='" + insertDate + "'" + " AND" + " CLICK_TIME" + "= '" + insertTime + "'");


                 /*   strSelectSQL_01 =
                            "DELETE FROM STATISTICS_DATA where TILES_NAME=" + insertTiles + " AND" + " CLICK_DATE=" + insertDate + " AND " + "CLICK_TIME=" + insertTime;
                    DatabaseAccess.database.execSQL(strSelectSQL_01);*/

                    Log.d("DemoApp", "Query SQL " + strSelectSQL_01);

                    databaseAccess.close();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return bodycontent;
        }

        @Override

        protected void onPreExecute() {


            ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            }
        }

        @Override
        protected void onPostExecute(String str) {


            // fetChImage(empId);
        }

    }

    public void getData() {
        String ownerAccount = "";
        databaseAccess.open();
        strSelectSQL_01 = null;
        cursor = null;

        strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO,CONSUMER_NAME " + "FROM CONSUMERDTLS WHERE VALIDATE_FLG=1 OR VALIDATE_FLG=9";
        cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
        conIds.clear();

        while (cursor.moveToNext()) {
            conid = cursor.getString(0);
            conIds.add(conid);
            mobno = cursor.getString(1);
            conName = cursor.getString(2);
            mobileNumberPay = mobno;
            Log.d("DemoApp", "in Loop conid" + conid);
        }

        cursor.close();
        databaseAccess.close();

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, conIds);
        aa.setDropDownViewResource(R.layout.spinner_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_ca.setAdapter(aa);
        spinner_ca.setOnItemSelectedListener(this);

        if (conIds.size() > 0) {
            if (!CommonMethods.getSelectedPosition(context).isEmpty()) {
                spinner_ca.setSelection(Integer.parseInt(CommonMethods.getSelectedPosition(context)));
            }
        }

        try {
            //if (conIds.size() > 0) {
            iv_ca_drop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(spinner_ca.getSelectedItem() == null) { // user selected nothing...
                    spinner_ca.performClick();
                    // }
                }
            });
        } catch (Exception e) {

        }
        // }


        if ((!conid.equals("x"))) {

            cl_payy.setVisibility(View.GONE);
            cl_ca_number.setVisibility(View.VISIBLE);
            pay_amount.setVisibility(View.VISIBLE);
            tv_my_Account.setText(MainActivity.this.getString(R.string.my_account));
            //fetchInformation();
        } else {
            cl_payy.setVisibility(View.VISIBLE);
            cl_ca_number.setVisibility(View.GONE);
            pay_amount.setVisibility(View.GONE);
            tv_my_Account.setText(MainActivity.this.getString(R.string.register));

        }

    }

}

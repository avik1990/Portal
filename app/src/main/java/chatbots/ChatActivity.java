package chatbots;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.cesu.itcc05.consumeportal.CommonMethods;
import com.cesu.itcc05.consumeportal.ComplainModal;
import com.cesu.itcc05.consumeportal.ComplainModalSubCategory;
import com.cesu.itcc05.consumeportal.ComplainRegisterActivity;
import com.cesu.itcc05.consumeportal.ComplaintLoginActivity;
import com.cesu.itcc05.consumeportal.ConsumerMenuActivity;
import com.cesu.itcc05.consumeportal.DatabaseAccess;
import com.cesu.itcc05.consumeportal.HistoryBillActivity;
import com.cesu.itcc05.consumeportal.HistoryConsumptionActivity;
import com.cesu.itcc05.consumeportal.HistoryPaymentActivity;
import com.cesu.itcc05.consumeportal.HistoryPaymentBillActivity;
import com.cesu.itcc05.consumeportal.InstantPaymentActivity;
import com.cesu.itcc05.consumeportal.R;
import com.cesu.itcc05.consumeportal.ViewPDFActivity;
import com.cesu.itcc05.consumeportal.modal.BannerModal;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import chatbots.adapter.ChatAdapter;
import chatbots.extras.ConnectionDetector;
import chatbots.extras.Utils;
import chatbots.model.AnswerResponse;
import chatbots.model.ChatResponseModel;
import chatbots.model.QuestionResponse;
import chatbots.retrofit.ApiInterface;
import chatbots.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.GetQuestionId, ChatAdapter.GetPDFPath, ChatAdapter.GetMapCoordinates, View.OnClickListener, ChatAdapter.GetLikeDislike {

    RecyclerView rvRecycler;
    AutoCompleteTextView etChatView;
    Context context;
    List<ChatResponseModel> list_discoveryInstantSearchModel = new ArrayList<>();
    List<QuestionResponse> questionResponse;
    AnswerResponse answerResponse;
    List<AnswerResponse> answerResponseLocations;
    List<String> answerResponsePhone = new ArrayList<>();
    private String AuthURL1 = null;
    private String docURL = "";
    private String doc_name = "";
    ChatAdapter adapter;
    ConnectionDetector cd;
    int lastCount = 0;
    int currentCount = 0;
    int counter = 0;
    ImageView ivMenu;
    ImageView iv_back;
    private String StrUrl1 = "";
    ChatAdapter.GetMapCoordinates coordinates;
    ChatAdapter.GetPDFPath getPDFPath;
    ChatAdapter.GetLikeDislike getLikeDislike;
    List<String> listCA = new ArrayList<>();
    String MOBILE_NO = "";
    String stCA_NO = "";
    long downloadId;
    ArrayList<Long> list = new ArrayList<>();
    List<BannerModal> listSchemes = new ArrayList<>();
    List<BannerModal> listImages = new ArrayList<>();
    Button btnFile;
    File myDirectory;
    String BACKUP_FOLDER_PATH = "/chatbots/Backup";
    private String StrUrl = "";
    private String CompanyID = "";
    private String conname = "";
    private String consIDval = "";
    private String imeinum = "";
    private String consacc = "";
    private String divcode = "";
    private String dbtype = "";
    private String AuthURL = null;
    int procType = -1;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetBehavior bottomSheetBehavior;
    View bottomSheetView;
    ImageView ivQmenu;
    private DatabaseAccess databaseAccess = null;
    private String catDat = "";
    private List<ComplainModal> complainModals = new ArrayList<>();
    private List<ComplainModalSubCategory> complainModalSubCategories = new ArrayList<>();
    Spinner spCategory, spSubCategory;
    private List<String> catList = new ArrayList<>();
    private String spin2posID = "";
    String answerURL;
    String userEmail = "", userPhone = "", userAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dashboard);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        context = this;
        cd = new ConnectionDetector(context);
        coordinates = this;
        getPDFPath = this;
        getLikeDislike = this;
        databaseAccess = DatabaseAccess.getInstance(context);
        listCA.clear();
        listSchemes.clear();
        answerURL = "http://122.185.188.231/TPCODLCONNECTSERVICE/TPCODLConnectService.asmx/chatbot_Answers?checksum=01091981&id=";
        StrUrl = "http://portal.tpcentralodisha.com:8070";
        StrUrl1 = "http://portal.tpcentralodisha.com:8070" + "/ePortalAPP/ePortal_App.jsp?";
        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
        imeinum = sessiondata.getString("imeinum", null); // getting String
        CompanyID = sessiondata.getString("CompanyID", null); // getting String
        imeinum = "102030405060708";
        if (CompanyID == null) {
            CompanyID = "10";
        }

        try {
            Type type = new TypeToken<List<BannerModal>>() {
            }.getType();
            listSchemes = new Gson().fromJson(getIntent().getStringExtra("SCHEMES_ARRAY"), type);
        } catch (Exception e) {
        }

        //CONSUMER_NO = getIntent().getExtras().getString("CONSUMER_NO");
        MOBILE_NO = getIntent().getExtras().getString("MOBILE_NO");
        //CONSUMER_NAME = getIntent().getExtras().getString("CONSUMER_NAME");
        listCA = getIntent().getExtras().getStringArrayList("CA");

        /*listCA.add("504I15415805");
        listCA.add("101I11118434");
        listCA.add("102I11218817");
        listCA.add("103I11331727");
        listCA.add("302I13248601");*/
        listCA.add("Other CA");

        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        if (MOBILE_NO == null) {
            MOBILE_NO = "";
        }

        //Log.e("Values", CONSUMER_NO + ":" + MOBILE_NO + ":" + CONSUMER_NAME + ":" + listCA.size());
        initViews();

        adapter = new ChatAdapter(context, this, coordinates, getPDFPath, getLikeDislike);

        if (cd.isConnected()) {
            updateQuestions();
            String json = Utils.getChatData(context);
            if (json.isEmpty()) {
                fetchQuestions();
            } else {
                try {
                    list_discoveryInstantSearchModel = (new Gson()).fromJson(json, new TypeToken<ArrayList<ChatResponseModel>>() {
                    }.getType());
                    lastCount = list_discoveryInstantSearchModel.size();
                    adapter.updateChatView(list_discoveryInstantSearchModel);
                    rvRecycler.setAdapter(adapter);
                    rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                } catch (Exception e) {

                }
            }
        } else {
            String json = Utils.getChatData(context);
            list_discoveryInstantSearchModel = (new Gson()).fromJson(json, new TypeToken<ArrayList<ChatResponseModel>>() {
            }.getType());
            adapter.updateChatView(list_discoveryInstantSearchModel);
            rvRecycler.setAdapter(adapter);
            rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
            lastCount = list_discoveryInstantSearchModel.size();
        }

        FetchUserDetails();
    }

    private void updateQuestions() {
        UpdateQuestions();
    }

    private void initViews() {
        ivQmenu = findViewById(R.id.ivQmenu);
        btnFile = findViewById(R.id.btnFile);
        iv_back = findViewById(R.id.iv_back);
        ivMenu = findViewById(R.id.ivMenu);
        rvRecycler = findViewById(R.id.rvRecycler);
        etChatView = findViewById(R.id.etChatView);
        ivMenu.setOnClickListener(this);
        inflateAutocompletetexView();
        iv_back.setOnClickListener(this);
        btnFile.setOnClickListener(this);
        ivQmenu.setOnClickListener(this);
    }

    private void inflateAutocompletetexView() {
        String json = Utils.getQuestionData(context);
        if (!json.trim().isEmpty()) {
            final String[] questions;
            questionResponse = (new Gson()).fromJson(json, new TypeToken<ArrayList<QuestionResponse>>() {
            }.getType());

            questions = new String[questionResponse.size()];

            for (int i = 0; i < questionResponse.size(); i++) {
                questions[i] = questionResponse.get(i).getqUESTION();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, questions);
            etChatView.setThreshold(1);
            etChatView.setAdapter(adapter);

            etChatView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                    String selection = (String) parent.getItemAtPosition(position);
                    int indexOfTwo = Arrays.asList(questions).indexOf(selection);
                    getQuestionID(questionResponse.get(indexOfTwo).getfLAG().toString(), questionResponse.get(indexOfTwo).getqUESTION(), String.valueOf(questionResponse.get(indexOfTwo).getiD()));
                    etChatView.setText("");
                }
            });
        }
    }


    private void UpdateQuestions() {
        Log.e("TestQuestions", "Fetching Questions");
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<List<QuestionResponse>> call = service.getAllQuestions();
        call.enqueue(new Callback<List<QuestionResponse>>() {

            @Override
            public void onResponse(Call<List<QuestionResponse>> call, Response<List<QuestionResponse>> response) {
                questionResponse = response.body();

                try {
                    if (questionResponse != null) {
                        Gson g = new Gson();
                        String json = g.toJson(questionResponse);
                        Utils.setQuestionData(context, json);
                        inflateAutocompletetexView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<QuestionResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void fetchQuestions() {
        Log.e("TestQuestions", "Fetching Questions");
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<List<QuestionResponse>> call = service.getAllQuestions();
        call.enqueue(new Callback<List<QuestionResponse>>() {

            @Override
            public void onResponse(Call<List<QuestionResponse>> call, Response<List<QuestionResponse>> response) {
                questionResponse = response.body();
                try {
                    if (questionResponse != null) {
                        Gson g = new Gson();
                        String json = g.toJson(questionResponse);
                        Utils.setQuestionData(context, json);
                        mergerData();
                        inflateAutocompletetexView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<QuestionResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void mergerData() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = sdf.format(c.getTime());

        list_discoveryInstantSearchModel.add(new ChatResponseModel(0, questionResponse, -1, strDate));
        adapter.updateChatView(list_discoveryInstantSearchModel);
        rvRecycler.setAdapter(adapter);
    }

    @Override
    public void getQuestionID(String flag, String text, String id) {
        if (Utils.getLastCounter(context).isEmpty()) {
            counter = 0;
        } else {
            counter = Integer.parseInt(Utils.getLastCounter(context));
        }
        Log.e("counterplus", "" + counter);
        if (counter >= 7) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String strDate = sdf.format(c.getTime());
            counter = 0;
            Utils.setLastCounter(context, String.valueOf(counter));
            String json = Utils.getQuestionData(context);
            questionResponse = (new Gson()).fromJson(json, new TypeToken<ArrayList<QuestionResponse>>() {
            }.getType());
            list_discoveryInstantSearchModel.add(new ChatResponseModel(0, questionResponse, -1, strDate));
            adapter.updateChatView(list_discoveryInstantSearchModel);
            rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
        if (flag.trim().equalsIgnoreCase("1")) {
            showAlertDialog("1");
        } else if (flag.equalsIgnoreCase("7")) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String strDate = sdf.format(c.getTime());
            counter++;
            Utils.setLastCounter(context, String.valueOf(counter));
            list_discoveryInstantSearchModel.add(new ChatResponseModel("url", ChatAdapter.ITEM_TYPE_USERQUESTION, "https://tinyurl.com/1615uf87", -1, strDate));
            adapter.updateChatView(list_discoveryInstantSearchModel);
            rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
        } else if (flag.equalsIgnoreCase("6")) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String strDate = sdf.format(c.getTime());
            answerResponsePhone.clear();
            answerResponsePhone.add("1912");
            answerResponsePhone.add("18003457122");
            counter++;
            Utils.setLastCounter(context, String.valueOf(counter));
            list_discoveryInstantSearchModel.add(new ChatResponseModel(ChatAdapter.ITEM_TYPE_PHONE, "", answerResponsePhone, -1, strDate));
            adapter.updateChatView(list_discoveryInstantSearchModel);
            rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
        } else if (flag.equalsIgnoreCase("3")) {
            showAlertDialog("3");
        } else if (flag.equalsIgnoreCase("9")) {
            prepareImageData();
        } else if (flag.equalsIgnoreCase("11")) {
            showAlertDialogConsumptionHistory("1");
        } else if (flag.equalsIgnoreCase("10")) {
            showAlertDialogpAYMENTHistory("1");
        } else if (flag.equalsIgnoreCase("12")) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String strDate = sdf.format(c.getTime());
            counter++;
            Utils.setLastCounter(context, String.valueOf(counter));
            list_discoveryInstantSearchModel.add(new ChatResponseModel("Fight", ChatAdapter.ITEM_TYPE_USERQUESTION, "During the COVID-19 Pandemic, TPCODL is committed to provide uninterrupted power supply to essential emergency establishments and all its customers.\nBe our strength in this crisis by paying your electricity bills through digital modes.", -1, strDate));
            adapter.updateChatView(list_discoveryInstantSearchModel);
            rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
        } else if (flag.equalsIgnoreCase("13")) {
            handleComplaint();
        } else {
            fetchAnswers(id, text);

        }
    }

    private void fetchAnswers(String ID, final String text) {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<List<AnswerResponse>> call = service.getAnswerResponse(ID);
        call.enqueue(new Callback<List<AnswerResponse>>() {

            @Override
            public void onResponse(Call<List<AnswerResponse>> call, Response<List<AnswerResponse>> response) {
                answerResponseLocations = response.body();
                //Log.e("RESPONSESSS", answerResponseLocations.get(0).getANSWER());
                try {
                    if (answerResponseLocations.get(0).getFLAG().equalsIgnoreCase("5") || answerResponseLocations.get(0).getFLAG().equalsIgnoreCase("12")) {
                        counter++;
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String strDate = sdf.format(c.getTime());
                        Utils.setLastCounter(context, String.valueOf(counter));
                        list_discoveryInstantSearchModel.add(new ChatResponseModel(text, ChatAdapter.ITEM_TYPE_USERQUESTION, answerResponseLocations.get(0).getANSWER(), -1, strDate));
                        adapter.updateChatView(list_discoveryInstantSearchModel);
                        rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                    } else if (answerResponseLocations.get(0).getFLAG().equalsIgnoreCase("16")) {
                        listImages.clear();

                        for (int i = 0; i < answerResponseLocations.size(); i++) {
                            listImages.add(new BannerModal(answerResponseLocations.get(i).getANSWER()));
                        }

                        if (listImages.size() > 0) {
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String strDate = sdf.format(c.getTime());

                            list_discoveryInstantSearchModel.add(new ChatResponseModel(listImages, ChatAdapter.ITEM_TYPE_SCHEMES, -1, strDate));
                            adapter.updateChatView(list_discoveryInstantSearchModel);
                            rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                            currentCount = list_discoveryInstantSearchModel.size();
                            counter++;
                            Utils.setLastCounter(context, String.valueOf(counter));

                            String json = Utils.getQuestionData(context);
                            questionResponse = (new Gson()).fromJson(json, new TypeToken<ArrayList<QuestionResponse>>() {
                            }.getType());
                            list_discoveryInstantSearchModel.add(new ChatResponseModel(0, questionResponse, -1, strDate));
                            adapter.updateChatView(list_discoveryInstantSearchModel);
                            rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }
                    }

                    /*if (questionResponse != null) {
                        Gson g = new Gson();
                        String json = g.toJson(questionResponse);
                        Utils.setQuestionData(context, json);
                        mergerData();
                        inflateAutocompletetexView();
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<AnswerResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void handleComplaint() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Please confirm!!")
                .setMessage("Are you sure to register a complaint?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getCategory();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        bottomSheetView = getLayoutInflater().inflate(R.layout.bottomsheetdialog_layout_trying, null);
                        bottomSheetDialog = new BottomSheetDialog(ChatActivity.this);
                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        ((View) bottomSheetView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        bottomSheetDialog.show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.postDelayed(this, 1 * 60 * 100); // every 2 minutes
                                bottomSheetDialog.dismiss();
                                handler.removeCallbacksAndMessages(null);

                            }
                        }, 1 * 60 * 100);
                    }
                })
                .show();
    }


    public static String getCurrentDateAndTime() {
        Format f = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = f.format(new Date());
        System.out.println("Current Date = " + strDate);
        return strDate;
    }


    private void showAlertDialogpAYMENTHistory(final String flag) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Select CA Number");
        String[] items = new String[listCA.size()];
        items = listCA.toArray(items);
        final int lastPos = listCA.size() - 1;
        int checkedItem = -1;

        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (lastPos == which) {
                    showCADialogInputPaymenthISTORY();
                    dialog.dismiss();
                } else {
                    stCA_NO = listCA.get(which);
                }
            }
        });

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                consIDval = stCA_NO;
                if (!consIDval.isEmpty()) {
                    divcode = consIDval.substring(0, 3);
                    dbtype = consIDval.substring(3, 4);
                    consacc = consIDval.substring(4);

                    if (dbtype.equals("S")) {
                        dbtype = "1";
                    } else {
                        dbtype = "2";
                    }
                    fetchPaymentHistory();
                }
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void fetchConsumptionHistory() {
        if (cd.isConnected()) {
            procType = 1;
            AuthURL = "";
            AuthURL = StrUrl + "/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID=" + CompanyID + "&ReportID=1074&strDivCode=" + divcode + "&strCons_Acc=" + consacc;
            new UserAuthOnline().execute(AuthURL);
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchPaymentHistory() {
        if (cd.isConnected()) {
            procType = 2;
            AuthURL = "";
            AuthURL = StrUrl + "/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID=" + CompanyID + "&ReportID=1074&strDivCode=" + divcode + "&strCons_Acc=" + consacc;
            new UserAuthOnline().execute(AuthURL);
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getLikeCountFlag(int like) {
        if (like == 1) {
            bottomSheetView = getLayoutInflater().inflate(R.layout.bottomsheetdialog_layout_like, null);
            bottomSheetDialog = new BottomSheetDialog(ChatActivity.this);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            ((View) bottomSheetView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
            bottomSheetDialog.show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 1 * 60 * 100); // every 2 minutes
                    bottomSheetDialog.dismiss();
                    handler.removeCallbacksAndMessages(null);

                }
            }, 1 * 60 * 100);

        } else {
            bottomSheetView = getLayoutInflater().inflate(R.layout.bottomsheetdialog_layout_dislike, null);
            bottomSheetDialog = new BottomSheetDialog(ChatActivity.this);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            ((View) bottomSheetView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
            bottomSheetDialog.show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 1 * 60 * 100); // every 2 minutes
                    bottomSheetDialog.dismiss();
                    handler.removeCallbacksAndMessages(null);

                }
            }, 1 * 60 * 100);
        }
    }


    private class UserAuthOnline extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL = params[0];

            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent = null;
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
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
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
                                finish();
                            }
                        });
                // create alert dialog
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
            return bodycontent;
        }

        @Override
        protected void onPreExecute() {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                //  progressDialog = ProgressDialog.show(ConsumerMenuActivity.this, " ", " ");
            } else {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
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
                                finish();
                            }
                        });
                // create alert dialog
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }

        @Override
        protected void onPostExecute(String str) {
            String pipeDelBillInfo = str;
            stCA_NO = "";
            consIDval = "";
            String BillInfo[] = null;
            try {
                BillInfo = pipeDelBillInfo.split("[|]");
                if (BillInfo[0].equals("0") || BillInfo[1].equals("0") || BillInfo[2].equals("0")) {
                    Toast.makeText(context, "User id or report not found", Toast.LENGTH_SHORT).show();
                } else {
                    if (procType == 1) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String strDate = sdf.format(c.getTime());
                        list_discoveryInstantSearchModel.add(new ChatResponseModel("table", ChatAdapter.ITEM_TYPE_CONSUMPTION_HISTORY, pipeDelBillInfo, -1, strDate));
                        adapter.updateChatView(list_discoveryInstantSearchModel);
                        rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                        counter++;
                        Utils.setLastCounter(context, String.valueOf(counter));
                    } else if (procType == 2) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String strDate = sdf.format(c.getTime());
                        list_discoveryInstantSearchModel.add(new ChatResponseModel("table", ChatAdapter.ITEM_TYPE_PAYMENT_HISTORY, pipeDelBillInfo, -1, strDate));
                        adapter.updateChatView(list_discoveryInstantSearchModel);
                        rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                        counter++;
                        Utils.setLastCounter(context, String.valueOf(counter));
                    }
                }
            } catch (Exception e) {
                Toast.makeText(context, "User id or report not found", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void showAlertDialog(final String flag) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Select CA Number");
        String[] items = new String[listCA.size()];
        items = listCA.toArray(items);
        final int lastPos = listCA.size() - 1;
        int checkedItem = -1;

        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (lastPos == which) {
                    showCADialogInput(flag);
                    dialog.dismiss();
                } else {
                    stCA_NO = listCA.get(which);
                    consIDval = stCA_NO;
                    if (!consIDval.isEmpty()) {
                        divcode = consIDval.substring(0, 3);
                        dbtype = consIDval.substring(3, 4);
                        consacc = consIDval.substring(4);

                        if (dbtype.equals("S")) {
                            dbtype = "1";
                        } else {
                            dbtype = "2";
                        }
                    }
                }
            }
        });

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (flag.equalsIgnoreCase("3")) {
                    if (cd.isConnected()) {
                        if (!stCA_NO.isEmpty()) {
                            downloadfile();
                        }
                    } else {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!stCA_NO.isEmpty()) {
                        CommonMethods.loadPaymentPage(ChatActivity.this, stCA_NO, MOBILE_NO);
                    }
                }
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }


    private void showAlertDialoGcOMPLAINT(final String flag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.alertdialog_complaint_chat, null);
        builder.setView(customLayout);

        TextView tvOK = customLayout.findViewById(R.id.tvOK);
        TextView tvCancel = customLayout.findViewById(R.id.tvCancel);

        final Spinner spCANouber = customLayout.findViewById(R.id.spCANouber);
        final EditText editAccountno = customLayout.findViewById(R.id.editAccountno);
        final EditText editPhoneno = customLayout.findViewById(R.id.editPhoneno);
        final EditText editEmail = customLayout.findViewById(R.id.editEmail);
        final EditText editAddress = customLayout.findViewById(R.id.editAddress);
        final EditText editComplaint = customLayout.findViewById(R.id.editComplaint);
        final EditText editName = customLayout.findViewById(R.id.editName);


        editPhoneno.setText(userPhone);
        editEmail.setText(userEmail);
        editAddress.setText(userAddress);
        editName.setText(conname);

        spCategory = customLayout.findViewById(R.id.spCategory);
        spSubCategory = customLayout.findViewById(R.id.spSubCategory);

        if (listCA.size() > 1) {
            spCANouber.setVisibility(View.VISIBLE);
            editAccountno.setVisibility(View.GONE);
            final ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listCA);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCANouber.setAdapter(aa);

            spCANouber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    stCA_NO = spCANouber.getSelectedItem().toString();
                    if (stCA_NO.equalsIgnoreCase("Other CA")) {
                        editAccountno.setVisibility(View.VISIBLE);
                    } else {
                        editAccountno.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            editAccountno.setVisibility(View.VISIBLE);
            spCANouber.setVisibility(View.GONE);
            stCA_NO = editAccountno.getText().toString().trim();
        }

        if (stCA_NO.equalsIgnoreCase("Other CA")) {
            stCA_NO = editAccountno.getText().toString().trim();
        }

        final AlertDialog dialog = builder.create();
        dialog.show();
        tvOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (stCA_NO.equalsIgnoreCase("Other CA")) {
                    stCA_NO = editAccountno.getText().toString().trim();
                }

                if (stCA_NO.isEmpty()) {
                    Toast.makeText(context, "Please Enter CA Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                conname = editName.getText().toString().trim();

                if (editName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editPhoneno.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please Enter Phone No.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editPhoneno.getText().toString().length() < 10) {
                    Toast.makeText(context, "Please Enter Valid Phone No.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editAddress.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please Enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editComplaint.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please Enter Complaint", Toast.LENGTH_SHORT).show();
                    return;
                }
                String spinval1 = String.valueOf(spCategory.getSelectedItem());
                String spinval2 = String.valueOf(spSubCategory.getSelectedItem());

                if (spinval1.equalsIgnoreCase("Select Category")) {
                    Toast.makeText(context, "Please Select Category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spinval2.equalsIgnoreCase("Select Subcategory")) {
                    Toast.makeText(context, "Please Select Subcategory", Toast.LENGTH_SHORT).show();
                    return;
                }


                for (int i = 0; i < complainModalSubCategories.size(); i++) {
                    if (spinval2.equalsIgnoreCase(complainModalSubCategories.get(i).getComapinReaon())) {
                        spin2posID = complainModalSubCategories.get(i).getId();
                    }
                }

                funcUrlCheck(1, editPhoneno.getText().toString().trim(), editAddress.getText().toString().trim(), editEmail.getText().toString().trim(), editComplaint.getText().toString().trim());
                dialog.cancel();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void FetchUserDetails() {
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO,EMAIL,CONSUMER_ADD,CONSUMER_NAME FROM CONSUMERDTLS WHERE VALIDATE_FLG=1";
        Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
        String mobno = "";
        String email = "";
        String Addrs = "";
        conname = "";

        while (cursor.moveToNext()) {
            mobno = cursor.getString(1);
            email = cursor.getString(2);
            Addrs = cursor.getString(3);
            conname = cursor.getString(4);
        }
        if (email.equals("0") || email.isEmpty() || email == null) { //added on 040520 by santi
            email = "";
        }

        if (Addrs.equals("0") || Addrs.isEmpty() || Addrs == null) { //added on 040520 by santi
            Addrs = "";
        }

        cursor.close();
        //databaseAccess.close();
        //stremailval.setText(email);
        //strmobval.setText(mobno);

        userEmail = email;
        userPhone = mobno;
        Addrs = userAddress;

    }

    private String funcUrlCheck(int resCode, String phone, String address, String email, String complaint) {
        try {
            //System.out.println("spin2posID" + spin2posID);
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            String regtime = currentDate + "^" + currentTime;
            divcode = stCA_NO.substring(0, 3);
            dbtype = stCA_NO.substring(3, 4);
            consacc = stCA_NO.substring(4);
            if (dbtype.equals("S")) {
                dbtype = "1";
            } else {
                dbtype = "2";
            }

            conname = conname.replaceAll(" ", "^");
            conname = conname.replaceAll(" ", "^");
            address = address.replaceAll(" ", "^");
            conname = conname.replaceAll("\n", "");
            conname = conname.replaceAll("\t", "");
            address = address.replaceAll("\n", "");
            address = address.replaceAll("\t", "");
            String complaintUrl = "http://portal.tpcentralodisha.com:8070" + "/IncomingSMS/CESU_ComplaintRegister.jsp?";
            AuthURL = complaintUrl + "un=TEST&pw=TEST&CompanyID=10&imei=" + imeinum + "&mosarkar=0&Complaint_Flg=1&MO_SARKAR_ID=0&CONSUMER_NO=" + stCA_NO + "&NAME=" + conname + "&ADDRESS=" + address + "&LINE=&CITY=&STATE=&COUNTRY=&PIN=0&PHONE=&MOBILE=" + phone + "&EMAIL=" + email + "&DETAILS=" + complaint + "&COMPLAINT_DATE=" + regtime + "&OFF_CODE=" + divcode + "&SUBDIV=&SECTION=&COMP_CODE=" + spin2posID + "&DB_TYPE=" + dbtype + "&MOD_BY=ePortal_APP&MODULE_ID=ePortal_APP";
            System.out.println("qwerty==" + AuthURL);
            Log.e("URL", AuthURL);
            new UserAuthOnlineComplaint().execute(AuthURL);
        } catch (Exception e) {

        }
        return AuthURL;
    }

    private class UserAuthOnlineComplaint extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL = params[0];

            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent = null;
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

            }

            return bodycontent;
        }

        @Override

        protected void onPreExecute() {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                //  progressDialog = ProgressDialog.show(ComplainRegisterActivity.this, "Fetchig Data", "Please Wait:: connecting to server");
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
                                finish();
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
            //progressDialog.dismiss();
            String pipeDelBillInfo = str;

            String[] BillInfo = pipeDelBillInfo.split("[|]");
            Log.d("DemoApp", " BillInfo[0]   " + pipeDelBillInfo);//authoriztion check


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
                                finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            } else if (BillInfo[1].equals("0")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Complaint not registered");//changed by santi 17/04/20  by santi
                alertDialogBuilder.setMessage("Complaint not registered")//changed by santi 17/04/20  by santi
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            } else if (BillInfo[2].equals("0")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(getString(R.string.error_title));
                alertDialogBuilder.setMessage(getString(R.string.ca_not_found))
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            } else {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strDate = sdf.format(c.getTime());
                counter++;
                Utils.setLastCounter(context, String.valueOf(counter));
                list_discoveryInstantSearchModel.add(new ChatResponseModel("Complaint Registered", ChatAdapter.ITEM_TYPE_USERQUESTION, BillInfo[4], -1, strDate));
                adapter.updateChatView(list_discoveryInstantSearchModel);
                rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);

               /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Your Complaint is Successfully Registered");
                alertDialogBuilder.setMessage("Your Complaint No is:" + BillInfo[4])
                        .setCancelable(false)
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (strregtype.equals("reg")) {
                                    onBackPressed();
                                    finish();
                                } else {
                                    onBackPressed();
                                    finish();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();*/
            }
        }
    }

    private void getCategory() {
        if (CommonMethods.isNetworkAvailable(context)) {
            AuthURL1 = StrUrl1 + "CompanyID=" + 10 + "&RequestType=8";
            new callCategory().execute(AuthURL1);

        } else {
            Toast.makeText(context, "No internet connection,please connect to internet", Toast.LENGTH_SHORT).show();
        }
    }

    private class callCategory extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL = params[0];

            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent = null;
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
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Not Connectd to Server");
                alertDialogBuilder.setMessage("Please Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                // create alert dialog
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }

            return bodycontent;
        }

        @Override

        protected void onPreExecute() {
            //  btnsubmit.setEnabled(false);
            // btnsubmit.setClickable(false);
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                // progressDialog = ProgressDialog.show(ComplaintLoginActivity.this, "  ", " ");
                // spinner.setVisibility(View.VISIBLE);
            } else {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
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
                                finish();
                            }
                        });
                // create alert dialog
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }

        @Override
        protected void onPostExecute(String str) {
            Log.d("DemoApp", " str   " + str);
            // progressDialog.dismiss();
            String pipeDelBillInfo = str;
            String[] BillInfo = pipeDelBillInfo.split("[|]");

            Log.d("DemoApp", " BillInfo[0]   " + pipeDelBillInfo);//authoriztion check
            Log.d("DemoApp", " BillInfo[1]  " + BillInfo[1]);//authoriztion check
            String comData = "";
            String[] DetData = null;
            catDat = BillInfo[1];
            String[] comsubcatcode = catDat.split(";");
            int j = 0;
            if (comsubcatcode.length > 0) {
                complainModals.clear();
                complainModalSubCategories.clear();
                for (int i = 0; i < comsubcatcode.length; i++) {
                    try {
                        comData = comsubcatcode[i];
                        //Log.d("DemoApp", "comData   " + comData);
                        //comprmlen=comsubcatcode.length;
                        DetData = comData.split("[,]");
                        Log.d("DemoApp", " comData   " + comData);
                        Log.d("DemoApp", " comData   " + DetData[0]);

                        if (DetData[2].equalsIgnoreCase("0")) {
                            ComplainModal complainModal = new ComplainModal();
                            complainModal.setId(DetData[0]);
                            complainModal.setComapinReaon(DetData[1]);
                            complainModal.setCategoryId(DetData[2]);
                            if (DetData[1].equalsIgnoreCase("Supply Related")) {
                                complainModals.add(complainModal);
                            }
                        } else {
                            ComplainModalSubCategory complainModalSubCategory = new ComplainModalSubCategory();
                            complainModalSubCategory.setId(DetData[0]);
                            complainModalSubCategory.setComapinReaon(DetData[1]);
                            complainModalSubCategory.setCategoryId(DetData[2]);
                            complainModalSubCategories.add(complainModalSubCategory);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    j++;
                }
                showAlertDialoGcOMPLAINT("1");
                addListenerOnSpinnerItemSelection();

            }
        }
    }

    public void addListenerOnSpinnerItemSelection() {
        catList.clear();

        //catList.add("Select Category");
        for (int i = 0; i < complainModals.size(); i++) {
            catList.add(complainModals.get(i).getComapinReaon());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, catList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(dataAdapter);
        /////////////
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                // String value = String.valueOf(item.toString());
                String value = String.valueOf(position);
                //  spinpos1= String.valueOf(position);
                //spinpos = String.valueOf(position);
                String categoryId = "";

                //System.out.println("sdfg==" + position + "==" + spinpos);
                // if (position >= 1) {
                for (int j = 0; j < complainModals.size(); j++) {
                    if (item.toString().equalsIgnoreCase(complainModals.get(j).getComapinReaon())) {
                        categoryId = complainModals.get(j).getId();
                        //Toast.makeText(context, categoryId, Toast.LENGTH_SHORT).show();
                    }
                }
                //  }

                addItemsOnSpinner2(value, categoryId);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void addItemsOnSpinner2(String SelComplain, String categoryId) {
        List<String> list = new ArrayList<String>();
        //list.add("Select Subcategory");
        for (int i = 0; i < complainModalSubCategories.size(); i++) {
            if (complainModalSubCategories.get(i).getCategoryId().equalsIgnoreCase(categoryId)) {
                if (complainModalSubCategories.get(i).getComapinReaon().equalsIgnoreCase("Area No Supply")) {
                    list.add(complainModalSubCategories.get(i).getComapinReaon());
                }
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubCategory.setAdapter(dataAdapter);
        /////////////
        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                //String value = String.valueOf(item.toString());
                String value = String.valueOf(position);
                // spinpos1 = String.valueOf(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //////////////
    }


    private void showAlertDialogConsumptionHistory(final String flag) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Select CA Number");
        String[] items = new String[listCA.size()];
        items = listCA.toArray(items);
        final int lastPos = listCA.size() - 1;
        int checkedItem = -1;

        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (lastPos == which) {
                    showCADialogInputcONSUMPTIONhISTORY();
                    dialog.dismiss();
                } else {
                    stCA_NO = listCA.get(which);
                }
            }
        });

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                consIDval = stCA_NO;
                if (!consIDval.isEmpty()) {
                    divcode = consIDval.substring(0, 3);
                    dbtype = consIDval.substring(3, 4);
                    consacc = consIDval.substring(4);

                    if (dbtype.equals("S")) {
                        dbtype = "1";
                    } else {
                        dbtype = "2";
                    }

                    fetchConsumptionHistory();
                }
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }


    private void downloadfile() {
        if (new CheckForSDCard().isSDCardPresent()) {
            //myDirectory = new File(Environment.getExternalStorageDirectory() + "/" + BACKUP_FOLDER_PATH);
            myDirectory = new File(context.getExternalFilesDir(BACKUP_FOLDER_PATH).getAbsolutePath());
        } else {
            Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
        }

        if (!myDirectory.exists()) {
            myDirectory.mkdir();
        }

        if (stCA_NO.length() < 10) {
            Toast.makeText(context, "Invalid CA Number", Toast.LENGTH_SHORT).show();
            return;
        } else {
            char digits = stCA_NO.toUpperCase().charAt(3);
            int compareOneTwo = Character.compare(digits, 'S');
            Log.d("TAG", "downloadfile: " + compareOneTwo);
            if (!stCA_NO.isEmpty()) {
                if (compareOneTwo == 0) {
                    docURL = "http://portal.tpcentralodisha.com:8080/ConsumerBillInfo_2021/PrintPDF?ConsumerID=" + stCA_NO.toUpperCase();
                } else {
                    docURL = "http://portal.tpcentralodisha.com:8080/ConsumerBillInfo_2021/BillDetails2021.jsp?ConsumerID=" + stCA_NO.toUpperCase();
                }
            }
            Log.e("DOWNLFILEURL", docURL);
        }


        String url = docURL;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Downloading file...");
        request.setTitle(stCA_NO + "-BILL");

        long timestamp = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmm");
        Date date = new Date(timestamp);

        doc_name = stCA_NO + "_" + dateFormat.format(date) + ".pdf";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        request.setDestinationInExternalFilesDir(context, BACKUP_FOLDER_PATH, doc_name);
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = manager.enqueue(request);
        list.add(downloadId);
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            list.remove(referenceId);
            if (list.isEmpty()) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strDate = sdf.format(c.getTime());

                Random random = new Random();
                int randomNumber = random.nextInt(999) + 65;
                Toast.makeText(context, "Download completed!", Toast.LENGTH_SHORT).show();
                stCA_NO = "";
                list_discoveryInstantSearchModel.add(new ChatResponseModel("PDF", ChatAdapter.ITEM_TYPE_PDF, doc_name, -1, strDate));
                adapter.updateChatView(list_discoveryInstantSearchModel);
                rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);

                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                // .setContentTitle("GadgetSaint")
                                .setContentText("All Download completed");
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(randomNumber, mBuilder.build());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }


    private void showCADialogInput(final String flag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.alertdialog_ca, null);
        builder.setView(customLayout);

        final EditText editText = customLayout.findViewById(R.id.editText);
        TextView tvOK = customLayout.findViewById(R.id.tvOK);
        TextView tvCancel = customLayout.findViewById(R.id.tvCancel);

        final AlertDialog dialog = builder.create();
        dialog.show();
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().trim().isEmpty()) {
                    stCA_NO = editText.getText().toString().trim();
                    consIDval = stCA_NO;
                    if (!consIDval.isEmpty()) {
                        divcode = consIDval.substring(0, 3);
                        dbtype = consIDval.substring(3, 4);
                        consacc = consIDval.substring(4);

                        if (dbtype.equals("S")) {
                            dbtype = "1";
                        } else {
                            dbtype = "2";
                        }
                    }
                    if (flag.equalsIgnoreCase("1")) {
                        CommonMethods.loadPaymentPage(ChatActivity.this,
                                stCA_NO,
                                MOBILE_NO);
                        stCA_NO = "";
                    } else if (flag.equalsIgnoreCase("3")) {
                        if (cd.isConnected()) {
                            if (!stCA_NO.isEmpty()) {
                                downloadfile();
                            }
                        } else {
                            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                dialog.cancel();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }


    private void showCADialogInputcONSUMPTIONhISTORY() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.alertdialog_ca, null);
        builder.setView(customLayout);

        final EditText editText = customLayout.findViewById(R.id.editText);
        TextView tvOK = customLayout.findViewById(R.id.tvOK);
        TextView tvCancel = customLayout.findViewById(R.id.tvCancel);

        final AlertDialog dialog = builder.create();
        dialog.show();
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().trim().isEmpty()) {
                    stCA_NO = editText.getText().toString().trim();
                    consIDval = stCA_NO;
                    if (!consIDval.isEmpty()) {
                        divcode = consIDval.substring(0, 3);
                        dbtype = consIDval.substring(3, 4);
                        consacc = consIDval.substring(4);

                        if (dbtype.equals("S")) {
                            dbtype = "1";
                        } else {
                            dbtype = "2";
                        }
                    }
                    fetchConsumptionHistory();
                }
                dialog.cancel();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }


    private void showCADialogInputPaymenthISTORY() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.alertdialog_ca, null);
        builder.setView(customLayout);

        final EditText editText = customLayout.findViewById(R.id.editText);
        TextView tvOK = customLayout.findViewById(R.id.tvOK);
        TextView tvCancel = customLayout.findViewById(R.id.tvCancel);

        final AlertDialog dialog = builder.create();
        dialog.show();
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().trim().isEmpty()) {
                    stCA_NO = editText.getText().toString().trim();
                    consIDval = stCA_NO;
                    if (!consIDval.isEmpty()) {
                        divcode = consIDval.substring(0, 3);
                        dbtype = consIDval.substring(3, 4);
                        consacc = consIDval.substring(4);

                        if (dbtype.equals("S")) {
                            dbtype = "1";
                        } else {
                            dbtype = "2";
                        }
                    }
                    fetchPaymentHistory();
                }
                dialog.cancel();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void prepareImageData() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = sdf.format(c.getTime());

        list_discoveryInstantSearchModel.add(new ChatResponseModel(listSchemes, ChatAdapter.ITEM_TYPE_SCHEMES, -1, strDate));
        adapter.updateChatView(list_discoveryInstantSearchModel);
        rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
        currentCount = list_discoveryInstantSearchModel.size();
        counter++;
        Utils.setLastCounter(context, String.valueOf(counter));

        String json = Utils.getQuestionData(context);
        questionResponse = (new Gson()).fromJson(json, new TypeToken<ArrayList<QuestionResponse>>() {
        }.getType());
        list_discoveryInstantSearchModel.add(new ChatResponseModel(0, questionResponse, -1, strDate));
        adapter.updateChatView(list_discoveryInstantSearchModel);
        rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    public static Uri getOutputMediaFileUri(Context context, File fileObj) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", fileObj);
        } else {
            return Uri.fromFile(fileObj);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back) {
            finish();
        } else if (v == btnFile) {

                   /* bottomSheetView = getLayoutInflater().inflate(R.layout.bottomsheetdialog_layout_like, null);
            bottomSheetDialog = new BottomSheetDialog(ChatActivity.this);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetDialog.show();*/
            // bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
           /* File fileCreate = new File(context.getExternalFilesDir(BACKUP_FOLDER_PATH).getAbsolutePath());
            File pdfFile = new File(fileCreate, doc_name);
            Uri path = getOutputMediaFileUri(context, pdfFile);
            Log.e("Fragment2", String.valueOf(path));
            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
            pdfOpenintent.setDataAndType(path, "application/pdf");
            pdfOpenintent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (pdfOpenintent.resolveActivity(getPackageManager()) != null) {
                startActivity(pdfOpenintent);
            }*/
        } else if (v == ivMenu) {
            PopupMenu popup = new PopupMenu(this, ivMenu);
            popup.getMenuInflater().inflate(R.menu.chatmenu, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    SharedPreferences preferences = getSharedPreferences("botty", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    adapter.clearRecyclerView();
                    if (cd.isConnected()) {
                        fetchQuestions();
                    } else {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
            popup.show();
        } else if (v == ivQmenu) {
            //showQuestionDialog();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String strDate = sdf.format(c.getTime());

            String json = Utils.getQuestionData(context);
            questionResponse = (new Gson()).fromJson(json, new TypeToken<ArrayList<QuestionResponse>>() {
            }.getType());
            list_discoveryInstantSearchModel.add(new ChatResponseModel(0, questionResponse, -1, strDate));
            adapter.updateChatView(list_discoveryInstantSearchModel);
            rvRecycler.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
    }

    private void showQuestionDialog() {
        String json = Utils.getQuestionData(context);
        if (!json.trim().isEmpty()) {
            final String[] questions;
            questionResponse = (new Gson()).fromJson(json, new TypeToken<ArrayList<QuestionResponse>>() {
            }.getType());

            questions = new String[questionResponse.size()];
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
            builderSingle.setTitle("Choose Question:-");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
            arrayAdapter.clear();
            for (int i = 0; i < questionResponse.size(); i++) {
                questions[i] = questionResponse.get(i).getqUESTION();
                arrayAdapter.add(questionResponse.get(i).getqUESTION());
            }

            builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int position) {
                    String strName = arrayAdapter.getItem(position);
                    int indexOfTwo = Arrays.asList(questions).indexOf(strName);
                    getQuestionID(questionResponse.get(indexOfTwo).getfLAG().toString(), questionResponse.get(indexOfTwo).getqUESTION(), questionResponse.get(indexOfTwo).getiD());
                }
            });
            builderSingle.show();
        }
    }


    @Override
    public void getCoordinates(String id, String latitude, String longitude) {
        Toast.makeText(context, latitude + " " + longitude, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getPDFpath(String path1) {
        File fileCreate = new File(context.getExternalFilesDir(BACKUP_FOLDER_PATH).getAbsolutePath());
        File pdfFile = new File(fileCreate, path1);
        Uri path = getOutputMediaFileUri(context, pdfFile);
        Log.i("Fragment2", String.valueOf(path));
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        pdfOpenintent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (pdfOpenintent.resolveActivity(getPackageManager()) != null) {
            startActivity(pdfOpenintent);
        }
    }
}
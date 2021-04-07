package chatbots.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cesu.itcc05.consumeportal.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import chatbots.extras.Utils;
import chatbots.model.ChatResponseModel;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public static final int ITEM_TYPE_QUESTIONS = 0;
    public static final int ITEM_TYPE_ANSWERS = 1;
    public static final int ITEM_TYPE_IMAGES = 2;
    public static final int ITEM_TYPE_URL = 3;
    public static final int ITEM_TYPE_DIVIDER = 4;
    public static final int ITEM_TYPE_USERQUESTION = 5;
    public static final int ITEM_TYPE_LOCATIONS = 6;
    public static final int ITEM_TYPE_PDF = 7;
    public static final int ITEM_TYPE_SCHEMES = 8;
    public static final int ITEM_TYPE_CONSUMPTION_HISTORY = 9;
    public static final int ITEM_TYPE_PAYMENT_HISTORY = 10;
    public static final int ITEM_TYPE_PHONE = 11;
    GetQuestionId getQuestionId;
    GetMapCoordinates getCoordinates;
    GetPDFPath getPDFPath;
    GetLikeDislike getLikeDislike;
    List<ChatResponseModel> list_discoveryInstantSearchModel;
    boolean QuestionSet = false;

    public interface GetQuestionId {
        void getQuestionID(String flag, String text, String id);
    }

    public interface GetPDFPath {
        void getPDFpath(String path);
    }

    public interface GetMapCoordinates {
        void getCoordinates(String id, String latitude, String longitude);
    }

    public interface GetLikeDislike {
        void getLikeCountFlag(int like);
    }

    public void updateChatView(List<ChatResponseModel> list_discoveryInstantSearchModel) {
        this.list_discoveryInstantSearchModel = list_discoveryInstantSearchModel;
        Gson g = new Gson();
        String json = g.toJson(list_discoveryInstantSearchModel);
        Utils.setChatData(context, json);
        notifyDataSetChanged();
    }

    public void clearRecyclerView() {
        try {
            int size = list_discoveryInstantSearchModel.size();
            list_discoveryInstantSearchModel.clear();
            notifyItemRangeRemoved(0, size);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ChatAdapter(Context context, GetQuestionId getQuestionId, GetMapCoordinates getCoordinate, GetPDFPath getPDFPath, GetLikeDislike getLikeDislike) {
        this.context = context;
        this.getQuestionId = getQuestionId;
        this.getCoordinates = getCoordinate;
        this.getPDFPath = getPDFPath;
        this.getLikeDislike = getLikeDislike;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case ITEM_TYPE_QUESTIONS:
                view = inflater.inflate(R.layout.row_questions, parent, false);
                holder = new QuestionsViewHolder(view);
                break;
            case ITEM_TYPE_ANSWERS:
                view = inflater.inflate(R.layout.row_answers, parent, false);
                holder = new AnswerViewHolder(view);
                break;
            case ITEM_TYPE_USERQUESTION:
                view = inflater.inflate(R.layout.row_question_user, parent, false);
                holder = new QuestionUserViewHolder(view);
                break;
            case ITEM_TYPE_IMAGES:
                view = inflater.inflate(R.layout.row_images, parent, false);
                holder = new ImageViewHolder(view);
                break;
            case ITEM_TYPE_LOCATIONS:
                view = inflater.inflate(R.layout.row_contactus, parent, false);
                holder = new LocationViewHolder(view);
                break;
            case ITEM_TYPE_PDF:
                view = inflater.inflate(R.layout.row_pdf, parent, false);
                holder = new PDFViewHolder(view);
                break;
            case ITEM_TYPE_SCHEMES:
                view = inflater.inflate(R.layout.row_schemes, parent, false);
                holder = new SchemesViewHolder(view);
                break;
            case ITEM_TYPE_CONSUMPTION_HISTORY:
                view = inflater.inflate(R.layout.row_consumption_history, parent, false);
                holder = new ConsumptionHistoryViewHolder(view);
                break;
            case ITEM_TYPE_PAYMENT_HISTORY:
                view = inflater.inflate(R.layout.row_payment_history, parent, false);
                holder = new PaymentHistoryViewHolder(view);
                break;
            case ITEM_TYPE_PHONE:
                view = inflater.inflate(R.layout.row_contactus, parent, false);
                holder = new PhoneViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder.getItemViewType() == ITEM_TYPE_QUESTIONS) {
            QuestionsViewHolder userviewViewHolder = (QuestionsViewHolder) holder;
            QuestionViewSetter(userviewViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_ANSWERS) {
            AnswerViewHolder tripViewHolder = (AnswerViewHolder) holder;
            AnswerViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_USERQUESTION) {
            QuestionUserViewHolder tripViewHolder = (QuestionUserViewHolder) holder;
            UserTypedViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_IMAGES) {
            ImageViewHolder tripViewHolder = (ImageViewHolder) holder;
            ImageViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_LOCATIONS) {
            LocationViewHolder tripViewHolder = (LocationViewHolder) holder;
            LocationViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_PDF) {
            PDFViewHolder tripViewHolder = (PDFViewHolder) holder;
            PDFViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_SCHEMES) {
            SchemesViewHolder tripViewHolder = (SchemesViewHolder) holder;
            SCHEMEViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_CONSUMPTION_HISTORY) {
            ConsumptionHistoryViewHolder tripViewHolder = (ConsumptionHistoryViewHolder) holder;
            ConsumptionViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_PAYMENT_HISTORY) {
            PaymentHistoryViewHolder tripViewHolder = (PaymentHistoryViewHolder) holder;
            PaymentViewSetter(tripViewHolder, i);
        } else if (holder.getItemViewType() == ITEM_TYPE_PHONE) {
            PhoneViewHolder tripViewHolder = (PhoneViewHolder) holder;
            PhoneViewSetter(tripViewHolder, i);
        }
    }

    private void PaymentViewSetter(final PaymentHistoryViewHolder paymentHistoryViewHolder, final int l) {
        String[] BillInfo = null;
        paymentHistoryViewHolder.tvDatetime.setText(list_discoveryInstantSearchModel.get(l).getDate());
        try {
            if (list_discoveryInstantSearchModel.get(l).getUserTypedquestions() != null) {
                BillInfo = list_discoveryInstantSearchModel.get(l).getUserTypedquestions().split("[|]");
                if (BillInfo[0].equals("0") || BillInfo[1].equals("0") || BillInfo[2].equals("0")) {
                    //Toast.makeText(context, "User id or report not found", Toast.LENGTH_SHORT).show();
                } else {
                    int arrayparam = 70;
                    String strData = BillInfo[3];
                    String[] strData1 = strData.split("[;]");
                    String[] ContentWrap = new String[arrayparam];
                    // 1|1|1|10-2019,155,.41,10-2019,00074-44,664,25-10-2019,0;09-2019,186,.35,09-2019,00074-79,799,24-09-2019,0;08-2019,130,-.24,08-2019,00552272,555,19-08-2019,0;07-2019,351,0,07-2019,00326691,1678,22-07-2019,0;06-2019,259,-.01,06-2019,00056002,1178,20-06-2019,0;05-2019,132,-2.12,05-2019,00194076,800,22-05-2019,0;04-2019,231,234.38,04-2019,00184406,800,27-04-2019,0;03-2019,177,-17.29,03-2019,0074-152,760,23-03-2019,0;02-2019,138,.17,02-2019,00139625,590,23-02-2019,0;
                    String monData = "";
                    String[] MonthData = null;
                    Log.d("DemoApp", " strData1.length   " + strData1.length);
                    int j = 0;
                    //k=, data
                    //i=month data
                    // j= param data
                    int moncnt = strData1.length;
                    for (int i = 0; i < strData1.length; i++) {
                        try {

                            monData = strData1[i];
                            // 10-2019,155,.41,10-2019,00074-44,664,25-10-2019,0
                            MonthData = null;
                            MonthData = monData.split("[,]");
                            Log.d("DemoApp", " MonthData.length   " + MonthData.length);
                            for (int k = 0; k < MonthData.length; k++) {
                                ContentWrap[j] = MonthData[k];
                                j++;
                            }

                            //  Log.d("DemoApp", " strData1.length   " + strData1.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("DemoApp", " ContentWrap.length   " + ContentWrap.length);
                    String[] finContentWrap = new String[arrayparam];
                    for (int i = 0; i < arrayparam; i++) {
                        finContentWrap[i] = ContentWrap[i];
                        if (ContentWrap[i] == null) {
                            finContentWrap[i] = "-";
                        }
                    }

                    paymentHistoryViewHolder.strmon1.setText(finContentWrap[0]);
                    paymentHistoryViewHolder.strmon2.setText(finContentWrap[10]);
                    paymentHistoryViewHolder.strmon3.setText(finContentWrap[20]);
                    paymentHistoryViewHolder.strmon4.setText(finContentWrap[30]);
                    paymentHistoryViewHolder.strmon5.setText(finContentWrap[40]);
                    paymentHistoryViewHolder.strmon6.setText(finContentWrap[50]);
                    paymentHistoryViewHolder.strbill1.setText(finContentWrap[6]);
                    paymentHistoryViewHolder.strbill2.setText(finContentWrap[16]);
                    paymentHistoryViewHolder.strbill3.setText(finContentWrap[26]);
                    paymentHistoryViewHolder.strbill4.setText(finContentWrap[36]);
                    paymentHistoryViewHolder.strbill5.setText(finContentWrap[46]);
                    paymentHistoryViewHolder.strbill6.setText(finContentWrap[56]);
                    paymentHistoryViewHolder.strpmt1.setText(finContentWrap[4]);
                    paymentHistoryViewHolder.strpmt2.setText(finContentWrap[14]);
                    paymentHistoryViewHolder.strpmt3.setText(finContentWrap[24]);
                    paymentHistoryViewHolder.strpmt4.setText(finContentWrap[34]);
                    paymentHistoryViewHolder.strpmt5.setText(finContentWrap[44]);
                    paymentHistoryViewHolder.strpmt6.setText(finContentWrap[54]);
                    paymentHistoryViewHolder.strdate1.setText(finContentWrap[5]);
                    paymentHistoryViewHolder.strdate2.setText(finContentWrap[15]);
                    paymentHistoryViewHolder.strdate3.setText(finContentWrap[25]);
                    paymentHistoryViewHolder.strdate4.setText(finContentWrap[35]);
                    paymentHistoryViewHolder.strdate5.setText(finContentWrap[45]);
                    paymentHistoryViewHolder.strdate6.setText(finContentWrap[55]);

                    try {
                        if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 1) {
                            paymentHistoryViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                            paymentHistoryViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));

                        } else if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 0) {
                            paymentHistoryViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                            paymentHistoryViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    paymentHistoryViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            Gson g = new Gson();
                            if (paymentHistoryViewHolder.rbLike.isChecked()) {
                                getLikeDislike.getLikeCountFlag(1);
                                paymentHistoryViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                                paymentHistoryViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));
                                list_discoveryInstantSearchModel.get(l).setLikeDislike(1);
                                updateChatView(list_discoveryInstantSearchModel);
                            } else if (paymentHistoryViewHolder.rbdisLike.isChecked()) {
                                paymentHistoryViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                                paymentHistoryViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                                list_discoveryInstantSearchModel.get(l).setLikeDislike(0);
                                updateChatView(list_discoveryInstantSearchModel);
                                getLikeDislike.getLikeCountFlag(0);
                            }
                           // Log.e("LIKERESPONSE", g.toJson(list_discoveryInstantSearchModel));
                        }
                    });


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ConsumptionViewSetter(final ConsumptionHistoryViewHolder consumptionHistoryViewHolder, final int l) {
        String[] BillInfo = null;
        String[] finContentWrap = null;
        consumptionHistoryViewHolder.tvDatetime.setText(list_discoveryInstantSearchModel.get(l).getDate());
        try {
            if (list_discoveryInstantSearchModel.get(l).getUserTypedquestions() != null) {
                BillInfo = list_discoveryInstantSearchModel.get(l).getUserTypedquestions().split("[|]");
                if (BillInfo[0].equals("0") || BillInfo[1].equals("0") || BillInfo[2].equals("0")) {
                    //Toast.makeText(context, "User id or report not found", Toast.LENGTH_SHORT).show();
                } else {
                    int arrayparam = 70;
                    String strData = BillInfo[3];
                    String[] strData1 = strData.split("[;]");
                    String[] ContentWrap = new String[arrayparam];
                    //1|1|1|10-2019,155,.41,10-2019,00074-44,664,25-10-2019,0;09-2019,186,.35,09-2019,00074-79,799,24-09-2019,0;08-2019,130,-.24,08-2019,00552272,555,19-08-2019,0;07-2019,351,0,07-2019,00326691,1678,22-07-2019,0;06-2019,259,-.01,06-2019,00056002,1178,20-06-2019,0;05-2019,132,-2.12,05-2019,00194076,800,22-05-2019,0;04-2019,231,234.38,04-2019,00184406,800,27-04-2019,0;03-2019,177,-17.29,03-2019,0074-152,760,23-03-2019,0;02-2019,138,.17,02-2019,00139625,590,23-02-2019,0;
                    String monData = "";
                    String[] MonthData = null;
                    Log.d("DemoApp", " strData1.length   " + strData1.length);
                    int j = 0;
                    //k=, data
                    //i=month data
                    //j= param data
                    int moncnt = strData1.length;
                    for (int i = 0; i < strData1.length; i++) {
                        try {
                            monData = strData1[i];
                            //10-2019,155,.41,10-2019,00074-44,664,25-10-2019,0
                            MonthData = null;
                            MonthData = monData.split("[,]");
                            Log.d("DemoApp", " MonthData.length   " + MonthData.length);
                            for (int k = 0; k < MonthData.length; k++) {
                                ContentWrap[j] = MonthData[k];
                                j++;
                            }
                            //Log.d("DemoApp", " strData1.length   " + strData1.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("DemoApp", " ContentWrap.length   " + ContentWrap.length);
                    finContentWrap = new String[arrayparam];
                    for (int i = 0; i < arrayparam; i++) {
                        finContentWrap[i] = ContentWrap[i];
                        if (ContentWrap[i] == null) {
                            finContentWrap[i] = "-";
                        }
                    }


                    consumptionHistoryViewHolder.strmon1.setText(finContentWrap[0]);
                    consumptionHistoryViewHolder.strmon2.setText(finContentWrap[10]);
                    consumptionHistoryViewHolder.strmon3.setText(finContentWrap[20]);
                    consumptionHistoryViewHolder.strmon4.setText(finContentWrap[30]);
                    consumptionHistoryViewHolder.strmon5.setText(finContentWrap[40]);
                    consumptionHistoryViewHolder.strmon6.setText(finContentWrap[50]);

                    consumptionHistoryViewHolder.strdate1.setText(finContentWrap[1]);
                    consumptionHistoryViewHolder.strdate2.setText(finContentWrap[11]);
                    consumptionHistoryViewHolder.strdate3.setText(finContentWrap[21]);
                    consumptionHistoryViewHolder.strdate4.setText(finContentWrap[31]);
                    consumptionHistoryViewHolder.strdate5.setText(finContentWrap[41]);
                    consumptionHistoryViewHolder.strdate6.setText(finContentWrap[51]);

                    consumptionHistoryViewHolder.strbill1.setText(finContentWrap[2]);
                    consumptionHistoryViewHolder.strbill2.setText(finContentWrap[12]);
                    consumptionHistoryViewHolder.strbill3.setText(finContentWrap[22]);
                    consumptionHistoryViewHolder.strbill4.setText(finContentWrap[32]);
                    consumptionHistoryViewHolder.strbill5.setText(finContentWrap[42]);
                    consumptionHistoryViewHolder.strbill6.setText(finContentWrap[52]);

                    consumptionHistoryViewHolder.strbilldt1.setText(finContentWrap[6]);
                    consumptionHistoryViewHolder.strbilldt2.setText(finContentWrap[16]);
                    consumptionHistoryViewHolder.strbilldt3.setText(finContentWrap[26]);
                    consumptionHistoryViewHolder.strbilldt4.setText(finContentWrap[36]);
                    consumptionHistoryViewHolder.strbilldt5.setText(finContentWrap[46]);
                    consumptionHistoryViewHolder.strbilldt6.setText(finContentWrap[56]);

                    consumptionHistoryViewHolder.strpmt1.setText(finContentWrap[5]);
                    consumptionHistoryViewHolder.strpmt2.setText(finContentWrap[15]);
                    consumptionHistoryViewHolder.strpmt3.setText(finContentWrap[25]);
                    consumptionHistoryViewHolder.strpmt4.setText(finContentWrap[35]);
                    consumptionHistoryViewHolder.strpmt5.setText(finContentWrap[45]);
                    consumptionHistoryViewHolder.strpmt6.setText(finContentWrap[55]);

                    try {
                        if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 1) {
                            consumptionHistoryViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                            consumptionHistoryViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));

                        } else if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 0) {
                            consumptionHistoryViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                            consumptionHistoryViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    consumptionHistoryViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            // Gson g = new Gson();
                            if (consumptionHistoryViewHolder.rbLike.isChecked()) {
                                getLikeDislike.getLikeCountFlag(1);
                                consumptionHistoryViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                                consumptionHistoryViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));
                                list_discoveryInstantSearchModel.get(l).setLikeDislike(1);
                                updateChatView(list_discoveryInstantSearchModel);
                            } else if (consumptionHistoryViewHolder.rbdisLike.isChecked()) {
                                getLikeDislike.getLikeCountFlag(0);
                                consumptionHistoryViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                                consumptionHistoryViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                                list_discoveryInstantSearchModel.get(l).setLikeDislike(0);
                                updateChatView(list_discoveryInstantSearchModel);
                            }
                            // Log.e("LIKERESPONSE", g.toJson(list_discoveryInstantSearchModel));
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void SCHEMEViewSetter(final SchemesViewHolder schemesViewHolder, final int l) {
        schemesViewHolder.tvDate.setText(list_discoveryInstantSearchModel.get(l).getDate());
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(5, 5, 5, 5);
        try {
            schemesViewHolder.llContainers.removeAllViews();
            for (int i = 0; i < list_discoveryInstantSearchModel.get(l).getListImages().size(); i++) {
                try {
                    final ImageView iv = new ImageView(context);
                    iv.setId(i + 1);
                    iv.setLayoutParams(buttonLayoutParams);
                    iv.setTag(i);
                    Picasso.with(context)
                            .load(list_discoveryInstantSearchModel.get(l)
                                    .getListImages().get(i).getImgURL())
                            .into(iv);
                    final int finalI = i;
                    if(list_discoveryInstantSearchModel.get(l).getListImages().get(finalI).getClickType()!=null){
                        iv.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list_discoveryInstantSearchModel.get(l).getListImages().get(finalI).getClickType()));
                                context.startActivity(browserIntent);
                            }
                        });
                    }

                    schemesViewHolder.llContainers.addView(iv);
                } catch (Exception e) {
                }
            }


            try {
                if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 1) {
                    schemesViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                    schemesViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));

                } else if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 0) {
                    schemesViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                    schemesViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            schemesViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // Gson g = new Gson();
                    if (schemesViewHolder.rbLike.isChecked()) {
                        getLikeDislike.getLikeCountFlag(1);
                        schemesViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                        schemesViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));
                        list_discoveryInstantSearchModel.get(l).setLikeDislike(1);
                        updateChatView(list_discoveryInstantSearchModel);
                    } else if (schemesViewHolder.rbdisLike.isChecked()) {
                        getLikeDislike.getLikeCountFlag(0);
                        schemesViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                        schemesViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                        list_discoveryInstantSearchModel.get(l).setLikeDislike(0);
                        updateChatView(list_discoveryInstantSearchModel);
                    }
                    // Log.e("LIKERESPONSE", g.toJson(list_discoveryInstantSearchModel));
                }
            });
        } catch (Exception e) {
        }
    }

    private void PDFViewSetter(final PDFViewHolder pdfViewHolder, final int l) {
        pdfViewHolder.tvFilename.setText(list_discoveryInstantSearchModel.get(l).getUserTypedquestions());
        pdfViewHolder.tvTitle.setText("Your Bill..");
        pdfViewHolder.rlMainview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getPDFPath.getPDFpath(pdfViewHolder.tvFilename.getText().toString().trim());
            }
        });

        pdfViewHolder.tvDatetime.setText(list_discoveryInstantSearchModel.get(l).getDate());

        try {
            if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 1) {
                pdfViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                pdfViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));

            } else if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 0) {
                pdfViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                pdfViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pdfViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Gson g = new Gson();
                if (pdfViewHolder.rbLike.isChecked()) {
                    getLikeDislike.getLikeCountFlag(1);
                    pdfViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                    pdfViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));
                    list_discoveryInstantSearchModel.get(l).setLikeDislike(1);
                    updateChatView(list_discoveryInstantSearchModel);
                } else if (pdfViewHolder.rbdisLike.isChecked()) {
                    getLikeDislike.getLikeCountFlag(0);
                    pdfViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                    pdfViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                    list_discoveryInstantSearchModel.get(l).setLikeDislike(0);
                    updateChatView(list_discoveryInstantSearchModel);
                }
                // Log.e("LIKERESPONSE", g.toJson(list_discoveryInstantSearchModel));
            }
        });
    }

    private void QuestionViewSetter(QuestionsViewHolder userviewViewHolder, final int l) {
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(5, 5, 5, 5);

        try {
            userviewViewHolder.flexContainer.removeAllViews();
            for (int i = 0; i < list_discoveryInstantSearchModel.get(l).getListQuestions().size(); i++) {
                final TextView tv = new TextView(context);
                tv.setText(list_discoveryInstantSearchModel.get(l).getListQuestions().get(i).getqUESTION());
                tv.setHeight(70);
                tv.setTextSize(15.0f);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_yellow));
                tv.setId(i + 1);
                tv.setLayoutParams(buttonLayoutParams);
                tv.setTag(list_discoveryInstantSearchModel.get(l).getListQuestions().get(i).getfLAG());
                tv.setPadding(10, 10, 10, 10);

                final int finalI = i;
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String ID = tv.getTag().toString();
                       // list_discoveryInstantSearchModel.get(l).getListQuestions().get(finalI).getiD();
                        //Toast.makeText(context, list_discoveryInstantSearchModel.get(l).getListQuestions().get(finalI).getiD(),Toast.LENGTH_SHORT).show();
                        getQuestionId.getQuestionID(ID, tv.getText().toString().trim(),list_discoveryInstantSearchModel.get(l).getListQuestions().get(finalI).getiD());

                    }
                });

                userviewViewHolder.flexContainer.addView(tv);
                QuestionSet = true;
            }
        } catch (Exception e) {

        }
    }

    private void AnswerViewSetter(AnswerViewHolder userviewViewHolder, final int l) {
        userviewViewHolder.tvUserChat.setText(list_discoveryInstantSearchModel.get(l).getAnswers());
    }

    private void UserTypedViewSetter(final QuestionUserViewHolder userviewViewHolder, final int l) {
        userviewViewHolder.tvUserChat.setText(list_discoveryInstantSearchModel.get(l).getUserTypedquestions());
        userviewViewHolder.tvDdate.setText(list_discoveryInstantSearchModel.get(l).getDate());
        if (list_discoveryInstantSearchModel.get(l).getUserTypedquestions().contains("http") || list_discoveryInstantSearchModel.get(l).getUserTypedquestions().contains("https")) {
            userviewViewHolder.tvTitle.setText("Our Website");
            userviewViewHolder.tvUserChat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list_discoveryInstantSearchModel.get(l).getUserTypedquestions()));
                    context.startActivity(browserIntent);
                }
            });
            userviewViewHolder.ivForward.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list_discoveryInstantSearchModel.get(l).getUserTypedquestions()));
                    context.startActivity(browserIntent);
                }
            });
        } /*else if (list_discoveryInstantSearchModel.get(l).getInputtype().equalsIgnoreCase("phoneno")) {
            userviewViewHolder.tvTitle.setText("Contact Us");
            userviewViewHolder.ivForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + list_discoveryInstantSearchModel.get(l).getUserTypedquestions()));
                    context.startActivity(intent);
                }
            });
        }*/ else if (list_discoveryInstantSearchModel.get(l).getInputtype().equalsIgnoreCase("map")) {
            userviewViewHolder.ivForward.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String geo = "geo:" + list_discoveryInstantSearchModel.get(l).getLatitude() + "," + list_discoveryInstantSearchModel.get(l).getLongitude();
                    Uri gmmIntentUri = Uri.parse(geo);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(mapIntent);
                    }
                }
            });
        } else if (list_discoveryInstantSearchModel.get(l).getInputtype().equalsIgnoreCase("Fight")) {
            userviewViewHolder.tvTitle.setText("Fight Against Corona");
        } else if (list_discoveryInstantSearchModel.get(l).getInputtype().equalsIgnoreCase("Complaint Registered")) {
            userviewViewHolder.tvTitle.setText("Your Complaint Number is");
        }else{
            userviewViewHolder.ivForward.setVisibility(View.GONE);
            userviewViewHolder.tvTitle.setText(list_discoveryInstantSearchModel.get(l).getInputtype());
        }

        try {
            if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 1) {
                userviewViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                userviewViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));

            } else if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 0) {
                userviewViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                userviewViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        userviewViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Gson g = new Gson();
                if (userviewViewHolder.rbLike.isChecked()) {
                    getLikeDislike.getLikeCountFlag(1);
                    userviewViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                    userviewViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));
                    list_discoveryInstantSearchModel.get(l).setLikeDislike(1);
                    updateChatView(list_discoveryInstantSearchModel);
                } else if (userviewViewHolder.rbdisLike.isChecked()) {
                    getLikeDislike.getLikeCountFlag(0);
                    userviewViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                    userviewViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                    list_discoveryInstantSearchModel.get(l).setLikeDislike(0);
                    updateChatView(list_discoveryInstantSearchModel);
                }
                // Log.e("LIKERESPONSE", g.toJson(list_discoveryInstantSearchModel));
            }
        });
    }

    private void ImageViewSetter(ImageViewHolder userviewViewHolder, int l) {
        try {
            Picasso.with(context).load(list_discoveryInstantSearchModel.get(l).getAnswers()).into(userviewViewHolder.ivImages);
        } catch (Exception e) {
        }
    }

    private void PhoneViewSetter(final PhoneViewHolder phoneViewHolder, final int l) {
        phoneViewHolder.tvDatetime.setText(list_discoveryInstantSearchModel.get(l).getDate());

        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(5, 5, 5, 5);
        try {
            phoneViewHolder.llContainer.removeAllViews();
            for (int i = 0; i < list_discoveryInstantSearchModel.get(l).getListPhone().size(); i++) {
                final TextView tv = new TextView(context);
                tv.setText(list_discoveryInstantSearchModel.get(l).getListPhone().get(i));
                tv.setHeight(75);
                tv.setTextSize(14.0f);
                tv.setGravity(Gravity.CENTER);
                //tv.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context,R.drawable.ic_share), null);
                tv.setTextColor(Color.parseColor("#ffffff"));
                tv.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_chat_orange));
                tv.setId(i + 1);
                tv.setLayoutParams(buttonLayoutParams);
                // tv.setTag(list_discoveryInstantSearchModel.get(l).getListAnswers().get(i).getAnswerid());
                tv.setPadding(20, 10, 20, 10);

                final int finalI1 = i;

                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + list_discoveryInstantSearchModel.get(l).getListPhone().get(finalI1)));
                        context.startActivity(intent);
                    }
                });

                phoneViewHolder.llContainer.addView(tv);

                try {
                    if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 1) {
                        phoneViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                        phoneViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));

                    } else if (list_discoveryInstantSearchModel.get(l).getLikeDislike() == 0) {
                        phoneViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                        phoneViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                phoneViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // Gson g = new Gson();
                        if (phoneViewHolder.rbLike.isChecked()) {
                            getLikeDislike.getLikeCountFlag(1);
                            phoneViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_selected));
                            phoneViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_black));
                            list_discoveryInstantSearchModel.get(l).setLikeDislike(1);
                            updateChatView(list_discoveryInstantSearchModel);
                        } else if (phoneViewHolder.rbdisLike.isChecked()) {
                            getLikeDislike.getLikeCountFlag(0);
                            phoneViewHolder.rbdisLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dislike_selected));
                            phoneViewHolder.rbLike.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_like_black));
                            list_discoveryInstantSearchModel.get(l).setLikeDislike(0);
                            updateChatView(list_discoveryInstantSearchModel);
                        }
                        // Log.e("LIKERESPONSE", g.toJson(list_discoveryInstantSearchModel));
                    }
                });
            }
        } catch (Exception e) {

        }
    }


    private void LocationViewSetter(LocationViewHolder locationViewHolder, final int l) {
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(5, 5, 5, 5);
        try {
            locationViewHolder.llContainer.removeAllViews();
            for (int i = 0; i < list_discoveryInstantSearchModel.get(l).getListAnswers().size(); i++) {
                final TextView tv = new TextView(context);
                tv.setText(list_discoveryInstantSearchModel.get(l).getListAnswers().get(i).getANSWER());
                tv.setHeight(75);
                tv.setTextSize(14.0f);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_location));
                tv.setId(i + 1);
                tv.setLayoutParams(buttonLayoutParams);
                tv.setTag(list_discoveryInstantSearchModel.get(l).getListAnswers().get(i).getID());
                tv.setPadding(20, 10, 20, 10);

               /* final int finalI = i;
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String ID = tv.getTag().toString();
                        getCoordinates.getCoordinates(ID, list_discoveryInstantSearchModel.get(l).getListAnswers().get(finalI).getLatitute(), list_discoveryInstantSearchModel.get(l).getListAnswers().get(finalI).getLongitude());
                    }
                });*/

                locationViewHolder.llContainer.addView(tv);
            }
        } catch (Exception e) {

        }
    }


    @Override
    public int getItemCount() {
        return list_discoveryInstantSearchModel.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_QUESTIONS) {
            return ITEM_TYPE_QUESTIONS;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_ANSWERS) {
            return ITEM_TYPE_ANSWERS;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_USERQUESTION) {
            return ITEM_TYPE_USERQUESTION;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_IMAGES) {
            return ITEM_TYPE_IMAGES;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_LOCATIONS) {
            return ITEM_TYPE_LOCATIONS;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_PDF) {
            return ITEM_TYPE_PDF;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_SCHEMES) {
            return ITEM_TYPE_SCHEMES;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_CONSUMPTION_HISTORY) {
            return ITEM_TYPE_CONSUMPTION_HISTORY;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_PAYMENT_HISTORY) {
            return ITEM_TYPE_PAYMENT_HISTORY;
        } else if (list_discoveryInstantSearchModel.get(position).getItemtype() == ITEM_TYPE_PHONE) {
            return ITEM_TYPE_PHONE;
        } else {
            return ITEM_TYPE_DIVIDER;
        }


    }

    private class QuestionsViewHolder extends RecyclerView.ViewHolder {
        FlexboxLayout flexContainer;

        public QuestionsViewHolder(View itemView) {
            super(itemView);
            flexContainer = itemView.findViewById(R.id.flexContainer);
        }
    }

    private class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserChat;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            tvUserChat = itemView.findViewById(R.id.tvUserChat);
        }
    }

    private class QuestionUserViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserChat, tvTitle;
        ImageView ivForward;
        RadioGroup radioGroup;
        RadioButton rbLike;
        RadioButton rbdisLike;
        TextView tvDdate;

        public QuestionUserViewHolder(View itemView) {
            super(itemView);
            tvUserChat = itemView.findViewById(R.id.tvUserChat);
            ivForward = itemView.findViewById(R.id.ivForward);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            rbLike = itemView.findViewById(R.id.rbLike);
            rbdisLike = itemView.findViewById(R.id.rbdisLike);
            tvDdate = itemView.findViewById(R.id.tvDatetime);
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImages;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ivImages = itemView.findViewById(R.id.ivImages);
        }
    }

    private class LocationViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llContainer;

        public LocationViewHolder(View itemView) {
            super(itemView);
            llContainer = itemView.findViewById(R.id.llContainer);
        }
    }

    private class PhoneViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llContainer;
        RadioGroup radioGroup;
        RadioButton rbLike;
        RadioButton rbdisLike;
        TextView tvDatetime;

        public PhoneViewHolder(View itemView) {
            super(itemView);
            llContainer = itemView.findViewById(R.id.llContainer);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            rbLike = itemView.findViewById(R.id.rbLike);
            rbdisLike = itemView.findViewById(R.id.rbdisLike);
            tvDatetime = itemView.findViewById(R.id.tvDatetime);
        }
    }

    private class PDFViewHolder extends RecyclerView.ViewHolder {
        TextView tvFilename, tvTitle;
        RelativeLayout rlMainview;
        RadioGroup radioGroup;
        RadioButton rbLike;
        RadioButton rbdisLike;
        TextView tvDatetime;

        public PDFViewHolder(View itemView) {
            super(itemView);
            tvFilename = itemView.findViewById(R.id.tvFilename);
            rlMainview = itemView.findViewById(R.id.rlMainview);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            rbLike = itemView.findViewById(R.id.rbLike);
            rbdisLike = itemView.findViewById(R.id.rbdisLike);
            tvDatetime = itemView.findViewById(R.id.tvDatetime);

        }
    }

    private class SchemesViewHolder extends RecyclerView.ViewHolder {
        RadioGroup radioGroup;
        RadioButton rbLike;
        RadioButton rbdisLike;
        LinearLayout llContainers;
        TextView tvDate;

        public SchemesViewHolder(View itemView) {
            super(itemView);
            llContainers = itemView.findViewById(R.id.llContainers);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            rbLike = itemView.findViewById(R.id.rbLike);
            rbdisLike = itemView.findViewById(R.id.rbdisLike);
            tvDate = itemView.findViewById(R.id.tvDatetime);
        }
    }

    private class ConsumptionHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView strmon2;
        private TextView strbill2;

        private TextView strpmt2;
        private TextView strdate2;
        private TextView strmon3;
        private TextView strbill3;
        private TextView strpmt3;
        private TextView strdate3;
        private TextView strmon4;
        private TextView strbill4;
        private TextView strpmt4;
        private TextView strdate4;
        private TextView strmon5;
        private TextView strbill5;
        private TextView strpmt5;
        private TextView strdate5;
        private TextView strmon6;
        private TextView strbill6;
        private TextView strpmt6;
        private TextView strdate6;
        private TextView strmon1;
        private TextView strbill1;
        private TextView strpmt1;
        private TextView strdate1;

        private TextView strbilldt1;
        private TextView strbilldt2;
        private TextView strbilldt3;
        private TextView strbilldt4;
        private TextView strbilldt5;
        private TextView strbilldt6;
        RadioGroup radioGroup;
        RadioButton rbLike;
        RadioButton rbdisLike;
        TextView tvDatetime;

        public ConsumptionHistoryViewHolder(View itemView) {
            super(itemView);
            tvDatetime = itemView.findViewById(R.id.tvDatetime);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            rbLike = itemView.findViewById(R.id.rbLike);
            rbdisLike = itemView.findViewById(R.id.rbdisLike);
            strmon2 = itemView.findViewById(R.id.mon2);
            strdate2 = itemView.findViewById(R.id.untbl2);
            strbill2 = itemView.findViewById(R.id.bill2);
            strbilldt2 = itemView.findViewById(R.id.bdt2);
            strpmt2 = itemView.findViewById(R.id.pmt2);

            strmon3 = itemView.findViewById(R.id.mon3);
            strdate3 = itemView.findViewById(R.id.untbl3);
            strbill3 = itemView.findViewById(R.id.bill3);
            strbilldt3 = itemView.findViewById(R.id.bdt3);
            strpmt3 = itemView.findViewById(R.id.pmt3);

            strmon4 = itemView.findViewById(R.id.mon4);
            strdate4 = itemView.findViewById(R.id.untbl4);
            strbill4 = itemView.findViewById(R.id.bill4);
            strbilldt4 = itemView.findViewById(R.id.bdt4);
            strpmt4 = itemView.findViewById(R.id.pmt4);

            strmon5 = itemView.findViewById(R.id.mon5);
            strdate5 = itemView.findViewById(R.id.untbl5);
            strbill5 = itemView.findViewById(R.id.bill5);
            strbilldt5 = itemView.findViewById(R.id.bdt5);
            strpmt5 = itemView.findViewById(R.id.pmt5);

            strmon6 = itemView.findViewById(R.id.mon6);
            strdate6 = itemView.findViewById(R.id.untbl6);
            strbill6 = itemView.findViewById(R.id.bill6);
            strbilldt6 = itemView.findViewById(R.id.bdt6);
            strpmt6 = itemView.findViewById(R.id.pmt6);

            strmon1 = itemView.findViewById(R.id.mon1);
            strdate1 = itemView.findViewById(R.id.untbl1);
            strbill1 = itemView.findViewById(R.id.bill1);
            strbilldt1 = itemView.findViewById(R.id.bdt1);
            strpmt1 = itemView.findViewById(R.id.pmt1);
        }
    }

    private class PaymentHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView strmon2;
        private TextView strbill2;
        private TextView strpmt2;
        private TextView strdate2;
        private TextView strmon3;
        private TextView strbill3;
        private TextView strpmt3;
        private TextView strdate3;
        private TextView strmon4;
        private TextView strbill4;
        private TextView strpmt4;
        private TextView strdate4;
        private TextView strmon5;
        private TextView strbill5;
        private TextView strpmt5;
        private TextView strdate5;
        private TextView strmon6;
        private TextView strbill6;
        private TextView strpmt6;
        private TextView strdate6;
        private TextView strmon1;
        private TextView strbill1;
        private TextView strpmt1;
        private TextView strdate1;
        RadioGroup radioGroup;
        RadioButton rbLike;
        RadioButton rbdisLike;
        TextView tvDatetime;


        public PaymentHistoryViewHolder(View itemView) {
            super(itemView);
            tvDatetime = itemView.findViewById(R.id.tvDatetime);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            rbLike = itemView.findViewById(R.id.rbLike);
            rbdisLike = itemView.findViewById(R.id.rbdisLike);
            strmon2 = itemView.findViewById(R.id.mon2);
            strbill2 = itemView.findViewById(R.id.bill2);
            strpmt2 = itemView.findViewById(R.id.pmt2);
            strdate2 = itemView.findViewById(R.id.date2);
            strmon3 = itemView.findViewById(R.id.mon3);
            strbill3 = itemView.findViewById(R.id.bill3);
            strpmt3 = itemView.findViewById(R.id.pmt3);
            strdate3 = itemView.findViewById(R.id.date3);
            strmon4 = itemView.findViewById(R.id.mon4);
            strbill4 = itemView.findViewById(R.id.bill4);
            strpmt4 = itemView.findViewById(R.id.pmt4);
            strdate4 = itemView.findViewById(R.id.date4);
            strmon5 = itemView.findViewById(R.id.mon5);
            strbill5 = itemView.findViewById(R.id.bill5);
            strpmt5 = itemView.findViewById(R.id.pmt5);
            strdate5 = itemView.findViewById(R.id.date5);
            strmon6 = itemView.findViewById(R.id.mon6);
            strbill6 = itemView.findViewById(R.id.bill6);
            strpmt6 = itemView.findViewById(R.id.pmt6);
            strdate6 = itemView.findViewById(R.id.date6);

            strmon1 = itemView.findViewById(R.id.mon1);
            strbill1 = itemView.findViewById(R.id.bill1);
            strpmt1 = itemView.findViewById(R.id.pmt1);
            strdate1 = itemView.findViewById(R.id.date1);
        }
    }
}

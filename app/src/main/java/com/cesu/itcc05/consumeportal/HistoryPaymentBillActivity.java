package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import chart.chart.customfile.DemoBase;
import chart.chart.library.charts.CombinedChart;

import static java.lang.System.exit;


public class HistoryPaymentBillActivity  extends DemoBase {
//public class HistoryPaymentBillActivity  extends AppCompatActivity {
    private CombinedChart chart;
    private final int count = 6;
    private static TextView strmon2;
    private static TextView strbill2;

    private static TextView strpmt2;
    private static TextView strdate2;
    private static TextView strmon3;
    private static TextView strbill3;
    private static TextView strpmt3;
    private static TextView strdate3;
    private static TextView strmon4;
    private static TextView strbill4;
    private static TextView strpmt4;
    private static TextView strdate4;
    private static TextView strmon5;
    private static TextView strbill5;
    private static TextView strpmt5;
    private static TextView strdate5;
    private static TextView strmon6;
    private static TextView strbill6;
    private static TextView strpmt6;
    private static TextView strdate6;
    private static TextView strmon1;
    private static TextView strbill1;
    private static TextView strpmt1;
    private static TextView strdate1;
    private static TextView strconName;
    private String pipeDelBillInfo="";
    private static TextView strbilldt1;
    private static TextView strbilldt2;
    private static TextView strbilldt3;
    private static TextView strbilldt4;
    private static TextView strbilldt5;
    private static TextView strbilldt6;
    private String[] BillInfo=null;
    final Context context = this;
    private String[] finContentWrap =null;
    private  String consIDval="";
    private  String conname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_payment_bill);
       // Button btnback = (Button) findViewById(R.id.back);
        strconName =(TextView)findViewById(R.id.conName);
        strmon2 =(TextView)findViewById(R.id.mon2);
        strbill2=(TextView)findViewById(R.id.bill2);
        strbilldt2=(TextView)findViewById(R.id.bdt2);
        strpmt2=(TextView)findViewById(R.id.pmt2);
        strdate2=(TextView)findViewById(R.id.pmtdt2);

        strmon3 =(TextView)findViewById(R.id.mon3);
        strbill3=(TextView)findViewById(R.id.bill3);
        strbilldt3=(TextView)findViewById(R.id.bdt3);
        strpmt3=(TextView)findViewById(R.id.pmt3);
        strdate3=(TextView)findViewById(R.id.pmtdt3);

        strmon4 =(TextView)findViewById(R.id.mon4);
        strbill4=(TextView)findViewById(R.id.bill4);
        strbilldt4=(TextView)findViewById(R.id.bdt4);
        strpmt4=(TextView)findViewById(R.id.pmt4);
        strdate4=(TextView)findViewById(R.id.pmtdt4);

        strmon5 =(TextView)findViewById(R.id.mon5);
        strbill5=(TextView)findViewById(R.id.bill5);
        strbilldt5=(TextView)findViewById(R.id.bdt5);
        strpmt5=(TextView)findViewById(R.id.pmt5);
        strdate5=(TextView)findViewById(R.id.pmtdt5);

        strmon6 =(TextView)findViewById(R.id.mon6);
        strbill6=(TextView)findViewById(R.id.bill6);
        strbilldt6=(TextView)findViewById(R.id.bdt6);
        strpmt6=(TextView)findViewById(R.id.pmt6);
        strdate6=(TextView)findViewById(R.id.pmtdt6);

        strmon1 =(TextView)findViewById(R.id.mon1);
        strbill1=(TextView)findViewById(R.id.bill1);
        strbilldt1=(TextView)findViewById(R.id.bdt1);
        strpmt1=(TextView)findViewById(R.id.pmt1);
        strdate1=(TextView)findViewById(R.id.pmtdt1);




       // Button btnback = (Button) findViewById(R.id.back);
        Bundle parmtrDet = getIntent().getExtras();
        pipeDelBillInfo = parmtrDet.getString("pipeDelBillInfo");
        consIDval = parmtrDet.getString("consIDval");
        conname = parmtrDet.getString("conname");
        BillInfo = pipeDelBillInfo.split("[|]");
        if(BillInfo[0].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("User Not Found");
            alertDialogBuilder.setMessage("User Not Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HistoryPaymentBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else if(BillInfo[1].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Report ID Not Found");
            alertDialogBuilder.setMessage("Report IDNot Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HistoryPaymentBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else if(BillInfo[2].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Record Not Found");
            alertDialogBuilder.setMessage("Record Not Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HistoryPaymentBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else {
            SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
            String ConsID = sessiondata.getString("ConsID", null); // getting String
           // strconName.setText("Consumer ID: " + ConsID);
            strconName.setText(conname +"\n"+"("+consIDval+")");
            int arrayparam=70;
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
            finContentWrap = new String[arrayparam];
            for (int i = 0; i < arrayparam; i++) {
                finContentWrap[i] = ContentWrap[i];
                if (ContentWrap[i] == null) {
                    finContentWrap[i] = "-";
                }
            }

            strmon1.setText(finContentWrap[0]);
            strmon2.setText(finContentWrap[10]);
            strmon3.setText(finContentWrap[20]);
            strmon4.setText(finContentWrap[30]);
            strmon5.setText(finContentWrap[40]);
            strmon6.setText(finContentWrap[50]);

            strbill1.setText(finContentWrap[9]); //new Double( ).intValue()
            strbill2.setText(finContentWrap[19]);
            strbill3.setText(finContentWrap[29]);
            strbill4.setText(finContentWrap[39]);
            strbill5.setText(finContentWrap[49]);
            strbill6.setText(finContentWrap[59]);

            strbilldt1.setText(finContentWrap[3]);
            strbilldt2.setText(finContentWrap[13]);
            strbilldt3.setText(finContentWrap[23]);
            strbilldt4.setText(finContentWrap[43]);
            strbilldt5.setText(finContentWrap[53]);
            strbilldt6.setText(finContentWrap[63]);

            strpmt1.setText(finContentWrap[5]);
            strpmt2.setText(finContentWrap[15]);
            strpmt3.setText(finContentWrap[25]);
            strpmt4.setText(finContentWrap[35]);
            strpmt5.setText(finContentWrap[45]);
            strpmt6.setText(finContentWrap[55]);

            strdate1.setText(finContentWrap[6]);
            strdate2.setText(finContentWrap[16]);
            strdate3.setText(finContentWrap[26]);
            strdate4.setText(finContentWrap[36]);
            strdate5.setText(finContentWrap[46]);
            strdate6.setText(finContentWrap[56]);

            ////////////////chart start////////////////////////
            final String[] months1 = new String[]{
                    finContentWrap[50],  "  "+finContentWrap[40],  "  "+finContentWrap[30],  "  "+finContentWrap[20],  "  "+finContentWrap[10], "  "+finContentWrap[0], "", "", "", "", "", ""
            };
            chart = (CombinedChart) findViewById(R.id.chart1);
            chart.setVisibility(View.INVISIBLE);
            //chart.setNoDataText("");
             /*
            chart.getDescription().setEnabled(false);
            chart.setBackgroundColor(Color.WHITE);
            chart.setDrawGridBackground(false);
            chart.setDrawBarShadow(false);
            chart.setHighlightFullBarEnabled(false);

            // draw bars behind lines
            chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                    CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.BUBBLE
            });

            Legend l = chart.getLegend();
            l.setWordWrapEnabled(true);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);

            YAxis rightAxis = chart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            YAxis leftAxis = chart.getAxisLeft();
            leftAxis.setDrawGridLines(false);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    Log.d("DemoApp", "value % months.length " + value % months.length);
                    Log.d("DemoApp", "value  " + value);
                    Log.d("DemoApp", "months.length " + months.length);
                    return months1[(int) value % months1.length];

                }
            });

            CombinedData data = new CombinedData();

            data.setData(generateLineData());
            data.setData(generateBarData());
            // data.setData(generateBubbleData());
            //  data.setData(generateScatterData());
            //  data.setData(generateCandleData());
            data.setValueTypeface(tfLight);

            xAxis.setAxisMaximum(data.getXMax() + 0.45f);

            chart.setData(data);
            chart.invalidate();
            */
        }
        /*
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserDashboard = new Intent(getApplicationContext(), HistoryDetActivity.class);
                startActivity(UserDashboard);
                finish();
            }
        });
        */
    }
/*
    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();
        int linedata= 59;
        int valdata=0;
        for (int index = 0; index < 6; index++) {
            if(finContentWrap[linedata].equals("-")){
                valdata=0;
            }else{
                valdata= new Double(finContentWrap[linedata]).intValue();
               // valdata = Math.round(Integer.parseInt(finContentWrap[linedata]));
                Log.d("DemoApp", "months.length " + months.length);
            }
            entries.add(new Entry(index + 0.5f, valdata));
            linedata=linedata-10;
        }
        LineDataSet set = new LineDataSet(entries, "Billing");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        int linedata= 59;
        int linedata1=55;
        int valdata=0;
        for (int index = 0; index < count; index++) {
            valdata=0;
            if(finContentWrap[linedata].equals("-")){
                valdata=0;
            }else{
                valdata= new Double(finContentWrap[linedata]).intValue();
              //  valdata=Math.round(Integer.parseInt(finContentWrap[linedata]));
            }
            entries1.add(new BarEntry(0, valdata));
            linedata=linedata-10;
            valdata=0;
            if(finContentWrap[linedata1].equals("-")){
                valdata=0;
            }else{
                valdata= new Double(finContentWrap[linedata1]).intValue();
             //   valdata=Math.round(Integer.parseInt(finContentWrap[linedata1]));
            }
            // stacked
            entries2.add(new BarEntry(0, valdata));
            linedata1=linedata1-10;
        }

        BarDataSet set1 = new BarDataSet(entries1, "Billing");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "Payment");
        set2.setStackLabels(new String[]{"Payment"});
        set2.setColors(Color.rgb(61, 165, 255));
        set2.setValueTextColor(Color.rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }
    private BubbleData generateBubbleData() {

        BubbleData bd = new BubbleData();
        ArrayList<BubbleEntry> entries = new ArrayList<>();
        int linedata= 5;
        int linedata1= 7;
        for (int index = 0; index < count; index++) {
            float y = Integer.parseInt(BillInfo[linedata1]);
            float size = Integer.parseInt(BillInfo[linedata]);
            entries.add(new BubbleEntry(index + 0.5f, y, size));
            linedata=linedata+5;
            linedata1=linedata1+5;
        }

        BubbleDataSet set = new BubbleDataSet(entries, "Bubble DataSet");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.WHITE);
        set.setHighlightCircleWidth(1.5f);
        set.setDrawValues(true);
        bd.addDataSet(set);

        return bd;
    }
    */
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
    @Override
    public void saveToGallery() { /* Intentionally left empty */ }
}

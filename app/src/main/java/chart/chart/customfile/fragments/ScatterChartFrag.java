package chart.chart.customfile.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesu.itcc05.consumeportal.R;

import chart.chart.customfile.custom.MyMarkerView;
import chart.chart.library.charts.ScatterChart;
import chart.chart.library.components.Legend;
import chart.chart.library.components.XAxis;
import chart.chart.library.components.YAxis;


public class ScatterChartFrag extends SimpleFragment {

    @NonNull
    public static Fragment newInstance() {
        return new ScatterChartFrag();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private ScatterChart chart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_simple_scatter, container, false);

        chart = (ScatterChart) v.findViewById(R.id.scatterChart1);
        chart.getDescription().setEnabled(false);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.chart_custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv);

        chart.setDrawGridBackground(false);
        chart.setData(generateScatterData(6, 10000, 200));

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tf);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setDrawGridLines(false);

        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setTypeface(tf);
        l.setFormSize(14f);
        l.setTextSize(9f);

        // increase the space between legend & bottom and legend & content
        l.setYOffset(13f);
        chart.setExtraBottomOffset(16f);

        return v;
    }
}

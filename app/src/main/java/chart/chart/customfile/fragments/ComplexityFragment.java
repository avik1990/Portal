package chart.chart.customfile.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesu.itcc05.consumeportal.R;

import chart.chart.library.charts.LineChart;
import chart.chart.library.components.Legend;
import chart.chart.library.components.XAxis;
import chart.chart.library.components.YAxis;


public class ComplexityFragment extends SimpleFragment {

    @NonNull
    public static Fragment newInstance() {
        return new ComplexityFragment();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private LineChart chart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chartfrag_simple_line, container, false);

        chart = (LineChart) v.findViewById(R.id.lineChart1);

        chart.getDescription().setEnabled(false);

        chart.setDrawGridBackground(false);

        chart.setData(getComplexity());
        chart.animateX(3000);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");

        Legend l = chart.getLegend();
        l.setTypeface(tf);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tf);

        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        return v;
    }
}

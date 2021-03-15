package chart.chart.library.interfaces.dataprovider;


import chart.chart.library.components.YAxis;
import chart.chart.library.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}

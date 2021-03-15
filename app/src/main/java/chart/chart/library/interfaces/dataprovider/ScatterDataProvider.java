package chart.chart.library.interfaces.dataprovider;


import chart.chart.library.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}

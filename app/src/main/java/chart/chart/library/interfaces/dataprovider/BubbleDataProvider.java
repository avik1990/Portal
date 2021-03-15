package chart.chart.library.interfaces.dataprovider;


import chart.chart.library.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}

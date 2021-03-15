package chart.chart.library.interfaces.dataprovider;


import chart.chart.library.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}

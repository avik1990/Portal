package chart.chart.library.interfaces.dataprovider;


import chart.chart.library.components.YAxis;
import chart.chart.library.data.BarLineScatterCandleBubbleData;
import chart.chart.library.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);
    boolean isInverted(YAxis.AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}

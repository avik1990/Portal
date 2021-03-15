package chart.chart.customfile.custom;


import chart.chart.library.components.AxisBase;
import chart.chart.library.formatter.ValueFormatter;

/**
 * Created by Philipp Jahoda on 14/09/15.
 */
@SuppressWarnings("unused")
public class YearXAxisFormatter extends ValueFormatter
{

    private final String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    public YearXAxisFormatter() {
        // take parameters to change behavior of formatter
    }


    public String getAxisLabel(float value, AxisBase axis) {

        float percent = value / axis.mAxisRange;
        return mMonths[(int) (mMonths.length * percent)];
    }
}

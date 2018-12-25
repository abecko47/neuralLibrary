import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;

public class Chart {
    public void draw(ArrayList<ArrayList<Double>> input, ArrayList<ArrayList<Double>> output, String title) {
        XYChart Chart = new XYChartBuilder().width(600).height(500).title("ALLASLDSLD").xAxisTitle("i1").yAxisTitle("i2").build();

        Chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        Chart.getStyler().setChartTitleVisible(false);
        Chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        Chart.getStyler().setMarkerSize(8);

        double[] xDataO1 = new double[input.size()];
        double[] yDataO1 = new double[input.size()];

        double[] xDataO2 = new double[input.size()];
        double[] yDataO2 = new double[input.size()];
        for (int i = 0; i < input.size(); i++) {
            if (output.get(i).get(0) < 0.4 || output.get(i).get(0) > 0.999) {
                xDataO1[i] = input.get(i).get(0);
                yDataO1[i] = input.get(i).get(1);
            } else if (output.get(i).get(0) > 0.8 && output.get(i).get(0) < 0.99){
                xDataO2[i] = input.get(i).get(0);
                yDataO2[i] = input.get(i).get(1);
            } else {
                xDataO1[i] = 0;
                yDataO1[i] = 0;
                xDataO2[i] = 0;
                yDataO2[i] = 0;

            }

        }
        XYSeries zeros = Chart.addSeries("Zeros", xDataO1, yDataO1);
        XYSeries ones = Chart.addSeries("Ones", xDataO2, yDataO2);

        SwingWrapper drawChart = new SwingWrapper(Chart);
        drawChart.displayChart(title);
    }
}

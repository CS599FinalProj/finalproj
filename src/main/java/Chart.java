import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class Chart extends ApplicationFrame {

    //private static final long serialVersionUID = 1L;

    public Chart(String title, ArrayList<Double> positive, ArrayList<Double> negative, ArrayList<String> name) {
        super(title);
        CategoryDataset dataset = createDataset(positive, negative, name);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(600, 480));
        setContentPane(chartPanel);
    }

    private static CategoryDataset createDataset(ArrayList<Double> positive, ArrayList<Double> negative, ArrayList<String> name) {

        String series1 = "Positive";
        String series2 = "Negative";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(int i=0; i<name.size(); i++)
        {
            dataset.addValue(positive.get(i), series1, name.get(i));
            dataset.addValue(negative.get(i), series2, name.get(i));
        }

        return dataset;
    }

    private static JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart chart = ChartFactory
                .createBarChart("Result", "Testing Data", "Percentage", dataset,
                        PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();


        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(new DecimalFormat("0%"));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);


        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f,
                0.0f, new Color(0, 64, 0));
        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f,
                0.0f, new Color(64, 0, 0));
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);

        return chart;
    }
}
package shortestjobfirst;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;

public class Gantt extends ApplicationFrame {

    static int m1[][];
    static int m2[][];
    static int procesos;

    public Gantt(final String title, int m1[][], int m2[][], int procesos) {
        super(title);
        this.m1 = m1;
        this.m2 = m2;
        this.procesos = procesos;
        final IntervalCategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1215, 300));
        setContentPane(chartPanel);
    }

    public static IntervalCategoryDataset createDataset() {

        final TaskSeriesCollection collection = new TaskSeriesCollection();

        final TaskSeries s1 = new TaskSeries("Ráfaga");
        final TaskSeries s2 = new TaskSeries("Espera");

        for (int i = 0; i < procesos; i++) {
            s1.add(new Task("Proceso (" + (i + 1) + ")", new SimpleTimePeriod(m1[i][0], m1[i][1])));
            s2.add(new Task("Proceso (" + (i + 1) + ")", new SimpleTimePeriod(m2[i][0], m2[i][1])));
        }
        collection.add(s1);
        collection.add(s2);

        return collection;
    }

    private JFreeChart createChart(final IntervalCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createGanttChart(
                "Diagrama de Gantt", // chart title
                "Procesos", // domain axis label
                "Tiempo", // range axis label
                dataset, // data
                true, // include legend
                true, // tooltips
                false // urls
        );
        return chart;
    }

}

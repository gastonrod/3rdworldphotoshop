package view.components;

import javafx.scene.chart.*;

public class ImageHistogram extends BarChart<String, Number>{


    public ImageHistogram(int[] repetitions) {
        super(new CategoryAxis(), new NumberAxis());
        CategoryAxis xAxis = (CategoryAxis)getXAxis();
        NumberAxis yAxis = (NumberAxis)getYAxis();
        xAxis.setLabel("Color");
        yAxis.setLabel("Repetitions");
        XYChart.Series series1 = new XYChart.Series();
        for(int i = 0; i < repetitions.length; i++) {
            series1.getData().add(new XYChart.Data(Integer.toString(i),repetitions[i]));
        }
        getData().add(series1);
    }
}

package com.elpresidente.ui.graphical;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;

public class GameController {

    public PieChart pie;
    public BarChart<String, Integer> barChart;

    @FXML
    public void initialize() {
        pie.getData().add(new PieChart.Data("Industry", 0));
        pie.getData().add(new PieChart.Data("Agriculture", 0));

    }

}

package com.elpresidente.ui.graphical;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameController {

    public PieChart pie;
    public BarChart<String, Integer> barChart;
    public VBox vBox;

    public BarChart<Integer, String> foodChart;
    public BarChart<Integer, String> treasuryChart;
    public BorderPane borderPane;
    public HBox hBox;

    @FXML
    public void initialize() {
        pie.getData().add(new PieChart.Data("Industry", 0));
        pie.getData().add(new PieChart.Data("Agriculture", 0));

        XYChart.Series<Integer, String> series = new XYChart.Series<>();
        series.setName("");
        series.getData().add(new XYChart.Data<>(0, ""));
        foodChart.getData().add( series );

        series = new XYChart.Series<>();
        series.setName("");
        series.getData().add(new XYChart.Data<>(0, ""));
        treasuryChart.getData().add( series);

    }

    public void addEven(Pane pane){
        if(hBox.getChildren().size() == 0){
            hBox.getChildren().add(pane);
        }else{
            hBox.getChildren().set(0, pane);
        }

    }

}

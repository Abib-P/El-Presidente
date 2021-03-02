package com.elpresidente.ui.graphical;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameController {

    public PieChart pie;
    public BarChart<String, Integer> barChart;
    public VBox vBox;

    public Label treasuryLabel;
    public Label foodLabel;

    @FXML
    public void initialize() {
        pie.getData().add(new PieChart.Data("Industry", 0));
        pie.getData().add(new PieChart.Data("Agriculture", 0));

    }

    public void addEven(Pane pane){
        if(vBox.getChildren().size() <= 2){
            vBox.getChildren().add( pane);
        }else{
            vBox.getChildren().set(2, pane);
        }
    }

}

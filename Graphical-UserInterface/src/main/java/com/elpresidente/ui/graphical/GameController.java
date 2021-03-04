package com.elpresidente.ui.graphical;

import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameController {

    public PieChart pieChart;
    public BarChart<String, Integer> barChart;
    public VBox vBox;

    public BarChart<Integer, String> foodChart;
    public BarChart<Integer, String> treasuryChart;
    public BorderPane borderPane;
    public HBox hBox;
    public Label actionLabel;
    public Label seasonLabel;

    @FXML
    public void initialize() {
        pieChart.getData().add(new PieChart.Data("Industry", 0));
        pieChart.getData().add(new PieChart.Data("Agriculture", 0));

        XYChart.Series<Integer, String> series = new XYChart.Series<>();
        series.setName("");
        series.getData().add(new XYChart.Data<>(0, ""));
        foodChart.getData().add( series );

        series = new XYChart.Series<>();
        series.setName("");
        series.getData().add(new XYChart.Data<>(0, ""));
        treasuryChart.getData().add( series);

        XYChart.Series<String, Integer> satisfactionSeries, partisanSeries;

        ObservableList< XYChart.Series<String, Integer>> data = barChart.getData();
        satisfactionSeries = new XYChart.Series<>();
        satisfactionSeries.setName("Satisfaction");

        partisanSeries = new XYChart.Series<>();
        partisanSeries.setName("Partisan number");

        data.add( satisfactionSeries);
        data.add( partisanSeries);

    }

    public void updateGameInfo(Game game){
        pieChart.getData().get(0).setPieValue( game.getIndustries());
        pieChart.getData().get(1).setPieValue( game.getAgriculture());

        XYChart.Data< Integer, String> data = foodChart.getData().get(0).getData().get(0);
        data.setXValue( game.getFood());
        data = treasuryChart.getData().get(0).getData().get(0);
        data.setXValue( game.getTreasury());

        displayFactionsInfo(game.getFactionManager() );
    }

    private void displayFactionsInfo(Factions factions){

        XYChart.Series<String, Integer> satisfactionSeries, partisanSeries;

        satisfactionSeries = barChart.getData().get(0);
        partisanSeries = barChart.getData().get(1);

        if( barChart.getData().get(0).getData().isEmpty() ){
            for (Faction faction : factions.getFactions()) {
                satisfactionSeries.getData().add(new XYChart.Data<>(faction.getName(), faction.getSatisfaction()) );
                partisanSeries.getData().add(new XYChart.Data<>(faction.getName(), faction.getPartisanNumber()) );
            }
        }

        for (XYChart.Data<String, Integer> data : satisfactionSeries.getData() ) {
            data.setYValue( factions.getFactionByName(data.getXValue()).getSatisfaction() );
        }
        for (XYChart.Data<String, Integer> data : partisanSeries.getData() ) {
            data.setYValue( factions.getFactionByName(data.getXValue()).getPartisanNumber() );
        }

        for (Faction faction: factions.getFactions()) {
            System.out.println(faction.getName()+": "+faction.getSatisfaction()+"%");
        }
    }

    public void setEvent(Pane pane){
        if(hBox.getChildren().size() == 0){
            hBox.getChildren().add(pane);
        }else{
            hBox.getChildren().set(0, pane);
        }

    }

}

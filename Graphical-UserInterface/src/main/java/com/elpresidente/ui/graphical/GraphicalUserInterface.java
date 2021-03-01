package com.elpresidente.ui.graphical;

import com.elpresidente.event.*;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;
import com.elpresidente.game.Saisons;
import com.elpresidente.ui.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * JavaFX GraphicalUserInterface
 */
public class GraphicalUserInterface extends Application implements UserInterface {

    public static GraphicalUserInterface ui = null;

    private static Scene scene;
    public static GameController controller = null;

    public GraphicalUserInterface() {
        ui = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("gameController"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        Parent parent;
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource(fxml + ".fxml"));
        parent =  fxmlLoader.load();

        controller = (GameController) fxmlLoader.getController();
        System.out.println("test controller: "+ controller);

        return parent;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void displayGameInfo(Game game) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                PieChart chart = GraphicalUserInterface.controller.pie;
                chart.getData().get(0).setPieValue( game.getIndustries());
                chart.getData().get(1).setPieValue( game.getAgriculture());
            }
        });

        System.out.println("money: "+game.getTreasury()+"\nfood: "+ game.getFood()+"\nIndustry: "+game.getIndustries()+"% Agriculture: "+game.getAgriculture()+"%");
        displayFactionsInfo(game.getFactionManager());
    }
    private void displayFactionsInfo(Factions factions){
        BarChart<String, Integer> chart = GraphicalUserInterface.controller.barChart;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                XYChart.Series<String, Integer> series;

                if( chart.getData().isEmpty() ){
                    ObservableList< XYChart.Series<String, Integer>> data = chart.getData();
                    series = new XYChart.Series<String, Integer>();
                    series.setName("");
                    data.add( series);
                }else{
                    series = chart.getData().get(0);
                }

                if(series.getData().isEmpty()) {
                    for (Faction faction : factions.getFactions()) {
                        series.getData().add(new XYChart.Data<String, Integer>(faction.getName(), faction.getSatisfaction()));
                    }
                }else{
                    for (XYChart.Data<String, Integer> data : series.getData() ) {
                        data.setYValue( factions.getFactionByName(data.getXValue()).getSatisfaction() );
                    }
                }
            }
        });

        for (Faction faction: factions.getFactions()) {
            System.out.println(faction.getName()+": "+faction.getSatisfaction()+"%");
        }
    }

    @Override
    public void displayTreasury(int treasury) {

    }

    @Override
    public void displayAgriculture(int agriculture) {

    }

    @Override
    public void displayIndustry(int industry) {

    }

    @Override
    public void displayFactions(Factions factions) {

    }

    @Override
    public void displayMarket(int food, int necessaryFood) {

    }

    @Override
    public String selectScenario(Map<String, String> AllScenarioNames) {

        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {

                List<String> names = new ArrayList<String>(AllScenarioNames.keySet() );
                FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource("scenarioSelection.fxml"));
                Pane pane = fxmlLoader.load();

                scenarioSelectionController controller = (scenarioSelectionController) fxmlLoader.getController();
                controller.choiceBox.getItems().addAll(names);
                controller.choiceBox.setValue( names.get(0) );

                Dialog<String> dialog = new Dialog<String>( );
                dialog.setDialogPane((DialogPane) pane);

                dialog.showAndWait();

                return AllScenarioNames.get(controller.choiceBox.getValue());
            }
        };

        Platform.runLater(task);

        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean askForReplay() {
        return false;
    }

    @Override
    public float askForDifficulty() {
        return 0.5f;
    }

    @Override
    public void displaySeason(Saisons season) {

    }

    @Override
    public Choice getChoice(Event event) {

        Task<Choice> task = new Task<>() {
            @Override
            protected Choice call() throws Exception {

                FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource("eventChoiceSelector.fxml"));

                Pane pane = fxmlLoader.load();
                DialogPane dialogPane = fxmlLoader.getRoot();
                dialogPane.setHeaderText(event.getName());

                EventChoiceSelector controller = (EventChoiceSelector) fxmlLoader.getController();
                controller.choiceBox.getItems().addAll( event.getChoices());
                controller.choiceBox.setValue( event.getChoices().get(0) );

                Dialog<Choice> dialog = new Dialog<>( );
                dialog.setDialogPane((DialogPane) pane);

                dialog.showAndWait();

                return event.getChoices().get(0);
            }
        };

        Platform.runLater(task);

        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Faction selectFaction(Factions factions) {
        return factions.getFactions().get(0);
    }

    @Override
    public Faction selectFactionToCorrupt(Factions factions, int treasury) {
        return null;
    }

    @Override
    public int getMarketAmount(int treasury) {
        return 0;
    }
}

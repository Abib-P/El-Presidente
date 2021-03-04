package com.elpresidente.ui.graphical;

import com.elpresidente.event.*;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;
import com.elpresidente.game.Saisons;
import com.elpresidente.ui.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
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
    private Stage stage;

    private Scene gameControllerPane;
    public GameController gameController = null;

    private Scene scenarioSelectionScene;
    private final ScenarioSelectionController scenarioSelectionController;

    private Pane eventSelectorPane;
    private final EventChoiceSelector eventChoiceSelector;

    private Pane marketPane;
    private final MarketController marketController;

    private Pane factionCorruptionPane;
    private final FactionCorruptionController factionCorruptionController;

    public GraphicalUserInterface() {
        ui = this;

        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource("scenarioSelection.fxml"));
        try {
            scenarioSelectionScene = new Scene( fxmlLoader.load() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        scenarioSelectionController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource("gameController.fxml"));
        try {
            gameControllerPane = new Scene( fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource("eventChoiceSelector.fxml"));
        try {
            eventSelectorPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        eventChoiceSelector = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource("factionCorruption.fxml"));
        try {
            factionCorruptionPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        factionCorruptionController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource("market.fxml"));
        try {
            marketPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        marketController = fxmlLoader.getController();

    }

    @Override
    public void start(Stage stage) throws IOException {
       this.stage = stage;
       this.stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        Parent parent;
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource(fxml + ".fxml"));
        parent =  fxmlLoader.load();

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
                PieChart chart = gameController.pie;
                chart.getData().get(0).setPieValue( game.getIndustries());
                chart.getData().get(1).setPieValue( game.getAgriculture());

                XYChart.Data< Integer, String> data = gameController.foodChart.getData().get(0).getData().get(0);
                data.setXValue( game.getFood());
                data = gameController.treasuryChart.getData().get(0).getData().get(0);
                data.setXValue( game.getTreasury());

            }
        });

        System.out.println("money: "+game.getTreasury()+"\nfood: "+ game.getFood()+"\nIndustry: "+game.getIndustries()+"% Agriculture: "+game.getAgriculture()+"%");
        displayFactionsInfo(game.getFactionManager());
    }
    private void displayFactionsInfo(Factions factions){
        BarChart<String, Integer> chart = gameController.barChart;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                XYChart.Series<String, Integer> satisfactionSeries, partisanSeries;

                if( chart.getData().isEmpty() ){
                    ObservableList< XYChart.Series<String, Integer>> data = chart.getData();
                    satisfactionSeries = new XYChart.Series<String, Integer>();
                    satisfactionSeries.setName("Satisfaction");

                    partisanSeries = new XYChart.Series<String, Integer>();
                    partisanSeries.setName("Partisan number");

                    data.add( satisfactionSeries);
                    data.add( partisanSeries);

                    for (Faction faction : factions.getFactions()) {
                        satisfactionSeries.getData().add( new XYChart.Data<String, Integer>(faction.getName(), faction.getSatisfaction()) );
                        partisanSeries.getData().add( new XYChart.Data<String, Integer>(faction.getName(), faction.getPartisanNumber()) );
                    }
                }else{
                    satisfactionSeries = chart.getData().get(0);
                    partisanSeries = chart.getData().get(1);
                }

                for (XYChart.Data<String, Integer> data : satisfactionSeries.getData() ) {
                    data.setYValue( factions.getFactionByName(data.getXValue()).getSatisfaction() );
                }
                for (XYChart.Data<String, Integer> data : partisanSeries.getData() ) {
                    data.setYValue( factions.getFactionByName(data.getXValue()).getPartisanNumber() );
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
        String scenario;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                scenarioSelectionController.setData( AllScenarioNames);
                stage.setScene( scenarioSelectionScene);
                stage.sizeToScene();

            }
        });

        scenario = AllScenarioNames.get( scenarioSelectionController.getScenario());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene( gameControllerPane);
                stage.sizeToScene();
            }
        });

        return scenario;
    }

    @Override
    public boolean askForReplay() {
        return false;
    }

    @Override
    public float askForDifficulty() {
        return 1f;
    }

    @Override
    public void displaySeason(Saisons season) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.seasonLabel.setText( season.toString() );
            }
        });
    }

    @Override
    public Choice getChoice(Event event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.actionLabel.setText( "New Event" );
                gameController.addEven( eventSelectorPane);
                eventChoiceSelector.setEventChoice(event);
                stage.sizeToScene();
            }
        });

        return eventChoiceSelector.getEventChoice(event);
    }

    @Override
    public Faction selectFaction(Factions factions) {
        return factions.getFactions().get(0);
    }

    @Override
    public Faction selectFactionToCorrupt(Factions factions, int treasury) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                gameController.actionLabel.setText( "Corruption Time" );

                factionCorruptionController.setData(factions, treasury);

                gameController.addEven( factionCorruptionPane);
            }
        });

        return  factionCorruptionController.getFaction();
    }

    @Override
    public int getMarketAmount(int food, int necessaryFood,int treasury) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.actionLabel.setText( "Food Market" );
                marketController.setData(food, necessaryFood, treasury);
                gameController.addEven(marketPane);
            }
        });

        return marketController.getAmount();
    }
}

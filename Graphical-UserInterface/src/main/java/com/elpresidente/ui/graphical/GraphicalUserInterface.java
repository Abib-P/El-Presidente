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

    public static GameController controller = null;

    private static Scene scene;
    private Stage stage;

    private Pane eventSelectorPane;
    private final EventChoiceSelector eventChoiceSelector;

    private Pane marketPane;
    private final MarketController marketController;

    private HBox factionSelection = null;

    public GraphicalUserInterface() {
        ui = this;

        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource("eventChoiceSelector.fxml"));
        try {
            eventSelectorPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        eventChoiceSelector = fxmlLoader.getController();

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
        scene = new Scene(loadFXML("gameController"));
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
        stage.sizeToScene();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        Parent parent;
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource(fxml + ".fxml"));
        parent =  fxmlLoader.load();

        controller = fxmlLoader.getController();

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

                XYChart.Data< Integer, String> data = GraphicalUserInterface.controller.foodChart.getData().get(0).getData().get(0);
                data.setXValue( game.getFood());
                data = GraphicalUserInterface.controller.treasuryChart.getData().get(0).getData().get(0);
                data.setXValue( game.getTreasury());

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
        return 1f;
    }

    @Override
    public void displaySeason(Saisons season) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.seasonLabel.setText( season.toString() );
            }
        });
    }

    @Override
    public Choice getChoice(Event event) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.actionLabel.setText( "New Event" );
                controller.addEven( eventSelectorPane);
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
        final boolean[] selected = {false};
        final Faction[] selectedFaction = {null};

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                controller.actionLabel.setText( "Corruption Time" );
                factionSelection = new HBox();

                for (Faction faction: factions.getFactions() ) {

                    if(faction.getKey().equals( Factions.LoyalistsFactionKey))
                        continue;

                    Button button = new Button();

                    if( factions.areLoyalist()){
                        button.setText( faction.getName()+" ("+faction.getPartisanNumber()+"): "+faction.getCorruptionPrice()+"€\n Loyalists: "+ -faction.getCorruptionImpactOnLoyalist() );
                    }else{
                        button.setText( faction.getName()+" ("+faction.getPartisanNumber()+"): "+faction.getCorruptionPrice()+"€");
                    }

                    if(faction.getCorruptionPrice()<treasury && faction.getSatisfaction() < 100) {

                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                selectedFaction[0] = faction;
                                selected[0] = true;
                            }
                        });
                    }else{
                        button.setDisable(true);
                    }

                    factionSelection.getChildren().add( button);
                }

                Button button = new Button("finish");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        selectedFaction[0] = null;
                        selected[0] = true;
                    }
                });
                factionSelection.getChildren().add( button);

                controller.addEven( factionSelection);
            }
        });

        while ( !selected[0] ) {
            Thread.onSpinWait();
        }

        return  selectedFaction[0];
    }

    @Override
    public int getMarketAmount(int food, int necessaryFood,int treasury) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.actionLabel.setText( "Food Market" );
                marketController.setData(food, necessaryFood, treasury);
                controller.addEven(marketPane);
            }
        });

        return marketController.getAmount();
    }
}

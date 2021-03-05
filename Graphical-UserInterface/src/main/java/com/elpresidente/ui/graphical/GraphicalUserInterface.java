package com.elpresidente.ui.graphical;

import com.elpresidente.event.*;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;
import com.elpresidente.game.Saisons;
import com.elpresidente.ui.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

/**
 * JavaFX GraphicalUserInterface
 */
public class GraphicalUserInterface extends Application implements UserInterface {

    public static GraphicalUserInterface ui = null;

    private Stage stage;

    private Scene gameControllerPane;
    private final GameController gameController;

    private Scene scenarioSelectionScene;
    private final ScenarioSelectionController scenarioSelectionController;

    private Pane eventSelectorPane;
    private final EventChoiceSelector eventChoiceSelector;

    private Pane marketPane;
    private final MarketController marketController;

    private Pane factionCorruptionPane;
    private final FactionCorruptionController factionCorruptionController;

    private Pane replayPane;
    private final ReplayController replayConstroller;

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

        fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource("replay.fxml"));
        try {
            replayPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        replayConstroller = fxmlLoader.getController();

    }

    @Override
    public void start(Stage stage) {
       this.stage = stage;
       this.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void displayGameInfo(Game game) {

        Platform.runLater(() -> gameController.updateGameInfo(game));

        System.out.println("money: "+game.getTreasury()+"\nfood: "+ game.getFood()+"\nIndustry: "+game.getIndustries()+"% Agriculture: "+game.getAgriculture()+"%");
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
        Platform.runLater(() -> {
            scenarioSelectionController.setData( AllScenarioNames);
            stage.setScene( scenarioSelectionScene);
            stage.sizeToScene();

        });

        scenario = AllScenarioNames.get( scenarioSelectionController.getScenario());
        Platform.runLater(() -> {
            stage.setScene( gameControllerPane);
            stage.sizeToScene();
        });

        return scenario;
    }

    @Override
    public boolean askForReplay() {
        boolean answer;

        Platform.runLater(() -> {
            gameController.actionLabel.setText( "You Lose" );
            gameController.setEvent( replayPane);
            stage.sizeToScene();
        });

        answer =  replayConstroller.getAnswer();

        if( !answer){
            Platform.runLater(() -> stage.close());
        }

        return answer;
    }

    @Override
    public float askForDifficulty() {
        return 1f;
    }

    @Override
    public void displaySeason(Saisons season) {
        Platform.runLater(() -> gameController.seasonLabel.setText( season.toString() ));
    }

    @Override
    public Choice getChoice(Event event) {

        Platform.runLater(() -> {
            gameController.actionLabel.setText( "New Event" );
            gameController.setEvent( eventSelectorPane);
            eventChoiceSelector.setEventChoice(event);
            stage.sizeToScene();
        });

        return eventChoiceSelector.getEventChoice();
    }

    @Override
    public Faction selectFaction(Factions factions) {
        return factions.getFactions().get(0);
    }

    @Override
    public Faction selectFactionToCorrupt(Factions factions, int treasury) {

        Platform.runLater(() -> {

            gameController.actionLabel.setText( "Corruption Time" );

            factionCorruptionController.setData(factions, treasury);

            gameController.setEvent( factionCorruptionPane);
        });

        return  factionCorruptionController.getFaction();
    }

    @Override
    public int getMarketAmount(int food, int necessaryFood,int treasury) {
        Platform.runLater(() -> {
            gameController.actionLabel.setText( "Food Market" );
            marketController.setData(food, necessaryFood, treasury);
            gameController.setEvent(marketPane);
        });

        return marketController.getAmount();
    }
}

package com.elpresidente.ui.graphical;


import com.elpresidente.event.*;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;
import com.elpresidente.game.Saisons;
import com.elpresidente.ui.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * JavaFX GraphicalUserInterface
 */
public class GraphicalUserInterface extends Application implements UserInterface {

    public static GraphicalUserInterface ui = null;

    private static Scene scene;

    public GraphicalUserInterface() {
        ui = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
      //  scene = new Scene(loadFXML("select"));
      //  stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUserInterface.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void displayGameInfo(Game game) {

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
        return 0;
    }

    @Override
    public void displaySeason(Saisons season) {

    }

    @Override
    public Choice getChoice(Event event) {
        return null;
    }

    @Override
    public Faction selectFaction(Factions factions) {
        return null;
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
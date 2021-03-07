package com.elpresidente.ui.graphical;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import java.util.Map;

public class ScenarioSelectionController {

    private volatile boolean selected;

    @FXML
    public ChoiceBox< String > choiceBox;

    public void setData(Map<String, String> allScenarioNames){
        choiceBox.getItems().clear();
        for (Map.Entry<String, String> entry: allScenarioNames.entrySet() ) {
            choiceBox.getItems().add( entry.getKey() );
        }
        choiceBox.getSelectionModel().select(0);
        selected = false;
    }

    public String getScenario(){
        selected = false;
        while (!selected) Thread.onSpinWait();

        selected = false;

        return  choiceBox.getValue();
    }

    public void onSelect() {
        selected = true;
    }
}

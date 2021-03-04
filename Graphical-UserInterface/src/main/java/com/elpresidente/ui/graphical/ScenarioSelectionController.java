package com.elpresidente.ui.graphical;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScenarioSelectionController {

    private volatile boolean selected;

    @FXML
    public ChoiceBox<String> choiceBox;

    @FXML
    public void initialize() {
    }

    public void setData(Map<String, String> AllScenarioNames){
        List<String> names = new ArrayList<String>(AllScenarioNames.keySet() );
        choiceBox.getItems().addAll(names);
        choiceBox.setValue( names.get(0) );
        selected = false;
    }

    public String getScenario(){
        while (!selected) Thread.onSpinWait();

        selected = false;

        return choiceBox.getValue();
    }

    public void onSelect(ActionEvent actionEvent) {
        selected = true;
    }
}

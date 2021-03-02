package com.elpresidente.ui.graphical;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class EventChoiceSelector {

    @FXML
    public ChoiceBox<Choice> choiceBox;

    @FXML
    public Label label;

    private volatile boolean selected;

    @FXML
    public void initialize() {
        selected = false;
    }

    public void setEventChoice(Event event){
        label.setText( event.getName());
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll( event.getChoices());
        choiceBox.setValue( event.getChoices().get(0));
    }

    public Choice getEventChoice(Event event){

        while (!selected) Thread.onSpinWait();

        selected = false;

        return choiceBox.getValue();
    }


    public void onSelect(ActionEvent actionEvent) {
        selected = true;
    }
}

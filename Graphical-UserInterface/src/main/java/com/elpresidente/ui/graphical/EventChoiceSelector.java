package com.elpresidente.ui.graphical;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class EventChoiceSelector {

    @FXML
    public ChoiceBox<Choice> choiceBox;

    @FXML
    public Label label;
    public VBox vBox;

    private volatile boolean selected;

    @FXML
    public void initialize() {
       // vBox.prefWidthProperty().bind(borderPane.prefWidthProperty());
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

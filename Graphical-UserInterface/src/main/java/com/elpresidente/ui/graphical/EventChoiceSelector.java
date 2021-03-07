package com.elpresidente.ui.graphical;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.Map;

public class EventChoiceSelector {

    @FXML
    public Label choiceLabel;
    public ChoiceBox<Choice> choiceBox;
    @FXML
    public Label label;
    @FXML
    public VBox vBox;

    private volatile boolean selected;
    private boolean showAction = true;
    private float difficulty;

    @FXML
    public void initialize() {
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Choice>() {
            @Override
            public void changed(ObservableValue<? extends Choice> observableValue, Choice old, Choice newValue) {
                if( showAction){
                    StringBuilder text = new StringBuilder();

                    Choice choice = choiceBox.getValue();

                    if(choice == null)
                        return;

                    if(choice.getActionOnFaction() != null)
                        for (Map.Entry<String, Integer> entry: choice.getActionOnFaction().entrySet() ) {
                            text.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                        }

                    if(choice.getActionOnFactor() != null)
                        for (Map.Entry<String, Integer> entry: choice.getActionOnFactor().entrySet() ) {
                            text.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                        }

                    if( choice.getPartisanGained() != 0){
                        text.append("partisan: ").append(choice.getPartisanGained());
                    }

                    choiceLabel.setText(text.toString());
                }
            }
        });
        selected = false;
    }

    public void setEventChoice(Event event, float difficulty, boolean showAction){

        this.difficulty = difficulty;
        this.showAction = showAction;

        label.setText( event.getName());
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll( event.getChoices());
        choiceBox.setValue( event.getChoices().get(0));
    }

    public Choice getEventChoice(){

        while (!selected) Thread.onSpinWait();

        selected = false;

        return choiceBox.getValue();
    }


    public void onSelect() {
        selected = true;
    }

}

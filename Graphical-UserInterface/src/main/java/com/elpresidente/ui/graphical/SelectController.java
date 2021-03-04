package com.elpresidente.ui.graphical;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;

public class SelectController {

    @FXML
    public ChoiceBox<String> combo;

    @FXML
    public void initialize() {
        combo.getItems().removeAll(combo.getItems());
        combo.getItems().addAll("Console Interface", "Graphical Interface");
        combo.getSelectionModel().select("Graphical Interface");
    }

}

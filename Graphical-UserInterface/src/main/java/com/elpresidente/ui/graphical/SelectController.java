package com.elpresidente.ui.graphical;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectController {
    public ChoiceBox<String> combo;
    public DialogPane dialogPan;

    @FXML
    public void initialize() {
        combo.getItems().removeAll(combo.getItems());
        combo.getItems().addAll("Console Interface", "Graphical Interface");
        combo.getSelectionModel().select("Graphical Interface");
    }

}

package com.elpresidente.ui.graphical;

import java.io.IOException;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        GraphicalUserInterface.setRoot("primary");
    }
}
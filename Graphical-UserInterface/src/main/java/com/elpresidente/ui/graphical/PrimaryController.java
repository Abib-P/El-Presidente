package com.elpresidente.ui.graphical;

import java.io.IOException;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        GraphicalUserInterface.setRoot("secondary");
    }
}

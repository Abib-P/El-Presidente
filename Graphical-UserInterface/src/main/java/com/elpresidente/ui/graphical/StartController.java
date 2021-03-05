package com.elpresidente.ui.graphical;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class StartController {


    private volatile boolean selected;
    private boolean newGame;

    public void onLoad(ActionEvent actionEvent) {
        selected = true;
    }

    public void onNew(ActionEvent actionEvent) {
        selected = true;
        newGame = true;
    }

    public void onExit(ActionEvent actionEvent) {
        selected = true;
        Platform.exit();
        System.exit(0);
    }

    public boolean getAnswer(){
        selected = false;
        newGame = false;

        while (!selected) Thread.onSpinWait();

        return newGame;
    }
}

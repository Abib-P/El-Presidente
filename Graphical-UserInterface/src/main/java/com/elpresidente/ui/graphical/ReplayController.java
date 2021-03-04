package com.elpresidente.ui.graphical;

import javafx.event.ActionEvent;

public class ReplayController {
    private boolean replay = false;
    private volatile boolean selected = false;

    public void onNO(ActionEvent actionEvent) {
        selected = true;
    }

    public void onYes(ActionEvent actionEvent) {
        selected = true;
        replay = true;
    }

    public boolean getAnswer(){
        while (!selected) Thread.onSpinWait();
        selected = false;
        return replay;
    }
}

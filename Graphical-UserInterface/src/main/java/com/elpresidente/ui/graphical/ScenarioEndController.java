package com.elpresidente.ui.graphical;

public class ScenarioEndController {

    private boolean replay = false;
    private volatile boolean selected = false;

    public void onNO() {
        selected = true;
    }

    public void onYes() {
        selected = true;
        replay = true;
    }

    public boolean getAnswer(){
        while (!selected) Thread.onSpinWait();
        selected = false;
        return replay;
    }
}

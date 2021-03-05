package com.elpresidente.ui.graphical;

import javafx.event.ActionEvent;

public class RulesSelectionController {

    private volatile boolean selected, rules;

    public void onSandbox(ActionEvent actionEvent) {
        selected = true;
        rules = true;
    }

    public void onScenario(ActionEvent actionEvent) {
        selected = true;
    }

    public boolean getRule(){
        selected = false;
        rules = false;

        while (!selected) Thread.onSpinWait();

        return rules;

    }

}

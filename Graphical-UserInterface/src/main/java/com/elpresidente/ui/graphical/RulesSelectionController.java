package com.elpresidente.ui.graphical;

public class RulesSelectionController {

    private volatile boolean selected, rules;

    public void onSandbox() {
        selected = true;
        rules = true;
    }

    public void onScenario() {
        selected = true;
    }

    public boolean getRule(){
        selected = false;
        rules = false;

        while (!selected) Thread.onSpinWait();

        return rules;

    }

}

package com.elpresidente.ui.graphical;


import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;

public class DifficultyController {
    public Slider slider;
    public RadioButton radioButton;

    private volatile boolean selected;

    public void onSelect() {
        selected = true;
    }

    public void waitAnswer(){
        selected = false;
        while (!selected) Thread.onSpinWait();
    }

    public boolean getDisplayChoiceAction (){
        return  radioButton.isSelected();
    }

    public float getDifficulty(){
        return (float) slider.getValue();
    }
}

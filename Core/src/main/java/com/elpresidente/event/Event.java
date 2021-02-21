package com.elpresidente.event;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final String name;
    private List<Choice> choices;

    public Event(String name){
        this.name = name;
        this.choices = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void addChoice(Choice choice){
        choices.add(choice);
    }

    public void setChoices(List<Choice> choices){
        this.choices = choices;
    }
}

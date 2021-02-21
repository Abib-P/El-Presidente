package com.elpresidente.event;

import java.util.List;

public class Event {
    String name;
    List<Choice> choices;

    public Event(String name){
        this.name = name;
    }

    public void addChoice(Choice choice){
        choices.add(choice);
    }
}

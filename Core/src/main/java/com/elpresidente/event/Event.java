package com.elpresidente.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void setChoices(List<Choice> choices){
        this.choices = choices;
    }

    public static Map<String, Object> getEventToSave(Event event){
        Map<String, Object> eventToSave = new HashMap<>();
        eventToSave.put("name", event.getName());

        List<Map<String, Object>> choicesToSave = new ArrayList<>();
        for (Choice choice: event.getChoices()) {
            choicesToSave.add(choice.getChoiceToSave());
        }

        eventToSave.put("choices", choicesToSave);

        return eventToSave;
    }
}

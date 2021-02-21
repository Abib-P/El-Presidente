package com.elpresidente.event;

import java.util.Map;

public class Choice {
    String name;
    Event relatedEvent;
    Map<String,Integer> actionOnFaction;
    Map<String,Integer> actionOnFactor;
    Integer partisanGained;

    public Choice(String name, Map<String,Integer> actionOnFaction, Map<String,Integer> actionOnFactor, Integer partisanGained){
        this.name = name;
        this.actionOnFaction = actionOnFaction;
        this.actionOnFactor = actionOnFactor;
        this.partisanGained = partisanGained;
    }
}

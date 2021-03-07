package com.elpresidente.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Choice {
    String name;
    List<Event> relatedEvent;
    Map<String,Integer> actionOnFaction;
    Map<String,Integer> actionOnFactor;
    Integer partisanGained;

    public Choice(String name, Map<String,Integer> actionOnFaction, Map<String,Integer> actionOnFactor, Integer partisanGained){
        this.name = name;
        this.actionOnFaction = actionOnFaction;
        this.actionOnFactor = actionOnFactor;
        this.partisanGained = partisanGained;
    }

    public void setRelatedEvent(List<Event> relatedEvent){
        this.relatedEvent = relatedEvent;
    }

    public String getName() {
        return name;
    }

    public Integer getPartisanGained() {
        return partisanGained;
    }

    public Map<String, Integer> getActionOnFaction() {
        return actionOnFaction;
    }

    public Map<String, Integer> getActionOnFactor() {
        return actionOnFactor;
    }

    public Map<String, Object> getChoiceToSave(){
        Map<String, Object> map = new HashMap<>();
        map.put("choice", this.name);

        List<Map<String, Object>> effects = new ArrayList<>();
        if(!isActionOnFactionNull()){
            Map<String, Object> mapActionOnFaction = new HashMap<>();
            mapActionOnFaction.put("actionOnFaction", this.actionOnFaction);
            effects.add(mapActionOnFaction);
        }

        if(!isActionOnFactorNull()){
            Map<String, Object> mapActionOnFactor = new HashMap<>();
            mapActionOnFactor.put("actionOnFactor", this.actionOnFactor);
            effects.add(mapActionOnFactor);
        }

        if (!isPartisanGainedNull()){
            Map<String, Object> mapPartisans = new HashMap<>();
            mapPartisans.put("partisans", this.partisanGained);
            effects.add(mapPartisans);
        }

        if (!effects.isEmpty()){
            map.put("effects", effects);
        }

        if (!isRelatedEventNull()){
            List<Map<String, Object>> relatedEventToSave = new ArrayList<>();
            for (Event e: this.relatedEvent) {
                relatedEventToSave.add(Event.getEventToSave(e));
            }
            map.put("relatedEvents", relatedEventToSave);
        }

        return map;
    }

    private boolean isActionOnFactionNull(){
        return this.actionOnFaction == null;
    }

    private boolean isActionOnFactorNull(){
        return this.actionOnFactor == null;
    }

    private boolean isPartisanGainedNull(){
        return this.partisanGained == 0;
    }

    private boolean isRelatedEventNull(){
        return this.relatedEvent == null;
    }


    public List<Event> getRelatedEvent() {
        return relatedEvent;
    }

    @Override
    public String toString(){ return name; }
}

package com.elpresidente.rules;

import com.elpresidente.event.Event;
import com.elpresidente.game.Saisons;

import java.util.ArrayList;
import java.util.List;

public class Sandbox implements Rules{

    private final List<Event> events;
    private Integer index;

    public Sandbox(List<Event> events) {
        this.events = events;
    }

    public Sandbox(List<Event> events, Integer index) {
        this.events = events;
        this.index = index;
    }

    @Override
    public Event getEvent(Saisons season) {
        if (this.index == null){
            this.index = (int) (Math.random() * events.size());
        }
        Event event = events.get(this.index);
        this.index = (int) (Math.random() * events.size());
        return event;
    }

    @Override
    public boolean hasEvent() {
        return true;
    }

    @Override
    public List<Object> getEventsToSave() {

        List<Object> eventsToSave = new ArrayList<>();
        for (Event event: this.events) {
            eventsToSave.add(Event.getEventToSave(event));
        }

        return eventsToSave;
    }

    @Override
    public Integer getIndex() {
        return index;
    }
}

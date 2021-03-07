package com.elpresidente.rules;

import com.elpresidente.event.Event;
import com.elpresidente.game.Saisons;

import java.util.ArrayList;
import java.util.List;

public class Scenario implements Rules{

    private final List<Event> events;
    private Integer index;

    public Scenario(List<Event> events) {
        this.events = events;
        this.index = 0;
    }

    public Scenario(List<Event> events, Integer index) {
        this.events = events;
        this.index = index;
    }

    @Override
    public Event getEvent(Saisons season) {
        Event event = events.get(this.index);
        this.index += 1;
        return event;
    }

    @Override
    public boolean hasEvent() {
        return this.index <= this.events.size();
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

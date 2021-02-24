package com.elpresidente.rules;

import com.elpresidente.event.Event;
import com.elpresidente.game.Saisons;

import java.util.List;

public class Scenario implements Rules{

    private final List<Event> events;

    public Scenario(List<Event> events) {
        this.events = events;
    }

    @Override
    public Event getEvent(Saisons season) {
        Event event = events.get(0);
        events.remove(event);
        return event;
    }

    @Override
    public boolean hasEvent() {
        return !events.isEmpty();
    }
}

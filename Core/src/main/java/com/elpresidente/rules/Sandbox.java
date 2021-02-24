package com.elpresidente.rules;

import com.elpresidente.event.Event;
import com.elpresidente.game.Saisons;

import java.util.List;

public class Sandbox implements Rules{

    private final List<Event> events;

    public Sandbox(List<Event> events) {
        this.events = events;
    }

    @Override
    public Event getEvent(Saisons season) {
        return events.get((int) (Math.random() * events.size()));
    }

    @Override
    public boolean hasEvent() {
        return true;
    }
}

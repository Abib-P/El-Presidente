package com.elpresidente.rules;

import com.elpresidente.event.Event;

import java.util.List;

public class Sandbox implements Rules{

    private final List<Event> events;

    public Sandbox(List<Event> events) {
        this.events = events;
    }

    @Override
    public Event getEvent() {
        return events.get((int) (Math.random() * events.size()));
    }

    @Override
    public boolean hasEvent() {
        return true;
    }
}

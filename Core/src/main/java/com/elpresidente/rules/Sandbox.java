package com.elpresidente.rules;

import com.elpresidente.event.Event;

import java.util.List;

public class Sandbox implements Rules{

    private final List<Event> events;

    public Sandbox(List<Event> events) {
        this.events = events;
        System.out.println("test");
        for (Event event: events) {
            System.out.println(event.getName());
        }
    }

    @Override
    public Event getEvent() {
        return events.get((int) (Math.random() * events.size()));
    }
}

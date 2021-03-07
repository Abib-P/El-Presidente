package com.elpresidente.rules;

import com.elpresidente.event.Event;
import com.elpresidente.game.Saisons;

import java.util.List;

public interface Rules {

    Event getEvent(Saisons saison);
    boolean hasEvent();
    List<Object> getEventsToSave();
    Integer getIndex();
}

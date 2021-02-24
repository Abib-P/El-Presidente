package com.elpresidente.rules;

import com.elpresidente.event.Event;
import com.elpresidente.game.Saisons;

public interface Rules {

    Event getEvent(Saisons saison);
    boolean hasEvent();
}

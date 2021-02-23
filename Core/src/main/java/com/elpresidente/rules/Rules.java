package com.elpresidente.rules;

import com.elpresidente.event.Event;

public interface Rules {

    Event getEvent();
    boolean hasEvent();
}

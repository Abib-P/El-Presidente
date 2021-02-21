package com.elpresidente.input;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;

public interface Input {

     Choice getChoice(Event event);

}

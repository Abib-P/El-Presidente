package com.elpresidente.repository;

import com.elpresidente.event.Event;
import com.elpresidente.factions.Factions;

import java.util.List;

public interface Repository {
    List<Factions> getAllFactions();
    List<Event> getAllEvent();
}

package com.elpresidente.repository;

import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.game.GameParameter;

import java.util.List;

public interface Repository {
    List<Faction> getAllFactions();
    List<Event> getAllEvent();
    GameParameter getAllGameParameter();
    String getName();
    String getStory();
}

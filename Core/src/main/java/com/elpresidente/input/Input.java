package com.elpresidente.input;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;

import java.util.Map;

public interface Input {

     Choice getChoice(Event event);
     Faction selectFaction(Factions factions);
     int getMarketAmount(int treasury);
     String selectScenario(Map<String,String> allScenario);
     boolean askYesNoQuestion(String question);
     float askForDifficulty();

}

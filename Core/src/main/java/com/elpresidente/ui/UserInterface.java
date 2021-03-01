package com.elpresidente.ui;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;
import com.elpresidente.game.Saisons;

import java.util.Map;

public interface UserInterface {
    void displayGameInfo(Game game);
    void displayFactions(Factions factions);
    void displayMarket(int food, int necessaryFood);
    String selectScenario(Map<String,String> AllScenarioNames);
    boolean askForReplay();
    float askForDifficulty();
    void displaySeason(Saisons season);
    Choice getChoice(Event event);
    Faction selectFaction(Factions factions);
    int getMarketAmount(int treasury);
}

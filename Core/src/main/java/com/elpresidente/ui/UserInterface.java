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
    void displayTreasury(int treasury);
    void displayAgriculture(int agriculture);
    void displayIndustry(int industry);
    void displayFactions(Factions factions);
    void displayMarket(int food, int necessaryFood);
    String selectScenario(Map<String,String> AllScenarioNames);
    boolean askForReplay();
    boolean askForRules();
    boolean askForNewGame();
    float askForDifficulty();
    boolean askShowAction();

    /**
     *
     * @return true if the player want to keep playing in Sandbox
     */
    boolean askForChangeMode();
    void displayEndOfGame(boolean win);

    void displaySeason(Saisons season);
    Choice getChoice(Event event, float difficulty, boolean showAction);
    Faction selectFaction(Factions factions);
    Faction selectFactionToCorrupt(Factions factions, int treasury);
    int getMarketAmount(int food, int necessaryFood, int treasury);
}

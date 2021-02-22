package com.elpresidente.output;

import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;

public interface Output {

    void displayString(String string);
    void displayGameInfo(Game game);
    void displayFactions(Factions factions);
    void displayMarket(int food, int necessaryFood);
}

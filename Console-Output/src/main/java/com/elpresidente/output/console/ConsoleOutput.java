package com.elpresidente.output.console;

import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;
import com.elpresidente.output.Output;

public class ConsoleOutput implements Output {

    public void displayString(String string){
        System.out.println(string);
    }

    public void displayFactionsInfo(Factions factions){
        for (Faction faction: factions.getFactions()) {
            System.out.println(faction.getName()+": "+faction.getSatisfaction()+"%");
        }
    }

    public void displayGameInfo(Game game){
        System.out.println("money: "+game.getMoney()+"\nfood: "+ game.getFood()+"\nIndustry: "+game.getIndustries()+"% Agriculture: "+game.getAgriculture()+"%");
        displayFactionsInfo(game.getFactionManager());
    }

    public void displayFactions(Factions factions){
        Faction faction;
        System.out.println(" 0 do not corrupt");
        for (int i = 0; i < factions.getFactions().size(); i++) {
            faction = factions.getFactions().get(i);
            System.out.println(" "+(i+1)+". "+ faction.getName()+" "+faction.getSatisfaction()+"% ("+faction.getPartisanNumber()+")");
        }
    }

    public void displayMarket(int food, int necessaryFood){
        System.out.println("Food: "+ food+" / "+ necessaryFood+
                           "\namount: ");
    }
}

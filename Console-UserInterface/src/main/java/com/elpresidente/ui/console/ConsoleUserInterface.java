package com.elpresidente.ui.console;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;
import com.elpresidente.game.Saisons;
import com.elpresidente.ui.UserInterface;

import java.util.Map;
import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {

    @Override
    public void displayGameInfo(Game game) {
        System.out.println("money: "+game.getTreasury()+"\nfood: "+ game.getFood()+"\nIndustry: "+game.getIndustries()+"% Agriculture: "+game.getAgriculture()+"%");
        displayFactionsInfo(game.getFactionManager());
    }

    @Override
    public void displayTreasury(int treasury) {
        System.out.println("treasury: "+ treasury);
    }

    @Override
    public void displayAgriculture(int agriculture) {
        System.out.println("addAgriculture: "+ agriculture);
    }

    @Override
    public void displayIndustry(int industry) {
        System.out.println("addIndustry: "+ industry);
    }

    private void displayFactionsInfo(Factions factions){
        for (Faction faction: factions.getFactions()) {
            System.out.println(faction.getName()+": "+faction.getSatisfaction()+"%");
        }
    }

    @Override
    public void displayFactions(Factions factions) {
        Faction faction;
        System.out.println(" 0 do not corrupt");
        for (int i = 0; i < factions.getFactions().size(); i++) {
            faction = factions.getFactions().get(i);
            System.out.println(" "+(i+1)+". "+ faction.getName()+" "+faction.getSatisfaction()+"% ("+faction.getPartisanNumber()+")");
        }
    }

    @Override
    public void displayMarket(int food, int necessaryFood) {
        System.out.println("Food: "+ food+" / "+ necessaryFood+
                "\namount: ");
    }

    @Override
    public String selectScenario(Map<String, String> allScenario) {
        Object[] entries = allScenario.entrySet().toArray();
        Map.Entry<String, String> entry;
        int index = 0;
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < entries.length; i++) {
            entry = (Map.Entry<String, String>) entries[i];
            System.out.println(" "+i+". "+entry.getValue()+" ( "+ entry.getValue()+" )");
        }
        System.out.println("select a scenario: ");
        do{
            index = scanner.nextInt();
            if( index < 0 || index > entries.length ){
                System.out.println("invalid input");
            }

        }while (index < 0 || index > entries.length);

        entry = (Map.Entry<String, String>) entries[index];

        return  entry.getValue();
    }

    @Override
    public boolean askForReplay() {
        Scanner scanner = new Scanner(System.in);
        String answer;

        System.out.println("Do you want to replay ? (O/N)");

        do{
            answer = scanner.nextLine();
        }while( !answer.equals("O") && !answer.equals("N"));

        return answer.equals("O");
    }

    @Override
    public boolean askForRules() {
        return false;
    }

    @Override
    public boolean askForNewGame() {
        return true;
    }

    @Override
    public float askForDifficulty() {
        Scanner scanner = new Scanner(System.in);
        int difficulty = 1;

        System.out.println("""
                Select the difficulty\s
                 0. Easy
                 1. Normal
                 2. Hard""");

        do{
            difficulty = scanner.nextInt();
        }while( difficulty <0 || difficulty >2);

        return switch (difficulty) {
            case 0 -> 0.5f;
            case 2 -> 2f;
            default -> 1f;
        };
    }

    @Override
    public boolean askShowAction() {
        return false;
    }

    @Override
    public boolean askForChangeMode() {
        return false;
    }

    @Override
    public void displayEndOfGame(boolean win) {

    }

    @Override
    public void displaySeason(Saisons season) {
        System.out.println("||| "+ season+" |||");
    }

    @Override
    public Choice getChoice(Event event, float difficulty, boolean showAction) {
        Scanner scanner = new Scanner(System.in);
        int index = -1;
        System.out.println("event: "+event.getName());

        for (int i = 0; i < event.getChoices().size(); i++) {
            System.out.println("    "+i+". "+ event.getChoices().get(i).getName());
        }

        do{
            System.out.print("choix: ");
            index = scanner.nextInt();
            if( index < 0 || index >= event.getChoices().size()  ){
                System.out.println("Option invalid");
            }
        }while(index < 0 || index >= event.getChoices().size() );

        return event.getChoices().get(index);
    }

    @Override
    public Faction selectFaction(Factions factions) {
        Faction faction = null;
        int index = -1;
        Scanner scanner = new Scanner(System.in);

        do{
            index = scanner.nextInt(factions.getFactions().size()+1); // +1 because we are 1 based -> the 0 is fornot corrupt
        }while(index < 0);

        if (index > 0) { // 0 is the option to not corrupt
            faction = factions.getFactions().get(index - 1);
        }

        return faction;
    }

    @Override
    public Faction selectFactionToCorrupt(Factions factions, int treasury) {
        Faction faction;
        int index = -1;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select a faction to corrupt\ntreasury: "+treasury+"€\n 0. do not corrupt");
        for (int i = 0; i < factions.getFactions().size(); i++) {
            faction = factions.getFactions().get(i);
            System.out.println(" "+(i+1)+". "+faction.getName()+" "+faction.getSatisfaction()+"% ("+faction.getCorruptionPrice()+"€)");
        }

        do{
            index = scanner.nextInt(factions.getFactions().size()+1); // +1 because we are 1 based -> the 0 is for not corrupt

            if(index > 0 ){
                faction = factions.getFactions().get(index-1);

                if( faction.getCorruptionPrice() > treasury ) {
                    System.out.println("You don't have enough treasury");
                    index = -1;
                }else if( faction.getSatisfaction() >= 100){
                    System.out.println("The is already at 100% satisfaction");
                    index = -1;
                }

            }

        }while(index < 0);

        if (index > 0) { // 0 is the option to not corrupt
            return factions.getFactions().get(index - 1);
        }else{
            return null;
        }
    }


    @Override
    public int getMarketAmount(int food, int necessaryFood, int treasury) {
        int amount = 0, max = treasury / Game.FoodUnitPrice + 1;
        Scanner scanner = new Scanner( System.in);
        System.out.println("max: "+max);
        do {
            amount = scanner.nextInt();
        }while( amount < 0 || amount > max);

        return  amount;
    }
}

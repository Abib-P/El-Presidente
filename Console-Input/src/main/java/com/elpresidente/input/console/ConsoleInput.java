package com.elpresidente.input.console;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.game.Game;
import com.elpresidente.input.Input;

import java.util.Map;
import java.util.Scanner;

public class ConsoleInput implements Input {

    @Override
    public Choice getChoice(Event event) {
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

    public Faction selectFaction(Factions factions){
        Faction faction = null;
        int index = -1;
        Scanner scanner = new Scanner(System.in);

        do{
            index = scanner.nextInt(factions.getFactions().size()+1); // +1 because we are 1 based -> the 0 is for not corrupt
        }while(index < 0);

        if (index > 0) { // 0 is the option to not corrupt
            faction = factions.getFactions().get(index - 1);
        }

        return faction;
    }

    public int getMarketAmount(int treasury){
        int amount = 0;
        Scanner scanner = new Scanner( System.in);
        do {
            amount = scanner.nextInt(treasury / Game.FoodUnitPrice + 1);
        }while( amount < 0);

        return  amount;
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

        return  entry.getKey();
    }

    @Override
    public boolean askYesNoQuestion(String question) {
        Scanner scanner = new Scanner(System.in);
        String answer;

        System.out.println(question+" (O/N)");

        do{
            answer = scanner.nextLine();
        }while( !answer.equals("O") && !answer.equals("N"));

        return answer.equals("O");
    }
}

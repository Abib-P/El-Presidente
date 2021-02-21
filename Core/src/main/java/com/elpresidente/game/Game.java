package com.elpresidente.game;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.input.Input;
import com.elpresidente.output.Output;
import com.elpresidente.repository.Repository;
import com.elpresidente.rules.Rules;

import java.util.*;

public class Game {
    final Input input;
    final Output output;
    final Repository repository;
    Rules rules;


    Factions factionManager;
    int minGlobalSatisfaction = 10;
    float money = 200;
    int industries, agriculture;

    public Game(Input input, Output output, Repository repository){
        this.input = input;
        this.output = output;
        this.repository = repository;
    }

    public void start(){
        //ask for rules the player want use and difficulty
    }

    public void playGame(){
        boolean loose = false;
        String[] saisons = {"printemps", "été", "automne", "hiver"};

        ArrayList<Faction> factions = new ArrayList<Faction>();
        /*factions.add( new Faction("Capitalistes", 50, 6));
        factions.add( new Faction("communistes", 50, 6));
        factions.add( new Faction("libéraux", 50, 6));
        factions.add( new Faction("religieux", 50, 6));
        factions.add( new Faction("militaristes", 50, 6));
        factions.add( new Faction("écologistes", 50, 6));
        factions.add( new Faction("nationalistes", 50, 6));
        factions.add( new Faction("loyalistes", 100, 6));*/



        factionManager = new Factions(factions);

        while( !loose){

            for (int i = 0; i < 4; i++) {
                Event event = rules.getEvent();
                output.displayString("saison: " + saisons[i]);
                Choice choice = input.getChoice(event);
                applyChoice(choice);
            }

            loose = hasLoose();
        }
    }

    private void applyChoice(Choice choice){

    }

    private boolean hasLoose(){
        return factionManager.getGlobalSatisfaction() < minGlobalSatisfaction;
    }
}

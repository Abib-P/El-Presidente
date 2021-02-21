package com.elpresidente.game;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.input.Input;
import com.elpresidente.output.Output;
import com.elpresidente.repository.Repository;
import com.elpresidente.rules.Rules;
import com.elpresidente.rules.Sandbox;

import java.util.*;

public class Game {

    public static final int IndustryRevenue = 10;
    public static final int AgricultureRevenue = 40;
    public static final int PartisanFoodConsumption = 4;
    public static final int FoodUnitPrice = 8;

    private final Input input;
    private final Output output;
    private final Repository repository;
    private Rules rules;

    private Factions factionManager;
    private int minGlobalSatisfaction = 10;
    private int money = 200, food = 200;
    private int industries, agriculture;

    public Game(Input input, Output output, Repository repository){
        this.input = input;
        this.output = output;
        this.repository = repository;
    }

    public void start(){
        //ask for rules the player want use and difficulty
      /*  HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("COMMUNISTS", 5);

        Choice choice1 = new Choice("choice1", map, null, 0);
        ArrayList<Event> events = new ArrayList<>();
        Event event1 = new Event("event1");
        event1.addChoice( choice1);
        events.add(event1);
        */
        factionManager = new Factions(repository.getAllFactions());


        rules = new Sandbox( repository.getAllEvent());
        //rules = new Sandbox( events);
    }

    public void playGame(){
        boolean loose = false;
        Saisons[] saisons = Saisons.values();

        /*ArrayList<Faction> factions = new ArrayList<Faction>();
        factions.add( new Faction("Capitalistes", 50, 6));
        factions.add( new Faction("communistes", 50, 6));
        factions.add( new Faction("libéraux", 50, 6));
        factions.add( new Faction("religieux", 50, 6));
        factions.add( new Faction("militaristes", 50, 6));
        factions.add( new Faction("écologistes", 50, 6));
        factions.add( new Faction("nationalistes", 50, 6));
        factions.add( new Faction("loyalistes", 100, 6));*/

        while( !loose){

            for (int i = 0; i < Saisons.values().length; i++) {
                Event event = rules.getEvent();
                output.displayString("||| "+ saisons[i]+" |||");
                output.displayGameInfo(this);

                Choice choice = input.getChoice(event);
                applyChoice(choice);
            }

            endOfYear();

            loose = hasLoose();
        }
    }

    private void endOfYear(){
        int necessaryFood = factionManager.getTotalNumberOfPartisan() * Game.PartisanFoodConsumption;

        money += Game.IndustryRevenue * industries;
        food += Game.AgricultureRevenue * agriculture;

        if(food < necessaryFood){
            factionManager.addPopulation( (int)((necessaryFood - food) / (float) Game.PartisanFoodConsumption +0.5) );
        }else{
            factionManager.populate();
        }
        
    }

    private void addAgriculture(int amount){
        this.agriculture += amount;
        if( this.agriculture + this.industries > 100){
            this.industries -= this.agriculture + this.industries - 100;
        }
    }

    private void addIndustries(int amount){
        this.industries += amount;
        if( this.agriculture + this.industries > 100){
            this.agriculture -= this.agriculture + this.industries - 100;
        }
    }

    private void applyChoice(Choice choice){
        factionManager.addPopulation(choice.getPartisanGained());

        if(choice.getActionOnFaction() != null)
            for (Map.Entry<String, Integer> entry: choice.getActionOnFaction().entrySet() ) {
                factionManager.addSatisfactionToFaction(entry.getKey(), entry.getValue());
            }

        if(choice.getActionOnFactor() != null)
            for (Map.Entry<String, Integer> entry: choice.getActionOnFactor().entrySet() ) {
                factionManager.addSatisfactionToFaction(entry.getKey(), entry.getValue());
            }
        
    }

    public Factions getFactionManager() {
        return factionManager;
    }

    public int getMoney() {
        return money;
    }

    public int getFood() {
        return food;
    }

    public int getIndustries() {
        return industries;
    }

    public int getAgriculture() {
        return agriculture;
    }

    private boolean hasLoose(){
        return factionManager.getGlobalSatisfaction() < minGlobalSatisfaction;
    }
}

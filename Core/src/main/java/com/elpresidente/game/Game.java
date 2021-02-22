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
    GameParameter gameParameter;
    private int minGlobalSatisfaction;

    public Game(Input input, Output output, Repository repository){
        this.input = input;
        this.output = output;
        this.repository = repository;
    }

    public void start(){
        //TODO ask for rules the player want use and difficulty

        minGlobalSatisfaction = 10;

        factionManager = new Factions(repository.getAllFactions());
        gameParameter = repository.getAllGameParameter();
        rules = new Sandbox( repository.getAllEvent());
        //rules = new Sandbox( events);
    }

    public void playGame(){
        boolean loose = false;
        Saisons[] seasons = Saisons.values();

        while( !loose){

            for (int i = 0; i < Saisons.values().length; i++) {
                Event event = rules.getEvent();
                output.displayString("||| "+ seasons[i]+" |||");
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

        gameParameter.treasury += Game.IndustryRevenue * gameParameter.industryPercentage;
        gameParameter.foodUnits += Game.AgricultureRevenue * gameParameter.agriculturePercentage;

        /*TODO select the factions to corrupt
               possibility to buy food
        */

        if(gameParameter.foodUnits < necessaryFood){
            factionManager.addPopulation( (int)((necessaryFood - gameParameter.foodUnits) / (float) Game.PartisanFoodConsumption +0.5) );
        }else{
            factionManager.populate();
        }
        
    }

    private void addAgriculture(int amount){
        gameParameter.agriculturePercentage += amount;
        if( gameParameter.agriculturePercentage + gameParameter.industryPercentage > 100){
            gameParameter.industryPercentage -= gameParameter.agriculturePercentage + gameParameter.industryPercentage - 100;
        }
    }

    private void addIndustries(int amount){
        gameParameter.industryPercentage += amount;
        if( gameParameter.agriculturePercentage + gameParameter.industryPercentage > 100){
            gameParameter.agriculturePercentage -= gameParameter.agriculturePercentage + gameParameter.industryPercentage - 100;
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
                if(entry.getKey().equals("AGRICULTURE"))
                    addAgriculture(entry.getValue());
                if(entry.getKey().equals("INDUSTRY"))
                    addIndustries(entry.getValue());
            }
        
    }

    public Factions getFactionManager() {
        return factionManager;
    }

    public int getMoney() {
        return gameParameter.treasury;
    }

    public int getFood() {
        return gameParameter.foodUnits;
    }

    public int getIndustries() {
        return gameParameter.industryPercentage;
    }

    public int getAgriculture() {
        return gameParameter.agriculturePercentage;
    }

    private boolean hasLoose(){
        return factionManager.getGlobalSatisfaction() < minGlobalSatisfaction;
    }
}

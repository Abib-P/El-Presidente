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

    public static final String IndustryFactorKey = "INDUSTRY";
    public static final String AgricultureFactorKey = "AGRICULTURE";
    public static final int IndustryRevenue = 10;
    public static final int AgricultureRevenue = 40;
    public static final int PartisanFoodConsumption = 4;
    public static final int FoodUnitPrice = 8;

    private final Input input;
    private final Output output;
    private final Repository repository;
    private Rules rules;

    private Factions factionManager;
    private GameParameter gameParameter;

    private int minGlobalSatisfaction;
    private float difficulty;

    public Game(Input input, Output output, Repository repository){
        this.input = input;
        this.output = output;
        this.repository = repository;
    }

    public void start(){
        //TODO ask for rules the player want

        difficulty = input.askForDifficulty();

        minGlobalSatisfaction = (int) (30 * difficulty);

        factionManager = new Factions(repository.getAllFactions());
        gameParameter = repository.getAllGameParameter();

        rules = new Sandbox( repository.getAllEvent());

    }

    public void playGame(){
        boolean loose = false;
        Saisons[] seasons = Saisons.values();

        while( !loose){

            for (int i = 0; i < Saisons.values().length; i++) {

                if( isScenarioOver()) {
                    goToSandBoxMod();
                }

                Event event = rules.getEvent(seasons[i]);
                output.displayString("||| "+ seasons[i]+" |||");
                output.displayGameInfo(this);

                Choice choice = input.getChoice(event);
                applyChoice(choice);
            }

            endOfYear();

            loose = hasLoose();
        }
    }

    private void goToSandBoxMod(){
        rules = new Sandbox( repository.getAllEvent());
    }

    private boolean isScenarioOver() {
        return rules.hasEvent();
    }

    private void endOfYear(){
        Faction faction;
        int necessaryFood = factionManager.getTotalNumberOfPartisan() * Game.PartisanFoodConsumption;
        int boughtFood = 0;

        gameParameter.addTreasury( Game.IndustryRevenue * gameParameter.getIndustryPercentage() );
        gameParameter.addFood( Game.AgricultureRevenue * gameParameter.getAgriculturePercentage() );

        do{
            System.out.println("treasury: "+ gameParameter.getTreasury());
            output.displayFactions(factionManager);
            faction = input.selectFaction(factionManager);

            if(faction != null){
                gameParameter.addTreasury( -faction.getCorruptionPrice() );
                factionManager.corrupt(faction);
            }
        }while(faction != null);

        output.displayMarket(gameParameter.getFoodUnits(), necessaryFood);
        boughtFood = input.getMarketAmount(gameParameter.getTreasury());
        gameParameter.addTreasury( -boughtFood * Game.FoodUnitPrice);
        gameParameter.addFood( boughtFood );

        if(gameParameter.getFoodUnits() < necessaryFood){
            factionManager.addPopulation( (int)((gameParameter.getFoodUnits() - necessaryFood) / (float) Game.PartisanFoodConsumption +0.5) );
        }else{
            factionManager.populate();
        }
        
    }

    private int adaptValueToDifficulty(int value){
        if(value > 0){
            return (int) (value / difficulty);
        }else{
            return (int) (value * difficulty);
        }
    }

    private void applyChoice(Choice choice){

        factionManager.addPopulation( adaptValueToDifficulty( choice.getPartisanGained() ));

        if(choice.getActionOnFaction() != null) {
            for (Map.Entry<String, Integer> entry : choice.getActionOnFaction().entrySet()) {
                factionManager.addSatisfactionToFaction(entry.getKey(), adaptValueToDifficulty(entry.getValue()));
            }
        }

        if(choice.getActionOnFactor() != null) {
            for (Map.Entry<String, Integer> entry : choice.getActionOnFactor().entrySet()) {
                if (entry.getKey().equals(Game.AgricultureFactorKey)) {
                    gameParameter.addAgriculture(adaptValueToDifficulty(entry.getValue()));
                } else if (entry.getKey().equals(Game.IndustryFactorKey)) {
                    gameParameter.addIndustries(adaptValueToDifficulty(entry.getValue()));
                }
            }
        }
        
    }

    public Factions getFactionManager() {
        return factionManager;
    }

    public int getTreasury() {
        return gameParameter.getTreasury();
    }

    public int getFood() {
        return gameParameter.getFoodUnits();
    }

    public int getIndustries() {
        return gameParameter.getAgriculturePercentage();
    }

    public int getAgriculture() {
        return gameParameter.getAgriculturePercentage();
    }

    private boolean hasLoose(){
        return factionManager.getGlobalSatisfaction() < minGlobalSatisfaction;
    }
}

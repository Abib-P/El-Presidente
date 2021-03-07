package com.elpresidente.game;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.repository.Repository;
import com.elpresidente.rules.Rules;
import com.elpresidente.rules.Sandbox;
import com.elpresidente.save.Save;
import com.elpresidente.rules.Scenario;
import com.elpresidente.ui.UserInterface;

import java.io.IOException;
import java.util.*;

public class Game {

    public static final String IndustryFactorKey = "INDUSTRY";
    public static final String AgricultureFactorKey = "AGRICULTURE";
    public static final String TreasuryFactorKey = "TREASURY";
    public static final int IndustryRevenue = 10;
    public static final int AgricultureRevenue = 40;
    public static final int PartisanFoodConsumption = 4;
    public static final int FoodUnitPrice = 8;

    private final Repository repository;
    private Rules rules;
    private final Integer index = 0;

    private Factions factionManager;
    private GameParameter gameParameter;

    private final UserInterface userInterface;

    private int minGlobalSatisfaction;
    private float difficulty;
    private boolean showActionOnChoice;
    private Saisons currentSeason;

    private boolean hasLoose;

    public Game(UserInterface userInterface, Repository repository){
        this.userInterface = userInterface;
        this.repository = repository;
    }

    public void start(){

        if(  userInterface.askForRules() ){
            rules = new Sandbox( repository.getAllEvent());
        }else{
            rules = new Scenario( repository.getAllEvent());
        }

        difficulty = userInterface.askForDifficulty();
        showActionOnChoice = userInterface.askShowAction();

        minGlobalSatisfaction = (int) (30 * difficulty);

        factionManager = new Factions(repository.getAllFactions());
        gameParameter = repository.getAllGameParameter();

        currentSeason = Saisons.PRINTEMPS;

        userInterface.displayGameInfo(this);

        hasLoose = false;
    }

    public void load(){
        int mode = this.repository.getMode();
        if (mode == 0){
            rules = new Sandbox( repository.getAllEvent());
        }else{
            rules = new Scenario( repository.getAllEvent());
        }

        this.difficulty = (float) this.repository.getDifficulty();
        this.minGlobalSatisfaction = (int) (30 * this.difficulty);

        factionManager = new Factions(this.repository.getAllFactions());
        this.gameParameter = this.repository.getAllGameParameter();
        this.currentSeason = Saisons.values()[this.repository.getSeason()];

        this.userInterface.displayGameInfo(this);

        this.hasLoose = false;
    }

    public void playGame(){
        Saisons[] seasons = Saisons.values();
        boolean isPlaying = true;

        int seasonIndexStart;
        if (currentSeason != Saisons.PRINTEMPS){
            seasonIndexStart = this.repository.getSeason();
        }else{
            seasonIndexStart = 0;
        }

        while( isPlaying && !hasLoose){

            for (int i = seasonIndexStart; i < Saisons.values().length; i++) {

                if( isScenarioOver() ) {

                    if( !userInterface.askForChangeMode() ){
                        isPlaying = false;
                        break;
                    }
                    goToSandBoxMod();
                }
                currentSeason = seasons[i];

                Event event = rules.getEvent( currentSeason );
                Choice choice = userInterface.getChoice(event, difficulty, showActionOnChoice);
                applyChoice(choice);

                if( hasLoose ) {
                    break;
                }

            }

            if( !hasLoose && isPlaying){
                endOfYear();
            }

            seasonIndexStart = 0;
            currentSeason = seasons[0];

            try {
                Save.saveGame(this, "natha");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        userInterface.displayEndOfGame( !hasLoose);

    }

    private void goToSandBoxMod(){
        rules = new Sandbox( repository.getAllEvent());
    }

    private boolean isScenarioOver() {
        return !rules.hasEvent();
    }

    private void endOfYear(){
        Faction faction;
        int necessaryFood = getFoodConsumption();
        int boughtFood;

        gameParameter.addTreasury( Game.IndustryRevenue * gameParameter.getIndustryPercentage() );
        gameParameter.addFood( getFoodProduction() );
        userInterface.displayGameInfo(this);

        do{
            faction = userInterface.selectFactionToCorrupt(factionManager, gameParameter.getTreasury());

            if(faction != null){
                gameParameter.addTreasury( -faction.getCorruptionPrice() );
                factionManager.corrupt(faction);
                userInterface.displayGameInfo(this);
            }
        }while(faction != null);

        userInterface.displayMarket(gameParameter.getFoodUnits(), necessaryFood);

        boughtFood = userInterface.getMarketAmount(gameParameter.getFoodUnits(), necessaryFood, gameParameter.getTreasury());

        gameParameter.addTreasury( -boughtFood * Game.FoodUnitPrice);

        if(gameParameter.getFoodUnits() < necessaryFood){
            gameParameter.addFood( boughtFood );
            factionManager.addPopulation( (int)((gameParameter.getFoodUnits() - necessaryFood) / (float) Game.PartisanFoodConsumption +0.5) );
            gameParameter.addFood( -getFoodConsumption() );
        }else{
            gameParameter.addFood( boughtFood );
            gameParameter.addFood( -getFoodConsumption() );
            factionManager.populate();
        }

        hasLoose = hasLoose();

        userInterface.displayGameInfo(this);
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
                switch (entry.getKey()) {
                    case Game.AgricultureFactorKey -> gameParameter.addAgriculture( entry.getValue());
                    case Game.IndustryFactorKey -> gameParameter.addIndustries( entry.getValue());
                    case Game.TreasuryFactorKey -> gameParameter.addTreasury( adaptValueToDifficulty(entry.getValue()));
                }
            }

        }

        userInterface.displayGameInfo(this);

        hasLoose = hasLoose();

        if( !hasLoose ) {
            if (choice.getRelatedEvent() != null) {
                for (Event event : choice.getRelatedEvent()) {
                    System.out.println("related even: "+ event.getName());
                    choice = userInterface.getChoice(event, difficulty, showActionOnChoice);
                    applyChoice(choice);

                    if(hasLoose) {
                        break;
                    }
                }
            }
        }

    }

    public Saisons getCurrentSeason() {
        return currentSeason;
    }

    public int getFoodConsumption(){
        return factionManager.getTotalNumberOfPartisan() * Game.PartisanFoodConsumption;
    }

    public int getFoodProduction(){
        return Game.AgricultureRevenue * gameParameter.getAgriculturePercentage();
    }

    public float getGlobalSatisfaction(){
        return factionManager.getGlobalSatisfaction();
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
        return gameParameter.getIndustryPercentage();
    }

    public int getAgriculture() {
        return gameParameter.getAgriculturePercentage();
    }

    public boolean hasLoose(){
        return factionManager.getGlobalSatisfaction() < minGlobalSatisfaction || hasLoose;
    }

    public Map<String, Object> getGameStatement(String pseudo){
        Map<String, Object> gameStatement = new HashMap<>();

        gameStatement.put("name", this.repository.getName() + " - " + pseudo);
        gameStatement.put("story", this.repository.getStory());
        gameStatement.put("difficulty", this.difficulty);
        gameStatement.put("index", this.rules.getIndex());
        gameStatement.put("season", this.currentSeason.ordinal());

        if (rules instanceof Sandbox){
            gameStatement.put("mode", 1); //Sandbox
        }else{
            gameStatement.put("mode", 0); //Scenario
        }

        Map<String, Object> parametersNormal = new HashMap<>();
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("agriculturePercentage", this.gameParameter.getAgriculturePercentage());
        parameters.put("industryPercentage", this.gameParameter.getIndustryPercentage());
        parameters.put("treasury", this.gameParameter.getTreasury());
        parameters.put("foodUnits", this.gameParameter.getFoodUnits());

        parameters.put("factions", this.factionManager.getFactionsToSave());

        parametersNormal.put("NORMAL", parameters);

        gameStatement.put("gameStartParameters", parametersNormal);

        gameStatement.put("events", rules.getEventsToSave());

        return gameStatement;
    }
}

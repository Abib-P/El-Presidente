package com.elpresidente.game;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import com.elpresidente.repository.Repository;
import com.elpresidente.ui.UserInterface;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void should_game_end_when_factions_satisfaction_are_at_0(){
        Repository repository = new Repository() {
            @Override
            public List<Faction> getAllFactions() {
                return List.of(new Faction("TEST","test",0,10));
            }

            @Override
            public List<Event> getAllEvent() {
                Event event = new Event("Event");
                event.setChoices(List.of(new Choice("Not the right",Map.of("null",0),Map.of("null",0),-22)));
                return List.of(event);
            }

            @Override
            public GameParameter getAllGameParameter() {
                return new GameParameter(0,0,0,0);
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getSaveName() {
                return null;
            }

            @Override
            public String getStory() {
                return null;
            }

            @Override
            public double getDifficulty() {
                return 0;
            }

            @Override
            public boolean getShowActionOnChoice() {
                return false;
            }

            @Override
            public int getMode() {
                return 0;
            }

            @Override
            public int getIndex() {
                return 0;
            }

            @Override
            public int getSeason() {
                return 0;
            }
        };

        UserInterface userInterface = new UserInterface() {
            @Override
            public void displayGameInfo(Game game) {

            }

            @Override
            public void displayTreasury(int treasury) {

            }

            @Override
            public void displayAgriculture(int agriculture) {

            }

            @Override
            public void displayIndustry(int industry) {

            }

            @Override
            public void displayFactions(Factions factions) {

            }

            @Override
            public void displayMarket(int food, int necessaryFood) {

            }

            @Override
            public String selectScenario(Map<String, String> AllScenarioNames) {
                return null;
            }

            @Override
            public boolean askForReplay() {
                return false;
            }

            @Override
            public boolean askForRules() {
                return false;
            }

            @Override
            public boolean askForNewGame() {
                return false;
            }

            @Override
            public float askForDifficulty() {
                return 1;
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

            }

            @Override
            public Choice getChoice(Event event, float difficulty, boolean showAction) {
                return event.getChoices().get(0);
            }

            @Override
            public Faction selectFaction(Factions factions) {
                return null;
            }

            @Override
            public Faction selectFactionToCorrupt(Factions factions, int treasury) {
                return null;
            }

            @Override
            public int getMarketAmount(int food, int necessaryFood, int treasury) {
                return 0;
            }
        };

        Game game = new Game(userInterface,repository);
        game.start();
        assertThat(game.hasLoose()).isTrue();
    }

    @Test
    void should_game_be_won_when_factions_satisfaction_are_over_30(){
        Repository repository = new Repository() {
            @Override
            public List<Faction> getAllFactions() {
                return List.of(new Faction("TEST","test",50,10));
            }

            @Override
            public List<Event> getAllEvent() {
                Event event = new Event("Event");
                event.setChoices(List.of(new Choice("Not the right",Map.of("null",0),Map.of("null",0),-22)));
                return List.of(event);
            }

            @Override
            public GameParameter getAllGameParameter() {
                return new GameParameter(0,0,0,0);
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getSaveName() {
                return null;
            }

            @Override
            public String getStory() {
                return null;
            }

            @Override
            public double getDifficulty() {
                return 0;
            }

            @Override
            public boolean getShowActionOnChoice() {
                return false;
            }

            @Override
            public int getMode() {
                return 0;
            }

            @Override
            public int getIndex() {
                return 0;
            }

            @Override
            public int getSeason() {
                return 0;
            }
        };

        UserInterface userInterface = new UserInterface() {
            @Override
            public void displayGameInfo(Game game) {

            }

            @Override
            public void displayTreasury(int treasury) {

            }

            @Override
            public void displayAgriculture(int agriculture) {

            }

            @Override
            public void displayIndustry(int industry) {

            }

            @Override
            public void displayFactions(Factions factions) {

            }

            @Override
            public void displayMarket(int food, int necessaryFood) {

            }

            @Override
            public String selectScenario(Map<String, String> AllScenarioNames) {
                return null;
            }

            @Override
            public boolean askForReplay() {
                return false;
            }

            @Override
            public boolean askForRules() {
                return false;
            }

            @Override
            public boolean askForNewGame() {
                return false;
            }

            @Override
            public float askForDifficulty() {
                return 1;
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

            }

            @Override
            public Choice getChoice(Event event, float difficulty, boolean showAction) {
                return event.getChoices().get(0);
            }

            @Override
            public Faction selectFaction(Factions factions) {
                return null;
            }

            @Override
            public Faction selectFactionToCorrupt(Factions factions, int treasury) {
                return null;
            }

            @Override
            public int getMarketAmount(int food, int necessaryFood, int treasury) {
                return 0;
            }
        };

        Game game = new Game(userInterface,repository);
        game.start();
        assertThat(game.hasLoose()).isFalse();
    }
}
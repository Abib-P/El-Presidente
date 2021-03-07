package com.elpresidente.factions;

import com.elpresidente.faction.Faction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FactionsTest {

    @Test
    void should_add_10_satisfaction_to_faction_when_corrupt() {
        Faction faction = new Faction("TEST", "test", 10, 100);
        Factions factions = new Factions(List.of(faction));
        int expectedSatisfaction = 20;

        factions.corrupt(factions.getFactions().get(0));

        assertThat(factions.getFactions().get(0).getSatisfaction()).isEqualTo(expectedSatisfaction);
    }

    @Test
    void should_decrease_satisfaction_of_loyalist_when_faction_is_corrupted() {
        Faction faction = new Faction("TEST", "test", 50, 100);
        Faction loyalistFaction = new Faction("LOYALISTS", "loyalistTest", 100, 10);
        Factions factions = new Factions(List.of(faction, loyalistFaction));
        int expectedFactionSatisfaction = 60;
        int expectedLoyalistSatisfaction = 85;

        factions.corrupt(factions.getFactions().get(0));

        assertThat(factions.getFactions().get(0).getSatisfaction()).isEqualTo(expectedFactionSatisfaction);
        assertThat(factions.getFactions().get(1).getSatisfaction()).isEqualTo(expectedLoyalistSatisfaction);
    }

    @Test
    void should_get_the_right_faction_when_searched_by_name() {
        Faction faction1 = new Faction("TEST1", "test1", 10, 100);
        Faction faction2 = new Faction("TEST2", "test2", 20, 200);
        Faction faction3 = new Faction("TEST3", "test3", 30, 300);
        Faction faction4 = new Faction("TEST4", "test4", 40, 400);
        Factions factions = new Factions(List.of(faction1, faction2, faction3, faction4));

        Faction getFaction = factions.getFactionByName("test3");

        assertThat(getFaction.getName()).isEqualTo("test3");
        assertThat(getFaction.getPartisanNumber()).isEqualTo(300);
        assertThat(getFaction.getSatisfaction()).isEqualTo(30);
        assertThat(getFaction.getKey()).isEqualTo("TEST3");
    }

    @Test
    void should_return_that_there_is_a_loyalist_faction_when_a_loyalist_faction_is_present() {
        Faction faction1 = new Faction("TEST1", "test1", 10, 100);
        Faction faction2 = new Faction("TEST2", "test2", 20, 200);
        Faction faction3 = new Faction("LOYALISTS", "test3", 30, 300);
        Faction faction4 = new Faction("TEST4", "test4", 40, 400);
        Factions factions = new Factions(List.of(faction1, faction2, faction3, faction4));

        boolean isThereLoyalist = factions.areLoyalist();

        assertThat(isThereLoyalist).isEqualTo(true);
    }

    @Test
    void should_return_that_there_is_no_loyalist_faction_when_no_loyalist_faction_is_present() {
        Faction faction1 = new Faction("TEST1", "test1", 10, 100);
        Faction faction2 = new Faction("TEST2", "test2", 20, 200);
        Faction faction3 = new Faction("TEST3", "test3", 30, 300);
        Faction faction4 = new Faction("TEST4", "test4", 40, 400);
        Factions factions = new Factions(List.of(faction1, faction2, faction3, faction4));

        boolean isThereLoyalist = factions.areLoyalist();

        assertThat(isThereLoyalist).isEqualTo(false);
    }

    @Test
    void should_not_add_satisfaction_when_the_faction_is_not_present() {
        Faction faction1 = new Faction("TEST1", "test1", 10, 100);
        Faction faction2 = new Faction("TEST2", "test2", 20, 200);
        Faction faction3 = new Faction("TEST3", "test3", 30, 300);
        Faction faction4 = new Faction("TEST4", "test4", 40, 400);
        Factions factions = new Factions(List.of(faction1, faction2, faction3, faction4));

        factions.addSatisfactionToFaction("TEST5",50);

        assertThat(factions.getFactionByName("test1").getSatisfaction()).isEqualTo(10);
        assertThat(factions.getFactionByName("test2").getSatisfaction()).isEqualTo(20);
        assertThat(factions.getFactionByName("test3").getSatisfaction()).isEqualTo(30);
        assertThat(factions.getFactionByName("test4").getSatisfaction()).isEqualTo(40);
    }

    @Test
    void should_add_satisfaction_when_the_faction_ist_present() {
        Faction faction1 = new Faction("TEST1", "test1", 10, 100);
        Faction faction2 = new Faction("TEST2", "test2", 20, 200);
        Faction faction3 = new Faction("TEST3", "test3", 30, 300);
        Faction faction4 = new Faction("TEST4", "test4", 40, 400);
        Factions factions = new Factions(List.of(faction1, faction2, faction3, faction4));

        factions.addSatisfactionToFaction("TEST2",50);

        assertThat(factions.getFactionByName("test1").getSatisfaction()).isEqualTo(10);
        assertThat(factions.getFactionByName("test2").getSatisfaction()).isEqualTo(70);
        assertThat(factions.getFactionByName("test3").getSatisfaction()).isEqualTo(30);
        assertThat(factions.getFactionByName("test4").getSatisfaction()).isEqualTo(40);
    }

    @ParameterizedTest
    @CsvSource({"1,1,1", "20,65,50", "10,25,20"})
    void should_return_the_right_satisfaction(int faction1Satisfaction, int faction2Satisfaction, float expectedSatisfaction) {
        Faction faction1 = new Faction("TEST1", "test1", faction1Satisfaction, 100);
        Faction faction2 = new Faction("TEST2", "test2", faction2Satisfaction, 200);
        Factions factions = new Factions(List.of(faction1, faction2));

        float globalSatisfaction = factions.getGlobalSatisfaction();

        assertThat(globalSatisfaction).isEqualTo(expectedSatisfaction);
    }

    @ParameterizedTest
    @CsvSource({"1,1,2", "20,65,85", "10,25,35"})
    void should_return_the_number_of_partisan(int faction1NumberOfPartisan, int faction2NumberOfPartisan, int expectedNumberOfPartisan) {
        Faction faction1 = new Faction("TEST1", "test1", 10, faction1NumberOfPartisan);
        Faction faction2 = new Faction("TEST2", "test2", 20, faction2NumberOfPartisan);
        Factions factions = new Factions(List.of(faction1, faction2));

        int globalSatisfaction = factions.getTotalNumberOfPartisan();

        assertThat(globalSatisfaction).isEqualTo(expectedNumberOfPartisan);
    }
}
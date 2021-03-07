package com.elpresidente.faction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class FactionTest {

    @ParameterizedTest
    @CsvSource({"1,15", "15,225", "34,510"})
    void should_have_the_right_corruption_price(int numberOfPartisan, int expectedCorruptionPrice){
        Faction faction = new Faction("TEST", "test", 10, numberOfPartisan);

        assertThat(faction.getCorruptionPrice()).isEqualTo(expectedCorruptionPrice);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "15,22", "34,51"})
    void should_have_the_right_impact_on_loyalist_when_faction_get_corrupted(int numberOfPartisan, int expectedImpact){
        Faction faction = new Faction("TEST", "test", 10, numberOfPartisan);

        assertThat(faction.getCorruptionImpactOnLoyalist()).isEqualTo(expectedImpact);
    }

    @ParameterizedTest
    @CsvSource({"1,3", "15,17", "34,36"})
    void should_add_the_number_of_partisan_when_a_positive_amount_is_added(int numberOfPartisanToAdd, int expectedNumberOfPartisan){
        Faction faction = new Faction("TEST", "test", 10, 2);

        faction.addPartisanNumber(numberOfPartisanToAdd);

        assertThat(faction.getPartisanNumber()).isEqualTo(expectedNumberOfPartisan);
    }

    @ParameterizedTest
    @CsvSource({"-1,99", "-15,85", "-34,66"})
    void should_subtract_the_number_of_partisan_when_a_negative_amount_is_added(int numberOfPartisanToAdd, int expectedNumberOfPartisan){
        Faction faction = new Faction("TEST", "test", 10, 100);

        faction.addPartisanNumber(numberOfPartisanToAdd);

        assertThat(faction.getPartisanNumber()).isEqualTo(expectedNumberOfPartisan);
    }

    @Test
    void should_partisan_number_don_t_go_under_zero_when_a_negative_amount_is_added(){
        Faction faction = new Faction("TEST", "test", 10, 100);
        int expectedNumberOfPartisan = 0;

        faction.addPartisanNumber(-110);

        assertThat(faction.getPartisanNumber()).isEqualTo(expectedNumberOfPartisan);
    }

    @ParameterizedTest
    @CsvSource({"1,11", "15,25", "34,44"})
    void should_add_satisfaction_when_satisfaction_is_over_zero(int satisfactionToAdd, int expectedSatisfaction){
        Faction faction = new Faction("TEST", "test", 10, 100);

        faction.addSatisfaction(satisfactionToAdd);

        assertThat(faction.getSatisfaction()).isEqualTo(expectedSatisfaction);
    }

    @Test
    void should_satisfaction_don_t_go_over_100(){
        Faction faction = new Faction("TEST", "test", 98, 100);
        int satisfactionToAdd = 25;
        int expectedSatisfaction = 100;

        faction.addSatisfaction(satisfactionToAdd);

        assertThat(faction.getSatisfaction()).isEqualTo(expectedSatisfaction);
    }

    @Test
    void should_satisfaction_don_t_go_under_0(){
        Faction faction = new Faction("TEST", "test", 25, 100);
        int satisfactionToAdd = -100;
        int expectedSatisfaction = 0;

        faction.addSatisfaction(satisfactionToAdd);

        assertThat(faction.getSatisfaction()).isEqualTo(expectedSatisfaction);
    }

    @Test
    void should_not_add_satisfaction_when_satisfaction_is_at_zero(){
        Faction faction = new Faction("TEST", "test", 0, 100);
        int satisfactionToAdd = 100;
        int expectedSatisfaction = 0;

        faction.addSatisfaction(satisfactionToAdd);

        assertThat(faction.getSatisfaction()).isEqualTo(expectedSatisfaction);
    }
}
package com.elpresidente.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class GameParameterTest {
    @Test
    void should_add_amount_to_treasury() {
        GameParameter gameParameter = new GameParameter(50, 50, 100, 100);
        int expectedTreasury = 200;

        gameParameter.addTreasury(100);

        assertThat(gameParameter.getTreasury()).isEqualTo(expectedTreasury);
    }

    @Test
    void should_treasury_don_t_go_under_0() {
        GameParameter gameParameter = new GameParameter(50, 50, 100, 200);

        gameParameter.addTreasury(-1000);

        assertThat(gameParameter.getTreasury()).isZero();
    }

    @Test
    void should_add_amount_to_food_unit() {
        GameParameter gameParameter = new GameParameter(50, 50, 100, 200);
        int expectedTreasury = 300;

        gameParameter.addFood(100);

        assertThat(gameParameter.getFoodUnits()).isEqualTo(expectedTreasury);
    }

    @Test
    void should_food_unit_don_t_go_under_0() {
        GameParameter gameParameter = new GameParameter(50, 50, 100, 100);

        gameParameter.addFood(-1000);

        assertThat(gameParameter.getFoodUnits()).isZero();
    }

    @ParameterizedTest
    @CsvSource({"1,51,49", "20,70,30", "50,100,0"})
    void should_industry_increase_and_agriculture_decrease(int addedAmount, int expectedIndustry, int expectedAgriculture) {
        GameParameter gameParameter = new GameParameter(50, 50, 100, 100);

        gameParameter.addIndustries(addedAmount);

        assertThat(gameParameter.getIndustryPercentage()).isEqualTo(expectedIndustry);
        assertThat(gameParameter.getAgriculturePercentage()).isEqualTo(expectedAgriculture);
    }

    @ParameterizedTest
    @CsvSource({"1,49,51", "20,30,70", "50,0,100"})
    void should_agriculture_increase_and_industry_decrease(int addedAmount, int expectedIndustry, int expectedAgriculture) {
        GameParameter gameParameter = new GameParameter(50, 50, 100, 100);

        gameParameter.addAgriculture(addedAmount);

        assertThat(gameParameter.getIndustryPercentage()).isEqualTo(expectedIndustry);
        assertThat(gameParameter.getAgriculturePercentage()).isEqualTo(expectedAgriculture);
    }

    @Test
    void should_industry_don_t_go_under_zero() {
        GameParameter gameParameter = new GameParameter(50, 50, 100, 100);
        int expectedIndustry = 0;
        int expectedAgriculture = 50;

        gameParameter.addIndustries(-100);

        assertThat(gameParameter.getIndustryPercentage()).isEqualTo(expectedIndustry);
        assertThat(gameParameter.getAgriculturePercentage()).isEqualTo(expectedAgriculture);
    }

    @Test
    void should_agriculture_don_t_go_under_zero() {
        GameParameter gameParameter = new GameParameter(50, 50, 100, 100);
        int expectedIndustry = 50;
        int expectedAgriculture = 0;

        gameParameter.addAgriculture(-100);


        assertThat(gameParameter.getIndustryPercentage()).isEqualTo(expectedIndustry);
        assertThat(gameParameter.getAgriculturePercentage()).isEqualTo(expectedAgriculture);
    }
}
package com.elpresidente.repository.json;

import com.elpresidente.event.Event;
import com.elpresidente.game.GameParameter;
import com.elpresidente.repository.Repository;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonRepositoryTest {
    @Test
    public void should_get_all_faction_from_json() {
        Repository repository = new JsonRepository("src/test/resources/test.json");

        assertThat(repository.getAllFactions().get(0).getName()).isEqualTo("Capitalists");
        assertThat(repository.getAllFactions().get(1).getName()).isEqualTo("Communists");
        assertThat(repository.getAllFactions().get(2).getName()).isEqualTo("Liberals");
        assertThat(repository.getAllFactions().get(3).getName()).isEqualTo("Nationalists");
        assertThat(repository.getAllFactions().get(4).getName()).isEqualTo("Ecologists");
        assertThat(repository.getAllFactions().get(5).getName()).isEqualTo("Religious");
        assertThat(repository.getAllFactions().get(6).getName()).isEqualTo("Militarists");
        assertThat(repository.getAllFactions().get(7).getName()).isEqualTo("Loyalists");
    }

    @Test
    public void should_get_game_parameter_from_json() {
        Repository repository = new JsonRepository("src/test/resources/test.json");

        GameParameter gameParameter = repository.getAllGameParameter();

        assertThat(gameParameter.getIndustryPercentage()).isEqualTo(35);
        assertThat(gameParameter.getAgriculturePercentage()).isEqualTo(40);
        assertThat(gameParameter.getFoodUnits()).isEqualTo(500);
        assertThat(gameParameter.getTreasury()).isEqualTo(700);
    }

    @Test
    public void should_get_events_from_json() {
        Repository repository = new JsonRepository("src/test/resources/test.json");

        List<Event> events = repository.getAllEvent();

        assertThat(events.get(0).getName()).isEqualTo("Des monstres géants approchent le royaume... Que faites-vous ?");
        assertThat(events.get(0).getChoices().get(0).getName()).isEqualTo("J'envoie toute la force militaire du pays pour les éradiquer");
        assertThat(events.get(0).getChoices().get(1).getName()).isEqualTo("Je mets en place une défense basée sur des canons");
        assertThat(events.get(1).getName()).isEqualTo("Ils détournent leur chemin ! Ils s'attaquent à l'agriculture à l'avant du Royaume...");
        assertThat(events.get(1).getChoices().get(0).getName()).isEqualTo("J'élabore une stratégie pour combattre");
        assertThat(events.get(1).getChoices().get(1).getName()).isEqualTo("Je laisse tomber en me disant que j'ai d'autres champs et réserves de provisions");
    }
}
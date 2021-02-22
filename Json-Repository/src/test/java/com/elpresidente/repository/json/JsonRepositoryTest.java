package com.elpresidente.repository.json;

import com.elpresidente.repository.Repository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonRepositoryTest  {
    @Test
    public void petit_test(){
        Repository repository = new JsonRepository("src/test/resources/attackOnTitans.json");

        assertThat(repository.getAllFactions().get(2).getName()).isEqualTo("dd");
    }

    @Test
    public void petit_test2(){
        Repository repository = new JsonRepository("src/test/resources/attackOnTitans.json");

        assertThat(repository.getAllEvent()).isEqualTo("dd");
    }

    @Test
    public void petit_test3(){
        Repository repository = new JsonRepository("src/test/resources/attackOnTitans.json");

        assertThat(repository.getAllGameParameter()).isEqualTo("dd");
    }
}
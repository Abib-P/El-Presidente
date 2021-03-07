package com.elpresidente.repository.json;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JsonRepositoryUtilsTest {
    @Test
    public void should_get_all_file_name() {
        JsonRepositoryUtils jsonRepositoryUtils = new JsonRepositoryUtils();

        Map<String, String> allScenarioName = jsonRepositoryUtils.loadAllScenarioName("src/test/resources");

        //assertThat(allScenarioName.isEmpty()).isTrue();
    }
}
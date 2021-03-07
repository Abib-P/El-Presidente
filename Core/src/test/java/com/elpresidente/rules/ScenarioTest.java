package com.elpresidente.rules;

import com.elpresidente.event.Event;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScenarioTest {

    @Test
    void should_have_event_when_events_are_not_empty() {
        Rules rules = new Scenario(List.of(new Event("Event1"), new Event("Event2")));

        boolean hasEvent = rules.hasEvent();

        assertThat(hasEvent).isEqualTo(true);
    }

    @Test
    void should_have_no_event_when_events_are_empty() {
        Rules rules = new Scenario(List.of());

        boolean hasEvent = rules.hasEvent();

        assertThat(hasEvent).isEqualTo(false);
    }
}
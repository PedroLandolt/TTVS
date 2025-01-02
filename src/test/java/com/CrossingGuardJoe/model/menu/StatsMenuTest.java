package com.CrossingGuardJoe.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatsMenuTest {

    private StatsMenu statsMenu;

    @BeforeEach
    void setUp() {
        // Initialize a StatsMenu instance with test data
        statsMenu = new StatsMenu(500, 10, 1000, 20);
    }

    @Test
    void testGetCurrentScore() {
        // Verify the current score is properly returned
        assertThat(statsMenu.getCurrentScore()).isEqualTo(500);
    }

    @Test
    void testGetCurrentLevel() {
        // Verify the current level is properly returned
        assertThat(statsMenu.getCurrentLevel()).isEqualTo(10);
    }

    @Test
    void testGetHighestScore() {
        // Verify the highest score is properly returned
        assertThat(statsMenu.getHighestScore()).isEqualTo(1000);
    }

    @Test
    void testGetHighestLevel() {
        // Verify the highest level is properly returned
        assertThat(statsMenu.getHighestLevel()).isEqualTo(20);
    }
}
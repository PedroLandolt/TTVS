package com.CrossingGuardJoe.states.menu;

import com.CrossingGuardJoe.controller.menu.StatsMenuController;
import com.CrossingGuardJoe.model.menu.StatsMenu;
import com.CrossingGuardJoe.viewer.menu.StatsMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatsMenuStateTest {

    private StatsMenu statsMenu;
    private StatsMenuState statsMenuState;

    @BeforeEach
    void setUp() {
        // Properly initialize the StatsMenu with the required parameters
        int enemiesKilled = 10;
        int gamesPlayed = 5;
        int highestScore = 1000;
        int totalTimePlayed = 3600; // Assume 3600 seconds (1 hour)

        statsMenu = new StatsMenu(enemiesKilled, gamesPlayed, highestScore, totalTimePlayed);
        statsMenuState = new StatsMenuState(statsMenu);
    }

    @Test
    void testConstructorInitializesStatsMenuStateCorrectly() {
        // Assert that the StatsMenuState is properly initialized with the given StatsMenu model
        assertThat(statsMenuState.getModel())
                .isSameAs(statsMenu)
                .as("StatsMenuState should be initialized with the given StatsMenu model");
    }

    @Test
    void testGetControllerReturnsStatsMenuController() {
        // Act: Retrieve the controller from the StatsMenuState
        var controller = statsMenuState.getController();

        // Assert: Validate the instance type and its association with the StatsMenu model
        assertThat(controller)
                .isInstanceOf(StatsMenuController.class)
                .as("getController() should return an instance of StatsMenuController");
        assertThat(((StatsMenuController) controller).getModel())
                .isSameAs(statsMenu)
                .as("The StatsMenuController should use the same StatsMenu model passed to the StatsMenuState");
    }

    @Test
    void testGetViewerReturnsStatsMenuViewer() {
        // Act: Retrieve the viewer from the StatsMenuState
        var viewer = statsMenuState.getViewer();

        // Assert: Validate the instance type and its association with the StatsMenu model
        assertThat(viewer)
                .isInstanceOf(StatsMenuViewer.class)
                .as("getViewer() should return an instance of StatsMenuViewer");
        assertThat(((StatsMenuViewer) viewer).getModel())
                .isSameAs(statsMenu)
                .as("The StatsMenuViewer should use the same StatsMenu model passed to the StatsMenuState");
    }
}
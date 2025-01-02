package com.CrossingGuardJoe.states;

import com.CrossingGuardJoe.controller.game.RoadController;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.viewer.game.GameViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameStateTest {

    private Road road;
    private GameState gameState;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize the Road model and GameState
        road = new Road(); // Ensure Road has a valid constructor
        gameState = new GameState(road);
    }

    @Test
    void testConstructorInitializesGameStateProperly() {
        // Act: Retrieve the model from the GameState

        // Assert: Verify the GameState is initialized with the given Road model
        assertThat(gameState.getModel())
                .isSameAs(road)
                .as("GameState should be initialized with the given Road model");
    }

    @Test
    void testGetControllerReturnsRoadController() {
        // Act: Retrieve the controller from the GameState
        var controller = gameState.getController();

        // Assert: Verify the returned controller
        assertThat(controller)
                .isInstanceOf(RoadController.class)
                .as("getController() should return an instance of RoadController");
        assertThat(((RoadController) controller).getModel())
                .isSameAs(road)
                .as("The RoadController should use the same Road model passed to the GameState");
    }

    @Test
    void testGetViewerReturnsGameViewer() {
        // Act: Retrieve the viewer from the GameState
        var viewer = gameState.getViewer();

        // Assert: Verify the returned viewer
        assertThat(viewer)
                .isInstanceOf(GameViewer.class)
                .as("getViewer() should return an instance of GameViewer");
        assertThat(((GameViewer) viewer).getModel())
                .isSameAs(road)
                .as("The GameViewer should use the same Road model passed to the GameState");
    }
}
package com.CrossingGuardJoe.states.menu;

import com.CrossingGuardJoe.controller.menu.GameOverMenuController;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.model.menu.GameOverMenu;
import com.CrossingGuardJoe.viewer.menu.GameOverViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GameOverStateTest {

    private GameOverMenu gameOverMenu;
    private GameOverState gameOverState;
    private Road roadMock;

    @BeforeEach
    void setUp() {
        // Create a mock for the Road object (to fulfill GameOverMenu constructor dependency)
        roadMock = mock(Road.class);

        // Initialize GameOverMenu with required parameters
        boolean isGameOver = true; // Use a relevant boolean value
        gameOverMenu = new GameOverMenu(isGameOver, roadMock);

        // Initialize GameOverState with the created GameOverMenu
        gameOverState = new GameOverState(gameOverMenu);
    }

    @Test
    void testConstructorInitializesGameOverStateCorrectly() {
        // Verify that GameOverState is initialized with the provided GameOverMenu
        assertThat(gameOverState.getModel())
                .isSameAs(gameOverMenu)
                .as("GameOverState should be initialized with the given GameOverMenu model");
    }

    @Test
    void testGetControllerReturnsCorrectController() {
        // Verify that getController returns an instance of GameOverMenuController
        assertThat(gameOverState.getController())
                .isInstanceOf(GameOverMenuController.class)
                .as("getController() should return a GameOverMenuController instance using the correct model");

        // Ensure the GameOverMenuController is linked to the correct model
        GameOverMenuController controller = (GameOverMenuController) gameOverState.getController();
        assertThat(controller.getModel())
                .isSameAs(gameOverMenu)
                .as("The GameOverMenuController should use the same GameOverMenu model passed to the state");
    }

    @Test
    void testGetViewerReturnsCorrectViewer() {
        // Verify that getViewer returns an instance of GameOverViewer
        assertThat(gameOverState.getViewer())
                .isInstanceOf(GameOverViewer.class)
                .as("getViewer() should return a GameOverViewer instance using the correct model");

        // Ensure the GameOverViewer is linked to the correct model
        GameOverViewer viewer = (GameOverViewer) gameOverState.getViewer();
        assertThat(viewer.getModel())
                .isSameAs(gameOverMenu)
                .as("The GameOverViewer should use the same GameOverMenu model passed to the state");
    }
}
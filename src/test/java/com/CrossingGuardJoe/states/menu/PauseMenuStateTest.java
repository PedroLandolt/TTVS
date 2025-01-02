package com.CrossingGuardJoe.states.menu;

import com.CrossingGuardJoe.controller.menu.PauseMenuController;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.model.menu.PauseMenu;
import com.CrossingGuardJoe.viewer.menu.PauseMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PauseMenuStateTest {

    private PauseMenu pauseMenu;
    private PauseMenuState pauseMenuState;

    @BeforeEach
    void setUp() {
        // Create a Road object (can be a real or mock object)
        Road road = new Road();

        // Initialize PauseMenu with the required Road object
        pauseMenu = new PauseMenu(road);

        // Initialize PauseMenuState with the created PauseMenu
        pauseMenuState = new PauseMenuState(pauseMenu);
    }

    @Test
    void testConstructorInitializesPauseMenuStateProperly() {
        // Assert: Ensure the PauseMenuState is initialized with the correct PauseMenu model
        assertThat(pauseMenuState.getModel())
                .isSameAs(pauseMenu)
                .as("PauseMenuState should be initialized with the given PauseMenu model");
    }

    @Test
    void testGetControllerReturnsPauseMenuController() {
        // Act: Retrieve the controller from the PauseMenuState
        var controller = pauseMenuState.getController();

        // Assert: Verify the controller instance and its internal state
        assertThat(controller)
                .isInstanceOf(PauseMenuController.class)
                .as("getController() should return an instance of PauseMenuController");
        assertThat(((PauseMenuController) controller).getModel())
                .isSameAs(pauseMenu)
                .as("The PauseMenuController should use the same PauseMenu model given to the PauseMenuState");
    }

    @Test
    void testGetViewerReturnsPauseMenuViewer() {
        // Act: Retrieve the viewer from the PauseMenuState
        var viewer = pauseMenuState.getViewer();

        // Assert: Verify the viewer instance and its internal state
        assertThat(viewer)
                .isInstanceOf(PauseMenuViewer.class)
                .as("getViewer() should return an instance of PauseMenuViewer");
        assertThat(((PauseMenuViewer) viewer).getModel())
                .isSameAs(pauseMenu)
                .as("The PauseMenuViewer should use the same PauseMenu model given to the PauseMenuState");
    }
}
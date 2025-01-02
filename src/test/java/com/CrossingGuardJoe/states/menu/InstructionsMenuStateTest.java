package com.CrossingGuardJoe.states.menu;

import com.CrossingGuardJoe.controller.menu.InstructionsMenuController;
import com.CrossingGuardJoe.model.menu.InstructionsMenu;
import com.CrossingGuardJoe.viewer.menu.InstructionsMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstructionsMenuStateTest {

    private InstructionsMenu instructionsMenu;
    private InstructionsMenuState instructionsMenuState;

    @BeforeEach
    void setUp() {
        // Create a new instance of the InstructionsMenu and InstructionsMenuState
        instructionsMenu = new InstructionsMenu();
        instructionsMenuState = new InstructionsMenuState(instructionsMenu);
    }

    @Test
    void testConstructorInitializesInstructionsMenuStateCorrectly() {
        // Validate that the state has been properly initialized with the provided InstructionsMenu
        assertThat(instructionsMenuState.getModel())
                .isSameAs(instructionsMenu)
                .as("The InstructionsMenuState should use the given InstructionsMenu model");
    }

    @Test
    void testGetControllerReturnsInstructionsMenuController() {
        // Ensure that getController returns an InstructionsMenuController
        assertThat(instructionsMenuState.getController())
                .isInstanceOf(InstructionsMenuController.class)
                .as("getController() should return an instance of InstructionsMenuController");

        // Verify that the controller is linked to the correct model
        InstructionsMenuController controller = (InstructionsMenuController) instructionsMenuState.getController();
        assertThat(controller.getModel())
                .isSameAs(instructionsMenu)
                .as("The InstructionsMenuController should use the same InstructionsMenu model passed to the state");
    }

    @Test
    void testGetViewerReturnsInstructionsMenuViewer() {
        // Ensure that getViewer returns an InstructionsMenuViewer
        assertThat(instructionsMenuState.getViewer())
                .isInstanceOf(InstructionsMenuViewer.class)
                .as("getViewer() should return an instance of InstructionsMenuViewer");

        // Verify that the viewer is linked to the correct model
        InstructionsMenuViewer viewer = (InstructionsMenuViewer) instructionsMenuState.getViewer();
        assertThat(viewer.getModel())
                .isSameAs(instructionsMenu)
                .as("The InstructionsMenuViewer should use the same InstructionsMenu model passed to the state");
    }
}
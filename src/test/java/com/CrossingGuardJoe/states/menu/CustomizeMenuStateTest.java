package com.CrossingGuardJoe.states.menu;

import com.CrossingGuardJoe.controller.menu.CustomizeMenuController;
import com.CrossingGuardJoe.model.menu.CustomizeMenu;
import com.CrossingGuardJoe.viewer.menu.CustomizeMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomizeMenuStateTest {

    private CustomizeMenu customizeMenu;
    private CustomizeMenuState customizeMenuState;

    @BeforeEach
    void setUp() {
        // Initialize the CustomizeMenu and CustomizeMenuState
        customizeMenu = new CustomizeMenu();
        customizeMenuState = new CustomizeMenuState(customizeMenu);
    }

    @Test
    void testConstructorInitializesStateCorrectly() {
        // Verify that the state correctly initializes its model
        assertThat(customizeMenuState.getModel()).isSameAs(customizeMenu)
                .as("CustomizeMenuState should initialize with the provided CustomizeMenu model");
    }

    @Test
    void testGetControllerReturnsCorrectController() {
        // Verify that getController returns an instance of CustomizeMenuController
        assertThat(customizeMenuState.getController())
                .isInstanceOf(CustomizeMenuController.class)
                .as("getController() should return a CustomizeMenuController that uses the same model");

        // Ensure the CustomizeMenuController is initialized with the correct model
        CustomizeMenuController controller = (CustomizeMenuController) customizeMenuState.getController();
        assertThat(controller.getModel()).isSameAs(customizeMenu)
                .as("The CustomizeMenuController should use the same CustomizeMenu model as the state");
    }

    @Test
    void testGetViewerReturnsCorrectViewer() {
        // Verify that getViewer returns an instance of CustomizeMenuViewer
        assertThat(customizeMenuState.getViewer())
                .isInstanceOf(CustomizeMenuViewer.class)
                .as("getViewer() should return a CustomizeMenuViewer that uses the same model");

        // Ensure the CustomizeMenuViewer is initialized with the correct model
        CustomizeMenuViewer viewer = (CustomizeMenuViewer) customizeMenuState.getViewer();
        assertThat(viewer.getModel()).isSameAs(customizeMenu)
                .as("The CustomizeMenuViewer should use the same CustomizeMenu model as the state");
    }
}
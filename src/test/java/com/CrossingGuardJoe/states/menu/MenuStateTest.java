package com.CrossingGuardJoe.states.menu;

import com.CrossingGuardJoe.controller.menu.MenuController;
import com.CrossingGuardJoe.model.menu.Menu;
import com.CrossingGuardJoe.viewer.menu.MenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuStateTest {

    private Menu menu;
    private MenuState menuState;

    @BeforeEach
    void setUp() {
        // Setup required objects for testing
        menu = new Menu();
        menuState = new MenuState(menu);
    }

    @Test
    void testConstructorInitializesMenuStateCorrectly() {
        // Validate that the MenuState is initialized with the provided Menu object
        assertThat(menuState.getModel())
                .isSameAs(menu)
                .as("The MenuState should be initialized with the provided Menu model");
    }

    @Test
    void testGetControllerReturnsMenuController() {
        // Ensure that getController returns an instance of MenuController
        assertThat(menuState.getController())
                .isInstanceOf(MenuController.class)
                .as("getController() should return an instance of MenuController");

        // Verify that the MenuController is linked to the correct model
        MenuController controller = (MenuController) menuState.getController();
        assertThat(controller.getModel())
                .isSameAs(menu)
                .as("The MenuController should use the same Menu model passed to the MenuState");
    }

    @Test
    void testGetViewerReturnsMenuViewer() {
        // Ensure that getViewer returns an instance of MenuViewer
        assertThat(menuState.getViewer())
                .isInstanceOf(MenuViewer.class)
                .as("getViewer() should return an instance of MenuViewer");

        // Verify that the MenuViewer is linked to the correct model
        MenuViewer viewer = (MenuViewer) menuState.getViewer();
        assertThat(viewer.getModel())
                .isSameAs(menu)
                .as("The MenuViewer should use the same Menu model passed to the MenuState");
    }
}
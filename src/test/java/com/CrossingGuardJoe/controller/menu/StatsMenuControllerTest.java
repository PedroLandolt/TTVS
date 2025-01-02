package com.CrossingGuardJoe.controller.menu;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.menu.StatsMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class StatsMenuControllerTest {

    private StatsMenuController statsMenuController;
    private StatsMenu statsMenuMock;
    private Game gameMock;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        statsMenuMock = mock(StatsMenu.class);
        gameMock = mock(Game.class);

        // Create the StatsMenuController with the mocked StatsMenu
        statsMenuController = new StatsMenuController(statsMenuMock);
    }

    @Test
    void testConstructorInitializesModel() {
        // Assert that the controller initializes the passed model correctly
        assert statsMenuController.getModel() == statsMenuMock;
    }

    @Test
    void testNextActionWithESC() throws IOException {
        // Act: Call nextAction with ESC action
        statsMenuController.nextAction(gameMock, GUI.ACTION.ESC, 0);

        // Assert: Verify game.popState() is called once
        verify(gameMock, times(1)).popState();

        // Ensure no other interactions occur
        verifyNoMoreInteractions(gameMock);
    }

    @Test
    void testNextActionWithNonESCAction() throws IOException {
        // Act: Call nextAction with a non-ESC action (e.g., UP)
        statsMenuController.nextAction(gameMock, GUI.ACTION.UP, 0);

        // Assert: Verify game.popState() is NOT called
        verify(gameMock, never()).popState();

        // Ensure no other interactions occur
        verifyNoMoreInteractions(gameMock);
    }
}
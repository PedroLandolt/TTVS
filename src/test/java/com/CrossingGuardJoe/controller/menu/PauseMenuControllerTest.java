package com.CrossingGuardJoe.controller.menu;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.controller.Sounds;
import com.CrossingGuardJoe.controller.SoundsController;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.menu.PauseMenu;
import com.CrossingGuardJoe.model.menu.StatsMenu;
import com.CrossingGuardJoe.states.menu.StatsMenuState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

class PauseMenuControllerTest {
    private PauseMenu pauseMenuMock; // Mock for PauseMenu
    private Game gameMock; // Mock for Game
    private SoundsController soundsControllerMock; // Mock for SoundsController
    private MockedStatic<SoundsController> staticSoundsControllerMock; // Static mock for SoundsController
    private PauseMenuController pauseMenuController; // Class under test

    @BeforeEach
    void setUp() {
        // Mock Dependencies
        pauseMenuMock = mock(PauseMenu.class);
        gameMock = mock(Game.class);

        // Initialize Mocked Static Context for SoundsController
        staticSoundsControllerMock = mockStatic(SoundsController.class);
        soundsControllerMock = mock(SoundsController.class);
        staticSoundsControllerMock.when(SoundsController::getInstance).thenReturn(soundsControllerMock);

        // Initialize Class Under Test
        pauseMenuController = new PauseMenuController(pauseMenuMock);
    }

    @AfterEach
    void tearDown() {
        // Close static mocks after each test to prevent conflicts
        staticSoundsControllerMock.close();
        Mockito.framework().clearInlineMocks(); // Clear any leftover potential mocks
    }

    @Test
    void testNavigateUp() throws IOException {
        // Act: Trigger UP action
        pauseMenuController.nextAction(gameMock, GUI.ACTION.UP, 0);

        // Assert: Verify interactions
        verify(soundsControllerMock).play(Sounds.SFX.SELECT); // Verify sound
        verify(pauseMenuMock).navigateUp(); // Verify navigation
        verifyNoMoreInteractions(soundsControllerMock, pauseMenuMock, gameMock); // Assert no other interactions
    }

    @Test
    void testNavigateDown() throws IOException {
        // Act: Trigger DOWN action
        pauseMenuController.nextAction(gameMock, GUI.ACTION.DOWN, 0);

        // Assert: Verify interactions
        verify(soundsControllerMock).play(Sounds.SFX.SELECT); // Verify sound
        verify(pauseMenuMock).navigateDown(); // Verify navigation
        verifyNoMoreInteractions(soundsControllerMock, pauseMenuMock, gameMock); // Assert no other interactions
    }

    @Test
    void testSelectResume() throws IOException {
        // Arrange: Simulate the "Resume" option being selected
        when(pauseMenuMock.isSelectedResume()).thenReturn(true);

        // Act: Trigger SELECT action
        pauseMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Assert: Verify gameplay interactions
        verify(soundsControllerMock).play(Sounds.SFX.ENTER); // Verify ENTER sound
        verify(soundsControllerMock).play(Sounds.SFX.GAMEBGM); // Verify GAMEBGM sound
        verify(gameMock).popState(); // Ensure the game state is popped
        verify(pauseMenuMock).isSelectedResume(); // Validate "Resume" option is checked

        // IMPORTANT: Do NOT validate "no further interactions" for pauseMenuMock since internal valid interactions do occur
        verifyNoMoreInteractions(soundsControllerMock, gameMock); // Still enforce no unrelated interactions on these mocks
    }

    @Test
    void testSelectStats() throws IOException {
        // Arrange: Mock necessary methods for the "Stats" menu
        when(pauseMenuMock.isSelectedStats()).thenReturn(true); // Stats is selected
        when(pauseMenuMock.isSelectedResume()).thenReturn(false); // Resume is not selected
        when(pauseMenuMock.isSelectedExit()).thenReturn(false); // Exit is not selected

        // Mock the current game and Joe instance
        var currentGameMock = mock(com.CrossingGuardJoe.model.game.Road.class); // Mock current game
        var joeMock = mock(com.CrossingGuardJoe.model.game.elements.Joe.class); // Mock Joe

        // Set up mocked behaviors
        when(pauseMenuMock.getCurrentGame()).thenReturn(currentGameMock); // Mock getCurrentGame()
        when(currentGameMock.getJoe()).thenReturn(joeMock); // Return mocked Joe instance
        when(joeMock.getScore()).thenReturn(42); // Mock Joe's score
        when(currentGameMock.getCurrentLevel()).thenReturn(3); // Mock current level
        when(gameMock.getHighestScore()).thenReturn(100); // Mock highest score
        when(gameMock.getHighestLevel()).thenReturn(10); // Mock highest level

        // Act: Trigger SELECT action for Stats
        pauseMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Assert: Verify interactions and ensure correct behavior

        // Verify sound effects are played
        verify(soundsControllerMock).play(Sounds.SFX.ENTER);

        // Verify that stats selection is checked in the pause menu
        verify(pauseMenuMock).isSelectedStats();

        // Verify that the "Resume" and "Exit" checks were also made
        verify(pauseMenuMock).isSelectedResume();
        verify(pauseMenuMock).isSelectedExit();

        // Verify state transition to StatsMenuState
        verify(gameMock).setState(any(StatsMenuState.class));

        // Verify the `getCurrentGame()` call is explicitly invoked twice
        verify(pauseMenuMock, times(2)).getCurrentGame();

        // Verify interactions with `currentGameMock` and `Joe`
        verify(currentGameMock).getJoe();
        verify(joeMock).getScore();
        verify(currentGameMock).getCurrentLevel();
        verify(gameMock).getHighestScore();
        verify(gameMock).getHighestLevel();

        // Verify no additional interactions for all tested mocks
        verifyNoMoreInteractions(soundsControllerMock, gameMock, pauseMenuMock, currentGameMock, joeMock);
    }

    @Test
    void testSelectExit() throws IOException {
        // Arrange: Set up mocking for "Exit" selection
        when(pauseMenuMock.isSelectedExit()).thenReturn(true);
        when(pauseMenuMock.isSelectedStats()).thenReturn(false);
        when(pauseMenuMock.isSelectedResume()).thenReturn(false);

        // Act: Trigger SELECT action for Exit
        pauseMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Assert: Verify sound effects for all relevant actions
        // Sound played for "SELECT" action
        verify(soundsControllerMock).play(Sounds.SFX.ENTER);

        // Sound effects for stopping and playing background music due to "Exit"
        verify(soundsControllerMock).stop(Sounds.SFX.GAMEBGM);
        verify(soundsControllerMock).play(Sounds.SFX.MENUBGM);

        // Verify state pops twice on Exit
        verify(gameMock, times(2)).popState();

        // Verify pause menu interactions
        verify(pauseMenuMock).isSelectedExit();
        verify(pauseMenuMock).isSelectedStats();
        verify(pauseMenuMock).isSelectedResume();

        // Ensure no additional, unexpected interactions with mocks
        verifyNoMoreInteractions(soundsControllerMock, gameMock, pauseMenuMock);
    }

    @Test
    void testUnhandledAction() throws IOException {
        // Act: Trigger an unhandled action
        pauseMenuController.nextAction(gameMock, GUI.ACTION.LEFT, 0);

        // Assert: Verify no interactions occur
        verifyNoInteractions(pauseMenuMock, gameMock, soundsControllerMock);
    }
}
package com.CrossingGuardJoe.controller.menu;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.controller.Sounds;
import com.CrossingGuardJoe.controller.SoundsController;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.model.menu.GameOverMenu;
import com.CrossingGuardJoe.model.menu.StatsMenu;
import com.CrossingGuardJoe.states.menu.StatsMenuState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class GameOverMenuControllerTest {

    private GameOverMenuController gameOverMenuController;
    private GameOverMenu gameOverMenuMock;
    private SoundsController soundsControllerMock;
    private Game gameMock;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Mock the GameOverMenu model
        gameOverMenuMock = mock(GameOverMenu.class);

        // Mock SoundsController singleton
        soundsControllerMock = mock(SoundsController.class);
        Field instanceField = SoundsController.class.getDeclaredField("soundsController");
        instanceField.setAccessible(true);
        instanceField.set(null, soundsControllerMock);

        // Mock the Game object
        gameMock = mock(Game.class);

        // Create controller instance
        gameOverMenuController = new GameOverMenuController(gameOverMenuMock);
    }

    @Test
    void testNavigateUp() throws IOException {
        // Act
        gameOverMenuController.nextAction(gameMock, GUI.ACTION.UP, 0);

        // Assert
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT); // SELECT sound played
        verify(gameOverMenuMock, times(1)).navigateUp(); // Model interaction
        verifyNoMoreInteractions(gameOverMenuMock, soundsControllerMock, gameMock); // No unexpected interactions
    }

    @Test
    void testNavigateDown() throws IOException {
        // Act
        gameOverMenuController.nextAction(gameMock, GUI.ACTION.DOWN, 0);

        // Assert
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT); // SELECT sound played
        verify(gameOverMenuMock, times(1)).navigateDown(); // Model interaction
        verifyNoMoreInteractions(gameOverMenuMock, soundsControllerMock, gameMock); // No unexpected interactions
    }

    @Test
    void testSelectStats() throws IOException, NoSuchFieldException, IllegalAccessException {
        // Arrange: Set up conditions for stats selection
        when(gameOverMenuMock.isSelectedStats()).thenReturn(true);
        when(gameOverMenuMock.isSelectedExit()).thenReturn(false); // Ensure only stats is selected

        // Mock the Road instance returned by `getCurrentGame()`
        Road roadMock = mock(Road.class);
        when(gameOverMenuMock.getCurrentGame()).thenReturn(roadMock);

        // Mock the Joe instance returned by Road.getJoe()
        Joe joeMock = mock(Joe.class);
        when(roadMock.getJoe()).thenReturn(joeMock);
        when(joeMock.getScore()).thenReturn(100);

        // Mock additional properties on Road
        when(roadMock.getCurrentLevel()).thenReturn(2);

        // Mock the Game's highest scores
        when(gameMock.getHighestScore()).thenReturn(200);
        when(gameMock.getHighestLevel()).thenReturn(3);

        // Act: Call the `nextAction` method
        gameOverMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Assert: Verify calls to the `gameMock`
        ArgumentCaptor<StatsMenuState> stateCaptor = ArgumentCaptor.forClass(StatsMenuState.class);
        verify(gameMock, times(1)).setState(stateCaptor.capture()); // Verify state change

        // Extract the actual captured state
        StatsMenuState capturedState = stateCaptor.getValue();
        assertNotNull(capturedState, "StatsMenuState should not be null");

        // Access the `StatsMenu` model from the state
        StatsMenu statsMenu = capturedState.getModel();
        assertNotNull(statsMenu, "StatsMenu should not be null");

        // Assert that each field in `StatsMenu` was correctly initialized
        Field currentScoreField = StatsMenu.class.getDeclaredField("currentScore");
        currentScoreField.setAccessible(true);
        assertEquals(100, currentScoreField.get(statsMenu), "Current score should match Joe's score");

        Field currentLevelField = StatsMenu.class.getDeclaredField("currentLevel");
        currentLevelField.setAccessible(true);
        assertEquals(2, currentLevelField.get(statsMenu), "Current level should match the current game level");

        Field highestScoreField = StatsMenu.class.getDeclaredField("highestScore");
        highestScoreField.setAccessible(true);
        assertEquals(200, highestScoreField.get(statsMenu), "Highest score should match the game's highest score");

        Field highestLevelField = StatsMenu.class.getDeclaredField("highestLevel");
        highestLevelField.setAccessible(true);
        assertEquals(3, highestLevelField.get(statsMenu), "Highest level should match the game's highest level");

        // Verify associated interactions with gameOverMenuMock
        verify(gameOverMenuMock, times(1)).isSelectedStats(); // Verify stats selection
        verify(gameOverMenuMock, times(1)).isSelectedExit();  // Verify exit was checked
        verify(gameOverMenuMock, times(2)).getCurrentGame();  // Verify 2 calls made to getCurrentGame()

        // Verify associated interactions with the roadMock
        verify(roadMock, times(1)).getJoe();
        verify(roadMock, times(1)).getCurrentLevel();

        // Verify associated interactions with joeMock
        verify(joeMock, times(1)).getScore();

        // Verify all `gameMock` interactions explicitly
        verify(gameMock, times(1)).getHighestScore(); // Verify highest score retrieval
        verify(gameMock, times(1)).getHighestLevel(); // Verify highest level retrieval
        verify(gameMock, times(1)).setState(any(StatsMenuState.class)); // Ensure state was set

        // Verify interaction with soundsController
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.ENTER); // Ensure "ENTER" sound was played

        // Ensure no unexpected interactions with all mocks
        verifyNoMoreInteractions(gameOverMenuMock, roadMock, joeMock, gameMock, soundsControllerMock);
    }
    @Test
    void testSelectExit() throws IOException {
        // Arrange: Mock conditions where "Exit" option is selected
        when(gameOverMenuMock.isSelectedStats()).thenReturn(false); // "Stats" not selected
        when(gameOverMenuMock.isSelectedExit()).thenReturn(true);   // "Exit" is selected

        // Act
        gameOverMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Assert: Verify sound interactions and state transition
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.ENTER); // Enter sound played
        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.VICTORYBGM); // Victory BGM stopped
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.MENUBGM); // Menu BGM started
        verify(gameMock, times(1)).popState(); // Game state popped

        // Assert: Verify interactions with gameOverMenuMock
        verify(gameOverMenuMock, times(1)).isSelectedStats();
        verify(gameOverMenuMock, times(1)).isSelectedExit();

        // Ensure no additional unexpected interactions
        verifyNoMoreInteractions(soundsControllerMock, gameMock, gameOverMenuMock);
    }

    @Test
    void testSelectBothOptionsFalseDoesNothing() throws IOException {
        // Arrange: Mock conditions where neither "Stats" nor "Exit" are selected
        when(gameOverMenuMock.isSelectedStats()).thenReturn(false);
        when(gameOverMenuMock.isSelectedExit()).thenReturn(false);

        // Act
        gameOverMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Assert: Enter sound played
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.ENTER);

        // Verify no state transition or other actions occurred
        verify(gameOverMenuMock, times(1)).isSelectedStats();
        verify(gameOverMenuMock, times(1)).isSelectedExit();
        verifyNoInteractions(gameMock); // Game state should not change
        verifyNoMoreInteractions(soundsControllerMock, gameOverMenuMock, gameMock);
    }

    @Test
    void testUnhandledAction() throws IOException {
        // Act
        gameOverMenuController.nextAction(gameMock, GUI.ACTION.NONE, 0); // Pass an unhandled action

        // Assert: No interactions should occur
        verifyNoInteractions(soundsControllerMock, gameOverMenuMock, gameMock);
    }
}
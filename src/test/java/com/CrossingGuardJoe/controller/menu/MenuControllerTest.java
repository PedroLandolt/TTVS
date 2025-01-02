package com.CrossingGuardJoe.controller.menu;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.controller.Sounds;
import com.CrossingGuardJoe.controller.SoundsController;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.model.game.RoadBuilder;
import com.CrossingGuardJoe.model.menu.Menu;
import com.CrossingGuardJoe.states.GameState;
import com.CrossingGuardJoe.states.menu.CustomizeMenuState;
import com.CrossingGuardJoe.states.menu.InstructionsMenuState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuControllerTest {
    private MenuController menuController; // Class under test
    private Menu menuMock;
    private Game gameMock;
    private SoundsController soundsControllerMock;
    private MockedStatic<SoundsController> soundsControllerStaticMock;

    @BeforeEach
    void setUp() {
        menuMock = mock(Menu.class);
        gameMock = mock(Game.class);
        soundsControllerMock = mock(SoundsController.class);

        // Mock static access to SoundsController.getInstance()
        soundsControllerStaticMock = mockStatic(SoundsController.class);
        soundsControllerStaticMock.when(SoundsController::getInstance).thenReturn(soundsControllerMock);

        // Initialize the class under test
        menuController = new MenuController(menuMock);

        // Verify constructor behavior
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.MENUBGM);
    }

    @AfterEach
    void tearDown() {
        // Close static mocks to avoid contamination in other tests
        soundsControllerStaticMock.close();
    }

    @Test
    void testNavigateUp() throws IOException {
        // Act: Call `nextAction` with the UP action
        menuController.nextAction(gameMock, GUI.ACTION.UP, 0);

        // Assert: Verify behavior
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT);
        verify(menuMock, times(1)).navigateUp();
        verifyNoMoreInteractions(soundsControllerMock, menuMock, gameMock);
    }

    @Test
    void testNavigateDown() throws IOException {
        // Act: Call `nextAction` with the DOWN action
        menuController.nextAction(gameMock, GUI.ACTION.DOWN, 0);

        // Assert: Verify behavior
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT);
        verify(menuMock, times(1)).navigateDown();
        verifyNoMoreInteractions(soundsControllerMock, menuMock, gameMock);
    }

    @Test
    void testSelectStartGame() throws IOException {
        // Arrange: Mock menu selections
        when(menuMock.isSelectedStartGame()).thenReturn(true);       // StartGame selected
        when(menuMock.isSelectedInstructions()).thenReturn(false);  // Others deselected
        when(menuMock.isSelectedCustomize()).thenReturn(false);
        when(menuMock.isSelectedExit()).thenReturn(false);

        // Mock Road and RoadBuilder
        Road mockRoad = mock(Road.class);
        RoadBuilder mockRoadBuilder = mock(RoadBuilder.class);
        when(mockRoadBuilder.createRoad()).thenReturn(mockRoad);

        // Act: Simulate selection of "Start Game"
        menuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Assert: Verify interactions
        // 1. SoundsController interactions
        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.MENUBGM);  // Verify background music stops
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.ENTER);   // Verify "Enter" sound plays
        verify(soundsControllerMock, times(4)).play(Sounds.SFX.GAMEBGM); // Verify the repeated calls during GameController

        // 2. Menu interactions
        verify(menuMock, times(1)).isSelectedStartGame();       // Verify StartGame was queried
        verify(menuMock, times(1)).isSelectedInstructions();    // Verify Instructions were queried
        verify(menuMock, times(1)).isSelectedCustomize();       // Verify Customize was queried
        verify(menuMock, times(1)).isSelectedExit();            // Verify Exit was queried

        // 3. Game State transition
        verify(gameMock, times(1)).setState(any(GameState.class)); // Verify GameState transition

        // Ensure no further unexpected interactions
        verifyNoMoreInteractions(menuMock, soundsControllerMock, gameMock);
    }

    @Test
    void testSelectInstructions() throws IOException {
        // Arrange: Mock conditions
        when(menuMock.isSelectedInstructions()).thenReturn(true);

        // Act: Call nextAction to simulate the SELECT interaction
        menuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Assert: Check if the state transition occurred as expected
        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.MENUBGM);
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.ENTER);
        verify(gameMock, times(1)).setState(any(InstructionsMenuState.class)); // Verify state change to instructions
    }

    @Test
    void testSelectCustomize() throws IOException {
        // Arrange: Mock isSelectedCustomize to return true
        when(menuMock.isSelectedCustomize()).thenReturn(true);

        // Prevent unnecessary side effects during state transition
        doNothing().when(gameMock).setState(any(CustomizeMenuState.class));

        // Act: Call nextAction with the SELECT action
        menuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Assert: Verify correct behavior
        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.MENUBGM); // Background music stops
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.ENTER);  // Confirm "enter" sound
        verify(gameMock, times(1)).setState(any(CustomizeMenuState.class)); // Verify state transition
    }

    @Test
    void testSelectExit() throws IOException, InterruptedException {
        // Build a process to run the helper class
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
                "-cp",
                System.getProperty("java.class.path"),
                "com.CrossingGuardJoe.controller.menu.MenuControllerExitTestHelper"
        );

        // Start the process
        Process process = processBuilder.start();

        // Wait for process to complete (or timeout after a few seconds)
        boolean finished = process.waitFor(10, TimeUnit.SECONDS);

        // Ensure the process finished
        if (!finished) {
            // Destroy the process if it runs too long (this is a safety measure)
            process.destroy();
            throw new IllegalStateException("The process did not exit in a timely manner.");
        }

        // Assert: The process should exit with code 0 (indicating System.exit(0) was called)
        int exitCode = process.exitValue();
        assertEquals(0, exitCode, "System.exit(0) was not called as expected.");
    }

    @Test
    void testUnhandledAction() throws IOException {
        // Reset the mock to ensure no interactions occur
        reset(soundsControllerMock, menuMock, gameMock);

        // Act: Call `nextAction` with an unhandled action (e.g., LEFT)
        menuController.nextAction(gameMock, GUI.ACTION.LEFT, 0);

        // Assert: Verify no interactions occur
        verifyNoInteractions(menuMock, gameMock, soundsControllerMock);
    }
}
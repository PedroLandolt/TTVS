package com.CrossingGuardJoe.controller.menu;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.controller.Sounds;
import com.CrossingGuardJoe.controller.SoundsController;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.menu.InstructionsMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;

import static org.mockito.Mockito.*;

class InstructionsMenuControllerTest {
    private InstructionsMenuController instructionsMenuController;
    private InstructionsMenu instructionsMenuMock;
    private Game gameMock;
    private SoundsController soundsControllerMock;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        instructionsMenuMock = mock(InstructionsMenu.class);
        gameMock = mock(Game.class);
        soundsControllerMock = mock(SoundsController.class);

        // Mock static access to SoundsController.getInstance()
        try (MockedStatic<SoundsController> mockedStatic = mockStatic(SoundsController.class)) {
            // When getInstance is called, return the mock soundsController
            mockedStatic.when(SoundsController::getInstance).thenReturn(soundsControllerMock);

            // Initialize the InstructionsMenuController
            instructionsMenuController = new InstructionsMenuController(instructionsMenuMock);

            // Verify that the play method is called upon initialization
            verify(soundsControllerMock, times(1)).play(Sounds.SFX.INSTRUCTIONSBGM);
        }
    }

    @Test
    void testInitialSoundIsPlayed() {
        // Verify `Sounds.SFX.INSTRUCTIONSBGM` is played during initialization
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.INSTRUCTIONSBGM);
    }

    @Test
    void testNavigateRightAction() throws IOException {
        // Act: Call `nextAction` with the RIGHT action
        instructionsMenuController.nextAction(gameMock, GUI.ACTION.RIGHT, 0);

        // Assert: Verify `navigateRight` is called on the model
        verify(instructionsMenuMock, times(1)).navigateRight();
        verifyNoMoreInteractions(instructionsMenuMock, gameMock, soundsControllerMock);
    }

    @Test
    void testNavigateLeftAction() throws IOException {
        // Act: Call `nextAction` with the LEFT action
        instructionsMenuController.nextAction(gameMock, GUI.ACTION.LEFT, 0);

        // Assert: Verify `navigateLeft` is called on the model
        verify(instructionsMenuMock, times(1)).navigateLeft();
        verifyNoMoreInteractions(instructionsMenuMock, gameMock, soundsControllerMock);
    }

    @Test
    void testEscapeAction() throws IOException {
        // Mock static access to SoundsController.getInstance()
        MockedStatic<SoundsController> mockedStatic = mockStatic(SoundsController.class);
        mockedStatic.when(SoundsController::getInstance).thenReturn(soundsControllerMock);

        // Act: Call `nextAction` with the ESC action
        instructionsMenuController.nextAction(gameMock, GUI.ACTION.ESC, 0);

        // Assert: Verify the required interactions on soundsControllerMock
        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.INSTRUCTIONSBGM);
        verify(gameMock, times(1)).popState();
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.MENUBGM);

        // Ensure no other interactions occurred
        verifyNoMoreInteractions(soundsControllerMock);

        // Close static mocking to prevent pollution
        mockedStatic.close();
    }

    @Test
    void testUnhandledActionDoesNothing() throws IOException {
        // Reset mock interactions after constructor calls
        reset(soundsControllerMock);

        // Act: Call `nextAction` with an unhandled action (e.g., UP)
        instructionsMenuController.nextAction(gameMock, GUI.ACTION.UP, 0);

        // Assert: Verify no interactions with mocks after the call
        verifyNoInteractions(instructionsMenuMock, gameMock, soundsControllerMock);
    }
}
package com.CrossingGuardJoe.controller.menu;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.controller.Sounds;
import com.CrossingGuardJoe.controller.SoundsController;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.menu.ColorPaletteMenu;
import com.CrossingGuardJoe.model.menu.CustomizeMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomizeMenuControllerTest {

    private CustomizeMenuController customizeMenuController;
    private CustomizeMenu customizeMenuMock;
    private SoundsController soundsControllerMock;
    private Game gameMock;
    private ColorPaletteMenu colorPaletteMenuMock;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Mock the CustomizeMenu model
        customizeMenuMock = mock(CustomizeMenu.class);

        // Mock the ColorPaletteMenu
        colorPaletteMenuMock = mock(ColorPaletteMenu.class);
        when(customizeMenuMock.getColorPaletteMenu()).thenReturn(colorPaletteMenuMock);

        // Mock SoundsController singleton
        soundsControllerMock = mock(SoundsController.class);
        Field instanceField = SoundsController.class.getDeclaredField("soundsController");
        instanceField.setAccessible(true);
        instanceField.set(null, soundsControllerMock);

        // Mock the Game object
        gameMock = mock(Game.class);

        // Create controller instance
        customizeMenuController = new CustomizeMenuController(customizeMenuMock);
    }

    @Test
    void testConstructorPlaysCustomizeMusic() {
        // Verify that Customize BGM is played when the controller is initialized
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.CUSTOMIZEBGM);
    }

    @Test
    void testNavigateLeftWhenColorPaletteNotSelected() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);

        customizeMenuController.nextAction(gameMock, GUI.ACTION.LEFT, 0);

        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT);
        verify(customizeMenuMock, times(1)).navigateLeft();
    }

    @Test
    void testNavigateRightWhenColorPaletteNotSelected() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);

        customizeMenuController.nextAction(gameMock, GUI.ACTION.RIGHT, 0);

        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT);
        verify(customizeMenuMock, times(1)).navigateRight();
    }

    @Test
    void testNavigateUpWhenColorPaletteNotSelected() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);

        customizeMenuController.nextAction(gameMock, GUI.ACTION.UP, 0);

        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT);
        verify(customizeMenuMock, times(1)).navigateUp();
    }

    @Test
    void testNavigateDownWhenColorPaletteNotSelected() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);

        customizeMenuController.nextAction(gameMock, GUI.ACTION.DOWN, 0);

        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT);
        verify(customizeMenuMock, times(1)).navigateDown();
    }

    @Test
    void testEscActionWhenColorPaletteNotSelected() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);

        customizeMenuController.nextAction(gameMock, GUI.ACTION.ESC, 0);

        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.CUSTOMIZEBGM);
        verify(gameMock, times(1)).popState();
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.MENUBGM);
    }

    @Test
    void testSelectActionWhenColorPaletteNotSelected() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);
        when(customizeMenuMock.getSelectedColorChar()).thenReturn('A'); // Stub the return value

        // Perform the SELECT action
        customizeMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Verify interactions with mocks
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.ENTER);
        verify(customizeMenuMock, times(1)).getSelectedColorChar();
        verify(customizeMenuMock, times(1)).setColorPaletteSelected(true);

        // Assert that the selected color character was retrieved (optional verification)
        assertEquals('A', customizeMenuMock.getSelectedColorChar());
    }

    @Test
    void testLeftActionWhenColorPaletteSelected() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(true);

        customizeMenuController.nextAction(gameMock, GUI.ACTION.LEFT, 0);

        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT);
        verify(colorPaletteMenuMock, times(1)).navigateLeft();
    }

    @Test
    void testRightActionWhenColorPaletteSelected() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(true);

        customizeMenuController.nextAction(gameMock, GUI.ACTION.RIGHT, 0);

        verify(soundsControllerMock, times(1)).play(Sounds.SFX.SELECT);
        verify(colorPaletteMenuMock, times(1)).navigateRight();
    }

    @Test
    void testEscActionWhenColorPaletteSelected() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(true);

        customizeMenuController.nextAction(gameMock, GUI.ACTION.ESC, 0);

        verify(customizeMenuMock, times(1)).setColorPaletteSelected(false);
    }

    @Test
    void testSelectActionWhenColorPaletteSelected() throws IOException {
        // Step 1: Simulate when the palette is NOT selected
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false); // Initial state
        when(customizeMenuMock.getSelectedColorChar()).thenReturn('A'); // Old color is 'A'

        // Perform SELECT action to initialize oldColor
        customizeMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Verify oldColor is correctly set
        verify(customizeMenuMock, times(1)).getSelectedColorChar();
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(true); // Set palette to "selected"

        // Step 2: Simulate when the palette is SELECTED and a new color is chosen
        com.CrossingGuardJoe.viewer.Color newColor = mock(com.CrossingGuardJoe.viewer.Color.class);
        when(newColor.getCharacter()).thenReturn('B'); // New color is 'B'

        // Mock the ColorPaletteMenu behavior
        when(colorPaletteMenuMock.getSelectedColorIndex()).thenReturn(0); // Index of the new color
        when(colorPaletteMenuMock.getColorPalette()).thenReturn(List.of(newColor)); // Palette contains newColor

        // Perform SELECT action again to change the color
        customizeMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Step 3: Verify correct behavior
        verify(soundsControllerMock, times(2)).play(Sounds.SFX.ENTER); // Once for each SELECT action
        verify(customizeMenuMock, times(1)).setColorChange('A', 'B'); // Ensure the color change is invoked
        verify(customizeMenuMock, times(1)).setColorPaletteSelected(false); // Palette is deselected
    }

    @Test
    void testUnhandledActionWhenColorPaletteNotSelected() throws IOException {
        // Reset interactions caused by the constructor
        reset(soundsControllerMock);

        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);

        // Call the method to simulate unhandled action
        customizeMenuController.nextAction(gameMock, GUI.ACTION.NONE, 0);

        // Verify there are no interactions with soundsController after the reset
        verifyNoInteractions(soundsControllerMock);

        // Verify only the necessary interaction with customizeMenuMock
        verify(customizeMenuMock, times(1)).isColorPaletteSelected();
        verifyNoMoreInteractions(customizeMenuMock);
    }

    @Test
    void testUnhandledActionWhenColorPaletteSelected() throws IOException {
        // Reset interactions caused by the constructor
        reset(soundsControllerMock);

        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(true);

        // Call the method to simulate unhandled action
        customizeMenuController.nextAction(gameMock, GUI.ACTION.NONE, 0);

        // Verify the `isColorPaletteSelected` method was called
        verify(customizeMenuMock, times(1)).isColorPaletteSelected();

        // Ensure no further interactions occurred with customizeMenuMock
        verifyNoMoreInteractions(customizeMenuMock);

        // Ensure no interactions occurred with the soundsControllerMock after the reset
        verifyNoInteractions(soundsControllerMock);
    }



    /*-------------------- */

    @Test
    void testDefaultActionWhenColorPaletteNotSelected() throws IOException {
        // Reset interactions caused by the constructor
        reset(soundsControllerMock);

        // Arrange
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);

        // Act
        customizeMenuController.nextAction(gameMock, GUI.ACTION.NONE, 0); // Unhandled action

        // Assert
        verify(customizeMenuMock, times(1)).isColorPaletteSelected(); // Ensure palette selection check happened
        verifyNoMoreInteractions(customizeMenuMock); // No other interactions with the menu
        verifyNoInteractions(soundsControllerMock); // Ensure no interactions with sounds controller after reset
    }

    @Test
    void testDefaultActionWhenColorPaletteSelected() throws IOException {
        // Reset interactions caused by the constructor
        reset(soundsControllerMock);

        // Arrange
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(true);

        // Act
        customizeMenuController.nextAction(gameMock, GUI.ACTION.NONE, 0); // Unhandled action

        // Assert
        verify(customizeMenuMock, times(1)).isColorPaletteSelected(); // Ensure palette is checked
        verifyNoMoreInteractions(customizeMenuMock); // Ensure no additional interactions with customizeMenuMock
        verifyNoInteractions(soundsControllerMock); // Ensure no interactions with soundsControllerMock
        verifyNoInteractions(colorPaletteMenuMock); // Ensure no interactions with colorPaletteMenuMock
    }

    @Test
    void testNullAction() {
        // Reset the mock interactions from the constructor
        reset(soundsControllerMock, customizeMenuMock, colorPaletteMenuMock);

        // ACT: Call nextAction with null action
        assertThrows(NullPointerException.class, () -> customizeMenuController.nextAction(gameMock, null, 0));

        // ASSERT: Verify only the expected interactions occurred
        verify(customizeMenuMock, times(1)).isColorPaletteSelected(); // The menu's state is checked
        verifyNoInteractions(soundsControllerMock, colorPaletteMenuMock); // No other mocks should be used
    }

    @Test
    void testSetColorChangeWithOldColor() throws IOException {
        // Arrange: Simulate initial state
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);
        when(customizeMenuMock.getSelectedColorChar()).thenReturn('X'); // Old color is 'X'

        // Perform SELECT action to set the oldColor
        customizeMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Verify the oldColor is set
        verify(customizeMenuMock, times(1)).getSelectedColorChar();

        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(true); // Simulate palette is selected now

        // Arrange new color
        com.CrossingGuardJoe.viewer.Color mockNewColor = mock(com.CrossingGuardJoe.viewer.Color.class);
        when(mockNewColor.getCharacter()).thenReturn('Y'); // New color is 'Y'
        when(colorPaletteMenuMock.getSelectedColorIndex()).thenReturn(0);
        when(colorPaletteMenuMock.getColorPalette()).thenReturn(List.of(mockNewColor));

        // Act: Select the new color
        customizeMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Verify interactions and old-to-new color switch
        verify(soundsControllerMock, times(2)).play(Sounds.SFX.ENTER); // Sound played twice (for both SELECT actions)
        verify(customizeMenuMock, times(1)).setColorChange('X', 'Y'); // Old color to new color

        // Ensure the palette is deselected after selecting a new color
        verify(customizeMenuMock, times(1)).setColorPaletteSelected(false);
    }

    @Test
    void testSoundsForMultipleActions() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false); // Initial state

        // Perform multiple actions
        customizeMenuController.nextAction(gameMock, GUI.ACTION.LEFT, 0);
        customizeMenuController.nextAction(gameMock, GUI.ACTION.RIGHT, 0);
        customizeMenuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);

        // Verify total sound interactions
        verify(soundsControllerMock, times(2)).play(Sounds.SFX.SELECT); // LEFT and RIGHT actions
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.ENTER);  // SELECT action
    }

    @Test
    void testStopBackgroundMusicOnEscape() throws IOException {
        when(customizeMenuMock.isColorPaletteSelected()).thenReturn(false);

        // Act
        customizeMenuController.nextAction(gameMock, GUI.ACTION.ESC, 0);

        // Assert
        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.CUSTOMIZEBGM); // Background music stopped
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.MENUBGM); // Main menu music starts
        verify(gameMock, times(1)).popState(); // State is popped
    }

}
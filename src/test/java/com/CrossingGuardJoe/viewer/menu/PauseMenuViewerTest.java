package com.CrossingGuardJoe.viewer.menu;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.menu.Option;
import com.CrossingGuardJoe.model.menu.PauseMenu;
import com.CrossingGuardJoe.viewer.images.defined.ToolImages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class PauseMenuViewerTest {
    private PauseMenu pauseMenu;    // Mocked PauseMenu
    private GUI gui;               // Mocked GUI interface
    private PauseMenuViewer viewer; // The viewer under test

    @BeforeEach
    void setUp() {
        // Create mocks
        pauseMenu = mock(PauseMenu.class);
        gui = mock(GUI.class);

        // Initialize PauseMenuViewer with mocked PauseMenu
        viewer = new PauseMenuViewer(pauseMenu);
    }

    // Test for the drawTitle() method
    @Test
    void testDrawTitle() {
        // Act: Call drawTitle
        viewer.drawTitle(gui);

        // Assert: Verify the correct text is drawn at the correct position
        verify(gui, times(1))
                .drawText(new Position(207, 100), "Game paused", "#FFFFFF");
        verifyNoMoreInteractions(gui); // No additional GUI interactions
    }

    // Test for drawElements() with empty menu
    @Test
    void testDrawElements_EmptyMenu() {
        // Arrange: Simulate empty menu
        when(pauseMenu.getNumberOptions()).thenReturn(0);

        // Act: Call drawElements
        viewer.drawElements(gui);

        // Assert: Verify only title is drawn, no menu options
        verify(gui, times(1))
                .drawText(new Position(207, 100), "Game paused", "#FFFFFF");
        verifyNoMoreInteractions(gui); // No additional GUI interactions
    }

    // Test for drawElements() with a single selected option
    @Test
    void testDrawElements_SingleOptionSelected() {
        // Arrange: Menu with one option, which is selected
        Option option = new Option("Resume", new Position(200, 150), new String[] {});
        when(pauseMenu.getNumberOptions()).thenReturn(1);
        when(pauseMenu.getOption(0)).thenReturn(option);
        when(pauseMenu.isSelectedOption(0)).thenReturn(true);

        // Act: Call drawElements
        viewer.drawElements(gui);

        // Assert: Verify title and menu option interactions
        verify(gui, times(1))
                .drawText(new Position(207, 100), "Game paused", "#FFFFFF"); // Title
        verify(gui, times(1))
                .drawText(new Position(200, 150), "Resume", "#FFFFFF"); // Option
        verify(gui, times(1))
                .drawImage(new Position(185, 150), ToolImages.getArrowRightImage()); // Arrow
        verifyNoMoreInteractions(gui); // No additional GUI interactions
    }

    // Test for drawElements() with multiple options
    @Test
    void testDrawElements_MultipleOptions() {
        // Arrange: Menu with multiple options
        Option option1 = new Option("Resume", new Position(200, 150), new String[] {});
        Option option2 = new Option("Restart", new Position(200, 200), new String[] {});
        Option option3 = new Option("Quit", new Position(200, 250), new String[] {});
        when(pauseMenu.getNumberOptions()).thenReturn(3);
        when(pauseMenu.getOption(0)).thenReturn(option1);
        when(pauseMenu.getOption(1)).thenReturn(option2);
        when(pauseMenu.getOption(2)).thenReturn(option3);
        when(pauseMenu.isSelectedOption(0)).thenReturn(false);
        when(pauseMenu.isSelectedOption(1)).thenReturn(true); // 2nd option selected
        when(pauseMenu.isSelectedOption(2)).thenReturn(false);

        // Act: Call drawElements
        viewer.drawElements(gui);

        // Assert: Verify title and menu options
        verify(gui, times(1))
                .drawText(new Position(207, 100), "Game paused", "#FFFFFF"); // Title
        verify(gui, times(1))
                .drawText(new Position(200, 150), "Resume", "#FFFFFF"); // Option 1
        verify(gui, times(1))
                .drawText(new Position(200, 200), "Restart", "#FFFFFF"); // Option 2
        verify(gui, times(1))
                .drawText(new Position(200, 250), "Quit", "#FFFFFF"); // Option 3

        // Assert: Verify only 2nd option has an arrow
        verify(gui, times(1))
                .drawImage(new Position(185, 200), ToolImages.getArrowRightImage()); // Arrow for Option 2
        verify(gui, times(0))
                .drawImage(eq(new Position(185, 150)), any()); // No arrow for Option 1
        verify(gui, times(0))
                .drawImage(eq(new Position(185, 250)), any()); // No arrow for Option 3

        verifyNoMoreInteractions(gui); // No additional GUI interactions
    }

    // Test for drawElements() with boundary condition (last option selected)
    @Test
    void testDrawElements_LastOptionSelected() {
        // Arrange: Menu with two options, last option is selected
        Option option1 = new Option("Resume", new Position(200, 150), new String[] {});
        Option option2 = new Option("Quit", new Position(200, 200), new String[] {});
        when(pauseMenu.getNumberOptions()).thenReturn(2);
        when(pauseMenu.getOption(0)).thenReturn(option1);
        when(pauseMenu.getOption(1)).thenReturn(option2);
        when(pauseMenu.isSelectedOption(0)).thenReturn(false);
        when(pauseMenu.isSelectedOption(1)).thenReturn(true); // Last option selected

        // Act: Call drawElements
        viewer.drawElements(gui);

        // Assert: Verify the title is drawn (this happens inside drawElements())
        verify(gui, times(1))
                .drawText(new Position(207, 100), "Game paused", "#FFFFFF"); // Title

        // Assert: Verify BOTH menu options are drawn correctly
        verify(gui, times(1))
                .drawText(new Position(200, 150), "Resume", "#FFFFFF"); // Option 1
        verify(gui, times(1))
                .drawText(new Position(200, 200), "Quit", "#FFFFFF"); // Option 2

        // Assert: Verify the arrow is ONLY drawn for the last option
        verify(gui, times(1))
                .drawImage(new Position(185, 200), ToolImages.getArrowRightImage()); // Arrow for Option 2 (selected)

        // Assert: No arrow for the first option
        verify(gui, times(0))
                .drawImage(eq(new Position(185, 150)), any()); // No arrow for Option 1

        // Ensure no unexpected interactions
        verifyNoMoreInteractions(gui);
    }

    // Test for drawInformation() (no operation)
    @Test
    void testDrawInformation() {
        // Act: Call drawInformation
        viewer.drawInformation(gui);

        // Assert: Verify no interaction with GUI
        verifyNoInteractions(gui);
    }
}
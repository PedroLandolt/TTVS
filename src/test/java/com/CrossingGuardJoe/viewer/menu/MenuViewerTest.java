package com.CrossingGuardJoe.viewer.menu;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.menu.Menu;
import com.CrossingGuardJoe.model.menu.Option;
import com.CrossingGuardJoe.viewer.images.defined.LogoImages;
import com.CrossingGuardJoe.viewer.images.defined.ToolImages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MenuViewerTest {
    private Menu menu;            // Mocked menu model
    private GUI gui;              // Mocked GUI
    private MenuViewer viewer;    // Viewer under test

    @BeforeEach
    void setUp() {
        // Create mocks
        menu = mock(Menu.class);
        gui = mock(GUI.class);

        // Initialize the viewer
        viewer = new MenuViewer(menu);
    }

    @Test
    void testDrawTitle() {
        // Act: Call `drawTitle`
        viewer.drawTitle(gui);

        // Assert: Verify the logo is drawn at the correct position
        verify(gui, times(1)).drawImage(new Position(130, 50), LogoImages.getLogoGameImage());
        verifyNoMoreInteractions(gui); // Ensure no unexpected GUI interactions
    }

    @Test
    void testDrawElements_EmptyMenu() {
        // Arrange: Simulate an empty menu
        when(menu.getNumberOptions()).thenReturn(0);

        // Act: Call `drawElements`
        viewer.drawElements(gui);

        // Assert: Verify no interactions since there are no options
        verify(gui, times(1)).drawImage(new Position(130, 50), LogoImages.getLogoGameImage()); // Title is drawn
        verifyNoMoreInteractions(gui);
    }

    @Test
    void testDrawElements_WithOptions() {
        // Arrange: Simulate a menu with two options
        Option option1 = new Option("Start Game", new Position(200, 150), new String[] {"OptionDetails1"});
        Option option2 = new Option("Exit", new Position(200, 200), new String[] {"OptionDetails2"});
        when(menu.getNumberOptions()).thenReturn(2);
        when(menu.getOption(0)).thenReturn(option1);
        when(menu.getOption(1)).thenReturn(option2);
        when(menu.isSelectedOption(0)).thenReturn(true);
        when(menu.isSelectedOption(1)).thenReturn(false);

        // Act: Call `drawElements`
        viewer.drawElements(gui);

        // Assert: Verify the title is drawn
        verify(gui, times(1)).drawImage(new Position(130, 50), LogoImages.getLogoGameImage());

        // Assert: Verify both options are drawn with correct text and positions
        verify(gui, times(1)).drawText(new Position(200, 150), "Start Game", "#FFFFFF");
        verify(gui, times(1)).drawText(new Position(200, 200), "Exit", "#FFFFFF");

        // Assert: Verify the arrow is drawn for the selected option
        verify(gui, times(1)).drawImage(new Position(185, 150), ToolImages.getArrowRightImage());

        // Assert: Verify no arrow is drawn for the unselected option
        verify(gui, times(0)).drawImage(eq(new Position(185, 200)), any());

        verifyNoMoreInteractions(gui); // Ensure no unexpected interactions occur
    }

    @Test
    void testDrawElements_SingleOptionSelected() {
        // Arrange: Simulate a menu with one option, which is selected
        Option option = new Option("Start", new Position(400, 300), new String[] {"OptionDetails"});
        when(menu.getNumberOptions()).thenReturn(1);
        when(menu.getOption(0)).thenReturn(option);
        when(menu.isSelectedOption(0)).thenReturn(true);

        // Act: Call `drawElements`
        viewer.drawElements(gui);

        // Assert: Verify the title is drawn
        verify(gui, times(1)).drawImage(new Position(130, 50), LogoImages.getLogoGameImage());

        // Assert: Verify the option is drawn with the arrow
        verify(gui, times(1)).drawText(new Position(400, 300), "Start", "#FFFFFF");
        verify(gui, times(1)).drawImage(new Position(385, 300), ToolImages.getArrowRightImage());

        verifyNoMoreInteractions(gui); // Ensure no unexpected interactions occur
    }

    @Test
    void testDrawInformation_NoOperation() {
        // Act: Call `drawInformation`
        viewer.drawInformation(gui);

        // Assert: Verify no interaction with the GUI
        verifyNoInteractions(gui);
    }
}
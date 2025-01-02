package com.CrossingGuardJoe.viewer.menu;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.menu.StatsMenu;
import com.CrossingGuardJoe.viewer.images.defined.ToolImages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StatsMenuViewerTest {
    private StatsMenu statsMenu;   // Mocked StatsMenu
    private GUI gui;               // Mocked GUI
    private StatsMenuViewer viewer; // StatsMenuViewer under test

    @BeforeEach
    void setUp() {
        // Create mocks
        statsMenu = mock(StatsMenu.class);
        gui = mock(GUI.class);

        // Initialize StatsMenuViewer with mocked statsMenu
        viewer = new StatsMenuViewer(statsMenu);
    }

    // Test constructor
    @Test
    void testConstructor() {
        // Verify the viewer is initialized with the model
        StatsMenuViewer testViewer = new StatsMenuViewer(statsMenu);
        assert testViewer.getModel() == statsMenu; // Direct model getter verification
    }

    // Test for drawTitle() method
    @Test
    void testDrawTitle() {
        // Act: Call drawTitle
        viewer.drawTitle(gui);

        // Assert: Verify the title is drawn correctly
        verify(gui, times(1))
                .drawText(new Position(207, 100), "Game stats", "#FFFFFF");
        verifyNoMoreInteractions(gui); // Ensure no other GUI interactions
    }

    // Test for drawInformation() with normal stats values
    @Test
    void testDrawInformation_NormalValues() {
        // Arrange: Mock model data
        when(statsMenu.getCurrentScore()).thenReturn(1500);
        when(statsMenu.getCurrentLevel()).thenReturn(5);
        when(statsMenu.getHighestScore()).thenReturn(5000);
        when(statsMenu.getHighestLevel()).thenReturn(8);

        // Act: Call drawInformation
        viewer.drawInformation(gui);

        // Assert: Verify the stat texts are drawn correctly
        verify(gui, times(1))
                .drawText(new Position(210, 210), "score", "#FFFFFF");
        verify(gui, times(1))
                .drawText(new Position(210, 250), "level", "#FFFFFF");
        verify(gui, times(1))
                .drawText(new Position(150, 300), "highest score", "#FFFFFF");
        verify(gui, times(1))
                .drawText(new Position(150, 340), "highest level", "#FFFFFF");

        // Assert: Verify the actual stats values are drawn properly
        verify(gui, times(1))
                .drawText(new Position(275, 210), 1500, "#FFFFFF"); // currentScore
        verify(gui, times(1))
                .drawText(new Position(275, 250), 5, "#FFFFFF"); // currentLevel
        verify(gui, times(1))
                .drawText(new Position(275, 300), 5000, "#FFFFFF"); // highestScore
        verify(gui, times(1))
                .drawText(new Position(275, 340), 8, "#FFFFFF"); // highestLevel

        // Assert: Verify the escape key image
        verify(gui, times(1))
                .drawImage(new Position(4, 4), ToolImages.getKeyEscImage());

        verifyNoMoreInteractions(gui); // Ensure no extra interactions
    }

    // Test for drawInformation() with boundary conditions (level == 10)
    @Test
    void testDrawInformation_BoundaryValues_Level10() {
        // Arrange: Mock model data
        when(statsMenu.getCurrentScore()).thenReturn(3500);
        when(statsMenu.getCurrentLevel()).thenReturn(10);
        when(statsMenu.getHighestScore()).thenReturn(9000);
        when(statsMenu.getHighestLevel()).thenReturn(10);

        // Act: Call drawInformation
        viewer.drawInformation(gui);

        // Assert: Verify the stat texts are drawn correctly
        verify(gui, times(1))
                .drawText(new Position(210, 210), "score", "#FFFFFF");
        verify(gui, times(1))
                .drawText(new Position(210, 250), "level", "#FFFFFF");
        verify(gui, times(1))
                .drawText(new Position(150, 300), "highest score", "#FFFFFF");
        verify(gui, times(1))
                .drawText(new Position(150, 340), "highest level", "#FFFFFF");

        // Assert: Verify the boundary conditions for levels
        verify(gui, times(1))
                .drawText(new Position(275, 210), 3500, "#FFFFFF"); // currentScore
        verify(gui, times(1))
                .drawText(new Position(275, 250), 9, "#FFFFFF"); // currentLevel (adjusted)
        verify(gui, times(1))
                .drawText(new Position(275, 300), 9000, "#FFFFFF"); // highestScore
        verify(gui, times(1))
                .drawText(new Position(275, 340), 9, "#FFFFFF"); // highestLevel (adjusted)

        // Assert: Verify the escape key image
        verify(gui, times(1))
                .drawImage(new Position(4, 4), ToolImages.getKeyEscImage());

        verifyNoMoreInteractions(gui); // Ensure no extra interactions
    }

    // Test for drawElements() to verify both title and information are invoked
    @Test
    void testDrawElements() {
        // Arrange
        when(statsMenu.getCurrentScore()).thenReturn(1500);
        when(statsMenu.getCurrentLevel()).thenReturn(5);
        when(statsMenu.getHighestScore()).thenReturn(5000);
        when(statsMenu.getHighestLevel()).thenReturn(8);

        // Act
        viewer.drawElements(gui);

        // Assert: Verify drawTitle was called
        verify(gui, times(1))
                .drawText(new Position(207, 100), "Game stats", "#FFFFFF");

        // Assert: Verify drawInformation is working as expected
        verify(gui, times(1))
                .drawText(new Position(210, 210), "score", "#FFFFFF");
        verify(gui, times(1))
                .drawText(new Position(210, 250), "level", "#FFFFFF");
        verify(gui, times(1))
                .drawText(new Position(150, 300), "highest score", "#FFFFFF");
        verify(gui, times(1))
                .drawText(new Position(150, 340), "highest level", "#FFFFFF");

        verify(gui, times(1))
                .drawText(new Position(275, 210), 1500, "#FFFFFF"); // currentScore
        verify(gui, times(1))
                .drawText(new Position(275, 250), 5, "#FFFFFF");   // currentLevel
        verify(gui, times(1))
                .drawText(new Position(275, 300), 5000, "#FFFFFF"); // highestScore
        verify(gui, times(1))
                .drawText(new Position(275, 340), 8, "#FFFFFF");   // highestLevel

        // Assert: Verify the escape key image
        verify(gui, times(1))
                .drawImage(new Position(4, 4), ToolImages.getKeyEscImage());

        // Ensure no unwanted interactions occurred
        verifyNoMoreInteractions(gui);
    }
}
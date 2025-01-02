package com.CrossingGuardJoe.viewer.menu;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.model.menu.InstructionsMenu;
import com.CrossingGuardJoe.viewer.images.defined.*;
import com.CrossingGuardJoe.viewer.images.generator.Shape;
import com.CrossingGuardJoe.viewer.menu.InstructionsMenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InstructionsMenuViewerTest {
    private InstructionsMenu instructionsMenu; // Mocked menu model
    private GUI gui;                          // Mocked GUI interface
    private InstructionsMenuViewer viewer;    // The viewer under test

    @BeforeEach
    void setUp() {
        // Create mocks
        instructionsMenu = mock(InstructionsMenu.class);
        gui = mock(GUI.class);

        // Initialize the viewer using the mocked model
        viewer = new InstructionsMenuViewer(instructionsMenu);
    }

    @Test
    void testDrawTitle() {
        // Act
        viewer.drawTitle(gui);

        // Assert: Verify the title is drawn at the correct position and with the correct attributes
        verify(gui, times(1)).drawText(new Position(202, 17), "Instructions", "#FFFFFF");
    }

    @Test
    void testDrawInformation_CurrentPageNotLast() {
        // Arrange: Set up a mocked current and total page count
        when(instructionsMenu.getCurrentPage()).thenReturn(2);
        when(instructionsMenu.getTotalPages()).thenReturn(5);

        // Act
        viewer.drawInformation(gui);

        // Assert: Verify GUI interactions
        verify(gui, times(2)).drawImage(new Position(4, 4), ToolImages.getKeyEscImage()); // Verify 2 invocations
        verify(gui, times(1)).drawText(new Position(400, 474), "Page", "#FFFFFF");
        verify(gui, times(1)).drawText(new Position(454, 474), "of", "#FFFFFF");
        verify(gui, times(1)).drawText(new Position(474, 474), 5, "#FFFFFF");
        verify(gui, times(1)).drawText(new Position(440, 474), 2, "#FFFFFF"); // Current page text color: #FFFFFF
    }

    @Test
    void testDrawInformation_CurrentPageIsLast() {
        // Arrange: Set up the current page equal to the total pages
        when(instructionsMenu.getCurrentPage()).thenReturn(5);
        when(instructionsMenu.getTotalPages()).thenReturn(5);

        // Act
        viewer.drawInformation(gui);

        // Assert: Verify the last page uses a different text color
        verify(gui, times(1)).drawText(new Position(440, 474), 5, "#D30000"); // Page text color for last page: #D30000
    }

    @Test
    void testDrawElements_PageOne() {
        // Arrange: Set the current page to 1
        when(instructionsMenu.getCurrentPage()).thenReturn(1);

        // Act
        viewer.drawElements(gui);

        // Assert: Verify `drawPageOne` is called
        verify(gui, times(1)).drawText(new Position(50, 100), "you are Joe", "#FFFFFF");
        verify(gui, times(1)).drawText(new Position(70, 120), "a crossing guard", "#FFFFFF");
        verify(gui, times(1)).drawText(new Position(50, 400), "click once to move", "#FFFFFF");
        verify(gui, times(1)).drawImage(new Position(150, 190 ), JoeImages.getJoeStandImage());
        verify(gui, times(1)).drawImage(new Position(230, 190), JoeImages.getJoeWalkleftImage());
        verify(gui, times(1)).drawImage(new Position(310, 190), JoeImages.getJoeWalkrightImage());

    }

    @Test
    void testDrawElements_PageTwo() {
        // Arrange: Set the current page to 2
        when(instructionsMenu.getCurrentPage()).thenReturn(2);

        // Act
        viewer.drawElements(gui);

        // Assert: Verify `drawPageTwo` is called
        verify(gui, times(1)).drawText(new Position(50, 100), "you will help", "#FFFFFF");
        verify(gui, times(1)).drawImage(new Position(170, 180), JoeImages.getJoeStopImage());
    }

    @Test
    void testDrawElements_PageThree() {
        // Arrange: Set the current page to 3
        when(instructionsMenu.getCurrentPage()).thenReturn(3);

        // Act
        viewer.drawElements(gui);

        // Assert: Verify `drawPageThree` is called
        verify(gui, times(1)).drawText(new Position(50, 100), "be careful", "#FFFFFF");
        verify(gui, times(1)).drawImage(new Position(150, 195), CarImage.getCarImage());
    }

    @Test
    void testDrawElements_PageFour() {
        // Arrange: Set the current page to 4
        when(instructionsMenu.getCurrentPage()).thenReturn(4);

        // Act
        viewer.drawElements(gui);

        // Assert: Verify `drawPageFour` is called
        verify(gui, times(1)).drawText(new Position(50, 100), "if you lose a kid", "#FFFFFF");
        verify(gui, times(1)).drawText(new Position(70, 120), "you lose hp", "#FFFFFF");
    }

    @Test
    void testDrawElements_PageFive() {
        // Arrange: Set the current page to 5
        when(instructionsMenu.getCurrentPage()).thenReturn(5);

        // Act
        viewer.drawElements(gui);

        // Assert: Verify `drawPageFive` is called
        verify(gui, times(1)).drawText(new Position(50, 100), "try to get", "#FFFFFF");
    }
}
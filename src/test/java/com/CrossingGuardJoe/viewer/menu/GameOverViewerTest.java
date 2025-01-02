package com.CrossingGuardJoe.viewer.menu;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.menu.GameOverMenu;
import com.CrossingGuardJoe.model.game.Road;               // Correct import for Road
import com.CrossingGuardJoe.model.game.elements.Joe;       // Correct import for Joe
import com.CrossingGuardJoe.viewer.images.defined.ToolImages;
import com.CrossingGuardJoe.model.menu.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

class GameOverViewerTest {

    private GameOverViewer gameOverViewer;

    @Mock
    private GameOverMenu gameOverMenu;

    @Mock
    private GUI gui;

    @Mock
    private Road road; // Mocked Road object

    @Mock
    private Joe joe; // Mocked Joe object

    @BeforeEach
    void setUp() {
        // Mock necessary objects
        gameOverMenu = mock(GameOverMenu.class);
        gui = mock(GUI.class);
        road = mock(Road.class);
        joe = mock(Joe.class);

        // Ensure GameOverMenu.getCurrentGame() returns a mocked Road
        when(gameOverMenu.getCurrentGame()).thenReturn(road);

        // Ensure Road.getJoe() returns a mocked Joe
        when(road.getJoe()).thenReturn(joe);

        // Mock default behavior for Joe's score and Road's level
        when(joe.getScore()).thenReturn(300); // Default score
        when(road.getCurrentLevel()).thenReturn(5); // Default level

        gameOverViewer = new GameOverViewer(gameOverMenu);
    }
    @Test
    void testDrawTitle_Win() {
        when(gameOverMenu.isWin()).thenReturn(true);

        gameOverViewer.drawTitle(gui);

        verify(gui).drawText(new Position(187, 80), "Congratulations", "#FFFFFF");
    }

    @Test
    void testDrawTitle_Lose() {
        when(gameOverMenu.isWin()).thenReturn(false);

        gameOverViewer.drawTitle(gui);

        verify(gui).drawText(new Position(213, 80), "game over", "#FFFFFF");
    }

    @Test
    void testDrawInformation_Level10() {
        // Arrange
        when(gameOverMenu.getCurrentGame()).thenReturn(road); // Return mocked Road object
        when(road.getJoe()).thenReturn(joe); // Mocked Joe object
        when(joe.getScore()).thenReturn(500); // Mock score
        when(road.getCurrentLevel()).thenReturn(10); // Mock game level

        // Act
        gameOverViewer.drawInformation(gui);

        // Assert
        verify(gui).drawImage(any(Position.class), any());
        verify(gui).drawText(new Position(210, 165), "score", "#FFFFFF");
        verify(gui).drawText(new Position(210, 195), "level", "#FFFFFF");
        verify(gui).drawText(new Position(275, 165), 500, "#FFFFFF");
        verify(gui).drawText(new Position(275, 195), 9, "#D30000");
    }

    @Test
    void testDrawInformation_LevelNot10() {
        // Arrange
        when(gameOverMenu.getCurrentGame()).thenReturn(road); // Mock the getCurrentGame method
        when(road.getJoe()).thenReturn(joe); // Mock Road.getJoe to return mocked Joe object
        when(joe.getScore()).thenReturn(300); // Mock the score returned by Joe
        when(road.getCurrentLevel()).thenReturn(5); // Mock the current game level returned by Road

        // Act
        gameOverViewer.drawInformation(gui);

        // Assert
        // Verify that the drawText and drawImage calls are happening with the correct arguments
        verify(gui).drawImage(any(Position.class), any()); // Check for the drawing of an image (generic verification)
        verify(gui).drawText(new Position(210, 165), "score", "#FFFFFF"); // Check for score label
        verify(gui).drawText(new Position(210, 195), "level", "#FFFFFF"); // Check for level label
        verify(gui).drawText(new Position(275, 165), 300, "#FFFFFF"); // Check for score value
        verify(gui).drawText(new Position(275, 195), 5, "#FFFFFF"); // Check for level value
    }

    /*@Test
    void testDrawElements_NoOptions() {
        // Mock no options
        when(gameOverMenu.getNumberOptions()).thenReturn(0);

        // Execute method
        gameOverViewer.drawElements(gui);

        // Verify: Ensure no menu options are drawn
        verify(gameOverMenu, times(1)).getNumberOptions(); // Confirm we check the number of options
        verify(gameOverMenu, never()).getOption(anyInt()); // No options should be fetched
        verify(gameOverMenu, never()).isSelectedOption(anyInt()); // No options should be selected

        // Verify no GUI menu-related interaction (no option text/images drawn)
        verify(gui, never()).drawText(any(Position.class), eq("Option1"), anyString());
        verify(gui, never()).drawImage(any(Position.class), eq(ToolImages.getArrowRightImage())); // No menu-related images

        // **Allow GUI interactions for drawInformation and drawTitle**
        // These interactions are unrelated to menu options and should not cause test failures
        verify(gui, atLeastOnce()).drawImage(any(Position.class), any()); // Allow drawInformation images
        verify(gui, atLeastOnce()).drawText(any(Position.class), anyString(), anyString()); // Allow drawTitle text

        // End verification
        verifyNoMoreInteractions(gameOverMenu); // Ensure no extra, unrelated interactions occur
    }*/

    /*@Test
    void testDrawElements_SingleOption_NotSelected() {
        // Mock a single option
        Option option = new Option("Option1", new Position(100, 100), new String[]{"Image1"});
        when(gameOverMenu.getNumberOptions()).thenReturn(1);
        when(gameOverMenu.getOption(0)).thenReturn(option);
        when(gameOverMenu.isSelectedOption(0)).thenReturn(false);

        // Execute method
        gameOverViewer.drawElements(gui);

        // Assert: Text is drawn, but the arrow image is NOT drawn
        verify(gui).drawText(new Position(100, 100), "Option1", "#FFFFFF");
        verify(gui, never()).drawImage(any(Position.class), any());
    }
*/
    @Test
    void testDrawElements_SingleOption_Selected() {
        // Mock a single menu option
        Option option = new Option("Option1", new Position(100, 100), new String[]{"Option1Image"});
        when(gameOverMenu.getNumberOptions()).thenReturn(1);
        when(gameOverMenu.getOption(0)).thenReturn(option);
        when(gameOverMenu.isSelectedOption(0)).thenReturn(true);

        // Execute
        gameOverViewer.drawElements(gui);

        // Assert: Both text and the arrow image are drawn
        verify(gui).drawText(new Position(100, 100), "Option1", "#FFFFFF");
        verify(gui).drawImage(new Position(85, 100), ToolImages.getArrowRightImage()); // Arrow position adjusted by -15
    }

    @Test
    void testDrawElements_MultipleOptions_FirstSelected() {
        // Mock multiple menu options
        Option option1 = new Option("Option1", new Position(100, 100), new String[]{"Image1"});
        Option option2 = new Option("Option2", new Position(200, 200), new String[]{"Image2"});
        when(gameOverMenu.getNumberOptions()).thenReturn(2);
        when(gameOverMenu.getOption(0)).thenReturn(option1);
        when(gameOverMenu.getOption(1)).thenReturn(option2);
        when(gameOverMenu.isSelectedOption(0)).thenReturn(true);
        when(gameOverMenu.isSelectedOption(1)).thenReturn(false);

        // Execute
        gameOverViewer.drawElements(gui);

        // Assert: Correct text and images for both options
        verify(gui).drawText(new Position(100, 100), "Option1", "#FFFFFF");
        verify(gui).drawImage(new Position(85, 100), ToolImages.getArrowRightImage()); // Arrow for first option
        verify(gui).drawText(new Position(200, 200), "Option2", "#FFFFFF");
        verify(gui, never()).drawImage(new Position(185, 200), ToolImages.getArrowRightImage()); // No arrow for second option
    }

    @Test
    void testDrawElements_MultipleOptions_LastSelected() {
        // Mock multiple menu options
        Option option1 = new Option("Option1", new Position(100, 100), new String[]{"Image1"});
        Option option2 = new Option("Option2", new Position(200, 200), new String[]{"Image2"});
        when(gameOverMenu.getNumberOptions()).thenReturn(2);
        when(gameOverMenu.getOption(0)).thenReturn(option1);
        when(gameOverMenu.getOption(1)).thenReturn(option2);
        when(gameOverMenu.isSelectedOption(0)).thenReturn(false);
        when(gameOverMenu.isSelectedOption(1)).thenReturn(true);

        // Execute
        gameOverViewer.drawElements(gui);

        // Assert: Correct text and images for both options
        verify(gui).drawText(new Position(100, 100), "Option1", "#FFFFFF");
        verify(gui, never()).drawImage(new Position(85, 100), ToolImages.getArrowRightImage()); // No arrow for first option
        verify(gui).drawText(new Position(200, 200), "Option2", "#FFFFFF");
        verify(gui).drawImage(new Position(185, 200), ToolImages.getArrowRightImage()); // Arrow for the selected second option
    }
}
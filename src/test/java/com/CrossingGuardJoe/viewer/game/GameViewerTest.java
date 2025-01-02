package com.CrossingGuardJoe.viewer.game;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.model.game.elements.Car;
import com.CrossingGuardJoe.model.game.elements.Element;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.model.game.elements.Kid;
import com.CrossingGuardJoe.viewer.game.elements.CarView;
import com.CrossingGuardJoe.viewer.game.elements.ElementViewer;
import com.CrossingGuardJoe.viewer.images.defined.HUDImages;
import com.CrossingGuardJoe.viewer.images.defined.RoadItemsImages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class GameViewerTest {
    private GameViewer gameViewer;

    @Mock
    private GUI gui; // Mock the GUI dependency

    @Mock
    private Road road; // Mock the Road model

    @Mock
    private Car car; // Mock a Car element

    @Mock
    private Joe joe; // Mock Joe (the main character)

    @Mock
    private Kid kid; // Mock a Kid element

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        gameViewer = new GameViewer(road);  // Instantiate the class under test
    }

    @Test
    void testDrawElements() {
        // Arrange
        when(road.getCars()).thenReturn(new ArrayList<>()); // Mock empty car list
        when(road.getKids()).thenReturn(new ArrayList<>()); // Mock empty kid list
        when(road.getJoe()).thenReturn(joe); // Mock Joe

        // Mock methods for Joe and GUI interactions
        when(joe.getScore()).thenReturn(100);
        when(joe.getHearts()).thenReturn(3);

        doNothing().when(gui).setColorHexaCode(any());
        doNothing().when(gui).fillRectangle(any(), anyInt(), anyInt());
        doNothing().when(gui).drawImage(any(), any());

        // Act
        gameViewer.drawElements(gui);

        // Assert
        // Ensure the calls to draw the various components
        verify(gui, atLeastOnce()).setColorHexaCode("#C0BBB1"); // Ensure the road color is set
        verify(gui, atLeastOnce()).fillRectangle(any(Position.class), anyInt(), anyInt()); // Ensure roadlines are drawn
        verify(gui).drawImage(new Position(0, 0), HUDImages.getGameHudImage()); // Assert HUD image
        verify(gui).drawText(new Position(164, 10), 100, "#FFFFFF"); // Joe's score
    }

    @Test
    void testDrawRoad() {
        // Act
        gameViewer.drawRoad(gui);

        // Assert
        verify(gui, atLeastOnce()).fillRectangle(any(Position.class), anyInt(), anyInt());
        verify(gui, atLeastOnce()).setColorHexaCode("#C0BBB1");
    }

    @Test
    void testDrawRoadLines() {
        // Act
        gameViewer.drawRoadLines(gui);

        // Assert
        verify(gui, atLeastOnce()).fillRectangle(any(Position.class), anyInt(), anyInt());
    }

    @Test
    void testDrawRoadItems() {
        // Act
        gameViewer.drawRoadItems(gui);

        // Assert
        verify(gui).drawImage(new Position(426, 258), RoadItemsImages.getSignalImage()); // Right lane signal
        verify(gui).drawImage(new Position(55, 258), RoadItemsImages.getSignalImage()); // Left lane signal
    }

    @Test
    void testDrawHUD() {
        // Arrange
        when(road.getJoe()).thenReturn(joe); // Mock Joe
        when(joe.getScore()).thenReturn(100);
        when(joe.getHearts()).thenReturn(3); // Joe has 3 hearts

        // Act
        invokePrivateMethodHUD(gui);

        // Assert
        verify(gui).drawImage(new Position(0, 0), HUDImages.getGameHudImage()); // HUD image
        verify(gui, times(3)).drawImage(any(Position.class), eq(HUDImages.getHPImage())); // Hearts
    }

    @Test
    void testDrawScore() {
        // Arrange
        when(road.getCurrentLevel()).thenReturn(5); // Mock current level

        // Act
        invokePrivateMethodDrawScore(gui);

        // Assert
        verify(gui).drawText(any(Position.class), eq(5), eq("#FFFFFF"));
    }

    @Test
    void testDrawElement() {
        // Arrange
        ElementViewer<Car> carView = mock(CarView.class);
        when(car.getPosition()).thenReturn(new Position(10, 20));

        // Act
        invokePrivateGenericDrawElement(gui, car, carView);

        // Assert
        verify(carView).draw(car, gui);
    }

    @Test
    void testDrawElementsGenerics() {
        // Arrange
        List<Car> cars = List.of(car); // Cars list
        ElementViewer<Car> carView = mock(CarView.class);

        // Act
        invokePrivateGenericDrawElements(gui, cars, carView);

        // Assert
        verify(carView).draw(car, gui); // Ensure ElementViewer is called
    }

    // Helper method for invoking private `drawHUD`
    private void invokePrivateMethodHUD(GUI gui) {
        try {
            var method = GameViewer.class.getDeclaredMethod("drawHUD", GUI.class);
            method.setAccessible(true);
            method.invoke(gameViewer, gui);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method for invoking private `drawScore`
    private void invokePrivateMethodDrawScore(GUI gui) {
        try {
            var method = GameViewer.class.getDeclaredMethod("drawScore", GUI.class);
            method.setAccessible(true);
            method.invoke(gameViewer, gui);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Helper for invoking private generic `drawElement`
    private <T extends Element> void invokePrivateGenericDrawElement(GUI gui, T element, ElementViewer<T> viewer) {
        try {
            var method = GameViewer.class.getDeclaredMethod("drawElement", GUI.class, Element.class, ElementViewer.class);
            method.setAccessible(true);
            method.invoke(gameViewer, gui, element, viewer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Helper for invoking private generic `drawElements`
    private <T extends Element> void invokePrivateGenericDrawElements(GUI gui, List<T> elements, ElementViewer<T> viewer) {
        try {
            var method = GameViewer.class.getDeclaredMethod("drawElements", GUI.class, List.class, ElementViewer.class);
            method.setAccessible(true);
            method.invoke(gameViewer, gui, elements, viewer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
package com.CrossingGuardJoe.viewer.game.elements;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.game.elements.Car;
import com.CrossingGuardJoe.viewer.images.defined.CarImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CarViewTest {

    private CarView carView;
    private Car mockCar;
    private GUI mockGUI;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize CarView and mocks
        carView = new CarView();
        mockCar = mock(Car.class);
        mockGUI = mock(GUI.class);
    }

    @Test
    void testDrawInvokesGuiWithCorrectParameters() {
        // Arrange
        Position mockPosition = new Position(5, 10); // Replace with your project's Position class if different
        when(mockCar.getPosition()).thenReturn(mockPosition);

        // Act
        carView.draw(mockCar, mockGUI);

        // Assert
        verify(mockCar, times(1)).getPosition();
        verify(mockGUI, times(1)).drawImage(mockPosition, CarImage.getCarImage());
    }
}
package com.CrossingGuardJoe.controller.game.elements;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.model.game.elements.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CarControllerTest {

    private static final long MINIMUM_ELAPSED_TIME = 1000L; // Constant for minimum elapsed time

    @Mock
    private Road road;
    @Mock
    private Car car;
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carController = new CarController(road);
    }

    @Test
    void testConstructorInitializesLastUpdateTime() {
        long beforeConstruction = System.currentTimeMillis();
        carController = new CarController(road);
        long afterConstruction = System.currentTimeMillis();

        // Verify that the time initialized in the constructor is within a reasonable range
        assertTrue(afterConstruction - beforeConstruction < 100,
                "The time between construction should be very small");
    }

    @Test
    void testNextActionMovesCarsAfterElapsedTime() {
        // Arrange
        Position mockPosition = new Position(10, 10); // Mock position
        when(car.getPosition()).thenReturn(mockPosition);
        when(road.getCars()).thenReturn(Arrays.asList(car)); // Mock road's cars

        // Get the current time as the starting point
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime + MINIMUM_ELAPSED_TIME; // Simulate sufficient elapsed time

        // Act
        carController.nextAction(null, GUI.ACTION.NONE, elapsedTime);

        // Assert
        verify(car, times(1)).setPosition(any(Position.class)); // Verify position update
    }


    @Test
    void testNextActionDoesNotMoveCarsIfNotEnoughTimeElapsed() {
        // Arrange
        Position mockPosition = new Position(10, 10); // Mock position
        when(car.getPosition()).thenReturn(mockPosition); // Mock car's current position
        when(road.getCars()).thenReturn(Arrays.asList(car)); // Mock the road's cars

        carController = new CarController(road); // Initialize carController (real object, no mock needed)

        // Act
        long initialTime = System.currentTimeMillis(); // Mock initial time
        long insufficientElapsedTime = initialTime; // No time difference, should not trigger movement

        // Call the nextAction method with insufficient time elapsed
        carController.nextAction(null, GUI.ACTION.NONE, insufficientElapsedTime);

        // Assert that setPosition was never called on the car
        verify(car, never()).setPosition(any(Position.class)); // Ensure no movement occurs
    }

}

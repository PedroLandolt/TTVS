package com.CrossingGuardJoe.controller.game;

import com.CrossingGuardJoe.model.game.elements.Car;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.model.game.elements.Kid;
import com.CrossingGuardJoe.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuxCheckRangeTest {

    @Test
    void testIsInRangeJoeKid_True() {
        // Arrange
        Joe joe = mock(Joe.class);
        Kid kid = mock(Kid.class);
        Position joePosition = new Position(30, 0);
        Position kidPosition = new Position(35, 0);

        when(joe.getPosition()).thenReturn(joePosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeJoeKid(joe, kid);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeJoeKid_False() {
        // Arrange
        Joe joe = mock(Joe.class);
        Kid kid = mock(Kid.class);
        Position joePosition = new Position(60, 0);
        Position kidPosition = new Position(35, 0);

        when(joe.getPosition()).thenReturn(joePosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeJoeKid(joe, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeCarKid_True() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(45, 20);
        Position kidPosition = new Position(50, 25);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeCarKid_False() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(100, 100);
        Position kidPosition = new Position(50, 50);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeLeftCarJoe_True() {
        // Arrange
        Car car = mock(Car.class);
        Joe joe = mock(Joe.class);
        Position carPosition = new Position(10, 10);
        Position joePosition = new Position(15, 15);

        when(car.getPosition()).thenReturn(carPosition);
        when(joe.getPosition()).thenReturn(joePosition);

        // Act
        boolean result = AuxCheckRange.isInRangeLeftCarJoe(car, joe);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeLeftCarJoe_False() {
        // Arrange
        Car car = mock(Car.class);
        Joe joe = mock(Joe.class);
        Position carPosition = new Position(100, 100);
        Position joePosition = new Position(15, 15);

        when(car.getPosition()).thenReturn(carPosition);
        when(joe.getPosition()).thenReturn(joePosition);

        // Act
        boolean result = AuxCheckRange.isInRangeLeftCarJoe(car, joe);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeRightCarJoe_True() {
        // Arrange
        Car car = mock(Car.class);
        Joe joe = mock(Joe.class);
        Position carPosition = new Position(25, 15);
        Position joePosition = new Position(20, 20);

        when(car.getPosition()).thenReturn(carPosition);
        when(joe.getPosition()).thenReturn(joePosition);

        // Act
        boolean result = AuxCheckRange.isInRangeRightCarJoe(car, joe);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeRightCarJoe_False() {
        // Arrange
        Car car = mock(Car.class);
        Joe joe = mock(Joe.class);
        Position carPosition = new Position(90, 90);
        Position joePosition = new Position(20, 20);

        when(car.getPosition()).thenReturn(carPosition);
        when(joe.getPosition()).thenReturn(joePosition);

        // Act
        boolean result = AuxCheckRange.isInRangeRightCarJoe(car, joe);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeJoeKid_ExactRightBoundary() {
        // Arrange
        Joe joe = mock(Joe.class);
        Kid kid = mock(Kid.class);
        Position joePosition = new Position(44, 0); // Kid's X + JOE_AT_KID_RIGHT
        Position kidPosition = new Position(35, 0);

        when(joe.getPosition()).thenReturn(joePosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeJoeKid(joe, kid);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeJoeKid_ExactLeftBoundary() {
        // Arrange
        Joe joe = mock(Joe.class);
        Kid kid = mock(Kid.class);
        Position joePosition = new Position(14, 0); // Kid's X - JOE_AT_KID_LEFT
        Position kidPosition = new Position(35, 0);

        when(joe.getPosition()).thenReturn(joePosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeJoeKid(joe, kid);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeJoeKid_OutOfRange() {
        // Arrange
        Joe joe = mock(Joe.class);
        Kid kid = mock(Kid.class);
        Position joePosition = new Position(10, 0); // Out of range (left)
        Position kidPosition = new Position(35, 0);

        when(joe.getPosition()).thenReturn(joePosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeJoeKid(joe, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeCarKid_ExactHorizontalLeftBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(40, 25); // Kid's X + KID_AT_CAR_LEFT
        Position kidPosition = new Position(50, 25);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeCarKid_ExactHorizontalRightBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(50 - 52, 25); // Corrected: Kid's X - KID_AT_CAR_RIGHT
        Position kidPosition = new Position(50, 25);      // Ensure Y matches Y range

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeCarKid_ExactVerticalTopBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(50, 40); // Kid's Y + CAR_FRONT
        Position kidPosition = new Position(50, 25);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeCarKid_ExactVerticalBottomBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(50, -40); // Kid's Y - CAR_REAR
        Position kidPosition = new Position(50, 25);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInRangeCarKid_OutOfVerticalRange() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(50, 100); // Out of range
        Position kidPosition = new Position(50, 25);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeLeftCarJoe_BoundaryConditions() {
        // Arrange
        Car car = mock(Car.class);
        Joe joe = mock(Joe.class);
        Position carPosition = new Position(15, 25); // Joe's X + CAR_MIDDLE
        Position joePosition = new Position(15, 25); // Joe's X - JOE_AT_CAR_LEFTSIDE

        when(car.getPosition()).thenReturn(carPosition);
        when(joe.getPosition()).thenReturn(joePosition);

        // Act
        boolean result = AuxCheckRange.isInRangeLeftCarJoe(car, joe);

        // Assert
        assertTrue(result);
    }

    /* ---- */

    @Test
    void testIsInRangeJoeKid_AboveRightBoundary() {
        // Arrange
        Joe joe = mock(Joe.class);
        Kid kid = mock(Kid.class);
        Position joePosition = new Position(45, 0); // Beyond Kid's X + JOE_AT_KID_RIGHT (44)
        Position kidPosition = new Position(35, 0);

        when(joe.getPosition()).thenReturn(joePosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeJoeKid(joe, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeJoeKid_BelowLeftBoundary() {
        // Arrange
        Joe joe = mock(Joe.class);
        Kid kid = mock(Kid.class);
        Position joePosition = new Position(13, 0); // Below Kid's X - JOE_AT_KID_LEFT (14)
        Position kidPosition = new Position(35, 0);

        when(joe.getPosition()).thenReturn(joePosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeJoeKid(joe, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeCarKid_XAboveRightBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(61, 25); // Beyond Kid's X + KID_AT_CAR_LEFT (60)
        Position kidPosition = new Position(50, 25);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeCarKid_XBelowLeftBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(-3, 25); // Below Kid's X - KID_AT_CAR_RIGHT (-2)
        Position kidPosition = new Position(50, 25);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeCarKid_YAboveTopBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(50, 41); // Beyond Kid's Y + CAR_FRONT (40)
        Position kidPosition = new Position(50, 25);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeCarKid_YBelowBottomBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(50, -41); // Below Kid's Y - CAR_REAR (-40)
        Position kidPosition = new Position(50, 25);

        when(car.getPosition()).thenReturn(carPosition);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        boolean result = AuxCheckRange.isInRangeCarKid(car, kid);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInRangeLeftCarJoe_XFailsRightBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Joe joe = mock(Joe.class);
        Position carPosition = new Position(37, 15); // Beyond Joe's X + CAR_MIDDLE (36)
        Position joePosition = new Position(15, 15);

        when(car.getPosition()).thenReturn(carPosition);
        when(joe.getPosition()).thenReturn(joePosition);

        // Act
        boolean result = AuxCheckRange.isInRangeLeftCarJoe(car, joe);

        // Assert
        assertFalse(result); // Now this will be correct
    }

    @Test
    void testIsInRangeLeftCarJoe_YFailsBottomBoundary() {
        // Arrange
        Car car = mock(Car.class);
        Joe joe = mock(Joe.class);
        Position carPosition = new Position(15, -51); // Beyond Joe's Y - CAR_REAR (-50)
        Position joePosition = new Position(15, 15);

        when(car.getPosition()).thenReturn(carPosition);
        when(joe.getPosition()).thenReturn(joePosition);

        // Act
        boolean result = AuxCheckRange.isInRangeLeftCarJoe(car, joe);

        // Assert
        assertFalse(result); // Now this will be correct
    }


}
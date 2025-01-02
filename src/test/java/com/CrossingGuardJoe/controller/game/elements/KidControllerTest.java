package com.CrossingGuardJoe.controller.game.elements;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import java.util.*;

import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.game.elements.Kid;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.model.game.elements.Car;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.controller.game.AuxCheckRange;

class KidControllerTest {

    private Road road;
    private KidController kidController;
    private List<Kid> kids;
    private Joe joe;

    @BeforeEach
    void setUp() {
        road = mock(Road.class);
        kids = new ArrayList<>();
        joe = mock(Joe.class);
        when(road.getKids()).thenReturn(kids);
        when(road.getJoe()).thenReturn(joe);
        kidController = new KidController(road);

        // Assign a valid position to Joe
        Position joePosition = new Position(200, 100);
        when(joe.getPosition()).thenReturn(joePosition);
    }

    @Test
    void testMoveKid() {
        Kid kid = mock(Kid.class);
        Position startPosition = new Position(100, 50);
        when(kid.getPosition()).thenReturn(startPosition);

        kidController.moveKid(kid);

        ArgumentCaptor<Position> positionCaptor = ArgumentCaptor.forClass(Position.class);
        verify(kid).setPosition(positionCaptor.capture());
        Position movedPosition = positionCaptor.getValue();
        assertEquals(97, movedPosition.getX(), "Kid should move left by 3 units.");
        assertEquals(50, movedPosition.getY(), "Kid's Y position should remain unchanged.");
    }

    @Test
    void testMoveKidAfterHit() {
        Car car = mock(Car.class);
        Kid kid = mock(Kid.class);
        Position carPosition = new Position(50, 100);
        when(car.getPosition()).thenReturn(carPosition);

        kidController.moveKidAfterHit(car, kid, 70);

        ArgumentCaptor<Position> positionCaptor = ArgumentCaptor.forClass(Position.class);
        verify(kid).setPosition(positionCaptor.capture());
        Position newKidPosition = positionCaptor.getValue();
        assertEquals(70, newKidPosition.getX(), "Kid's X position should match hit position.");
        assertEquals(155, newKidPosition.getY(), "Kid's Y position should be adjusted correctly after hit.");
    }

    @Test
    void testStopKid() {
        Kid kid = mock(Kid.class);
        Position startPosition = new Position(100, 50);
        when(kid.getPosition()).thenReturn(startPosition);

        kidController.stopKid(kid);

        verify(kid).setNotWalking();
        verify(kid).getPosition();
    }

    @Test
    void testIsFirstKid() {
        Kid kid1 = mock(Kid.class);
        Kid kid2 = mock(Kid.class);
        kids.add(kid1);
        kids.add(kid2);

        assertTrue(kidController.isFirstKid(kid1), "Kid1 should be the first kid.");
        assertFalse(kidController.isFirstKid(kid2), "Kid2 should not be the first kid.");
    }

    @Test
    void testInMinDistance() {
        Kid kid1 = mock(Kid.class);
        Kid kid2 = mock(Kid.class);
        Position position1 = new Position(50, 100);
        Position position2 = new Position(59, 100);

        when(kid1.getPosition()).thenReturn(position1);
        when(kid2.getPosition()).thenReturn(position2);
        when(kid2.getIsHit()).thenReturn(false);
        kids.add(kid1);
        kids.add(kid2);

        assertTrue(kidController.inMinDistance(kid2), "Kid2 should be in minimum distance from Kid1.");
        assertFalse(kidController.inMinDistance(kid1), "Kid1 has no kid in front to measure distance.");
    }

    @Test
    void testCanContinueWalk() {
        Kid kid1 = mock(Kid.class);
        Kid kid2 = mock(Kid.class);
        Position position1 = new Position(50, 100);
        Position position2 = new Position(59, 100);

        when(kid1.getPosition()).thenReturn(position1);
        when(kid2.getPosition()).thenReturn(position2);
        when(kid2.getIsHit()).thenReturn(false);
        kids.add(kid1);
        kids.add(kid2);

        assertFalse(kidController.canContinueWalk(kid2), "Kid2 should stop as it is too close to Kid1.");
        assertTrue(kidController.canContinueWalk(kid1), "Kid1 should continue walking as there is no kid in front.");
    }

    @Test
    void testRepositionQueue() {
        Kid kid1 = mock(Kid.class);
        Kid kid2 = mock(Kid.class);
        Position position1 = new Position(50, 100);
        Position position2 = new Position(70, 100);

        when(kid1.getPosition()).thenReturn(position1);
        when(kid2.getPosition()).thenReturn(position2);
        when(kid1.getMovesInQueueLeft()).thenReturn(2);
        when(kid2.getMovesInQueueLeft()).thenReturn(0);
        kids.add(kid1);
        kids.add(kid2);

        kidController.repositionQueue();

        verify(kid1).addMovesInQueueLeft(-1);
        verify(kid2).setNotWalking();
    }

    @Test
    void testNextLevelNumberKids() {
        assertEquals(4, kidController.nextLevelNumberKids(2), "Level 2 should have 4 kids.");
        assertEquals(12, kidController.nextLevelNumberKids(1), "Default case should return 12 kids.");
    }

    @Test
    void testCheckPoints() {
        Kid kid = mock(Kid.class);
        Position position = new Position(-1, 100);
        when(kid.getPosition()).thenReturn(position);
        when(kid.getPass()).thenReturn(false);
        kids.add(kid);

        kidController.checkPoints();

        verify(kid).setPass();
        verify(joe).addScore(anyInt());
    }

    @Test
    void testCollisionsIndirectly() {
        Kid kid = mock(Kid.class);
        Car car = mock(Car.class);
        Position carPosition = new Position(50, 100);
        Position kidPosition = new Position(50, 100);

        try (MockedStatic<AuxCheckRange> mockedStatic = mockStatic(AuxCheckRange.class)) {
            mockedStatic.when(() -> AuxCheckRange.isInRangeCarKid(car, kid)).thenReturn(true);

            when(car.getPosition()).thenReturn(carPosition);
            when(kid.getPosition()).thenReturn(kidPosition);
            when(kid.getDeathCounted()).thenReturn(false);
            kids.add(kid);

            // Ensure the car is added to the road model
            List<Car> cars = new ArrayList<>();
            cars.add(car);
            when(road.getCars()).thenReturn(cars);

            // Call nextAction to trigger the collision check
            kidController.nextAction(null, GUI.ACTION.DOWN, System.currentTimeMillis());

            verify(kid).isHit();
            verify(kid, times(2)).setNotWalking(); // Adjusted to expect 2 invocations
            verify(kid).setDead();
            verify(joe).removeHeart();
        }
    }

    @Test
    void testNextAction() {
        Kid kid = mock(Kid.class);
        Position kidPosition = new Position(100, 100);

        try (MockedStatic<AuxCheckRange> mockedStatic = mockStatic(AuxCheckRange.class)) {
            mockedStatic.when(() -> AuxCheckRange.isInRangeJoeKid(joe, kid)).thenReturn(true);

            when(kid.getPosition()).thenReturn(kidPosition);
            when(kid.getIsHit()).thenReturn(false);
            kids.add(kid);

            kidController.nextAction(null, GUI.ACTION.DOWN, System.currentTimeMillis());

            verify(kid).setSelected(); // Ensure kid is selected
        }
    }
}

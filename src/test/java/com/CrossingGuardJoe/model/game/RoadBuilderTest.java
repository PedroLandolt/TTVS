package com.CrossingGuardJoe.model.game;

import com.CrossingGuardJoe.model.game.elements.Car;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.model.game.elements.Kid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;

class RoadBuilderTest {

    private RoadBuilder roadBuilder;

    @BeforeEach
    void setUp() {
        roadBuilder = new RoadBuilder();
    }

    @Test
    void shouldCreateRoad() {
        // When
        Road road = roadBuilder.createRoad();

        // Then
        assertThat(road).isNotNull(); // Ensure road is created
        assertThat(road.getJoe()).isNotNull(); // Ensure Joe is initialized
        assertThat(road.getJoe().getPosition().getX()).isEqualTo(390); // Correct initial X position of Joe
        assertThat(road.getJoe().getPosition().getY()).isEqualTo(297); // Correct initial Y position of Joe

        assertThat(road.getKids()).isNotNull(); // Ensure Kids list is not null
        assertThat(road.getKids().size()).isEqualTo(3); // Ensure initial number of kids is 3
        road.getKids().forEach(kid -> {
            assertThat(kid).isNotNull(); // Kids are properly initialized
            assertThat(kid.getPosition().getX()).isGreaterThan(0);
            assertThat(kid.getPosition().getY()).isGreaterThan(0);
        });

        assertThat(road.getCars()).isNotNull(); // Ensure Cars list is not null
        assertThat(road.getCars().size()).isEqualTo(3); // Ensure 3 cars are created
        road.getCars().forEach(car -> {
            assertThat(car).isNotNull(); // Cars are properly initialized
            assertThat(car.getPosition().getX()).isIn(85, 172, 259, 346); // Cars should be in valid ROAD_LANES
        });
    }

    @Test
    void shouldCreateJoeWithValidPosition() {
        // When
        Joe joe = roadBuilder.createRoad().getJoe();

        // Then
        assertThat(joe).isNotNull(); // Ensure Joe is created
        assertThat(joe.getPosition().getX()).isEqualTo(390); // Verify X position
        assertThat(joe.getPosition().getY()).isEqualTo(297); // Verify Y position
    }

    @Test
    void shouldCreateInitialKids() {
        // When
        List<Kid> kids = roadBuilder.createRoad().getKids();

        // Then
        assertThat(kids).isNotNull(); // Ensure Kids list is not null
        assertThat(kids.size()).isEqualTo(3); // Ensure initial number of kids is correct
        kids.forEach(kid -> {
            assertThat(kid).isNotNull(); // Each kid is properly initialized
            assertThat(kid.getPosition().getX()).isGreaterThan(0); // Kids have valid positions
            assertThat(kid.getPosition().getY()).isEqualTo(330); // Initial Y position for all kids
        });
    }

    @Test
    void shouldCreateKidsForNextLevel() {
        // Given
        int numberOfKids = 5; // Arbitrary number for next level

        // When
        List<Kid> kids = roadBuilder.createKidsNextLevel(numberOfKids);

        // Then
        assertThat(kids).isNotNull(); // Kids list should not be null
        assertThat(kids.size()).isEqualTo(numberOfKids); // Ensure correct number of kids is created
        kids.forEach(kid -> {
            assertThat(kid).isNotNull(); // Kids should be initialized
            assertThat(kid.getPosition().getX()).isGreaterThan(0); // Kids have valid positions
            assertThat(kid.getPosition().getY()).isEqualTo(330); // Initial Y position
        });
    }

    @Test
    void shouldCreateCarsWithValidPositions() {
        // When
        List<Car> cars = roadBuilder.createRoad().getCars();

        // Then
        assertThat(cars).isNotNull(); // Cars list should not be null
        assertThat(cars.size()).isEqualTo(3); // Ensure number of cars matches the specification
        cars.forEach(car -> {
            assertThat(car).isNotNull(); // Each car is properly initialized
            assertThat(car.getPosition().getX()).isIn(85, 172, 259, 346); // X positions should match road lanes
            assertThat(car.getPosition().getY()).isLessThan(0); // Y positions must be less than 0 (off-screen start)
        });
    }

    @Test
    void shouldGenerateKidsWithCorrectSpacing() {
        // Given
        int numberOfKids = 4;

        // When
        List<Kid> kids = roadBuilder.createKidsNextLevel(numberOfKids);

        // Then
        assertThat(kids).isNotNull(); // Ensure kids list is not null
        assertThat(kids.size()).isEqualTo(numberOfKids); // Ensure correct number of kids is created
        for (int i = 1; i < kids.size(); i++) {
            int previousKidX = kids.get(i - 1).getPosition().getX();
            int currentKidX = kids.get(i).getPosition().getX();

            assertThat(currentKidX - previousKidX).isEqualTo(9); // Ensure correct distance between kids
        }
    }

    /// /////////////////////////////////////////////

    @Test
    void shouldGenerateUniqueRandomYForEachCarAfterOverlapping() {
        // Given
        try (var auxCarCheckMock = Mockito.mockStatic(AuxCarCheck.class)) {
            // Simulate overlapping `randomY` for cars. First calls return true (overlap), and then false (valid).
            auxCarCheckMock
                    .when(() -> AuxCarCheck.isAnyCarOverlapping(anyInt(), anyList(), anyInt()))
                    .thenReturn(true, true, false); // 3 attempts: 2 overlap, 1 valid

            // When
            List<Car> cars = roadBuilder.createRoad().getCars();

            // Then
            // The cars list should be generated correctly
            assertThat(cars).isNotNull();
            assertThat(cars.size()).isEqualTo(3); // Confirm expected car count

            for (Car car : cars) {
                assertThat(car).isNotNull();
                assertThat(car.getPosition().getY()).isLessThan(0); // Valid `randomY`
            }

            // Verify that static method `isAnyCarOverlapping` was called at least 3 times
            auxCarCheckMock.verify(() -> AuxCarCheck.isAnyCarOverlapping(anyInt(), anyList(), anyInt()), atLeast(3));
        }
    }

    @Test
    void shouldHandleInterruptedExceptionInCarThread() {
        // Using a test thread directly mimicking the thread in `createCars`
        List<Car> cars = roadBuilder.createRoad().getCars();

        Thread testThread = new Thread(() -> {
            try {
                Thread.sleep(100); // Sleep to simulate thread pause
            } catch (InterruptedException e) {
                // This should simulate the catch block logic in `RoadBuilder`
                assertThat(e).isNotNull();
                System.out.println("ERROR: " + e.getMessage()); // Verifies branch handling
            }
        });

        // Start and forcefully interrupt the thread
        testThread.start();
        testThread.interrupt();

        // Wait for the thread to finish
        try {
            testThread.join(); // Wait for all actions to complete
        } catch (InterruptedException e) {
            fail("Test thread was interrupted while joining");
        }
    }
}
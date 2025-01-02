package com.CrossingGuardJoe.model.game;

import com.CrossingGuardJoe.model.game.elements.Car;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.model.game.elements.Kid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoadTest {

    private Road road;

    @BeforeEach
    void setUp() {
        road = new Road();
    }

    @Test
    void shouldInitializeWithDefaultValues() {
        // Then
        assertThat(road.getJoe()).isNull();
        assertThat(road.getKids()).isNull();
        assertThat(road.getCars()).isNull();
        assertThat(road.getCurrentLevel()).isEqualTo(1);
        assertThat(road.isGameEnded()).isFalse();
    }

    @Test
    void shouldSetAndGetJoe() {
        // Given
        Joe joe = mock(Joe.class);

        // When
        road.setJoe(joe);

        // Then
        assertThat(road.getJoe()).isEqualTo(joe);
    }

    @Test
    void shouldSetAndGetKids() {
        // Given
        Kid kid1 = mock(Kid.class);
        Kid kid2 = mock(Kid.class);
        List<Kid> kids = List.of(kid1, kid2);

        // When
        road.setKids(kids);

        // Then
        assertThat(road.getKids()).isEqualTo(kids);
    }

    @Test
    void shouldSetAndGetCars() {
        // Given
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        List<Car> cars = List.of(car1, car2);

        // When
        road.setCars(cars);

        // Then
        assertThat(road.getCars()).isEqualTo(cars);
    }

    @Test
    void shouldLevelUp() {
        // Given
        int initialLevel = road.getCurrentLevel();

        // When
        road.levelUp();

        // Then
        assertThat(road.getCurrentLevel()).isEqualTo(initialLevel + 1);
        assertThat(road.isGameEnded()).isFalse();
    }

    @Test
    void shouldReachFinalLevelAndEndGame() {
        // Given
        for (int i = 1; i < 9; i++) {
            road.levelUp(); // Increment the level to 9 (after the loop ends, currentLevel = 9)
        }

        // Assert current state after loop
        assertThat(road.getCurrentLevel()).isEqualTo(9); // Correctly assert the current level
        assertThat(road.isGameEnded()).isFalse(); // Game should still not be ended

        // When
        road.levelUp(); // Increment to level 10, triggering game end

        // Then
        assertThat(road.getCurrentLevel()).isEqualTo(10); // Level should now be 10
        assertThat(road.isGameEnded()).isTrue(); // Game should now be marked as ended
    }

    @Test
    void shouldNotLevelUpAfterGameEnds() {
        // Given
        for (int i = 1; i <= 10; i++) {
            road.levelUp(); // Bring road to level 10
        }

        assertThat(road.getCurrentLevel()).isEqualTo(10);
        assertThat(road.isGameEnded()).isTrue();

        // When
        road.levelUp(); // Attempt to level up again

        // Then
        assertThat(road.getCurrentLevel()).isEqualTo(10); // Level should remain at 10
        assertThat(road.isGameEnded()).isTrue();
    }

    @Test
    void shouldSetKidsNextLevel() {
        // Given
        int numberOfKids = 3; // Expected number of kids
        road = new Road(); // Use the real Road instance

        // When
        road.setKidsNextLevel(numberOfKids); // Call setKidsNextLevel

        // Then
        assertThat(road.getKids()).isNotNull(); // Ensure kids list is not null
        assertThat(road.getKids().size()).isEqualTo(numberOfKids); // Verify the correct number of kids are added

        // Ensure all kids are instances of Kid and have valid positions
        road.getKids().forEach(kid -> {
            assertThat(kid).isNotNull(); // Each kid object should not be null
            assertThat(kid).isInstanceOf(Kid.class); // Ensure each object in the list is a Kid
            assertThat(kid.getPosition().getX()).isGreaterThan(0); // Validate X position property
            assertThat(kid.getPosition().getY()).isGreaterThan(0); // Validate Y position property
        });
    }
}
package com.CrossingGuardJoe.model.game;

import com.CrossingGuardJoe.model.game.elements.Car;
import com.CrossingGuardJoe.model.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuxCarCheckTest {

    @Test
    void shouldReturnTrueWhenThereIsAnOverlappingCar() {
        // Given
        int newY = 50;
        int minDistance = 20;

        Car car1 = mock(Car.class);
        when(car1.getPosition()).thenReturn(new Position(0, 40)); // 50 - 40 < 20 = overlapping

        Car car2 = mock(Car.class);
        when(car2.getPosition()).thenReturn(new Position(0, 70)); // Not overlapping

        List<Car> cars = List.of(car1, car2);

        // When
        boolean result = AuxCarCheck.isAnyCarOverlapping(newY, cars, minDistance);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNoCarIsOverlapping() {
        // Given
        int newY = 50;
        int minDistance = 20;

        Car car1 = mock(Car.class);
        when(car1.getPosition()).thenReturn(new Position(0, 30)); // 50 - 30 = 20, not overlapping

        Car car2 = mock(Car.class);
        when(car2.getPosition()).thenReturn(new Position(0, 80)); // 50 - 80 > 20, not overlapping

        List<Car> cars = List.of(car1, car2);

        // When
        boolean result = AuxCarCheck.isAnyCarOverlapping(newY, cars, minDistance);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnFalseWhenCarListIsEmpty() {
        // Given
        int newY = 50;
        int minDistance = 20;
        List<Car> cars = List.of(); // Empty list of cars

        // When
        boolean result = AuxCarCheck.isAnyCarOverlapping(newY, cars, minDistance);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenSingleCarOverlaps() {
        // Given
        int newY = 50;
        int minDistance = 15;

        Car car = mock(Car.class);
        when(car.getPosition()).thenReturn(new Position(0, 45)); // 50 - 45 < 15 = overlapping

        List<Car> cars = List.of(car);

        // When
        boolean result = AuxCarCheck.isAnyCarOverlapping(newY, cars, minDistance);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenSingleCarDoesNotOverlap() {
        // Given
        int newY = 50;
        int minDistance = 15;

        Car car = mock(Car.class);
        when(car.getPosition()).thenReturn(new Position(0, 30)); // 50 - 30 = 20, not overlapping

        List<Car> cars = List.of(car);

        // When
        boolean result = AuxCarCheck.isAnyCarOverlapping(newY, cars, minDistance);

        // Then
        assertThat(result).isFalse();
    }
}
package com.CrossingGuardJoe.model.game.elements;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class CarTest {

    @Test
    void shouldInitializePositionCorrectly() {
        // Given
        int x = 10;
        int y = 20;

        // When
        Car car = new Car(x, y);

        // Then
        assertThat(car.getPosition()).isNotNull();
        assertThat(car.getPosition().getX()).isEqualTo(x);
        assertThat(car.getPosition().getY()).isEqualTo(y);
    }

    @Test
    void shouldAllowNegativeCoordinatesInPosition() {
        // Given
        int x = -15;
        int y = -25;

        // When
        Car car = new Car(x, y);

        // Then
        assertThat(car.getPosition().getX()).isEqualTo(x);
        assertThat(car.getPosition().getY()).isEqualTo(y);
    }

    @Test
    void shouldHandleZeroCoordinates() {
        // Given
        int x = 0;
        int y = 0;

        // When
        Car car = new Car(x, y);

        // Then
        assertThat(car.getPosition().getX()).isZero();
        assertThat(car.getPosition().getY()).isZero();
    }

    @Test
    void shouldHandleInfinityCoordinates() {
        // Given
        int x = Integer.MAX_VALUE;
        int y = Integer.MIN_VALUE;

        // When
        Car car = new Car(x, y);

        // Then
        assertThat(car.getPosition().getX()).isEqualTo(x);
        assertThat(car.getPosition().getY()).isEqualTo(y);
    }
}
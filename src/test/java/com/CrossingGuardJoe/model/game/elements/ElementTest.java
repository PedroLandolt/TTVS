package com.CrossingGuardJoe.model.game.elements;

import com.CrossingGuardJoe.model.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ElementTest {

    @Test
    void shouldInitializePositionCorrectly() {
        // Given
        int x = 10;
        int y = 15;

        // When
        Element element = new Element(x, y);

        // Then
        assertThat(element.getPosition()).isNotNull();
        assertThat(element.getPosition().getX()).isEqualTo(x);
        assertThat(element.getPosition().getY()).isEqualTo(y);
    }

    @Test
    void shouldAllowPositionUpdate() {
        // Given
        Element element = new Element(10, 15);
        Position newPosition = new Position(20, 25);

        // When
        element.setPosition(newPosition);

        // Then
        assertThat(element.getPosition()).isEqualTo(newPosition);
        assertThat(element.getPosition().getX()).isEqualTo(20);
        assertThat(element.getPosition().getY()).isEqualTo(25);
    }

    @Test
    void shouldHandleNegativeCoordinates() {
        // Given
        Element element = new Element(-5, -10);

        // Then
        assertThat(element.getPosition().getX()).isEqualTo(-5);
        assertThat(element.getPosition().getY()).isEqualTo(-10);
    }

    @Test
    void shouldAllowUpdatingPositionToNegativeCoordinates() {
        // Given
        Element element = new Element(0, 0);
        Position negativePosition = new Position(-10, -20);

        // When
        element.setPosition(negativePosition);

        // Then
        assertThat(element.getPosition().getX()).isEqualTo(-10);
        assertThat(element.getPosition().getY()).isEqualTo(-20);
    }

    @Test
    void shouldHandleZeroCoordinates() {
        // Given
        Element element = new Element(0, 0);

        // Then
        assertThat(element.getPosition().getX()).isZero();
        assertThat(element.getPosition().getY()).isZero();
    }

    @Test
    void shouldReturnSamePositionObject() {
        // Given
        Element element = new Element(5, 10);

        // When
        Position position = element.getPosition();

        // Then
        assertThat(position).isNotNull();
        assertThat(position.getX()).isEqualTo(5);
        assertThat(position.getY()).isEqualTo(10);
    }

    @Test
    void shouldRetainCorrectPositionAfterMultipleUpdates() {
        // Given
        Element element = new Element(10, 15);

        // When
        Position position1 = new Position(20, 25);
        Position position2 = new Position(-30, -35);

        element.setPosition(position1);
        assertThat(element.getPosition()).isEqualTo(position1);

        element.setPosition(position2);
        assertThat(element.getPosition()).isEqualTo(position2);

        // Then
        assertThat(position1).isNotEqualTo(position2);
    }
}
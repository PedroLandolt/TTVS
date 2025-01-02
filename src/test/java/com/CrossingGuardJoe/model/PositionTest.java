package com.CrossingGuardJoe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    private Position position;

    @BeforeEach
    void setUp() {
        // Initialize the Position instance with test data
        position = new Position(5, 10);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        // Verify the constructor initializes x and y correctly
        assertThat(position.getX()).isEqualTo(5);
        assertThat(position.getY()).isEqualTo(10);
    }

    @Test
    void testSetXChangesXCoordinate() {
        // Modify x and verify its value changes
        position.setX(15);
        assertThat(position.getX()).isEqualTo(15);
    }

    @Test
    void testSetYChangesYCoordinate() {
        // Modify y and verify its value changes
        position.setY(20);
        assertThat(position.getY()).isEqualTo(20);
    }

    @Test
    void testEqualsReturnsTrueForEquivalentObjects() {
        // Create another Position with the same values as the first one
        Position anotherPosition = new Position(5, 10);

        // Verify that equals() returns true when x and y are the same
        assertThat(position).isEqualTo(anotherPosition);
    }

    @Test
    void testEqualsReturnsTrueForItself() {
        // Verify that equals() returns true when comparing the object with itself
        assertThat(position.equals(position)).isTrue();
    }

    @Test
    void testEqualsReturnsFalseForDifferentXValues() {
        // Create a Position with a different x value
        Position differentXPosition = new Position(100, 10);

        // Verify that equals() returns false
        assertThat(position).isNotEqualTo(differentXPosition);
    }

    @Test
    void testEqualsReturnsFalseForDifferentYValues() {
        // Create a Position with a different y value
        Position differentYPosition = new Position(5, 20);

        // Verify that equals() returns false
        assertThat(position).isNotEqualTo(differentYPosition);
    }

    @Test
    void testEqualsReturnsFalseForDifferentObjects() {
        // Verify that equals() returns false when comparing to an object of a different type
        String notAPosition = "not a position";
        assertThat(position).isNotEqualTo(notAPosition);
    }

    @Test
    void testEqualsReturnsFalseForNull() {
        // Verify that equals() returns false when comparing to null
        assertThat(position).isNotEqualTo(null);
    }

    @Test
    void testHashCodeIsConsistentForEqualObjects() {
        // Create another Position with the same values
        Position anotherPosition = new Position(5, 10);

        // Verify that hashCode() is the same for equivalent objects
        assertThat(position.hashCode()).isEqualTo(anotherPosition.hashCode());
    }

    @Test
    void testHashCodeDiffersForDifferentXValues() {
        // Create a Position with a different x value
        Position differentXPosition = new Position(100, 10);

        // Verify that hashCode() differs for different x values
        assertThat(position.hashCode()).isNotEqualTo(differentXPosition.hashCode());
    }

    @Test
    void testHashCodeDiffersForDifferentYValues() {
        // Create a Position with a different y value
        Position differentYPosition = new Position(5, 20);

        // Verify that hashCode() differs for different y values
        assertThat(position.hashCode()).isNotEqualTo(differentYPosition.hashCode());
    }
}
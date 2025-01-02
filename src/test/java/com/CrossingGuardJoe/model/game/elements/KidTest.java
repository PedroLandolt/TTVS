package com.CrossingGuardJoe.model.game.elements;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KidTest {

    @Test
    void shouldInitializeCorrectly() {
        // Given
        int x = 5;
        int y = 10;

        // When
        Kid kid = new Kid(x, y);

        // Then
        assertThat(kid.getPosition().getX()).isEqualTo(x);
        assertThat(kid.getPosition().getY()).isEqualTo(y);
        assertThat(kid.getWalkingState()).isFalse();
        assertThat(kid.getIsHit()).isFalse();
        assertThat(kid.isSelected()).isFalse();
        assertThat(kid.isFirstHalfOfMovement()).isTrue();
        assertThat(kid.getDeathCounted()).isFalse();
        assertThat(kid.getPass()).isFalse();
        assertThat(kid.getCounted()).isFalse();
        assertThat(kid.getMovesInQueueLeft()).isZero();
        assertThat(kid.getPoints()).isEqualTo(100);
    }

    @Test
    void shouldSetWalkingStateTrue() {
        // Given
        Kid kid = new Kid(0, 0);

        // When
        kid.setWalking();

        // Then
        assertThat(kid.getWalkingState()).isTrue();
    }

    @Test
    void shouldSetWalkingStateFalse() {
        // Given
        Kid kid = new Kid(0, 0);
        kid.setWalking(); // Make the kid walk first

        // When
        kid.setNotWalking();

        // Then
        assertThat(kid.getWalkingState()).isFalse();
        assertThat(kid.isFirstHalfOfMovement()).isTrue();
    }

    @Test
    void shouldSetFirstHalfOfMovementTrue() {
        // Given
        Kid kid = new Kid(0, 0);

        // When
        kid.setFirstHalfOfMovement(true);

        // Then
        assertThat(kid.isFirstHalfOfMovement()).isTrue();
    }

    @Test
    void shouldSetFirstHalfOfMovementFalse() {
        // Given
        Kid kid = new Kid(0, 0);

        // When
        kid.setFirstHalfOfMovement(false);

        // Then
        assertThat(kid.isFirstHalfOfMovement()).isFalse();
    }

    @Test
    void shouldBeSelected() {
        // Given
        Kid kid = new Kid(0, 0);

        // When
        kid.setSelected();

        // Then
        assertThat(kid.isSelected()).isTrue();
    }

    @Test
    void shouldBeNotSelected() {
        // Given
        Kid kid = new Kid(0, 0);
        kid.setSelected();

        // When
        kid.setNotSelected();

        // Then
        assertThat(kid.isSelected()).isFalse();
    }

    @Test
    void shouldBeHitAndDeselected() {
        // Given
        Kid kid = new Kid(0, 0);
        kid.setSelected();

        // When
        kid.isHit();

        // Then
        assertThat(kid.getIsHit()).isTrue();
        assertThat(kid.isSelected()).isFalse();
    }

    @Test
    void shouldSetKidAsDead() {
        // Given
        Kid kid = new Kid(0, 0);

        // When
        kid.setDead();

        // Then
        assertThat(kid.getDeathCounted()).isTrue();
    }

    @Test
    void shouldAddMovesToQueue() {
        // Given
        Kid kid = new Kid(0, 0);

        // When
        kid.addMovesInQueueLeft(5);

        // Then
        assertThat(kid.getMovesInQueueLeft()).isEqualTo(5);
    }

    @Test
    void shouldAddMultipleMovesToQueue() {
        // Given
        Kid kid = new Kid(0, 0);

        // When
        kid.addMovesInQueueLeft(3);
        kid.addMovesInQueueLeft(7);

        // Then
        assertThat(kid.getMovesInQueueLeft()).isEqualTo(10);
    }

    @Test
    void shouldSetPass() {
        // Given
        Kid kid = new Kid(0, 0);

        // When
        kid.setPass();

        // Then
        assertThat(kid.getPass()).isTrue();
    }

    @Test
    void shouldBeCountedToNextLevel() {
        // Given
        Kid kid = new Kid(0, 0);

        // When
        kid.setCountToNextLevel();

        // Then
        assertThat(kid.getCounted()).isTrue();
    }

    @Test
    void shouldReturnPoints() {
        // Given
        Kid kid = new Kid(0, 0);

        // Then
        assertThat(kid.getPoints()).isEqualTo(100);
    }
}
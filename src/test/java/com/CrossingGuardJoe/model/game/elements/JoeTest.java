package com.CrossingGuardJoe.model.game.elements;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JoeTest {

    @Test
    void shouldInitializeCorrectly() {
        // Given
        int x = 10;
        int y = 20;

        // When
        Joe joe = new Joe(x, y);

        // Then
        assertThat(joe.getPosition().getX()).isEqualTo(x);
        assertThat(joe.getPosition().getY()).isEqualTo(y);
        assertThat(joe.getScore()).isEqualTo(0); // Initial score
        assertThat(joe.getHearts()).isEqualTo(10); // Initial hearts
        assertThat(joe.getIsWalkingState()).isFalse();
        assertThat(joe.getIsRaisingStopSign()).isFalse();
        assertThat(joe.getIsPassSign()).isFalse();
        assertThat(joe.getIsHit()).isFalse();
        assertThat(joe.isFirstHalfOfMovement()).isTrue();
    }

    @Test
    void shouldIncreaseScore() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.addScore(10);

        // Then
        assertThat(joe.getScore()).isEqualTo(10);
    }

    @Test
    void shouldRemoveHeart() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.removeHeart();

        // Then
        assertThat(joe.getHearts()).isEqualTo(9);
    }

    @Test
    void shouldNotRemoveHeartIfJoeHasNoHearts() {
        // Given
        Joe joe = new Joe(0, 0);

        // Remove all hearts
        for (int i = 0; i < 10; i++) {
            joe.removeHeart();
        }

        // When
        joe.removeHeart();

        // Then
        assertThat(joe.getHearts()).isEqualTo(0);
    }

    @Test
    void shouldStartWalkingToLeft() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.startWalkingToLeft();

        // Then
        assertThat(joe.getIsWalkingState()).isTrue();
        assertThat(joe.getIsWalkingToLeft()).isTrue();
        assertThat(joe.getIsRaisingStopSign()).isFalse();
        assertThat(joe.getIsPassSign()).isFalse();
    }

    @Test
    void shouldStartWalkingToRight() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.startWalkingToRight();

        // Then
        assertThat(joe.getIsWalkingState()).isTrue();
        assertThat(joe.getIsWalkingToLeft()).isFalse();
        assertThat(joe.getIsRaisingStopSign()).isFalse();
        assertThat(joe.getIsPassSign()).isFalse();
    }

    @Test
    void shouldStopWalking() {
        // Given
        Joe joe = new Joe(0, 0);
        joe.startWalkingToRight();

        // When
        joe.stopWalking();

        // Then
        assertThat(joe.getIsWalkingState()).isFalse();
        assertThat(joe.getIsRaisingStopSign()).isFalse();
        assertThat(joe.getIsPassSign()).isFalse();
        assertThat(joe.isFirstHalfOfMovement()).isTrue();
    }

    @Test
    void shouldStartRaisingStopSign() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.startRaisingStopSign();

        // Then
        assertThat(joe.getIsWalkingState()).isFalse();
        assertThat(joe.getIsRaisingStopSign()).isTrue();
        assertThat(joe.getIsPassSign()).isFalse();
        assertThat(joe.isFirstHalfOfMovement()).isTrue();
    }

    @Test
    void shouldStartRaisingPassSign() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.startRaisingPassSign();

        // Then
        assertThat(joe.getIsWalkingState()).isFalse();
        assertThat(joe.getIsRaisingStopSign()).isFalse();
        assertThat(joe.getIsPassSign()).isTrue();
        assertThat(joe.isFirstHalfOfMovement()).isTrue();
    }

    @Test
    void shouldSetFirstHalfOfMovement() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.setFirstHalfOfMovement(false);

        // Then
        assertThat(joe.isFirstHalfOfMovement()).isFalse();
    }

    @Test
    void shouldNotBeHit() {
        // Given
        Joe joe = new Joe(0, 0);
        joe.isHitLeft(); // Assume Joe was hit

        // When
        joe.isNotHit();

        // Then
        assertThat(joe.getIsHit()).isFalse();
    }

    @Test
    void shouldBeHitFromLeft() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.isHitLeft();

        // Then
        assertThat(joe.getIsHit()).isTrue();
        assertThat(joe.getHitLeft()).isTrue();
    }

    @Test
    void shouldBeHitFromRight() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.isHitRight();

        // Then
        assertThat(joe.getIsHit()).isTrue();
        assertThat(joe.getHitLeft()).isFalse();
    }

    @Test
    void shouldAddHitPointsWhenScoreGreaterThanZero() {
        // Given
        Joe joe = new Joe(0, 0);
        joe.addScore(10);

        // When
        joe.countHitPoints();

        // Then
        assertThat(joe.getScore()).isEqualTo(8); // 10 - 2 (HITPOINTS)
    }

    @Test
    void shouldNotAddHitPointsWhenScoreIsZero() {
        // Given
        Joe joe = new Joe(0, 0);

        // When
        joe.countHitPoints();

        // Then
        assertThat(joe.getScore()).isEqualTo(0); // Score is unchanged
    }

    @Test
    void shouldNotAddHitPointsWhenScoreIsNegative() {
        // Given
        Joe joe = new Joe(0, 0);
        joe.addScore(-5); // Set score to negative

        // When
        joe.countHitPoints();

        // Then
        assertThat(joe.getScore()).isEqualTo(-5); // Score is unchanged
    }
}
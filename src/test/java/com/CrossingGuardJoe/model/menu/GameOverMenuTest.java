package com.CrossingGuardJoe.model.menu;

import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.game.Road;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameOverMenuTest {

    private GameOverMenu gameOverMenuWin;
    private GameOverMenu gameOverMenuLose;
    private Road mockRoad;

    @BeforeEach
    void setUp() {
        mockRoad = new Road(); // Create a new mock instance of Road
        gameOverMenuWin = new GameOverMenu(true, mockRoad);
        gameOverMenuLose = new GameOverMenu(false, mockRoad);
    }

    @Test
    void verifyConstructorInitialization() {
        // Validate 'isWin' for both states
        assertThat(gameOverMenuWin.isWin()).isTrue();
        assertThat(gameOverMenuLose.isWin()).isFalse();

        // Validate the current game reference
        assertThat(gameOverMenuWin.getCurrentGame()).isEqualTo(mockRoad);
        assertThat(gameOverMenuLose.getCurrentGame()).isEqualTo(mockRoad);

        // Validate options list
        assertThat(gameOverMenuWin.getNumberOptions()).isEqualTo(2);
        assertThat(gameOverMenuWin.getOption(0).name()).isEqualTo("Stats");
        assertThat(gameOverMenuWin.getOption(1).name()).isEqualTo("Exit");

        // Validate default selected option
        assertThat(gameOverMenuWin.getOptionSelected()).isEqualTo(0);
    }

    @Test
    void testNavigateUp() {
        // Navigate up from the default selection (Stats -> Exit)
        assertThat(gameOverMenuWin.isSelectedStats()).isTrue(); // Default
        gameOverMenuWin.navigateUp();
        assertThat(gameOverMenuWin.isSelectedStats()).isFalse();
        assertThat(gameOverMenuWin.isSelectedExit()).isTrue(); // Wrap around to Exit

        // Navigate up from Exit back to Stats
        gameOverMenuWin.navigateUp();
        assertThat(gameOverMenuWin.isSelectedStats()).isTrue();
        assertThat(gameOverMenuWin.isSelectedExit()).isFalse();
    }

    @Test
    void testNavigateDown() {
        // Navigate down from the default selection (Stats -> Exit)
        assertThat(gameOverMenuWin.isSelectedStats()).isTrue(); // Default
        gameOverMenuWin.navigateDown();
        assertThat(gameOverMenuWin.isSelectedStats()).isFalse();
        assertThat(gameOverMenuWin.isSelectedExit()).isTrue();

        // Navigate down from Exit back to Stats
        gameOverMenuWin.navigateDown();
        assertThat(gameOverMenuWin.isSelectedStats()).isTrue();
        assertThat(gameOverMenuWin.isSelectedExit()).isFalse();
    }

    @Test
    void testIsSelectedOption() {
        // Default option is Stats (index 0)
        assertThat(gameOverMenuWin.isSelectedOption(0)).isTrue();
        assertThat(gameOverMenuWin.isSelectedOption(1)).isFalse();

        // Navigate to Exit (index 1)
        gameOverMenuWin.navigateDown();
        assertThat(gameOverMenuWin.isSelectedOption(0)).isFalse();
        assertThat(gameOverMenuWin.isSelectedOption(1)).isTrue();

        // Navigate back to Stats (index 0)
        gameOverMenuWin.navigateDown();
        assertThat(gameOverMenuWin.isSelectedOption(0)).isTrue();
        assertThat(gameOverMenuWin.isSelectedOption(1)).isFalse();
    }

    @Test
    void testIsSelectedStats() {
        // Default selection is Stats
        assertThat(gameOverMenuWin.isSelectedStats()).isTrue();
        assertThat(gameOverMenuWin.isSelectedExit()).isFalse();

        // Navigate to Exit
        gameOverMenuWin.navigateDown();
        assertThat(gameOverMenuWin.isSelectedStats()).isFalse();
        assertThat(gameOverMenuWin.isSelectedExit()).isTrue();

        // Navigate back to Stats
        gameOverMenuWin.navigateDown();
        assertThat(gameOverMenuWin.isSelectedStats()).isTrue();
        assertThat(gameOverMenuWin.isSelectedExit()).isFalse();
    }

    @Test
    void testIsSelectedExit() {
        // Default selection is Stats
        assertThat(gameOverMenuWin.isSelectedStats()).isTrue();
        assertThat(gameOverMenuWin.isSelectedExit()).isFalse();

        // Navigate to Exit
        gameOverMenuWin.navigateDown();
        assertThat(gameOverMenuWin.isSelectedStats()).isFalse();
        assertThat(gameOverMenuWin.isSelectedExit()).isTrue();
    }

    @Test
    void testGetOption() {
        // Validate each option in the list
        Option statsOption = gameOverMenuWin.getOption(0);
        Option exitOption = gameOverMenuWin.getOption(1);

        assertThat(statsOption.name()).isEqualTo("Stats");
        assertThat(statsOption.position()).isEqualTo(new Position(232, 270));

        assertThat(exitOption.name()).isEqualTo("Exit");
        assertThat(exitOption.position()).isEqualTo(new Position(236, 310));
    }

    @Test
    void testGetNumberOptions() {
        // The menu should always have 2 options
        assertThat(gameOverMenuWin.getNumberOptions()).isEqualTo(2);
    }

    @Test
    void testNavigateRightAndLeft() {
        // Navigate right and left do nothing; ensure no state changes
        int initialOption = gameOverMenuWin.getOptionSelected();
        gameOverMenuWin.navigateRight();
        assertThat(gameOverMenuWin.getOptionSelected()).isEqualTo(initialOption);

        gameOverMenuWin.navigateLeft();
        assertThat(gameOverMenuWin.getOptionSelected()).isEqualTo(initialOption);
    }
}
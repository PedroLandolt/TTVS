package com.CrossingGuardJoe.model.menu;

import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.game.Road;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PauseMenuTest {

    private PauseMenu pauseMenu;
    private Road mockRoad;

    @BeforeEach
    void setUp() {
        // Initialize a mock Road object for testing
        mockRoad = new Road();

        // Initialize PauseMenu with the mock Road
        pauseMenu = new PauseMenu(mockRoad);
    }

    @Test
    void testInitialOptionSelectedIsResume() {
        // The default selected option should be "Resume"
        assertThat(pauseMenu.isSelectedResume()).isTrue();
        assertThat(pauseMenu.isSelectedStats()).isFalse();
        assertThat(pauseMenu.isSelectedExit()).isFalse();
    }

    @Test
    void testNavigateDownCyclesThroughOptions() {
        // Navigate down through the menu options to verify cycling behavior
        pauseMenu.navigateDown();
        assertThat(pauseMenu.isSelectedResume()).isFalse();
        assertThat(pauseMenu.isSelectedStats()).isTrue();

        pauseMenu.navigateDown();
        assertThat(pauseMenu.isSelectedStats()).isFalse();
        assertThat(pauseMenu.isSelectedExit()).isTrue();

        pauseMenu.navigateDown();
        assertThat(pauseMenu.isSelectedExit()).isFalse();
        assertThat(pauseMenu.isSelectedResume()).isTrue(); // Cycles back to the first option
    }

    @Test
    void testNavigateUpCyclesThroughOptions() {
        // Navigate up through the menu options to verify reverse cycling behavior
        pauseMenu.navigateUp();
        assertThat(pauseMenu.isSelectedExit()).isTrue(); // Wraps around to last option

        pauseMenu.navigateUp();
        assertThat(pauseMenu.isSelectedExit()).isFalse();
        assertThat(pauseMenu.isSelectedStats()).isTrue();

        pauseMenu.navigateUp();
        assertThat(pauseMenu.isSelectedStats()).isFalse();
        assertThat(pauseMenu.isSelectedResume()).isTrue();
    }

    @Test
    void testGetCurrentGame() {
        // Verify that PauseMenu correctly holds a reference to the current game
        assertThat(pauseMenu.getCurrentGame()).isEqualTo(mockRoad);
    }

    @Test
    void testGetOptionReturnsNonNullOptions() {
        // Verify that each menu option is correctly initialized and can be retrieved by index
        for (int i = 0; i < pauseMenu.getNumberOptions(); i++) {
            Option option = pauseMenu.getOption(i);
            assertThat(option).isNotNull();
        }
    }

    @Test
    void testGetNumberOptions() {
        // Verify the number of menu options is correct
        assertThat(pauseMenu.getNumberOptions()).isEqualTo(3); // Resume, Stats, Exit
    }

    @Test
    void testIsSelectedOption() {
        // Verify that the selected option tracks properly
        assertThat(pauseMenu.isSelectedOption(0)).isTrue(); // "Resume" is selected by default
        assertThat(pauseMenu.isSelectedOption(1)).isFalse();
        assertThat(pauseMenu.isSelectedOption(2)).isFalse();

        // Navigate through options and check results
        pauseMenu.navigateDown();
        assertThat(pauseMenu.isSelectedOption(0)).isFalse();
        assertThat(pauseMenu.isSelectedOption(1)).isTrue();

        pauseMenu.navigateDown();
        assertThat(pauseMenu.isSelectedOption(2)).isTrue();

        pauseMenu.navigateDown(); // Loops back
        assertThat(pauseMenu.isSelectedOption(0)).isTrue();
    }

    @Test
    void testIsSelectedResume() {
        // Verify the convenience method for the "Resume" menu option
        assertThat(pauseMenu.isSelectedResume()).isTrue(); // Default is Resume
        pauseMenu.navigateDown();
        assertThat(pauseMenu.isSelectedResume()).isFalse();
    }

    @Test
    void testIsSelectedStats() {
        // Verify the convenience method for the "Stats" menu option
        assertThat(pauseMenu.isSelectedStats()).isFalse();
        pauseMenu.navigateDown();
        assertThat(pauseMenu.isSelectedStats()).isTrue(); // After navigating down, Stats is highlighted
    }

    @Test
    void testIsSelectedExit() {
        // Verify the convenience method for the "Exit" menu option
        assertThat(pauseMenu.isSelectedExit()).isFalse();
        pauseMenu.navigateDown();
        pauseMenu.navigateDown();
        assertThat(pauseMenu.isSelectedExit()).isTrue(); // After navigating down twice, Exit is highlighted
    }

    @Test
    void testNavigateRightDoesNothing() {
        // Verify that the no-op `navigateRight` does not affect the selected option
        pauseMenu.navigateRight();
        assertThat(pauseMenu.isSelectedResume()).isTrue();

        pauseMenu.navigateDown();
        pauseMenu.navigateRight();
        assertThat(pauseMenu.isSelectedStats()).isTrue(); // No change
    }

    @Test
    void testNavigateLeftDoesNothing() {
        // Verify that the no-op `navigateLeft` does not affect the selected option
        pauseMenu.navigateLeft();
        assertThat(pauseMenu.isSelectedResume()).isTrue();

        pauseMenu.navigateDown();
        pauseMenu.navigateLeft();
        assertThat(pauseMenu.isSelectedStats()).isTrue(); // No change
    }
}
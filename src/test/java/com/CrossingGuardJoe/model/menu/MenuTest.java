package com.CrossingGuardJoe.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuTest {

    private Menu menu;

    @BeforeEach
    void setUp() {
        // Setup a new instance of Menu before every test
        menu = new Menu();
    }

    @Test
    void testInitialOptionSelectedIsStartGame() {
        // Verify the default selected option is Start Game.
        assertThat(menu.isSelectedStartGame()).isTrue();
        assertThat(menu.isSelectedInstructions()).isFalse();
        assertThat(menu.isSelectedCustomize()).isFalse();
        assertThat(menu.isSelectedExit()).isFalse();
    }

    @Test
    void testNavigateDownCyclesThroughOptions() {
        // When we navigate down, cycle through all options
        menu.navigateDown();
        assertThat(menu.isSelectedInstructions()).isTrue(); // Selects option 2

        menu.navigateDown();
        assertThat(menu.isSelectedCustomize()).isTrue(); // Selects option 3

        menu.navigateDown();
        assertThat(menu.isSelectedExit()).isTrue(); // Selects option 4

        menu.navigateDown(); // Back to first option
        assertThat(menu.isSelectedStartGame()).isTrue();
    }

    @Test
    void testNavigateUpCyclesThroughOptions() {
        // When we navigate up, cycle through all options in reverse order
        menu.navigateUp();
        assertThat(menu.isSelectedExit()).isTrue(); // Goes to last option

        menu.navigateUp();
        assertThat(menu.isSelectedCustomize()).isTrue(); // Cycles backward to option 3

        menu.navigateUp();
        assertThat(menu.isSelectedInstructions()).isTrue(); // Cycles backward to option 2

        menu.navigateUp();
        assertThat(menu.isSelectedStartGame()).isTrue(); // Cycles back to first option
    }

    @Test
    void testGetOptionReturnsNonNullOptions() {
        // Ensure all options exist in the menu and can be retrieved.
        for (int i = 0; i < menu.getNumberOptions(); i++) {
            assertThat(menu.getOption(i)).isNotNull(); // Verify options are non-null
        }
    }

    @Test
    void testGetNumberOptions() {
        // Ensure the correct number of options is returned
        assertThat(menu.getNumberOptions()).isEqualTo(4); // 4 predefined options
    }

    @Test
    void testIsSelectedOption() {
        // Assert the selected option functionality
        assertThat(menu.isSelectedOption(0)).isTrue(); // Start Game is the default
        assertThat(menu.isSelectedOption(1)).isFalse();
        assertThat(menu.isSelectedOption(2)).isFalse();
        assertThat(menu.isSelectedOption(3)).isFalse();

        // Navigate through options and check the selection
        menu.navigateDown(); // Move to option 1
        assertThat(menu.isSelectedOption(0)).isFalse();
        assertThat(menu.isSelectedOption(1)).isTrue();

        menu.navigateDown(); // Move to option 2
        assertThat(menu.isSelectedOption(2)).isTrue();

        menu.navigateDown(); // Move to option 3
        assertThat(menu.isSelectedOption(3)).isTrue();

        menu.navigateDown(); // Loop back to option 0
        assertThat(menu.isSelectedOption(0)).isTrue();
    }

    @Test
    void testNavigateRightDoesNothing() {
        // Test that the no-op method `navigateRight()` does not affect state changes
        menu.navigateRight();
        assertThat(menu.isSelectedStartGame()).isTrue();

        menu.navigateDown();
        menu.navigateRight();
        assertThat(menu.isSelectedInstructions()).isTrue();
    }

    @Test
    void testNavigateLeftDoesNothing() {
        // Test that the no-op method `navigateLeft()` does not affect state changes
        menu.navigateLeft();
        assertThat(menu.isSelectedStartGame()).isTrue();

        menu.navigateDown(); // Change option first
        menu.navigateLeft();
        assertThat(menu.isSelectedInstructions()).isTrue();
    }

    @Test
    void testAllSelectMethods() {
        // Test all "isSelected*" helper methods for correctness
        assertThat(menu.isSelectedStartGame()).isTrue();
        assertThat(menu.isSelectedInstructions()).isFalse();
        assertThat(menu.isSelectedCustomize()).isFalse();
        assertThat(menu.isSelectedExit()).isFalse();

        menu.navigateDown();
        assertThat(menu.isSelectedStartGame()).isFalse();
        assertThat(menu.isSelectedInstructions()).isTrue();

        menu.navigateDown();
        assertThat(menu.isSelectedCustomize()).isTrue();
        assertThat(menu.isSelectedExit()).isFalse();

        menu.navigateDown();
        assertThat(menu.isSelectedExit()).isTrue();
    }
}
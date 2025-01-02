package com.CrossingGuardJoe.model.menu;

import com.CrossingGuardJoe.controller.Sounds;
import com.CrossingGuardJoe.controller.SoundsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class InstructionsMenuTest {

    private InstructionsMenu instructionsMenu;

    @BeforeEach
    void setUp() {
        // Create a fresh instance of InstructionsMenu for each test
        instructionsMenu = new InstructionsMenu();
    }

    @Test
    void testConstructorInitialization() {
        // Initial state validations
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(1); // Default page
        assertThat(instructionsMenu.getTotalPages()).isEqualTo(5); // LAST_PAGE
    }

    @Test
    void testGetCurrentPage() {
        // Default page
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(1);

        // Navigate to another page and verify
        instructionsMenu.navigateRight();
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(2);
    }

    @Test
    void testGetTotalPages() {
        // Total pages should always be 5
        assertThat(instructionsMenu.getTotalPages()).isEqualTo(5);
    }

    @Test
    void testNavigateRightWithinBounds() {
        // Navigate right up to the LAST_PAGE
        instructionsMenu.navigateRight();
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(2); // Page incremented

        instructionsMenu.navigateRight();
        instructionsMenu.navigateRight();
        instructionsMenu.navigateRight();
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(5); // At LAST_PAGE
    }

    @Test
    void testNavigateRightOutOfBounds() {
        // Navigate right beyond the LAST_PAGE
        for (int i = 0; i < 5; i++) {
            instructionsMenu.navigateRight();
        }
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(5); // Should stay at LAST_PAGE

        instructionsMenu.navigateRight(); // Extra navigation (should have no effect)
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(5);
    }

    @Test
    void testNavigateLeftWithinBounds() {
        // Navigate right first, then back left
        instructionsMenu.navigateRight(); // Page 2
        instructionsMenu.navigateRight(); // Page 3
        instructionsMenu.navigateLeft(); // Back to Page 2
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(2);

        instructionsMenu.navigateLeft(); // Back to Page 1
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(1);
    }

    @Test
    void testNavigateLeftOutOfBounds() {
        // Attempt to navigate left while on the first page
        instructionsMenu.navigateLeft(); // Should have no effect
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(1);

        instructionsMenu.navigateLeft(); // Extra navigation (should still have no effect)
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(1);
    }

    @Test
    void testNavigateUp() {
        // `navigateUp()` is a no-op; validate it has no effect
        int initialPage = instructionsMenu.getCurrentPage();
        instructionsMenu.navigateUp();
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(initialPage);

        instructionsMenu.navigateUp(); // Further calls should also have no effect
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(initialPage);
    }

    @Test
    void testNavigateDown() {
        // `navigateDown()` is a no-op; validate it has no effect
        int initialPage = instructionsMenu.getCurrentPage();
        instructionsMenu.navigateDown();
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(initialPage);

        instructionsMenu.navigateDown(); // Further calls should also have no effect
        assertThat(instructionsMenu.getCurrentPage()).isEqualTo(initialPage);
    }

    @Test
    void testSoundEffectTriggeredOnNavigateRight() {
        // Mock static calls to SoundsController.getInstance()
        try (MockedStatic<SoundsController> mockedSounds = mockStatic(SoundsController.class)) {
            SoundsController mockController = mock(SoundsController.class);
            mockedSounds.when(SoundsController::getInstance).thenReturn(mockController);

            // Navigate right and ensure sound effect is triggered
            instructionsMenu.navigateRight();
            verify(mockController, times(1)).play(Sounds.SFX.FLIPPAGE);

            instructionsMenu.navigateRight();
            verify(mockController, times(2)).play(Sounds.SFX.FLIPPAGE);
        }
    }

    @Test
    void testSoundEffectTriggeredOnNavigateLeft() {
        // Mock static calls to SoundsController.getInstance()
        try (MockedStatic<SoundsController> mockedSounds = mockStatic(SoundsController.class)) {
            SoundsController mockController = mock(SoundsController.class);
            mockedSounds.when(SoundsController::getInstance).thenReturn(mockController);

            // Navigate right first to test navigation back left
            instructionsMenu.navigateRight();
            instructionsMenu.navigateLeft();
            verify(mockController, times(2)).play(Sounds.SFX.FLIPPAGE); // Once for right, once for left
        }
    }

    @Test
    void testNoSoundOnInvalidNavigation() {
        // Mock static calls to SoundsController.getInstance()
        try (MockedStatic<SoundsController> mockedSounds = mockStatic(SoundsController.class)) {
            SoundsController mockController = mock(SoundsController.class);
            mockedSounds.when(SoundsController::getInstance).thenReturn(mockController);

            // Navigate right up to the LAST_PAGE (5)
            for (int i = 0; i < 6; i++) { // Try navigating 6 times
                instructionsMenu.navigateRight();
            }

            // Verify only 4 sound effects played (valid navigations 1 → 2 → 3 → 4 → 5)
            verify(mockController, times(4)).play(Sounds.SFX.FLIPPAGE);

            // Navigate left to the first page (1)
            for (int i = 0; i < 6; i++) { // Try navigating 6 times backward
                instructionsMenu.navigateLeft();
            }

            // Verify 4 additional sound effects played (valid navigations 5 → 4 → 3 → 2 → 1)
            verify(mockController, times(8)).play(Sounds.SFX.FLIPPAGE); // Total: 4 (right) + 4 (left)
        }
    }
}
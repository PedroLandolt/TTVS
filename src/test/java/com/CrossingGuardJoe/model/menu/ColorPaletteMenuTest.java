package com.CrossingGuardJoe.model.menu;

import com.CrossingGuardJoe.controller.Sounds;
import com.CrossingGuardJoe.controller.SoundsController;
import com.CrossingGuardJoe.viewer.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ColorPaletteMenuTest {

    private ColorPaletteMenu colorPaletteMenu;

    @BeforeEach
    void setUp() {
        // Ensure a new ColorPaletteMenu object is created for each test
        colorPaletteMenu = new ColorPaletteMenu();
    }

    @Test
    void constructorShouldInitializeColorPaletteAndSelectedIndex() {
        List<Color> colorPalette = colorPaletteMenu.getColorPalette();

        // Verify that the color palette contains all Color enum values
        assertThat(colorPalette).isNotNull();
        assertThat(colorPalette).containsExactly(Color.values());

        // Verify the selected color index is initialized to 0
        assertThat(colorPaletteMenu.getSelectedColorIndex()).isEqualTo(0);
    }

    @Test
    void shouldReturnColorPalette() {
        List<Color> colorPalette = colorPaletteMenu.getColorPalette();

        // Verify the color palette matches the expected values
        assertThat(colorPalette).isNotNull();
        assertThat(colorPalette).containsExactly(Color.values());
    }

    @Test
    void shouldNavigateLeftWithWrapping() {
        try (MockedStatic<SoundsController> soundsControllerMock = mockStatic(SoundsController.class)) {
            SoundsController soundsController = mock(SoundsController.class);
            soundsControllerMock.when(SoundsController::getInstance).thenReturn(soundsController);

            // Moving left from index 0 should wrap to the last index
            colorPaletteMenu.navigateLeft();
            assertThat(colorPaletteMenu.getSelectedColorIndex()).isEqualTo(colorPaletteMenu.getColorPalette().size() - 1);

            // Verify sound effect was played exactly once (since we only move left once)
            verify(soundsController).play(Sounds.SFX.SELECT);
        }
    }

    @Test
    void shouldNavigateRightWithWrapping() {
        try (MockedStatic<SoundsController> soundsControllerMock = mockStatic(SoundsController.class)) {
            SoundsController soundsController = mock(SoundsController.class);
            soundsControllerMock.when(SoundsController::getInstance).thenReturn(soundsController);

            int paletteSize = colorPaletteMenu.getColorPalette().size();

            // Move right through the entire palette
            for (int i = 1; i <= paletteSize; i++) {
                colorPaletteMenu.navigateRight();
                assertThat(colorPaletteMenu.getSelectedColorIndex()).isEqualTo(i % paletteSize);
            }

            // Verify the sound effect was played for each navigation step
            verify(soundsController, times(paletteSize)).play(Sounds.SFX.SELECT);
        }
    }

    @Test
    void shouldResetSelectedColorIndexToZero() {
        // Navigate right to change selected index
        colorPaletteMenu.navigateRight();
        colorPaletteMenu.navigateRight();
        assertThat(colorPaletteMenu.getSelectedColorIndex()).isEqualTo(2);

        // Reset the selected color index
        colorPaletteMenu.resetSelectedColorIndex();
        assertThat(colorPaletteMenu.getSelectedColorIndex()).isEqualTo(0);
    }

    @Test
    void shouldVerifyIfSpecificColorIsSelected() {
        // Verify initial state
        assertThat(colorPaletteMenu.isColorSelected(0)).isTrue();
        assertThat(colorPaletteMenu.isColorSelected(1)).isFalse();

        // Navigate right to change selection
        colorPaletteMenu.navigateRight();
        assertThat(colorPaletteMenu.isColorSelected(0)).isFalse();
        assertThat(colorPaletteMenu.isColorSelected(1)).isTrue();
    }

    @Test
    void navigateUpShouldDoNothing() {
        // Call navigateUp; it should not change the selected index
        colorPaletteMenu.navigateUp();
        assertThat(colorPaletteMenu.getSelectedColorIndex()).isEqualTo(0);
    }

    @Test
    void navigateDownShouldDoNothing() {
        // Call navigateDown; it should not change the selected index
        colorPaletteMenu.navigateDown();
        assertThat(colorPaletteMenu.getSelectedColorIndex()).isEqualTo(0);
    }
}
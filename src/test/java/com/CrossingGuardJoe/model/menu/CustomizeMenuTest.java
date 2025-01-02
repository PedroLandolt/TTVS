package com.CrossingGuardJoe.model.menu;

import com.CrossingGuardJoe.viewer.images.defined.CarImage;
import com.CrossingGuardJoe.viewer.images.defined.JoeImages;
import com.CrossingGuardJoe.viewer.images.defined.KidImages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomizeMenuTest {

    private CustomizeMenu customizeMenu;

    @BeforeEach
    void setUp() {
        customizeMenu = new CustomizeMenu();
    }

    @Test
    void testConstructorInitialization() {
        // Check overall structure of menu levels
        assertThat(customizeMenu.getMenuLevels()).hasSize(3);

        // Verify specific details about each menu level
        verifyJoeMenu();
        verifyKidsMenu();
        verifyCarsMenu();

        // Check default state of other fields
        assertThat(customizeMenu.getColorPaletteMenu()).isNotNull();
        assertThat(customizeMenu.isColorPaletteSelected()).isFalse();
        assertThat(customizeMenu.getDefinedColors()).hasSize(9);
        assertThat(customizeMenu.getOldColor()).isEqualTo('\0'); // Default char value
        assertThat(customizeMenu.getNewColor()).isEqualTo('\0'); // Default char value
    }

    private void verifyJoeMenu() {
        List<Option> joeMenu = customizeMenu.getMenuLevels().get(0);
        assertThat(joeMenu.get(0).image()).isEqualTo(JoeImages.getJoeStandImage());
        assertThat(joeMenu.get(1).name()).isEqualTo("Cap");
        assertThat(joeMenu.get(2).name()).isEqualTo("Clothes");
        assertThat(joeMenu.get(3).name()).isEqualTo("Vest");
        assertThat(joeMenu.get(4).name()).isEqualTo("Shoes");
    }

    private void verifyKidsMenu() {
        List<Option> kidsMenu = customizeMenu.getMenuLevels().get(1);
        assertThat(kidsMenu.get(0).image()).isEqualTo(KidImages.getKidStandImage());
        assertThat(kidsMenu.get(1).name()).isEqualTo("Shirt");
        assertThat(kidsMenu.get(2).name()).isEqualTo("Pants");
        assertThat(kidsMenu.get(3).name()).isEqualTo("BackPack");
        assertThat(kidsMenu.get(4).name()).isEqualTo("Shoes");
    }

    private void verifyCarsMenu() {
        List<Option> carsMenu = customizeMenu.getMenuLevels().get(2);
        assertThat(carsMenu.get(0).image()).isEqualTo(CarImage.getCarImage());
        assertThat(carsMenu.get(1).name()).isEqualTo("Car");
    }

    @Test
    void testNavigationBetweenLevels() {
        // Start at level 0 (Joe Customize)
        assertThat(customizeMenu.isSelectedJoeCustomize()).isTrue();

        // Navigate to level 1 (Kids Customize)
        customizeMenu.navigateRight();
        assertThat(customizeMenu.isSelectedKidsCustomize()).isTrue();

        // Navigate to level 2 (Cars Customize)
        customizeMenu.navigateRight();
        assertThat(customizeMenu.isSelectedCarsCustomize()).isTrue();

        // Should not navigate beyond level 2
        customizeMenu.navigateRight();
        assertThat(customizeMenu.isSelectedCarsCustomize()).isTrue();

        // Navigate back to level 0
        customizeMenu.navigateLeft();
        customizeMenu.navigateLeft();
        assertThat(customizeMenu.isSelectedJoeCustomize()).isTrue();

        // Should not navigate below level 0
        customizeMenu.navigateLeft();
        assertThat(customizeMenu.isSelectedJoeCustomize()).isTrue();
    }

    @Test
    void testNavigationWithinLevel() {
        // Within level 0
        assertThat(customizeMenu.isSelectedJoeCap()).isTrue(); // Default option

        customizeMenu.navigateDown(); // Move to "Clothes"
        assertThat(customizeMenu.isSelectedJoeClothes()).isTrue();

        customizeMenu.navigateDown(); // Move to "Vest"
        assertThat(customizeMenu.isSelectedJoeVest()).isTrue();

        customizeMenu.navigateDown(); // Move to "Shoes"
        assertThat(customizeMenu.isSelectedJoeShoes()).isTrue();

        // Should not move beyond last option
        customizeMenu.navigateDown();
        assertThat(customizeMenu.isSelectedJoeShoes()).isTrue();

        // Move back up
        customizeMenu.navigateUp(); // "Shoes" -> "Vest"
        assertThat(customizeMenu.isSelectedJoeVest()).isTrue();

        customizeMenu.navigateUp(); // "Vest" -> "Clothes"
        assertThat(customizeMenu.isSelectedJoeClothes()).isTrue();

        customizeMenu.navigateUp(); // "Clothes" -> "Cap"
        assertThat(customizeMenu.isSelectedJoeCap()).isTrue();

        // Should not move above first option
        customizeMenu.navigateUp();
        assertThat(customizeMenu.isSelectedJoeCap()).isTrue();
    }

    @Test
    void testSelectedOptionAndColor() {
        // Validate Joe level color mapping
        assertThat(customizeMenu.getSelectedColorChar()).isEqualTo('<');
        customizeMenu.navigateDown();
        assertThat(customizeMenu.getSelectedColorChar()).isEqualTo('!');
        customizeMenu.navigateDown();
        assertThat(customizeMenu.getSelectedColorChar()).isEqualTo('+');
        customizeMenu.navigateDown();
        assertThat(customizeMenu.getSelectedColorChar()).isEqualTo('*');

        // Validate Kids level color mapping
        customizeMenu.navigateRight();
        assertThat(customizeMenu.getSelectedColorChar()).isEqualTo('&');
        customizeMenu.navigateUp();
        assertThat(customizeMenu.getSelectedColorChar()).isEqualTo(')');
        customizeMenu.navigateUp();
        assertThat(customizeMenu.getSelectedColorChar()).isEqualTo('(');
        customizeMenu.navigateUp();
        assertThat(customizeMenu.getSelectedColorChar()).isEqualTo('\'');

        // Validate Cars level color mapping
        customizeMenu.navigateRight();
        assertThat(customizeMenu.getSelectedColorChar()).isEqualTo('@');
    }

    @Test
    void testDefinedColors() {
        List<Option> definedColors = customizeMenu.getDefinedColors();
        assertThat(definedColors).hasSize(9);

        // Validate the colors have correct image dimensions
        for (Option color : definedColors) {
            assertThat(color.image().length).isEqualTo(16);
        }
    }

    @Test
    void testColorSelectionAndChange() {
        // Ensure initial state
        assertThat(customizeMenu.isColorPaletteSelected()).isFalse();

        // Set color palette selection
        customizeMenu.setColorPaletteSelected(true);
        assertThat(customizeMenu.isColorPaletteSelected()).isTrue();

        customizeMenu.setColorPaletteSelected(false);
        assertThat(customizeMenu.isColorPaletteSelected()).isFalse();

        // Perform a color change
        customizeMenu.setColorChange('X', 'Y');
        assertThat(customizeMenu.getOldColor()).isEqualTo('X');
        assertThat(customizeMenu.getNewColor()).isEqualTo('Y');

        assertThat(customizeMenu.getColorPaletteMenu().getSelectedColorIndex()).isEqualTo(0);
    }

    @Test
    void testIsSelectedKidsCustomize() {
        // By default, start at Level 0 (Joe Customize)
        assertThat(customizeMenu.isSelectedKidsCustomize()).isFalse();

        // Navigate to Level 1 (Kids Customize)
        customizeMenu.navigateRight();
        assertThat(customizeMenu.isSelectedKidsCustomize())
                .as("After navigating to Level 1, Kids Customize should be selected")
                .isTrue();
    }

    @Test
    void testIsSelectedCarsCustomize() {
        // By default, start at Level 0 (Joe Customize)
        assertThat(customizeMenu.isSelectedCarsCustomize()).isFalse();

        // Navigate to Level 1 (Kids Customize)
        customizeMenu.navigateRight();
        assertThat(customizeMenu.isSelectedCarsCustomize()).isFalse();

        // Navigate to Level 2 (Cars Customize)
        customizeMenu.navigateRight();
        assertThat(customizeMenu.isSelectedCarsCustomize())
                .as("After navigating to Level 2, Cars Customize should be selected")
                .isTrue();
    }

    @Test
    void testIsSelectedJoeCustomize() {
        // By default, the currentLevel is 0, so Joe Customize should be selected
        assertThat(customizeMenu.isSelectedJoeCustomize())
                .as("Default selected menu should be Joe Customize")
                .isTrue();

        // Navigate to other levels and check Joe Customize is no longer selected
        customizeMenu.navigateRight(); // Level 1 (Kids Customize)
        assertThat(customizeMenu.isSelectedJoeCustomize()).isFalse();

        customizeMenu.navigateRight(); // Level 2 (Cars Customize)
        assertThat(customizeMenu.isSelectedJoeCustomize()).isFalse();

        // Navigate back to Level 0 (Joe Customize)
        customizeMenu.navigateLeft();
        customizeMenu.navigateLeft();
        assertThat(customizeMenu.isSelectedJoeCustomize())
                .as("After navigating back, Joe Customize should be selected again")
                .isTrue();
    }
}
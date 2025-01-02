package com.CrossingGuardJoe.viewer.images.defined;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class ToolImagesTest {

    @Test
    void testGetArrowDownImage() {
        // Act
        String[] actual = ToolImages.getArrowDownImage();

        // Assert: Verify the defensive clone
        assertNotNull(actual, "ArrowDown image array should not be null");
        assertTrue(actual.length > 0, "ArrowDown image array should not be empty");

        // Validate known content
        Assertions.assertThat(actual).contains(
                "$$$$$$$$$$$$$$$$",
                "$$GGGGGGGGGGGG$$",
                "       $$       "
        );

        // Test immutability
        String[] original = ToolImages.getArrowDownImage();
        actual[0] = "Modified Content";
        assertNotEquals(actual[0], original[0], "ArrowDown image array must be immutable");
    }

    @Test
    void testGetArrowRightImage() {
        // Act
        String[] actual = ToolImages.getArrowRightImage();

        // Assert: Verify the defensive clone
        assertNotNull(actual, "ArrowRight image array should not be null");
        assertTrue(actual.length > 0, "ArrowRight image array should not be empty");

        // Validate known content
        Assertions.assertThat(actual).contains(
                "$$",
                "$GGGGGGGGG$$$",
                "$GGG$$$"
        );

        // Test immutability
        String[] original = ToolImages.getArrowRightImage();
        actual[0] = "Modified Content";
        assertNotEquals(actual[0], original[0], "ArrowRight image array must be immutable");
    }

    @Test
    void testGetKeyUpImage() {
        // Act
        String[] actual = ToolImages.getKeyUpImage();

        // Assert: Verify the defensive clone
        assertNotNull(actual, "KeyUp image array should not be null");
        assertTrue(actual.length > 0, "KeyUp image array should not be empty");

        // Validate known content
        Assertions.assertThat(actual).contains(
                "$$$$KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK$$$$",
                "$$KKKK$$KKKKKKKKKKKK$$$$$$$$$$KKKKKKKKKKKKK$$KKKK$$",
                "  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  "
        );

        // Test immutability
        String[] original = ToolImages.getKeyUpImage();
        actual[0] = "Modified Content";
        assertNotEquals(actual[0], original[0], "KeyUp image array must be immutable");
    }

    @Test
    void testGetKeyDownImage() {
        // Act
        String[] actual = ToolImages.getKeyDownImage();

        // Assert: Verify the defensive clone
        assertNotNull(actual, "KeyDown image array should not be null");
        assertTrue(actual.length > 0, "KeyDown image array should not be empty");

        // Validate known content
        Assertions.assertThat(actual).contains(
                "$$KKKK$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$KKKK$$",
                "$$KKKK$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$KKKK$$",
                "$$KKKK$$KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK$$KKKK$$"
        );

        // Test immutability
        String[] original = ToolImages.getKeyDownImage();
        actual[0] = "Modified Content";
        assertNotEquals(actual[0], original[0], "KeyDown image array must be immutable");
    }

    @Test
    void testGetKeyRightImage() {
        // Act
        String[] actual = ToolImages.getKeyRightImage();

        // Assert: Verify the defensive clone
        assertNotNull(actual, "KeyRight image array should not be null");
        assertTrue(actual.length > 0, "KeyRight image array should not be empty");

        // Validate known content
        Assertions.assertThat(actual).contains(
                "$$KKKK$$KKKKKKKKK$$$$$KKKKKKKKKKKKKKKKKKKKK$$KKKK$$",
                "$$KKKK$$KKKKKKKKK$$$KKKKKKKKKKKKKKKKKKKKKKK$$KKKK$$",
                "$$KKKK$$KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK$$KKKK$$"
        );

        // Test immutability
        String[] original = ToolImages.getKeyRightImage();
        actual[0] = "Modified Content";
        assertNotEquals(actual[0], original[0], "KeyRight image array must be immutable");
    }

    @Test
    void testGetKeyLeftImage() {
        // Act
        String[] actual = ToolImages.getKeyLeftImage();

        // Assert: Verify the defensive clone
        assertNotNull(actual, "KeyLeft image array should not be null");
        assertTrue(actual.length > 0, "KeyLeft image array should not be empty");

        // Validate known content
        Assertions.assertThat(actual).contains(
                "$$KKKK$$KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK$$KKKK$$",
                "$$KKKK$$KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK$$KKKK$$",
                "$$KKKK$$KKKKKKKKKKKKKKKKKKKKKKK$$$KKKKKKKKK$$KKKK$$"
        );

        // Test immutability
        String[] original = ToolImages.getKeyLeftImage();
        actual[0] = "Modified Content";
        assertNotEquals(actual[0], original[0], "KeyLeft image array must be immutable");
    }

    @Test
    void testGetKeyEscImage() {
        // Act
        String[] actual = ToolImages.getKeyEscImage();

        // Assert: Verify the defensive clone
        assertNotNull(actual, "KeyEsc image array should not be null");
        assertTrue(actual.length > 0, "KeyEsc image array should not be empty");

        // Validate known content
        Assertions.assertThat(actual).contains(
                "$$KKKK$$KKKKKKKKKKKKKKKKKKKKKKKKKKKKK$$",
                "$$KKKK$$KKKKKKKK$$$$$$$KK$$$$$$$$KKKK$$",
                "$$KKKK$$KKKKKKKK$$$$$$$KK$$$$$$$$KKKK$$"
        );

        // Test immutability
        String[] original = ToolImages.getKeyEscImage();
        actual[0] = "Modified Content";
        assertNotEquals(actual[0], original[0], "KeyEsc image array must be immutable");
    }
}
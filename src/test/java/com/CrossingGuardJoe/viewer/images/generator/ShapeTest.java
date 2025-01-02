package com.CrossingGuardJoe.viewer.images.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {

    @Test
    void testRectangleWithStandardDimensions() {
        // Act
        String[] result = Shape.RectangleFilledGenerator(4, 3, '*', 2, '#');

        // Assert
        assertNotNull(result, "The result should not be null.");
        assertEquals(3, result.length, "The result should have 3 rows.");
        assertEquals("########", result[0], "Top border row should match the expected string.");
        assertEquals("##****##", result[1], "Inner row with borders should match expected content.");
        assertEquals("########", result[2], "Bottom border row should match the expected string.");
    }

    @Test
    void testRectangleWithMinimumDimensions() {
        // Act
        String[] result = Shape.RectangleFilledGenerator(1, 1, '@', 1, '$');

        // Assert
        assertNotNull(result, "The result should not be null for minimum size rectangle.");
        assertEquals(1, result.length, "The result should have 1 row.");
        assertEquals("$$$", result[0], "The single row should match the expected content.");
    }

    @Test
    void testNarrowRectangle() {
        // Act
        String[] result = Shape.RectangleFilledGenerator(1, 5, '*', 1, '#');

        // Assert
        assertNotNull(result, "The result should not be null for a narrow rectangle.");
        assertEquals(5, result.length, "The result should have 5 rows.");
        assertEquals("###", result[0], "Top border row should match the expected content.");
        assertEquals("#*#", result[1], "Each inner row should match the expected content.");
        assertEquals("#*#", result[3], "Each inner row should match the expected content.");
        assertEquals("###", result[4], "Bottom border row should match the expected content.");
    }

    @Test
    void testWideRectangle() {
        // Act
        String[] result = Shape.RectangleFilledGenerator(5, 1, '=', 1, '+');

        // Assert
        assertNotNull(result, "The result should not be null for a wide rectangle.");
        assertEquals(1, result.length, "The result should have 1 row.");
        assertEquals("+++++++", result[0], "The single row (with border) should match the expected content.");
    }

    @Test
    void testLargeDimensions() {
        // Act
        String[] result = Shape.RectangleFilledGenerator(50, 10, '*', 3, '#');

        // Assert
        assertNotNull(result, "The result should not be null for a large rectangle.");
        assertEquals(10, result.length, "The result should have 10 rows.");
        assertEquals(50 + 2 * 3, result[0].length(), "Each row should have the correct total length.");
        assertTrue(result[0].startsWith("###"), "Each row should start with the border character.");
        assertTrue(result[0].endsWith("###"), "Each row should end with the border character.");
    }

    @Test
    void testSpecialCharacters() {
        // Act
        String[] result = Shape.RectangleFilledGenerator(3, 2, '@', 1, '$');

        // Assert
        assertNotNull(result, "The result should not be null for special characters.");
        assertEquals(2, result.length, "The result should have 2 rows.");
        assertEquals("$$$$$", result[0], "Top border row should match the expected content.");
        assertEquals("$$$$$", result[1], "Inner row content with borders should match expected content.");
    }

    @Test
    void testZeroWidthAndHeight() {
        // Act & Assert
        ArrayIndexOutOfBoundsException exception = assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                        Shape.RectangleFilledGenerator(0, 0, '*', 1, '#'),
                "Zero width and height should throw ArrayIndexOutOfBoundsException."
        );

        // Assert: Ensure the exception message contains meaningful information (optional)
        assertTrue(exception.getMessage().contains("Index") || exception.getMessage().contains("bounds"),
                "The exception message should mention 'Index' or 'bounds'.");
    }

    @Test
    void testNegativeWidth() {
        // Act
        String[] result = Shape.RectangleFilledGenerator(-1, 3, '*', 1, '#');

        // Assert
        assertNotNull(result, "The result should not be null.");
        assertEquals(3, result.length, "The result should have exactly the number of rows as the height.");
        assertEquals("#", result[0], "The border for the top row should only contain board characters.");
        assertEquals("##", result[1], "The middle rows should only contain board characters since width is negative.");
        assertEquals("#", result[2], "The border for the bottom row should only contain board characters.");
    }

    @Test
    void testNegativeHeight() {
        // Act & Assert
        NegativeArraySizeException exception = assertThrows(NegativeArraySizeException.class, () ->
                        Shape.RectangleFilledGenerator(5, -5, '*', 1, '#'),
                "Negative height should throw NegativeArraySizeException."
        );

        // No assertions about the message, as it may vary between environments
        assertNotNull(exception, "The exception should not be null.");
    }

    @Test
    void testNegativeBorders() {
        // Act
        String[] result = Shape.RectangleFilledGenerator(3, 3, '*', -2, '#');

        // Assert
        assertNotNull(result, "The result should not be null for negative borders.");
        assertEquals(3, result.length, "The number of rows should match the height.");
        assertEquals("***", result[1], "The middle row should only contain the rectangle with no borders for negative border width.");
    }
}
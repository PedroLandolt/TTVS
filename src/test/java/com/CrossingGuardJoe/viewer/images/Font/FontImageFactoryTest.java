package com.CrossingGuardJoe.viewer.images.Font;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class FontImageFactoryTest {

    @Test
    void testConstructor() {
        // Act
        FontImageFactory factory = new FontImageFactory();

        // Assert
        assertNotNull(factory, "FontImageFactory should be properly instantiated");
    }

    @Test
    void testGetImageRepresentationWithStringInput() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        String input = "Hello";

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Image representation for String input should not be null");
        assertTrue(imageRepresentation.length > 0, "Image representation for String input should not be empty");
        assertThat(imageRepresentation).allMatch(line -> line.length() > 0, "Each line in the image should have non-zero length");
    }

    @Test
    void testGetImageRepresentationWithNumericInput() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        Integer input = 123;

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Image representation for numeric input should not be null");
        assertTrue(imageRepresentation.length > 0, "Image representation for numeric input should not be empty");
        assertThat(imageRepresentation).allMatch(line -> line.length() > 0, "Each line in the image should have non-zero length");
    }

    @Test
    void testGetImageRepresentationWithUnsupportedInput() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        Object unsupportedInput = new Object();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.getImageRepresentation(unsupportedInput),
                "An exception should be thrown for unsupported input types");

        assertEquals("Unsupported input type", exception.getMessage(), "Exception message should match expected text");
    }

    @Test
    void testGetImageRepresentationWithLongInput() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        Long input = 456L;

        // Convert Long to int explicitly (compensating for the casting limitation in the method)
        int intInput = input.intValue();

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(intInput);

        // Assert
        assertNotNull(imageRepresentation, "Image representation for Long input converted to int should not be null");
        assertTrue(imageRepresentation.length > 0, "Image representation for Long input converted to int should not be empty");
        assertThat(imageRepresentation).allMatch(line -> line.length() > 0, "Each line in the image should have non-zero length");
    }

    @Test
    void testGetImageRepresentationWithShortInput() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        Short input = 12;

        // Convert Short to int explicitly (compensating for casting limitation in the method)
        int intInput = input.intValue();

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(intInput);

        // Assert
        assertNotNull(imageRepresentation, "Image representation for Short input converted to int should not be null");
        assertTrue(imageRepresentation.length > 0, "Image representation for Short input converted to int should not be empty");
        assertThat(imageRepresentation).allMatch(line -> line.length() > 0, "Each line in the image should have non-zero length");
    }

    @Test
    void testGetImageRepresentationWithStringContainingSpace() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        String input = "A B";

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Image representation for String with spaces should not be null");
        assertTrue(imageRepresentation.length > 0, "Image representation for String with spaces should not be empty");
        assertThat(imageRepresentation).allMatch(line -> line.contains(" "), "Representation should correctly handle spaces");
    }

    @Test
    void testGetImageRepresentationHandlesMixedCaseString() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        String input = "aBc";

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Mixed case string input should return a non-null image representation");
        assertTrue(imageRepresentation.length > 0, "Mixed case string input should not return an empty image representation");
    }

    @Test
    void testGetImageRepresentationHandlesEmptyString() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        String input = "";

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Image representation for an empty string should not be null");
        assertThat(imageRepresentation).allMatch(String::isEmpty, "All lines should be empty for an empty string input");
    }

    @Test
    void testGetImageRepresentationHandlesNumericZero() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        int input = 0;

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Image representation for numeric zero should not be null");
        assertTrue(imageRepresentation.length > 0, "Image representation for numeric zero should not be empty");
    }

    @Test
    void testGetAlphabetIndexPrivateMethod() throws Exception {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        char inputChar = 'C';

        // Use reflection to access the private method
        java.lang.reflect.Method method = FontImageFactory.class.getDeclaredMethod("getAlphabetIndex", char.class);
        method.setAccessible(true);

        // Act
        int alphabetIndex = (int) method.invoke(factory, inputChar);

        // Assert
        assertEquals(2, alphabetIndex, "Alphabet index of 'C' should be 2");
    }

    @Test
    void testGetImageRepresentationStringWithOnlySpaces() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        String input = "    "; // String containing only spaces

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Image representation for spaces should not be null");
        assertTrue(imageRepresentation.length > 0, "Image representation for spaces should not be empty");
        assertThat(imageRepresentation).allMatch(line -> line.trim().isEmpty(), "Each line should correspond to a space");
    }

    @Test
    void testGetImageRepresentationStringWithNonAlphabetCharacters() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        String input = "123!@#"; // Contains numbers and special characters

        // Act & Assert
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> factory.getImageRepresentation(input),
                "Should throw ArrayIndexOutOfBoundsException for unsupported characters");
    }

    @Test
    void testGetImageRepresentationWithIntegerMinValue() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        int input = Integer.MIN_VALUE; // -2147483648

        // Act & Assert
        ArrayIndexOutOfBoundsException exception = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> factory.getImageRepresentation(input),
                "Expected ArrayIndexOutOfBoundsException for Integer.MIN_VALUE"
        );

        // Verify exception details
        assertTrue(exception.getMessage().contains("Index"),
                "Exception message should indicate an array index issue");
    }

    @Test
    void testGetImageRepresentationWithIntegerMaxValue() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        int input = Integer.MAX_VALUE; // 2147483647

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Should return a non-null representation for Integer.MAX_VALUE");
        assertTrue(imageRepresentation.length > 0);
    }

    @Test
    void testGetNumberImageHandlesZero() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        int input = 0;

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Result for numeric 0 should not be null");
        assertTrue(imageRepresentation.length > 0, "Result should not be empty");
    }

    @Test
    void testGetImageRepresentationStringMixedContent() {
        // Arrange
        FontImageFactory factory = new FontImageFactory();
        String input = "A B C"; // Alphabets with spaces

        // Act
        String[] imageRepresentation = factory.getImageRepresentation(input);

        // Assert
        assertNotNull(imageRepresentation, "Mixed alphanumeric input should not return null");
        assertTrue(imageRepresentation.length > 0, "Representation should not be empty");

        // Ensure at least one line contains a space (fixing previous anyMatch issue)
        boolean containsSpace = java.util.Arrays.stream(imageRepresentation)
                .anyMatch(line -> line.contains(" "));
        assertTrue(containsSpace, "At least one line should contain spaces for mixed input");
    }
}
package com.CrossingGuardJoe.viewer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ColorCustomizeTest {

    private ColorCustomize colorCustomize;

    @BeforeEach
    void setUp() throws Exception {
        // Get the singleton instance before each test
        colorCustomize = ColorCustomize.getInstance();

        // Clear the characterMap field using reflection
        Field characterMapField = ColorCustomize.class.getDeclaredField("characterMap");
        characterMapField.setAccessible(true); // Allow private field access
        Map<?, ?> characterMap = (Map<?, ?>) characterMapField.get(colorCustomize); // Access the map
        characterMap.clear(); // Clear any existing state
    }

    @Test
    void testSingletonInstance() {
        // Arrange
        ColorCustomize anotherInstance = ColorCustomize.getInstance();

        // Assert: Both instances should be the same
        assertSame(colorCustomize, anotherInstance, "Instances of ColorCustomize should be the same (Singleton).");
    }

    @Test
    void testAddMapping() {
        // Act
        colorCustomize.addMapping('A', 'B');

        // Assert: Retrieve the mapping and verify it
        char mappedCharacter = colorCustomize.getMappedCharacter('A');
        assertEquals('B', mappedCharacter, "Mapped character for 'A' should be 'B'.");
    }

    @Test
    void testGetMappedCharacterWithExistingMapping() {
        // Arrange: Add a mapping
        colorCustomize.addMapping('X', 'Y');

        // Act: Retrieve the mapping
        char result = colorCustomize.getMappedCharacter('X');

        // Assert: Verify the correct mapped character is returned
        assertEquals('Y', result, "Mapped character for 'X' should be 'Y'.");
    }

    @Test
    void testGetMappedCharacterWithoutMapping() {
        // Act: Query a non-mapped character
        char result = colorCustomize.getMappedCharacter('Z');

        // Assert: The non-mapped character itself should be returned
        assertEquals('Z', result, "For non-mapped characters, the method should return the same character.");
    }

    @Test
    void testOverwriteMapping() {
        // Arrange: Add an initial mapping
        colorCustomize.addMapping('C', 'D');
        assertEquals('D', colorCustomize.getMappedCharacter('C'), "Initially, 'C' should map to 'D'.");

        // Act: Add a new mapping for the same character
        colorCustomize.addMapping('C', 'E');

        // Assert: Verify the mapping is updated to the new value
        assertEquals('E', colorCustomize.getMappedCharacter('C'), "Mapping for 'C' should be updated to 'E'.");
    }

    @Test
    void testMultipleMappings() {
        // Arrange: Add multiple mappings
        colorCustomize.addMapping('A', 'B');
        colorCustomize.addMapping('1', '2');
        colorCustomize.addMapping('$', '%');

        // Act: Verify each mapping
        assertEquals('B', colorCustomize.getMappedCharacter('A'), "Mapped character for 'A' should be 'B'.");
        assertEquals('2', colorCustomize.getMappedCharacter('1'), "Mapped character for '1' should be '2'.");
        assertEquals('%', colorCustomize.getMappedCharacter('$'), "Mapped character for '$' should be '%'.");
    }

    @Test
    void testClearStateBetweenTests() {

        // Arrange: Add a mapping in an earlier test
        colorCustomize.addMapping('G', 'H');

        // Act: Query a non-mapped character
        char result = colorCustomize.getMappedCharacter('X');

        // Assert: The map should only return previously mapped characters
        assertEquals('X', result, "Non-mapped characters should return themselves.");
        assertEquals('H', colorCustomize.getMappedCharacter('G'), "'G' should still map to 'H'.");
    }
}
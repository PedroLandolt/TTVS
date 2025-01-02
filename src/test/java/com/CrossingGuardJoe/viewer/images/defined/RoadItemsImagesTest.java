package com.CrossingGuardJoe.viewer.images.defined;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class RoadItemsImagesTest {

    @Test
    void testGetSignalImageNotNull() {
        // Act
        String[] actual = RoadItemsImages.getSignalImage();

        // Assert: The returned signal image array should not be null
        assertNotNull(actual, "Signal image array should not be null");
    }

    @Test
    void testGetSignalImageNotEmpty() {
        // Act
        String[] actual = RoadItemsImages.getSignalImage();

        // Assert: The array should not be empty
        assertTrue(actual.length > 0, "Signal image array should not be empty");
    }

    @Test
    void testGetSignalImageContainsExpectedContent() {
        // Act
        String[] actual = RoadItemsImages.getSignalImage();

        // Assert: Check for a known pattern in the returned array
        Assertions.assertThat(actual).contains(
                "                 $$$$$                 ",
                "                $$qqq$$                ",
                "                $AAAAA$                "
        );
    }

    @Test
    void testGetSignalImageImmutability() {
        // Act
        String[] original = RoadItemsImages.getSignalImage();
        String[] modified = RoadItemsImages.getSignalImage();

        // Modify the returned array
        modified[0] = "MODIFIED CONTENT";

        // Assert: The original array should remain unchanged
        assertNotEquals(modified[0], original[0],
                "Signal image array should be cloned defensively, and modifications to the returned array should not affect the original");
    }
}
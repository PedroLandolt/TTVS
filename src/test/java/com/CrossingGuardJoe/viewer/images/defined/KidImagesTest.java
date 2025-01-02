package com.CrossingGuardJoe.viewer.images.defined;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class KidImagesTest {

    @Test
    void testGetKidStandImage() {
        // Act
        String[] actual = KidImages.getKidStandImage();

        // Assert: Not null
        assertNotNull(actual, "Kid stand image array should never be null");

        // Assert: Not empty
        assertTrue(actual.length > 0, "Kid stand image array should not be empty");

        // Assert: Known content
        Assertions.assertThat(actual).contains("                    $$$$$                         ");

        // Assert: Immutability
        String[] clonedImage = KidImages.getKidStandImage();
        clonedImage[0] = "MODIFIED CONTENT";  // Modifying the clone
        assertNotEquals(clonedImage[0], actual[0], "Kid stand image should return a cloned array");
    }

    @Test
    void testGetKidWalkImage() {
        // Act
        String[] actual = KidImages.getKidWalkImage();

        // Assert: Not null
        assertNotNull(actual, "Kid walk image array should never be null");

        // Assert: Not empty
        assertTrue(actual.length > 0, "Kid walk image array should not be empty");

        // Assert: Known content
        Assertions.assertThat(actual).contains("                    $$$$$                         ");

        // Assert: Immutability
        String[] clonedImage = KidImages.getKidWalkImage();
        clonedImage[0] = "CHANGED CONTENT";
        assertNotEquals(clonedImage[0], actual[0], "Kid walk image should return a cloned array");
    }

    @Test
    void testGetKidHitImage() {
        // Act
        String[] actual = KidImages.getKidHitImage();

        // Assert: Not null
        assertNotNull(actual, "Kid hit image array should never be null");

        // Assert: Not empty
        assertTrue(actual.length > 0, "Kid hit image array should not be empty");

        // Assert: Known content
        Assertions.assertThat(actual).contains("                         $$$$$$$$                         ");

        // Assert: Immutability
        String[] clonedImage = KidImages.getKidHitImage();
        clonedImage[0] = "ALTERED CONTENT";
        assertNotEquals(clonedImage[0], actual[0], "Kid hit image should return a cloned array");
    }
}
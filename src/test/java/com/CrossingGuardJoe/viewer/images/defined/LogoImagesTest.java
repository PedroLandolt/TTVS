package com.CrossingGuardJoe.viewer.images.defined;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogoImagesTest {

    @Test
    void testGetLogoImage() {
        // Act
        String[] actual = LogoImages.getLogoGameImage();

        // Assert: Not null
        assertNotNull(actual, "Logo image array should never be null");

        // Assert: Not empty
        assertTrue(actual.length > 0, "Logo image array should not be empty");

        // Assert: Known content
        Assertions.assertThat(actual).contains("$$$$$$$$WWWWWWWWWWW$$$$        $$WWWWWWWWWWW$$$$$$$$$$$   $WWWWWWWWWWWW$$$$$$$$$$        $$WWWWWWWWWWWW$$$$$$$$$$   $WWWWWWWWWWWW$$$$$$$$$$$$$$$$$$$$WWWWWWWWWWWW$$$$$$$$$$   $WWWWWWWWWWWW$$$$$$$$WWWWWWWWWWWWWWWWW$$$$$$$$$$$$$$$$$   $WWWWWWWWWWWW$$$$$$$$$$   $WWWWWWWWWWWW$$$$$$$$$$$$$$$$  $$$$$$$$$$$$$$$                 $$WWWWWWWWWWW$$$$$$$$$$    $WWWWWWWWWWW$$$$$$$$$$$        $$WWWWWWWWWWW$$$$$$$$$$    $WWWWWWWWWWWW$$$$$$$$$$$$$$$$$$$$$$$$$$$$$             ");

        // Assert: Immutability
        String[] clonedImage = LogoImages.getLogoGameImage();
        clonedImage[0] = "MODIFIED CONTENT";  // Modifying the clone
        assertNotEquals(clonedImage[0], actual[0], "Logo image should return a cloned array");
    }
}

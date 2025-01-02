package com.CrossingGuardJoe.viewer.images.defined;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class JoeImagesTest {

    @Test
    void testGetJoeStandImage() {
        // Act
        String[] actual = JoeImages.getJoeStandImage();

        // Assert: Not null
        assertNotNull(actual, "Joe stand image array should never be null");

        // Assert: Correct length
        assertTrue(actual.length > 0, "Joe stand image array should not be empty");

        // Assert: Known content
        Assertions.assertThat(actual).contains("                                    $$$$                                        ");

        // Assert: Immutability (clone check)
        String[] newImage = JoeImages.getJoeStandImage();
        newImage[0] = "MODIFIED ROW";
        assertNotEquals(newImage[0], actual[0], "Joe stand image should return a cloned array");
    }

    @Test
    void testGetJoeWalkleftImage() {
        // Act
        String[] actual = JoeImages.getJoeWalkleftImage();

        // Assert: Not null
        assertNotNull(actual, "Joe walk left image array should never be null");

        // Assert: Correct length
        assertTrue(actual.length > 0, "Joe walk left image array should not be empty");

        // Assert: Known content
        Assertions.assertThat(actual).contains("      $*********$$$$                               $$$!!!!!!!$$                 ");

        // Assert: Immutability
        String[] newImage = JoeImages.getJoeWalkleftImage();
        newImage[0] = "MODIFIED";
        assertNotEquals(newImage[0], actual[0], "Joe walk left image should return a cloned array");
    }

    @Test
    void testGetJoeWalkrightImage() {
        String[] actual = JoeImages.getJoeWalkrightImage();

        assertNotNull(actual, "Joe walk right image array should never be null");
        assertTrue(actual.length > 0, "Joe walk right image array should not be empty");
        Assertions.assertThat(actual).contains("               $>>>>$$        $$$$WWWWWWWWWWWWW$$+$        $$>>>$$              ");

        String[] newImage = JoeImages.getJoeWalkrightImage();
        newImage[0] = "CHANGED";
        assertNotEquals(newImage[0], actual[0], "Joe walk right image should return a cloned array");
    }

    @Test
    void testGetJoeWalksecondhalfImage() {
        String[] actual = JoeImages.getJoeWalksecondhalfImage();

        assertNotNull(actual, "Joe walk second half image array should never be null");
        assertTrue(actual.length > 0, "Joe walk second half image array should not be empty");
        Assertions.assertThat(actual).contains("                                $$$<<<<$<<<$<<$$                                ");

        String[] newImage = JoeImages.getJoeWalksecondhalfImage();
        newImage[0] = "ALTERED";
        assertNotEquals(newImage[0], actual[0], "Joe walk second half image should return a cloned array");
    }

    @Test
    void testGetJoePassImage() {
        String[] actual = JoeImages.getJoePassImage();

        assertNotNull(actual, "Joe pass image array should never be null");
        assertTrue(actual.length > 0, "Joe pass image array should not be empty");
        Assertions.assertThat(actual).contains("                           $$$$$*****$$   $$*******$$                           ");

        String[] newImage = JoeImages.getJoePassImage();
        newImage[0] = "MODIFIED CONTENT";
        assertNotEquals(newImage[0], actual[0], "Joe pass image should return a cloned array");
    }

    @Test
    void testGetJoeStopImage() {
        String[] actual = JoeImages.getJoeStopImage();

        assertNotNull(actual, "Joe stop image array should never be null");
        assertTrue(actual.length > 0, "Joe stop image array should not be empty");
        Assertions.assertThat(actual).contains("                            $<$<<<$$<<<$<<<$<<<<$      $RRRR$WWW$RRRRRRRRRRR$   ");

        String[] newImage = JoeImages.getJoeStopImage();
        newImage[0] = "CHANGED";
        assertNotEquals(newImage[0], actual[0], "Joe stop image should return a cloned array");
    }

    @Test
    void testGetJoeHitleftImage() {
        String[] actual = JoeImages.getJoeHitleftImage();

        assertNotNull(actual, "Joe hit left image array should never be null");
        assertTrue(actual.length > 0, "Joe hit left image array should not be empty");
        Assertions.assertThat(actual).contains("                              $!!!!!!!!!!!!!!!!!!!!!!!$********$!!!!!!!!!$*******$");

        String[] newImage = JoeImages.getJoeHitleftImage();
        newImage[0] = "CHANGED ROW";
        assertNotEquals(newImage[0], actual[0], "Joe hit left image should return a cloned array");
    }

    @Test
    void testGetJoeHitrightImage() {
        String[] actual = JoeImages.getJoeHitrightImage();

        assertNotNull(actual, "Joe hit right image array should never be null");
        assertTrue(actual.length > 0, "Joe hit right image array should not be empty");
        Assertions.assertThat(actual).contains("$*******$$$$      $*******$$  $$!$$$++++++++++++$$!$                              ");

        String[] newImage = JoeImages.getJoeHitrightImage();
        newImage[0] = "MODIFIED!";
        assertNotEquals(newImage[0], actual[0], "Joe hit right image should return a cloned array");
    }
}
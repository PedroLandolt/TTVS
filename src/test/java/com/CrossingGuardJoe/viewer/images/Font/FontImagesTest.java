package com.CrossingGuardJoe.viewer.images.Font;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FontImagesTest {

    @Test
    void testGetSpaceImage() {
        // Act
        String[] spaceImage = FontImages.getSpaceImage();

        // Assert
        assertNotNull(spaceImage, "The SPACE array should not be null.");
        assertEquals(16, spaceImage.length, "The SPACE image should have 16 rows.");
        assertArrayEquals(new String[]{
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                ",
                "                "
        }, spaceImage, "The content of the SPACE image does not match the expected values.");
    }

    @Test
    void testGetNumbersImage() {
        // Act
        String[] numbersImage = FontImages.getNumbersImage();

        // Assert
        assertNotNull(numbersImage, "The NUMBERS array should not be null.");
        assertEquals(160, numbersImage.length, "The NUMBERS image should have 160 rows.");
        assertEquals("  $$$$$$$$$     ", numbersImage[0],
                "The first row of NUMBERS image does not match the expected value.");
        assertEquals("  $$$$$$$$$     ", numbersImage[159],
                "The last row of NUMBERS image does not match the expected value.");
    }

    @Test
    void testGetAlphabetImage() {
        // Act
        String[] alphabetImage = FontImages.getAlphabetImage();

        // Assert
        assertNotNull(alphabetImage, "The ALPHABET array should not be null.");
        assertEquals(416, alphabetImage.length, "The ALPHABET image should have 416 rows.");
        assertEquals("   $$$$$$$      ", alphabetImage[0],
                "The first row of the ALPHABET image does not match the expected value.");
        assertEquals("$$$$$$$$$$$$    ", alphabetImage[415],
                "The last row of the ALPHABET image does not match the expected value.");
    }

    @Test
    void testCloneIndependenceForSpaceImage() {
        // Act
        String[] spaceImageOriginal = FontImages.getSpaceImage();
        String[] spaceImageClone = FontImages.getSpaceImage();

        // Modify the clone
        spaceImageClone[0] = "Modified Row";

        // Assert
        assertNotNull(spaceImageOriginal, "The original SPACE array should not be null.");
        assertNotEquals(spaceImageOriginal[0], spaceImageClone[0],
                "Modifying the clone should not affect the original SPACE array.");
    }

    @Test
    void testCloneIndependenceForNumbersImage() {
        // Act
        String[] numbersImageOriginal = FontImages.getNumbersImage();
        String[] numbersImageClone = FontImages.getNumbersImage();

        // Modify the clone
        numbersImageClone[0] = "Modified Row";

        // Assert
        assertNotNull(numbersImageOriginal, "The original NUMBERS array should not be null.");
        assertNotEquals(numbersImageOriginal[0], numbersImageClone[0],
                "Modifying the clone should not affect the original NUMBERS array.");
    }

    @Test
    void testCloneIndependenceForAlphabetImage() {
        // Act
        String[] alphabetImageOriginal = FontImages.getAlphabetImage();
        String[] alphabetImageClone = FontImages.getAlphabetImage();

        // Modify the clone
        alphabetImageClone[0] = "Modified Row";

        // Assert
        assertNotNull(alphabetImageOriginal, "The original ALPHABET array should not be null.");
        assertNotEquals(alphabetImageOriginal[0], alphabetImageClone[0],
                "Modifying the clone should not affect the original ALPHABET array.");
    }
}
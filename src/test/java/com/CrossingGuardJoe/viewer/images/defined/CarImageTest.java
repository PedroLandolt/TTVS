package com.CrossingGuardJoe.viewer.images.defined;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarImageTest {

    /*@Test
    void testGetCarImageReturnsCorrectContent() {
        // Act
        String[] carImage = CarImage.getCarImage();

        // Assert
        // Validate the content of the returned array
        assertNotNull(carImage, "Car image array must not be null.");
        assertEquals(119, carImage.length, "The car image array should have 119 elements.");
        assertTrue(carImage[0].contains("$$$$"), "The first line of the car image must contain dollar signs.");
        assertTrue(carImage[1].contains("$@@@@@@@@@"), "The second line should begin with '@'.");
        assertTrue(carImage[carImage.length - 1].contains("$$$$"), "The last line must contain dollar signs.");
    }

    @Test
    void testGetCarImageReturnsClone() {
        // Act
        String[] carImage1 = CarImage.getCarImage();
        String[] carImage2 = CarImage.getCarImage();

        // Modify the cloned array
        carImage1[0] = "Modified Line";

        // Assert
        // Ensure that modifying the cloned array does not change the original or other clones
        assertEquals(119, carImage2.length, "The car image array should remain unchanged with 119 elements.");
        assertNotEquals(carImage1[0], carImage2[0], "Cloned array must not affect the original or other clones.");
        assertTrue(carImage2[0].contains("$$$$"), "The first element of the original clone must still contain dollar signs.");
    }*/

    @Test
    void testCarImageArrayIsImmutable() {
        // Get the original clone
        String[] carImage = CarImage.getCarImage();

        // Attempt to change the returned array
        carImage[0] = "New Line";

        // Retrieve the array again
        String[] newCarImage = CarImage.getCarImage();

        // Assert
        // Ensure the original content has not been altered
        assertNotEquals("New Line", newCarImage[0], "Changing the returned array must not affect the original.");
    }

    @Test
    void testCarArrayContentsDoNotChange() {
        // Get two clones
        String[] carImage1 = CarImage.getCarImage();
        String[] carImage2 = CarImage.getCarImage();

        // Validate that the content is identical between clones
        assertArrayEquals(carImage1, carImage2, "Each call to getCarImage() must return an identical clone.");
    }
}
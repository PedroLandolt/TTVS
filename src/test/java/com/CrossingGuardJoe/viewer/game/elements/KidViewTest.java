package com.CrossingGuardJoe.viewer.game.elements;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.game.elements.Kid;
import com.CrossingGuardJoe.viewer.images.defined.KidImages;
import com.CrossingGuardJoe.viewer.images.defined.ToolImages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class KidViewTest {

    private KidView kidView;

    @Mock
    private Kid kid; // Mock the Kid class

    @Mock
    private GUI gui; // Mock the GUI class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        kidView = new KidView(); // Instantiate class under test
    }

    @Test
    void testDrawWhenKidIsHit() {
        // Arrange
        when(kid.getIsHit()).thenReturn(true);

        // Act
        kidView.draw(kid, gui);

        // Assert
        verify(gui).drawImage(kid.getPosition(), KidImages.getKidHitImage());
        verifyNoMoreInteractions(gui); // Ensure no other images are drawn
    }


    @Test
    void testDrawWhenKidIsSelected() {
        // Arrange
        when(kid.getIsHit()).thenReturn(false); // Ensure the "isHit" branch is skipped
        when(kid.isSelected()).thenReturn(true); // Enter the "isSelected" branch
        when(kid.getWalkingState()).thenReturn(false); // Ensure walking state is skipped
        Position kidPosition = new Position(10, 10); // Kid's position
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        kidView.draw(kid, gui);

        // Assert
        verify(gui).drawImage(
                new Position(10 + 7, 10 - 20), // Matches logic in KidView
                ToolImages.getArrowDownImage() // Arrow image expected during selection
        );
    }

    @Test
    void testDrawWhenKidIsWalkingFirstHalfOfMovement() {
        // Arrange
        when(kid.getIsHit()).thenReturn(false);
        when(kid.isSelected()).thenReturn(false);
        when(kid.getWalkingState()).thenReturn(true);
        when(kid.isFirstHalfOfMovement()).thenReturn(true);
        Position kidPosition = new Position(5, 5);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        kidView.draw(kid, gui);

        // Assert
        verify(gui).drawImage(kidPosition, KidImages.getKidWalkImage());
        verify(kid).setFirstHalfOfMovement(false); // Verify the toggle
        verifyNoMoreInteractions(gui);
    }

    @Test
    void testDrawWhenKidIsWalkingSecondHalfOfMovement() {
        // Arrange
        when(kid.getIsHit()).thenReturn(false);
        when(kid.isSelected()).thenReturn(false);
        when(kid.getWalkingState()).thenReturn(true);
        when(kid.isFirstHalfOfMovement()).thenReturn(false);
        Position kidPosition = new Position(5, 5);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        kidView.draw(kid, gui);

        // Assert
        verify(gui).drawImage(kidPosition, KidImages.getKidStandImage());
        verify(kid).setFirstHalfOfMovement(true); // Verify the toggle
        verifyNoMoreInteractions(gui);
    }

    @Test
    void testDrawDefaultState() {
        // Arrange
        when(kid.getIsHit()).thenReturn(false);
        when(kid.isSelected()).thenReturn(false);
        when(kid.getWalkingState()).thenReturn(false);
        Position kidPosition = new Position(7, 8);
        when(kid.getPosition()).thenReturn(kidPosition);

        // Act
        kidView.draw(kid, gui);

        // Assert
        verify(gui).drawImage(kidPosition, KidImages.getKidStandImage());
        verifyNoMoreInteractions(gui);
    }
}
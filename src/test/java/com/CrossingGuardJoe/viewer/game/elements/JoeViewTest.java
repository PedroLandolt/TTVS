package com.CrossingGuardJoe.viewer.game.elements;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.viewer.images.defined.JoeImages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

// JUnit 5 Test Class
class JoeViewTest {

    private JoeView joeView;

    @Mock
    private Joe joe; // Mock the Joe class

    @Mock
    private GUI gui; // Mock the GUI class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        joeView = new JoeView(); // Instantiate the class we are testing
    }

    @Test
    void testDrawWhenJoeIsHitAndHitLeft() {
        // Prepare mock behavior
        when(joe.getIsHit()).thenReturn(true);
        when(joe.getHitLeft()).thenReturn(true);

        // Call the method under test
        joeView.draw(joe, gui);

        // Verify interactions
        verify(gui).drawImage(joe.getPosition(), JoeImages.getJoeHitleftImage());
        verify(joe).isNotHit();
        verifyNoMoreInteractions(gui); // No unnecessary GUI calls
    }

    @Test
    void testDrawWhenJoeIsHitAndHitRight() {
        when(joe.getIsHit()).thenReturn(true);
        when(joe.getHitLeft()).thenReturn(false);

        joeView.draw(joe, gui);

        verify(gui).drawImage(joe.getPosition(), JoeImages.getJoeHitrightImage());
        verify(joe).isNotHit();
        verifyNoMoreInteractions(gui);
    }

    @Test
    void testDrawWhenJoeIsWalkingFirstHalfLeft() {
        when(joe.getIsHit()).thenReturn(false);
        when(joe.getIsWalkingState()).thenReturn(true);
        when(joe.isFirstHalfOfMovement()).thenReturn(true);
        when(joe.getIsWalkingToLeft()).thenReturn(true);

        joeView.draw(joe, gui);

        verify(gui).drawImage(joe.getPosition(), JoeImages.getJoeWalkleftImage());
        verify(joe).setFirstHalfOfMovement(false); // Validates the toggle
        verifyNoMoreInteractions(gui);
    }

    @Test
    void testDrawWhenJoeIsWalkingFirstHalfRight() {
        when(joe.getIsHit()).thenReturn(false);
        when(joe.getIsWalkingState()).thenReturn(true);
        when(joe.isFirstHalfOfMovement()).thenReturn(true);
        when(joe.getIsWalkingToLeft()).thenReturn(false);

        joeView.draw(joe, gui);

        verify(gui).drawImage(joe.getPosition(), JoeImages.getJoeWalkrightImage());
        verify(joe).setFirstHalfOfMovement(false);
        verifyNoMoreInteractions(gui);
    }

    @Test
    void testDrawWhenJoeIsWalkingSecondHalf() {
        when(joe.getIsHit()).thenReturn(false);
        when(joe.getIsWalkingState()).thenReturn(true);
        when(joe.isFirstHalfOfMovement()).thenReturn(false);

        joeView.draw(joe, gui);

        verify(gui).drawImage(joe.getPosition(), JoeImages.getJoeWalksecondhalfImage());
        verify(joe).setFirstHalfOfMovement(true);
        verifyNoMoreInteractions(gui);
    }

    @Test
    void testDrawWhenJoeIsRaisingStopSign() {
        when(joe.getIsHit()).thenReturn(false);
        when(joe.getIsWalkingState()).thenReturn(false);
        when(joe.getIsRaisingStopSign()).thenReturn(true);

        joeView.draw(joe, gui);

        verify(gui).drawImage(joe.getPosition(), JoeImages.getJoeStopImage());
        verifyNoMoreInteractions(gui);
    }

    @Test
    void testDrawWhenJoeIsPassSign() {
        when(joe.getIsHit()).thenReturn(false);
        when(joe.getIsWalkingState()).thenReturn(false);
        when(joe.getIsRaisingStopSign()).thenReturn(false);
        when(joe.getIsPassSign()).thenReturn(true);

        joeView.draw(joe, gui);

        verify(gui).drawImage(joe.getPosition(), JoeImages.getJoePassImage());
        verifyNoMoreInteractions(gui);
    }

    @Test
    void testDrawDefaultState() {
        when(joe.getIsHit()).thenReturn(false);
        when(joe.getIsWalkingState()).thenReturn(false);
        when(joe.getIsRaisingStopSign()).thenReturn(false);
        when(joe.getIsPassSign()).thenReturn(false);

        joeView.draw(joe, gui);

        verify(gui).drawImage(joe.getPosition(), JoeImages.getJoeStandImage());
        verifyNoMoreInteractions(gui);
    }
}
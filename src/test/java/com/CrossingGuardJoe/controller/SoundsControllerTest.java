package com.CrossingGuardJoe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static com.CrossingGuardJoe.controller.Sounds.SFX;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SoundsControllerTest {

    private SoundsController soundsController;
    private Sounds mockSound;

    @BeforeEach
    void setUp() {
        // Get the SoundsController singleton instance
        soundsController = SoundsController.getInstance();

        // Mock the Sounds class instance
        mockSound = mock(Sounds.class);

        // Inject the mocked Sounds instance into all fields of SoundsController
        String[] soundFields = {
                "menuBgm", "gameBgm", "customizeBgm", "instructionsBgm", // Background music fields
                "select", "enter", "flipPage",                           // Menu sound effects
                "levelUp", "joePass1", "joePass2", "joeStop", "joeHit", // Joe's sound effects
                "kidWalk1", "kidStop1", "kidStop2", "kidHit", "kidScore", // Kid sound effects
                "carBreak", "gameOver", "victoryBgm"                    // Game sound effects
        };

        // Inject mocks into SoundsController's fields AFTER initialization
        try {
            for (String fieldName : soundFields) {
                Field field = SoundsController.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(soundsController, mockSound);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set up mock for field: " + e.getMessage());
        }
    }

    @Test
    void testGetInstance() {
        // Verify that the singleton instance is always the same
        SoundsController instance1 = SoundsController.getInstance();
        SoundsController instance2 = SoundsController.getInstance();

        assertSame(instance1, instance2, "SoundsController.getInstance() should return the same instance.");
    }

    @Test
    void testPlay() {
        // Act & Assert for each branch in the play(SFX sfx) switch
        soundsController.play(SFX.MENUBGM);
        verify(mockSound, times(1)).loop(0.2f); // MENUBGM should call loop with 0.2f

        reset(mockSound); // Reset mock interactions for the next case
        soundsController.play(SFX.GAMEBGM);
        verify(mockSound, times(1)).loop(0.15f); // GAMEBGM should call loop with 0.15f

        reset(mockSound);
        soundsController.play(SFX.CUSTOMIZEBGM);
        verify(mockSound, times(1)).loop(1f); // CUSTOMIZEBGM should call loop with 1f

        reset(mockSound);
        soundsController.play(SFX.INSTRUCTIONSBGM);
        verify(mockSound, times(1)).loop(0.5f); // INSTRUCTIONSBGM should call loop with 0.5f

        reset(mockSound);
        soundsController.play(SFX.SELECT);
        verify(mockSound, times(1)).play(0.5f); // SELECT should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.ENTER);
        verify(mockSound, times(1)).play(0.5f); // ENTER should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.FLIPPAGE);
        verify(mockSound, times(1)).play(0.7f); // FLIPPAGE should call play with 0.7f

        reset(mockSound);
        soundsController.play(SFX.LEVELUP);
        verify(mockSound, times(1)).play(1f); // LEVELUP should call play with 1f

        reset(mockSound);
        soundsController.play(SFX.JOEPASS1);
        verify(mockSound, times(1)).play(0.5f); // JOEPASS1 should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.JOEPASS2);
        verify(mockSound, times(1)).play(0.5f); // JOEPASS2 should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.JOESTOP);
        verify(mockSound, times(1)).play(0.5f); // JOESTOP should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.JOEHIT);
        verify(mockSound, times(1)).play(0.5f); // JOEHIT should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.KIDWALK1);
        verify(mockSound, times(1)).play(0.5f); // KIDWALK1 should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.KIDSTOP1);
        verify(mockSound, times(1)).play(0.5f); // KIDSTOP1 should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.KIDSTOP2);
        verify(mockSound, times(1)).play(0.5f); // KIDSTOP2 should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.KIDHIT);
        verify(mockSound, times(1)).play(0.5f); // KIDHIT should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.KIDSCORE);
        verify(mockSound, times(1)).play(0.8f); // KIDSCORE should call play with 0.8f

        reset(mockSound);
        soundsController.play(SFX.CARBREAK);
        verify(mockSound, times(1)).play(0.5f); // CARBREAK should call play with default volume

        reset(mockSound);
        soundsController.play(SFX.GAMEOVER);
        verify(mockSound, times(1)).play(1f); // GAMEOVER should call play with 1f

        reset(mockSound);
        soundsController.play(SFX.VICTORYBGM);
        verify(mockSound, times(1)).loop(1f); // VICTORYBGM should call loop with 1f
    }

    @Test
    void testPause() throws Exception {

        reset(mockSound);

        // Arrange unique mocks
        Sounds mockGameBgm = mock(Sounds.class);
        Sounds mockVictoryBgm = mock(Sounds.class);

        // Inject mocks into the fields
        Field gameBgmField = SoundsController.class.getDeclaredField("gameBgm");
        gameBgmField.setAccessible(true);
        gameBgmField.set(soundsController, mockGameBgm);

        Field victoryBgmField = SoundsController.class.getDeclaredField("victoryBgm");
        victoryBgmField.setAccessible(true);
        victoryBgmField.set(soundsController, mockVictoryBgm);

        // Act: Trigger `pause`
        soundsController.pause(SFX.GAMEBGM);
        soundsController.pause(SFX.VICTORYBGM);

        // Assert: Verify only the correct mocks were paused
        verify(mockGameBgm, times(1)).pause();
        verify(mockVictoryBgm, times(1)).pause();

        // Ensure no interaction with global mockSound
        verify(mockSound, never()).pause();
    }

    @Test
    void testStop() throws Exception {
        // Reset the mockSound interactions
        reset(mockSound);

        // Act: Stop multiple fields
        soundsController.stop(SFX.MENUBGM);
        soundsController.stop(SFX.GAMEBGM);
        soundsController.stop(SFX.VICTORYBGM);
        soundsController.stop(SFX.CUSTOMIZEBGM);
        soundsController.stop(SFX.INSTRUCTIONSBGM);
        soundsController.stop(SFX.CARBREAK);

        // Assert: mockSound should have been invoked
        verify(mockSound, times(6)).stop();
    }

    @Test
    void testPlayRandom() {
        // Arrange: Inject unique mock objects for joePass1 and joePass2
        Sounds mockJoePass1 = mock(Sounds.class);
        Sounds mockJoePass2 = mock(Sounds.class);

        try {
            Field joePass1Field = SoundsController.class.getDeclaredField("joePass1");
            joePass1Field.setAccessible(true);
            joePass1Field.set(soundsController, mockJoePass1);

            Field joePass2Field = SoundsController.class.getDeclaredField("joePass2");
            joePass2Field.setAccessible(true);
            joePass2Field.set(soundsController, mockJoePass2);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to inject mock objects for playRandom test: " + e.getMessage());
        }

        // Act: Call `playRandom` multiple times
        soundsController.playRandom(SFX.JOEPASS1, SFX.JOEPASS2);

        // Assert: Ensure one of the mock objects' `play` methods is called randomly
        verify(mockJoePass1, atMostOnce()).play(0.5f);
        verify(mockJoePass2, atMostOnce()).play(0.5f);
    }
}
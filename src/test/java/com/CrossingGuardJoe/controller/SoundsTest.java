package com.CrossingGuardJoe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SoundsTest {

    private Sounds sounds; // Subject under test
    private Clip mockClip;

    @BeforeEach
    void setUp() throws Exception {
        // Mock the Clip object
        mockClip = mock(Clip.class);

        // Create a Sounds instance
        sounds = new Sounds("");

        // Inject the mocked Clip object into the Sounds instance with reflection
        Field soundField = Sounds.class.getDeclaredField("sound");
        soundField.setAccessible(true); // Make the private field accessible
        soundField.set(sounds, mockClip); // Assign the mocked Clip
    }

    @Test
    void testPlayWhenClipIsNotRunning() {
        // Arrange: Simulate that the Clip is NOT running
        when(mockClip.isRunning()).thenReturn(false);

        // Act: Call the play method
        sounds.play(0.5f);

        // Assert: Ensure playback starts with frame reset
        verify(mockClip).setFramePosition(0);
        verify(mockClip).start();
    }

    @Test
    void testPlayWhenClipIsRunning() {
        // Arrange: Simulate that the Clip is already running
        when(mockClip.isRunning()).thenReturn(true);

        // Act: Call the play method
        sounds.play(0.5f);

        // Assert: Ensure no new playback starts
        verify(mockClip, never()).setFramePosition(anyInt());
        verify(mockClip, never()).start();
    }

    @Test
    void testPause() {
        // Act: Call the pause method
        sounds.pause();

        // Assert: Ensure that playback was stopped
        verify(mockClip).stop();
    }

    @Test
    void testStop() {
        // Act: Call the stop method
        sounds.stop();

        // Assert: Ensure frame position is reset and playback was stopped
        verify(mockClip).setFramePosition(0);
        verify(mockClip).stop();
    }

    @Test
    void testLoop() {
        // Act: Call the loop method
        sounds.loop(0.7f);

        // Assert: Ensure the clip was set to loop continuously
        verify(mockClip).loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Test
    void testSetVolumeWithSupportedControl() {
        // Arrange: Mock volume control behavior
        FloatControl mockControl = mock(FloatControl.class);
        when(mockClip.isControlSupported(FloatControl.Type.MASTER_GAIN)).thenReturn(true);
        when(mockClip.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(mockControl);

        // Act: Set the volume
        Sounds.setVolume(mockClip, 0.8f);

        // Assert: Ensure the volume was adjusted
        verify(mockControl).setValue(anyFloat());
    }

    @Test
    void testSetVolumeWhenControlUnsupported() {
        // Arrange: Simulate unsupported control
        when(mockClip.isControlSupported(FloatControl.Type.MASTER_GAIN)).thenReturn(false);

        // Act: Attempt to set the volume
        Sounds.setVolume(mockClip, 0.8f);

        // Assert: Ensure no control interaction occurs
        verify(mockClip, never()).getControl(any());
    }

    @Test
    void testSFXEnumValues() {
        // Act: Retrieve all enum values
        Sounds.SFX[] values = Sounds.SFX.values();

        // Assert: Verify correct number of values and specific enum constant
        assertEquals(20, values.length, "Sounds.SFX enum should contain 20 values.");
        assertNotNull(Sounds.SFX.valueOf("MENUBGM"), "MENUBGM should be a valid enum constant.");
    }

    @Test
    void testConstructorWithInvalidFile() {
        // Act: Create a Sounds instance with an invalid file path
        Sounds invalidSound = new Sounds("invalid_file");

        // Assert: Ensure the Clip field is null for invalid file paths
        try {
            Field soundField = Sounds.class.getDeclaredField("sound");
            soundField.setAccessible(true);
            assertNull(soundField.get(invalidSound), "Clip should be null for invalid sound paths.");
        } catch (Exception e) {
            fail("Unexpected exception during test execution: " + e.getMessage());
        }
    }

    /*@Test
    void testPlayWhenClipIsNull() throws Exception {
        // Arrange: Set the "sound" field to null
        Field soundField = Sounds.class.getDeclaredField("sound");
        soundField.setAccessible(true);
        soundField.set(sounds, null);

        // Act & Assert: Ensure play does not throw any exception
        assertDoesNotThrow(() -> sounds.play(0.6f), "Play should handle null Clip gracefully.");
    }

    @Test
    void testPauseWhenClipIsNull() throws Exception {
        // Arrange: Set the "sound" field to null
        Field soundField = Sounds.class.getDeclaredField("sound");
        soundField.setAccessible(true);
        soundField.set(sounds, null);

        // Act & Assert: Ensure pause does not throw any exception
        assertDoesNotThrow(() -> sounds.pause(), "Pause should handle null Clip gracefully.");
    }

    @Test
    void testStopWhenClipIsNull() throws Exception {
        // Arrange: Set the "sound" field to null
        Field soundField = Sounds.class.getDeclaredField("sound");
        soundField.setAccessible(true);
        soundField.set(sounds, null);

        // Act & Assert: Ensure stop does not throw any exception
        assertDoesNotThrow(() -> sounds.stop(), "Stop should handle null Clip gracefully.");
    }

    @Test
    void testLoopWhenClipIsNull() throws Exception {
        // Arrange: Set the "sound" field to null
        Field soundField = Sounds.class.getDeclaredField("sound");
        soundField.setAccessible(true);
        soundField.set(sounds, null);

        // Act & Assert: Ensure loop does not throw any exception
        assertDoesNotThrow(() -> sounds.loop(0.9f), "Loop should handle null Clip gracefully.");
    }*/

    @Test
    void testLoadSoundWithNullPath() {
        // Act: Create a Sounds instance with a null path
        Sounds nullSound = new Sounds(null);

        // Assert: Ensure the Clip is null
        try {
            Field soundField = Sounds.class.getDeclaredField("sound");
            soundField.setAccessible(true);
            assertNull(soundField.get(nullSound), "Clip should be null for null path.");
        } catch (Exception e) {
            fail("Unexpected exception during test execution: " + e.getMessage());
        }
    }
}
package com.CrossingGuardJoe.viewer;

import com.CrossingGuardJoe.gui.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewerTest {

    private TestViewer testViewer; // Concrete implementation of Viewer for testing
    private GUI mockGUI;          // Mock implementation of GUI

    @BeforeEach
    void setUp() {
        // Create a mock GUI instance
        mockGUI = mock(GUI.class);

        // Create a concrete subclass of Viewer for testing
        testViewer = new TestViewer("Test Model");
    }

    @Test
    void testGetModel() {
        // Act: Retrieve the model from the Viewer
        String model = testViewer.getModel();

        // Assert: Verify that the model instance is correctly returned
        assertEquals("Test Model", model, "getModel() should return the correct model.");
    }

    @Test
    void testDrawMethod() throws IOException {
        // Arrange: Use Mockito to verify order of method calls on GUI
        InOrder inOrder = inOrder(mockGUI);

        // Act: Call the draw method
        testViewer.draw(mockGUI);

        // Assert: Verify the proper sequence of GUI method calls
        inOrder.verify(mockGUI).clearScreen();        // First step
        inOrder.verify(mockGUI).refreshScreen();     // Third step
    }

    @Test
    void testDrawElementsIsCalled() throws IOException {
        // Arrange: Override drawElements() in TestViewer
        TestViewer spyViewer = spy(testViewer);

        // Act: Call the draw method
        spyViewer.draw(mockGUI);

        // Assert: Confirm that drawElements is called during draw()
        verify(spyViewer).drawElements(mockGUI);
    }

    // Concrete subclass of Viewer used for testing
    private static class TestViewer extends Viewer<String> {
        public TestViewer(String model) {
            super(model); // Call the parent constructor
        }

        @Override
        public void drawElements(GUI gui) {
            // No implementation needed for basic testing
        }
    }
}
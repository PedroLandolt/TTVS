package com.CrossingGuardJoe.states;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.controller.Controller;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.viewer.Viewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StateTest {

    // Mock dependencies
    private Controller<Object> mockController;
    private Viewer<Object> mockViewer;
    private GUI mockGUI;
    private Game mockGame;

    // Concrete implementation of State for testing
    private State<Object> state;
    private Object model;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockController = mock(Controller.class);
        mockViewer = mock(Viewer.class);
        mockGUI = mock(GUI.class);
        mockGame = mock(Game.class);

        // Dummy model
        model = new Object(); // Use a simple Object as the model

        // Create a concrete implementation of State for testing
        state = new State<>(model) {
            @Override
            public Controller<Object> getController() {
                return mockController;
            }

            @Override
            public Viewer<Object> getViewer() {
                return mockViewer;
            }
        };
    }

    @Test
    void testConstructorInitializesFieldsCorrectly() {
        // Act & Assert
        assertThat(state.getModel())
                .isSameAs(model)
                .as("State should correctly initialize the model");

        assertThat(state.getController())
                .isSameAs(mockController)
                .as("State should correctly initialize the controller returned by getController()");

        assertThat(state.getViewer())
                .isSameAs(mockViewer)
                .as("State should correctly initialize the viewer returned by getViewer()");
    }

    @Test
    void testGetModelReturnsModel() {
        // Act & Assert
        assertThat(state.getModel())
                .isSameAs(model)
                .as("getModel() should return the model passed to the constructor");
    }

    @Test
    void testStepExecutesProperly() throws IOException {
        // Arrange
        when(mockGUI.getNextAction()).thenReturn(GUI.ACTION.UP); // Mock the next action

        // Act
        state.step(mockGame, mockGUI, 123L);

        // Assert - Ensure all calls are made correctly
        verify(mockGUI, times(1)).getNextAction();
        verify(mockController, times(1)).nextAction(mockGame, GUI.ACTION.UP, 123L);
        verify(mockViewer, times(1)).draw(mockGUI);
    }
}
package com.CrossingGuardJoe;

import com.CrossingGuardJoe.gui.LanternaGUI;
import com.CrossingGuardJoe.states.State;
import com.CrossingGuardJoe.states.menu.MenuState;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {
    private Game game;               // Test instance of Game
    private State mockState;         // Mock State instance

    @BeforeEach
    void setUp() {
        // Mock the State instance
        mockState = mock(State.class);

        try {
            // Initialize the Game instance with its default constructor
            game = new Game();
        } catch (IOException | URISyntaxException | FontFormatException e) {
            throw new RuntimeException("Failed to initialize Game instance for tests", e);
        }
    }

    @Test
    void testConstructor() throws IOException, URISyntaxException, FontFormatException {
        // Assert that Game is initialized correctly
        assertNotNull(game.getCurrentState(), "The initial state should be set.");
        assertInstanceOf(MenuState.class, game.getCurrentState(), "The initial state should be a MenuState.");
    }

    @Test
    void testSetState() {
        // Act: Add a new state
        game.setState(mockState);

        // Assert: Verify state stack has the new state
        assertEquals(mockState, game.getCurrentState(), "Current state should be the newly set state.");
    }

    @Test
    void testPopState() {
        // Arrange: Add a temporary state to the stack
        State originalState = game.getCurrentState();
        game.setState(mockState);

        // Act: Pop the current state
        game.popState();

        // Assert: The previous state should now be the current state
        assertEquals(originalState, game.getCurrentState(), "Current state should be the previous state after pop.");
    }

    @Test
    void testPopStateWhenStackIsEmpty() {
        // Arrange: Clear the stack
        while (game.getCurrentState() != null) {
            game.popState();
        }

        // Act: Attempt to pop when stack is empty
        game.popState();

        // Assert: Current state should still be null, no exceptions thrown
        assertNull(game.getCurrentState(), "Current state should be null when stateStack is empty.");
    }

    @Test
    void testGetHighestScore() {
        // Arrange: Set a high score
        game.setHighestScore(100);

        // Assert: The fetched score matches the set score
        assertEquals(100, game.getHighestScore(), "Highest score should match the set value.");
    }

    @Test
    void testSetHighestScore() {
        // Act: Set a new high score
        game.setHighestScore(500);

        // Assert: Verify the high score is updated
        assertEquals(500, game.getHighestScore(), "Highest score should be updated correctly.");
    }

    @Test
    void testGetHighestLevel() {
        // Arrange: Set the highest level
        game.setHighestLevel(10);

        // Assert: The fetched level matches the set level
        assertEquals(10, game.getHighestLevel(), "Highest level should match the set value.");
    }

    @Test
    void testSetHighestLevel() {
        // Act: Set a new highest level
        game.setHighestLevel(15);

        // Assert: Verify the highest level is updated
        assertEquals(15, game.getHighestLevel(), "Highest level should be updated correctly.");
    }

    @Test
    void testGameBehaviorWithMocks() throws IOException {
        // Arrange: Mock the State and GUI
        State mockState = mock(State.class);
        LanternaGUI mockGUI = mock(LanternaGUI.class);

        // Add the mocked state to the game instance
        game.setState(mockState);

        // Act & Assert: Simulate the behavior of run() through getCurrentState().step(...)
        assertDoesNotThrow(() -> {
            game.getCurrentState().step(game, mockGUI, System.currentTimeMillis());
        });

        // Verify that the mocked state's step() is called
        verify(mockState, times(1)).step(eq(game), eq(mockGUI), anyLong());
    }


    /*@Test
    void testRunMethod() throws Exception {
        // Mock dependencies
        LanternaGUI mockGUI = mock(LanternaGUI.class); // Mock GUI
        State mockState = mock(State.class);          // Mock State

        // Push a mock state onto the stack
        game.setState(mockState);

        // Mock `step` to do nothing but break the loop after one call
        doAnswer(invocation -> {
            game.popState();
            return null;
        }).when(mockState).step(eq(game), eq(mockGUI), anyLong());

        // Use reflection to access the private `run` method
        Method runMethod = Game.class.getDeclaredMethod("run");
        runMethod.setAccessible(true); // Make the private method accessible

        // Invoke the private `run` method
        runMethod.invoke(game);

        // Verify `step` method was called exactly once
        verify(mockState, times(1)).step(eq(game), eq(mockGUI), anyLong());

        // Verify GUI was properly closed
        verify(mockGUI, times(1)).closeScreen();
    }*/

    /*@Test
    void testMainMethod() throws Exception {
        // Mock dependencies
        State mockState = mock(State.class);
        LanternaGUI mockGUI = mock(LanternaGUI.class);

        // Use reflection to spy on the created Game instance inside main()
        Game spyGame = spy(new Game(mockGUI));
        doNothing().when(spyGame).run();

        // Call the `main()` method
        assertDoesNotThrow(() -> Game.main(new String[0]));

        // Verify that `run()` was called
        verify(spyGame, times(1)).run();
    }
*/
    /*@Test
    void testRunWithSpyAndReflection() throws Exception {
        // Spy on game instance
        Game spyGame = spy(game);

        // Mock `currentState` and inject controlled behavior
        State mockState = mock(State.class);
        spyGame.setState(mockState);

        // Use reflection to access and invoke private `run()`
        Method runMethod = Game.class.getDeclaredMethod("run");
        runMethod.setAccessible(true);
        runMethod.invoke(spyGame);

        // Verify the state step and GUI interactions
        verify(mockState, atLeastOnce()).step(eq(spyGame), any(), anyLong());
    }*/

}
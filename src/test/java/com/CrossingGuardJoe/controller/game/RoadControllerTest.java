package com.CrossingGuardJoe.controller.game;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.controller.Sounds;
import com.CrossingGuardJoe.controller.SoundsController;
import com.CrossingGuardJoe.controller.game.elements.CarController;
import com.CrossingGuardJoe.controller.game.elements.JoeController;
import com.CrossingGuardJoe.controller.game.elements.KidController;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.states.menu.GameOverState;
import com.CrossingGuardJoe.states.menu.PauseMenuState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RoadControllerTest {

    private Road roadMock;
    private RoadController roadController;

    private Game gameMock;
    private Joe joeMock;
    private Position joePositionMock;

    private JoeController joeControllerMock;
    private KidController kidControllerMock;
    private CarController carControllerMock;

    private SoundsController soundsControllerMock;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Mocks
        roadMock = mock(Road.class);
        gameMock = mock(Game.class);

        joeMock = mock(Joe.class);
        joePositionMock = mock(Position.class);
        when(joeMock.getPosition()).thenReturn(joePositionMock); // Avoid NullPointerException for Position
        when(roadMock.getJoe()).thenReturn(joeMock);

        // Controller mocks
        joeControllerMock = mock(JoeController.class);
        kidControllerMock = mock(KidController.class);
        carControllerMock = mock(CarController.class);

        // Mock SoundsController singleton
        soundsControllerMock = mock(SoundsController.class);
        Field instanceField = SoundsController.class.getDeclaredField("soundsController");
        instanceField.setAccessible(true);
        instanceField.set(null, soundsControllerMock); // Inject the mocked soundsController

        // Create RoadController instance and inject dependencies using reflection
        roadController = new RoadController(roadMock);
        injectController("joeController", joeControllerMock);
        injectController("kidController", kidControllerMock);
        injectController("carController", carControllerMock);
    }

    private void injectController(String fieldName, Object fieldInstance) throws NoSuchFieldException, IllegalAccessException {
        Field field = RoadController.class.getDeclaredField(fieldName);
        field.setAccessible(true); // Make private field accessible
        field.set(roadController, fieldInstance);
    }

    @Test
    void testConstructorInitializesControllers() throws NoSuchFieldException, IllegalAccessException {
        // Verify that roadController's internal controllers are initialized
        Field joeField = RoadController.class.getDeclaredField("joeController");
        joeField.setAccessible(true);
        assertNotNull(joeField.get(roadController));

        Field kidField = RoadController.class.getDeclaredField("kidController");
        kidField.setAccessible(true);
        assertNotNull(kidField.get(roadController));

        Field carField = RoadController.class.getDeclaredField("carController");
        carField.setAccessible(true);
        assertNotNull(carField.get(roadController));
    }

    @Test
    void testNextAction_CallControllers() throws IOException {
        // Action and time for testing
        GUI.ACTION action = GUI.ACTION.UP;
        long time = 1234L;

        // Call nextAction
        roadController.nextAction(gameMock, action, time);

        // Verify all controllers' nextAction was called
        verify(joeControllerMock, times(1)).nextAction(gameMock, action, time);
        verify(kidControllerMock, times(1)).nextAction(gameMock, action, time);
        verify(carControllerMock, times(1)).nextAction(gameMock, action, time);
    }

    @Test
    void testNextAction_EscAction() throws IOException {
        // ESC action case
        GUI.ACTION action = GUI.ACTION.ESC;

        // Call nextAction
        roadController.nextAction(gameMock, action, 0);

        // Verify SoundsController pauses background music
        verify(soundsControllerMock, times(1)).pause(Sounds.SFX.GAMEBGM);

        // Verify Joe stops walking
        verify(joeMock, times(1)).stopWalking();

        // Verify game state changes to PauseMenuState
        ArgumentCaptor<PauseMenuState> stateCaptor = ArgumentCaptor.forClass(PauseMenuState.class);
        verify(gameMock, times(1)).setState(stateCaptor.capture());
        assertTrue(stateCaptor.getValue() instanceof PauseMenuState);
    }

    @Test
    void testNextAction_JoeLostAllHearts() throws IOException {
        // Set up Joe with 0 hearts
        when(joeMock.getHearts()).thenReturn(0);

        // Call nextAction
        roadController.nextAction(gameMock, GUI.ACTION.LEFT, 0);

        // Verify SoundsController stops certain sounds
        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.GAMEBGM);
        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.CARBREAK);

        // Verify game state changes to GameOverState
        ArgumentCaptor<GameOverState> stateCaptor = ArgumentCaptor.forClass(GameOverState.class);
        verify(gameMock, times(1)).setState(stateCaptor.capture());
        assertTrue(stateCaptor.getValue() instanceof GameOverState);

        // Verify SoundsController plays the Game Over sound
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.GAMEOVER);
    }

    @Test
    void testNextAction_GameVictory() throws IOException {
        // Set up a game victory
        when(roadMock.isGameEnded()).thenReturn(true);
        when(joeMock.getHearts()).thenReturn(5); // Hearts are not zero to prevent conflict

        // Call nextAction
        roadController.nextAction(gameMock, GUI.ACTION.RIGHT, 0);

        // Verify SoundsController stops GAMEBGM once
        verify(soundsControllerMock, times(1)).stop(Sounds.SFX.GAMEBGM);

        // Verify SoundsController plays victory music
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.VICTORYBGM);

        // Verify game state changes to GameOverState
        ArgumentCaptor<GameOverState> gameOverCaptor = ArgumentCaptor.forClass(GameOverState.class);
        verify(gameMock, times(1)).setState(gameOverCaptor.capture());
        assertTrue(gameOverCaptor.getValue() instanceof GameOverState);
    }

}
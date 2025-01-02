package com.CrossingGuardJoe.controller.game.elements;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.controller.Sounds;
import com.CrossingGuardJoe.controller.SoundsController;
import com.CrossingGuardJoe.controller.game.AuxCheckRange;
import com.CrossingGuardJoe.model.game.elements.Car;
import com.CrossingGuardJoe.model.game.Road;
import com.CrossingGuardJoe.model.game.elements.Joe;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.game.elements.Kid;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

public class JoeControllerTest {

    private JoeController joeController;

    @Mock private Road road;
    @Mock private Joe joe;

    private static final Position DEFAULT_POSITION = new Position(100, 100);

    private MockedStatic<SoundsController> soundsControllerMockedStatic;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(road.getJoe()).thenReturn(joe);
        when(road.getCars()).thenReturn(List.of());
        when(joe.getPosition()).thenReturn(DEFAULT_POSITION);
        joeController = new JoeController(road);

        // Mock static methods for SoundsController
        soundsControllerMockedStatic = mockStatic(SoundsController.class);
        when(SoundsController.getInstance()).thenReturn(mock(SoundsController.class));
    }

    @AfterEach
    public void tearDown() {
        // Close the static mock after each test
        soundsControllerMockedStatic.close();
    }

    @Test
    public void testMoveJoeLeft() {
        joeController.moveJoeLeft();
        verify(joe).setPosition(any(Position.class));
        verify(joe).startWalkingToLeft();
    }

    @Test
    public void testMoveJoeRight() {
        joeController.moveJoeRight();
        verify(joe).setPosition(any(Position.class));
        verify(joe).startWalkingToRight();
    }

    @Test
    public void testMoveJoeHit() {
        joeController.moveJoeLeftHit();
        verify(joe).setPosition(any(Position.class));
        verify(joe).countHitPoints();
    }

    @Test
    public void testJoeStopSign() {
        joeController.joeStopSign();
        verify(joe).startRaisingStopSign();
    }

    @Test
    public void testJoePassSign() {
        joeController.joePassSign();
        verify(joe).startRaisingPassSign();
    }

    @Test
    public void testJoeActionPass() {
        joeController.JoeAction(DEFAULT_POSITION, 'p');
        verify(joe).startRaisingPassSign();
    }

    @Test
    public void testJoeActionStop() {
        joeController.JoeAction(DEFAULT_POSITION, 's');
        verify(joe).startRaisingStopSign();
    }

    @Test
    public void testJoeActionLeft() {
        joeController.JoeAction(DEFAULT_POSITION, 'l');
        verify(joe).startWalkingToLeft();
    }

    @Test
    public void testJoeActionRight() {
        joeController.JoeAction(DEFAULT_POSITION, 'r');
        verify(joe).startWalkingToRight();
    }

    private void testNextAction(GUI.ACTION action, Consumer<Joe> verifyAction) {
        Game gameMock = mock(Game.class);
        when(joe.getPosition()).thenReturn(DEFAULT_POSITION);
        when(road.getJoe()).thenReturn(joe);

        JoeController spyJoeController = spy(joeController);
        spyJoeController.nextAction(gameMock, GUI.ACTION.NONE, 1000); // Call with dummy action first
        spyJoeController.nextAction(gameMock, action, 1000); // Call with the actual action to test

        // Verify that the expected method was called
        verifyAction.accept(joe); // This will be the actual verification
    }

    @Test
    public void testNextActionLeft() {
        testNextAction(GUI.ACTION.LEFT, joe -> {
            verify(joe).startWalkingToLeft();
        });
    }

    @Test
    public void testNextActionRight() {
        testNextAction(GUI.ACTION.RIGHT, joe -> {
            verify(joe).startWalkingToRight();
        });
    }

    @Test
    public void testNextActionUp() {
        Joe joeMock = mock(Joe.class);

        Road roadMock = mock(Road.class);
        when(roadMock.getJoe()).thenReturn(joeMock);
        JoeController joeController = new JoeController(roadMock);

        // Close any existing static mock registration
        if (soundsControllerMockedStatic != null) {
            soundsControllerMockedStatic.close();
        }

        // Mock static methods for SoundsController
        soundsControllerMockedStatic = mockStatic(SoundsController.class);
        SoundsController soundsControllerMock = mock(SoundsController.class);
        when(SoundsController.getInstance()).thenReturn(soundsControllerMock);

        joeController.nextAction(null, GUI.ACTION.UP, 0);

        verify(joeMock, times(1)).startRaisingStopSign();

        // Verify that the correct sound is played
        verify(soundsControllerMock, times(1)).play(Sounds.SFX.JOESTOP);

        // Ensure no additional calls to getInstance()
        soundsControllerMockedStatic.verify(() -> SoundsController.getInstance(), times(1));
    }

    @Test
    public void testNextActionDown() {
        Joe joeMock = mock(Joe.class);

        Road roadMock = mock(Road.class);
        when(roadMock.getJoe()).thenReturn(joeMock);
        JoeController joeController = new JoeController(roadMock);

        joeController.nextAction(null, GUI.ACTION.DOWN, 0);

        verify(joeMock, times(1)).startRaisingPassSign();

        soundsControllerMockedStatic.verify(() -> SoundsController.getInstance().playRandom(Sounds.SFX.JOEPASS1, Sounds.SFX.JOEPASS2), times(1));
    }

    @Test
    public void testNextActionNone() throws NoSuchFieldException, IllegalAccessException {
        Game gameMock = mock(Game.class);
        when(joe.getPosition()).thenReturn(DEFAULT_POSITION);
        when(road.getJoe()).thenReturn(joe);

        JoeController spyJoeController = spy(joeController);
        spyJoeController.nextAction(gameMock, GUI.ACTION.LEFT, 1000);

        Field lastActionField = JoeController.class.getDeclaredField("lastAction");
        lastActionField.setAccessible(true);
        lastActionField.set(spyJoeController, GUI.ACTION.NONE);

        spyJoeController.nextAction(gameMock, GUI.ACTION.NONE, 1000);
        verify(spyJoeController).joeNotWalking();
    }

    @Test
    public void testCheckCollisionsNoCars() {
        joeController.checkCollisions();
        verify(joe, never()).isHitLeft();
        verify(joe, never()).isHitRight();
    }

    private void testCheckCollisionsWithCar(Consumer<Joe> verifyAction) {
        Car car = mock(Car.class);
        when(road.getCars()).thenReturn(List.of(car));
        Joe joe = mock(Joe.class);

        joeController.checkCollisions();

        verifyAction.accept(joe);
    }

    @Test
    public void testCheckCollisionsWithCarLeft() {
        try (MockedStatic<AuxCheckRange> mockedStatic = Mockito.mockStatic(AuxCheckRange.class)) {
            mockedStatic.when(() -> AuxCheckRange.isInRangeLeftCarJoe(any(Car.class), any(Joe.class)))
                    .thenReturn(true);

            testCheckCollisionsWithCar(Joe::isHitLeft);
        }
    }

    @Test
    public void testCheckCollisionsWithCarRight() {
        try (MockedStatic<AuxCheckRange> mockedStatic = Mockito.mockStatic(AuxCheckRange.class)) {
            mockedStatic.when(() -> AuxCheckRange.isInRangeRightCarJoe(any(Car.class), any(Joe.class)))
                    .thenReturn(true);

            testCheckCollisionsWithCar(Joe::isHitRight);
        }
    }

    @Test
    public void testJoeMovesToSpecificPosition() {
        Position targetPosition = new Position(200, 200);
        joeController.JoeAction(targetPosition, 'r');

        ArgumentCaptor<Position> captor = ArgumentCaptor.forClass(Position.class);
        verify(joe).setPosition(captor.capture());
        Position capturedPosition = captor.getValue();

        assertEquals(targetPosition.getX(), capturedPosition.getX());
        assertEquals(targetPosition.getY(), capturedPosition.getY());
    }

    @Test
    public void testJoeMovement() {
        // Setup Joe using the correct constructor
        int initialX = 100;
        int initialY = 50;
        Joe joe = new Joe(initialX, initialY);  // Correct constructor usage

        // Set up the JoeController with a mock Road
        Road roadMock = mock(Road.class);
        JoeController joeController = new JoeController(roadMock);

        // Set the mock Road to use the real Joe
        when(joeController.getModel().getJoe()).thenReturn(joe);

        // Perform Joe's movement
        joeController.moveJoeLeft();  // Move left, Joe should be moved to position 94 (100 - 6)

        // Assert that the Joe's position is updated as expected
        assertEquals(94, joe.getPosition().getX());  // Ensure the X position is decreased by 6 (step size)
    }

    @Test
    public void testJoeControllerHandlesMovement() {
        // Setup Joe using the correct constructor
        int initialX = 100;
        int initialY = 50;
        Joe joe = new Joe(initialX, initialY);

        // Setup JoeController
        Road roadMock = mock(Road.class);
        JoeController joeController = new JoeController(roadMock);
        when(joeController.getModel().getJoe()).thenReturn(joe);

        // Test moving Joe to the right
        joeController.moveJoeRight();  // Move Joe right, should go to 106

        // Verify Joe's position after movement
        assertEquals(106, joe.getPosition().getX());  // Assert that X has increased by 6
    }

    @Test
    public void testCanGoThrough() throws Exception {
        Position positionWithinBounds = new Position(100, 100);
        Position positionOutOfBoundsLeft = new Position(40, 100);
        Position positionOutOfBoundsRight = new Position(500, 100);

        // Use reflection to access the private method
        java.lang.reflect.Method canGoThroughMethod = JoeController.class.getDeclaredMethod("canGoThrough", Position.class);
        canGoThroughMethod.setAccessible(true);

        assertTrue((boolean) canGoThroughMethod.invoke(joeController, positionWithinBounds));
        assertFalse((boolean) canGoThroughMethod.invoke(joeController, positionOutOfBoundsLeft));
        assertFalse((boolean) canGoThroughMethod.invoke(joeController, positionOutOfBoundsRight));
    }

    @Test
    public void testSetLastActionNone() throws Exception {
        // Use reflection to access the private method
        java.lang.reflect.Method setLastActionNoneMethod = JoeController.class.getDeclaredMethod("setLastActionNone");
        setLastActionNoneMethod.setAccessible(true);

        setLastActionNoneMethod.invoke(joeController);

        Field lastActionField = JoeController.class.getDeclaredField("lastAction");
        lastActionField.setAccessible(true);
        assertEquals(GUI.ACTION.NONE, lastActionField.get(joeController));
    }

    /* ------------------ */

    @Test
    public void testNextActionLeftRightSwitch() throws Exception {
        Game gameMock = mock(Game.class);
        when(joe.getPosition()).thenReturn(DEFAULT_POSITION);
        when(road.getJoe()).thenReturn(joe);

        JoeController spyJoeController = spy(joeController);
        spyJoeController.nextAction(gameMock, GUI.ACTION.LEFT, 1000);
        spyJoeController.nextAction(gameMock, GUI.ACTION.RIGHT, 1000);

        Field lastActionField = JoeController.class.getDeclaredField("lastAction");
        lastActionField.setAccessible(true);
        assertEquals(GUI.ACTION.NONE, lastActionField.get(spyJoeController));
    }

    @Test
    public void testNextActionRightLeftSwitch() throws Exception {
        Game gameMock = mock(Game.class);
        when(joe.getPosition()).thenReturn(DEFAULT_POSITION);
        when(road.getJoe()).thenReturn(joe);

        JoeController spyJoeController = spy(joeController);
        spyJoeController.nextAction(gameMock, GUI.ACTION.RIGHT, 1000);
        spyJoeController.nextAction(gameMock, GUI.ACTION.LEFT, 1000);

        Field lastActionField = JoeController.class.getDeclaredField("lastAction");
        lastActionField.setAccessible(true);
        assertEquals(GUI.ACTION.NONE, lastActionField.get(spyJoeController));
    }

    @Test
    public void testNextActionDownWithJoeInRange() {
        Joe joeMock = mock(Joe.class);
        when(joeMock.getPosition()).thenReturn(new Position(200, 200));
        when(road.getJoe()).thenReturn(joeMock);
        when(road.getKids()).thenReturn(List.of(mock(Kid.class)));

        JoeController spyJoeController = spy(joeController);
        spyJoeController.nextAction(null, GUI.ACTION.DOWN, 1000);

        verify(joeMock).startRaisingPassSign();
        soundsControllerMockedStatic.verify(() -> SoundsController.playRandom(Sounds.SFX.JOEPASS1, Sounds.SFX.JOEPASS2), times(1));
    }

    @Test
    public void testNextActionUpWithJoeInRange() {
        // Mock dependencies
        Joe joeMock = mock(Joe.class);
        when(joeMock.getPosition()).thenReturn(new Position(200, 200));
        when(road.getJoe()).thenReturn(joeMock);
        when(road.getKids()).thenReturn(List.of(mock(Kid.class)));

        // Spy on JoeController
        JoeController spyJoeController = spy(joeController);

        // Ensure no old static mocks exist
        if (soundsControllerMockedStatic != null) {
            soundsControllerMockedStatic.close();
        }

        // Mock static methods for SoundsController
        soundsControllerMockedStatic = mockStatic(SoundsController.class);
        SoundsController soundsControllerMock = mock(SoundsController.class);
        soundsControllerMockedStatic.when(SoundsController::getInstance).thenReturn(soundsControllerMock);

        // Act
        spyJoeController.nextAction(null, GUI.ACTION.UP, 1000);

        // Verify Joe's behavior
        verify(joeMock).startRaisingStopSign();

        // Verify the correct sound is played
        verify(soundsControllerMock).play(Sounds.SFX.JOESTOP);

        // Ensure getInstance() is called once
        soundsControllerMockedStatic.verify(SoundsController::getInstance, times(1));
    }

    /*----------------*/

    @Test
    public void testNextActionUpWhenJoeIsRaisingStopSign() {
        // Mock the Joe object and its behavior
        Joe joeMock = mock(Joe.class);
        when(joeMock.getPosition()).thenReturn(new Position(200, 200));
        when(joeMock.getIsRaisingStopSign()).thenReturn(true);
        when(road.getJoe()).thenReturn(joeMock);
        when(road.getKids()).thenReturn(List.of(mock(Kid.class)));

        // Ensure no old static mocks exist
        if (soundsControllerMockedStatic != null) {
            soundsControllerMockedStatic.close();
        }

        // Mock static methods for SoundsController
        soundsControllerMockedStatic = mockStatic(SoundsController.class);
        SoundsController soundsControllerMock = mock(SoundsController.class);
        soundsControllerMockedStatic.when(SoundsController::getInstance).thenReturn(soundsControllerMock);

        // Spy on JoeController
        JoeController spyJoeController = spy(joeController);

        // Act
        spyJoeController.nextAction(null, GUI.ACTION.UP, 1000);

        // Verify Joe's behavior
        verify(joeMock).startRaisingStopSign();

        // Verify the correct sound is played
        verify(soundsControllerMock).play(Sounds.SFX.JOESTOP);

        // Ensure getInstance() is called once
        soundsControllerMockedStatic.verify(SoundsController::getInstance, times(1));
    }

/*    @Test
    public void testCheckCollisionsJoeHitLeft() {
        // 1. Mock the Car object and its behavior
        Car carMock = mock(Car.class);
        when(carMock.getPosition()).thenReturn(new Position(100, 100));
        when(road.getCars()).thenReturn(List.of(carMock));

        // 2. Mock the Joe object and its behavior
        Joe joeMock = mock(Joe.class);
        when(joeMock.getPosition()).thenReturn(new Position(90, 100));
        when(road.getJoe()).thenReturn(joeMock);

        // 3. Spy on JoeController
        JoeController spyJoeController = spy(joeController);

        // 4. Ensure no old static mocks exist
        if (soundsControllerMockedStatic != null) {
            soundsControllerMockedStatic.close();
        }

        // 5. Mock static methods for SoundsController
        soundsControllerMockedStatic = mockStatic(SoundsController.class);

        // 6. Create a mocked instance of SoundsController and ensure it gets returned
        SoundsController soundsControllerMock = mock(SoundsController.class);
        soundsControllerMockedStatic.when(SoundsController::getInstance).thenReturn(soundsControllerMock);

        // 7. Define behavior for the mocked play method if required
        doNothing().when(soundsControllerMock).play(any(Sounds.SFX.class));

        // 8. Act
        spyJoeController.checkCollisions();

        // 9. Verify Joe's expected behavior
        verify(joeMock).isHitLeft();
        verify(joeMock).setPosition(any(Position.class));
        verify(joeMock).countHitPoints();

        // 10. Properly verify the static call to SoundsController
        soundsControllerMockedStatic.verify(() -> SoundsController.getInstance().play(Sounds.SFX.JOEHIT), times(1));

        // 11. Close the static mock
        soundsControllerMockedStatic.close();
    }*/

/*    @Test
    public void testCheckCollisionsJoeHitRight() {
        // Mock Car and dependencies
        Car carMock = mock(Car.class);
        when(carMock.getPosition()).thenReturn(new Position(100, 100)); // Car's position
        when(road.getCars()).thenReturn(List.of(carMock));             // Road contains the car

        // Mock Joe's position
        when(joe.getPosition()).thenReturn(new Position(90, 100)); // Joe's position

        try (
                // Static mocking setup
                MockedStatic<AuxCheckRange> auxCheckRangeMockedStatic = Mockito.mockStatic(AuxCheckRange.class);
                MockedStatic<SoundsController> soundsControllerMockedStatic = Mockito.mockStatic(SoundsController.class)
        ) {
            // Define behavior for AuxCheckRange.isInRangeRightCarJoe (collision detected)
            auxCheckRangeMockedStatic.when(() -> AuxCheckRange.isInRangeRightCarJoe(carMock, joe))
                    .thenReturn(true);

            // Mock the static SoundsController singleton
            SoundsController soundsControllerMock = mock(SoundsController.class);
            soundsControllerMockedStatic.when(SoundsController::getInstance).thenReturn(soundsControllerMock);

            // Act: Call the method being tested
            joeController.checkCollisions();

            // Assert & verify Joe's interactions
            verify(joe).isHitRight();
            verify(joe).setPosition(any(Position.class));
            verify(joe).countHitPoints();

            // Verify AuxCheckRange was called correctly
            auxCheckRangeMockedStatic.verify(() -> AuxCheckRange.isInRangeRightCarJoe(carMock, joe), times(1));

            // Verify SoundsController plays the correct sound
            soundsControllerMockedStatic.verify(SoundsController::getInstance, times(1));
            verify(soundsControllerMock).play(Sounds.SFX.JOEHIT);
        }
    }*/

/*    @Test
    public void testNextActionNoneWhenLastActionNotNone() throws NoSuchFieldException, IllegalAccessException {
        Game gameMock = mock(Game.class);
        when(joe.getPosition()).thenReturn(DEFAULT_POSITION);
        when(road.getJoe()).thenReturn(joe);

        JoeController spyJoeController = spy(joeController);
        spyJoeController.nextAction(gameMock, GUI.ACTION.LEFT, 1000);

        Field lastActionField = JoeController.class.getDeclaredField("lastAction");
        lastActionField.setAccessible(true);
        lastActionField.set(spyJoeController, GUI.ACTION.LEFT);

        spyJoeController.nextAction(gameMock, GUI.ACTION.NONE, 1000);
        verify(spyJoeController).joeNotWalking();
    }*/
}

package com.CrossingGuardJoe.gui;

import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.viewer.ColorCustomize;
import com.CrossingGuardJoe.viewer.images.Font.FontImageFactory;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.w3c.dom.Text;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LanternaGUITest {

    private LanternaGUI gui;
    @Mock private TextGraphics mockGraphics;
    @Mock private Screen mockScreen;
    @Mock private ColorCustomize mockColorCustomize;

    @BeforeEach
    void setUp() {
        // Instantiate the LanternaGUI
        gui = new LanternaGUI(80, 24);
        mockGraphics = mock(TextGraphics.class);
        mockScreen = mock(Screen.class);

        // Inject mocked graphics and screen
        gui.setGraphics(mockGraphics);
        gui.setScreen(mockScreen);
    }

    @Test
    void createTerminal_shouldCreateTerminalSuccessfully() throws Exception {
        // Mock dependencies and ensure no exceptions are thrown
        gui.createTerminal();
        assertEquals(gui.getClass(), LanternaGUI.class);
    }

    @Test
    void clearScreen_shouldFillScreenWithDefaultColor() {
        // Act
        gui.clearScreen();

        // Verify interactions
        verify(mockGraphics).setBackgroundColor(TextColor.Factory.fromString("#7F7976"));
        verify(mockGraphics).fillRectangle(Mockito.any(), Mockito.any(), eq(' '));
    }

    @Test
    void refreshScreen_shouldInvokeScreenRefresh() throws IOException {
        // Act
        gui.refreshScreen();

        // Verify screen refresh
        verify(mockScreen, times(1)).refresh();
    }

    @Test
    void closeScreen_shouldInvokeScreenClose() throws IOException {
        // Act
        gui.closeScreen();

        // Verify screen close
        verify(mockScreen, times(1)).close();
    }

    @Test
    void setBackgroundColor_shouldSetColorCorrectly() {
        // Arrange
        String colorHex = "#123456";

        // Act
        gui.setBackgroundColor(colorHex);

        // Verify the color was set as expected
        verify(mockGraphics).setBackgroundColor(TextColor.Factory.fromString(colorHex));
    }

    @Test
    void fillRectangle_shouldCallFillRectangleOnGraphics() {
        // Arrange
        Position position = new Position(5, 5);

        // Act
        gui.fillRectangle(position, 10, 10);

        // Verify rectangle is filled
        verify(mockGraphics).fillRectangle(new TerminalPosition(position.getX(), position.getY()),
                new TerminalSize(10, 10), ' ');
    }

    @Test
    void drawImage_shouldLoopAndDrawEachLineOfImage() {
        // Arrange
        String[] image = {"Line1", "Line2"}; // Two lines in the image
        Position position = new Position(5, 3); // Starting position (x=5, y=3)

        // Act
        gui.drawImage(position, image);

        // Verify: Ensure setBackgroundColor is called for distinct colors
        verify(mockGraphics, times(2)) // Only 2 distinct `setColor` calls are made based on behavior
                .setBackgroundColor(any(TextColor.class));

        // Verify: Each character in the image triggers a call to fillRectangle
        verify(mockGraphics, times(10)) // 5 characters in "Line1" + 5 characters in "Line2"
                .fillRectangle(any(TerminalPosition.class), eq(new TerminalSize(1, 1)), eq(' '));

        // Verify no unexpected interactions
        verifyNoMoreInteractions(mockGraphics);
    }

    @Test
    void setColorHexaCode_shouldSetBackgroundColor() {
        // Act
        gui.setColorHexaCode("#445588");

        // Verify
        verify(mockGraphics).setBackgroundColor(TextColor.Factory.fromString("#445588"));
    }

    @Test
    void getNextAction_shouldReturnMappedAction() throws IOException {
        // Arrange
        KeyStroke mockKey = mock(KeyStroke.class);
        when(mockScreen.pollInput()).thenReturn(mockKey);
        when(mockKey.getKeyType()).thenReturn(KeyType.ArrowDown);

        // Act
        GUI.ACTION action = gui.getNextAction();

        // Assert
        assertEquals(GUI.ACTION.DOWN, action);

        // Edge case for NONE
        when(mockScreen.pollInput()).thenReturn(null);
        assertEquals(GUI.ACTION.NONE, gui.getNextAction());
    }

    /* ---- */

    @Test
    void getNextAction_shouldHandleAllKeyTypes() throws IOException {
        KeyStroke mockKey = mock(KeyStroke.class);

        when(mockScreen.pollInput()).thenReturn(mockKey);

        // Test Left Arrow
        when(mockKey.getKeyType()).thenReturn(KeyType.ArrowLeft);
        assertEquals(GUI.ACTION.LEFT, gui.getNextAction());

        // Test Right Arrow
        when(mockKey.getKeyType()).thenReturn(KeyType.ArrowRight);
        assertEquals(GUI.ACTION.RIGHT, gui.getNextAction());

        // Test Up Arrow
        when(mockKey.getKeyType()).thenReturn(KeyType.ArrowUp);
        assertEquals(GUI.ACTION.UP, gui.getNextAction());

        // Test Enter
        when(mockKey.getKeyType()).thenReturn(KeyType.Enter);
        assertEquals(GUI.ACTION.SELECT, gui.getNextAction());

        // Test Escape
        when(mockKey.getKeyType()).thenReturn(KeyType.Escape);
        assertEquals(GUI.ACTION.ESC, gui.getNextAction());

        // Test EOF
        when(mockKey.getKeyType()).thenReturn(KeyType.EOF);
        assertEquals(GUI.ACTION.QUIT, gui.getNextAction());

        // Test Default
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        assertEquals(GUI.ACTION.NONE, gui.getNextAction());
    }

    /*@Test
    void drawText_shouldRenderTextCorrectly() {
        // Arrange
        Position position = new Position(10, 10); // Starting position
        String text = "Test Text"; // Text to render
        String colorHex = "#FF0000"; // Expected render color
        String outlineColorHex = "#000000"; // Outline color (black)
        String[] textImage = {"Line1", "Line2"}; // Mock representation of the text

        // Mock FontImageFactory to return text image
        try (MockedConstruction<FontImageFactory> mockedFactory = mockConstruction(FontImageFactory.class,
                (mock, context) -> when(mock.getImageRepresentation(any())).thenReturn(textImage))) {

            // Act
            gui.drawText(position, text, colorHex);

            // Assert

            // 1. Calculate the number of non-space characters in the image
            int nonSpaceCharacters = countNonSpaceCharacters(textImage);

            // 2. Verify the outline rendering (color: #000000)
            verify(mockGraphics, times(nonSpaceCharacters)).setBackgroundColor(TextColor.Factory.fromString(outlineColorHex));
            verify(mockGraphics, times(nonSpaceCharacters)).fillRectangle(
                    any(TerminalPosition.class),
                    eq(new TerminalSize(1, 1)),
                    eq(' ')
            );

            // 3. Verify the text rendering (color: #FF0000)
            verify(mockGraphics, times(nonSpaceCharacters)).setBackgroundColor(TextColor.Factory.fromString(colorHex));
            verify(mockGraphics, times(nonSpaceCharacters)).fillRectangle(
                    any(TerminalPosition.class),
                    eq(new TerminalSize(1, 1)),
                    eq(' ')
            );

            // Ensure no unexpected interactions happened
            verifyNoMoreInteractions(mockGraphics);
        }
    }*/

    /*@Test
    void drawText_shouldDrawTextWithCorrectColorsAndOutline() {
        // Arrange: Test parameters
        Position position = new Position(5, 5);
        String text = "Test";
        String colorHexCode = "#FF0000";
        String outlineColorHex = "#000000";

        // Mock FontImageFactory to provide text rendering representation
        String[] mockImageRepresentation = {"Test"};
        try (MockedConstruction<FontImageFactory> mockedFactory = mockConstruction(FontImageFactory.class,
                (mock, context) -> when(mock.getImageRepresentation(any())).thenReturn(mockImageRepresentation))) {

            // Act: Trigger `drawText` on the GUI with the mock
            gui.drawText(position, text, colorHexCode);

            // Assert: Verify behavior for the outline
            int totalCharacters = text.length();
            verify(mockGraphics, times(totalCharacters)).setBackgroundColor(TextColor.Factory.fromString(outlineColorHex));
            verify(mockGraphics, times(totalCharacters)).fillRectangle(any(TerminalPosition.class),
                    eq(new TerminalSize(1, 1)), eq(' '));

            // Assert: Verify behavior for the foreground
            verify(mockGraphics, times(totalCharacters)).setBackgroundColor(TextColor.Factory.fromString(colorHexCode));
            verify(mockGraphics, times(totalCharacters)).fillRectangle(any(TerminalPosition.class),
                    eq(new TerminalSize(1, 1)), eq(' '));

            // Assert: Ensure total render operations are double the length of the text (outline + foreground)
            verify(mockGraphics, times(2 * totalCharacters)).fillRectangle(any(TerminalPosition.class),
                    eq(new TerminalSize(1, 1)), eq(' '));

            // Assert: Clean environment
            verifyNoMoreInteractions(mockGraphics);
        }
    }*/

    /*// Helper method to count non-space characters in the text image
    private int countNonSpaceCharacters(String[] textImage) {
        int count = 0;
        for (String line : textImage) {
            for (char c : line.toCharArray()) {
                if (!Character.isWhitespace(c)) {
                    count++;
                }
            }
        }
        return count;
    }*/

    @Test
    void drawLineCustomColor_shouldHandleLineCorrectly() {
        String imageLine = "ABC";

        // Act
        gui.drawLineCustomColor(5, 3, imageLine, "#FF0000");

        // Verify color and rectangle are applied for each character
        verify(mockGraphics, times(imageLine.length())).setBackgroundColor(any(TextColor.class));
        verify(mockGraphics, times(imageLine.length())).fillRectangle(any(), any(), eq(' '));
    }

    @Test
    void getMappedCharacter_shouldReturnMappedValue() {
        // Mock the `ColorCustomize` getInstance method
        try (MockedStatic<ColorCustomize> mockedStatic = mockStatic(ColorCustomize.class)) {
            // Create a mocked instance of ColorCustomize
            ColorCustomize mockColorCustomize = mock(ColorCustomize.class);

            // Configure the static method `getInstance()` to return the mocked instance
            mockedStatic.when(ColorCustomize::getInstance).thenReturn(mockColorCustomize);

            // Configure the mock's behavior
            when(mockColorCustomize.getMappedCharacter('A')).thenReturn('B');

            // Create a new LanternaGUI instance so it uses the mocked ColorCustomize
            gui = new LanternaGUI(80, 24);

            // The actual test assertion
            assertEquals('B', gui.getMappedCharacter('A'), "Mapped character for 'A' should be 'B'!");

            // Verify that the mock was called correctly
            verify(mockColorCustomize).getMappedCharacter('A');
        }
    }

    @Test
    void drawLine_shouldHandleEmptyString() {
        // Act
        gui.drawLine(5, 5, "");

        // Verify no interactions occurred
        verify(mockGraphics, never()).fillRectangle(any(), any(), anyChar());
    }

    @Test
    void setBackgroundColor_shouldThrowExceptionForInvalidColorCode() {
        // Act and Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gui.setBackgroundColor("#INVALID"),
                "Expected setBackgroundColor to throw IllegalArgumentException for invalid color codes"
        );

        // Verify the exception message explicitly
        assertEquals("Unknown color definition \"#INVALID\"", exception.getMessage(),
                "Exception message should indicate the provided color is unknown or invalid");
    }

    @Test
    void addColorMapping_shouldAddMappingToColorCustomize() {
        // Mock the `ColorCustomize` singleton
        try (MockedStatic<ColorCustomize> mockedStatic = mockStatic(ColorCustomize.class)) {
            // Create a mocked instance of `ColorCustomize`
            ColorCustomize mockColorCustomize = mock(ColorCustomize.class);

            // Configure the static method `getInstance` to return the mocked instance
            mockedStatic.when(ColorCustomize::getInstance).thenReturn(mockColorCustomize);

            // Create a new `LanternaGUI` instance so it uses the mocked `ColorCustomize`
            gui = new LanternaGUI(80, 24);

            // Act: Call the `addColorMapping` method
            char oldCharacter = 'A';
            char newCharacter = 'B';
            gui.addColorMapping(oldCharacter, newCharacter);

            // Assert: Verify the method was called on the mocked `ColorCustomize`
            verify(mockColorCustomize, times(1)).addMapping(oldCharacter, newCharacter);

            // Ensure no unexpected interactions
            verifyNoMoreInteractions(mockColorCustomize);
        }
    }
}
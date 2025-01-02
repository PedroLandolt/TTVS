package com.CrossingGuardJoe.viewer.menu;

import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.Position;
import com.CrossingGuardJoe.model.menu.ColorPaletteMenu;
import com.CrossingGuardJoe.model.menu.CustomizeMenu;
import com.CrossingGuardJoe.model.menu.Option;
import com.CrossingGuardJoe.viewer.Color;
import com.CrossingGuardJoe.viewer.images.defined.ToolImages;
import com.CrossingGuardJoe.viewer.images.generator.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomizeMenuViewerTest {

    private CustomizeMenuViewer viewer;
    private CustomizeMenu model;
    private GUI gui;

    @BeforeEach
    void setUp() {
        model = mock(CustomizeMenu.class);
        gui = mock(GUI.class);
        viewer = new CustomizeMenuViewer(model);
    }

    @Test
    void drawTitle() {
        viewer.drawTitle(gui);

        verify(gui, times(1)).drawText(new Position(180, 28), "CUSTOMIZE YOUR GAME", "#FFFFFF");
    }

    @Test
    void drawInformation() {
        try (MockedStatic<ToolImages> toolImages = Mockito.mockStatic(ToolImages.class)) {
            toolImages.when(ToolImages::getKeyEscImage).thenReturn(new String[]{" "});

            viewer.drawInformation(gui);

            verify(gui, times(1)).drawImage(new Position(4, 4), new String[]{" "});
        }
    }

    @Test
    void drawSelectionBox() {
        // Mock necessary data for menu options
        Option option1 = new Option("option1", new Position(50, 60), new String[]{"#####"});
        Option option2 = new Option("option2", new Position(70, 80), new String[]{"#####"});
        List<List<Option>> menuLevels = new ArrayList<>();
        menuLevels.add(Collections.singletonList(option1));
        menuLevels.add(Collections.singletonList(option2));

        // Mock CustomizeMenu behavior
        when(model.getMenuLevels()).thenReturn(menuLevels);
        when(model.isSelectedJoeCustomize()).thenReturn(true);

        // Mock ColorPaletteMenu to avoid NullPointerException in drawColorsPalette
        ColorPaletteMenu paletteMenu = mock(ColorPaletteMenu.class);
        when(model.getColorPaletteMenu()).thenReturn(paletteMenu);
        when(paletteMenu.getColorPalette()).thenReturn(Collections.emptyList());
        when(model.isColorPaletteSelected()).thenReturn(false);

        // Mock Shape to provide the drawImage content
        try (MockedStatic<Shape> shapeMock = Mockito.mockStatic(Shape.class)) {
            String[] mockShape = new String[]{"#"};
            shapeMock.when(() -> Shape.RectangleFilledGenerator(235, 300, 'K', 2, '$')).thenReturn(mockShape);

            // Invoke the method for the test
            viewer.drawElements(gui);

            // Verify selection box was drawn 3 times
            verify(gui, times(3)).drawImage(any(Position.class), eq(mockShape));
        }
    }

    @Test
    void drawElements() {
        // Mock options for menu levels
        Option option1 = new Option("option1", new Position(50, 60), new String[]{"#####"});
        Option option2 = new Option("option2", new Position(70, 80), new String[]{"#####"});
        List<List<Option>> menuLevels = new ArrayList<>();
        menuLevels.add(Collections.singletonList(option1));
        menuLevels.add(Collections.singletonList(option2));

        // Mock ColorPaletteMenu and make it non-null
        ColorPaletteMenu paletteMenu = mock(ColorPaletteMenu.class);
        when(model.getColorPaletteMenu()).thenReturn(paletteMenu);

        // Prepare mock for the color palette (dummy list of colors)
        List<Color> colorPalette = List.of(Color.RED, Color.BLUE);
        when(paletteMenu.getColorPalette()).thenReturn(colorPalette);
        when(paletteMenu.isColorSelected(0)).thenReturn(true);
        when(model.isColorPaletteSelected()).thenReturn(true);

        // Mock necessary behaviors of the CustomizeMenu
        when(model.getMenuLevels()).thenReturn(menuLevels);
        when(model.isSelectedJoeCustomize()).thenReturn(true);
        when(model.isSelectedOption(0, 0)).thenReturn(true);

        // Invoke the method
        viewer.drawElements(gui);

        // Verify the total number of calls to drawImage
        verify(gui, times(12)).drawImage(any(Position.class), any(String[].class)); // Match the total number of invocations
    }

    @Test
    void drawColorPalette() {
        Color color1 = Color.RED;
        Color color2 = Color.BLUE;

        ColorPaletteMenu paletteMenu = mock(ColorPaletteMenu.class);
        when(model.getColorPaletteMenu()).thenReturn(paletteMenu);
        when(paletteMenu.getColorPalette()).thenReturn(List.of(color1, color2));
        when(paletteMenu.isColorSelected(1)).thenReturn(true);
        when(model.isColorPaletteSelected()).thenReturn(true);

        viewer.drawElements(gui); // Tests drawColorsPalette indirectly

        verify(gui, times(1)).setColorHexaCode(color1.getColorHexCode());
        verify(gui, times(1)).setColorHexaCode(color2.getColorHexCode());
        verify(gui, times(1)).drawImage(new Position(70, 405), ToolImages.getArrowDownImage());
    }

    @Test
    void addColorMap() {
        // Mocking necessary behaviors
        ColorPaletteMenu colorPaletteMenu = mock(ColorPaletteMenu.class);
        when(model.getColorPaletteMenu()).thenReturn(colorPaletteMenu); // Avoid NullPointerException

        when(model.getOldColor()).thenReturn('A'); // Mock old color
        when(model.getNewColor()).thenReturn('B'); // Mock new color

        viewer.drawElements(gui); // Tests addColorMap indirectly

        // Verify the correct color mapping was added to the GUI
        verify(gui, times(1)).addColorMapping('A', 'B');
    }


    /// /////////////////////////////

    /*@Test
    void drawSelectionBoxKidsCustomize() {
        // Mock `isSelectedKidsCustomize` to force the condition
        when(model.isSelectedKidsCustomize()).thenReturn(true);
        when(model.isSelectedJoeCustomize()).thenReturn(false);
        when(model.isSelectedCarsCustomize()).thenReturn(false);

        // Properly mock ColorPaletteMenu
        ColorPaletteMenu paletteMenu = mock(ColorPaletteMenu.class);
        when(model.getColorPaletteMenu()).thenReturn(paletteMenu);
        when(paletteMenu.getColorPalette()).thenReturn(Collections.emptyList());
        when(model.isColorPaletteSelected()).thenReturn(false);

        // Mock Shape.RectangleFilledGenerator
        try (MockedStatic<Shape> shapeMock = Mockito.mockStatic(Shape.class)) {
            String[] mockShape = {"#"}; // Expected simple shape for Kids Customize
            shapeMock.when(() -> Shape.RectangleFilledGenerator(235, 300, ' ', 2, 'G')).thenReturn(mockShape);

            // Invoke the tested method
            viewer.drawElements(gui);

            // Verify the correct image was drawn
            verify(gui).drawImage(
                    argThat(pos -> pos.getX() == 195 && pos.getY() == 70), eq(mockShape)
            );
        }
    }

    @Test
    void drawSelectionBoxCarsCustomize() {
        // Mock necessary data
        when(model.isSelectedCarsCustomize()).thenReturn(true); // Mock Cars Customize selection

        // Mock Shape for drawing the selection box
        try (MockedStatic<Shape> shapeMock = Mockito.mockStatic(Shape.class)) {
            String[] mockShape = new String[]{"#"};
            shapeMock.when(() -> Shape.RectangleFilledGenerator(235, 300, ' ', 2, 'G')).thenReturn(mockShape);

            // Invoke the method
            viewer.drawElements(gui);

            // Verify the selection box for Cars Customize was drawn
            verify(gui, times(1)).drawImage(new Position(350, 70), mockShape);
        }
    }

    @Test
    void drawOptionsTextWhenIndexGreaterThanZero() {
        // Mock options for menu levels
        Option option1 = new Option("FirstOption", new Position(50, 60), new String[]{"#####"});
        Option option2 = new Option("SecondOption", new Position(70, 80), new String[]{"#####"});

        List<Option> levelOptions = List.of(option1, option2);
        when(model.getMenuLevels()).thenReturn(List.of(levelOptions));

        // Invoke the method
        viewer.drawElements(gui);

        // Verify text shadow is drawn only for the second option (j > 0)
        verify(gui, times(1)).drawText(eq(new Position(69, 79)), eq("SecondOption"), eq("#FFFFFF"));
        verify(gui, never()).drawText(eq(new Position(49, 59)), eq("FirstOption"), eq("#FFFFFF")); // First option (j = 0)
    }

    @Test
    void drawElementsColorSquare() {
        // Mock color squares
        Option colorSquare1 = new Option("Color1", new Position(100, 100), new String[]{"RedSquare"});
        Option colorSquare2 = new Option("Color2", new Position(200, 200), new String[]{"BlueSquare"});

        // Mock defined colors in the menu
        when(model.getDefinedColors()).thenReturn(List.of(colorSquare1, colorSquare2));

        // Invoke the method
        viewer.drawElements(gui);

        // Verify that each color square is drawn
        verify(gui, times(1)).drawImage(colorSquare1.position(), colorSquare1.image());
        verify(gui, times(1)).drawImage(colorSquare2.position(), colorSquare2.image());
    }

    @Test
    void addColorMapWithValidOldColor() {
        // Mock old and new color values
        when(model.getOldColor()).thenReturn('A');
        when(model.getNewColor()).thenReturn('B');

        // Invoke the method
        viewer.drawElements(gui);

        // Verify that the color mapping was added
        verify(gui, times(1)).addColorMapping('A', 'B');
    }

    @Test
    void addColorMapWithInvalidOldColor() {
        // Mock oldColor as '\uFFFF' (should skip adding mapping)
        when(model.getOldColor()).thenReturn('\uFFFF');
        when(model.getNewColor()).thenReturn('B');

        // Invoke the method
        viewer.drawElements(gui);

        // Verify that no color mapping was added
        verify(gui, never()).addColorMapping(anyChar(), anyChar());
    }*/
}
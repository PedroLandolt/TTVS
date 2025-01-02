package com.CrossingGuardJoe.viewer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void testGetColorHexCode() {
        // Loop through all enum values and validate their hex codes
        for (Color color : Color.values()) {
            switch (color) {
                case BLACK -> assertEquals("#000000", color.getColorHexCode(), "BLACK should return #000000");
                case DARK_GREY -> assertEquals("#3D3638", color.getColorHexCode(), "DARK_GREY should return #3D3638");
                case BACKGROUND -> assertEquals("#7F7976", color.getColorHexCode(), "BACKGROUND should return #7F7976");
                case LIGHT_GREY -> assertEquals("#C0BBB1", color.getColorHexCode(), "LIGHT_GREY should return #C0BBB1");
                case WHITE -> assertEquals("#FFFFFF", color.getColorHexCode(), "WHITE should return #FFFFFF");
                case SKIN -> assertEquals("#FFEBB2", color.getColorHexCode(), "SKIN should return #FFEBB2");
                case SKIN2 -> assertEquals("#FFCA8E", color.getColorHexCode(), "SKIN2 should return #FFCA8E");
                case ORANGE -> assertEquals("#F0922D", color.getColorHexCode(), "ORANGE should return #F0922D");
                case RED -> assertEquals("#FF5451", color.getColorHexCode(), "RED should return #FF5451");
                case RED2 -> assertEquals("#FF0000", color.getColorHexCode(), "RED2 should return #FF0000");
                case ORANGE2 -> assertEquals("#F36849", color.getColorHexCode(), "ORANGE2 should return #F36849");
                case ROASTED_YELLOW -> assertEquals("#8E6E43", color.getColorHexCode(), "ROASTED_YELLOW should return #8E6E43");
                case YELLOW2 -> assertEquals("#F6D756", color.getColorHexCode(), "YELLOW2 should return #F6D756");
                case YELLOW -> assertEquals("#FFDC1C", color.getColorHexCode(), "YELLOW should return #FFDC1C");
                case YELLOW3 -> assertEquals("#FFDB00", color.getColorHexCode(), "YELLOW3 should return #FFDB00");
                case GREEN -> assertEquals("#BAF11C", color.getColorHexCode(), "GREEN should return #BAF11C");
                case GREEN3 -> assertEquals("#66B032", color.getColorHexCode(), "GREEN3 should return #66B032");
                case GREEN2 -> assertEquals("#6C9772", color.getColorHexCode(), "GREEN2 should return #6C9772");
                case DARK_GREEN -> assertEquals("#0E3B2C", color.getColorHexCode(), "DARK_GREEN should return #0E3B2C");
                case CYAN -> assertEquals("#36CDFF", color.getColorHexCode(), "CYAN should return #36CDFF");
                case BLUE -> assertEquals("#447296", color.getColorHexCode(), "BLUE should return #447296");
                case BLUE2 -> assertEquals("#4D9CFD", color.getColorHexCode(), "BLUE2 should return #4D9CFD");
                case BLUE3 -> assertEquals("#368CFF", color.getColorHexCode(), "BLUE3 should return #368CFF");
                case DARK_BLUE -> assertEquals("#193364", color.getColorHexCode(), "DARK_BLUE should return #193364");
                case BROWN -> assertEquals("#601E00", color.getColorHexCode(), "BROWN should return #601E00");
                case PURPLE -> assertEquals("#9E0CFF", color.getColorHexCode(), "PURPLE should return #9E0CFF");
                case PINK -> assertEquals("#F85DC6", color.getColorHexCode(), "PINK should return #F85DC6");
            }
        }
    }

    @Test
    void testGetCharacter() {
        // Loop through all enum values and validate their characters
        for (Color color : Color.values()) {
            switch (color) {
                case BLACK -> assertEquals('$', color.getCharacter(), "BLACK should return '$'");
                case DARK_GREY -> assertEquals('L', color.getCharacter(), "DARK_GREY should return 'L'");
                case BACKGROUND -> assertEquals('A', color.getCharacter(), "BACKGROUND should return 'A'");
                case LIGHT_GREY -> assertEquals('K', color.getCharacter(), "LIGHT_GREY should return 'K'");
                case WHITE -> assertEquals('W', color.getCharacter(), "WHITE should return 'W'");
                case SKIN -> assertEquals('%', color.getCharacter(), "SKIN should return '%'");
                case SKIN2 -> assertEquals('>', color.getCharacter(), "SKIN2 should return '>'");
                case ORANGE -> assertEquals('+', color.getCharacter(), "ORANGE should return '+'");
                case RED -> assertEquals('&', color.getCharacter(), "RED should return '&'");
                case RED2 -> assertEquals('R', color.getCharacter(), "RED2 should return 'R'");
                case ORANGE2 -> assertEquals('O', color.getCharacter(), "ORANGE2 should return 'O'");
                case ROASTED_YELLOW -> assertEquals('@', color.getCharacter(), "ROASTED_YELLOW should return '@'");
                case YELLOW2 -> assertEquals('<', color.getCharacter(), "YELLOW2 should return '<'");
                case YELLOW -> assertEquals(')', color.getCharacter(), "YELLOW should return ')'");
                case YELLOW3 -> assertEquals('q', color.getCharacter(), "YELLOW3 should return 'q'");
                case GREEN -> assertEquals('G', color.getCharacter(), "GREEN should return 'G'");
                case GREEN3 -> assertEquals('u', color.getCharacter(), "GREEN3 should return 'u'");
                case GREEN2 -> assertEquals('g', color.getCharacter(), "GREEN2 should return 'g'");
                case DARK_GREEN -> assertEquals('v', color.getCharacter(), "DARK_GREEN should return 'v'");
                case CYAN -> assertEquals('=', color.getCharacter(), "CYAN should return '='");
                case BLUE -> assertEquals('(', color.getCharacter(), "BLUE should return '('");
                case BLUE2 -> assertEquals('!', color.getCharacter(), "BLUE2 should return '!'");
                case BLUE3 -> assertEquals('~', color.getCharacter(), "BLUE3 should return '~'");
                case DARK_BLUE -> assertEquals('B', color.getCharacter(), "DARK_BLUE should return 'B'");
                case BROWN -> assertEquals('*', color.getCharacter(), "BROWN should return '*'");
                case PURPLE -> assertEquals('\'', color.getCharacter(), "PURPLE should return '''");
                case PINK -> assertEquals('P', color.getCharacter(), "PINK should return 'P'");
            }
        }
    }

    @Test
    void testGetColor() {
        // Valid cases: Ensure the correct Color is returned for a given character
        assertEquals(Color.BLACK, Color.getColor('$'), "Character '$' should return BLACK");
        assertEquals(Color.RED, Color.getColor('&'), "Character '&' should return RED");
        assertEquals(Color.BLUE, Color.getColor('('), "Character '(' should return BLUE");

        // Invalid case: Ensure null is returned for unmapped characters
        assertNull(Color.getColor('Z'), "Character 'Z' should return null as it is not mapped");
        assertNull(Color.getColor('1'), "Character '1' should return null as it is not mapped");
    }
}
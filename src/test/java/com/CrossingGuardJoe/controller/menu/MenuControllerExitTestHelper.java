package com.CrossingGuardJoe.controller.menu;

import com.CrossingGuardJoe.Game;
import com.CrossingGuardJoe.controller.SoundsController;
import com.CrossingGuardJoe.gui.GUI;
import com.CrossingGuardJoe.model.menu.Menu;

import static org.mockito.Mockito.*;

public class MenuControllerExitTestHelper {
    public static void main(String[] args) throws Exception {
        // Mock dependencies
        Menu menuMock = mock(Menu.class);
        Game gameMock = mock(Game.class);
        SoundsController soundsControllerMock = mock(SoundsController.class);

        // Mock static SoundsController.getInstance() method
        try (var staticMock = mockStatic(SoundsController.class)) {
            staticMock.when(SoundsController::getInstance).thenReturn(soundsControllerMock);

            // Arrange: Simulate Exit being selected
            when(menuMock.isSelectedExit()).thenReturn(true);

            // Act: Create the controller and invoke nextAction
            MenuController menuController = new MenuController(menuMock);
            menuController.nextAction(gameMock, GUI.ACTION.SELECT, 0);
        }
    }
}
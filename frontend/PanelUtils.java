package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelUtils {

    // Public method to handle ESC key to navigate back to a given panel
    public static void moveBack(JPanel currentPanel, String targetPanelName, CardLayout cardLayout, JPanel mainPanel) {
        // Add key binding for the ESC key
        InputMap inputMap = currentPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = currentPanel.getActionMap();

        // Map the ESC key to a custom action
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "moveBack");
        actionMap.put("moveBack", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("ESC key pressed, moving back to: " + targetPanelName);
                cardLayout.show(mainPanel, targetPanelName);
            }
        });
    }
}

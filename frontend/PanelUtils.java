package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The PanelUtils class contains utility methods for managing panel interactions,
 * including key bindings to move between panels and mouse hover effects for UI components.
 */
public class PanelUtils {

    /**
     * Adds a key binding to the current panel that listens for the ESC key.
     * When the ESC key is pressed, it moves the user back to the specified target panel.
     *
     * @param currentPanel    The panel where the ESC key binding is being set.
     * @param targetPanelName The name of the panel to move back to.
     * @param cardLayout      The CardLayout managing the panels.
     * @param mainPanel       The main panel containing all the different screens.
     */
    public static void moveBack(JPanel currentPanel, String targetPanelName, CardLayout cardLayout, JPanel mainPanel) {
        // Add key binding for the ESC key
        InputMap inputMap = currentPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = currentPanel.getActionMap();

        // Map the ESC key to a custom action
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "moveBack");
        actionMap.put("moveBack", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Moves to the specified target panel when the ESC key is pressed
                cardLayout.show(mainPanel, targetPanelName);
            }
        });
    }

    /**
     * Applies a hover effect to an image by adjusting its opacity.
     * This effect is used to provide visual feedback when the user hovers over a button.
     *
     * @param originalImage The original image to which the hover effect will be applied.
     * @param opacity       The desired opacity level (0.0f to 1.0f).
     * @return A new Image with the specified opacity applied.
     */
    public static Image hoverEffect(Image originalImage, float opacity) {
        int width = originalImage.getWidth(null);
        int height = originalImage.getHeight(null);

        BufferedImage transparentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = transparentImage.createGraphics();

        // Set the opacity
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return transparentImage;
    }

    /**
     * Adds a mouse rollover effect to a JButton, making it change appearance when hovered.
     * The image's opacity is reduced when the mouse enters the button, and restored when it exits.
     *
     * @param button       The JButton to which the hover effect will be applied.
     * @param hoverOpacity The opacity level to apply when the mouse hovers over the button (0.0f to 1.0f).
     */
    public static void mouseRollOver(JButton button, float hoverOpacity) {
        button.addMouseListener(new MouseAdapter() {
            private Image originalImage;

            @Override
            public void mouseEntered(MouseEvent e) {
                if (originalImage == null) {
                    // Retrieve the original image from the button's icon
                    ImageIcon icon = (ImageIcon) button.getIcon();
                    originalImage = icon.getImage();
                }

                // Apply the hover effect by reducing the opacity
                button.setIcon(new ImageIcon(hoverEffect(originalImage, hoverOpacity)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restore the original image when the mouse exits
                if (originalImage != null) {
                    button.setIcon(new ImageIcon(originalImage));
                }
            }
        });
    }
}

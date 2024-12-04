package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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

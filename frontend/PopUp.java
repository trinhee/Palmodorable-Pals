package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

/**
 * The PopUp class provides a customizable dialog window that can be used for input prompts.
 * It supports displaying a background image, an input text field, and submit and cancel buttons.
 */
public class PopUp {
    private JDialog dialog;
    private JTextField textField;

    /**
     * Constructs a new PopUp dialog.
     *
     * @param parent       The parent JFrame to which this dialog is attached.
     * @param imagePath    The path to the background image to be displayed in the dialog.
     * @param placeholder  The placeholder text to be shown in the input text field.
     * @param submitAction The action to be performed when the submit button is clicked or Enter is pressed.
     */
    public PopUp(JFrame parent, String imagePath, String placeholder, ActionListener submitAction) {
        // Create the dialog
        dialog = new JDialog(parent, "", true); // Empty title for no title bar
        dialog.setSize(1000, 500);
        dialog.setUndecorated(true); // Remove the title bar
        dialog.setBackground(new Color(0, 0, 0, 0)); // Enable transparency
        dialog.setLayout(null); // Manual positioning of components
        dialog.setLocationRelativeTo(parent); // Center the popup on the screen

        // Load background image
        JLabel backgroundLabel = new JLabel();
        try {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: " + imagePath);
            }
            Image backgroundImage = ImageIO.read(imageUrl).getScaledInstance(dialog.getWidth(), dialog.getHeight(), Image.SCALE_SMOOTH);
            backgroundLabel.setIcon(new ImageIcon(backgroundImage));
            backgroundLabel.setBounds(0, 0, dialog.getWidth(), dialog.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Text Field
        textField = new JTextField(placeholder, 20);
        textField.setBounds(300, 200, 400, 30); // Centered placement
        textField.setFont(new Font("Arial", Font.PLAIN, 16));

        textField.addActionListener(e -> {
            if (submitAction != null) {
                submitAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, textField.getText()));
            }
            dialog.dispose();
        });

        // Buttons
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(550, 300, 100, 30);
        submitButton.addActionListener(e -> {
            if (submitAction != null) {
                submitAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, textField.getText()));
            }
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(350, 300, 100, 30);
        cancelButton.addActionListener(e -> dialog.dispose());

        // Make the dialog layered and add components
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, dialog.getWidth(), dialog.getHeight());
        layeredPane.add(backgroundLabel, Integer.valueOf(0)); // Background at the bottom layer
        layeredPane.add(textField, Integer.valueOf(1)); // Text field above background
        layeredPane.add(submitButton, Integer.valueOf(1));
        layeredPane.add(cancelButton, Integer.valueOf(1));

        dialog.add(layeredPane);
    }

    /**
     * Displays the PopUp dialog to the user.
     */
    public void show() {
        dialog.setVisible(true);
    }
}

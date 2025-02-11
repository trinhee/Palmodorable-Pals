package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

/**
 * The PasswordScreen class provides a user interface for entering a password.
 * If the correct password is entered, it allows access to the parent controls panel.
 */
public class PasswordScreen extends JPanel {
    private Image background;

    /**
     * Constructs a new PasswordScreen panel.
     *
     * @param cardLayout The CardLayout for navigating between different screens.
     * @param mainPanel  The main panel containing all the different screens.
     */
    public PasswordScreen(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridBagLayout()); // Use GridBagLayout to center components
        
        try {
            URL bgUrl = getClass().getResource("resources/password_background.png");
            background = ImageIO.read(bgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Add padding between components
        gbc.anchor = GridBagConstraints.CENTER;

        // Create Label for the Text Field
        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passwordLabel.setForeground(Color.BLACK);
        add(passwordLabel, gbc);

        // Create Text Field for Password Entry
        gbc.gridy++;
        JTextField passwordField = new JTextField(20); // Text field with 20 columns
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        add(passwordField, gbc);

        // Request focus for the password field to allow immediate typing
        passwordField.requestFocusInWindow();

        // Add ActionListener to detect when the user presses Enter
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = passwordField.getText().trim();
                if ("study hard".equalsIgnoreCase(input)) {
                    cardLayout.show(mainPanel, "Parent"); // Switch to Parent class panel
                    passwordField.setText(""); // Clear the text field
                } else {
                    JOptionPane.showMessageDialog(PasswordScreen.this,
                            "Incorrect password. Please try again.",
                            "Invalid Password",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add KeyListener to detect when the user presses ESC
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    passwordField.setText(""); // Clear the text field
                }
            }
        });

        // Add functionality to move back to the main menu when ESC is pressed on the panel
        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);
    }

    /**
     * Paints the background image of the panel.
     *
     * @param g The Graphics object used to draw the background image.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

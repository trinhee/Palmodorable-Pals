package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PasswordScreen extends JPanel {
    public PasswordScreen(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridBagLayout()); // Use GridBagLayout to center components
        setBackground(Color.WHITE); // Set background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Add padding between components
        gbc.anchor = GridBagConstraints.CENTER;

        // Create Label for the Text Field
        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(passwordLabel, gbc);

        // Create Text Field for Password Entry
        gbc.gridy++;
        JTextField passwordField = new JTextField(20); // Text field with 20 columns
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        add(passwordField, gbc);

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

        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);
    }
}
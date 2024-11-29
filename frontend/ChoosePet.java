import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChoosePet extends JPanel {
    public ChoosePet(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Stack elements vertically
        setBackground(Color.LIGHT_GRAY);

        // Title
        JLabel title = new JLabel("Adopt a Pet!");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(50)); // Add vertical spacing
        add(title);

        // Buttons
        JButton dogButton = createButton("Dog");
        JButton catButton = createButton("Cat");
        JButton birdButton = createButton("Bird");

        // Button actions
        dogButton.addActionListener(e -> System.out.println("Dog button clicked"));
        catButton.addActionListener(e -> System.out.println("Cat button clicked"));
        birdButton.addActionListener(e -> System.out.println("Bird button clicked"));

        add(Box.createVerticalStrut(20)); // Add spacing between title and buttons
        add(dogButton);
        add(Box.createVerticalStrut(10)); // Spacing between buttons
        add(catButton);
        add(Box.createVerticalStrut(10)); // Spacing between buttons
        add(birdButton);

        // Back button (Optional)
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu")); // Return to the main menu
        add(Box.createVerticalStrut(20));
        add(backButton);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40)); // Set button size
        return button;
    }
}

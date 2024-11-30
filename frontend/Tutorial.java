import javax.swing.*;
import java.awt.*;

public class Tutorial extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Tutorial(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Tutorial");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Tutorial Content
        String tutorialText = """
                Welcome to the game tutorial!
                
                1. Create or load an existing pet.
                2. Feed your pet by clicking on the food icons.
                3. Study to take care of your pet.
                4. Complete daily challenges to earn rewards.
                5. Explore the game world and unlock achievements.
                
                Have fun and enjoy the game!
                """;

        JTextArea textArea = new JTextArea(tutorialText);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu")); // Return to the main menu
        add(backButton, BorderLayout.SOUTH);

        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);
    }
}

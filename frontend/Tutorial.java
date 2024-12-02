package frontend;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class Tutorial extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Image background;

    public Tutorial(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        // Load background image
        try {
            URL bgUrl = getClass().getResource("resources/tutorial_background.jpg");
            if (bgUrl == null) {
                throw new RuntimeException("Resource not found: /tutorial_background.jpg");
            }
            background = ImageIO.read(bgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tutorial Image Centered on the Panel
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            URL imageUrl = getClass().getResource("resources/tutorial_text.png");
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: /tutorial_text.png");
            }
            ImageIcon imageIcon = new ImageIcon(ImageIO.read(imageUrl));
            imageLabel.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(imageLabel, BorderLayout.CENTER);

        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

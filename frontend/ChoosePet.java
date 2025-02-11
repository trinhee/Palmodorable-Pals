package frontend;

import javax.swing.*;
import backend.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * @code ChoosePet class represents a panel where users can select a pet to adopt in the game.
 * It provides animated buttons for selecting different types of pets and manages the transition onto the game screen
 */

public class ChoosePet extends JPanel {
    private Timer animationTimer;
    private int currentFrame = 0;
    private BufferedImage background;
    private BufferedImage[] dogFrames;
    private BufferedImage[] catFrames;
    private BufferedImage[] birdFrames;

    private Menu menu;
    private JFrame parentFrame;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private GameManager gameManager;


    public ChoosePet(CardLayout cardLayout, JPanel mainPanel, Menu menu, JFrame parentFrame) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.menu = menu;
        this.parentFrame = parentFrame;
        this.gameManager = GameManager.getInstance();
        // Load background image
        try {
            URL bgUrl = getClass().getResource("resources/choose_pet_background.jpg");
            if (bgUrl == null) {
                throw new RuntimeException("Resource not found: /choose_pet_background.png");
            }
            background = ImageIO.read(bgUrl);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        setLayout(new BorderLayout());

        // Image Title at the Top Center
        JLabel imageTitle = new JLabel();
        imageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        imageTitle.setVerticalAlignment(SwingConstants.CENTER);
        try {
            URL titleImageURL = getClass().getResource("resources/adopt_title.png");
            if (titleImageURL == null) {
                throw new RuntimeException("Resource not found: /adopt_title.png");
            }
            ImageIcon titleIcon = new ImageIcon(titleImageURL);
            imageTitle.setIcon(titleIcon);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        add(imageTitle, BorderLayout.NORTH);

        // Load sprite sheets
        dogFrames = loadSpriteSheet("resources/dog_idle.png", 48, 48);
        catFrames = loadSpriteSheet("resources/cat_idle.png", 48, 48);
        birdFrames = loadSpriteSheet("resources/bird_idle.png", 32, 32);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Allow the background to show through
        buttonPanel.setBackground(new Color(0, 0, 0, 0)); // Transparent panel background

        // Constraints for centering buttons with increased spacing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(100, 100, 100, 100); // Double the spacing (Insets for top, left, bottom, right)

        // Animated Buttons
        JButton dogButton = createAnimatedButton(dogFrames, "Dog", 384, 384);
        JButton catButton = createAnimatedButton(catFrames, "Cat", 384, 384);
        JButton birdButton = createAnimatedButton(birdFrames, "Bird", 256, 256);

        dogButton.addActionListener(e -> showPopUp("resources/pop_up.png", "", 0));
        catButton.addActionListener(e -> showPopUp("resources/pop_up.png", "", 1));
        birdButton.addActionListener(e -> showPopUp("resources/pop_up.png", "",2));

        // Add buttons to the panel
        buttonPanel.add(dogButton, gbc);
        gbc.gridx++;
        buttonPanel.add(catButton, gbc);
        gbc.gridx++;
        buttonPanel.add(birdButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);

        // Start the animation timer
        startAnimation();


    }

    /**
     * paints the component by drawing the background image
     * @param g the {@link Graphics} object used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Loads a sprite sheet and splits into amount of frames
     * @param resourcePath the path to the sprite sheet
     * @param frameWidth the width of each frame
     * @param frameHeight the height of reach frame
     * @return an array of {@link BufferedImage} objects representing the frames
     */
    private BufferedImage[] loadSpriteSheet(String resourcePath, int frameWidth, int frameHeight) {
        try {
            URL spriteSheetURL = getClass().getResource(resourcePath);
            if (spriteSheetURL == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            BufferedImage spriteSheet = ImageIO.read(spriteSheetURL);

            int frameCount = spriteSheet.getWidth() / frameWidth;

            if (spriteSheet.getHeight() < frameHeight) {
                throw new RuntimeException("Sprite sheet height is smaller than expected: " + resourcePath);
            }

            BufferedImage[] frames = new BufferedImage[frameCount];
            for (int i = 0; i < frameCount; i++) {
                frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }
            return frames;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    /**
     * Creates an animated button with hover effects
     * @param frames the animation frames for the button
     * @param name the name of the button
     * @param width the width of button
     * @param height the height of button
     * @return the created {@link} JButton
     */

    private JButton createAnimatedButton(BufferedImage[] frames, String name, int width, int height) {
        // Scale frames to desired size
        BufferedImage[] scaledFrames = new BufferedImage[frames.length];
        for (int i = 0; i < frames.length; i++) {
            scaledFrames[i] = scaleImage(frames[i], width, height);
        }

        JButton button = new JButton(new ImageIcon(scaledFrames[0])); // Set the first frame
        button.setPreferredSize(new Dimension(width, height)); // Adjust button size
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        // button.setBorder(new LineBorder(Color.ORANGE, 2));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            private boolean isHovered = false;

            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                animationTimer.stop(); // Pause animation
                Image hoverImage = PanelUtils.hoverEffect(scaledFrames[currentFrame], 0.75f);
                button.setIcon(new ImageIcon(hoverImage));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                animationTimer.start(); // Resume animation
                button.setIcon(new ImageIcon(scaledFrames[currentFrame])); // Restore current frame
            }
        });

        // Set animation update
        button.putClientProperty("frames", scaledFrames);

        return button;
    }
    
    /**
     * Shows the popup window 
     * @param imagePath the path to the pop up image
     * @param placeholder a placeholder string
     * @param petType type of pet selected
     */
    private void showPopUp(String imagePath, String placeholder, int petType) {
        PopUp popup = new PopUp(parentFrame, imagePath, placeholder, e -> {

            String input = e.getActionCommand().trim();
            System.out.println(input);
            Pet pet = new Pet(input,petType,0,0);
            pet.saveToFile();
            GameManager gameManager = new GameManager(input, petType);
            StatisticsTracker.setInstance(new StatisticsTracker(input));
            GameManager.setInstance(gameManager);

            GameScreen gameScreen = new GameScreen(cardLayout, mainPanel);
            mainPanel.add(gameScreen, "Game");
            cardLayout.show(mainPanel, "Game");

            // Fade out the current music
            Music.getInstance().fadeOut(3000);
        });
        popup.show();
    }

    /**
     * starts the animation timer for updating button images
     */
    private void startAnimation() {
        animationTimer = new Timer(100, e -> {
            currentFrame = (currentFrame + 1) % 4; // 4 frames in each sprite sheet
            for (Component comp : getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel buttonPanel = (JPanel) comp;
                    for (Component buttonComp : buttonPanel.getComponents()) {
                        if (buttonComp instanceof JButton) {
                            JButton button = (JButton) buttonComp;
                            BufferedImage[] frames = (BufferedImage[]) button.getClientProperty("frames");
                            if (frames != null) {
                                button.setIcon(new ImageIcon(frames[currentFrame]));
                            }
                        }
                    }
                }
            }
        });
        animationTimer.start();
    }

    /**
     * 
     * @param originalImage orignal image to scale
     * @param width
     * @param height
     * @return a {@link BufferedImage} of the scaled image
     */
    private BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import javax.swing.event.AncestorEvent;

public class GameScreen extends JPanel {
    private BufferedImage background;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Timer fadeTimer;
    private float fadeOpacity;
    private JButton startButton;
    private JLabel countdownLabel;
    private Timer countdownTimer;
    private BufferedImage[] petFrames;
    private Timer animationTimer;
    private int currentFrame = 0; // Added currentFrame for animation

    private String petType; // Added pet type

    public GameScreen(CardLayout cardLayout, JPanel mainPanel, String petType) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.petType = petType;

        // Load background image
        try {
            URL bgUrl = getClass().getResource("/game_background.png");
            if (bgUrl == null) {
                throw new RuntimeException("Resource not found: /game_background.png");
            }
            background = ImageIO.read(bgUrl);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Set layout
        setLayout(null);

        // Enable escape key to return to the menu
        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);

        // Add a start button and initialize the animated pet sprite
        initializeStartButton();
        initializePetSprite();

        // Detect when the panel is shown
        addAncestorListener(new AncestorListenerAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                startFadeIn(); // Trigger fade-in when the panel is shown
            }
        });
    }

    private void initializeStartButton() {
        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setFocusPainted(false);

        // Default position and size
        startButton.setBounds(755, 492, 300, 100); // on the screen
        startButton.addActionListener(e -> {
            System.out.println("Start button clicked!");
            Music.getInstance().stop();
            Music.getInstance().play("/lofi.wav");
            remove(startButton); // Remove the button
            initializeCountdown(1, 30, 0); // set to 1 hour 30 for test
            revalidate();
            repaint();
        });

        add(startButton);
    }

    private void initializeCountdown(int hours, int minutes, int seconds) {
        countdownLabel = new JLabel(formatTime(hours, minutes, seconds), SwingConstants.CENTER);
        countdownLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        countdownLabel.setForeground(Color.RED);
        countdownLabel.setBounds(755, 492, 300, 100); // on the screen

        add(countdownLabel);

        // Countdown logic
        int[] remainingTime = {hours * 3600 + minutes * 60 + seconds}; // Convert to total seconds
        countdownTimer = new Timer(1000, e -> {
            remainingTime[0]--;
            if (remainingTime[0] > 0) {
                int h = remainingTime[0] / 3600;
                int m = (remainingTime[0] % 3600) / 60;
                int s = remainingTime[0] % 60;
                countdownLabel.setText(formatTime(h, m, s));
            } else {
                countdownTimer.stop();
                remove(countdownLabel);
                revalidate();
                repaint();
                System.out.println("Countdown finished!");
                // Trigger game logic here
            }
        });
        countdownTimer.start();
    }

    private String formatTime(int hours, int minutes, int seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void startFadeIn() {
        fadeOpacity = 0.0f; // Reset fade opacity
        if (fadeTimer != null && fadeTimer.isRunning()) {
            fadeTimer.stop();
        }

        // Timer to handle background fade-in
        fadeTimer = new Timer(30, e -> { // Slower fade with smaller increments
            fadeOpacity = Math.min(fadeOpacity + 0.007f, 1.0f); // Gradually increase opacity
            repaint();

            if (fadeOpacity >= 1.0f) { // Stop timer once fade-in is complete
                fadeTimer.stop();
            }
        });
        fadeTimer.start();
    }

    private void initializePetSprite() {
        String resourcePath;
        int frameWidth, frameHeight, scaledWidth, scaledHeight;

        // Determine the resource path and dimensions based on pet type
        switch (petType.toLowerCase()) {
            case "dog":
                resourcePath = "/dog_idle.png";
                frameWidth = 48;
                frameHeight = 48;
                scaledWidth = 384;
                scaledHeight = 384;
                break;
            case "cat":
                resourcePath = "/cat_idle.png";
                frameWidth = 48;
                frameHeight = 48;
                scaledWidth = 384;
                scaledHeight = 384;
                break;
            case "bird":
                resourcePath = "/bird_idle.png";
                frameWidth = 32;
                frameHeight = 32;
                scaledWidth = 256;
                scaledHeight = 256;
                break;
            default:
                throw new IllegalArgumentException("Invalid pet type: " + petType);
        }

        // Load the sprite sheet
        petFrames = loadSpriteSheet(resourcePath, frameWidth, frameHeight);

        // Start animation timer
        animationTimer = new Timer(100, e -> {
            currentFrame = (currentFrame + 1) % petFrames.length;
            repaint();
        });
        animationTimer.start();
    }

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw black background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the background image with fade effect
        if (background != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeOpacity));
            g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            g2d.setComposite(originalComposite);
        }

        // Draw the animated pet sprite
        if (petFrames != null && petFrames.length > 0) {
            int x = petType.equalsIgnoreCase("bird") ? 750 : 720;
            int y = petType.equalsIgnoreCase("bird") ? 720 : 635;
            int scaledWidth = petType.equalsIgnoreCase("bird") ? 256 : 384;
            int scaledHeight = petType.equalsIgnoreCase("bird") ? 256 : 384;
            g2d.drawImage(petFrames[currentFrame], x, y, scaledWidth, scaledHeight, this);
        }

        g2d.dispose();
    }
}

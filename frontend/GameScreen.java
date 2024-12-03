package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import javax.swing.event.AncestorEvent;

public class GameScreen extends JPanel {
    private BufferedImage background;
    private GameManager gameManager;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Timer fadeTimer;
    private float fadeOpacity;
    private JButton startButton;
    private JLabel countdownLabel;
    private Timer countdownTimer;
    private BufferedImage[] petFrames;
    private Timer animationTimer;
    private int currentFrame = 0;
    private int petType;
    private Settings settings;
    private JButton sleepButton;
    private JButton vetButton;
    private JButton exerciseButton;
    private JButton inventoryButton;
    private JProgressBar healthBar;
    private JProgressBar sleepBar;
    private JProgressBar hungerBar;
    private JProgressBar happinessBar;

    public GameScreen(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.settings = Settings.getInstance();
        this.petType = GameManager.getInstance().getCurrentPet().getPetType();
        this.gameManager = GameManager.getInstance();

        // Load background image
        try {
            URL bgUrl = getClass().getResource("resources/game_background.png");
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

        initializeStartButton();
        initializeSleepButton();
        initializeInventoryButton();
        initializeVetButton();
        initializeExerciseButton();
        initializePetSprite(petType);
        initializeStatusBars();

        // Detect when the panel is shown
        addAncestorListener(new AncestorListenerAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                startFadeIn();
            }
        });
    }

    private void initializeStartButton() {
        startButton = new JButton();
        startButton.setFocusPainted(false);

        try {
            URL startButtonUrl = getClass().getResource("resources/start_button.png");
            if (startButtonUrl == null) {
                throw new RuntimeException("Resource not found: /start_button.png");
            }
            BufferedImage image = ImageIO.read(startButtonUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            startButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startButton.setBounds(755, 492, 300, 100);
        startButton.addActionListener(e -> {
            System.out.println("Start button clicked!");
            Music.getInstance().stop();
            Music.getInstance().play("resources/lofi.wav");
            Music.getInstance().setVolume(50 / 100f);

            startButton.setVisible(false);

            int studyTime = settings.getStudyTime();
            int hours = studyTime / 60;
            int minutes = studyTime % 60;

            initializeCountdown(hours, minutes);
            revalidate();
            repaint();
        });

        add(startButton);
    }

    private void initializeCountdown(int hours, int minutes) {
        countdownLabel = new JLabel(formatTime(hours, minutes, 0), SwingConstants.CENTER);
        countdownLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        countdownLabel.setForeground(Color.RED);
        countdownLabel.setBounds(755, 492, 300, 100);

        add(countdownLabel);

        int[] remainingTime = {hours * 3600 + minutes * 60};
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
            }
        });
        countdownTimer.start();
    }

    private String formatTime(int hours, int minutes, int seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void startFadeIn() {
        fadeOpacity = 0.0f;
        if (fadeTimer != null && fadeTimer.isRunning()) {
            fadeTimer.stop();
        }

        fadeTimer = new Timer(30, e -> {
            fadeOpacity = Math.min(fadeOpacity + 0.007f, 1.0f);
            repaint();

            if (fadeOpacity >= 1.0f) {
                fadeTimer.stop();
            }
        });
        fadeTimer.start();
    }

    private void initializePetSprite(int petType) {
        String resourcePath;
        int frameWidth, frameHeight;

        switch (petType) {
            case 0:
                resourcePath = "resources/dog_idle.png";
                frameWidth = 48;
                frameHeight = 48;
                break;
            case 1:
                resourcePath = "resources/cat_idle.png";
                frameWidth = 48;
                frameHeight = 48;
                break;
            case 2:
                resourcePath = "resources/bird_idle.png";
                frameWidth = 32;
                frameHeight = 32;
                break;
            default:
                throw new IllegalArgumentException("Invalid pet type: " + petType);
        }

        petFrames = loadSpriteSheet(resourcePath, frameWidth, frameHeight);

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

    private void initializeStatusBars() {
        healthBar = new JProgressBar(0, 100);
        healthBar.setForeground(Color.RED);
        healthBar.setBounds(20, 850, 150, 20);
        add(healthBar);

        sleepBar = new JProgressBar(0, 100);
        sleepBar.setForeground(Color.BLUE);
        sleepBar.setBounds(20, 880, 150, 20);
        add(sleepBar);

        hungerBar = new JProgressBar(0, 100);
        hungerBar.setForeground(Color.ORANGE);
        hungerBar.setBounds(20, 910, 150, 20);
        add(hungerBar);

        happinessBar = new JProgressBar(0, 100);
        happinessBar.setForeground(Color.GREEN);
        happinessBar.setBounds(20, 940, 150, 20);
        add(happinessBar);

        updateStatusBars();
    }

    private void updateStatusBars() {
        healthBar.setValue(gameManager.getCurrentPet().getHealth());
        sleepBar.setValue(gameManager.getCurrentPet().getSleep());
        hungerBar.setValue(gameManager.getCurrentPet().getFullness());
        happinessBar.setValue(gameManager.getCurrentPet().getHappiness());
    }

    private void initializeInventoryButton() {
        inventoryButton = new JButton();
        inventoryButton.setFocusPainted(false);
        inventoryButton.setBorderPainted(false);
        inventoryButton.setContentAreaFilled(false);

        try {
            URL imageUrl = getClass().getResource("resources/inventory.png");
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: /inventory.png");
            }
            BufferedImage image = ImageIO.read(imageUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            inventoryButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        inventoryButton.setBounds(1500, 100, 200, 200);
        inventoryButton.addActionListener(e -> {
            System.out.println("Inventory button clicked!");
            cardLayout.show(mainPanel, "Inventory");
            updateStatusBars();
            revalidate();
            repaint();
        });
        add(inventoryButton);
    }

    private void initializeSleepButton() {
        sleepButton = new JButton();
        sleepButton.setFocusPainted(false);
        sleepButton.setBorderPainted(false);
        sleepButton.setContentAreaFilled(false);

        try {
            URL imageUrl = getClass().getResource("resources/sleep.png");
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: /sleep.png");
            }
            BufferedImage image = ImageIO.read(imageUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            sleepButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sleepButton.setBounds(1100, 100, 200, 200);
        sleepButton.addActionListener(e -> {
            gameManager.sleepPet();
            System.out.println("Sleep button clicked!");
            updateStatusBars();
            revalidate();
            repaint();
        });
        add(sleepButton);
    }

    private void initializeVetButton() {
        vetButton = new JButton();
        vetButton.setFocusPainted(false);
        vetButton.setBorderPainted(false);
        vetButton.setContentAreaFilled(false);

        try {
            URL imageUrl = getClass().getResource("resources/vet.png");
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: /vet.png");
            }
            BufferedImage image = ImageIO.read(imageUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            vetButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        vetButton.setBounds(700, 100, 200, 200);
        vetButton.addActionListener(e -> {
            gameManager.visitVet();
            System.out.println("Vet button clicked!");
            updateStatusBars();
            revalidate();
            repaint();
        });
        add(vetButton);
    }

    private void initializeExerciseButton() {
        exerciseButton = new JButton();
        exerciseButton.setFocusPainted(false);
        exerciseButton.setBorderPainted(false);
        exerciseButton.setContentAreaFilled(false);

        try {
            URL imageUrl = getClass().getResource("resources/exercise.png");
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: /exercise.png");
            }
            BufferedImage image = ImageIO.read(imageUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            exerciseButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        exerciseButton.setBounds(300, 100, 200, 200);
        exerciseButton.addActionListener(e -> {
            gameManager.exercisePet();
            System.out.println("Exercise button clicked!");
            updateStatusBars();
            revalidate();
            repaint();
        });
        add(exerciseButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (background != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeOpacity));
            g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            g2d.setComposite(originalComposite);
        }

        if (petFrames != null && petFrames.length > 0) {
            int x, y, scaledWidth, scaledHeight;
            switch (petType) {
                case 0:
                    x = 720;
                    y = 635;
                    scaledWidth = 384;
                    scaledHeight = 384;
                    break;
                case 1:
                    x = 720;
                    y = 635;
                    scaledWidth = 384;
                    scaledHeight = 384;
                    break;
                case 2:
                    x = 750;
                    y = 720;
                    scaledWidth = 256;
                    scaledHeight = 256;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid pet type: " + petType);
            }
            g2d.drawImage(petFrames[currentFrame], x, y, scaledWidth, scaledHeight, this);
        }

        g2d.dispose();
    }
}

package frontend;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

/**
 * The Loading class represents a loading screen with an animated cat sprite and a loading progress bar.
 * It uses Swing components to create the animation and the loading effect.
 */
public class Loading extends JPanel {
    private BufferedImage spriteSheet;
    private BufferedImage[] frames;
    private int frameWidth = 66; // Adjust as per your sprite dimensions
    private int frameHeight = 66; // Adjust as per your sprite dimensions
    private int scaleFactor = 3; // Scale the sprite
    private int currentFrame = 0;
    private int loadingProgress = 0;
    private Timer animationTimer;
    private Timer loadingTimer;
    private Music music;

    /**
     * Constructs the Loading screen panel.
     * This panel includes an animated cat sprite and a loading progress bar.
     */
    public Loading() {
        setBackground(Color.DARK_GRAY);

        // Load the sprite sheet and extract frames
        loadSpriteSheet();

        // Timer to update the frame for animation
        animationTimer = new Timer(100, e -> {
            currentFrame = (currentFrame + 1) % frames.length;
            repaint();
        });
        animationTimer.start();

        // Timer to simulate the loading progress
        loadingTimer = new Timer(5, e -> {
            if (loadingProgress < 100) {
                loadingProgress++;
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
                Music.getInstance().play("resources/Happy.wav");
                Music.getInstance().setVolume(50 / 100f);
            }
        });
        loadingTimer.start();
    }

    /**
     * Loads the sprite sheet containing the cat animation frames.
     * Extracts individual frames from the sprite sheet.
     */
    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("resources/cat_loading.png"));
            int frameCount = 5; // 5 frames in the sprite sheet
            frames = new BufferedImage[frameCount];
            for (int i = 0; i < frameCount; i++) {
                frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Paints the components of the loading screen, including the animation and the loading bar.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw dark gray background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the cat animation
        if (frames != null && loadingProgress < 100) {
            int scaledWidth = frameWidth * scaleFactor;
            int scaledHeight = frameHeight * scaleFactor;
            int x = (getWidth() - scaledWidth) / 2;
            int y = (getHeight() - scaledHeight) / 2 - 50;
            g.drawImage(frames[currentFrame], x, y, scaledWidth, scaledHeight, null);
        }

        // Draw the loading bar
        if (loadingProgress < 100) {
            int barWidth = 300;
            int barHeight = 20;
            int barX = (getWidth() - barWidth) / 2;
            int barY = (getHeight() / 2) + 100;

            g.setColor(Color.WHITE);
            g.drawRect(barX, barY, barWidth, barHeight);

            g.setColor(Color.GREEN);
            g.fillRect(barX + 1, barY + 1, (loadingProgress * (barWidth - 2)) / 100, barHeight - 2);

            g.setColor(Color.WHITE);
            g.drawString(loadingProgress + "%", barX + barWidth / 2 - 10, barY + barHeight - 5);
        }
    }

    /**
     * Checks if the loading is complete.
     *
     * @return true if loading progress is 100%, false otherwise.
     */
    public boolean isLoadingComplete() {
        return loadingProgress >= 100;
    }

    /**
     * Gets the Music instance associated with the loading screen.
     *
     * @return The Music instance.
     */
    public Music getMusicInstance() {
        return music;
    }
}

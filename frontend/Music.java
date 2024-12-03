package frontend;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Music {
    private static final Music instance = new Music();
    private Clip clip;
    private FloatControl volumeControl;

    private Music() {
        // Private constructor to enforce singleton pattern
    }

    /**
     * Gets the singleton instance of the Music class.
     *
     * @return The singleton Music instance.
     */
    public static Music getInstance() {
        return instance;
    }

    /**
     * Plays a music file from the specified resource path.
     *
     * @param resourcePath Path to the audio file in the resources folder.
     */
    public void play(String resourcePath) {
        stop(); // Stop any currently playing music before starting new music
        try {
            URL soundURL = getClass().getResource(resourcePath);
            if (soundURL == null) {
                throw new RuntimeException("Music file not found: " + resourcePath);
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Get volume control
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing music.
     */
    public void stop() {
        if (clip != null && clip.isOpen()) {
            clip.stop();
            clip.close();
            clip = null; // Clear the clip reference
            volumeControl = null; // Clear the volume control reference
        }
    }

    /**
     * Fades out the music over a specified duration.
     *
     * @param duration The fade-out duration in milliseconds.
     */
    public void fadeOut(int duration) {
        if (clip == null || volumeControl == null || !clip.isOpen()) {
            System.out.println("No music to fade out.");
            return;
        }

        float initialVolume = volumeControl.getValue(); // Current volume
        float minVolume = volumeControl.getMinimum(); // Minimum volume

        int steps = 50; // Number of fade steps
        long stepDuration = duration / steps; // Duration per step
        float volumeStep = (initialVolume - minVolume) / steps;

        new Thread(() -> {
            float[] currentVolume = {volumeControl.getValue()}; // Use an array to encapsulate the volume variable
            for (int i = 0; i < steps; i++) {
                currentVolume[0] -= volumeStep;
                if (currentVolume[0] < minVolume) {
                    currentVolume[0] = minVolume;
                }
                volumeControl.setValue(currentVolume[0]);
                try {
                    Thread.sleep(stepDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stop(); // Stop the clip after fading out
        }).start();
    }

    /**
     * Adjusts the volume to a specific level.
     *
     * @param volume A float value between 0.0 (mute) and 1.0 (full volume).
     */
    public void setVolume(float volume) {
        if (volumeControl == null) {
            System.out.println("Volume control is not available.");
            return;
        }

        float minVolume = volumeControl.getMinimum();
        float maxVolume = volumeControl.getMaximum();
        float adjustedVolume = minVolume + (maxVolume - minVolume) * volume;

        volumeControl.setValue(adjustedVolume);
    }
}

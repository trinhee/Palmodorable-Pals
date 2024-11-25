import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Settings {

    private int studyTime;
    private int breakTime;
    private int targetStudyTime;
    private boolean backgroundMusic; // Changed to boolean
    private boolean isParent;
    private String petName;

    private static final String FILE_NAME = "data_handling/game_settings.csv";

    public Settings(String petName) {
        loadSettings(petName);
    }

    private void loadSettings(String petName) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean isFirstRow = true;

            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; 
                }

                String[] values = line.split(",");

                // Check if the petName matches the current row
                if (values[0].equalsIgnoreCase(petName)) {
                    this.petName = values[0];
                    this.backgroundMusic = parseBoolean(values[1]); // Parse background music as boolean
                    this.isParent = parseBoolean(values[2]);
                    this.studyTime = Integer.parseInt(values[3]);
                    this.breakTime = Integer.parseInt(values[4]);
                    this.targetStudyTime = Integer.parseInt(values[5]);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);

                // If the line matches the pet name, update it
                if (values[0].equalsIgnoreCase(this.petName)) {
                    line = String.format("%s,%s,%d,%d,,%d,%d,%d",
                            this.petName,
                            values[1], // player_id remains unchanged
                            this.backgroundMusic ? 1 : 0, // Write boolean as 1/0
                            this.isParent ? 1 : 0,
                            this.studyTime,
                            this.breakTime,
                            this.targetStudyTime);
                    updated = true;
                }

                lines.add(line); // Add the (updated or unchanged) line to the list
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        // If the pet name was not found, throw an exception
        if (!updated) {
            throw new IllegalArgumentException("Pet not found in the file: " + this.petName);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }

            System.out.println("Settings successfully saved for pet: " + this.petName);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    private boolean parseBoolean(String value) {
        return "1".equals(value);
    }

    public void setStudyTime(int time) {
        this.studyTime = time;
    }

    public int getStudyTime() {
        return this.studyTime;
    }

    public void setBreakTime(int time) {
        this.breakTime = time;
    }

    public int getBreakTime() {
        return this.breakTime;
    }

    public void setTargetStudyTime(int time) {
        this.targetStudyTime = time;
    }

    public int getTargetStudyTime() {
        return this.targetStudyTime;
    }

    public boolean getBackgroundMusic() {
        return this.backgroundMusic;
    }

    public void setBackgroundMusic(boolean backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    public boolean isParent() {
        return this.isParent;
    }

    public String getPetName() {
        return this.petName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Settings for Pet: ").append(this.petName).append("\n");
        sb.append("Background Music: ").append(this.backgroundMusic ? "On" : "Off").append("\n");
        sb.append("Is Parent: ").append(this.isParent ? "Yes" : "No").append("\n");
        sb.append("Study Time: ").append(this.studyTime).append(" minutes\n");
        sb.append("Break Time: ").append(this.breakTime).append(" minutes\n");
        sb.append("Target Study Time: ").append(this.targetStudyTime).append(" sessions\n");
        return sb.toString();
    }
}

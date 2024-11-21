import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Settings {

    private int studyTime;
    private int breakTime;
    private int targetStudyTime;
    private String backgroundMusic;
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
                    this.backgroundMusic = parseBackgroundMusic(values[2]);
                    this.isParent = Integer.parseInt(values[3]) == 1;
                    this.studyTime = Integer.parseInt(values[5]);
                    this.breakTime = Integer.parseInt(values[6]);
                    this.targetStudyTime = Integer.parseInt(values[7]);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    private String parseBackgroundMusic(String value) {
        switch (value) {
            case "1":
                return "Lofi";
            case "2":
                return "Classical";
            case "3":
                return "Jazz";
            default:
                return "Unknown";
        }
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

    public String getBackgroundMusic() {
        return this.backgroundMusic;
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
        sb.append("Background Music: ").append(this.backgroundMusic).append("\n");
        sb.append("Is Parent: ").append(this.isParent ? "Yes" : "No").append("\n");
        sb.append("Study Time: ").append(this.studyTime).append(" minutes\n");
        sb.append("Break Time: ").append(this.breakTime).append(" minutes\n");
        sb.append("Target Study Time: ").append(this.targetStudyTime).append(" sessions\n");
        return sb.toString();
    }
    
}
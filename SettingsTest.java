public class SettingsTest {
    public static void main(String[] args) {
        // Load settings for pet "Buddy"
        Settings settings = new Settings("Buddy");
        System.out.println(settings);

        settings.setBackgroundMusic(false); // Turn off background music
        settings.setStudyTime(40);
        settings.setBreakTime(10);
        settings.saveToFile();
        
        
    }
}
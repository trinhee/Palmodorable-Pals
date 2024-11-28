public class SettingsTest {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        // Load settings for pet "Buddy"
        Settings settings = new Settings("Buddy", "data_handling/game_settings.csv");
        System.out.println(settings);

        settings.setBackgroundMusic(false); // Turn off background music
        settings.setStudyTime(40);
        settings.setBreakTime(10);
        settings.saveToFile();
        
        
    }
}
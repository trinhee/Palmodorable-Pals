public class SettingsTest {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        // Load settings for pet "Buddy"
        Settings settings = new Settings("data_handling/game_settings.csv");
        System.out.println(settings);

        settings.setBackgroundMusic(false); // Turn off background music
        settings.setStudyTime(600); 
        settings.setBreakTime(20);
        settings.saveToFile();
        
        
    }
}
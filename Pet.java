
/**
 * Pet to be played with in the game.
 * 
 * @author Kaegan Walker Fulton
 */

public class Pet {

    /**Pet's name */
    public String name;
    /**Pet's health */
    public int health;
    /**Pet's sleep */
    public int sleep;
    /**Pets fullness */
    public int fullness;
    /**Pet's happiness */
    public int happiness;
    /**Pet's maximum health */
    public int maxHealth;
    /**Pet's maximum sleep */
    public int maxSleep;
    /**Pet's maximum fullness */
    public int maxFullness;
    /**Pet's maximum happiness */
    public int maxHappiness;
    /**The effectiveness (stat increase) of sleep */
    public int sleepEffectiveness;
    /**The effectiveness (stat increase) of play */
    public int playEffectiveness;

    /**
     * Pet Constructor. Initializes max's to given values.
     * Initializes current values to max's.
     * Sets sleep and play effectiveness to given value.
     * 
     * @param name Pet's name
     * @param maxHealth Pet's maximum health
     * @param maxSleep Pet's maximum sleep
     * @param maxFullness Pet's maximum fullness
     * @param maxHappiness Pet's maximum happiness
     * @param sleepEffectiveness Effectiveness(stat increase) of sleep
     * @param playEffectiveness Effectiveness(stat increase) of play
     */
    Pet(String name, int sleepEffectiveness, int playEffectiveness) {
        this.name = name;
        this.maxHealth = 100;
        this.maxSleep = 100;
        this.maxFullness = 100;
        this.maxHappiness = 100;
        this.health = this.maxHealth;
        this.sleep = this.maxSleep;
        this.fullness = this.maxFullness;
        this.happiness = this.maxHappiness;
        this.sleepEffectiveness = sleepEffectiveness;
        this.playEffectiveness = playEffectiveness;
        
    }

    
    /** 
     * Name Setter.
     * 
     * @param name pet's name
     */
    public void setName(String name){
        this.name = name;
    }

    
    /** 
     * Health Setter.
     * 
     * @param health pet's health
     */
    public void setHealth(int health){
        this.health = health;
    }

    
    /** 
     * Maximum Health Setter.
     * 
     * @param maxHealth pet's maximum health
     */
    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }

    
    /** 
     * Sleep Setter.
     * 
     * @param sleep pet's sleep
     */
    public void setSleep(int sleep){
        this.sleep = sleep;
    }

    
    /** 
     * Maximum Sleep Setter.
     * 
     * @param maxSleep pet's maximum sleep
     */
    public void setMaxSleep(int maxSleep){
        this.maxSleep = maxSleep;
    }

    
    /** 
     * Fullness Setter.
     * 
     * @param fullness pet's sleep
     */
    public void setFullness(int fullness){
        this.fullness = fullness;
    }
    
    
    /** 
     * Maximum Fullness Setter.
     * 
     * @param maxFullness pet's maximum fullness
     */
    public void setMaxFullness(int maxFullness){
        this.maxFullness = maxFullness;
    }

    
    /** 
     * Happiness Setter.
     * 
     * @param happiness pet's happiness
     */
    public void setHappiness(int happiness){
        this.happiness = happiness;
    }

    
    /** 
     * Maximum Happiness Setter.
     * 
     * @param maxHappiness pet's maximum hapiness
     */
    public void setMaxHappiness(int maxHappiness){
        this.maxHappiness = maxHappiness;
    }

    
    /** 
     * Sleep Effectiveness Setter.
     * 
     * @param sleepEffectiveness sleep (stat increase) effectiveness
     */
    public void setSleepEffectiveness(int sleepEffectiveness){
        this.sleepEffectiveness = sleepEffectiveness;
    }

    
    /** 
     * Play Effectiveness Setter
     * 
     * @param playEffectiveness play (stat increase) effectiveness
     */
    public void setPlayEffectiveness(int playEffectiveness){
        this.playEffectiveness = playEffectiveness;
    }

    
    /** 
     * Name Getter.
     * 
     * @return pet's name
     */
    public String getName(){
        return this.name;
    }

    
    /** 
     * Health Getter.
     * 
     * @return pet's health
     */
    public int getHealth(){
        return this.health;
    }

    
    /**
     * Maximum Health Getter.
     *  
     * @return pet's maximum health
     */
    public int getMaxHealth(){
        return this.maxHealth;
    }

    
    /** 
     * Sleep Getter.
     * 
     * @return pet's sleep
     */
    public int getSleep(){
        return this.sleep;
    }

    
    /**
     * Maximum Sleep Getter.
     *  
     * @return pet's maximum sleep
     */
    public int getMaxSleep(){
        return this.maxSleep;
    }

    
    /** 
     * Fullness Getter.
     * 
     * @return pet's fullness
     */
    public int getFullness(){
        return this.fullness;
    }

    
    /** 
     * Maximum Fullness Getter.
     * 
     * @return pet's maximum fullness
     */
    public int getMaxFullness(){
        return this.maxFullness;
    }

    
    /** 
     * Happiness Getter.
     * 
     * @return pet's happiness
     */
    public int getHappiness(){
        return this.happiness;
    }

    
    /** 
     * Maximum Happiness Setter.
     * 
     * @return pet's maximum happiness
     */
    public int getMaxHappiness(){
        return this.maxHappiness;
    }

    
    /**
     * Sleep Effectiveness Getter.
     * 
     * @return pet's sleep (stat increase) effectiveness
     */
    public int getSleepEffectiveness(){
        return this.sleepEffectiveness;
    }

    
    /** 
     * Play Effectiveness Getter.
     * 
     * @return pet's play (stat increase) effectiveness
     */
    public int getPlayEffectiveness(){
        return this.playEffectiveness;
    }


    /**
     * Adds sleep (stat increase) effectiveness to sleep.
     * 
     */
    public void sleep(){

        if(this.getSleep() + this.getSleepEffectiveness() < this.getSleepEffectiveness()){
            this.setSleep(this.getSleep() + this.getSleepEffectiveness());
        }

        else{
            this.setSleep(this.getMaxSleep());
        }
    }

    
    /** 
     * Applys effect of item to pet.
     * 
     * @param item the item to be used
     */
    public void useItem(Item item){
        item.applyEffect(this);
    }


    /**
     * Adds play (stat increase) effectiveness to happiness.
     * 
     */
    public void play(){

        if(this.getHappiness() + this.getPlayEffectiveness() < this.getMaxHappiness()){
            this.setHappiness(this.getMaxHappiness() + this.getPlayEffectiveness());
        }

        else{
            this.setHappiness(this.getMaxHappiness());
        }
    }

    /**
     * Sets health to maximum health.
     * 
     */
    public void takeToVet(){
        this.setHealth(this.getMaxHealth());
    }

}

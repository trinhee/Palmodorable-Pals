/**
 * Dog class, a subclass of Pet.
 * Represents a dog with additional behaviors specific to dogs.
 * 
 * @author Kaegan Walker Fulton
 */
public class Dog extends Pet {

    /** The breed of the dog */
    private String breed;

    /**
     * Dog Constructor.
     * Initializes the dog with predefined arbitrary values for starting and maximum stats.
     * 
     * @param name Dog's name
     * @param breed Dog's breed
     */
    public Dog(String name, String breed) {
        super(
            name,                 // Name
            100,                  // maxHealth
            50,                   // maxSleep
            80,                   // maxFullness
            90,                   // maxHappiness
            10,                   // sleepEffectiveness
            15                    // playEffectiveness
        );
        this.breed = breed;
        // You can modify the starting values if needed (e.g., partial sleep/fullness)
        this.setSleep(40);  // Example: Dog starts slightly less than fully rested
        this.setFullness(70); // Example: Dog starts with less than full fullness
    }

    /**
     * Breed Getter.
     * 
     * @return Dog's breed
     */
    public String getBreed() {
        return breed;
    }

    /**
     * Breed Setter.
     * 
     * @param breed Dog's breed
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * Makes the dog bark.
     * 
     * @return A string representing the dog's bark.
     */
    public String bark() {
        return this.getName() + " says: Woof! Woof!";
    }

    /**
     * Makes the dog wag its tail, increasing happiness.
     */
    public void wagTail() {
        System.out.println(this.getName() + " is wagging its tail!");
        this.setHappiness(Math.min(this.getHappiness() + 5, this.getMaxHappiness()));
    }

    /**
     * Makes the dog fetch, increasing happiness and reducing sleep.
     */
    public void fetch() {
        System.out.println(this.getName() + " is fetching the ball!");
        this.setHappiness(Math.min(this.getHappiness() + 10, this.getMaxHappiness()));
        this.setSleep(Math.max(this.getSleep() - 5, 0));
    }

    /**
     * Overrides the play method to include a unique dog play behavior.
     */
    @Override
    public void play() {
        System.out.println(this.getName() + " is playing fetch!");
        this.fetch();
    }

    /**
     * Provides a string representation of the dog's state.
     * 
     * @return A string representing the dog's attributes.
     */
    @Override
    public String toString() {
        return "Dog{" +
                "name='" + getName() + '\'' +
                ", breed='" + breed + '\'' +
                ", health=" + getHealth() +
                ", sleep=" + getSleep() +
                ", fullness=" + getFullness() +
                ", happiness=" + getHappiness() +
                '}';
    }
}



/**
 * Generic item class for food and gift
 * @author Kaegan Walker Fulton
 */
public class Item {

    /**The items name */
    public String name;
    /**The item's type */
    public String type;
    /**The item's effectiveness(how much it will increase stat) */
    public int effectiveness;
    
    /**
     * Item constructor. Creates an instance of an Item object. Sets initial values to given values.
     * 
     * @param name Name of item
     * @param type Type of item. gift for Gift, food for Food.
     * @param effectiveness Increase in stat. Happiness for Gift, fullness for Food.
     */
    Item(String name, String type, int effectiveness){

        this.name = name;
        this.type = type;
        this.effectiveness = effectiveness;
    }

    
    /** 
     * Name Setter.
     * 
     * @param name item name
     */
    public void setName(String name){

        this.name = name;

    }

    
    /** 
     * Type Setter.
     * 
     * @param type item type
     */
    public void setType(String type){
        this.type = type;
    }

    
    /**
     * Effectiveness Setter.
     *  
     * @param effectiveness item effectiveness
     */
    public void setEffectiveness(int effectiveness){
        this.effectiveness = effectiveness;
    }

    
    /** 
     * Name Getter.
     * 
     * @return item name
     */
    public String getName(){
        return this.name;
    }

    
    /** 
     * Type Getter.
     * 
     * @return item type
     */
    public String getType(){
        return this.type;
    }

    
    /** 
     * Effectiveness Getter.
     * 
     * @return item effectiveness
     */
    public int getEffectiveness(){
        return this.effectiveness;
    }

    
    /**
     * Updates pet stat (fullness for food, happiness for gift).
     *  
     * @param pet The pet in the game
     */
    public void applyEffect(Pet pet){

        try {
            
            if(this.type.equals("gift")){

                if(pet.getHappiness() + this.effectiveness <= pet.getMaxHappiness()){

                    pet.setHappiness() += this.effectiveness;  
                }
                
                else{
                    pet.setHappiness(pet.getMaxHappiness);
                }
                
            }
    
            else if(this.type.equals("type")){
                
                if(pet.getFullness() + this.effectiveness <= pet.getMaxFullness()){

                    pet.setFullness() += this.effectiveness;  
                }
                
                else{
                    pet.setHappiness(pet.getMaxFullness);
                }
            }
    
            else{
    
                throw new Exception("Invalid type in item class");
            }

        } 
        
        catch (Exception e) {
            ;
        }
    }

}

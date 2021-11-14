package model;

public class Bottle {
    // how much water can be stored in the bottle (mL)
    private int capacity;

    // how much water is currently in the bottle (mL)
    // INVARIANT: 0 <= water <= capacity
    private int waterLevel;


    //EFFECTS: constructs a full water bottle
    public Bottle(int capacity) {
        this.capacity = capacity;
        this.waterLevel = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    //EFFECTS: modifies current water bottle capacity
    public void changeCapacity(int capacity) {
        this.capacity = capacity;
    }

    //EFFECTS: changes current water level
    public void drinkWater(int drank) {
        int water = drank%capacity;

        int currentLevel = waterLevel - water;

        if (currentLevel > 0) {
            waterLevel = currentLevel;
        } else {
            currentLevel = water - waterLevel;
            waterLevel = capacity - currentLevel;
        }

    }

}

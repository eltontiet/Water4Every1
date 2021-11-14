package model;

import org.json.JSONObject;
import persistence.Writable;

public class Bottle implements Writable {
    // how much water can be stored in the bottle (mL)
    private int capacity;

    // how much water is currently in the bottle (mL)
    // INVARIANT: 0 <= water <= capacity
    private int waterLevel;


    //EFFECTS: constructs an empty water bottle
    public Bottle() {
        capacity = 0;
        waterLevel = 0;
    }

    //EFFECTS: constructs a full water bottle
    public Bottle(int capacity) {
        this.capacity = capacity;
        this.waterLevel = capacity;
    }

    //EFFECTS: constructs a water bottle at waterLevel
    public Bottle(int capacity, int waterLevel) {
        this.capacity = capacity;
        this.waterLevel = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    //EFFECTS: modifies current water bottle capacity, assumes bottle is full
    public void changeCapacity(int capacity) {
        this.capacity = capacity;
        waterLevel = capacity;
    }

    //EFFECTS: fills water bottle
    public void fillWater() {
        this.waterLevel = capacity;
    }

    //EFFECTS: changes current water level
    public void drinkWater(int drank) {
        int water = drank%capacity;

        if (waterLevel - water > 0) {
            waterLevel = waterLevel - water;
        } else {
            waterLevel = capacity - water + waterLevel;
        }
    }

    // EFFECTS: returns a JSONObject that stores this object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("capacity", capacity);
        jsonObject.put("waterLevel", waterLevel);
        return jsonObject;
    }
}

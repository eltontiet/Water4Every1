package model;

import exceptions.GoToSleepException;
import exceptions.TimeOverlapException;
import org.json.JSONObject;
import persistence.Writable;

public class User implements Writable {
    private int waterDrank;
    private Bottle bottle;
    private Schedule schedule;
    private String name;

    public User(String name) {
        this.name = name;
        bottle = new Bottle();
        schedule = new Schedule();
    }

    // EFFECTS: initializes user with a bottle and schedule
    public User(String name, Bottle bottle, Schedule schedule) {
        this.name = name;
        this.bottle = bottle;
        this.schedule = schedule;
    }

    public int getWaterDrank() {
        return waterDrank;
    }

    public void drinkWater(int water) {
        waterDrank += water;
        bottle.drinkWater(water);
    }

    public void fillWater() {
        bottle.fillWater();
    }

    public void editBottle(int capacity) {
        bottle.changeCapacity(capacity);
    }

    // MODIFIES: this
    // EFFECTS: adds an activity to the schedule
    public void addActivity(int startHour, int startMinute, int endHour, int endMinute) throws TimeOverlapException, GoToSleepException {
        schedule.addTime(startHour,startMinute,endHour,endMinute);
    }

    // getters
    public String getName() {
        return name;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    // setters
    public void setWaterDrank(int waterDrank) {
        this.waterDrank = waterDrank;
    }

    // EFFECTS: returns a JSONObject that stores this object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", name);
        jsonObject.put("bottle", bottle.toJson());
        jsonObject.put("schedule", schedule.toJson());
        jsonObject.put("waterDrank", waterDrank);

        return jsonObject;
    }
}

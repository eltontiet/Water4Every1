package model;

public class User {
    private String username;
    private int waterDrank;
    private Bottle bottle;
    private Schedule schedule;


    public User() {
        bottle = new Bottle();
        schedule = new Schedule();
        username = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWaterDrank() {
        return waterDrank;
    }

    public Bottle getBottle() {
        return bottle;
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

    public void resetWater() {
        waterDrank = 0;
    }

}

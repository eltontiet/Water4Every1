package model;

public class User {
    private int waterDrank;
    private Bottle bottle;
    private Schedule schedule;

    public User() {
        bottle = new Bottle();
        schedule = new Schedule();
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

}

package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalTime;
import java.util.TreeMap;

// Represents a TimeHandler that compares the current time to
public class TimeHandler implements Writable {
    Schedule schedule;
    LocalTime currTime;
    LocalTime nextDrink;

    // MODIFIES: this
    // EFFECTS: initializes schedule and currTime
    public TimeHandler(Schedule schedule) {
        this.schedule = schedule;
        currTime = LocalTime.now();
    }

    // MODIFIES: this
    // EFFECTS: updates currTime
    public void updateCurrTime() {
        currTime = LocalTime.now();
    }

    // EFFECTS: returns nextBreak if it has not passed, or schedules the next break to be during the next break,
    //          or 2 hours in the future if the break would be longer than 2 hours
    private LocalTime getNextBreak() {
        TreeMap<LocalTime,LocalTime> times = schedule.getTimes();
        LocalTime nextTime = times.floorKey(currTime);

        if (nextTime == null) {
            return currTime.plusHours(2);
        }

        if (nextTime.isBefore(currTime)) {
            nextTime = times.ceilingKey(currTime);
        }

        if (nextTime == null) {
            return currTime.plusHours(2);
        }

        while (times.get(nextTime) != null) { // while end time == next start time
            nextTime = times.get(nextTime);
        }

        return nextTime;
    }

    // MODIFIES: this
    // EFFECTS: updates nextDrink
    public void updateNextDrink() {
        nextDrink = getNextBreak();
    }

    // EFFECTS: returns true if nextDrink has passed. SHOULD CALL updateCurrTime BEFORE
    public boolean hasPassed() {
        return currTime.isAfter(nextDrink);
    }

    // getters
    public LocalTime getNextDrink() {
        return nextDrink;
    }

    public LocalTime getCurrTime() {
        return currTime;
    }

    // FOR TESTING PURPOSES
    public void setCurrTime(LocalTime time) {
        currTime = time;
    }

    // setter
    public void setNextDrink(LocalTime time) {
        nextDrink = time;
    }

    // EFFECTS: returns a JSONObject that stores this object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        if (nextDrink != null) {
            jsonObject.put("nextDrinkHour", nextDrink.getHour());
            jsonObject.put("nextDrinkMinute", nextDrink.getMinute());
        }
        return jsonObject;
    }

    // EFFECTS: returns the difference in minutes between nextDrink and CurrTime
    public String getDifference() {
        int carry = 0;

        int minutes = nextDrink.getMinute() - currTime.getMinute();

        if (minutes < 0) {
            carry = 1;
            minutes += 60;
        }
        int hours = (nextDrink.getHour() - currTime.getHour() - carry);

        if (hours < 0) {
            hours += 24;
        }

        if (hours == 0) {
            return minutes + " minutes";
        } else if (hours == 1) {
            return  hours + " hour " + minutes + " minutes";
        }

        return  hours + " hours " + minutes + " minutes";
    }
}

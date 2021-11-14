package model;

import exceptions.NoTimeExistsException;
import exceptions.TimeOverlapException;

import java.time.LocalTime;
import java.util.*;

// Represents a schedule
public class Schedule {
    TreeMap<LocalTime,LocalTime> times;

    // MODIFIES: this
    // EFFECTS: initializes times
    public Schedule() {
        times = new TreeMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds time from startHour, startMinute to endHour, endMinute
    //          throws TimeOverlapException if times overlap
    public void addTime(int startHour, int startMinute, int endHour, int endMinute) throws TimeOverlapException {

        LocalTime startTime = LocalTime.of(startHour,startMinute);

        LocalTime endTime = LocalTime.of(endHour,endMinute);

        LocalTime timeBefore;
        if (times.floorKey(startTime) == null) {
            timeBefore = LocalTime.of(0,0);
        } else {
            timeBefore = times.get(times.floorKey(startTime));
        }

        LocalTime timeAfter;
        if (times.ceilingKey(startTime) == null) {
            timeAfter = LocalTime.of(23,59);
        } else {
            timeAfter = times.ceilingKey(startTime);
        }

        if (timeBefore.isAfter(startTime) || timeAfter.isBefore(endTime)) {
            throw new TimeOverlapException();
        } else {
            times.put(startTime,endTime);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the time at hour, minute. throws NoTimeExistsException if not in the schedule
    public void removeTime(int hour, int minute) throws NoTimeExistsException {
        times.remove(getTime(hour,minute));
    }

    // EFFECTS: returns the time at hour, minute. throws NoTimeExistsException if not in the schedule
    private LocalTime getTime(int hour, int minute) throws NoTimeExistsException {
        LocalTime time = LocalTime.of(hour, minute);
        if (!times.containsKey(time)) {
            throw new NoTimeExistsException();
        } else {
            return time;
        }

    }

    // getter
    public TreeMap<LocalTime, LocalTime> getTimes() {
        return times;
    }

}

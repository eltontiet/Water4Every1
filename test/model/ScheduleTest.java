package model;

import exceptions.GoToSleepException;
import exceptions.NoTimeExistsException;
import exceptions.TimeOverlapException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    Schedule schedule;

    @BeforeEach
    void setUp() {
        schedule = new Schedule();
        try {
            schedule.addTime(12,0,14,0);
            schedule.addTime(15,0,16,0);
        } catch (TimeOverlapException | GoToSleepException e) {
            // do nothing
        }
    }

    @Test
    void addTimeGeneral() {
        try {
            schedule.addTime(10, 0, 11, 30);
            assertTrue(schedule.getTimes().containsKey(LocalTime.of(10, 0)));

            LocalTime endTime = schedule.getTimes().get(LocalTime.of(10, 0));
            assertEquals(endTime.getHour(), 11);
            assertEquals(endTime.getMinute(), 30);
        } catch (TimeOverlapException | GoToSleepException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void addTimeStartOverlap() {
        try {
            schedule.addTime(16,0,17,30);
            assertTrue(schedule.getTimes().containsKey(LocalTime.of(16,0)));

            LocalTime endTime = schedule.getTimes().get(LocalTime.of(16,0));
            assertEquals(endTime.getHour(), 17);
            assertEquals(endTime.getMinute(), 30);

        } catch (TimeOverlapException | GoToSleepException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void addTimeEndOverlap() {
        try {
            schedule.addTime(11,0,12,0);
            assertTrue(schedule.getTimes().containsKey(LocalTime.of(11,0)));

            LocalTime endTime = schedule.getTimes().get(LocalTime.of(11,0));
            assertEquals(endTime.getHour(), 12);
            assertEquals(endTime.getMinute(), 0);

        } catch (TimeOverlapException | GoToSleepException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void addTimeBothOverlap() {
        try {
            schedule.addTime(14,0,15,0);
            assertTrue(schedule.getTimes().containsKey(LocalTime.of(14,0)));

            LocalTime endTime = schedule.getTimes().get(LocalTime.of(14,0));
            assertEquals(endTime.getHour(), 15);
            assertEquals(endTime.getMinute(), 0);

        } catch (TimeOverlapException | GoToSleepException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void addTimeStartInSchedule() {
        try {
            schedule.addTime(15,30,17,0);

            fail("Should throw TimeOverlapException");

        } catch (TimeOverlapException e) {
            // pass;
        } catch (GoToSleepException e) {
            fail("Should throw TimeOverlapException");
        }
    }

    @Test
    void addTimeEndInSchedule() {
        try {
            schedule.addTime(11,30,13,0);

            fail("Should throw TimeOverlapException");

        } catch (TimeOverlapException e) {
            // pass;
        } catch (GoToSleepException e) {
            fail("Should throw TimeOverlapException");
        }
    }

    @Test
    void addTimeBothInSchedule() {
        try {
            schedule.addTime(14,30,16,30);

            fail("Should throw TimeOverlapException");

        } catch (TimeOverlapException e) {
            // pass;
        } catch (GoToSleepException e) {
            fail("Should throw TimeOverlapException");
        }
    }

    @Test
    void addTimeGoToSleepDifferentHour() {
        try {
            schedule.addTime(23,59,0,0);

            fail("Should throw GoToSleepException");

        } catch (TimeOverlapException e) {
            fail("Should throw GoToSleepException");
        } catch (GoToSleepException e) {
            // pass
        }
    }

    @Test
    void addTimeGoToSleepDifferentMinute() {
        try {
            schedule.addTime(23,59,23,58);

            fail("Should throw GoToSleepException");

        } catch (TimeOverlapException e) {
            fail("Should throw GoToSleepException");
        } catch (GoToSleepException e) {
            // pass
        }
    }

    @Test
    void removeTime() {
        try {
            schedule.removeTime(12,0);
            assertFalse(schedule.getTimes().containsKey(LocalTime.of(12,0)));
        } catch (NoTimeExistsException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void removeTimeNotInSchedule() {
        try {
            schedule.removeTime(11,0);
            fail("Should throw NoTimeExistsException");
        } catch (NoTimeExistsException e) {
            // pass
        }
    }
}

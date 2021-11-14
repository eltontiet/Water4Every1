package model;

import exceptions.TimeOverlapException;
import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class TimeHandlerTest {

    Schedule schedule;
    TimeHandler timeHandler;

    @BeforeEach
    void setup() {
        schedule = new Schedule();
        timeHandler = new TimeHandler(schedule);

        try {
            schedule.addTime(12,0,13,0);
            schedule.addTime(14,0,15,0);
            schedule.addTime(15,0,16,30);
        } catch (TimeOverlapException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateCurrTime() {
        timeHandler.updateCurrTime();
        assertEquals(timeHandler.getCurrTime(), LocalTime.now());
    }

    @Test
    void updateNextDrinkGeneral() {
        timeHandler.setCurrTime(LocalTime.of(11,0));
        timeHandler.updateNextDrink();
        assertEquals(timeHandler.getNextDrink(), LocalTime.of(13,0));
    }

    @Test
    void updateNextDrinkOverlap() {
        timeHandler.setCurrTime(LocalTime.of(14,0));
        timeHandler.updateNextDrink();
        assertEquals(timeHandler.getNextDrink(), LocalTime.of(16,30));
    }

    @Test
    void updateNextDrinkEmpty() {
        timeHandler.setCurrTime(LocalTime.of(17,0));
        timeHandler.updateNextDrink();
        assertEquals(timeHandler.getNextDrink(), LocalTime.of(19,0));
    }

    @Test
    void hasPassed() {
        timeHandler.setCurrTime(LocalTime.of(11,0));
        timeHandler.updateNextDrink();
        assertFalse(timeHandler.hasPassed());
        timeHandler.setCurrTime(LocalTime.of(14,0));
        assertTrue(timeHandler.hasPassed());
    }
}

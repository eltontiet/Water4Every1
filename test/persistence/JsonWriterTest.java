package persistence;

import exceptions.GoToSleepException;
import exceptions.TimeOverlapException;
import model.TimeHandler;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testSaveEmptyUser() {
        try {
            User u = new User("guy");
            TimeHandler timeHandler = new TimeHandler(u.getSchedule());

            JsonWriter writer = new JsonWriter("./data/testJsonWriter.json");

            writer.open();

            writer.write(u,timeHandler);

            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriter.json");

            User user = reader.readUser();
            TimeHandler newTimeHandler = reader.readTimeHandler(user.getSchedule());

            assertEquals(user.getName(), "guy");
            assertNull(newTimeHandler.getNextDrink());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSaveGeneral() {
        try {
            User u = new User("guy");
            TimeHandler timeHandler = new TimeHandler(u.getSchedule());

            u.editBottle(1000);
            u.drinkWater(500);
            u.addActivity(12,0,13,0);
            u.addActivity(15,0,18,30);

            timeHandler.setCurrTime(LocalTime.of(15,0));
            timeHandler.updateNextDrink();

            JsonWriter writer = new JsonWriter("./data/testJsonWriterGeneral.json");

            writer.open();

            writer.write(u,timeHandler);

            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterGeneral.json");

            User user = reader.readUser();
            TimeHandler newTimeHandler = reader.readTimeHandler(user.getSchedule());

            assertEquals(user.getName(), "guy");
            assertEquals(user.getWaterDrank(), 500);
            assertEquals(timeHandler.getNextDrink(), newTimeHandler.getNextDrink());

            LocalTime time = user.getSchedule().getTimes().get(LocalTime.of(12,0));
            assertTrue(user.getSchedule().getTimes().containsKey(LocalTime.of(12,0)));
            assertEquals(time, LocalTime.of(13,0));

            time = user.getSchedule().getTimes().get(LocalTime.of(15,0));
            assertTrue(user.getSchedule().getTimes().containsKey(LocalTime.of(15,0)));
            assertEquals(time, LocalTime.of(18,30));

        } catch (TimeOverlapException | GoToSleepException | IOException e) {
            e.printStackTrace();
        }
    }

}

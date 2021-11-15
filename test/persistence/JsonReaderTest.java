package persistence;

import model.TimeHandler;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonReaderTest {

    @Test
    void testReaderGeneral() {
        try {
            JsonReader reader = new JsonReader("./data/testJsonWriterGeneral.json");

            User user =  reader.readUser();

            TimeHandler newTimeHandler = reader.readTimeHandler(user.getSchedule());

            assertEquals(user.getName(), "guy");
            assertEquals(user.getWaterDrank(), 500);
            assertEquals(LocalTime.of(18,30), newTimeHandler.getNextDrink());

            LocalTime time = user.getSchedule().getTimes().get(LocalTime.of(12,0));
            assertTrue(user.getSchedule().getTimes().containsKey(LocalTime.of(12,0)));
            assertEquals(time, LocalTime.of(13,0));

            time = user.getSchedule().getTimes().get(LocalTime.of(15,0));
            assertTrue(user.getSchedule().getTimes().containsKey(LocalTime.of(15,0)));
            assertEquals(time, LocalTime.of(18,30));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

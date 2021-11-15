package persistence;

import exceptions.GoToSleepException;
import exceptions.TimeOverlapException;
import model.Bottle;
import model.Schedule;
import model.TimeHandler;
import model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

// Represents a reader that reads json data from file
public class JsonReader {

    private String source;

    // EFFECTS: initializes source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads source file as a string and returns it
    public String readFile() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: reads the file and returns a TimeHandler
    public TimeHandler readTimeHandler(Schedule schedule) throws IOException {
        String jsonData = readFile();
        JSONObject jsonObject = new JSONObject(jsonData);
        jsonObject = jsonObject.getJSONObject("timeHandler");
        return parseTimeHandler(jsonObject, schedule);
    }

    // EFFECTS: parses a TimeHandler from jsonObject
    private TimeHandler parseTimeHandler(JSONObject jsonObject, Schedule schedule) {
        TimeHandler timeHandler = new TimeHandler(schedule);

        try {
            int hour = jsonObject.getInt("nextDrinkHour");
            int minute = jsonObject.getInt("nextDrinkMinute");
            timeHandler.setNextDrink(LocalTime.of(hour, minute));
        } catch (JSONException e) {
            timeHandler.updateCurrTime();
            timeHandler.updateNextDrink();
        }

        return timeHandler;
    }

    // EFFECTS: reads the file and returns a User
    public User readUser() throws IOException {
        String jsonData = readFile();
        JSONObject jsonObject = new JSONObject(jsonData);
        jsonObject = jsonObject.getJSONObject("user");
        return parseUser(jsonObject);
    }

    // EFFECTS: parses a User from jsonObject
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Bottle bottle = parseBottle(jsonObject.getJSONObject("bottle"));
        Schedule schedule = parseSchedule(jsonObject.getJSONObject("schedule"));
        User user = new User(name,bottle,schedule);
        user.setWaterDrank(jsonObject.getInt("waterDrank"));
        return user;
    }

    // EFFECTS: parses a Schedule from jsonObject
    private Schedule parseSchedule(JSONObject jsonObject) {
        Schedule schedule = new Schedule();
        JSONArray array = jsonObject.getJSONArray("times");
        for (Object json : array) {
            JSONObject activity = (JSONObject) json;

            int startHour = activity.getInt("startHour");
            int startMinute = activity.getInt("startMinute");
            int endHour = activity.getInt("endHour");
            int endMinute = activity.getInt("endMinute");

            try {
                schedule.addTime(startHour,startMinute,endHour, endMinute);

            } catch (TimeOverlapException e) {
                e.printStackTrace();
                System.err.printf("Could not load time at: %d:%d to %d:%d\n", startHour, startMinute, endHour, endMinute);
            } catch (GoToSleepException e) {
                e.printStackTrace();
                System.err.println("Go to sleep.");
            }
        }

        return schedule;
    }

    // EFFECTS: parses a Bottle from jsonObject
    private Bottle parseBottle(JSONObject jsonObject) {
        int capacity = jsonObject.getInt("capacity");
        int waterLevel = jsonObject.getInt("waterLevel");
        return new Bottle(capacity,waterLevel);
    }

    // EFFECTS: reads last date opened
    public LocalDate readLastDate() throws IOException {
        String jsonData = readFile();
        JSONObject jsonObject = new JSONObject(jsonData);
        return LocalDate.parse(jsonObject.getString("lastDate"));
    }
}

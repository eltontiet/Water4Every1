package persistence;

import model.TimeHandler;
import model.User;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes json data into a file
public class JsonWriter {
    private static final int TAB = 4;

    private PrintWriter writer;
    private String destination;

    // EFFECTS: initializes destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // EFFECTS: opens writer to write at destination
    //          throws FileNotFoundException if file cannot be written to
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: saves json to file
    public void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // EFFECTS: writes user and timeHandler to file
    public void write(User user, TimeHandler timeHandler) {
        JSONObject json = new JSONObject();
        json.put("user",user.toJson());
        json.put("timeHandler", timeHandler.toJson());
        saveToFile(json.toString(TAB));
    }
}

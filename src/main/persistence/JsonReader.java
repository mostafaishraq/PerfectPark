package persistence;

import model.Car;
import model.ParkingList;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads parking list from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads parking list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ParkingList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseParkingList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses parking list from JSON object and returns it
    private ParkingList parseParkingList(JSONObject jsonObject) {
        Integer maxSize = jsonObject.getInt("maxSize");
        Double rate = jsonObject.getDouble("rate");
        Integer minDiscountHours = jsonObject.getInt("minDiscountHours");
        Double discountPercentage = jsonObject.getDouble("discountPercentage");
        ParkingList pl = new ParkingList(maxSize, rate, minDiscountHours, discountPercentage);
        addCars(pl, jsonObject);
        return pl;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addCars(ParkingList pl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cars");
        for (Object json : jsonArray) {
            JSONObject nextCar = (JSONObject) json;
            addCar(pl, nextCar);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addCar(ParkingList pl, JSONObject jsonObject) {
        String licenseNum = jsonObject.getString("licenseNum");
        String startTime = jsonObject.getString("startTime");
        String startDate = jsonObject.getString("startDate");
        Double rate = jsonObject.getDouble("rate");

        Car car = new Car(licenseNum, startTime, startDate, rate);
        pl.addCar(car);
    }
}

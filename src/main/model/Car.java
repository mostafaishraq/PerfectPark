package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents a car having a license number, start time, start date and rate of charge
public class Car implements Writable {
    private final String licenseNum;   // license plate number of car
    private final String startTime;    // time of entry of car in HH:MM
    private final String startDate;    // date of entry of car in MM-DD-YYYY
    private final Double rate;         // rate of charge for this car

    // REQUIRES: startTime is in format HH:MM and startDate is in format MM-DD-YYYY
    // EFFECTS: constructs a car object, license number of car is set to licenseNum,
    //          time of entry is set to startTime, date of entry is set to startDate,
    //          and rate of charge for the car is set to rate
    public Car(String licenseNum, String startTime, String startDate, Double rate) {
        this.licenseNum = licenseNum;
        this.startTime = startTime;
        this.startDate = startDate;
        this.rate = rate;
    }

    // EFFECTS: returns the license number of the car
    public String getLicenseNum() {
        return licenseNum;
    }

    // EFFECTS: returns the time when the car started parking
    public String getStartTime() {
        return startTime;
    }

    // EFFECTS: returns the hour of the day when the car started parking
    public Integer getStartHour() {
        return Integer.parseInt(startTime.substring(0, 2));
    }

    // EFFECTS: returns the date when the car started parking
    public String getStartDate() {
        return startDate;
    }

    // EFFECTS: returns the day of month when the car started parking
    public Integer getStartDay() {
        return Integer.parseInt(startDate.substring(3, 5));
    }

    // EFFECTS: returns the rate at which the car is charged
    public Double getRate() {
        return rate;
    }

    // ASSUMES: the car already parked in the parking
    // EFFECTS: returns the number of hours passed since the car was parked
    public Integer getHoursParked(String endTime, String endDate) {
        Integer endHour = Integer.parseInt(endTime.substring(0, 2));
        Integer endDay = Integer.parseInt(endDate.substring(3, 5));

        if (endDay - this.getStartDay() == 0) {
            return endHour - this.getStartHour();
        } else {
            return endHour - this.getStartHour() + 24;
        }
    }

    public boolean validate() {
        try {
            getStartHour();
            Integer.parseInt(startTime.substring(3, 5));
            getStartDay();
            Integer.parseInt(startDate.substring(0, 2));
            Integer.parseInt(startDate.substring(6, 10));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("licenseNum", licenseNum);
        json.put("startDate", startDate);
        json.put("startTime", startTime);
        json.put("rate", rate);
        return json;
    }

}

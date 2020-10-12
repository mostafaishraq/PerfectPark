package model;


public class Car {
    private final String licenseNum;
    private final String startTime;
    private final String startDate;
    private final Double rate;

    // EFFECTS:
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

    // EFFECTS: returns the start time of the car
    public String getStartTime() {
        return startTime;
    }

    // EFFECTS:
    public Integer getStartHour() {
        return Integer.parseInt(startTime.substring(0, 2));
    }

    //EFFECTS:
    public String getStartDate() {
        return startDate;
    }

    // EFFECTS:
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

}

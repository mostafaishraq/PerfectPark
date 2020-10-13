package model;

import java.util.ArrayList;
import java.util.List;

public class ParkingList {
    private final List<Car> parkingList;    // list of parked cars
    private final Integer maxSize;          // maximum number of cars the parking can accommodate

    // REQUIRES: maxSize > 0
    // EFFECTS: constructs a list of cars parked with maximum allowed size of maxSize
    public ParkingList(Integer maxSize) {
        parkingList = new ArrayList<>();
        this.maxSize = maxSize;
    }

    // MODIFIES: this
    // EFFECTS: if parking list is not full,
    //          - adds the car to the list and
    //          - returns true
    //          otherwise, returns false
    public boolean addCar(Car car) {
        if (getNumberEmptySlots() == 0) {
            return false;
        } else {
            parkingList.add(car);
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: if the car exists in the list,
    //          - removes the car from the list
    //          - returns true
    //          otherwise, return false
    public boolean removeCar(Car car) {
        return parkingList.remove(car);
    }


    //EFFECTS: returns the number of empty parking spots
    public Integer getNumberEmptySlots() {
        return maxSize - parkingList.size();
    }


    // EFFECTS: looks for car with given licenseNum and if found, returns the car record,
    //          otherwise, returns null
    public Car getCar(String licenseNum) {
        for (Car car: parkingList) {
            if (car.getLicenseNum().equals(licenseNum)) {
                return car;
            }
        }
        return null;
    }

}


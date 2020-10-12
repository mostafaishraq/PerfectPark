package model;

import java.util.ArrayList;
import java.util.List;

public class ParkingList {
    private final List<Car> parkingList;
    private final Integer maxSize;

    // EFFECTS: constructs a list of cars parked
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


    // REQUIRES: the car exits in the list
    // EFFECTS: returns the car record with the given licenseNum
    public Car getCar(String licenseNum) {
        for (Car car: parkingList) {
            if (car.getLicenseNum().equals(licenseNum)) {
                return car;
            }
        }
        return null;
    }

}


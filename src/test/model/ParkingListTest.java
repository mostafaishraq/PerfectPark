package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingListTest {
    ParkingList parkingList;

    @BeforeEach
    void runBefore() {
        parkingList = new ParkingList(10, 5.00, 3, 10.00);
    }

    @Test
    void testAddCarListNotFull() {
        for (int i = 0; i < 10; i++) {
            Car car = new Car("A", "10:10", "10-10-2020", 5.00);
            assertTrue(parkingList.addCar(car));
            assertEquals(i+1, 10 - parkingList.getNumberEmptySlots());
        }
    }

    @Test
    void testAddCarListFull() {
        for (int i = 0; i < 10; i++) {
            Car car = new Car("A", "10:10", "10-10-2020", 5.00);
            assertTrue(parkingList.addCar(car));
            assertEquals(i+1, 10 - parkingList.getNumberEmptySlots());
        }

        Car car = new Car("A", "10:10", "10-10-2020", 5.00);
        assertFalse(parkingList.addCar(car));
        assertEquals(10,10 - parkingList.getNumberEmptySlots());
    }

    @Test
    void testRemoveCarExists() {
        Car car = new Car("A", "10:10", "10-10-2020", 5.00);
        assertTrue(parkingList.addCar(car));
        assertEquals(1, 10 - parkingList.getNumberEmptySlots());

        assertTrue(parkingList.removeCar(car));
        assertEquals(0, 10 - parkingList.getNumberEmptySlots());

    }

    @Test
    void testRemoveCarDoesNotExist() {
        Car car = new Car("A", "10:10", "10-10-2020", 5.00);

        assertEquals(0, 10 - parkingList.getNumberEmptySlots());

        assertFalse(parkingList.removeCar(car));
        assertEquals(0, 10 - parkingList.getNumberEmptySlots());
    }

    @Test
    void testGetCarExists() {
        Car car1 = new Car("A", "10:10", "10-10-2020", 5.00);
        assertTrue(parkingList.addCar(car1));
        assertEquals(1, 10 - parkingList.getNumberEmptySlots());

        Car car2 = parkingList.getCar("A");

        assertEquals(car1.getLicenseNum(), car2.getLicenseNum());
        assertEquals(car1.getStartTime(), car2.getStartTime());
        assertEquals(car1.getStartDate(), car2.getStartDate());
        assertEquals(car1.getRate(), car2.getRate());
        assertEquals(1, 10 - parkingList.getNumberEmptySlots());
    }
    @Test
    void testGetCarDoesNotExist() {
        Car car1 = new Car("A", "10:10", "10-10-2020", 5.00);
        assertTrue(parkingList.addCar(car1));
        assertEquals(1, 10 - parkingList.getNumberEmptySlots());

        Car car2 = parkingList.getCar("B");

        assertNull(car2);

        assertEquals(1, 10 - parkingList.getNumberEmptySlots());
    }
}

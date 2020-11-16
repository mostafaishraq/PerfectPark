package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Car class
class CarTest {
    private Car car;

    @BeforeEach
    void runBefore() {
        car = new Car("ABC123", "10:10", "10-10-2020", 5.00);
    }

    @Test
    void testGetLicenseNum() {
        assertEquals("ABC123", car.getLicenseNum());
    }

    @Test
    void testGetStartTime() {
        assertEquals("10:10", car.getStartTime());
    }

    @Test
    void testGetStartDate() {
        assertEquals("10-10-2020", car.getStartDate());
    }

    @Test
    void testGetRate() {
        assertEquals(5.00, car.getRate());
    }

    @Test
    void testGetHoursParkedSameDay() {
        Integer hoursParked1 = car.getHoursParked("20:09", "10-10-2020");
        assertEquals(9, hoursParked1);
        Integer hoursParked2 = car.getHoursParked("20:10", "10-10-2020");
        assertEquals(10, hoursParked2);
        Integer hoursParked3 = car.getHoursParked("20:11", "10-10-2020");
        assertEquals(10, hoursParked3);
    }


    @Test
    void testGetHoursParkedDiffDay() {
        Integer hoursParked1 = car.getHoursParked("09:09", "10-11-2020");
        assertEquals(22, hoursParked1);
        Integer hoursParked2 = car.getHoursParked("09:10", "10-11-2020");
        assertEquals(23, hoursParked2);
        Integer hoursParked3 = car.getHoursParked("09:11", "10-11-2020");
        assertEquals(23, hoursParked3);
    }

    @Test
    void testGetTotalCostZeroHour() {
        ParkingList parkingList = new ParkingList(10, 5.00, 3, 10.00);
        Double totalCost = car.getTotalCost("11:09", "10-10-2020", parkingList);
        assertEquals(5.00, totalCost);
    }

    @Test
    void testGetTotalCostMoreHourNoDiscount() {
        ParkingList parkingList = new ParkingList(10, 5.00, 3, 10.00);
        Double totalCost1 = car.getTotalCost("11:10", "10-10-2020", parkingList);
        Double totalCost2 = car.getTotalCost("13:09", "10-10-2020", parkingList);
        assertEquals(5.00, totalCost1);
        assertEquals(10.00, totalCost2);
    }

    @Test
    void testGetTotalCostMoreHourWithDiscount() {
        ParkingList parkingList = new ParkingList(10, 5.00, 3, 10.00);
        Double totalCost = car.getTotalCost("13:10", "10-10-2020", parkingList);
        assertEquals(13.5, totalCost);
    }

    @Test
    void testValidateStringsValid() {
        assertTrue(car.validate());
    }

    @Test
    void testValidateStringsInValid() {
        Car car1 = new Car("ABC123", "AA:AA", "10-10-2020", 5.00);
        assertFalse(car1.validate());
        Car car2 = new Car("ABC123", "10:AA", "AA-AA-AAAA", 5.00);
        assertFalse(car2.validate());
    }
}
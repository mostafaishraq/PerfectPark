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
        Integer hoursParked = car.getHoursParked("20:10", "10-10-2020");
        assertEquals(10, hoursParked);
    }

    @Test
    void testGetHoursParkedDiffDay() {
        Integer hoursParked = car.getHoursParked("09:10", "10-11-2020");
        assertEquals(23, hoursParked);
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
        assertFalse(car1.validate());
    }
}
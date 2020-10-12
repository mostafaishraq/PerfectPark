# My Personal Project

## PerfectPark

The application, called PerfectPark, is a car parking management application. 
The potential user is a staff of the car parking. 

When a car enters the parking lot, the user inputs the car's license plate number, and
the time and date of the start of the car's parking period into the application. 
These are stored by the application as long as the car is parked in the parking lot.

The car will be charged at a half-hourly rate. If a car parks for less than half-an-hour,
it will be charged for half-an-hour.
The rate can be changed by the user, 
but a car will pay the rate displayed when it was entering the parking lot. 
Thus, the rate for the car will be recorded when it enters the system 
so that future rate changes does not affect its cost.

When a car exists the parking lot, the user inputs the car's license plate number, and 
the time and date of the end of the car's parking period into the application.
The number of half-hours is calculated from the start and end times and subsequently, 
the total cost is calculated from the rate (this is the rate store at the start of the car's parking period).
A discount can be added to the total cost if the number of half- hours parked 
is greater than a specific number of half-hours, which can be set and changed by the user.

## User Stories

- As a user, I want to be able to add a vehicle record to my parking slots.
- As a user, I want to be able to set and change the rate of charge for each type of vehicle.
- As a user, I want to be able to  set and change the minimum number of half-hours for discount.
- As a user, I want to be able to see the filled spots and vacant spots in the parking.
- As a user, I want to be able to end a car's parking period.
- As a user, I want to be able to know if the parking is full.
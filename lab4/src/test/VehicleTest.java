package test;

import exceptions.PassengerNotFoundException;
import exceptions.VehicleFullException;
import infrastructure.Road;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passengers.Firefighter;
import passengers.Person;
import passengers.Policeman;
import vehicles.Bus;
import vehicles.FireTruck;
import vehicles.PoliceCar;
import vehicles.Taxi;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    private Person person1;
    private Firefighter firefighter1;
    private Policeman policeman1;

    @BeforeEach
    void setUp() {
        person1 = new Person("Regular Passenger");
        firefighter1 = new Firefighter("Brave Firefighter");
        policeman1 = new Policeman("Officer");
    }

    @Test
    void testBusCanBoardAllTypes() throws VehicleFullException {
        Bus bus = new Bus(3);
        bus.boardPassenger(person1);
        bus.boardPassenger(firefighter1);
        bus.boardPassenger(policeman1);

        assertEquals(3, bus.getOccupiedSeats());
    }

    @Test
    void testTaxiCanBoardAllTypes() throws VehicleFullException {
        Taxi taxi = new Taxi(2);
        taxi.boardPassenger(person1);
        taxi.boardPassenger(policeman1);

        assertEquals(2, taxi.getOccupiedSeats());
    }

    @Test
    void testFireTruckOnlyBoardsFirefighters() throws VehicleFullException {
        FireTruck fireTruck = new FireTruck(4);
        fireTruck.boardPassenger(firefighter1);

        assertEquals(1, fireTruck.getOccupiedSeats());
    }

    @Test
    void testPoliceCarOnlyBoardsPolicemen() throws VehicleFullException {
        PoliceCar policeCar = new PoliceCar(2);
        policeCar.boardPassenger(policeman1);

        assertEquals(1, policeCar.getOccupiedSeats());
    }

    @Test
    void testVehicleFullException() throws VehicleFullException {
        Taxi taxi = new Taxi(1);
        taxi.boardPassenger(person1);

        assertThrows(VehicleFullException.class, () -> {
            taxi.boardPassenger(new Person("Another Passenger"));
        });

        assertEquals(1, taxi.getOccupiedSeats());
    }

    @Test
    void testDisembarkPassengerSuccess() throws VehicleFullException, PassengerNotFoundException {
        Bus bus = new Bus(5);
        bus.boardPassenger(person1);
        bus.boardPassenger(firefighter1);
        assertEquals(2, bus.getOccupiedSeats());

        bus.disembarkPassenger(person1);
        assertEquals(1, bus.getOccupiedSeats());
    }

    @Test
    void testPassengerNotFoundException() throws VehicleFullException {
        Bus bus = new Bus(5);
        bus.boardPassenger(person1);

        Person person2 = new Person("Stranger");

        assertThrows(PassengerNotFoundException.class, () -> {
            bus.disembarkPassenger(person2);
        });

        assertEquals(1, bus.getOccupiedSeats());
    }

    @Test
    void testRoadCountHumans() throws VehicleFullException {
        Road road = new Road();

        Bus bus = new Bus(10);
        bus.boardPassenger(person1);
        bus.boardPassenger(firefighter1);

        PoliceCar policeCar = new PoliceCar(2);
        policeCar.boardPassenger(policeman1);

        FireTruck fireTruck = new FireTruck(4);

        road.addCarToRoad(bus);
        road.addCarToRoad(policeCar);
        road.addCarToRoad(fireTruck);

        assertEquals(3, road.getCountOfHumans());
    }
}
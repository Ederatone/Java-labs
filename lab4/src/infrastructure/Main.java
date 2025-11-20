package infrastructure;

import exceptions.VehicleFullException;
import passengers.Firefighter;
import passengers.Person;
import passengers.Policeman;
import vehicles.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting vehicle simulation...");

        Road mainRoad = new Road();

        Bus bus1 = new Bus(50);
        Bus bus2 = new Bus(20);
        Taxi taxi1 = new Taxi(4);
        FireTruck fireTruck1 = new FireTruck(6);
        PoliceCar policeCar1 = new PoliceCar(2);
        PoliceCar policeCar2 = new PoliceCar(4);

        try {
            bus1.boardPassenger(new Person("Regular Passenger 1"));
            bus1.boardPassenger(new Firefighter("Firefighter 1"));
            bus1.boardPassenger(new Policeman("Policeman 1"));

            bus2.boardPassenger(new Person("Regular Passenger 2"));

            taxi1.boardPassenger(new Person("Taxi Passenger 1"));
            taxi1.boardPassenger(new Person("Taxi Passenger 2"));

            fireTruck1.boardPassenger(new Firefighter("Firefighter 2"));
            fireTruck1.boardPassenger(new Firefighter("Firefighter 3"));

            policeCar1.boardPassenger(new Policeman("Policeman 2"));

            policeCar2.boardPassenger(new Policeman("Policeman 3"));
            policeCar2.boardPassenger(new Policeman("Policeman 4"));

            mainRoad.addCarToRoad(bus1);
            mainRoad.addCarToRoad(bus2);
            mainRoad.addCarToRoad(taxi1);
            mainRoad.addCarToRoad(fireTruck1);
            mainRoad.addCarToRoad(policeCar1);
            mainRoad.addCarToRoad(policeCar2);

        } catch (VehicleFullException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Current Status on the Road ---");

        int i = 1;
        for (Vehicle<?> vehicle : mainRoad.carsInRoad) {
            String vehicleType = vehicle.getClass().getSimpleName();
            int passengers = vehicle.getOccupiedSeats();
            int maxSeats = vehicle.getMaxSeats();

            System.out.println(
                    String.format("Vehicle #%d: %s | Passengers: %d/%d",
                            i, vehicleType, passengers, maxSeats)
            );
            i++;
        }

        System.out.println("------------------------------------");
        System.out.println("Total humans on the road: " + mainRoad.getCountOfHumans());
    }
}
package infrastructure;

import vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Road {

    public List<Vehicle<?>> carsInRoad = new ArrayList<>();

    public void addCarToRoad(Vehicle<?> car) {
        carsInRoad.add(car);
        System.out.println(car.getClass().getSimpleName() + " added to the road.");
    }

    public int getCountOfHumans() {
        int totalHumans = 0;
        for (Vehicle<?> vehicle : carsInRoad) {
            totalHumans += vehicle.getOccupiedSeats();
        }
        return totalHumans;
    }
}
package vehicles;

import exceptions.PassengerNotFoundException;
import exceptions.VehicleFullException;
import passengers.Person;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle<T extends Person> {

    private final int maxSeats;
    protected final List<T> passengers;

    public Vehicle(int maxSeats) {
        this.maxSeats = maxSeats;
        this.passengers = new ArrayList<>(maxSeats);
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public int getOccupiedSeats() {
        return passengers.size();
    }

    public void boardPassenger(T passenger) throws VehicleFullException {
        if (passengers.size() >= maxSeats) {
            throw new VehicleFullException("Vehicle is full. Cannot board " + passenger);
        }
        passengers.add(passenger);
        System.out.println(passenger + " boarded " + this.getClass().getSimpleName());
    }

    public void disembarkPassenger(T passenger) throws PassengerNotFoundException {
        if (!passengers.remove(passenger)) {
            throw new PassengerNotFoundException("Passenger " + passenger + " not found in " + this.getClass().getSimpleName());
        }
        System.out.println(passenger + " disembarked from " + this.getClass().getSimpleName());
    }
}
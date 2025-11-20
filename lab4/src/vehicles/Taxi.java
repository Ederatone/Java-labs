package vehicles;

import passengers.Person;

public class Taxi extends Car<Person> {
    public Taxi(int maxSeats) {
        super(maxSeats);
    }
}
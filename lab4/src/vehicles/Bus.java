package vehicles;

import passengers.Person;

public class Bus extends Vehicle<Person> {
    public Bus(int maxSeats) {
        super(maxSeats);
    }
}
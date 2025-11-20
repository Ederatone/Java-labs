package vehicles;

import passengers.Person;

public abstract class Car<T extends Person> extends Vehicle<T> {
    public Car(int maxSeats) {
        super(maxSeats);
    }
}
package passengers;

public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public Person() {
        this.name = "Generic Person";
    }

    @Override
    public String toString() {
        return this.name;
    }
}
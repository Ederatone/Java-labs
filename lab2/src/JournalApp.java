import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class JournalApp {

    private final List<Student> journal = new ArrayList<>();
    private final JournalView view = new JournalView();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new JournalApp().run();
    }

    public void run() {
        boolean running = true;
        while (running) {
            view.displayMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    view.displayAllRecords(journal);
                    break;
                case "3":
                    running = false;
                    view.displayMessage("Exiting the application.");
                    break;
                default:
                    view.displayError("Invalid choice. Please enter a number from 1 to 3.");
            }
        }
    }

    private void addStudent() {
        String lastName = getValidatedInput(
                "Enter student's last name: ",
                "Last name must contain only letters.",
                "^[a-zA-Zа-яА-ЯіІїЇєЄ'\\-]+$"
        );

        String firstName = getValidatedInput(
                "Enter student's first name: ",
                "First name must contain only letters.",
                "^[a-zA-Zа-яА-ЯіІїЇєЄ'\\-]+$"
        );

        LocalDate dateOfBirth = getValidatedDate(
                "Enter date of birth (in format dd.MM.yyyy): ",
                "Invalid date format. Expected dd.MM.yyyy."
        );

        String phoneNumber = getValidatedInput(
                "Enter student's phone number (e.g., +380991234567): ",
                "Invalid phone format. Expected format like +380XXXXXXXXX.",
                "^\\+380\\d{9}$"
        );

        String address = getValidatedInput(
                "Enter student's address (e.g., Shevchenka St, 15): ",
                "Address cannot be empty.",
                "^.+$"
        );

        Student student = new Student(lastName, firstName, dateOfBirth, phoneNumber, address);
        journal.add(student);
        view.displayMessage("Record added successfully!");
    }

    private String getValidatedInput(String prompt, String errorMessage, String regex) {
        String input;
        Pattern pattern = Pattern.compile(regex);
        while (true) {
            view.displayPrompt(prompt);
            input = scanner.nextLine().trim();
            if (pattern.matcher(input).matches()) {
                return input;
            } else {
                view.displayError(errorMessage);
            }
        }
    }

    private LocalDate getValidatedDate(String prompt, String errorMessage) {
        String input;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        while (true) {
            view.displayPrompt(prompt);
            input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                view.displayError(errorMessage);
            }
        }
    }
}

class Student {
    private final String lastName;
    private final String firstName;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    private final String address;

    public Student(String lastName, String firstName, LocalDate dateOfBirth, String phoneNumber, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return String.format(
                "Name: %s %s | DoB: %s | Phone: %s | Address: %s",
                firstName, lastName, dateOfBirth.format(formatter), phoneNumber, address
        );
    }
}

class JournalView {

    public void displayAllRecords(List<Student> students) {
        if (students.isEmpty()) {
            displayMessage("Journal is empty.");
            return;
        }
        displayMessage("\n--- All Student Records ---");
        for (int i = 0; i < students.size(); i++) {
            displayMessage(String.format("%d. %s", i + 1, students.get(i).toString()));
        }
        displayMessage("---------------------------\n");
    }

    public void displayMenu() {
        System.out.println("\n--- Journal Application Menu ---");
        System.out.println("1. Add a new student record");
        System.out.println("2. Display all records");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String error) {
        System.err.println("ERROR: " + error);
    }

    public void displayPrompt(String prompt) {
        System.out.print(prompt);
    }
}
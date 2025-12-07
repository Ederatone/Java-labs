import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Account {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private final int id;
    private int balance;

    public Account(int initialBalance) {
        this.id = idGenerator.getAndIncrement();
        this.balance = initialBalance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "Acc-" + id + " [" + balance + "]";
    }
}

class Bank {
    public void transfer(Account from, Account to, int amount) {
        if (from.equals(to)) return;

        Account lockFirst = from.getId() < to.getId() ? from : to;
        Account lockSecond = from.getId() < to.getId() ? to : from;

        synchronized (lockFirst) {
            synchronized (lockSecond) {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                }
            }
        }
    }

    public long getTotalBalance(List<Account> accounts) {
        long total = 0;
        for (Account acc : accounts) {
            synchronized (acc) {
                total += acc.getBalance();
            }
        }
        return total;
    }
}

public class BankTask {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        List<Account> accounts = new ArrayList<>();
        Random random = new Random();

        int numberOfAccounts = 100;

        System.out.println(" ");
        for (int i = 0; i < numberOfAccounts; i++) {
            int randomBalance = random.nextInt(4001) + 1000;
            accounts.add(new Account(randomBalance));
        }

        long initialTotalBalance = bank.getTotalBalance(accounts);
        System.out.println("Total Money in Bank (Start): " + initialTotalBalance);

        System.out.println("Starting transfers...");

        int numberOfThreads = 5000;
        ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                Account from = accounts.get(random.nextInt(accounts.size()));
                Account to = accounts.get(random.nextInt(accounts.size()));
                int amount = random.nextInt(100) + 1;

                bank.transfer(from, to, amount);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long finalTotalBalance = bank.getTotalBalance(accounts);

        System.out.println("Total Money in Bank (End):   " + finalTotalBalance);

        if (initialTotalBalance == finalTotalBalance) {
            System.out.println("\nSUCCESS: No money was lost during parallel transfers.");
        } else {
            System.out.println("\nERROR: Money logic is broken!");
        }
    }
}
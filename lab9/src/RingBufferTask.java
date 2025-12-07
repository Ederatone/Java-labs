class Node {
    String data;
    Node next;
}

class RingBuffer {
    private Node head;
    private Node tail;
    private int size;
    private final int capacity;

    public RingBuffer(int capacity) {
        this.capacity = capacity;
        this.size = 0;

        this.head = new Node();
        Node current = this.head;
        for (int i = 1; i < capacity; i++) {
            current.next = new Node();
            current = current.next;
        }
        current.next = this.head;
        this.tail = this.head;
    }

    public synchronized void put(String message) {
        while (size == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        tail.data = message;
        tail = tail.next;
        size++;
        notifyAll();
    }

    public synchronized String get() {
        while (size == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String data = head.data;
        head = head.next;
        size--;
        notifyAll();
        return data;
    }
}

public class RingBufferTask {
    public static void main(String[] args) {
        RingBuffer buffer1 = new RingBuffer(10);
        RingBuffer buffer2 = new RingBuffer(10);

        for (int i = 1; i <= 5; i++) {
            final int threadNum = i;
            Thread producer = new Thread(() -> {
                while (true) {
                    String msg = "Producer #" + threadNum + " generated message";
                    buffer1.put(msg);
                    try { Thread.sleep(50); } catch (InterruptedException e) {}
                }
            });
            producer.setDaemon(true);
            producer.start();
        }

        for (int i = 1; i <= 2; i++) {
            final int threadNum = i;
            Thread translator = new Thread(() -> {
                while (true) {
                    String msg = buffer1.get();
                    String newMsg = "Translator #" + threadNum + " translated: [" + msg + "]";
                    buffer2.put(newMsg);
                }
            });
            translator.setDaemon(true);
            translator.start();
        }

        for (int i = 0; i < 100; i++) {
            String result = buffer2.get();
            System.out.println((i + 1) + ". " + result);
        }

        System.out.println("Main thread finished 100 messages. Exiting.");
    }
}
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Buffer {
    private final int[] buffer;
    private int bufferIndex = 0;
    private int bufferSize;
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;

    public Buffer(int bufferSize) {
        this.bufferSize = bufferSize;
        this.buffer = new int[bufferSize];
        this.mutex = new Semaphore(1);
        this.empty = new Semaphore(bufferSize);
        this.full = new Semaphore(0);
    }

    public void producer(long producerId) throws InterruptedException {
        empty.acquire();
        mutex.acquire();

        int num = new Random().nextInt(100) + 1;
        buffer[bufferIndex] = num;
        bufferIndex = (bufferIndex + 1) % bufferSize;

        System.out.println("Üretici " + producerId + " üretti: " + num);

        mutex.release();
        full.release();
    }

    public void consumer(long consumerId) throws InterruptedException, IOException {
        full.acquire();
        mutex.acquire();

        int num = buffer[bufferIndex];
        bufferIndex = (bufferIndex + bufferSize - 1) % bufferSize;

        System.out.println("Tüketici " + consumerId + " tüketti: " + num);

        BufferedWriter writer = new BufferedWriter(new FileWriter("Sayilar.txt", true));
        writer.write(consumerId + ": " + num + "\n");
        writer.close();

        mutex.release();
        empty.release();
    }

    public static void main(String[] args) {
        int numberOfProducers = 5;
        int numberOfConsumers = 5;
        int bufferSize = 100;

        Buffer monitor = new Buffer(bufferSize);

        for (int i = 0; i < numberOfProducers; i++) {
            new Thread(() -> {
                try {
                    while (true) {
                        monitor.producer(Thread.currentThread().getId());
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 0; i < numberOfConsumers; i++) {
            new Thread(() -> {
                try {
                    while (true) {
                        monitor.consumer(Thread.currentThread().getId());
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
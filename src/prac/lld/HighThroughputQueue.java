package prac.lld;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class HighThroughputQueue<T> {
    public interface Consumer<T> {
        void consume(T t);
    }

    private final List<ConsumerWorker<T>> consumers = new CopyOnWriteArrayList<>();

    // Add a consumer with its own queue and thread
    public void addConsumer(Consumer<T> consumer, int maxDepth) {
        ConsumerWorker<T> worker = new ConsumerWorker<>(consumer, maxDepth);
        consumers.add(worker);
        worker.start();
    }

    // Push a message to all consumers
    public void push(T message) {
        for (ConsumerWorker<T> worker : consumers) {
            worker.enqueue(message);
        }
    }

    // Stop all consumer threads
    public void stop() {
        for (ConsumerWorker<T> worker : consumers) {
            worker.stop();
        }
    }

    // Inner class for each consumer worker
    private static class ConsumerWorker<T> {
        private final BlockingQueue<T> queue;
        private final Thread thread;
        private final AtomicBoolean running = new AtomicBoolean(true);

        ConsumerWorker(Consumer<T> consumer, int maxDepth) {
            queue = new LinkedBlockingQueue<>(maxDepth);
            thread = new Thread(() -> {
                while (running.get() && !Thread.currentThread().isInterrupted()) {
                    try {
                        T msg = queue.take();
                        consumer.consume(msg);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }

        void start() {
            thread.start();
        }

        void enqueue(T message) {
            try {
                queue.put(message); // blocks if consumer queue is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        void stop() {
            running.set(false);
            thread.interrupt();
        }
    }
}

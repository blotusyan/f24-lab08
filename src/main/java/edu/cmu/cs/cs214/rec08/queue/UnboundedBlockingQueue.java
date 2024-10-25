package edu.cmu.cs.cs214.rec08.queue;

import java.util.ArrayDeque;
import java.util.Deque;

import net.jcip.annotations.ThreadSafe;
import net.jcip.annotations.GuardedBy;

/**
 * A thread-safe implementation of UnboundedBlockingQueue.
 */
@ThreadSafe
public class UnboundedBlockingQueue<E> implements SimpleQueue<E> {
    @GuardedBy("this")
    private final Deque<E> queue = new ArrayDeque<>();

    public UnboundedBlockingQueue() { }

    @Override
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public synchronized int size() {
        return queue.size();
    }

    @Override
    public synchronized E peek() {
        return queue.peek();
    }

    @Override
    public synchronized void enqueue(E element) {
        queue.add(element);
        // Notify any waiting thread that an element is available.
        notifyAll();
    }

    @Override
    public synchronized E dequeue() throws InterruptedException {
        // While the queue is empty, wait for an element to be enqueued.
        while (queue.isEmpty()) {
            wait();
        }
        // Now that the queue is not empty, return the first element.
        return queue.remove();
    }

    @Override
    public synchronized String toString() {
        return queue.toString();
    }
}

package com.mycompany.app.threads;

/**
 * Created by devlakhova on 1/21/15.
 */
public class Counter {

    private volatile int count = 0;

    public synchronized void add(int count) {
        this.count += count;
        notifyAll();
    }

    public synchronized void dec(int value) {
        this.count -= value;
        notifyAll();
    }

    public int getCount() {
        return count;
    }

    public synchronized void waitFor(int i) throws InterruptedException {
        while (count != i) {
            wait();
        }
    }
}

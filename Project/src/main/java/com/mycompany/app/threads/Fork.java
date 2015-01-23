package com.mycompany.app.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by devlakhova on 1/23/15.
 */
public class Fork {

    private ReentrantLock fork = new ReentrantLock(true);
    //private Condition forkCondition = fork.newCondition();

    public synchronized void waitForLock() {
        if (!fork.isLocked()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        fork.lock();
    }

    public synchronized void waitForLockAndLock() {
        waitForLock();
        lock();
    }

    public void unlock() {
        fork.unlock();
    }

    public synchronized void lock() {
        fork.lock();
        this.notifyAll();
    }

    public boolean isHeldByCurrentThread() {
        return fork.isHeldByCurrentThread();
    }

    public boolean isLocked() {
        return fork.isLocked();
    }
}

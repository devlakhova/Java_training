package com.mycompany.app.threads.semaphoreRWLock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by devlakhova on 1/23/15.
 */
public class ReadWriteLockOnSemaphore implements ReadWriteLock {

    private static final int PERMITS = 1024;

    private Semaphore semaphore = new Semaphore(PERMITS, true);

    @Override
    public Lock readLock() {
        return new Lock() {
            @Override
            public void lock() {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void lockInterruptibly() throws InterruptedException {

            }

            @Override
            public boolean tryLock() {
                return false;
            }

            @Override
            public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public void unlock() {
                semaphore.release();
            }

            @Override
            public Condition newCondition() {
                return null;
            }
        };
    }


    @Override
    public Lock writeLock() {
        return new Lock() {
            @Override
            public void lock() {
                try {
                    semaphore.acquire(PERMITS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void lockInterruptibly() throws InterruptedException {

            }

            @Override
            public boolean tryLock() {
                return false;
            }

            @Override
            public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public void unlock() {
                semaphore.release(PERMITS);
            }

            @Override
            public Condition newCondition() {
                return null;
            }
        };
    }
}

package com.mycompany.app.threads.semaphoreRWLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by devlakhova on 1/23/15.
 */
public class TestReadWriteLock {

    List<String> list = new ArrayList<>();

    public static void main(String[] args) {

        TestReadWriteLock test = new TestReadWriteLock();

        ListChanger runnable = test.new ListChanger();

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();

    }

    private class ListChanger implements Runnable {

        ReadWriteLock rwLock = new ReadWriteLockOnSemaphore();
        Semaphore semaphore = new Semaphore(2);

        @Override
        public void run() {

            System.out.println("Try to enter semaphore " + Thread.currentThread().getName());
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread + " + Thread.currentThread().getName() + "within semaphore");
            System.out.println("Thread + " + Thread.currentThread().getName() + "within semaphore");
            System.out.println("Thread + " + Thread.currentThread().getName() + "within semaphore");
            System.out.println("Released semaphore " + Thread.currentThread().getName());
            semaphore.release();

            rwLock.writeLock().lock();
            System.out.println("Thread + " + Thread.currentThread().getName() + "write lock");
            list.add(Thread.currentThread().getName());
            System.out.println("Added" + Thread.currentThread().getName());
            list.add(Thread.currentThread().getName());
            System.out.println("Added" + Thread.currentThread().getName());
            list.add(Thread.currentThread().getName());
            System.out.println("Added" + Thread.currentThread().getName());
            rwLock.writeLock().unlock();
            System.out.println("Thread + " + Thread.currentThread().getName() + "write unlock");

            rwLock.readLock().lock();
            System.out.println("Thread + " + Thread.currentThread().getName() + "read lock");
            for (String string : list) {
                System.out.println(string);
            }
            rwLock.readLock().unlock();
            System.out.println("Thread + " + Thread.currentThread().getName() + "read unlock");



//            }
        }
    }
}

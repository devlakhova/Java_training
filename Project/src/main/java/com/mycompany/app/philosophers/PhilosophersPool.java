package com.mycompany.app.philosophers;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by devlakhova on 1/22/15.
 */
public class PhilosophersPool {
//    ReentrantLock

    public static final int N = 5;
    Lock[] forks = new Lock[]{new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock(), new ReentrantLock()};
    LinkedList<Runnable> threads;

    public PhilosophersPool() throws InterruptedException {

        threads = new LinkedList<>();


        for (int i = 0; i < 5; i++) {


            PhilosopherThread phi = new PhilosopherThread("" + i + "");

            threads.add(phi);

        }
        feedThemAll();
        while (true) {
            ListIterator<Runnable> itr = threads.listIterator();
            while (itr.hasNext()) {
                PhilosopherThread thread = (PhilosopherThread) itr.next();
                System.out.print("Ph " + thread.name + " ate - " + thread.numberOfEats + " ");
                System.out.println("---");
            }
            Thread.sleep(10_000);
        }
    }

    public void feedThemAll() {
        ListIterator<Runnable> itr = threads.listIterator();
//        while (true) {
        int i = 0;
            while (itr.hasNext()) {
                PhilosopherThread thread = (PhilosopherThread) itr.next();
                if (i == 0) {
                    thread.leftNeib = (PhilosopherThread) threads.get(4);
                    thread.rightNeib = (PhilosopherThread) threads.get(i+1);
                } else if (i == 4) {
                    thread.leftNeib = (PhilosopherThread) threads.get(i-1);
                    thread.rightNeib = (PhilosopherThread) threads.get(0);
                } else  {
                    thread.leftNeib = (PhilosopherThread) threads.get(i-1);
                    thread.rightNeib = (PhilosopherThread) threads.get(i+1);
                }

                Thread newThread = new Thread(thread, "" + i + "");
                i++;
                newThread.start();

            }

//        }
    }



    public class PhilosopherThread implements Runnable {

        private String name;
        public PhilosopherThread leftNeib;
        public PhilosopherThread rightNeib;
        public int numberOfEats = 0;

        public boolean eating;

        PhilosopherThread (String name) {
            this.name = name;
        }

        public void run() {
            while (true) {
                if (nonSyncEat()) {
                    try {
                        Thread.sleep(1000);
                        eating = false;
//                        Thread.sleep(10);
                        Thread.yield();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

        public boolean nonSyncEat () {
            enterRegion(Integer.parseInt(Thread.currentThread().getName()));
            if(!rightNeib.eating && !leftNeib.eating) {
                eating = true;
                numberOfEats++;
                System.out.println("Philosopher " + Thread.currentThread().getName() + "eats");

                leaveRegion(Integer.parseInt(Thread.currentThread().getName()));
                return true;
            }
            leaveRegion(Integer.parseInt(Thread.currentThread().getName()));
            return false;
        }

        public synchronized boolean eat() {
            if(!rightNeib.eating && !leftNeib.eating) {
                eating = true;
                numberOfEats++;
                System.out.println("Philosopher " + Thread.currentThread().getName() + "eats");

                return true;
            }
            return false;
        }
        private static final int FALSE = 0;
        boolean[] interested = new boolean[N];

        int[] turn = new int[N];
        int[] stage = new int[N + 1];

        void enterRegion(int process)
        {
            for (int i = 1; i <= N - 1; i++) // перебор стадий
            {
                stage[process] = i;
                turn[i] = process;
                for (int j = 1; j <= N; j++) // "опрос" остальных процессов
                {
                    if (j == process)
                        continue;
                    while (stage[j] >= i  && turn[i] == process);
                }
            }
        }

        void leaveRegion(int process)
        {
            stage[process] = FALSE;
        }

    }
}

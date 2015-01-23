package com.mycompany.app.philosophers;

import com.mycompany.app.threads.Fork;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by devlakhova on 1/22/15.
 */
public class ScheduledPP {

    public static final int NU_OF_PHILOSOPHERS = 5;

    public static void main(String[] args) throws InterruptedException {


        Fork [] forks = new Fork[NU_OF_PHILOSOPHERS];
        for (int i1 = 0; i1 < forks.length; i1++) {
            forks[i1] = new Fork();
        }
        Philosopher[] philosophers = new Philosopher[NU_OF_PHILOSOPHERS];
        ScheduledPP pp = new ScheduledPP();
        CyclicBarrier barrier = new CyclicBarrier(NU_OF_PHILOSOPHERS);
        for (int i = 0; i < NU_OF_PHILOSOPHERS; i++) {
            philosophers[i] = pp.new Philosopher(i, forks[i], forks[(i+1)%NU_OF_PHILOSOPHERS], barrier);
        }


        philosophers[0].state = STATE.EATING_STATE;
        philosophers[2].state = STATE.EATING_STATE;


        philosophers[4].state = STATE.WAITING_STATE;

        philosophers[1].state = STATE.SLEEPING_STATE;
        philosophers[3].state = STATE.SLEEPING_STATE;

        for (Philosopher p : philosophers) {
            Thread thread = new Thread(p);
            thread.start();
        }
    }

    private enum STATE {
        EATING_STATE, SLEEPING_STATE, WAITING_STATE
    }

    public class Philosopher implements Runnable {




        private CyclicBarrier barrier;


        Integer id;
        Fork rightFork;
        Fork leftFork;
        STATE state;

        public Philosopher (int id,  Fork leftFork, Fork rightFork, CyclicBarrier barrier) {

            this.id = id;
            this.rightFork = rightFork;
            this.leftFork = leftFork;
            this.barrier = barrier;
        }

        private void initState () {
            switch (state) {

                case EATING_STATE:
                    takeLeftFork();
                    takeRightFork();
                    break;
                case SLEEPING_STATE:
                    break;
                case WAITING_STATE:
                    takeLeftFork();
                    break;
            }
        }

        @Override
        public void run() {
            initState();
            try {
                barrier.await();
                //System.out.println(String.format("Philosopher %s is here", this.id));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {
                switch (state) {
                    case EATING_STATE: {
                        if (!rightFork.isHeldByCurrentThread()) {
                            takeRightFork();
                        }
                        if (!leftFork.isHeldByCurrentThread()) {
                            takeLeftFork();
                        }
                        eat();
                        break;
                    }
                    case WAITING_STATE:{
                        try {
                            waitForDinner();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case SLEEPING_STATE: {
                        try {
                            sleep();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }

        public boolean takeRightFork() {
            rightFork.lock();
//            System.out.println(String.format("Philosopher %s pickedup Right Fork", this.id));
            return true;
        }

        public boolean takeLeftFork() {
            leftFork.lock();
//                System.out.println(String.format("Philosopher %s pickedup Left Fork", this.id));
            return true;

        }

        public void eat(){

            System.out.println(String.format("Philosopher %s is eating", this.id));
            try {
                //Thread.sleep(2000);
                Thread.sleep(1000 + (int)(Math.random() * 1000));
                releaseForks();

                leftFork.waitForLock();
                rightFork.waitForLock();

                state = STATE.SLEEPING_STATE;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void sleep() throws InterruptedException {
            //System.out.println(String.format("Philosopher %s is sleeping", this.id));
            //Thread.sleep(10 + (int)(Math.random() * 100));
            if(rightFork.isLocked()) {
                state = STATE.WAITING_STATE;
            } else {
                state = STATE.EATING_STATE;
            }



        }

        public void waitForDinner() throws InterruptedException {
            if(!leftFork.isHeldByCurrentThread()) {
                takeLeftFork();
            }
            //takeRightFork();
            //Thread.sleep(2000);
//            takeLeftFork();
            state = STATE.EATING_STATE;

        }

        public void releaseForks() {
            if(leftFork.isHeldByCurrentThread()) {
                leftFork.unlock();
            }
            if(rightFork.isHeldByCurrentThread()) {
                rightFork.unlock();
            }
        }
    }
}

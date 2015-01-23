package com.mycompany.app.threads;

/**
 * Created by devlakhova on 1/20/15.
 */
public class ThreadsMain {

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("Thread group");
        for(int i=1; i<10; i++){
            Thread thread = new Thread(threadGroup, "" + i){
                public void run(){
                    System.out.println("Thread: " + getName() + " running");
                    while (true) {
                        try {
                            sleep(Integer.MAX_VALUE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.setPriority(i);
//            thread.start();
        }
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);

        for (Thread thread : threads) {
            thread.start();
        }
    }
}

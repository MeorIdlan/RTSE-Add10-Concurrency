package com.rtse;
import java.util.concurrent.Semaphore;

public final class App {
    private App() {
    }

    private static final int THREADS = 2;
    private static int value = 0;
    private static Semaphore sem = new Semaphore(1);

    /**
     * Add 10 to a variable (value) in each thread (number of threads running defined by THREADS)
     * Semaphore (sem) ensures mutual exclusion
     */
    public static void main(String[] args) {
        // create an array of threads to run
        Thread[] threads = new Thread[THREADS];

        // defining each threads with a run() function that runs the add10 function
        // and then starts each of the threads
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    add10();
                }
            });

            threads[i].start();
        }

        // join the threads at the end of the execution
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // print out the total value at the end after all threads are joined
        System.out.println("Total value: " + value);
    }

    private static void add10() {
        for (int i = 0; i < 10; i++) {
            try {
                sem.acquire();
                value++;
                System.out.println("Value: " + value);
                sem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

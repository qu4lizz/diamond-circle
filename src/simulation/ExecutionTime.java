package simulation;

import gui.Simulation;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecutionTime implements Runnable {
    private int executionTime;
    private static final Object lockExecutionTime = new Object();

    private static volatile boolean paused = false;
    private static final Object pauseLock = new Object();

    public static void pause() {
        paused = true;
    }

    public static void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public int getExecutionTime() {
        synchronized (lockExecutionTime) {
            return executionTime;
        }
    }

    @Override
    public void run() {
        long start = new Date().getTime();
        while(!Game.isGameOver()) {
            synchronized (pauseLock) {
                if (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                }
            }
            try {
                synchronized (lockExecutionTime) {
                    executionTime = (int)(new Date().getTime() - start) / 1000;
                    Simulation.executionTimeRefresh(executionTime);
                }
                Thread.sleep(Game.TIME_FOR_RELOAD);
            } catch(InterruptedException e) {
                Logger.getLogger(Thread.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
    }
}

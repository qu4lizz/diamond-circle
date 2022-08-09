package simulation;

import gui.Simulation;
import javafx.application.Platform;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecutionTime implements Runnable {
    private long executionTime;
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

    public long getExecutionTime() {
        synchronized (lockExecutionTime) {
            return executionTime;
        }
    }

    @Override
    public void run() {
        long start = new Date().getTime();
        long waitTime = 0, startWait = 0, completeWait = 0;
        while(!Game.isGameOver()) {
            synchronized (pauseLock) {
                if (paused) {
                    try {
                        startWait = new Date().getTime();
                        pauseLock.wait();
                        waitTime = (new Date().getTime() - startWait) / 1000;
                        completeWait += waitTime;
                    } catch (InterruptedException e) {
                        Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                }
            }
            try {
                synchronized (lockExecutionTime) {
                    executionTime = (new Date().getTime() - start) / 1000 - completeWait;
                    Platform.runLater(() -> Game.getSimulation().executionTimeRefresh(executionTime));
                }
                Thread.sleep(Game.TIME_FOR_RELOAD);
            } catch(InterruptedException e) {
                Logger.getLogger(Thread.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
    }
}

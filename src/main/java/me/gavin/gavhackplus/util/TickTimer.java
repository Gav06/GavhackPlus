package me.gavin.gavhackplus.util;

public class TickTimer {

    double startTick;
    double delay;

    boolean paused;

    public TickTimer(){

        startTick = ((double) System.currentTimeMillis()) / 1000 * 20;

        paused = false;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean isPassed(){
        if (!paused) {
            return ((double) System.currentTimeMillis()) / 1000 * 20 - startTick >= delay;
        }

        return false;
    }

    public boolean isPassedEarly(){
        if (!paused) {
            return ((double) System.currentTimeMillis()) / 1000 * 20 - startTick >= delay - 1;
        }

        return false;
    }

    public void resetDelay(){

        startTick = ((double) System.currentTimeMillis()) / 1000 * 20;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}

package me.bubbles.hotpotato.games;

public class Timer {

    private int ticks;
    private int time;

    public Timer(int ticks) {
        this.time=ticks;
        this.ticks=ticks;
    }

    public void onTick() {
        ticks=clamp(ticks,ticks-1,time,0);
    }

    public boolean isOver() {
        return ticks==0;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.time=ticks;
        this.ticks=ticks;
    }

    private int clamp(int old, int now, int max, int min) {
        if(now<min)
            return old;
        if(now>max)
            return old;
        return now;
    }

}

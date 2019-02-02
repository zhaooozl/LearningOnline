package com.example.learn.timer;

import com.example.learn.timer.OnTimerListener;

import java.util.TimerTask;

public class BaseTimerTask extends TimerTask {

    private OnTimerListener onTimerListener;

    public BaseTimerTask(OnTimerListener onTimerListener) {
        this.onTimerListener = onTimerListener;
    }

    public void setOnTimerListener(OnTimerListener onTimerListener) {
        this.onTimerListener = onTimerListener;
    }

    @Override
    public void run() {
        if (onTimerListener != null) {
            onTimerListener.onTimer();
        }
    }
}

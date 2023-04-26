package com.example.trainer.ui.workouts.currentWorkout;

import java.time.Duration;
import java.util.Objects;

public class WorkoutDurationUpdater extends Thread{

    private boolean isRunning = false;
    private final DurationUpdateMethod target;

    private Duration current;

    public WorkoutDurationUpdater(Duration initialDuration, DurationUpdateMethod target){
        this.target = Objects.requireNonNull(target);
        this.current = Objects.requireNonNull(initialDuration);
    }

    @Override
    public void run(){
        isRunning = true;
        while(isRunning){
            target.updateDuration(current);
            sleepOneSecond();
            current = current.plusSeconds(1);
        }
    }

    private void sleepOneSecond(){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            isRunning = false;
        }
    }

    public void stopUpdating(){
        isRunning = false;
    }

}

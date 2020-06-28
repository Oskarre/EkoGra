package com.group.gra.entities;

public class GameParameters {
    float trashDuration;
    float trashDelay;
    int numberOfTrashes;
    float conveyorSpeed;

    public GameParameters(float trashDuration, float trashDelay, int numberOfTrashes, float conveyorSpeed) {
        this.trashDuration = trashDuration;
        this.trashDelay = trashDelay;
        this.numberOfTrashes = numberOfTrashes;
        this.conveyorSpeed = conveyorSpeed;
    }

    public float getTrashDuration() {
        return trashDuration;
    }

    public float getTrashDelay() {
        return trashDelay;
    }

    public int getNumberOfTrashes() {
        return numberOfTrashes;
    }

    public float getConveyorSpeed() {
        return conveyorSpeed;
    }
}

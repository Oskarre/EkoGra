package com.group.gra.entities;

public class GameParameters {
    float trashSpeed;
    float trashDelay;
    int numberOfTrashes;

    public GameParameters(float trashSpeed, float trashDelay, int numberOfTrashes) {
        this.trashSpeed = trashSpeed;
        this.trashDelay = trashDelay;
        this.numberOfTrashes = numberOfTrashes;
    }

    public float getTrashSpeed() {
        return trashSpeed;
    }

    public float getTrashDelay() {
        return trashDelay;
    }

    public int getNumberOfTrashes() {
        return numberOfTrashes;
    }
}

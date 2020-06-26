package com.group.gra.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;


public class ActorWithStatus {
    Actor actor;
    com.group.gra.entities.ActorStatus status;

    public ActorWithStatus(Actor actor, com.group.gra.entities.ActorStatus status) {
        this.actor = actor;
        this.status = status;
    }

    public Actor getActor() {
        return actor;
    }

    public ActorStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorWithStatus that = (ActorWithStatus) o;
        return that.actor.equals(actor) &&
                status == that.status;
    }
}

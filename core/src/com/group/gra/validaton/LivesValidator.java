package com.group.gra.validaton;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.group.gra.entities.ActorStatus;
import com.group.gra.entities.ActorWithStatus;

public class LivesValidator {
    private Array<Actor> actorsInGame;
    private Array<ActorWithStatus> lastActorsStates;

    public LivesValidator(Array<Actor> actorsInGame, Array<ActorWithStatus> lastActorsStates) {
        this.actorsInGame = actorsInGame;
        this.lastActorsStates = lastActorsStates;
    }

    public boolean hasUserLive() {
        Array<ActorWithStatus> states = removeStaticActors(lastActorsStates);
        Array<ActorWithStatus> actorStatuses = changeActorsStatusWhoWasRemovedBySequenceAction(actorsInGame, states);
        Array<ActorWithStatus> uncorrectMatches = findUncorrectMatches(actorStatuses);
        return !continueGame(uncorrectMatches);
    }

    public  Array<ActorWithStatus>  findUncorrectMatches(Array<ActorWithStatus> states) {
        Array<ActorWithStatus> uncorecctMatches = new Array<>();
        for (ActorWithStatus state : states) {
            if (state.getStatus().equals(ActorStatus.WrongMatched)
                    || state.getStatus().equals(ActorStatus.Expired)) {
                uncorecctMatches.add(state);
            }
        }
        return uncorecctMatches;
    }

    public  Array<ActorWithStatus> findCorrectMatches(Array<ActorWithStatus> states) {
        Array<ActorWithStatus> correctMatches = new Array<>();
        for (ActorWithStatus state : states) {
            if (state.getStatus().equals(ActorStatus.CorrectMatched)) {
                correctMatches.add(state);
            }
        }
        return correctMatches;
    }

    private boolean continueGame(Array<ActorWithStatus> chances) {
        return chances.size >= 3;
    }

    private Array<ActorWithStatus> removeStaticActors(Array<ActorWithStatus> actorStates) {
        for (ActorWithStatus actorWithStatus : actorStates) {
            if (actorWithStatus.getStatus().equals(ActorStatus.StaticActor)) {
                actorStates.removeValue(actorWithStatus, false);
                actorStates.removeValue(actorWithStatus, false);
            }
        }
        return actorStates;
    }

    private Array<ActorWithStatus> changeActorsStatusWhoWasRemovedBySequenceAction(Array<Actor> inGame, Array<ActorWithStatus> actorStates) {
        removeActorsWhichCorrectOrUndefined(inGame, actorStates);
        changeActorsStatusToExpired(inGame, actorStates);

        return actorStates;
    }

    private void removeActorsWhichCorrectOrUndefined(Array<Actor> inGame, Array<ActorWithStatus> actorStates) {
        for (Actor actor : inGame) {
            removedActorByStatus(actorStates, actor, ActorStatus.CorrectMatched);
            removedActorByStatus(actorStates, actor, ActorStatus.Touched);
        }
    }

    private void removedActorByStatus(Array<ActorWithStatus> actorStates, Actor actor, ActorStatus correctMatched) {
        if (actorStates.contains(new ActorWithStatus(actor, correctMatched), false)) {
            actorStates.removeValue(new ActorWithStatus(actor, correctMatched), false);
        }
    }

    private void changeActorsStatusToExpired(Array<Actor> inGame, Array<ActorWithStatus> actorStates) {
        for (ActorWithStatus actorStatus : actorStates) {
            changeActorStatusToExpired(inGame, actorStates, actorStatus);
        }
    }

    private void changeActorStatusToExpired(Array<Actor> inGame, Array<ActorWithStatus> actorStates, ActorWithStatus actorStatus) {
        if (!inGame.contains(actorStatus.getActor(), false) && actorStatus.getStatus().equals(ActorStatus.NotTouched)) {
            actorStates.removeValue(actorStatus, false);
            actorStates.add(new ActorWithStatus(actorStatus.getActor(), ActorStatus.Expired));
        }
    }
}

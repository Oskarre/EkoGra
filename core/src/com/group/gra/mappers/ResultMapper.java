package com.group.gra.mappers;

import com.badlogic.gdx.utils.Array;
import com.group.gra.entities.ActorStatus;
import com.group.gra.entities.ActorWithStatus;
import com.group.gra.entities.MatchStatus;
import com.group.gra.entities.Result;

public class ResultMapper {

    public ResultMapper() {
    }

    public Array<Result> mapToResultList(Array<com.group.gra.entities.ActorWithStatus> actorsWithStatus) {
        Array<com.group.gra.entities.Result> resultList = new Array<>();
        for (ActorWithStatus actor : actorsWithStatus) {
            if (actor.getStatus().equals(com.group.gra.entities.ActorStatus.CorrectMatched)) {
                resultList.add(new com.group.gra.entities.Result(actor.getActor().getName(), com.group.gra.entities.MatchStatus.Correct));
            }
            if (actor.getStatus().equals(com.group.gra.entities.ActorStatus.Expired) || actor.getStatus().equals(ActorStatus.WrongMatched)) {
                resultList.add(new Result(actor.getActor().getName(), MatchStatus.Wrong));
            }
        }
        return resultList;
    }
}

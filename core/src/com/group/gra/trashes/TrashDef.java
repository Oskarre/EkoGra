package com.group.gra.trashes;

import com.badlogic.gdx.math.Vector2;

public class TrashDef {
    public Vector2 position;
    public Class<?> type;

    public TrashDef(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;
    }
}

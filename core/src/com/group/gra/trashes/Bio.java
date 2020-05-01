package com.group.gra.trashes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bio extends Trash {

    public Bio(String name, String imageName) {
        super(name, new Image(new Texture(imageName)));
    }
}

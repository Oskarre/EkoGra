package com.group.gra.trashes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MixedTrash extends Trash{

    public MixedTrash(String name, String imageName) {
        super(name, new Image(new Texture(imageName)));
    }
}

package com.group.gra.trashes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Trash extends Image {
    private Image trash;

    public Trash(Image trash) {
        this.trash = trash;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        trash.draw(batch, parentAlpha);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        trash.setPosition(x, y, alignment);
    }

    @Override
    public void addAction(Action action) {
        trash.addAction(action);
    }

    @Override
    public float getY() {
        return trash.getY();
    }
}

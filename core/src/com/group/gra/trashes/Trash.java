package com.group.gra.trashes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Trash extends Image {
    public Image image;
    protected String name;

    public Trash(String name, Image image) {
        this.name = name;
        this.image = image;
        image.setSize(100, 50);
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        image.draw(batch, parentAlpha);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        image.setPosition(x, y, alignment);
    }

    @Override
    public void addAction(Action action) {
        image.addAction(action);
    }

    @Override
    public float getY() {
        return image.getY();
    }
}

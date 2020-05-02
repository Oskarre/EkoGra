package com.group.gra.trashes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.group.gra.EkoGra;
import com.group.gra.screens.PlayScreen;

import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public abstract class Trash extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Body body;
    protected  Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;


    public Trash(PlayScreen screen,float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        setBounds(getX(),getY(),getWidth()/ EkoGra.PPM,getHeight()/EkoGra.PPM);
        defineTrash();
        toDestroy = false;
        destroyed = false;
    }

    public abstract void defineTrash();
    public abstract void use();

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void draw(Batch batch){
        if(!destroyed){
            super.draw(batch);
        }
    }

    public void destroy(){
        toDestroy = true;
    }
}

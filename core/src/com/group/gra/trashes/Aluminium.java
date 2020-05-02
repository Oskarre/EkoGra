package com.group.gra.trashes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.group.gra.EkoGra;
import com.group.gra.screens.PlayScreen;

public class Aluminium extends Trash{
    public Aluminium(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getPicturelist().random());
        setSize(8/EkoGra.PPM,8/EkoGra.PPM);
        velocity = new Vector2(0.2f,0);
    }

    @Override
    public void defineTrash() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        // box
        shape.setAsBox(4/EkoGra.PPM,4/EkoGra.PPM);
        //bdef.position.set(65/EkoGra.PPM,140/EkoGra.PPM);
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use() {
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        body.setLinearVelocity(velocity);
    }
}

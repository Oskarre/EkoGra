package com.group.gra.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
            Fixture fa = contact.getFixtureA(); // platform
            Fixture fb = contact.getFixtureB(); // square
            System.out.println(fa.getUserData() + " made contact with: " + fb.getUserData());
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA(); // platform
        Fixture fb = contact.getFixtureB(); // square
        System.out.println(fa.getUserData() + " contact ended with:  " + fb.getUserData());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

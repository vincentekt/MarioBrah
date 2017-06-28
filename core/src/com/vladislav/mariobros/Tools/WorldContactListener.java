package com.vladislav.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.vladislav.mariobros.MarioBros;
import com.vladislav.mariobros.Sprites.Enemies.Enemy;
import com.vladislav.mariobros.Sprites.Items.Item;
import com.vladislav.mariobros.Sprites.TileObjects.InteractiveTileObject;
import com.vladislav.mariobros.Sprites.Mario;

/**
 * Created by vincent.tan on 1/1/2017.
 */
public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch(cDef){
            case MarioBros.MARIO_HEAD_BIT | MarioBros.BRICK_BIT:
            case MarioBros.MARIO_HEAD_BIT | MarioBros.COIN_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
                break;
            case MarioBros.ENEMY_HEAD_BIT | MarioBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead((Mario) fixB.getUserData());
                else
                    ((Enemy) fixB.getUserData()).hitOnHead((Mario) fixA.getUserData());
                break;
            case MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.ENEMY_BIT | MarioBros.WALL_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
//            case MarioBros.MARIO_BIT | MarioBros.GROUND_BIT:
//                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_BIT)
//                    Gdx.app.log("MARIO", Float.toString(((Mario) fixA.getUserData()).getY()));
//                    Gdx.app.log("MARIO", Float.toString(((Mario) fixB.getUserData()).getY()));
//                break;
//            case MarioBros.MARIO_BIT | MarioBros.OBJECT_BIT:
//                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_BIT){
//                    Gdx.app.log("MARIO", "Object - previous_state: " + ((Mario) fixA.getUserData()).previousState);
//                    Gdx.app.log("MARIO", "Object - current_state:" + ((Mario) fixA.getUserData()).currentState);
//                     }
//                else {
//                    Gdx.app.log("MARIO", "Object - " + Float.toString(((Mario) fixB.getUserData()).getY()));
//                    Gdx.app.log("MARIO", "Object - previous_state: " + ((Mario) fixB.getUserData()).previousState);
//                    Gdx.app.log("MARIO", "Object - current_state:" + ((Mario) fixB.getUserData()).currentState);
//                }
//                break;
            case MarioBros.MARIO_HEAD_BIT | MarioBros.ENEMY_BIT:
            case MarioBros.MARIO_BIT | MarioBros.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_BIT)
                    ((Mario) fixA.getUserData()).hit((Enemy) fixB.getUserData());
                else
                    ((Mario) fixB.getUserData()).hit((Enemy) fixA.getUserData());
                Gdx.app.log("MARIO", "DIED");
                break;
            case MarioBros.ENEMY_BIT | MarioBros.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).onEnemyHit((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).onEnemyHit((Enemy) fixA.getUserData());
                break;
            case MarioBros.ITEM_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((Item) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.ITEM_BIT | MarioBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT){
                    ((Item) fixA.getUserData()).use((Mario) fixB.getUserData());
                }
                else {
                    ((Item) fixB.getUserData()).use((Mario) fixA.getUserData());
                }
                break;
        }

//        // Gets called when 2 fixtures begin to connect
//        Gdx.app.log("Begin contact", "");
    }

    @Override
    public void endContact(Contact contact) {
//        // Gets called when 2 fixtures stop to connect
//        Gdx.app.log("End contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // To warn something has collided and change blah

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Gives the result of collision
    }
}

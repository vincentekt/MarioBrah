package com.vladislav.mariobros.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.vladislav.mariobros.Screens.PlayScreen;
import com.vladislav.mariobros.Sprites.Mario;

/**
 * Created by vincent.tan on 2/1/2017.
 */
public abstract class Enemy extends Sprite{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;
    public boolean destroyed;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(0.5f, 0);
        b2body.setActive(false);
        destroyed = false;
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void hitOnHead(Mario mario);
    public abstract void onEnemyHit(Enemy enemy);

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = - velocity.x;
        if(y)
            velocity.y = - velocity.y;
    }

    public boolean isDestroyed(){
        return destroyed;
    }
}

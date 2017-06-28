package com.vladislav.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.vladislav.mariobros.MarioBros;
import com.vladislav.mariobros.Scenes.Hud;
import com.vladislav.mariobros.Screens.PlayScreen;

/**
 * Created by vincent.tan on 1/1/2017.
 */
public class Brick extends InteractiveTileObject{
    public Brick(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);

        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(MarioBros.DESTROYED_BIT); //Update to destroyed, in Mario.java destroyed bit is not detected.
        getCell().setTile(null);
        Hud.addScore(200);
        manager.get("audio/sounds/breakblock.wav", Sound.class);
    }
}

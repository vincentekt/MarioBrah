package com.vladislav.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.vladislav.mariobros.MarioBros;
import com.vladislav.mariobros.Scenes.Hud;
import com.vladislav.mariobros.Screens.PlayScreen;
import com.vladislav.mariobros.Sprites.Mario;

/**
 * Created by vincent.tan on 1/1/2017.
 */
public class Brick extends InteractiveTileObject{
    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);

        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {
        Gdx.app.log("Brick", "Collision");
        if(mario.isBig()){
            setCategoryFilter(MarioBros.DESTROYED_BIT); //Update to destroyed, in Mario.java destroyed bit is not detected.
            getCell().setTile(null);
            Hud.addScore(200);
            manager.get("audio/sounds/breakblock.wav", Sound.class);
        }
        else
            manager.get("audio/sounds/bump.wav", Sound.class).play();
    }
}

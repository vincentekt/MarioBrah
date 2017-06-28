package com.vladislav.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.*;
import com.vladislav.mariobros.MarioBros;
import com.vladislav.mariobros.Scenes.Hud;
import com.vladislav.mariobros.Screens.PlayScreen;
import com.vladislav.mariobros.Sprites.Items.ItemDef;
import com.vladislav.mariobros.Sprites.Items.Mushroom;
import com.vladislav.mariobros.Sprites.Mario;

/**
 * Created by vincent.tan on 1/1/2017.
 */
public class Coin extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.COIN_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {
        Gdx.app.log("Coin", "Collision");
        if(getCell().getTile().getId() == BLANK_COIN)
            manager.get("audio/sounds/bump.wav", Sound.class).play();
        else{

            if(object.getProperties().containsKey("mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / MarioBros.PPM), Mushroom.class));
                manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else
                manager.get("audio/sounds/coin.wav", Sound.class).play();
            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(100);
        }
    }
}

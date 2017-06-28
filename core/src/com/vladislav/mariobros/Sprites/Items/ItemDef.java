package com.vladislav.mariobros.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by vincent.tan on 15-Jan-17.
 */
public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;
    }
}

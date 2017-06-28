package com.vladislav.mariobros.Tools;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by vincent.tan on 24-Jan-17.
 */
public class CellInp {
    public Rectangle rect;
    public Cell.State state;

    public CellInp(float x, float y, float width, float height, Cell.State state){
        rect = new Rectangle(x, y, width, height);
        this.state = state;
    }
}

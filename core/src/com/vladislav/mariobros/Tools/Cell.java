package com.vladislav.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.vladislav.mariobros.MarioBros;
import com.vladislav.mariobros.Sprites.Mario;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by vincent.tan on 20-Jan-17.
 */
public class Cell extends Group{
    public int indX;
    public int indY;

    private int indX_c;
    private int indY_c;

    private int tmpX;
    private int tmpY;
    private short pixels;
    private short dispPerPixel;

    public enum State {NONE, GROUND, ENEMY, PIPE, BRICK};

    private int numStates;

    public State cur_state;
    public State pre_state;
    private Class<?> thisClass;

    private Actor body;

    private ShapeRenderer shapeRenderer;

    private float x;
    private float y;
    private float width;
    private float height;

    public Cell(int indX, int indY, short pixels, float width, float height,
                Actor body, ShapeRenderer shapeRenderer) {
        this.indX = indX;
        this.indY = indY;
        this.pixels = pixels;
        this.dispPerPixel = dispPerPixel;
        this.shapeRenderer = shapeRenderer;
        this.body = body;
        thisClass = null;
        cur_state = State.NONE;
        x = 0.125f + body.getX() + indX * width;
        y = 0.125f + body.getY() + indY * height;
        this.width = width;
        this.height = height;
        numStates = State.values().length;
    }

    public void setClass(Class<?> thisClass){
        this.thisClass = thisClass;
    }

    public void setCenterIdx(int indX_c, int indY_c){
        this.indX_c = indX_c;
        this.indY_c = indY_c;
        tmpX = indX - indX_c;
        tmpY = indY - indY_c;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        if (cur_state != pre_state){
            if(thisClass == Mario.class) {
                fillRect(Color.GOLD, x, y, width, height);
            } else {

                switch (cur_state) {
                    case ENEMY:
                        fillRect(Color.RED, x, y, width, height);
                        break;
                    case PIPE:
                        fillRect(Color.GREEN, x, y, width, height);
                        break;
                    case BRICK:
                        fillRect(Color.GRAY, x, y, width, height);
                        break;
                    case GROUND:
                        fillRect(Color.BROWN, x, y, width, height);
                        break;
                    case NONE:
                    default:
                        fillRect(Color.SKY, x, y, width, height);
                        break;
                }
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.box(x, y,0.0f, width, height,
                    0.0f);
            shapeRenderer.end();
            pre_state = cur_state;
        }
    }

    public void fillRect(Color color, float x, float y, float width, float height){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    public int getNumStates(){
        return State.values().length;
    }

    public void update(float dt, float marioPosX, float marioPosY, ArrayList<CellInp> cellInps){
//        , float objPosX, float objPosY, float objWidth,
//        float objHeight, State state
        float pixLim = 10;

        Rectangle marioRect = new Rectangle((marioPosX + tmpX * pixels / MarioBros.PPM),
                (marioPosY + tmpY * pixels / MarioBros.PPM), pixels / MarioBros.PPM,
                pixels / MarioBros.PPM);

        if(thisClass != Mario.class) {
            this.cur_state = State.NONE;

            for(CellInp cellInp : cellInps) {

                if (marioRect.overlaps(cellInp.rect)) {
                    float posLL_x = max(marioRect.getX(), cellInp.rect.getX());
                    float posLL_y = max(marioRect.getY(), cellInp.rect.getY());
                    float posUR_x = min(marioRect.getX() + marioRect.getWidth(),
                            cellInp.rect.getX() + cellInp.rect.getWidth());
                    float posUR_y = min(marioRect.getY() + marioRect.getHeight(),
                            cellInp.rect.getY() + cellInp.rect.getHeight());

                    if (((posUR_x - posLL_x) * (posUR_y - posLL_y)) >
                            ((pixLim / MarioBros.PPM) * (pixLim / MarioBros.PPM)))
                        this.cur_state = cellInp.state;
                }
            }
        }
    }
}

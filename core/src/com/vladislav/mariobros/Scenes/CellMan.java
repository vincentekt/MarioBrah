package com.vladislav.mariobros.Scenes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.vladislav.mariobros.Sprites.Mario;
import com.vladislav.mariobros.Tools.Cell;
import com.vladislav.mariobros.Tools.CellInp;

import java.util.ArrayList;

/**
 * Created by vincent.tan on 20-Jan-17.
 */
public class CellMan {
    public Actor body;
    public ShapeRenderer shapeRenderer;

    public static final short radius = 3;
    public static final short radiusX = 4;
    public static final short radiusY = 3;
    public static final short pixels = 16;
    public static final short centIdx = 29;

    public static final float percDisp_x = 0.025f;
    public static final float percDisp_y = 0.0075f;

    public ArrayList<Cell> cells;

    public CellMan(Stage stage) {
        body = new Actor();


        body.setBounds(0.2f * stage.getWidth(), 0.2f * stage.getHeight(),
                (1 + 2 * radiusX) * percDisp_x * stage.getWidth(),
                (1 + 2 * radiusY) * percDisp_y * stage.getHeight());
        shapeRenderer = new ShapeRenderer();

        cells = new ArrayList<Cell>();

        for(int i = 0; i < (1 + 2 * radiusX); i++)
            for(int j = 0; j < (1 + 2 * radiusY); j++)
                cells.add(new Cell(i, j, pixels, percDisp_x * stage.getWidth(),
                        (1 + 2 * radiusY) * percDisp_y * stage.getHeight(), body, shapeRenderer));

        cells.get(centIdx).setClass(Mario.class);
        for(Cell cell : cells){
            cell.setCenterIdx(cells.get(centIdx).indX, cells.get(centIdx).indY);
            stage.addActor(cell);
        }
    }

    public void update(float dt, float marioPosX, float marioPosY, ArrayList<CellInp> cellInps){
        for(Cell cell : cells)
            cell.update(dt, marioPosX, marioPosY, cellInps);
    }
}

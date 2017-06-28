package com.vladislav.mariobros.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vladislav.mariobros.MarioBros;
import com.vladislav.mariobros.Sprites.Mario;
import com.vladislav.mariobros.Tools.Cell;
import com.vladislav.mariobros.Tools.CellInp;

import java.util.ArrayList;

/**
 * Created by vincent.tan on 20-Jan-17.
 */
public class SceneMan implements Disposable {
    public Stage stage;
    private Viewport viewport; // private and another viewport nad camera because when the game world change we want
    private Hud hud;
    public CellMan cellMan;

    public SceneMan(SpriteBatch sb, float worldWidth, float worldHeight) {
        viewport = new FitViewport(worldWidth, worldHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb); // Empty box, create table in stage to organize widgets
        hud = new Hud(stage);
        cellMan = new CellMan(stage);
    }

    public void toggleCellMan(){
        if(cellMan == null)
            cellMan = new CellMan(stage);
        else{
            for(Cell cell: cellMan.cells)
                cell.remove();
            cellMan = null;}
    }

    public void update(float dt, Mario player, ArrayList<CellInp> cellInps){
        hud.update(dt);
        if(cellMan != null) {
            cellMan.update(dt, player.b2body.getPosition().x - player.getWidth() / 2,
                    player.b2body.getPosition().y - player.getHeight() / 2, cellInps);
        }
    }

    public Hud getHud(){
        return hud;
    }

    public void dispose() {
        stage.dispose();
    }
}

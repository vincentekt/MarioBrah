package com.vladislav.mariobros.MLFW;

import com.badlogic.gdx.Gdx;
import com.vladislav.mariobros.MarioBros;
import com.vladislav.mariobros.Screens.AI_GameOverScreen;
import com.vladislav.mariobros.Screens.PlayScreen;
import com.vladislav.mariobros.Tools.Cell;

/**
 * Created by Victor on 18-Mar-17.
 */
public class Algo_1 {

    private float timeSpent;
    private float timeCount;
    private float distance;
    private float attempt;

    private float speed;

    private int numStates;
    private int numCells;

    public Algo_1(int numStates, int numCells){
        timeCount = 0;

        // OneHotEncoder
        this.numStates = numStates;
        this.numCells = numCells;
    }

    public void update(PlayScreen playScreen, float dt){

        speed = (MarioBros.END_X - playScreen.getPlayer().b2body.getPosition().x) - distance;
        distance = MarioBros.END_X - playScreen.getPlayer().b2body.getPosition().x;
        timeCount += dt;

//        for(Cell cell: playScreen.getSceneMan().cellMan.cells){
////            cell.cur_state.ordinal()
//        }

        if(playScreen.getSceneMan().getHud().getWorldTimer() == 0) {
            playScreen.gameOver = true;
        }
        if(playScreen.getPlayer().b2body.getPosition().x >= MarioBros.END_X ){
            playScreen.gameOver = true;
        }
    }
}

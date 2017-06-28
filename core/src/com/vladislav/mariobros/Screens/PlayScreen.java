package com.vladislav.mariobros.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.vladislav.mariobros.MLFW.Algo_1;
import com.vladislav.mariobros.MarioBros;
import com.vladislav.mariobros.Scenes.CellMan;
import com.vladislav.mariobros.Scenes.SceneMan;
import com.vladislav.mariobros.Sprites.Enemies.Enemy;
import com.vladislav.mariobros.Sprites.Items.Item;
import com.vladislav.mariobros.Sprites.Items.ItemDef;
import com.vladislav.mariobros.Sprites.Items.Mushroom;
import com.vladislav.mariobros.Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vladislav.mariobros.Sprites.Mario;
import com.vladislav.mariobros.Tools.Cell;
import com.vladislav.mariobros.Tools.CellInp;
import com.vladislav.mariobros.Tools.WorldContactListener;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by vincent.tan on 31/12/2016.
 */
public class PlayScreen implements Screen{

    private MarioBros game;
    private static TextureAtlas atlas = new TextureAtlas("Mario_and_Enemies.pack");;

//    Texture texture;

    private static OrthographicCamera gamecam = new OrthographicCamera();
    private static Viewport gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM,
                                               MarioBros.V_HEIGHT / MarioBros.PPM, gamecam);
//    private Hud hud;

//    private AIVision aivision;
    private SceneMan sceneMan;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr; // Gives the graphical representation of fixtures and bodies inside of box2d world
    private B2WorldCreator creator;

    //sprites
    private Mario player;

    private Music music;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    public PlayScreen(MarioBros game){
        this.game = game;
        loader();
    }

    public MarioBros getGame(){
        return game;
    }

    public boolean gameOver;

    private Algo_1 algo_1;

    public void loader(){
//        Gdx.app.log("Wadafak", "abcd");

        gameOver = false;
        sceneMan = new SceneMan(game.batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);

        //Gamecam centering at 0,0 hence only 25% of game screen.
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true); // Gravity
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        // create mario in our game world
        player = new Mario(this);

        world.setContactListener(new WorldContactListener());

        music = game.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
//        music.play();

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

//        algo_1 = new Algo_1(sceneMan.cellMan.cells.get(0).getNumStates(), sceneMan.cellMan.cells.size());
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Mushroom.class){
                items.add(new Mushroom(this, idef.position.x, idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handleInput(float dt){
//        if(Gdx.input.isTouched()){
//            gamecam.position.x += 100 * dt;
//        }
        if(player.currentState != Mario.State.DEAD){
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0){
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            }


            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1.25f){
               if(player.b2body.getLinearVelocity().y == 0 ){
                   if(player.b2body.getLinearVelocity().x < 0){
                       player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
                   } else {
                       player.b2body.applyLinearImpulse(new Vector2(0.75f, 0), player.b2body.getWorldCenter(), true);
                   }
                } else if (player.b2body.getLinearVelocity().y <= 3f){
                    if(player.b2body.getLinearVelocity().x <= 1.25f / 2){
                        player.b2body.applyLinearImpulse(new Vector2(0.25f, 0), player.b2body.getWorldCenter(), true);
                    }
                }
            }

            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -1.25f &&
                    player.b2body.getPosition().x >= 240 / MarioBros.PPM){
                if(player.b2body.getLinearVelocity().y == 0){
                    if(player.b2body.getLinearVelocity().x > 0){
                        player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
                    } else {
                        player.b2body.applyLinearImpulse(new Vector2(-0.75f, 0), player.b2body.getWorldCenter(), true);
                    }
                } else if (player.b2body.getLinearVelocity().y <= 3f){
                    player.b2body.applyLinearImpulse(new Vector2(-0.25f, 0), player.b2body.getWorldCenter(), true);
                }
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
                sceneMan.toggleCellMan();
//                Gdx.app.log("Mario Position", "x: " + Float.toString(player.b2body.getPosition().x) +
//                        " | y: " + Float.toString(player.b2body.getPosition().y));
            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
//            }
        }
    }

    public void update(float dt) {

        int count = 0;
        handleInput(dt);

        handleSpawningItems();

        world.step(1f / 60f, 6, 2);

        player.update(dt);

        ArrayList<CellInp> cellInps = new ArrayList<CellInp>();

        for(Rectangle rect : creator.pipes)
            cellInps.add(new CellInp(rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM,
                    rect.getWidth() / MarioBros.PPM, rect.getHeight() / MarioBros.PPM,
                    Cell.State.PIPE));

        for(Rectangle rect : creator.grounds)
            cellInps.add(new CellInp(rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM,
                    rect.getWidth() / MarioBros.PPM, rect.getHeight() / MarioBros.PPM,
                    Cell.State.GROUND));
        for(Rectangle rect : creator.bricks)
            cellInps.add(new CellInp(rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM,
                    rect.getWidth() / MarioBros.PPM, rect.getHeight() / MarioBros.PPM,
                    Cell.State.BRICK));


//        for(Rectangle rect : creator.blockades)
//            sceneMan.cellMan.update(dt, player.b2body.getPosition().x - player.getWidth() / 2,
//                    player.b2body.getPosition().y  - player.getHeight() / 2,
//                    rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM,
//                    rect.getWidth() / MarioBros.PPM, rect.getHeight() / MarioBros.PPM, Cell.State.BLOCKADE);

        for (Enemy enemy : creator.getEnemies()){
            enemy.update(dt);
            if (!enemy.b2body.isActive() && (enemy.getX() < player.getX() + 224 / MarioBros.PPM))
                enemy.b2body.setActive(true);
            if(enemy.isDestroyed()){
                creator.removeEnemy(enemy);
            }
//            if(enemy.b2body.isActive()){
//                sceneMan.cellMan.update(dt, player.b2body.getPosition().x - player.getWidth() / 2,
//                        player.b2body.getPosition().y - player.getHeight() / 2,
//                        enemy.b2body.getPosition().x - enemy.getWidth() / 2,
//                        enemy.b2body.getPosition().y  - enemy.getHeight() / 2, enemy.getWidth(),
//                        enemy.getHeight(), Cell.State.ENEMY);
//
//            }
            if(enemy.b2body.isActive()){
                cellInps.add(new CellInp(enemy.b2body.getPosition().x - enemy.getWidth() / 2,
                        enemy.b2body.getPosition().y  - enemy.getHeight() / 2, enemy.getWidth(),
                        enemy.getHeight(), Cell.State.ENEMY));
            }
        }




        for(Item item : items)
            item.update(dt);

        sceneMan.update(dt, player, cellInps);


        if(player.currentState != Mario.State.DEAD && player.b2body.getPosition().x >= 240 / MarioBros.PPM){
            gamecam.position.x = player.b2body.getPosition().x;
        }

//        algo_1.update(this, dt);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear colour and alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
//        game.batch.setProjectionMatrix(gamecam.combined);
//        game.batch.begin(); // opens the box
//        game.batch.draw(texture, 0, 0);
//        game.batch.end(); // closes the box and sends it to the screen

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined); // region has been set hence no overriding would occur
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        for(Item item : items)
            item.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(sceneMan.stage.getCamera().combined);
        sceneMan.stage.draw();

        gameOver();
    }

    public void gameOver(){
        if(player.currentState == Mario.State.DEAD && player.getStateTimer() > 3)
            gameOver = true;
        if(gameOver){
            Gdx.app.log("Game", "Over");
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public SceneMan getSceneMan(){
        return sceneMan;
    }

    public Mario getPlayer(){
        return player;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    public AssetManager getManager(){
        return game.manager;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();

    }
}

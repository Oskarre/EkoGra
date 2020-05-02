package com.group.gra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.EkoGra;
import com.group.gra.Handlers.WorldContactListener;
import com.group.gra.trashes.Aluminium;
import com.group.gra.trashes.Trash;
import com.group.gra.trashes.TrashDef;

import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PlayScreen implements Screen {
    private EkoGra ekogra;

    private Texture texture;
    private Sprite spriteBackground;
    private Stage stage;
    //playscreen variables
    private OrthographicCamera gamecam;
    private FitViewport viewport;

    //box2d fizyka
    private Box2DDebugRenderer b2dr;
    private World world;
    private Body body;
    private FixtureDef fdef;
    private BodyDef bdef;
    //sprites - smiecie
    private Trash trash;

    private FileHandle dirWithTextures;
    private Array<Texture> picturelist;

    private Array<Trash> trashes;
    private LinkedBlockingQueue<TrashDef> trashesDef;


    private Sprite test;

    public PlayScreen(EkoGra ekogra) {
        this.ekogra = ekogra;
        gamecam = new OrthographicCamera();
        //gamecam.setToOrtho(false,800,400);
        //gamecam.setToOrtho(false);
        viewport = new FitViewport(EkoGra.V_WIDTH/EkoGra.PPM,EkoGra.V_HEIGHT/EkoGra.PPM,gamecam);
        gamecam.position.set(viewport.getWorldWidth()/2/EkoGra.PPM,viewport.getWorldHeight()/2/EkoGra.PPM,0);

        //prawdopodobnie bedzie potrzebny -> https://github.com/libgdx/libgdx/wiki/Viewports
        stage = new Stage(viewport,ekogra.sb);

        //Testy backgroundu
        Texture backgroundTexture = new Texture("grabackground.png");
        spriteBackground = new Sprite(backgroundTexture);
        spriteBackground.setSize(EkoGra.V_WIDTH,EkoGra.V_HEIGHT);

        //box2d fizyka
        world = new World(new Vector2(0,-10f),true);
        world.setContactListener(new WorldContactListener());
        b2dr = new Box2DDebugRenderer();

        bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(100/EkoGra.PPM,5/EkoGra.PPM);
        fdef = new FixtureDef();

        bdef.position.set(160/EkoGra.PPM,120/EkoGra.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("platform");

        picturelist = selectTrashes();

        trashes = new Array<Trash>();
        trashesDef = new LinkedBlockingQueue<TrashDef>();
        spawnTrash(new TrashDef(new Vector2(65/EkoGra.PPM,140/EkoGra.PPM),Aluminium.class));
//        test = new Sprite();
        //test.setRegion(this.getPicturelist().random());
        //test.setSize(EkoGra.V_WIDTH,EkoGra.V_HEIGHT);
        //System.out.println(this.getPicturelist().random());

    }


    public void spawnTrash(TrashDef trashdef){
        trashesDef.add(trashdef);
    }
    public void handleSpawningTrashes(){
        if(!trashesDef.isEmpty()){
            TrashDef trashdef = trashesDef.poll();
            if(trashdef.type == Aluminium.class){
                trashes.add(new Aluminium(this,trashdef.position.x,trashdef.position.y));
            }
        }
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        //FIZYKA
    }

    public void update(float delta) {
//        if(Gdx.input.isTouched()) {
//            gamecam.position.x += 100 * delta;
//        }
        handleSpawningTrashes();
        //body.setLinearVelocity(new Vector2(0.2f,0));
        gamecam.update();
        world.step(delta,6,2);

        for(Trash trash : trashes) trash.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ekogra.sb.setProjectionMatrix(gamecam.combined);
        b2dr.render(world, gamecam.combined);
        ekogra.sb.begin();
        //spriteBackground.draw(ekogra.sb);
        for(Trash trash : trashes) trash.draw(ekogra.sb);
        //test.draw(ekogra.sb);
        ekogra.sb.end();
        //stage.act(delta);
        //stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        stage.getViewport().update(width,height,true);
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

    }


    public World getWorld() {
        return world;
    }

    public Array<Texture> getPicturelist() {
        return picturelist;
    }

    public Array<Texture> selectTrashes() {
        dirWithTextures = Gdx.files.internal("trashes/");
        //System.out.println(dirWithTextures.list()[1]);
        Array<Texture> pictureList = new Array<>();

        for (int i = 0; i < dirWithTextures.list().length; i++) {
            pictureList.add(new Texture(dirWithTextures.list()[i]));
        };
        pictureList.shuffle();
        return pictureList;
    }
}

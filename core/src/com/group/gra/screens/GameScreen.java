package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.group.gra.EkoGra;
import com.group.gra.trashes.Trash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class GameScreen implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch sb;
    public Sprite spriteBackground;
    private FileHandle dirWithTextures;

    //playscreen variables
    private OrthographicCamera gamecam;
    private StretchViewport viewport;

    //box2d fizyka
    private Box2DDebugRenderer b2dr;
    private World world;
    private Body body;
    private FixtureDef fdef;
    private BodyDef bdef;
    //sprites - smiecie
    private Trash trash;


    BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/black.fnt"), false);

    public GameScreen(SpriteBatch sb) {
        this.sb = sb;
        //trash = new Trash(world);
        gamecam = new OrthographicCamera();
        //ustawienie viewporta na wybrana rozdzielczosc
        viewport = new StretchViewport(EkoGra.V_WIDTH / EkoGra.PPM, EkoGra.V_HEIGHT / EkoGra.PPM, gamecam);
        // ustawienie aby kamera byla na srodku
        //gamecam.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);
        gamecam.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);
        //fizyka
        b2dr = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), true);

        bdef = new BodyDef();
        bdef.position.set(601,297);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(100);
        fdef.shape = shape;
        body.createFixture(fdef);
        trash.defineTrash();
    }

    @Override
    public void show() {
//        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/black.fnt"), false);
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);
//        atlas = new TextureAtlas("ui/uiskin.atlas");
//        skin = new Skin(atlas);
//
//        TextButton.TextButtonStyle textButtonStyle = createTextButtonStyle(font);
//        TextButton buttonComeBack = new TextButton("Powrót MainMenu", textButtonStyle);
//        buttonComeBack.pad(20);
//        buttonComeBack.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(sb));
//            }
//        });

        //createBackground();
        //Label label = createLabel(font);
        //Table table = configureTable(label, buttonComeBack);
        //ArrayList<Image> picturelist = selectTrashes();
        //stage.addActor(table);
//        for (Image t: picturelist) {
//            t.setPosition(100, 200);// < -- set Position according to your requirement.
//            System.out.println(t.getY());
//            t.addAction(Actions.moveTo(300,200,3f));
//            stage.addActor(t);
//        }



        //FIZYKA




    }
    private TextButton.TextButtonStyle createTextButtonStyle(BitmapFont font) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("default-round");
        textButtonStyle.down = skin.getDrawable("default-round-down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = font;
        return textButtonStyle;
    }

    private void createBackground() {
        Texture backgroundTexture = new Texture("grabackground.png");
        spriteBackground = new Sprite(backgroundTexture);
        spriteBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private Label createLabel(BitmapFont font) {
        Label.LabelStyle style = createLabelStyle(font);
        return new Label("Ekran Game", style);
    }

    private Label.LabelStyle createLabelStyle(BitmapFont font) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.background = skin.getDrawable("textfield");
        style.font = font;
        style.fontColor = Color.BLACK;
        return style;
    }

    private Table configureTable(Label label, TextButton buttonComeBack) {
        Table table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.defaults().pad(10).fillX();
        table.add(label).row();
        table.add(buttonComeBack).row();
        return table;
    }

    public ArrayList<Image> selectTrashes() {
        dirWithTextures = Gdx.files.internal("trashes/");
        //System.out.println(dirWithTextures.list()[1]);
        ArrayList<Image> pictureList = new ArrayList<>();

        for (int i = 0; i < dirWithTextures.list().length; i++) {
            pictureList.add(new Image(new Texture(dirWithTextures.list()[i])));
        };
        Collections.shuffle(pictureList);
        return pictureList;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        b2dr.render(world, gamecam.combined);
        sb.setProjectionMatrix(gamecam.combined);
        if(Gdx.input.justTouched())
        {
            System.out.println(Gdx.input.getX());
            System.out.println(Gdx.input.getY());
        }
        sb.begin();
        //spriteBackground.draw(sb);
        //world.step(delta,6,2);
        font.draw(sb,"play prosze",384, 302);
        sb.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //stage.getViewport().update(width, height);
        //gamecam.update();
        //viewport.update(width,height);


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
//        atlas.dispose();
//        skin.dispose();
//        stage.dispose();
//        spriteBackground.getTexture().dispose();
        sb.dispose();
    }
}

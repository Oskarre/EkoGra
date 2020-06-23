package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.DragAndDropNew;
import com.group.gra.trashes.*;

import java.util.HashMap;
import java.util.Map;

import static com.group.gra.DragAndDropNew.*;
import static com.group.gra.EkoGra.*;

public class GameScreen implements Screen {
    private int DELAY_TIME = 2;
    private Stage stage;
    private SpriteBatch sb;
    private Sprite spriteBackground;
    private Image plasticContainer;
    private Image paperContainer;
    private Image hazardousContainer;
    private Image glassContainer;
    private Image bioContainer;
    private Image mixedContainer;
    private Sprite maciag;
    private Music backgroundMusic;
    private Map<String, String> correctMatches;
    private Integer counter;
    private Label label;
    private Skin skin;
    private Window windowPause;
    private boolean pause;
    final Button buttonPause,buttonSound;
    final TextButton buttonResume,buttonQuit;

    public GameScreen(SpriteBatch sb) {
        this.sb = sb;
        FitViewport viewPort = new FitViewport(800, 480);
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);

        spriteBackground = new Sprite(new Texture("gameScreenBackground.png"));
        spriteBackground.setSize(800, 480);

        maciag = new Sprite(new Texture("maciag_1.png"));
        maciag.setSize(1500, 200);
        maciag.setPosition(-350, 0);
        createContainers();
        correctMatches = new HashMap<>();
        counter = 0;
        label = createCounterLabel();
        stage.addActor(label);

        TextureAtlas atlas = new TextureAtlas("ui/design.atlas");
        skin = new Skin(Gdx.files.internal("ui/design.json"), atlas);

        buttonPause = new Button(skin.get("pause",Button.ButtonStyle.class));
        buttonPause.setSize(50, 50);
        addButtonPauseListener(buttonPause);
        stage.addActor(buttonPause);

        buttonResume = new TextButton("Resume",skin);
        addButtonResume(buttonResume);
        buttonQuit = new TextButton("Quit",skin);
        addButtonQuit(buttonQuit);


        Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
        buttonSound = createButtonSound(prefs);
        buttonSound.setBounds(stage.getWidth()-50,stage.getHeight()-50,50,50);
        addButtonSoundListener(buttonSound);

        windowPause = new Window("", skin);
        windowPause.setSize(stage.getWidth(),stage.getHeight());
        windowPause.setMovable(false);
        Table table = new Table(skin);
        windowPause.add(table);

        table.add(buttonResume).row();
        table.add(buttonQuit).row();
        table.add(buttonSound).row();


        TrashGenerator generator = new TrashGenerator();
        Array<Trash> trashArray = generator.generateTrashArray(10);

        for (Trash trash : trashArray) {
            correctMatches.put(trash.getName(), "Zle");
            trash.getImage().setPosition(0, 100);
            SequenceAction sequence = new SequenceAction(Actions.hide(), Actions.delay(DELAY_TIME), Actions.show(),
                    Actions.moveTo(640, 100, 4f), Actions.removeActor());

            addContainersAsActors();
            DragAndDropNew dragAndDropInstance = new DragAndDropNew();
            initializeDragAndDrop(trash, dragAndDropInstance, sequence);
            stage.addActor(trash.getImage());
            trash.getImage().addAction(sequence);
            DELAY_TIME += 2;
        }

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameScreenMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.1f);

        if(prefs.getBoolean(SOUND_ON)) {
            backgroundMusic.play();
        }

    }

    private Label createCounterLabel() {
        TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);
        Label label = new Label(counter.toString(), skin);
        label.setColor(Color.BLACK);
        label.setBounds(600, 200, 100, 100);
        return label;
    }

    private void createContainers() {
        plasticContainer = createContainer("plasticContainer.png", 100, 150);
        paperContainer = createContainer("paperContainer.png", 200, 150);
        glassContainer = createContainer("glassContainer.png", 300, 150);
        hazardousContainer = createContainer("hazardousContainer.png", 400, 150);
        bioContainer = createContainer("bioContainer.png", 500, 150);
        mixedContainer = createContainer("container.png", 600, 150);
    }

    private Image createContainer(String fileName, int x, int y) {
        Image container = new Image(new Texture(fileName));
        container.setSize(100, 100);
        container.setPosition(x, y);
        return container;
    }

    @Override
    public void show() {

        Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
        if(prefs.getBoolean(SOUND_ON))
        {
            backgroundMusic.play();
        }
    }

    private void addContainersAsActors() {
        stage.addActor(plasticContainer);
        stage.addActor(bioContainer);
        stage.addActor(glassContainer);
        stage.addActor(hazardousContainer);
        stage.addActor(paperContainer);
        stage.addActor(mixedContainer);
    }

    private void initializeDragAndDrop(Trash trash, DragAndDropNew dragAndDrop, SequenceAction sequence) {
        createDragAndDropSource(trash, sequence, dragAndDrop);
        createDragAndDropTarget(trash.getImage(), dragAndDrop, bioContainer, Bio.class);
        createDragAndDropTarget(trash.getImage(), dragAndDrop, plasticContainer, Plastic.class);
        createDragAndDropTarget(trash.getImage(), dragAndDrop, hazardousContainer, HazardousTrash.class);
        createDragAndDropTarget(trash.getImage(), dragAndDrop, glassContainer, Glass.class);
        createDragAndDropTarget(trash.getImage(), dragAndDrop, mixedContainer, MixedTrash.class);
        createDragAndDropTarget(trash.getImage(), dragAndDrop, paperContainer, Paper.class);
    }

    private void createDragAndDropSource(final Trash trash, final SequenceAction sequence, final DragAndDropNew dragAndDrop) {
        dragAndDrop.addSource(new Source(trash.getImage()) {
            @Override
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                Payload payload = new Payload();
                payload.setObject(trash);
                payload.setDragActor(getActor());
                dragAndDrop.setDragActorPosition(getActor().getWidth() / 2, -(getActor().getHeight() / 2));
                getActor().removeAction(sequence);
                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                if (target == null)
                    getActor().remove();
                //TODO trashes on ground
            }
        });
    }

    private void createDragAndDropTarget(final Image trash, DragAndDropNew dragAndDrop, Image container, final Object objectType) {
        dragAndDrop.addTarget(new Target(container) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                return payload.getObject().getClass().equals(objectType);
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                counter += 1;
                label.setText(counter.toString());
                trash.remove();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        spriteBackground.draw(sb);
        maciag.draw(sb);
        sb.end();
        if(pause) {
            delta = 0;
        }
        stage.draw();
            stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
        if(prefs.getBoolean(SOUND_ON)) {
            backgroundMusic.pause();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        spriteBackground.getTexture().dispose();
        sb.dispose();
        backgroundMusic.dispose();
    }


    private void addButtonPauseListener(Button buttonPause) {
        buttonPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause=true;
                stage.addActor(windowPause);
            }
        });
    }

    private void addButtonResume(final TextButton buttonResume) {
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause=false;
                windowPause.addAction(Actions.removeActor());
            }
        });
    }

    private void addButtonQuit(final TextButton buttonQuit) {
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(sb));
            }
        });
    }

    private void addButtonSoundListener(final Button buttonSound) {
        buttonSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
                if (prefs.getBoolean(SOUND_ON,true)) {
                    prefs.putBoolean(SOUND_ON, false).flush();
                    buttonSound.setStyle(skin.get("sound_off",Button.ButtonStyle.class));
                    backgroundMusic.stop();
                }
                else {
                    prefs.putBoolean(SOUND_ON, true).flush();
                    buttonSound.setStyle(skin.get("sound_on",Button.ButtonStyle.class));
                    backgroundMusic.play();
                }
            }
        });
    }
    private Button createButtonSound(Preferences prefs) {
        if (prefs.getBoolean(SOUND_ON,true)) {
            return new Button(skin.get("sound_on",Button.ButtonStyle.class));
        } else {
            return new Button(skin.get("sound_off",Button.ButtonStyle.class));
        }
    }
}

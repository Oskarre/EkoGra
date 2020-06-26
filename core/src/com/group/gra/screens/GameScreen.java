package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.mappers.ResultMapper;
import com.group.gra.validaton.DragAndDropNew;
import com.group.gra.trashes.*;
import com.group.gra.uifactory.UIFactory;
import com.group.gra.validaton.LivesValidator;

import static com.group.gra.EkoGra.SETTINGS_FILE;
import static com.group.gra.validaton.DragAndDropNew.*;

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
    private Integer correctMatchesCounter = 0;
    private Label CorrectMatchesLabel;
    private Integer liveCounter = 3;
    private Label livesLabel;
    Array<com.group.gra.entities.ActorWithStatus> actorsWithStatus;
    private PauseWidget pauseWidget;

    public GameScreen(SpriteBatch sb) {
        this.sb = sb;
        FitViewport viewPort = new FitViewport(800, 480);
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        actorsWithStatus = new Array<>();

        UIFactory uiFactory = new UIFactory();
        spriteBackground = uiFactory.createSpriteBackground("gameScreenBackground.png");
        addMaciag();
        createContainers(uiFactory);
        addLabels(uiFactory);


        if(prefs.getBoolean(SOUND_ON)) {
            backgroundMusic.play();
        }

        pauseWidget = new PauseWidget(backgroundMusic,stage,sb);
        pauseWidget.createButtonPause(0, 0, 50, 50);

        playMusic(false);
    }

    private void addMaciag() {
        maciag = new Sprite(new Texture("maciag_1.png"));
        maciag.setSize(1500, 200);
        maciag.setPosition(-350, 0);
    }

    private void addLabels(UIFactory uiFactory) {
        CorrectMatchesLabel = uiFactory.createCounterLabel(correctMatchesCounter);
        livesLabel = uiFactory.createLivesLabel(liveCounter);
        stage.addActor(CorrectMatchesLabel);
        stage.addActor(livesLabel);
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(CorrectMatchesLabel, com.group.gra.entities.ActorStatus.StaticActor));
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(livesLabel, com.group.gra.entities.ActorStatus.StaticActor));
    }

    private void createContainers(UIFactory uiFactory) {
        plasticContainer = uiFactory.createContainer("plasticContainer.png", 100, 150);
        paperContainer = uiFactory.createContainer("paperContainer.png", 200, 150);
        glassContainer = uiFactory.createContainer("glassContainer.png", 300, 150);
        hazardousContainer = uiFactory.createContainer("hazardousContainer.png", 400, 150);
        bioContainer = uiFactory.createContainer("bioContainer.png", 500, 150);
        mixedContainer = uiFactory.createContainer("container.png", 600, 150);
        addContainersAsActors();
    }

    private void addContainersAsActors() {
        stage.addActor(plasticContainer);
        stage.addActor(bioContainer);
        stage.addActor(glassContainer);
        stage.addActor(hazardousContainer);
        stage.addActor(paperContainer);
        stage.addActor(mixedContainer);
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(plasticContainer, com.group.gra.entities.ActorStatus.StaticActor));
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(bioContainer, com.group.gra.entities.ActorStatus.StaticActor));
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(glassContainer, com.group.gra.entities.ActorStatus.StaticActor));
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(hazardousContainer, com.group.gra.entities.ActorStatus.StaticActor));
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(paperContainer, com.group.gra.entities.ActorStatus.StaticActor));
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(mixedContainer, com.group.gra.entities.ActorStatus.StaticActor));
    }

    private void playMusic(boolean turnMusic) {
        if (turnMusic) {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameScreenMusic.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.1f);
            backgroundMusic.play();
        }
    }

    @Override
    public void show() {
        Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
        if(prefs.getBoolean(SOUND_ON))
        {
            backgroundMusic.play();
        }

        TrashGenerator generator = new TrashGenerator();
        Array<Trash> trashArray = generator.generateTrashArray(10);
        for (Trash trash : trashArray) {
            prepareTrashToGame(trash);
        }
    }

    private void prepareTrashToGame(Trash trash) {
        Image image = trash.getImage();
        image.setName(trash.getName());
        image.setPosition(0, 100);
        SequenceAction sequence = new SequenceAction(Actions.hide(), Actions.delay(DELAY_TIME), Actions.show(),
                Actions.moveTo(640, 100, 4f), Actions.removeActor());
        stage.addActor(image);
        image.addAction(sequence);
        DragAndDropNew dragAndDrop = new DragAndDropNew();
        initializeDragAndDrop(trash, dragAndDrop, sequence);
        DELAY_TIME += 2;
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(image, com.group.gra.entities.ActorStatus.NotTouched));
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
                changeMatchStatusToUndefine(getActor());
                getActor().removeAction(sequence);
                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                if (target == null) {
                    changeMatchStatusToWrong(getActor());
                    getActor().remove();
                }
            }
        });
    }

    private void changeMatchStatusToUndefine(Actor trash) {
        removedActorByStatus(actorsWithStatus, trash, com.group.gra.entities.ActorStatus.NotTouched);
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(trash, com.group.gra.entities.ActorStatus.Touched));
    }

    private void changeMatchStatusToWrong(Actor trash) {
        removedActorByStatus(actorsWithStatus, trash, com.group.gra.entities.ActorStatus.NotTouched);
        removedActorByStatus(actorsWithStatus, trash, com.group.gra.entities.ActorStatus.Touched);
        removedActorByStatus(actorsWithStatus, trash, com.group.gra.entities.ActorStatus.CorrectMatched);
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(trash, com.group.gra.entities.ActorStatus.WrongMatched));
    }

    private void createDragAndDropTarget(final Image trash, DragAndDropNew dragAndDrop, Image container, final Object objectType) {
        dragAndDrop.addTarget(new Target(container) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                return payload.getObject().getClass().equals(objectType);
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                correctMatchesCounter += 1;
                CorrectMatchesLabel.setText(correctMatchesCounter.toString());
                changeMatchStatusToCorrect(trash);
                trash.remove();
            }
        });
    }

    private void changeMatchStatusToCorrect(Actor trash) {
        removedActorByStatus(actorsWithStatus, trash, com.group.gra.entities.ActorStatus.NotTouched);
        removedActorByStatus(actorsWithStatus, trash, com.group.gra.entities.ActorStatus.Touched);
        actorsWithStatus.add(new com.group.gra.entities.ActorWithStatus(trash, com.group.gra.entities.ActorStatus.CorrectMatched));
    }

    private void removedActorByStatus(Array<com.group.gra.entities.ActorWithStatus> actorStates, Actor actor, com.group.gra.entities.ActorStatus correctMatched) {
        if (actorStates.contains(new com.group.gra.entities.ActorWithStatus(actor, correctMatched), false)) {
            actorStates.removeValue(new com.group.gra.entities.ActorWithStatus(actor, correctMatched), false);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        spriteBackground.draw(sb);
        maciag.draw(sb);
        sb.end();
        if(pauseWidget.isGamePaused()) {
            delta = 0;
        }
        stage.draw();
        stage.act(delta);

        endLevelWhenUserLivesEnded();
        endLevelWhenTrashesEnded();
    }

    private void endLevelWhenUserLivesEnded() {
        com.group.gra.validaton.LivesValidator livesValidator = new LivesValidator(stage.getActors(), actorsWithStatus);
        if (!livesValidator.hasUserLive()) {
            ResultMapper mapper = new ResultMapper();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new SummaryScreen(sb, mapper.mapToResultList(actorsWithStatus)));
        }
    }

    private void endLevelWhenTrashesEnded() {
        if (stage.getActors().size == 8) {
            ResultMapper mapper = new ResultMapper();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new SummaryScreen(sb, mapper.mapToResultList(actorsWithStatus)));
        }
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
}

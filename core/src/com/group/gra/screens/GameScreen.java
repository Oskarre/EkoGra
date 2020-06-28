package com.group.gra.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.group.gra.entities.ActorStatus;
import com.group.gra.entities.ActorWithStatus;
import com.group.gra.entities.GameParameters;
import com.group.gra.mappers.ResultMapper;
import com.group.gra.validaton.DragAndDropNew;
import com.group.gra.trashes.*;
import com.group.gra.uifactory.UIFactory;
import com.group.gra.validaton.LivesValidator;

import static com.group.gra.EkoGra.*;
import static com.group.gra.validaton.DragAndDropNew.*;

public class GameScreen implements Screen {
    private float DELAY_TIME;

    private Stage stage;
    private SpriteBatch sb;
    private Sprite spriteBackground;
    private Image plasticContainer;
    private Image paperContainer;
    private Image hazardousContainer;
    private Image glassContainer;
    private Image bioContainer;
    private Image mixedContainer;
    private Sprite conveyor;
    private Music backgroundMusic;
    private Label CorrectMatchesLabel;
    private Label livesLabel;
    private Label informationLabel;
    Array<ActorWithStatus> actorsWithStatus;
    private PauseWidget pauseWidget;
    private Preferences prefs;
    private Sound plasticTrashSound;
    private Sound bioAndMixTrashSound;
    private Sound glassAndHazardousTrashSound;
    private Sound paperTrashSound;
    private GameParameters gameParameters;

    public GameScreen(SpriteBatch sb, GameParameters gameParameters) {
        this.sb = sb;
        FitViewport viewPort = new FitViewport(800, 480);
        stage = new Stage(viewPort, sb);
        Gdx.input.setInputProcessor(stage);
        actorsWithStatus = new Array<>();
        prefs = Gdx.app.getPreferences(SETTINGS_FILE);

        UIFactory uiFactory = new UIFactory();
        spriteBackground = uiFactory.createSpriteBackground("gameScreenBackground.png");
        addConveyor();
        createContainers(uiFactory);
        addLabels(uiFactory);

        initMusic();
        if(prefs.getBoolean(SOUND_ON)) {
            backgroundMusic.play();
        }

        pauseWidget = new PauseWidget(backgroundMusic,stage,sb);
        pauseWidget.createButtonPause(0, 0, 50, 50);
        this.gameParameters = gameParameters;
        DELAY_TIME = gameParameters.getTrashDelay();
        initSounds();
    }


    private void addConveyor() {
        conveyor = new Sprite(new Texture("conveyor.png"));
        conveyor.setSize(1500, 200);
        conveyor.setPosition(-350, 0);
    }

    private void addLabels(UIFactory uiFactory) {
        CorrectMatchesLabel = uiFactory.createLabel("Matches: " + 0, 400, 250);
        livesLabel = uiFactory.createLabel("Lives: " + 3, 400, 300);
        informationLabel = uiFactory.createLabel(getInformationLabelText(), 400, 350);
        stage.addActor(CorrectMatchesLabel);
        stage.addActor(livesLabel);
        stage.addActor(informationLabel);
        actorsWithStatus.add(new ActorWithStatus(CorrectMatchesLabel, ActorStatus.StaticActor));
        actorsWithStatus.add(new ActorWithStatus(livesLabel, ActorStatus.StaticActor));
        actorsWithStatus.add(new ActorWithStatus(informationLabel, ActorStatus.StaticActor));
    }

    private String getInformationLabelText() {
        int gameMode = prefs.getInteger(GAME_MODE);
        if(gameMode == LEVEL_MODE){
            prefs.getInteger(GAME_MODE);
            return "Level: " + prefs.getInteger(LEVEL);
        }
        if(gameMode == TRENING_MODE){
            return "Training speed: " + prefs.getInteger(GAME_SPEED);
        }
        return"";
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
        actorsWithStatus.add(new ActorWithStatus(plasticContainer, ActorStatus.StaticActor));
        actorsWithStatus.add(new ActorWithStatus(bioContainer, ActorStatus.StaticActor));
        actorsWithStatus.add(new ActorWithStatus(glassContainer, ActorStatus.StaticActor));
        actorsWithStatus.add(new ActorWithStatus(hazardousContainer, ActorStatus.StaticActor));
        actorsWithStatus.add(new ActorWithStatus(paperContainer, ActorStatus.StaticActor));
        actorsWithStatus.add(new ActorWithStatus(mixedContainer, ActorStatus.StaticActor));
    }

    private void initMusic() {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameScreenMusic.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.1f);
    }

    private void initSounds() {
        plasticTrashSound = Gdx.audio.newSound(Gdx.files.internal("sound/plastic.mp3"));
        plasticTrashSound.setVolume(1,0.1f);
        bioAndMixTrashSound = Gdx.audio.newSound(Gdx.files.internal("sound/bioMixed.mp3"));
        bioAndMixTrashSound.setVolume(1,0.1f);
        glassAndHazardousTrashSound = Gdx.audio.newSound(Gdx.files.internal("sound/glassHazardous.mp3"));
        glassAndHazardousTrashSound.setVolume(1,0.1f);
        paperTrashSound = Gdx.audio.newSound(Gdx.files.internal("sound/paper.mp3"));
        paperTrashSound.setVolume(1,0.1f);
    }

    @Override
    public void show() {
        if(prefs.getBoolean(SOUND_ON))
        {
            backgroundMusic.play();
        }

        TrashGenerator generator = new TrashGenerator();
        Array<Trash> trashArray = generator.generateTrashArray(gameParameters.getNumberOfTrashes());
        for (Trash trash : trashArray) {
            prepareTrashToGame(trash);
        }
    }

    private void prepareTrashToGame(Trash trash) {
        Image image = trash.getImage();
        image.setName(trash.getName());
        image.setPosition(0, 100);
        SequenceAction sequence = new SequenceAction(Actions.hide(), Actions.delay(DELAY_TIME), Actions.show(),
                Actions.moveTo(640, 100, gameParameters.getTrashSpeed()), Actions.removeActor());
        stage.addActor(image);
        image.addAction(sequence);
        DragAndDropNew dragAndDrop = new DragAndDropNew();
        initializeDragAndDrop(trash, dragAndDrop, sequence);
        DELAY_TIME += gameParameters.getTrashDelay();
        actorsWithStatus.add(new ActorWithStatus(image, ActorStatus.NotTouched));
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
        removedActorByStatusIfExist(actorsWithStatus, trash, ActorStatus.NotTouched);
        actorsWithStatus.add(new ActorWithStatus(trash, ActorStatus.Touched));
    }

    private void changeMatchStatusToWrong(Actor trash) {
        removedActorByStatusIfExist(actorsWithStatus, trash, ActorStatus.NotTouched);
        removedActorByStatusIfExist(actorsWithStatus, trash, ActorStatus.Touched);
        removedActorByStatusIfExist(actorsWithStatus, trash, ActorStatus.CorrectMatched);
        actorsWithStatus.add(new ActorWithStatus(trash, ActorStatus.WrongMatched));
    }

    private void createDragAndDropTarget(final Image trash, DragAndDropNew dragAndDrop, Image container, final Object objectType) {
        dragAndDrop.addTarget(new Target(container) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                return payload.getObject().getClass().equals(objectType);
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                changeMatchStatusToCorrect(trash);
                if(prefs.getBoolean(SOUND_ON)){
                    playTrashSound(objectType);
                }
                trash.remove();
            }
        });
    }

    private void playTrashSound(Object type) {

        if(type.equals(Bio.class) || type.equals(MixedTrash.class)){
            bioAndMixTrashSound. play();
        }
        if(type.equals(Glass.class) || type.equals(HazardousTrash.class)){
            glassAndHazardousTrashSound. play();
        }
        if(type.equals(Plastic.class) ){
            plasticTrashSound. play();
        }
        if(type.equals(Paper.class) ){
            paperTrashSound. play();
        }
    }

    private void changeMatchStatusToCorrect(Actor trash) {
        removedActorByStatusIfExist(actorsWithStatus, trash, ActorStatus.NotTouched);
        removedActorByStatusIfExist(actorsWithStatus, trash, ActorStatus.Touched);
        actorsWithStatus.add(new ActorWithStatus(trash, ActorStatus.CorrectMatched));
    }

    private void removedActorByStatusIfExist(Array<ActorWithStatus> actorStates, Actor actor, ActorStatus correctMatched) {
        if (actorStates.contains(new ActorWithStatus(actor, correctMatched), false)) {
            actorStates.removeValue(new ActorWithStatus(actor, correctMatched), false);
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        spriteBackground.draw(sb);
        conveyor.draw(sb);
        sb.end();
        if(pauseWidget.isGamePaused()) {
            delta = 0;
        }
        stage.draw();
        stage.act(delta);
        LivesValidator livesValidator = new LivesValidator(stage.getActors(), actorsWithStatus);
        endLevelWhenUserLivesEnded(livesValidator);
        endLevelWhenTrashesEnded();

        changeLabelStatus(livesValidator);
    }

    private void changeLabelStatus(LivesValidator livesValidator) {
        Array<ActorWithStatus> uncorrectMatches = livesValidator.findUncorrectMatches(actorsWithStatus);
        livesLabel.setText("Lives: " + (3 - uncorrectMatches.size));

        Array<ActorWithStatus> correctMatches = livesValidator.findCorrectMatches(actorsWithStatus);
        CorrectMatchesLabel.setText("Matches: " + correctMatches.size);
    }

    private void endLevelWhenUserLivesEnded(LivesValidator livesValidator) {
        if (!livesValidator.hasUserLive()) {
            ResultMapper mapper = new ResultMapper();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new SummaryScreen(sb, mapper.mapToResultList(actorsWithStatus),false));
        }
    }

    private void endLevelWhenTrashesEnded() {
        if (stage.getActors().size == 10) {
            ResultMapper mapper = new ResultMapper();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new SummaryScreen(sb, mapper.mapToResultList(actorsWithStatus),true));
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

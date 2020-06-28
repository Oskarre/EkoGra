package com.group.gra.uifactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class UIFactory {
    public UIFactory() {
    }

    public Label createLabel(String labelValue, int x, int y) {
        TextureAtlas atlas = new TextureAtlas("ui/design.atlas");
        Skin skin = new Skin(Gdx.files.internal("ui/design.json"), atlas);
        Label label = new Label(labelValue, skin);
        label.setColor(Color.BLACK);
        label.setBounds(x, y, 100, 100);
        return label;
    }

    public Image createContainer(String fileName, int x, int y) {
        Image container = new Image(new Texture(fileName));
        container.setSize(100, 100);
        container.setPosition(x, y);
        return container;
    }

    public Button createReturnButton() {
        TextureAtlas atlas = new TextureAtlas("ui/design.atlas");
        Skin skin = new Skin(Gdx.files.internal("ui/design.json"), atlas);
        Button returnButton =  new Button(skin.get("return",Button.ButtonStyle.class));
        returnButton.setBounds(750, 0, 50, 50);
        return returnButton;
    }

    public Button createPlayButton() {
        TextureAtlas atlas = new TextureAtlas("ui/design.atlas");
        Skin skin = new Skin(Gdx.files.internal("ui/design.json"), atlas);
        Button returnButton =  new Button(skin.get("play",Button.ButtonStyle.class));
        returnButton.setBounds(750, 60, 50, 50);
        return returnButton;
    }

    public Sprite createSpriteBackground(String fileName) {
        Texture backgroundTexture = new Texture(fileName);
        Sprite spriteBackground = new Sprite(backgroundTexture);
        spriteBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return spriteBackground;
    }
}

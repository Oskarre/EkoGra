package com.group.gra.uifactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class UIFactory {
    public UIFactory() {
    }

    public Label createCounterLabel(Integer labelValue) {
        TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);
        Label label = new Label(labelValue.toString(), skin);
        label.setColor(Color.BLACK);
        label.setBounds(600, 200, 100, 100);
        return label;
    }

    public Label createLivesLabel(Integer labelValue) {
        TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);
        Label label = new Label(labelValue.toString(), skin);
        label.setColor(Color.WHITE);
        label.setBounds(600, 250, 100, 100);
        return label;
    }

    public Image createContainer(String fileName, int x, int y) {
        Image container = new Image(new Texture(fileName));
        container.setSize(100, 100);
        container.setPosition(x, y);
        return container;
    }

    public ImageButton createReturnButton() {
        Texture texture = new Texture("returnButton.png");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        ImageButton returnButton = new ImageButton(drawable);
        returnButton.setBounds(750, 0, 50, 50);
        return returnButton;
    }

    public Sprite createSpriteBackground(String fileName) {
        Texture backgroundTexture = new Texture(fileName);
        Sprite spriteBackground = new Sprite(backgroundTexture);
        spriteBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return spriteBackground;
    }
}

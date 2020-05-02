package com.group.gra.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.group.gra.EkoGra;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(EkoGra.TITLE);
        config.setWindowedMode(1240,624);
        new Lwjgl3Application(new EkoGra(), config);
    }
}

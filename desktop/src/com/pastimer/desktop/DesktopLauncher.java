package com.pastimer.desktop;

import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pastimer.Pastimer;

import java.awt.Dimension;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        config.title = "Pastimer";
        config.width = screenDimension.width;
        config.height = screenDimension.height;
        config.resizable = false;
        config.x = -8;
        config.y = 0;
        config.foregroundFPS = 60;
        new LwjglApplication(new Pastimer(), config);
    }
}
package com.pastimer.desktop;

import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pastimer.Pastimer;

import java.awt.Dimension;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
<<<<<<< HEAD
        
        config.title = "Pastimer";
        config.width = 800;
        config.height = 480;
        new LwjglApplication(new Drop(), config);
=======
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        config.title = "Pastimer";
        config.width = screenDimension.width;
        config.height = screenDimension.height;
        config.resizable = false;
        config.x = -8;
        config.y = 0;
        config.foregroundFPS = 60;
        new LwjglApplication(new Pastimer(), config);
>>>>>>> 6522d39f007c773843d5dd36964410dc9fddfde4
    }
}
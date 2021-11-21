package com.pastimer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopLauncher {
  
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Minesweeper";
        config.width = 500;
        config.height = 520;
        new LwjglApplication(new Minesweeper(), config); 
     


    }
}
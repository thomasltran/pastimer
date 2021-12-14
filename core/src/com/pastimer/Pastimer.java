package com.pastimer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Pastimer extends Game{
    static public Skin skin;
    public void create(){
        skin = new Skin(Gdx.files.internal("pastimer.json"));
        this.setScreen(new WelcomeScreen(this));
    }

    public void render(){
        super.render();
    }

    public void dispose(){

    }
}

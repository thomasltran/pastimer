package com.pastimer.desktop;

import com.rafaskoberg.gdx.typinglabel.utils.*;
import com.rafaskoberg.gdx.typinglabel.effects.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rafaskoberg.gdx.typinglabel.*;

public class WelcomeScreen extends ApplicationAdapter {

    public Stage stage;
    Skin skin;

    @Override
    public void create(){
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("test1.json"));
        TypingLabel text = new TypingLabel("{WAIT=1.0}{GRADIENT}{SPEED=0.15}pastimer", skin, "title");//need to add in a delay
        text.setPosition(Gdx.graphics.getWidth()/2-133, Gdx.graphics.getHeight()/2);
        stage.addActor(text);
        Gdx.input.setInputProcessor(stage); //handles mouse/keyboard
    }
    @Override
    public void render(){
        Gdx.gl.glClearColor(220/255.0f, 133/255.0f, 133/255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}

package com.pastimer.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
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
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("test1.json"));
        this.setUp();        
    }
    @Override
    public void render(){
        Gdx.gl.glClearColor(220/255.0f, 133/255.0f, 133/255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose(){
        stage.dispose();
    }

    public void setUp(){
        TypingLabel text = new TypingLabel("{WAIT=1.0}{GRADIENT}{SPEED=0.15}pastimer", skin, "title");
        Texture texture = new Texture(Gdx.files.internal("peachLogo.png"));
        Image image = new Image(texture);
        MoveToAction position = new MoveToAction();

        text.setPosition(Gdx.graphics.getWidth()/2-133-10, Gdx.graphics.getHeight()/2);
        image.setPosition(Gdx.graphics.getWidth()/2+133, Gdx.graphics.getHeight()/2);
        image.setSize(image.getWidth()/3, image.getHeight()/3);

        position.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        position.setDuration(5);

        text.addAction(position);

        stage.addActor(text);
        stage.addActor(image);
    }
}

package com.pastimer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.rafaskoberg.gdx.typinglabel.*;

public class TimerScreen implements Screen {

    private Stage stage;
    private Game game;

    public TimerScreen(Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
        int num = 25;
         Label time = new Label(String.valueOf(num), Pastimer.skin, "time");
        time.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stage.addActor(time);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(220/255.0f, 133/255.0f, 133/255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        stage.dispose();
    }
}
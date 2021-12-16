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
import com.badlogic.gdx.utils.TimeUtils;
import com.rafaskoberg.gdx.typinglabel.*;

public class TimerScreen implements Screen {

    private Stage stage;
    private Game game;
    private Time timer;
    private Label display;
    private long currTime;

    public TimerScreen(Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        timer = new Time();
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
        display = new Label(timer.getString(timer.getHour())+":"
                                +timer.getString(timer.getMinute())+":"
                                +timer.getString(timer.getSecond()), Pastimer.skin, "time");
        System.out.println(display.getWidth()+" "+display.getHeight());
        display.setPosition(Gdx.graphics.getWidth()/2-233, Gdx.graphics.getHeight()-144-72);
        stage.addActor(display);
        currTime = TimeUtils.nanoTime();
    }
    //466 144

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(220/255.0f, 133/255.0f, 133/255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        display.setText(timer.getString(timer.getHour())+":"
                +timer.getString(timer.getMinute())+":"
                +timer.getString(timer.getSecond()));
        if(TimeUtils.nanosToMillis(currTime) + 1000 < TimeUtils.nanosToMillis(TimeUtils.nanoTime())){
            currTime = TimeUtils.nanoTime();
            timer.subtract();
        }
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
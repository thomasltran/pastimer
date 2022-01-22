package com.pastimer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TimerScreen implements Screen {

    private Stage stage;
    private Game game;
    private Time timer;
    private TextField display;
    private long currTime;
    private Button add;
    private Button subtract;
    private TextButton welcomeScreen;
    private TextButton mineSweeperScreen;
    private TextField toDoInput;
    int count;
    public TimerScreen(Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        timer = new Time();
        count = 0;
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);

        buildTimer();
        buildButtons();

        //change to a screen example
        welcomeScreen = new TextButton("Welcome", Pastimer.skin);
        welcomeScreen.setPosition(10, 10);
        stage.addActor(welcomeScreen);
        //look at render section

        mineSweeperScreen = new TextButton("Mine Sweeper", Pastimer.skin);
        mineSweeperScreen.setPosition(10,40);
        stage.addActor(mineSweeperScreen);

        toDoInput = new TextField("hello world", Pastimer.skin);

        toDoInput.setPosition(500, 500);
        toDoInput.setSize(150, 150);
        stage.addActor(toDoInput);
    }
    //466 144

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(234/255.0f, 172/255.0f, 172/255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        /*display.setText(timer.getString(timer.getHour())+":"
                +timer.getString(timer.getMinute())+":"
                +timer.getString(timer.getSecond()));*/
        if(TimeUtils.nanosToMillis(currTime) + 1000 < TimeUtils.nanosToMillis(TimeUtils.nanoTime())){
            currTime = TimeUtils.nanoTime();
            timer.subtract();
        }
        if(add.isPressed()){
            timer.add();
            stage.setKeyboardFocus(null);
        }
        if(subtract.isPressed()){
            timer.subtract();
        }

        //change to a screen example
        if(welcomeScreen.isPressed())
            game.setScreen(new WelcomeScreen(game));
        if(mineSweeperScreen.isPressed())
            game.setScreen(new MineSweeperScreen(game));
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

    private void buildTimer(){
        display = new TextField(timer.getString(timer.getHour())+":"
                                +timer.getString(timer.getMinute())+":"
                                +timer.getString(timer.getSecond()), Pastimer.skin, "time");
        display.setSize(800, 200);
        display.setPosition(Gdx.graphics.getWidth()/2-386, Gdx.graphics.getHeight()-239);
        display.setFocusTraversal(true);
        display.setAlignment(1);
      /*  display.setTouchable(Touchable.enabled);
        display.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                count++;
                System.out.println("touch"+count);

            }
        });*/
        stage.addActor(display);
    }

    private void buildButtons(){
        add = new Button(Pastimer.skin);
        add.setPosition(display.getX(), display.getY()-30);
        stage.addActor(add);
        subtract = new Button(Pastimer.skin);
        subtract.setPosition(display.getX()+30, display.getY()-30);
        stage.addActor(subtract);
        currTime = TimeUtils.nanoTime();
    }
}
package com.pastimer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.rafaskoberg.gdx.typinglabel.*;

public class WelcomeScreen implements Screen {

    private Stage stage;
    private Game game;
    private long delayTime;

    public WelcomeScreen(Game gam){
        this.game = gam;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show(){
        this.setup();
//        ImageButton button = new ImageButton(Pastimer.skin);
//        button.setPosition(20,20);
//        button.addListener(new InputListener(){
//            @Override
//            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                game.setScreen(new TimerScreen(game));
//            }
//            @Override
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                return true;
//            }
//        });
//        stage.addActor(button);
        delayTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(220/255.0f, 133/255.0f, 133/255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        if(TimeUtils.nanosToMillis(delayTime) + 6000 < TimeUtils.nanosToMillis(TimeUtils.nanoTime()))
            game.setScreen(new TimerScreen(game));
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

    public void setup(){
        TypingLabel text = new TypingLabel("{WAIT=1.0}{GRADIENT}{SPEED=0.125}pastimer", Pastimer.skin, "title");
        //266 79
        //216 219
        Texture texture = new Texture(Gdx.files.internal("peachLogo.png"));
        Image image = new Image(texture);
        System.out.println(text.getWidth() + " " + text.getHeight());
        System.out.println(texture.getWidth()+" "+texture.getHeight());
        text.setPosition(Gdx.graphics.getWidth()/2-133, Gdx.graphics.getHeight()/2);
        image.setPosition(Gdx.graphics.getWidth()/2+143, Gdx.graphics.getHeight()/2-7);
        image.setSize(image.getWidth()/2.2f, image.getHeight()/2.2f);
        image.addAction(Actions.sequence(Actions.fadeOut(0), Actions.fadeOut(2.5f), Actions.fadeIn(2)));
        stage.addActor(text);
        stage.addActor(image);
    }
}
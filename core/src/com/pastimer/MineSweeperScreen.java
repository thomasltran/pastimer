package com.pastimer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class MineSweeperScreen implements Screen {

    private Game game;
    private Stage stage;
    OrthographicCamera camera;

    public MineSweeperScreen(Game game) {
        this.game = game;
        Texture s = new Texture("start.png");
        Image start = new Image(s);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        start.setPosition(500 - start.getWidth() / 2, 520 - start.getHeight() / 2);
        stage.addActor(start);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
        
       
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
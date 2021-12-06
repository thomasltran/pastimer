package com.pastimer.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;


public class MainMenuScreen implements Screen {

    final Minesweeper game;
    OrthographicCamera camera;
    private Texture start;

    public MainMenuScreen(final Minesweeper gam) {
        game = gam;
        start = new Texture("start.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 500, 520);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        game.batch.draw(start, (500 - start.getWidth()) / 2, (520 - start.getHeight()) / 2);
        game.batch.end();

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
    }
}
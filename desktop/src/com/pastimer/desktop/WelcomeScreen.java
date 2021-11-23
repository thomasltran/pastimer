//to-do: animate in letters, don't have to click to enter timer
package com.pastimer.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class WelcomeScreen implements Screen {

    final Pastimer intro;
    OrthographicCamera camera;

    public WelcomeScreen(final Pastimer in) {
        intro = in;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        intro.batch.setProjectionMatrix(camera.combined);

        intro.batch.begin();
        intro.font.draw(intro.batch, "Pastimer ", 100, 150);
        intro.font.draw(intro.batch, "Tap anywhere to begin!", 100, 100);
        intro.batch.end();

        /*if (Gdx.input.isTouched()) {
            intro.setScreen(new GameScreen(new Drop()));
            dispose();
        }*/
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